package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.integrationTests;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.*;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.OrdenDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.*;
import testing.BaseIntegrationAbstract;
import testing.ContainerExtension;
import testing.NeedsLiberty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class PagoBeanIT extends BaseIntegrationAbstract {


    private PagoBean pagoBean;
    private OrdenBean ordenBean;
    private PagoDetalleBean pagoDetalleBean;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();

        ordenBean = new OrdenBean();
        ordenBean.em = em;

        pagoDetalleBean = new PagoDetalleBean();
        pagoDetalleBean.setEm(em);

        pagoBean = new PagoBean();
        pagoBean.setEm(em);
        pagoBean.setOrdenBean(ordenBean);
        pagoBean.setPagoDetalleBean(pagoDetalleBean);
    }

    @Test
    @Order(1)
    public void testRegistrarPago() {
        System.out.println("PagoBeanIT -------------> testRegistrarPago");

        EntityTransaction tx = em.getTransaction();

        Orden orden = null;
        BigDecimal montoPagado = null;

        try {
            tx.begin();

            // Obtener un ProductoPrecio existente
            ProductoPrecio productoPrecio = em.find(ProductoPrecio.class, 8L);
            assertNotNull(productoPrecio);

            // Crear ítem de carrito con ese ProductoPrecio
            CarritoItemDTO item = new CarritoItemDTO();
            item.setIdProductoPrecio(productoPrecio.getIdProductoPrecio());
            item.setCantidad(4);
            item.setPrecio(productoPrecio.getPrecioSugerido());

            List<CarritoItemDTO> carrito = new ArrayList<>();
            carrito.add(item);

            // Usar OrdenBean para crear la orden con el carrito
            ordenBean.setProductoPrecioBean(new ProductoPrecioBean() {
                @Override
                public ProductoPrecio findById(Object id) {
                    return em.find(ProductoPrecio.class, id);
                }
            });

            ordenBean.setOrdenDetalleBean(new OrdenDetalleBean() {
                @Override
                public void create(OrdenDetalle entity) {
                    em.persist(entity);
                }
            });

            OrdenDTO ordenDTO = ordenBean.crearOrdenCarrito(carrito, "ZARZA");
            assertNotNull(ordenDTO.getIdOrden());

            // Buscar la orden persistida
            orden = em.find(Orden.class, ordenDTO.getIdOrden());
            assertNotNull(orden);
            assertEquals(1, orden.getOrdenDetalleList().size());

            // Calcular el monto
            montoPagado = productoPrecio.getPrecioSugerido().multiply(BigDecimal.valueOf(4));

            tx.commit();
            System.out.println("Orden creada con ID: " + orden.getIdOrden());

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creando la orden", e);
            if (tx.isActive()) tx.rollback();
            fail("No se pudo crear la orden correctamente.");
        }

        try {
            tx.begin();

            Pago pago = pagoBean.registrarPago(
                    orden.getIdOrden(),
                    "EFECTIVO",
                    "REF123",
                    montoPagado,
                    "Pago exitoso"
            );

            tx.commit();

            assertNotNull(pago);
            assertNotNull(pago.getIdPago());
            assertEquals("EFECTIVO", pago.getMetodoPago());
            System.out.println("--> PAGO <--");
            System.out.println("Pago registrado con ID: " + pago.getIdPago() +
                    "\nFECHA: " + pago.getFecha() +
                    "\nMETODO PAGO: " + pago.getMetodoPago() +
                    "\nREFERENCIA: " + pago.getReferencia());
            System.out.println("--> PAGO DETALLE <--");
            System.out.println(pago.getPagoDetalleList());

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al registrar el pago", e);
            if (tx.isActive()) tx.rollback();
            fail("Falló el registro del pago.");
        }
    }
}
