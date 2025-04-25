/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import testing.BaseIntegrationAbstract;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.units.qual.A;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import testing.ContainerExtension;
import testing.NeedsLiberty;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
@NeedsLiberty
public class ProductoResourceIT extends BaseIntegrationAbstract {

    private static Long productoCreadoId;
    
    @Test
    @Order(1)
    public void testCreate() {
        System.out.println("ProductoResourceIT ----------> Test create");

        //producto nullo
        Response nullResponse = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(null, MediaType.APPLICATION_JSON));
        
        
        assertEquals(500, nullResponse.getStatus());
        
        //creacion de producto
        Producto producto = new Producto();
        producto.setNombre("Pupusa prueba");
        producto.setActivo(true);
        producto.setObservaciones("Pupusa para prueba IT");
        
        Response response = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(producto, MediaType.APPLICATION_JSON));
        
        if(response.getStatus() != 201){
            String error = response.readEntity(String.class);
            System.out.println("CREAR ERROR: " + error);
        }
        
        assertEquals(201, response.getStatus());
        
        //Verifica header
        String location = response.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("Created product location: " + location);

        // Extraer id
        productoCreadoId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        assertNotNull(productoCreadoId);
    }
    
    @Test
    @Order(2)
    public void testfindById(){
         System.out.println("ProductoResourceIT ----------> Test findById");

         //Verificar el id del producto
        if (productoCreadoId == null) {
            testCreate();
        }

        // Id valido
        Response response = target.path("producto").path(productoCreadoId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        
        Producto ProductoEncontrado = response.readEntity(Producto.class);
        assertNotNull(ProductoEncontrado);
        assertEquals(productoCreadoId, ProductoEncontrado.getIdProducto());
        assertEquals("Pupusa prueba", ProductoEncontrado.getNombre());

        // Id que no existe
        Response notFoundResponse = target.path("producto").path("999999")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(404, notFoundResponse.getStatus());

        // formato de id no valido
        Response invalidResponse = target.path("producto").path("invalid")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(404, invalidResponse.getStatus());
       
    }

    @Test
    @Order(3)
    public void testUpdate() {
        System.out.println("ProductoResourceIT ----------> Test update");

        
        // obtener producto creado
        Producto productoExistente = target.path("producto").path(productoCreadoId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Producto.class);

        // Modificar producto
        productoExistente.setNombre("Pupusa vacaciones");
        productoExistente.setObservaciones("Pupusa editado con prueba IT");

        // actualizar
        Response updateResponse = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(productoExistente, MediaType.APPLICATION_JSON));
        assertEquals(200, updateResponse.getStatus());

        // Verificar la actualización
        Producto productoActualizado = target.path("producto").path(productoCreadoId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get(Producto.class);
        assertEquals("Pupusa vacaciones", productoActualizado.getNombre());
        assertEquals("Pupusa editado con prueba IT", productoActualizado.getObservaciones());

        // producto nullo
        Producto nullIdProducto = new Producto();
        nullIdProducto.setIdProducto(null);
        Response nullIdResponse = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(nullIdProducto, MediaType.APPLICATION_JSON));
        assertEquals(500, nullIdResponse.getStatus());

    }


    @Test
    @Order(4)
    public void testFindRange() {
        System.out.println("ProductoResourceIT ----------> Test findRange");

        // paramatros por defecto
        Response defaultResponse = target.path("producto")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, defaultResponse.getStatus());
        
        List<Producto> productos = defaultResponse.readEntity(new GenericType<List<Producto>>() {});
        assertNotNull(productos);
        assertFalse(productos.isEmpty());


        // con parametros
        Response customResponse = target.path("producto")
                .queryParam("first", 0)
                .queryParam("max", 2)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, customResponse.getStatus());
        
        List<Producto> limiteProductos = customResponse.readEntity(new GenericType<List<Producto>>() {});
        assertNotNull(limiteProductos);
        assertTrue(limiteProductos.size() <= 2);

        // ivnalidos parametro
        Response invalidFirst = target.path("producto")
                .queryParam("first", -1)
                .queryParam("max", 10)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(500, invalidFirst.getStatus());

        Response invalidMax = target.path("producto")
                .queryParam("first", 0)
                .queryParam("max", 0)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(500, invalidMax.getStatus());
    }

    
    @Test
    @Order(5)
    public void testProductosActivos() {
        System.out.println("ProductoResourceIT ----------> Test productosActivos");

        // parametros por defecto
        Response response = target.path("producto").path("activos")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        
        List<Producto> activeProductos = response.readEntity(new GenericType<List<Producto>>() {});
        assertNotNull(activeProductos);
        
        // verificar que este activos
        for (Producto p : activeProductos) {
            assertTrue(p.getActivo());
        }

        // parametro de paginacion
        Response paginatedResponse = target.path("producto").path("activos")
                .queryParam("first", 0)
                .queryParam("max", 2)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, paginatedResponse.getStatus());
        
        List<Producto> paginatedActive = paginatedResponse.readEntity(new GenericType<List<Producto>>() {});
        assertNotNull(paginatedActive);
    }
    
    
    
    @Test
    @Order(6)
    public void testGetProductosAgrupadosPorTipo() {
        System.out.println("ProductoResourceIT ----------> Test getProductosAgrupadosPorTipo");

        Response response = target.path("producto/por-tipo")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        
        Map<String, List<ProductoConPrecioDTO>> productosPorTipo = response.readEntity(
            new GenericType<Map<String, List<ProductoConPrecioDTO>>>() {});
        
        assertNotNull(productosPorTipo);
        
    }

    @Test
    @Order(7)
    public void testDelete() {
        System.out.println("ProductoResourceIT ----------> Test delete");


        // Eliminar Producto
        Response deleteResponse = target.path("producto").path(productoCreadoId.toString())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(200, deleteResponse.getStatus());

        // verificar si elimino
        Response verifyResponse = target.path("producto").path(productoCreadoId.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(404, verifyResponse.getStatus());

        // eliminar producto que no existe
        Response noExisteResponse = target.path("producto").path("999999")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertTrue(noExisteResponse.getStatus() >= 400);

        // id invalido
        Response invalidoResponse = target.path("producto").path("invalid")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(404, invalidoResponse.getStatus());
    }
    
}
