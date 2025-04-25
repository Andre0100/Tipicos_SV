/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ProductoPrecioTest {

    @Test
    public void testConstructorVacio() {
        ProductoPrecio precio = new ProductoPrecio();
        assertNotNull(precio);
        System.out.println("ProductoPrecioTest --> testConstructorVacio ");
    }

    @Test
    public void testConstructorConId() {
        ProductoPrecio precio = new ProductoPrecio(1L);
        assertEquals(1L, precio.getIdProductoPrecio());
        System.out.println("ProductoPrecioTest --> testConstructorConId ");
    }

    @Test
    public void testSettersYGetters() {
        ProductoPrecio precio = new ProductoPrecio();
        Date desde = new Date();
        Date hasta = new Date();
        BigDecimal valor = new BigDecimal("5.75");

        Producto producto = new Producto();
        List<OrdenDetalle> lista = new ArrayList<>();

        precio.setIdProductoPrecio(2L);
        precio.setFechaDesde(desde);
        precio.setFechaHasta(hasta);
        precio.setPrecioSugerido(valor);
        precio.setIdProducto(producto);
        precio.setOrdenDetalleList(lista);

        assertEquals(2L, precio.getIdProductoPrecio());
        assertEquals(desde, precio.getFechaDesde());
        assertEquals(hasta, precio.getFechaHasta());
        assertEquals(valor, precio.getPrecioSugerido());
        assertEquals(producto, precio.getIdProducto());
        assertEquals(lista, precio.getOrdenDetalleList());
        System.out.println("ProductoPrecioTest --> testSettersYGetters ");
    }

    @Test
    public void testEqualsYHashCode() {
        ProductoPrecio p1 = new ProductoPrecio(10L);
        ProductoPrecio p2 = new ProductoPrecio(10L);
        ProductoPrecio p3 = new ProductoPrecio(20L);
        ProductoPrecio pNull = new ProductoPrecio();

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "otro tipo");
        assertNotEquals(p1, pNull);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        System.out.println("ProductoPrecioTest --> testEqualsYHashCode ");
    }

    @Test
    public void testToString() {
        ProductoPrecio precio = new ProductoPrecio(15L);
        String str = precio.toString();
        assertTrue(str.contains("idProductoPrecio=15"));
        System.out.println("ProductoPrecioTest --> testToString ");
    }

}
