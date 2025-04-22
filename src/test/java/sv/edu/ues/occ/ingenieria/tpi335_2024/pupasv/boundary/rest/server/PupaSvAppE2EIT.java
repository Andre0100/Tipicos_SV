/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import testing.ContainerExtension;


/**
 *
 * @author morales
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class PupaSvAppE2EIT extends BaseIntegrationAbstract{

    
    
    @BeforeAll
    public static void setup(){
        ContainerExtension.configurarParaE2E();
    }
    //Prueba de sistema 
    
    //Obtenemos primero los productos y combos
    
    @Test
    @Order(1)
    public void obtenerProductos(){
        System.out.println("FLUJO DE ORDEN");
        
        System.out.println("Obteniendo datos: Productos, Combos");
        
        Response response = target.path("producto/por-tipo")
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
        
        Map<String, List<ProductoConPrecioDTO>> productosPorTipo = response.readEntity(
            new GenericType<Map<String, List<ProductoConPrecioDTO>>>() {});
        
        assertNotNull(productosPorTipo);
        
        
        //Imprimir en formato JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String jsonOutput = mapper.writeValueAsString(productosPorTipo);
            System.out.println("\n=== PRODUCTOS EN FORMATO JSON ===");
            System.out.println(jsonOutput);
        } catch (JsonProcessingException e) {
            System.err.println("Error al formatear JSON: " + e.getMessage());
        }
        
        
        Response responseCombos = target.path("combo").path("por-combo")
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        assertEquals(200, response.getStatus());
        
        Map<String, List<ComboProductosDTO>> productosPorCombo = responseCombos.readEntity(
            new GenericType<Map<String, List<ComboProductosDTO>>>() {});
        
        assertNotNull(productosPorCombo);
        
        
        //Imprimir en formato JSON
        ObjectMapper mapper2 = new ObjectMapper();
        mapper2.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String jsonOutput2 = mapper2.writeValueAsString(productosPorCombo);
            System.out.println("\n=== COMBOS EN FORMATO JSON ===");
            System.out.println(jsonOutput2);
        } catch (JsonProcessingException e) {
            System.err.println("Error al formatear JSON: " + e.getMessage());
        }
        
        //Obtener combos
         
         
         
        //Estos datos los traera el json y con el dto se parsea a un objeto 
        //Agregar items al carrito productos
        
//        CarritoItemDTO item1 = new CarritoItemDTO();
//        item1.setIdProductoPrecio(productos.get(0).get(0).getIdProductoPrecio());
//        item1.setNombreProducto();
//        item1.setCantidad();
//        item1.setPrecio();
//         
//         
//        System.out.println("Agregando productos al carrito");
//        Response respuestaItemAgregar = target.path("carrito")
//                 .request(MediaType.APPLICATION_JSON)
//                 .post(entity);
//         
//        assertNotNull(respuestaItemAgregar);
//        assertEquals(200, respuestaItemAgregar.getStatus());
         
         
    }
    
    
    
}
