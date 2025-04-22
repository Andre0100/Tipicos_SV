/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

/**
 *
 * @author andrea
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComboResourceIT extends BaseIntegrationAbstract {

    private Combo comboTest;

    @BeforeEach
    public void setUp() {
        comboTest = new Combo();
        comboTest.setNombre("Combo Familiar");
        comboTest.setActivo(true);
        comboTest.setDescripcionPublica("Incluye 3 hamburguesas, papas fritas y 3 bebidas");
    }

    @Test
    @Order(1)
    public void testCreateCombo() {
        Response response = target.path("combo")
                .request()
                .post(Entity.json(comboTest));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        // Extraer el ID del Location header para usar en pruebas posteriores
        String location = response.getHeaderString("Location");
        assertNotNull(location);
        String[] parts = location.split("/");
        comboTest.setIdCombo(Long.parseLong(parts[parts.length - 1]));
    }

    @Test
    @Order(2)
    public void testFindById() {
        assertNotNull(comboTest.getIdCombo(), "El combo debe tener ID después de crearse");

        Response response = target.path("combo/" + comboTest.getIdCombo())
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Combo encontrado = response.readEntity(Combo.class);
        assertNotNull(encontrado);
        assertEquals(comboTest.getNombre(), encontrado.getNombre());
    }

    @Test
    @Order(3)
    public void testListCombos() {
        Response response = target.path("combo")
                .queryParam("first", "0")
                .queryParam("max", "10")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Verificar que el header con el total esté presente
        String totalHeader = response.getHeaderString("Total-Combos");
        assertNotNull(totalHeader);
        assertTrue(Long.parseLong(totalHeader) > 0);
    }

    @Test
    @Order(4)
    public void testUpdateCombo() {
        comboTest.setNombre("Combo Test Modificado");

        Response response = target.path("combo")
                .request()
                .put(Entity.json(comboTest));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Verificar que los cambios se aplicaron
        Response getResponse = target.path("combo/" + comboTest.getIdCombo())
                .request()
                .get();

        Combo modificado = getResponse.readEntity(Combo.class);
        assertEquals("Combo Test Modificado", modificado.getNombre());
    }

    @Test
    @Order(5)
    public void testDeleteCombo() {
        Response response = target.path("combo/" + comboTest.getIdCombo())
                .request()
                .delete();

        assertTrue(
                response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()
                || response.getStatus() == Response.Status.CONFLICT.getStatusCode(),
                "El combo debería eliminarse o estar en conflicto si tiene dependencias"
        );
    }

    @Test
    @Order(6)
    public void testInvalidRequests() {
        // Crear combo con ID (no permitido)
        Combo comboInvalido = new Combo();
        comboInvalido.setIdCombo(999L);

        Response createResponse = target.path("combo")
                .request()
                .post(Entity.json(comboInvalido));
        assertEquals(422, createResponse.getStatus());

        // Actualizar sin ID
        Combo comboSinId = new Combo();
        Response updateResponse = target.path("combo")
                .request()
                .put(Entity.json(comboSinId));
        assertEquals(422, updateResponse.getStatus());

        // Buscar con ID inválido
        Response findResponse = target.path("combo/999999")
                .request()
                .get();
        assertEquals(404, findResponse.getStatus());
    }

}
