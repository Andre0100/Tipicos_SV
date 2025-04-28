package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenDetalleBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoPrecioBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenBeanTest {

    @Mock
    ProductoPrecioBean ppBean;
    @Mock
    OrdenDetalleBean odBean;
    @Mock
    EntityManager em;
    @InjectMocks
    OrdenBean cut;

    Producto producto;
    ProductoPrecio productoPrecio;

    @BeforeEach
    void setUp() {
        //Carga de producto
        producto = new Producto();
        producto.setNombre("Pupusa");
        productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
    }

    @Test
    void crearOrdenCarrito(){
        System.out.println("OrdenBean --> crearOrdenCarrito");
        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setPrecio(new BigDecimal("0.75"));

        when(ppBean.findById(1L)).thenReturn(productoPrecio);

        doAnswer(invocation ->{
            Orden orden = invocation.getArgument(0);
            orden.setIdOrden(1L);
            return null;
        }).when(em).persist(any(Orden.class));

        doNothing().when(odBean).create(any(OrdenDetalle.class));

        Orden resultado = cut.crearOrdenCarrito(List.of(item), "Sucursal Centro");

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Sucursal Centro", resultado.getSucursal());
        Assertions.assertFalse(resultado.getOrdenDetalleList().isEmpty());
        Assertions.assertEquals(1, resultado.getOrdenDetalleList().size());

        OrdenDetalle ordenDetalle = resultado.getOrdenDetalleList().get(0);
        Assertions.assertEquals(1L, ordenDetalle.getProductoPrecio().getIdProductoPrecio());
        Assertions.assertEquals(2, ordenDetalle.getCantidad());
        Assertions.assertEquals(new BigDecimal("0.75"), ordenDetalle.getPrecio());

        verify(ppBean).findById(1L);
        verify(odBean).create(any(OrdenDetalle.class));
        verify(em).persist(any(Orden.class));
    }

    @Test
    void crearOrdenCarrito_CarritoNulo() {
        System.out.println("OrdenBean --> crearOrdenCarrito_CarritoNulo");
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cut.crearOrdenCarrito(null, "Sucursal Centro");
        });
        Assertions.assertEquals("El carrito está vacío.", ex.getMessage());
    }

    @Test
    void crearOrdenCarrito_CarritoVacio() {
        System.out.println("OrdenBean --> crearOrdenCarrito_CarritoVacio");
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cut.crearOrdenCarrito(List.of(), "Sucursal Centro");
        });
        Assertions.assertEquals("El carrito está vacío.", ex.getMessage());
    }

    @Test
    void crearOrdenCarrito_SinIdOrden() {
        System.out.println("OrdenBean --> crearOrdenCarrito_SinIdOrden");
        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(99L); // Producto existente

        // Simula que no hay ID de orden
        assertThrows(IllegalArgumentException.class, () -> {
            cut.crearOrdenCarrito(List.of(item), "Sucursal Centro");
        });
    }
}
