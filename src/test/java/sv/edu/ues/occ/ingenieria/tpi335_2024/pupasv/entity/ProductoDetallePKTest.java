/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ProductoDetallePKTest {

    @Test
    public void testConstructorVacio() {
        ProductoDetallePK pk = new ProductoDetallePK();
        assertNotNull(pk);
        System.out.println("ProductoDetallePKTest --> testConstructorVacio ejecutado");
    }

    @Test
    public void testConstructorConParametros() {
        ProductoDetallePK pk = new ProductoDetallePK(1, 100L);
        assertEquals(1, pk.getIdTipoProducto());
        assertEquals(100L, pk.getIdProducto());
        System.out.println("ProductoDetallePKTest --> testConstructorConParametros ejecutado");
    }

    @Test
    public void testSettersYGetters() {
        ProductoDetallePK pk = new ProductoDetallePK();
        pk.setIdTipoProducto(2);
        pk.setIdProducto(200L);

        assertEquals(2, pk.getIdTipoProducto());
        assertEquals(200L, pk.getIdProducto());
        System.out.println("ProductoDetallePKTest --> testSettersYGetters ejecutado");
    }

    @Test
    public void testEquals() {
        ProductoDetallePK pk1 = new ProductoDetallePK(3, 300L);
        ProductoDetallePK pk2 = new ProductoDetallePK(3, 300L);
        ProductoDetallePK pk3 = new ProductoDetallePK(4, 400L);

        assertEquals(pk1, pk2);
        assertNotEquals(pk1, pk3);
        assertNotEquals(pk1, null);
        assertNotEquals(pk1, "Otro objeto");
        System.out.println("ProductoDetallePKTest --> testEquals ejecutado");
    }

    @Test
    public void testHashCode() {
        ProductoDetallePK pk1 = new ProductoDetallePK(5, 500L);
        ProductoDetallePK pk2 = new ProductoDetallePK(5, 500L);
        ProductoDetallePK pk3 = new ProductoDetallePK(6, 600L);

        assertEquals(pk1.hashCode(), pk2.hashCode());
        assertNotEquals(pk1.hashCode(), pk3.hashCode());
        System.out.println("ProductoDetallePKTest --> testHashCode ejecutado");
    }

    @Test
    public void testToString() {
        ProductoDetallePK pk = new ProductoDetallePK(7, 700L);
        String result = pk.toString();
        assertTrue(result.contains("idTipoProducto=7"));
        assertTrue(result.contains("idProducto=700"));
        System.out.println("ProductoDetallePKTest --> testToString ejecutado");
    }

}
