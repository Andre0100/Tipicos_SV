package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.PagoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.PagoDetalleBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.PagoDetalle;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoBeanTest{

    @Mock
    OrdenBean ordenBean;

    @Mock
    PagoDetalleBean pagoDetalleBean;

    @Mock
    EntityManager em;

    @InjectMocks
    PagoBean cut;

    @Test
    void registrarPago() {
        Long idOrden = 1L;
        BigDecimal montoCorrecto = BigDecimal.valueOf(50.00);
        Orden ordenMock = new Orden();
        OrdenDetalle detalle = new OrdenDetalle();
        detalle.setCantidad(2);
        detalle.setPrecio(BigDecimal.valueOf(25.00));
        ordenMock.setOrdenDetalleList(Collections.singletonList(detalle));

        when(ordenBean.findById(idOrden)).thenReturn(ordenMock);
        doNothing().when(em).persist(any(Pago.class)); 
        
        Pago pago = cut.registrarPago(idOrden, "EFECTIVO", "REF123", montoCorrecto, "Observaciones");
        assertNotNull(pago);
        assertEquals("EFECTIVO", pago.getMetodoPago());
        verify(pagoDetalleBean).create(any(PagoDetalle.class));

        // Orden no encontrada
        when(ordenBean.findById(99L)).thenReturn(null);
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            cut.registrarPago(99L, "EFECTIVO", "REF123", BigDecimal.valueOf(50.00), "Observaciones");
        });
        assertEquals("No se encontró la orden.", ex1.getMessage());

        // Monto incorrecto
        when(ordenBean.findById(idOrden)).thenReturn(ordenMock); // Reiniciar mock
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            cut.registrarPago(idOrden, "EFECTIVO", "REF123", BigDecimal.valueOf(40.00), "Observaciones");
        });
        assertEquals("El monto pagado no coincide con el total de la orden.", ex2.getMessage());

        // Datos nulos
        assertThrows(IllegalArgumentException.class, () -> {
            cut.registrarPago(null, "EFECTIVO", "REF123", montoCorrecto, "Observaciones");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.registrarPago(idOrden, null, "REF123", montoCorrecto, "Observaciones");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.registrarPago(idOrden, "EFECTIVO", "REF123", null, "Observaciones");
        });
    }
}
