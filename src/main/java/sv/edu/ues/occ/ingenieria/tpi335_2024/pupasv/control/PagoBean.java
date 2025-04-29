package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.PagoDetalle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class PagoBean extends AbstractDataPersistence<Pago> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;

    @Inject
    OrdenBean ordenBean;
    @Inject
    PagoDetalleBean pagoDetalleBean;

    public PagoBean() {
        super(Pago.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Pago registrarPago(Long idOrden, String metodoPago, String referencia, BigDecimal montoPagado, String observaciones) {
        if (idOrden == null || metodoPago == null || montoPagado == null) {
            throw new IllegalArgumentException("Los datos de pago son inválidos.");
        }
        // Buscar la orden
        Orden orden = ordenBean.findById(idOrden);
        if (orden == null) {
            throw new IllegalArgumentException("No se encontró la orden.");
        }
        // Validar total de la orden
        BigDecimal totalOrden = calcularTotalOrden(orden);
        if (totalOrden.compareTo(montoPagado) != 0) {
            throw new IllegalArgumentException("El monto pagado no coincide con el total de la orden.");
        }
        // Crear el Pago
        Pago pago = new Pago();
        pago.setFecha(new Date());
        pago.setMetodoPago(metodoPago);
        pago.setReferencia(referencia);
        pago.setIdOrden(orden);

        this.create(pago);

        // Crear el PagoDetalle
        PagoDetalle detalle = new PagoDetalle();
        detalle.setIdPago(pago);
        detalle.setMonto(montoPagado);
        detalle.setObservaciones(observaciones);

        pagoDetalleBean.create(detalle);

        /*// Asignar la lista de detalles al pago
        List<PagoDetalle> detalles = new ArrayList<>();
        detalles.add(detalle);
        pago.setPagoDetalleList(detalles); */

        return pago;
    }

    public BigDecimal calcularTotalOrden(Orden orden) {
        return orden.getOrdenDetalleList().stream()
                .map(detalle -> detalle.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
