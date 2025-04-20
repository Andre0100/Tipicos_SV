/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoResourceIT extends BaseIntegrationAbstract {

    @Test
    @Order(1)
    public void testCreate() {
        System.out.println("Test crear");

        //Prueba de error 500 si la entidad es nula
        Producto invalidoProducto = null;

        Response respuestaInvalida = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(invalidoProducto, MediaType.APPLICATION_JSON));

        assertEquals(500, respuestaInvalida.getStatus());

        //Prueba 201 entidad creada
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Producto test");
        nuevoProducto.setActivo(true);
        nuevoProducto.setObservaciones("Producto para prueba IT");

        System.out.println("ID PRODUCTO" + nuevoProducto.getIdProducto());

        //Hacer solicitud post
        Response respuesta = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nuevoProducto, MediaType.APPLICATION_JSON));

        System.out.println("error respuesta" + respuesta);

        if (respuesta.getStatus() == 500) {
            String errorDetails = respuesta.readEntity(String.class);
            System.out.println("ERROR DETAILS: " + errorDetails);

            MultivaluedMap<String, String> ss = respuesta.getStringHeaders();

            System.out.println("   AAA" + ss);
            // Verificar logs adicionales del servidor
            //System.out.println("=== Server Error Logs ===");
            //System.out.println(.getLogs());
        }

        assertNotNull(respuesta);
        assertEquals(201, respuesta.getStatus());

        //Verificar la ubicación del nuevo producto
        String location = respuesta.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("Nuevo Producto creado en: " + location);

        String idStr = location.substring(location.lastIndexOf('/') + 1);
        Long idCreado = Long.valueOf(idStr);
        assertNotNull(idCreado);

    }

    @Test
    @Order(2)
    public void testUpdate() {

        System.out.println("Test Update");

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Producto para Actualizar");
        nuevoProducto.setActivo(true);
        nuevoProducto.setObservaciones("Producto para prueba update");

        Response createResponse = target.path("producto")
                .request()
                .post(Entity.entity(nuevoProducto, MediaType.APPLICATION_JSON));

        assertEquals(201, createResponse.getStatus());
        String location = createResponse.getHeaderString("Location");
        assertNotNull(location);

        Long idProducto = Long.valueOf(location.substring(location.lastIndexOf("/") + 1));

        //Modificar el producto
        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(idProducto);
        productoActualizado.setNombre("Producto actualizado");
        productoActualizado.setObservaciones("Producto actualiza con el test");

        Response updateResponse = target.path("producto")
                .request()
                .put(Entity.entity(productoActualizado, MediaType.APPLICATION_JSON));

        assertNotNull(updateResponse);
        assertEquals(200, updateResponse.getStatus());

        //Verificar los cambios, obteniendo por id
        Response getResponse = target.path("producto").path(idProducto.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        Producto productoVerificado = getResponse.readEntity(Producto.class);
        assertEquals("Producto actualizado", productoVerificado.getNombre());
        assertEquals("Producto actualiza con el test", productoVerificado.getObservaciones());
    }

    @Test
    @Order(3)
    public void testFindRange() {
        System.out.println("findRange");
        //assertTrue(openliberty.isRunning());
        Response respuesta = target.path("producto").request(MediaType.APPLICATION_JSON).get();
        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatus());
        List<Producto> registros = respuesta.readEntity(new GenericType<List<Producto>>() {
        });
        assertNotNull(registros);
        assertEquals(2, registros.size());
    }

}
