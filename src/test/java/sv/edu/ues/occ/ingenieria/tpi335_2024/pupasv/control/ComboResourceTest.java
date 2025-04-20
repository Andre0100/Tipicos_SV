/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ComboResource;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

/**
 *
 * @author andrea
 */
@ExtendWith(MockitoExtension.class) 
public class ComboResourceTest { 
    
    @Mock
    private ComboBean comboBean;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private ComboResource comboResource;

    private final Combo combo = new Combo(1L);

    @Test
    void list() { // DeberiaRetornarListaPaginada
        // Configurar
        List<Combo> combos = Arrays.asList(combo, new Combo(2L));
        when(comboBean.findRange(0, 30, true)).thenReturn(combos);
        when(comboBean.count(true)).thenReturn(100L);

        // Ejecutar
        Response response = comboResource.list(0, 30, true);

        // Verificar
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(combos, response.getEntity());
        assertEquals("100", response.getHeaderString("Total-Combos"));
        verify(comboBean).findRange(0, 30, true);
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 10, true",   // first inválido
        "0, 0, false",    // max inválido
        "5, 51, true"     // max excede límite
    })
    void list_DeberiaFallarConParametrosInvalidos(int first, int max, boolean activos) {
        Response response = comboResource.list(first, max, activos);
        
        assertEquals(422, response.getStatus());
        assertEquals("first >= 0, 1 <= max <= 50", response.getHeaderString("error"));
    }

    @Test
    void findById_DeberiaRetornarComboExistente() {
        // Configurar
        when(comboBean.findByIdWithDetalles(1)).thenReturn(combo);

        // Ejecutar
        Response response = comboResource.findById(1L);

        // Verificar
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(combo, response.getEntity());
        verify(comboBean).findByIdWithDetalles(1);
    }

    @Test
    void findById_DeberiaRetornar404SiNoExiste() {
        when(comboBean.findByIdWithDetalles(anyInt())).thenReturn(null);
        
        Response response = comboResource.findById(999L);
        
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Combo no encontrado", response.getHeaderString("error"));
    }

    @Test
    void create_DeberiaCrearNuevoCombo() throws Exception {
        // Configurar
        Combo nuevo = new Combo();
        nuevo.setNombre("Nuevo Combo");
        
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(
            UriBuilder.fromUri(URI.create("http://localhost/combo"))
        );
        
        // Simular generación de ID
        doAnswer(invocation -> {
            nuevo.setIdCombo(100L);
            return null;
        }).when(comboBean).create(nuevo);

        // Ejecutar
        Response response = comboResource.create(nuevo, uriInfo);

        // Verificar
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("http://localhost/combo/100", response.getLocation().toString());
        verify(comboBean).create(nuevo);
    }

    @Test
    void create_DeberiaFallarSiTieneId() {
        Combo conId = new Combo(1L);
        
        Response response = comboResource.create(conId, uriInfo);
        
        assertEquals(422, response.getStatus());
        assertEquals("ID debe ser null al crear", response.getHeaderString("error"));
    }

    @Test
    void update_DeberiaActualizarExistente() {
        Combo actualizado = new Combo(1L);
        actualizado.setNombre("Combo Actualizado");
        
        Response response = comboResource.update(actualizado);
        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(comboBean).update(actualizado);
    }

    @Test
    void update_DeberiaFallarSinId() {
        Combo sinId = new Combo();
        
        Response response = comboResource.update(sinId);
        
        assertEquals(422, response.getStatus());
        assertEquals("ID requerido para actualizar", response.getHeaderString("error"));
    }

    @Test
    void delete_DeberiaEliminarExistente() {
        when(comboBean.findByIdWithDetalles(1)).thenReturn(combo);
        
        Response response = comboResource.delete(1L);
        
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(comboBean).delete(combo);
    }

    @Test
    void delete_DeberiaManejarConflictos() {
        when(comboBean.findByIdWithDetalles(1)).thenReturn(combo);
        doThrow(new IllegalStateException("No se puede eliminar"))
            .when(comboBean).delete(combo);
        
        Response response = comboResource.delete(1L);
        
        assertEquals(409, response.getStatus());
        assertEquals("No se puede eliminar", response.getEntity());
    }
} 

