/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.integrationTests;

import testing.BaseIntegrationAbstract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.OrdenDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.PagoRequestDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;
import testing.ContainerExtension;
import testing.NeedsLiberty;


/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
@NeedsLiberty
public class PupaSvAppE2EIT extends BaseIntegrationAbstract{
    
    private Map<String, List<ProductoConPrecioDTO>> productosPorTipo;
    private Map<String, List<ComboProductosDTO>> productosPorCombo;
    private List<CarritoItemDTO> productosItem = new ArrayList<>();
    private NewCookie sessionCookie;
    private Long idOrden; 
    private OrdenDTO orden;
    private CarritoDTO carritoDTO;
    
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
       Response postResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(itemsParaAgregar, MediaType.APPLICATION_JSON));

        // Guardar la cookie de sesión
        sessionCookie = postResponse.getCookies().get("JSESSIONID");
        assertEquals(200, postResponse.getStatus());
        System.out.println("CARRITO ITEM"+ itemsParaAgregar);
      
        
        //Verificar contenido del carrito
          // Segunda llamada (GET) con la cookie
        Response carritoResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .cookie(sessionCookie) // <- Aquí se envía la cookie
            .get();
        assertEquals(200, carritoResponse.getStatus());

        CarritoDTO carrito = carritoResponse.readEntity(CarritoDTO.class);
        
        System.out.println("CARRITODTO"+ carrito);
        assertNotNull(carrito);
        assertFalse(carrito.getItemsCarrito().isEmpty());
       

        // Imprimir contenido del carrito
        System.out.println("\n=== CONTENIDO ACTUAL DEL CARRITO ===");
        carrito.getItemsCarrito().forEach(item -> {
            System.out.printf("%s x%d - $%.2f%n", 
                item.getNombreProducto(), 
                item.getCantidad(), 
                item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        });
        System.out.printf("TOTAL: $%.2f%n", carrito.getTotal());
        
        
        //despues probar metodos para actualizar y eliminar items
        
    }

    @Test
    @Order(3)
    public void crearOrdenDesdeCarrito() throws JsonProcessingException {
        System.out.println("\n=== CREANDO ORDEN DESDE CARRITO ===");

        //Obtener el carrito actual
        Response carritoResponse = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .cookie(sessionCookie)
            .get();

        carritoDTO = carritoResponse.readEntity(CarritoDTO.class);
        assertFalse(carritoDTO.getItemsCarrito().isEmpty());
        
        // Imprimir contenido del carrito
        System.out.println("\n=== CONTENIDO ACTUAL DEL CARRITO ===");
        carritoDTO.getItemsCarrito().forEach(item -> {
            System.out.printf("%s x%d - $%.2f%n", 
                item.getNombreProducto(), 
                item.getCantidad(), 
                item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        });
        System.out.printf("TOTAL: $%.2f%n", carritoDTO.getTotal());
        
        //Crear orden desde el carrito
        Response ordenResponse = target.path("orden/desde-carrito")
            .queryParam("sucursal", "ZARZA")
            .request(MediaType.APPLICATION_JSON)
            .cookie(sessionCookie)
            .post(null);
        

        assertEquals(Response.Status.CREATED.getStatusCode(), ordenResponse.getStatus());

        //Verificar que la orden se creó correctamente
        //parsear fecha con
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        orden = mapper.readValue(ordenResponse.readEntity(String.class), OrdenDTO.class);
        
        assertNotNull(orden);
        assertNotNull(orden.getIdOrden());

        System.out.printf("\nOrden creada con ID: %d%n", orden.getIdOrden());
        System.out.printf("Sucursal: %s%n", orden.getSucursal());
        System.out.printf("Fecha: %s%n", orden.getFecha());
        System.out.println("Estado: " + orden.getAnulada());
        
    }
    
    @Test
    @Order(4)
    public void testprocederAlPago() throws JsonProcessingException{
        System.out.println("PROCESO DE PAGO");
    
        PagoRequestDTO pagoDTO = new PagoRequestDTO();
        pagoDTO.setIdOrden(orden.getIdOrden());
        pagoDTO.setMetodoPago("Efectivo");
        pagoDTO.setReferencia("Pago de orden");
        pagoDTO.setMonto(carritoDTO.getTotal());
        pagoDTO.setObservaciones("Monto exacto pagado en efectivo");
        
        //Probar resource de pago
        Response responsePago = target.path("pago")
                .request(MediaType.APPLICATION_JSON)
                .cookie(sessionCookie)
                .post(Entity.entity(pagoDTO, MediaType.APPLICATION_JSON));
        assertEquals(201, responsePago.getStatus());
        
 
        
        //parsear fecha
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        Pago pago = mapper.readValue(responsePago.readEntity(String.class), Pago.class);
        
        assertNotNull(pago);
       
        System.out.println("========PAGO DE ORDEN=======");
        System.out.println("ID PAGO: " +  pago.getIdPago());
        System.out.println("ID ORDEN: "+ pago.getIdOrden());
        System.out.println("FECHA: " + pago.getFecha());
        System.out.println("METODO: " + pago.getMetodoPago());
        System.out.println("REFERENCIA: "+ pago.getReferencia());
        
        //Verificar que el carrito quedo vacío
        Response carritoResponseVacio = target.path("carrito")
            .request(MediaType.APPLICATION_JSON)
            .cookie(sessionCookie)
            .get();
        
        assertEquals(204, carritoResponseVacio.getStatus());
        carritoDTO= carritoResponseVacio.readEntity(CarritoDTO.class);
        assertNull(carritoDTO);
    }
}
