/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;


/**
 *
 * @author morales
 */

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class PupaSvAppIT extends BaseIntegrationAbstract{
//
//    //Prueba de sistema 
//    
//    //Obtenemos primero los productos y combos
//    
//    @Test
//    @Order(1)
//    public void OrdenFlujo(){
//        System.out.println("FLUJO DE ORDEN");
//        
//        System.out.println("Obteniendo datos: Productos, Combos");
//        
//        Response respuestaProductosPrecio = target.path("producto/por-tipo")
//                .request(MediaType.APPLICATION_JSON)
//                .get();
//        
//        assertNotNull(respuestaProductosPrecio);
//        assertEquals(200, respuestaProductosPrecio.getStatus());
//        
//        Map<String, List<ProductoConPrecioDTO>> productos = respuestaProductosPrecio.readEntity(
//                new GenericType<Map<String, List<ProductoConPrecioDTO>>>() {});
//        
//        assertNotNull(productos);
//        assertFalse(productos.isEmpty());
//        
//        
//        //Obtener combos
//         
//         
//         
//        //Estos datos los traera el json y con el dto se parsea a un objeto 
//        //Agregar items al carrito productos
//        
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
//         
//         
//    }
//    
//    
//    
//}
