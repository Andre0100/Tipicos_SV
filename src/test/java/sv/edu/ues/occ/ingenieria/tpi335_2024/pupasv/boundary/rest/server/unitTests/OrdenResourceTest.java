package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.OrdenResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.RestResourceHeaderPattern;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenResourceTest {

    @Mock
    OrdenBean ordenBean;

    @Mock
    CarritoBean carritoBean;

    @InjectMocks
    OrdenResource ordenResource;

    @Test
    void findRange(){
        int first = 0;
        int pageSize = 10;
        List<Orden> ordenes = Arrays.asList(new Orden(), new Orden());

        when(ordenBean.findRange(first, pageSize)).thenReturn(ordenes);
        when(ordenBean.count()).thenReturn(2);

        // Caso éxito
        Response response = ordenResource.findRange(first, pageSize);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        // Caso error inesperado
        when(ordenBean.findRange(first, pageSize)).thenThrow(new RuntimeException("Error interno"));
        Response responseError = ordenResource.findRange(first, pageSize);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseError.getStatus());
    }

    @Test
    void findById(){
        Long idExistente = 1L;
        Long idNoExistente = 2L;
        Long idError = 3L;

        // Caso exitoso - Orden encontrada
        Orden ordenMock = new Orden();
        ordenMock.setIdOrden(idExistente);
        when(ordenBean.findById(idExistente)).thenReturn(ordenMock);

        Response respuestaExistente = ordenResource.findById(idExistente);
        assertEquals(Response.Status.OK.getStatusCode(), respuestaExistente.getStatus());
        assertEquals(ordenMock, respuestaExistente.getEntity());

        // Caso de error controlado - Orden no encontrada
        when(ordenBean.findById(idNoExistente)).thenReturn(null);

        Response respuestaNoExistente = ordenResource.findById(idNoExistente);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), respuestaNoExistente.getStatus());
        assertNotNull(respuestaNoExistente.getHeaders().getFirst(RestResourceHeaderPattern.DETALLE_ERROR));

        // Caso de excepción - Error interno
        when(ordenBean.findById(idError)).thenThrow(new RuntimeException("Error simulado"));

        Response respuestaError = ordenResource.findById(idError);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), respuestaError.getStatus());
        assertNotNull(respuestaError.getHeaders().getFirst(RestResourceHeaderPattern.DETALLE_ERROR));
    }

    @Test
    void create(){
        Orden nuevaOrden = new Orden();
        nuevaOrden.setIdOrden(1L);

        // Caso orden nula
        Response responseNula = ordenResource.create(null);
        assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_FALTANTE, responseNula.getStatus());

        // Caso éxito
        Response responseExitosa = ordenResource.create(nuevaOrden);
        assertEquals(Response.Status.CREATED.getStatusCode(), responseExitosa.getStatus());
        assertEquals(nuevaOrden, responseExitosa.getEntity());
        verify(ordenBean).create(nuevaOrden);

        // Caso error en creación
        doThrow(new RuntimeException("Error en creación")).when(ordenBean).create(any());
        Response responseError = ordenResource.create(new Orden());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseError.getStatus());
    }

    @Test
    void update(){
        Long idExistente = 1L;
        Long idNoExistente = 2L;
        Orden ordenExistente = new Orden();
        ordenExistente.setIdOrden(idExistente);

        Orden nuevaOrden = new Orden();

        when(ordenBean.findById(idExistente)).thenReturn(ordenExistente);
        when(ordenBean.findById(idNoExistente)).thenReturn(null);

        // Caso orden encontrada y actualizada
        Response responseActualizada = ordenResource.update(idExistente, nuevaOrden);
        assertEquals(Response.Status.OK.getStatusCode(), responseActualizada.getStatus());
        assertEquals(idExistente, ((Orden) responseActualizada.getEntity()).getIdOrden());
        verify(ordenBean).update(any());

        // Caso orden no encontrada
        Response responseNoEncontrada = ordenResource.update(idNoExistente, nuevaOrden);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseNoEncontrada.getStatus());

        // Caso error inesperado
        when(ordenBean.findById(null)).thenThrow(new RuntimeException("Error interno"));
        Response responseError = ordenResource.update(null, nuevaOrden);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseError.getStatus());
    }

    @Test
    void delete(){
        Long idExistente = 1L;
        Long idNoExistente = 2L;
        Orden ordenExistente = new Orden();
        ordenExistente.setIdOrden(idExistente);

        when(ordenBean.findById(idExistente)).thenReturn(ordenExistente);
        when(ordenBean.findById(idNoExistente)).thenReturn(null);

        // Caso orden encontrada y eliminada
        Response responseEliminada = ordenResource.delete(idExistente);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseEliminada.getStatus());
        verify(ordenBean).delete(ordenExistente);

        // Caso orden no encontrada
        Response responseNoEncontrada = ordenResource.delete(idNoExistente);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseNoEncontrada.getStatus());

        // Caso error inesperado
        when(ordenBean.findById(null)).thenThrow(new RuntimeException("Error interno"));
        Response responseError = ordenResource.delete(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseError.getStatus());
    }
}
