package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.PagoResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.PagoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.PagoRequestDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagoResourceTest {

    @Mock
    PagoBean pagoBean;
    @Mock
    CarritoBean carritoBean;

    @InjectMocks
    PagoResource pagoResource;

    @Test
    void realizarPago() {
        System.out.println("PagoResourceTest -- > realizarPago");
        PagoRequestDTO requestExitoso = new PagoRequestDTO();
        requestExitoso.setIdOrden(1L);
        requestExitoso.setMetodoPago("EFECTIVO");
        requestExitoso.setReferencia("REF123");
        requestExitoso.setMonto(BigDecimal.valueOf(50.00));
        requestExitoso.setObservaciones("Pago exitoso");

        Pago pagoSimulado = new Pago();
        pagoSimulado.setMetodoPago("EFECTIVO");

        when(pagoBean.registrarPago(
                eq(1L), anyString(), anyString(), any(BigDecimal.class), anyString()
        )).thenReturn(pagoSimulado);

        Response responseExitoso = pagoResource.realizarPago(requestExitoso);

        assertEquals(Response.Status.CREATED.getStatusCode(), responseExitoso.getStatus());
        assertTrue(responseExitoso.getEntity() instanceof Pago);
        assertEquals("EFECTIVO", ((Pago) responseExitoso.getEntity()).getMetodoPago());

        // Orden no encontrada
        PagoRequestDTO requestOrdenNoEncontrada = new PagoRequestDTO();
        requestOrdenNoEncontrada.setIdOrden(99L);
        requestOrdenNoEncontrada.setMetodoPago("EFECTIVO");
        requestOrdenNoEncontrada.setReferencia("REF456");
        requestOrdenNoEncontrada.setMonto(BigDecimal.valueOf(75.00));
        requestOrdenNoEncontrada.setObservaciones("Orden no encontrada");

        when(pagoBean.registrarPago(
                eq(99L), anyString(), anyString(), any(BigDecimal.class), anyString()
        )).thenThrow(new IllegalArgumentException("No se encontró la orden."));

        Response responseOrdenNoEncontrada = pagoResource.realizarPago(requestOrdenNoEncontrada);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseOrdenNoEncontrada.getStatus());
        assertEquals("No se encontró la orden.", responseOrdenNoEncontrada.getEntity());

        // Error interno
        PagoRequestDTO requestErrorInterno = new PagoRequestDTO();
        requestErrorInterno.setIdOrden(2L);
        requestErrorInterno.setMetodoPago("TARJETA");
        requestErrorInterno.setReferencia("REF789");
        requestErrorInterno.setMonto(BigDecimal.valueOf(100.00));
        requestErrorInterno.setObservaciones("Error inesperado");

        when(pagoBean.registrarPago(
                eq(2L), anyString(), anyString(), any(BigDecimal.class), anyString()
        )).thenThrow(new RuntimeException("Error en base de datos"));

        Response responseErrorInterno = pagoResource.realizarPago(requestErrorInterno);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseErrorInterno.getStatus());
        assertTrue(responseErrorInterno.getEntity().toString().contains("Error interno"));
    }

}
