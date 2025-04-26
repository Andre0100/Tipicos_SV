package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ComboResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ComboBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComboResourceTest {

    @InjectMocks
    private ComboResource comboResource;

    @Mock
    private ComboBean comboBean;

    @Test
    void getProductosAgrupadosPorCombo() {
        System.out.println("ComboResourceTest --> getProductosAgrupadosPorCombo");
        Map<String, List<ComboProductosDTO>> productos = Collections.emptyMap();
        when(comboBean.getProductosAgrupadosPorCombo()).thenReturn(productos);

        Response response = comboResource.getProductosAgrupadosPorCombo();

        assertEquals(200, response.getStatus());
        assertEquals(productos, response.getEntity());

        // Excepción
        when(comboBean.getProductosAgrupadosPorCombo()).thenThrow(new RuntimeException("Fallo interno"));
        response = comboResource.getProductosAgrupadosPorCombo();
        assertEquals(500, response.getStatus());
        assertEquals("Error al obtener productos: Fallo interno", response.getEntity());
    }

    @Test
    void list() {
        System.out.println("ComboResourceTest --> list");

        Combo combo = new Combo();
        combo.setIdCombo(1L);
        combo.setNombre("Combo 1");
        when(comboBean.findRange(0, 30, true)).thenReturn(Collections.singletonList(combo));
        when(comboBean.count(true)).thenReturn(1L);

        Response response = comboResource.list(0, 30, true);
        assertEquals(200, response.getStatus());
        assertEquals(1L, Long.parseLong(response.getHeaderString("Total-Combos")));

        // Parámetros inválidos
        response = comboResource.list(-1, 30, true);
        assertEquals(422, response.getStatus());

        response = comboResource.list(0, 0, true);
        assertEquals(422, response.getStatus());

        response = comboResource.list(0, 51, true);
        assertEquals(422, response.getStatus());

        // Excepción
        when(comboBean.findRange(0, 30, true)).thenThrow(new RuntimeException("Error"));
        response = comboResource.list(0, 30, true);
        assertEquals(500, response.getStatus());
    }

    @Test
    void findById() {
        System.out.println("ComboResourceTest --> findById");

        Long id = 1L;
        Combo combo = new Combo();
        combo.setIdCombo(id);
        combo.setNombre("Combo 1");
        when(comboBean.findByIdWithDetalles(id.intValue())).thenReturn(combo);

        Response response = comboResource.findById(id);
        assertEquals(200, response.getStatus());
        assertEquals(combo, response.getEntity());

        // ID nulo
        response = comboResource.findById(null);
        assertEquals(422, response.getStatus());

        // Combo no encontrado
        when(comboBean.findByIdWithDetalles(99)).thenReturn(null);
        response = comboResource.findById(99L);
        assertEquals(404, response.getStatus());

        // Excepción
        when(comboBean.findByIdWithDetalles(1)).thenThrow(new RuntimeException("Error"));
        response = comboResource.findById(1L);
        assertEquals(500, response.getStatus());
    }

    @Test
    void create() {
        System.out.println("ComboResourceTest --> create");

        Combo combo = new Combo();
        combo.setNombre("Nuevo Combo");

        doAnswer(invocation -> {
            Combo created = invocation.getArgument(0);
            created.setIdCombo(1L);
            return null;
        }).when(comboBean).create(any(Combo.class));

        UriInfo uriInfo = mock(UriInfo.class);
        UriBuilder uriBuilder = mock(UriBuilder.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(URI.create("http://localhost:8080/combo/1"));

        Response response = comboResource.create(combo, uriInfo);
        assertEquals(201, response.getStatus());

        // ID no debe estar definido
        combo.setIdCombo(10L);
        response = comboResource.create(combo, uriInfo);
        assertEquals(422, response.getStatus());

        // Excepciones
        combo.setIdCombo(null);
        doThrow(new IllegalArgumentException("Datos inválidos")).when(comboBean).create(any());
        response = comboResource.create(combo, uriInfo);
        assertEquals(400, response.getStatus());

        doThrow(new IllegalStateException("Ya existe")).when(comboBean).create(any());
        response = comboResource.create(combo, uriInfo);
        assertEquals(409, response.getStatus());

        doThrow(new RuntimeException("Error general")).when(comboBean).create(any());
        response = comboResource.create(combo, uriInfo);
        assertEquals(500, response.getStatus());
    }

    @Test
    void update() {
        System.out.println("ComboResourceTest --> update");

        Combo combo = new Combo();
        combo.setIdCombo(1L);
        combo.setNombre("Combo Actualizado");
        when(comboBean.update(combo)).thenReturn(combo);

        Response response = comboResource.update(combo);
        assertEquals(200, response.getStatus());

        // ID nulo
        combo.setIdCombo(null);
        response = comboResource.update(combo);
        assertEquals(422, response.getStatus());

        // Excepción
        combo.setIdCombo(1L);
        doThrow(new IllegalArgumentException("No válido")).when(comboBean).update(combo);
        response = comboResource.update(combo);
        assertEquals(400, response.getStatus());

        doThrow(new RuntimeException("Error")).when(comboBean).update(combo);
        response = comboResource.update(combo);
        assertEquals(500, response.getStatus());
    }

    @Test
    void delete() {
        System.out.println("ComboResourceTest --> delete");

        Long id = 1L;
        Combo combo = new Combo();
        combo.setIdCombo(id);
        when(comboBean.findByIdWithDetalles(id.intValue())).thenReturn(combo);

        Response response = comboResource.delete(id);
        assertEquals(204, response.getStatus());

        // ID nulo
        response = comboResource.delete(null);
        assertEquals(422, response.getStatus());

        // Excepción
        when(comboBean.findByIdWithDetalles(id.intValue())).thenReturn(combo);
        doThrow(new IllegalArgumentException("No válido")).when(comboBean).delete(combo);
        response = comboResource.delete(id);
        assertEquals(400, response.getStatus());

        doThrow(new IllegalStateException("No se puede eliminar")).when(comboBean).delete(combo);
        response = comboResource.delete(id);
        assertEquals(409, response.getStatus());

        doThrow(new RuntimeException("Error grave")).when(comboBean).delete(combo);
        response = comboResource.delete(id);
        assertEquals(500, response.getStatus());
    }


}
