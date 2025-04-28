/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.integrationTests;

import testing.BaseIntegrationAbstract;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;
import testing.ContainerExtension;
import testing.NeedsLiberty;

/**
 *
 * @author andrea
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
@NeedsLiberty
public class TipoProductoResourceIT extends BaseIntegrationAbstract{
    
    @Test
    @Order(1)
    public void testCreate() {
        System.out.println("TipoProductoResourceIT ----------> Test crear");

        // Prueba de error 500 si la entidad es nula
        TipoProducto invalidoTipoProducto = null;

        Response respuestaInvalida = target.path("tipoProducto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(invalidoTipoProducto, MediaType.APPLICATION_JSON));

        assertEquals(500, respuestaInvalida.getStatus());

        // Prueba 201 entidad creada
        TipoProducto nuevoTipoProducto = new TipoProducto();
        nuevoTipoProducto.setNombre("TipoProducto Test");
        
        // Hacer solicitud POST
        Response respuesta = target.path("tipoProducto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nuevoTipoProducto, MediaType.APPLICATION_JSON));

        assertNotNull(respuesta);
        assertEquals(201, respuesta.getStatus());

        // Verificar la ubicación del nuevo TipoProducto
        String location = respuesta.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("Nuevo TipoProducto creado en: " + location);

        String idStr = location.substring(location.lastIndexOf('/') + 1);
        Long idCreado = Long.valueOf(idStr);
        assertNotNull(idCreado);
    }

    @Test
    @Order(2)
    public void testFindRange() {
        System.out.println("TipoProductoResourceIT ----------> Test findRange");

        Response respuesta = target.path("tipoProducto")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatus());
        List<TipoProducto> registros = respuesta.readEntity(new GenericType<List<TipoProducto>>() {});
        assertNotNull(registros);
        assertEquals(1, registros.size()); // Suponiendo que solo haya 1 registro en la base de datos
    }

    @Test
    @Order(3)
    public void testCreateWithInvalidData() {
        System.out.println("TipoProductoResourceIT ----------> Test create with invalid data");

        // Crear un TipoProducto con datos inválidos (ej. sin nombre)
        TipoProducto tipoProductoInvalido = new TipoProducto();
        tipoProductoInvalido.setNombre(null); // Simulando un error

        Response respuestaInvalida = target.path("tipoProducto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tipoProductoInvalido, MediaType.APPLICATION_JSON));

        assertEquals(500, respuestaInvalida.getStatus());
    }
    
}
