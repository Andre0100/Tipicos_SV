package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ProductoResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoResourceTest {

    @Mock
    private ProductoBean pBean;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private UriBuilder uriBuilder;

    @InjectMocks
    private ProductoResource productoResource;

    @BeforeEach
    void setUp() {
        // Configuración leniente para URI
        lenient().when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        lenient().when(uriBuilder.path(anyString())).thenReturn(uriBuilder);

        // Configuración leniente básica para el bean
        lenient().when(pBean.count()).thenReturn(1);
        lenient().when(pBean.findRange(anyInt(), anyInt())).thenReturn(Arrays.asList(new Producto()));
        lenient().when(pBean.findByActivo()).thenReturn(Arrays.asList(new Producto()));
    }

    @Test
    void testGetProductosAgrupadosPorTipo_Success() {
        Map<String, List<ProductoConPrecioDTO>> mockMap = new HashMap<>();
        mockMap.put("Bebidas", Arrays.asList(new ProductoConPrecioDTO()));
        when(pBean.getProductosAgrupadosPorTipo()).thenReturn(mockMap);

        Response response = productoResource.getProductosAgrupadosPorTipo();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetProductosAgrupadosPorTipo_Exception() {
        // Configurar mock para lanzar excepción
        when(pBean.getProductosAgrupadosPorTipo())
                .thenThrow(new RuntimeException("Error de base de datos simulado"));

        // Ejecutar método
        Response response = productoResource.getProductosAgrupadosPorTipo();

        // Verificar respuesta de error
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Error al obtener productos"));

        // Verificar que se registró el error en el log
        // (Requiere configurar un mock para Logger)
    } 
    

    @Test
    void testProductoList_Success() {
        when(pBean.findRange(0, 30)).thenReturn(Arrays.asList(new Producto()));
        when(pBean.count()).thenReturn(1);

        Response response = productoResource.ProductoList(0, 30);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testFindById_Success() {
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        when(pBean.findById(1L)).thenReturn(producto);

        Response response = productoResource.findById(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testFindById_NotFound() {
        when(pBean.findById(1L)).thenReturn(null);

        Response response = productoResource.findById(1L);
        assertEquals(404, response.getStatus());
    }

    @Test
    void testCreate_Success() {
        Producto producto = new Producto();
        producto.setNombre("Nuevo Producto");

        doAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setIdProducto(1L);
            return null;
        }).when(pBean).create(producto);

        Response response = productoResource.create(producto, uriInfo);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void testDelete_InvalidId() {
        Response response = productoResource.delete(null);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("ID de producto no válido", response.getEntity());
    }

    @Test
    void testDelete_Success() {
        Producto producto = new Producto();
        producto.setIdProducto(1L);

        when(pBean.findById(1L)).thenReturn(producto);
        doNothing().when(pBean).delete(producto);

        Response response = productoResource.delete(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testDelete_NotFound() {
        when(pBean.findById(1L)).thenReturn(null);

        Response response = productoResource.delete(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Producto no encontrado", response.getEntity());
    }

    @Test
    void testDelete_NullId() {
        Response response = productoResource.delete(null);

        // Cambiar la expectativa a 400 (BAD_REQUEST)
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("ID de producto no válido", response.getEntity());
    }

    // Versión modificada del test
    @Test
    void testDelete_Exception() {
        Producto producto = new Producto();
        producto.setIdProducto(1L);

        when(pBean.findById(1L)).thenReturn(producto);
        doThrow(new RuntimeException("Error de base de datos")).when(pBean).delete(producto);

        Response response = productoResource.delete(1L);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Error de base de datos"));
    }

}
