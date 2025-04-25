/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ProductoTest {

    @Test
    public void testConstructorVacio() {
        System.out.println("ProductoTest --> testConstructorVacio...");
        Producto producto = new Producto();
        assertNotNull(producto);
    }

    @Test
    public void testConstructorConId() {
        System.out.println("ProductoTest --> testConstructorConId...");
        Producto producto = new Producto(10L);
        assertEquals(10L, producto.getIdProducto());
    }

    @Test
    public void testGettersSetters() {
        System.out.println("ProductoTest --> testGettersSetters...");
        Producto producto = new Producto();

        Long id = 20L;
        String nombre = "Empanada";
        Boolean activo = true;
        String observaciones = "Producto típico";

        List<ComboDetalle> comboList = new ArrayList<>();
        List<ProductoDetalle> detalleList = new ArrayList<>();
        List<ProductoPrecio> precioList = new ArrayList<>();

        producto.setIdProducto(id);
        producto.setNombre(nombre);
        producto.setActivo(activo);
        producto.setObservaciones(observaciones);
        producto.setComboDetalleList(comboList);
        producto.setProductoDetalleList(detalleList);
        producto.setProductoPrecioList(precioList);

        assertEquals(id, producto.getIdProducto());
        assertEquals(nombre, producto.getNombre());
        assertEquals(activo, producto.getActivo());
        assertEquals(observaciones, producto.getObservaciones());
        assertEquals(comboList, producto.getComboDetalleList());
        assertEquals(detalleList, producto.getProductoDetalleList());
        assertEquals(precioList, producto.getProductoPrecioList());
    }

    @Test
    public void testEqualsYHashCodeIguales() {
        System.out.println("ProductoTest --> testEqualsYHashCodeIguales...");
        Producto p1 = new Producto(1L);
        Producto p2 = new Producto(1L);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testEqualsYHashCodeDiferentes() {
        System.out.println("ProductoTest --> testEqualsYHashCodeDiferentes...");
        Producto p1 = new Producto(1L);
        Producto p2 = new Producto(2L);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testEqualsConNull() {
        System.out.println("ProductoTest --> testEqualsConNull...");
        Producto producto = new Producto(1L);
        assertNotEquals(producto, null);
    }

    @Test
    public void testEqualsConOtraClase() {
        System.out.println("ProductoTest --> testEqualsConOtraClase...");
        Producto producto = new Producto(1L);
        String otro = "no es Producto";
        assertNotEquals(producto, otro);
    }

    @Test
    public void testEqualsConAmbosIdNull() {
        System.out.println("ProductoTest --> testEqualsConAmbosIdNull...");
        Producto p1 = new Producto();
        Producto p2 = new Producto();
        assertEquals(p1, p2);
    }

    @Test
    public void testEqualsConUnoIdNull() {
        System.out.println("ProductoTest --> testEqualsConUnoIdNull...");
        Producto p1 = new Producto();
        Producto p2 = new Producto(5L);
        assertNotEquals(p1, p2);
    }

    @Test
    public void testToString() {
        System.out.println("ProductoTest --> testToString...");
        Producto producto = new Producto(15L);
        String result = producto.toString();
        assertTrue(result.contains("idProducto=15"));

        // Assertions.fail("Esta prueba no pasa");

    }

}
