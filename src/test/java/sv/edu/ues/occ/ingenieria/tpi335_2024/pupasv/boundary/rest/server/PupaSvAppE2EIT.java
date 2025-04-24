/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import testing.ContainerExtension;


/**
 *
 * @author morales
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class PupaSvAppE2EIT extends BaseIntegrationAbstract{
    
    private Map<String, List<ProductoConPrecioDTO>> productosPorTipo;
    private Map<String, List<ComboProductosDTO>> productosPorCombo;
    private List<CarritoItemDTO> productosItem = new ArrayList<>();
    
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
        
         productosPorTipo = response.readEntity(
            new GenericType<Map<String, List<ProductoConPrecioDTO>>>() {});
        
        assertNotNull(productosPorTipo);
        assertEquals(false, productosPorTipo.isEmpty());
        
        
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
        
        
        productosPorCombo = responseCombos.readEntity(
            new GenericType<Map<String, List<ComboProductosDTO>>>() {});
        
        assertNotNull(productosPorCombo);
        assertEquals(false, productosPorCombo.isEmpty());
        
        
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
              
    }
    
    @Test
    @Order(2)
    public void agregarItemsAlCarrito() {
        System.out.println("\n=== AGREGANDO ITEMS AL CARRITO ===");

        // Crear lista de items para agregar al carrito
        List<CarritoItemDTO> itemsParaAgregar = new ArrayList<>();

        // Agregar productos individuales
        ProductoConPrecioDTO pupusaRevuelta = productosPorTipo.get("tipicos").stream()
            .filter(p -> "Pupusa revueltas".equals(p.getNombre()))
            .findFirst()
            .orElseThrow();

        CarritoItemDTO itemPupusa = new CarritoItemDTO();
        itemPupusa.setIdProductoPrecio(pupusaRevuelta.getIdProductoPrecio());
        itemPupusa.setNombreProducto(pupusaRevuelta.getNombre());
        itemPupusa.setCantidad(3); // 3 pupusas revueltas
        itemPupusa.setPrecio(pupusaRevuelta.getPrecio());
        itemPupusa.setObservaciones("Sin curtido");
        itemsParaAgregar.add(itemPupusa);

        // Agregar un combo completo
        List<ComboProductosDTO> comboPupusero = productosPorCombo.get("Combo pupusero");
        comboPupusero.forEach(productoCombo -> {
            CarritoItemDTO itemCombo = new CarritoItemDTO();
            itemCombo.setIdProductoPrecio(productoCombo.getIdProductoPrecio());
            itemCombo.setNombreProducto(productoCombo.getNombre());
            itemCombo.setCantidad(productoCombo.getCantidad());
            itemCombo.setPrecio(productoCombo.getPrecio());
            itemsParaAgregar.add(itemCombo);
        });

        //Enviar items al carrito via API
        Response response = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(itemsParaAgregar, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        System.out.println("CARRITO ITEM"+ itemsParaAgregar);
        //Verificar contenido del carrito
        Response carritoResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .get();
        
        assertEquals(200, carritoResponse.getStatus());

        CarritoDTO carrito = carritoResponse.readEntity(CarritoDTO.class);
        
         System.out.println("CARRITODTO"+ carrito);
        assertNotNull(carrito);
        assertTrue(carrito.getItemsCarrito().isEmpty());
       

        // Imprimir contenido del carrito
        System.out.println("\n=== CONTENIDO ACTUAL DEL CARRITO ===");
        carrito.getItemsCarrito().forEach(item -> {
            System.out.printf("%s x%d - $%.2f%n", 
                item.getNombreProducto(), 
                item.getCantidad(), 
                item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        });
        System.out.printf("TOTAL: $%.2f%n", carrito.getTotal());
    }

    @Test
    @Order(3)
    public void crearOrdenDesdeCarrito() {
        System.out.println("\n=== CREANDO ORDEN DESDE CARRITO ===");

        //Obtener el carrito actual
        Response carritoResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .get();

        CarritoDTO carrito = carritoResponse.readEntity(CarritoDTO.class);
        assertTrue(carrito.getItemsCarrito().isEmpty());

        //Crear orden desde el carrito
        Response ordenResponse = target.path("orden/desde-carrito")
            .queryParam("sucursal", "CENTRAL")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(null));

        assertEquals(Response.Status.CREATED.getStatusCode(), ordenResponse.getStatus());

        //Verificar que la orden se creó correctamente
        Orden orden = ordenResponse.readEntity(Orden.class);
        assertNotNull(orden);
        assertNotNull(orden.getIdOrden());

        System.out.printf("\nOrden creada con ID: %d%n", orden.getIdOrden());
        System.out.printf("Sucursal: %s%n", orden.getSucursal());
        System.out.printf("Fecha: %s%n", orden.getFecha());
        System.out.printf("Total: $%.2f%n", orden.getOrdenDetalleList().stream()
            .map(d -> d.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));

        // 4. Verificar que el carrito quedó vacío
        carritoResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .get();

        carrito = carritoResponse.readEntity(CarritoDTO.class);
        assertTrue(carrito.getItemsCarrito().isEmpty());
    }


}
