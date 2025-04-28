/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.math.BigDecimal;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

/**
 *
 * @author andrea
 */
public class ComboProductosDTOTest {

    private Producto producto;
    private ProductoPrecio productoPrecio;

    @BeforeEach
    void setUp() {
        // Crear instancias de Producto y ProductoPrecio para pruebas
        producto = new Producto();
        producto.setNombre("Pupusa");
        producto.setObservaciones("Deliciosa pupusa de frijoles");

        productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(BigDecimal.valueOf(2.50));
        productoPrecio.setFechaHasta(new Date());
    }

    @Test
    void testConstructor() {
        // Crear ComboProductosDTO usando el constructor con parámetros
        ComboProductosDTO dto = new ComboProductosDTO(producto, productoPrecio, 10);

        // Verificar que los valores del DTO se asignen correctamente
        assertEquals(1L, dto.getIdProductoPrecio(), "El idProductoPrecio debería ser 1");
        assertEquals("Pupusa", dto.getNombre(), "El nombre del producto debería ser 'Pupusa'");
        assertEquals("Deliciosa pupusa de frijoles", dto.getObservaciones(), "Las observaciones deberían ser 'Deliciosa pupusa de frijoles'");
        assertEquals(10, dto.getCantidad(), "La cantidad debería ser 10");
        assertEquals(BigDecimal.valueOf(2.50), dto.getPrecio(), "El precio debería ser 2.50");
        assertNotNull(dto.getFechaValidezPrecio(), "La fecha de validez no debería ser nula");

        System.out.println("ComboProductosDTOTest --> testConstructor");

    }

    /*
    @Test
    void testConstructorConProductoYPrecioNulos() {

        // modificado para manejar el caso cuando productoPrecio es null, evita NullPointerException
        // los valores se asignan correctamente
        
        Producto producto = new Producto();
        producto.setNombre("Pupusa");
        producto.setObservaciones("Pupusa de frijoles");

        // ProductoPrecio es nulo
        ComboProductosDTO dto = new ComboProductosDTO(producto, null, 5);

        // Verificar los valores asignados correctamente, incluso cuando ProductoPrecio es nulo
        assertNull(dto.getIdProductoPrecio(), "El idProductoPrecio debería ser nulo");
        assertNotNull(dto.getNombre(), "El nombre debería ser asignado correctamente");
        assertNotNull(dto.getObservaciones(), "Las observaciones deberían ser asignadas correctamente");
        assertEquals(BigDecimal.ZERO, dto.getPrecio(), "El precio debería ser BigDecimal.ZERO cuando productoPrecio es nulo");
        assertNull(dto.getFechaValidezPrecio(), "La fechaValidezPrecio debería ser nula cuando productoPrecio es nulo");
    }
    */

    @Test
    void testGettersYSetters() {
        // Crear un objeto ComboProductosDTO
        ComboProductosDTO dto = new ComboProductosDTO();
        dto.setIdProductoPrecio(2L);
        dto.setNombre("Combo de Pupusas");
        dto.setObservaciones("Combo de 3 pupusas");
        dto.setPrecio(BigDecimal.valueOf(7.50));
        dto.setCantidad(3);
        dto.setFechaValidezPrecio(new Date());

        // Verificar los valores con los getters
        assertEquals(2L, dto.getIdProductoPrecio(), "El idProductoPrecio debería ser 2");
        assertEquals("Combo de Pupusas", dto.getNombre(), "El nombre debería ser 'Combo de Pupusas'");
        assertEquals("Combo de 3 pupusas", dto.getObservaciones(), "Las observaciones deberían ser 'Combo de 3 pupusas'");
        assertEquals(BigDecimal.valueOf(7.50), dto.getPrecio(), "El precio debería ser 7.50");
        assertEquals(3, dto.getCantidad(), "La cantidad debería ser 3");
        assertNotNull(dto.getFechaValidezPrecio(), "La fecha de validez debería no ser nula");
    }

    @Test
    void testCantidadNula() {
        // Crear ComboProductosDTO con cantidad nula
        ComboProductosDTO dto = new ComboProductosDTO();
        dto.setCantidad(null);

        // Verificar que la cantidad sea nula
        assertNull(dto.getCantidad(), "La cantidad debería ser nula");
    }

}
