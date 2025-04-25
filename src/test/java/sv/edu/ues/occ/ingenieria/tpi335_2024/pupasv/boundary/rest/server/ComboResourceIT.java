/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import testing.BaseIntegrationAbstract;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;
import testing.ContainerExtension;
import testing.NeedsLiberty;

/**
 *
 * @author andrea
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
@NeedsLiberty
public class ComboResourceIT extends BaseIntegrationAbstract {

    private static Long createdComboId;

    @Test
    @Order(1)
    public void testCreate() {
        System.out.println("ComboResourceIT ----------> Test crear");

        //Provocar error
        Combo invalidCombo = null;
        Response invalidResponse = target.path("combo")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(invalidCombo, MediaType.APPLICATION_JSON));
        assertEquals(500, invalidResponse.getStatus());

        //Provocar error
        Combo comboWithId = new Combo();
        comboWithId.setIdCombo(1L);
        Response responseWithId = target.path("combo")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(comboWithId, MediaType.APPLICATION_JSON));
        assertEquals(422, responseWithId.getStatus());

        //creacion correcta
        Combo newCombo = new Combo();
        newCombo.setNombre("Combo de prueba");
        newCombo.setActivo(true);
        newCombo.setDescripcionPublica("Descripción pública del combo de prueba");

        Response response = target.path("combo")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newCombo, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 500) {
            String errorDetails = response.readEntity(String.class);
            System.out.println("ERROR DETAllES: " + errorDetails);
        }

        assertEquals(201, response.getStatus());

        // verificar locacion
        String location = response.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("Nuevo Combo creado en: " + location);

        // Extraer id
        String idStr = location.substring(location.lastIndexOf('/') + 1);
        createdComboId = Long.valueOf(idStr);
        assertNotNull(createdComboId);
    }

    @Test
    @Order(2)
    public void testUpdate() {
        System.out.println("ComboResourceIT ----------> Test actualizar");

        // Verificar que el combo ya se creo
        if (createdComboId == null) {
            testCreate();
        }

        //Obtener el como a actualizar
        Response getResponse = target.path("combo").path(createdComboId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, getResponse.getStatus());
        Combo existingCombo = getResponse.readEntity(Combo.class);

        // actualizar combo
        existingCombo.setNombre("Combo actualizado");
        existingCombo.setDescripcionPublica("Descripción actualizada");

        Response updateResponse = target.path("combo")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(existingCombo, MediaType.APPLICATION_JSON));
        assertEquals(200, updateResponse.getStatus());

        // verificar si se realizaron los combos
        Response verifyResponse = target.path("combo").path(createdComboId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        Combo updatedCombo = verifyResponse.readEntity(Combo.class);
        assertEquals("Combo actualizado", updatedCombo.getNombre());
        assertEquals("Descripción actualizada", updatedCombo.getDescripcionPublica());
    }

    @Test
    @Order(3)
    public void testFindById() {
        System.out.println("ComboResourceIT ----------> Test buscar por ID");

        if (createdComboId == null) {
            testCreate();
        }

        // obtenercombo por id
        Response response = target.path("combo").path(createdComboId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        Combo foundCombo = response.readEntity(Combo.class);
        assertNotNull(foundCombo);
        assertEquals(createdComboId, foundCombo.getIdCombo());

        // combo no existe 404
        Response notFoundResponse = target.path("combo").path("999999")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(500, notFoundResponse.getStatus());

        // id nullo 
        Response nullIdResponse = target.path("combo").path("null")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(404, nullIdResponse.getStatus());
    }

    @Test
    @Order(4)
    public void testFindRange() {
        System.out.println("ComboResourceIT ----------> Test findRange");

        // obtener lista de combos
        Response defaultResponse = target.path("combo")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, defaultResponse.getStatus());
        List<Combo> combos = defaultResponse.readEntity(new GenericType<List<Combo>>() {});
        assertNotNull(combos);
        assertFalse(combos.isEmpty());

        // obtener lista con parametros
        Response customResponse = target.path("combo")
                .queryParam("first", 0)
                .queryParam("max", 5)
                .queryParam("activos", true)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, customResponse.getStatus());
        List<Combo> limitedCombos = customResponse.readEntity(new GenericType<List<Combo>>() {});
        assertNotNull(limitedCombos);
        assertTrue(limitedCombos.size() <= 5);

        // invalidos parametro
        Response invalidFirst = target.path("combo")
                .queryParam("first", -1)
                .queryParam("max", 10)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(422, invalidFirst.getStatus());

        Response invalidMax = target.path("combo")
                .queryParam("first", 0)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(422, invalidMax.getStatus());
    }

    @Test
    @Order(5)
    public void testGetProductosAgrupadosPorCombo() {
        System.out.println("ComboResourceIT ----------> Test getProductosAgrupadosPorCombo");

        Response response = target.path("combo").path("por-combo")
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        assertEquals(200, response.getStatus());
        
        Map<String, List<ComboProductosDTO>> productosPorCombo = response.readEntity(
            new GenericType<Map<String, List<ComboProductosDTO>>>() {});
        
        assertNotNull(productosPorCombo);
        
        //pediente por aplicar logica
    }

    @Test
    @Order(6)
    public void testDelete() {
        System.out.println("ComboResourceIT ----------> Test eliminar");

        
        if (createdComboId == null) {
            testCreate();
        }

        // eliminar
        Response deleteResponse = target.path("combo").path(createdComboId.toString())
                .request()
                .delete();
        assertEquals(204, deleteResponse.getStatus());

        // verificar si elimino
        Response verifyResponse = target.path("combo").path(createdComboId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(500, verifyResponse.getStatus());

        // id del combo no existe
        Response notFoundResponse = target.path("combo").path("999999")
                .request()
                .delete();
        assertTrue(notFoundResponse.getStatus() == 500 || notFoundResponse.getStatus() == 400);

        // id null como parametro
        Response nullIdResponse = target.path("combo").path("null")
                .request()
                .delete();
        assertEquals(404, nullIdResponse.getStatus());
    }

}
