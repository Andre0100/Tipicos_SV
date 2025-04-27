package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ProductoDetalleResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoDetalleBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoDetalle;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoDetalleResourceTest {

    @Mock
    ProductoDetalleBean pDBean;

    @InjectMocks
    ProductoDetalleResource productoDetalleResource;

    @Test
    void testProductoList() {
        // Caso éxito
        int first = 0;
        int max = 20;
        ProductoDetalle producto1 = new ProductoDetalle();
        ProductoDetalle producto2 = new ProductoDetalle();
        List<ProductoDetalle> productos = Arrays.asList(producto1, producto2);
        int totalProductos = 2;

        when(pDBean.findRange(first, max)).thenReturn(productos);
        when(pDBean.count()).thenReturn(totalProductos);

        Response response = productoDetalleResource.ProductoList(first, max);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(String.valueOf(totalProductos), response.getHeaderString("Total-Productos"));

        // Caso parámetros inválidos
        int invalidFirst = -1;
        int invalidMax = 60;

        Response responseInvalid = productoDetalleResource.ProductoList(invalidFirst, invalidMax);
        assertEquals(422, responseInvalid.getStatus()); // Usamos directamente el valor numérico 422

        // Caso error interno
        int errorFirst = 5;
        int errorMax = 10;

        when(pDBean.findRange(errorFirst, errorMax)).thenThrow(new RuntimeException("Error interno"));

        Response responseError = productoDetalleResource.ProductoList(errorFirst, errorMax);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseError.getStatus());
        assertEquals("Error interno", responseError.getEntity());
    }
}
