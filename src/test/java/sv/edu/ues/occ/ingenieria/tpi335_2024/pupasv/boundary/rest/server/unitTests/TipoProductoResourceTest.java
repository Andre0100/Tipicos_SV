package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.TipoProductoResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoProductoResourceTest {

    @Mock
    TipoProductoBean tPBean;

    @Mock
    UriInfo uriInfo;

    @Mock
    UriBuilder uriBuilder;

    @InjectMocks
    TipoProductoResource tipoProductoResource;

    @Test
    void testProductoList() {
        // Configuración del test
        int first = 0;
        int max = 20;
        TipoProducto tipo1 = new TipoProducto(1, "Bebidas");
        TipoProducto tipo2 = new TipoProducto(2, "Comidas");
        List<TipoProducto> tipos = Arrays.asList(tipo1, tipo2);
        int totalTipos = 2;

        // Configurar mocks
        when(tPBean.findRange(first, max)).thenReturn(tipos);
        when(tPBean.count()).thenReturn(totalTipos);

        // Ejecutar
        Response response = tipoProductoResource.ProductoList(first, max);

        // Verificaciones
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(String.valueOf(totalTipos), response.getHeaderString("Total-Productos"));

        // Prueba con parámetros inválidos
        Response responseInvalidFirst = tipoProductoResource.ProductoList(-1, 20);
        assertEquals(422, responseInvalidFirst.getStatus());

        Response responseInvalidMax = tipoProductoResource.ProductoList(0, 51);
        assertEquals(422, responseInvalidMax.getStatus());

        // Prueba con excepción
        when(tPBean.findRange(5, 10)).thenThrow(new RuntimeException("Error de base de datos"));
        Response responseError = tipoProductoResource.ProductoList(5, 10);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseError.getStatus());
        assertEquals("Error de base de datos", responseError.getEntity());
    }

    @Test
    void testCreate() {
        // Configuración del test
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setNombre("Nuevo Tipo");

        // Configurar mocks para caso exitoso
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        
        // Simular asignación de ID después de create
        doAnswer(invocation -> {
            TipoProducto tp = invocation.getArgument(0);
            tp.setIdTipoProducto(1);
            return null;
        }).when(tPBean).create(tipoProducto);

        // Ejecutar y verificar caso exitoso
        Response responseSuccess = tipoProductoResource.create(tipoProducto, uriInfo);
        assertEquals(Response.Status.CREATED.getStatusCode(), responseSuccess.getStatus());

        // Prueba con tipoProducto nulo
        Response responseNull = tipoProductoResource.create(null, uriInfo);
        assertEquals(500, responseNull.getStatus());

        // Prueba con ID ya existente
        TipoProducto tipoConId = new TipoProducto(1, "Existente");
        Response responseWithId = tipoProductoResource.create(tipoConId, uriInfo);
        assertEquals(500, responseWithId.getStatus());

        // Prueba con error en creación
        TipoProducto tipoError = new TipoProducto();
        tipoError.setNombre("Error");
        doThrow(new RuntimeException("Error al guardar")).when(tPBean).create(tipoError);
        
        Response responseError = tipoProductoResource.create(tipoError, uriInfo);
        assertEquals(500, responseError.getStatus());
        assertEquals("Error al guardar", responseError.getEntity());
    }
}