package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.CarritoResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoResourceTest {

    @Mock
    CarritoBean carritoBean;

    @InjectMocks
    CarritoResource carritoResource;

    @Test
    void agregarItems() {
        System.out.println("CarritoResourceTest --> agregarItems");

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);

        Response responseOk = carritoResource.agregarItems(List.of(item));
        assertEquals(Response.Status.OK.getStatusCode(), responseOk.getStatus());
        verify(carritoBean).agregarItem(anyList());

        // Lista Nula
        Response responseNull = carritoResource.agregarItems(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseNull.getStatus());
        assertEquals("La lista de items no puede estar vacía o ser nula", responseNull.getEntity());

        // Lista vacía
        Response responseVacia = carritoResource.agregarItems(List.of());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseVacia.getStatus());
        assertEquals("La lista de items no puede estar vacía o ser nula", responseVacia.getEntity());

        // Item inválido
        CarritoItemDTO itemInvalido = new CarritoItemDTO();
        itemInvalido.setCantidad(0);
        Response responseInvalido = carritoResource.agregarItems(List.of(itemInvalido));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseInvalido.getStatus());
        assertEquals("Cada item debe tener un producto válido y una cantidad positiva", responseInvalido.getEntity());

        // Excepción de negocio
        CarritoItemDTO itemValido = new CarritoItemDTO();
        itemValido.setIdProductoPrecio(2L);
        itemValido.setCantidad(1);

        doThrow(new IllegalArgumentException("Stock insuficiente")).when(carritoBean).agregarItem(anyList());
        Response responseNegocio = carritoResource.agregarItems(List.of(itemValido));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseNegocio.getStatus());
        assertEquals("Stock insuficiente", responseNegocio.getEntity());

        // Excepción general
        doThrow(new RuntimeException("Error inesperado")).when(carritoBean).agregarItem(anyList());
        Response responseGeneral = carritoResource.agregarItems(List.of(itemValido));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseGeneral.getStatus());
        assertTrue(((String) responseGeneral.getEntity()).contains("Error inesperado"));
    }

    @Test
    void obtenerCarrito() {
        System.out.println("CarritoResourceTest --> obtenerCarrito");

        CarritoDTO dto = new CarritoDTO();
        List<CarritoItemDTO> items = new ArrayList<>();
        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        items.add(item);
        dto.setItemsCarrito(items);
        dto.setTotal(BigDecimal.valueOf(100.00));

        when(carritoBean.obtenerItems()).thenReturn(items);
        when(carritoBean.calcularTotal()).thenReturn(BigDecimal.valueOf(100.00));

        Response responseOk = carritoResource.obtenerCarrito();
        assertEquals(Response.Status.OK.getStatusCode(), responseOk.getStatus());
        CarritoDTO result = (CarritoDTO) responseOk.getEntity();
        assertEquals(1, result.getItemsCarrito().size());
        assertEquals(BigDecimal.valueOf(100.00), result.getTotal());

        // Carrito vacío
        when(carritoBean.obtenerItems()).thenReturn(new ArrayList<>());
        when(carritoBean.calcularTotal()).thenReturn(BigDecimal.valueOf(0.0));

        Response responseVacio = carritoResource.obtenerCarrito();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseVacio.getStatus());
        assertEquals("El carrito está vacío", responseVacio.getEntity());

        // Excepción general
        when(carritoBean.obtenerItems()).thenThrow(new RuntimeException("Ocurrió un error al obtener el carrito."));
        Response responseError = carritoResource.obtenerCarrito();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), responseError.getStatus());
        assertTrue(((String) responseError.getEntity()).contains("Ocurrió un error al obtener el carrito:"));
    }

    @Test
    void testEliminarItem() {
        System.out.println("CarritoResourceTest --> eliminarItem");
        Response response = carritoResource.eliminarItem(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(carritoBean).eliminarItem(1L);
    }
}
