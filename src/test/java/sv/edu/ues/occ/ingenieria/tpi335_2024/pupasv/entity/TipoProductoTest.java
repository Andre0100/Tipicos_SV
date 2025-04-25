/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class TipoProductoTest {

    @Test
    public void testConstructorVacio() {
        TipoProducto tipo = new TipoProducto();
        assertNotNull(tipo);
        System.out.println("TipoProductoTest --> testConstructorVacio ");
    }

    @Test
    public void testConstructorConId() {
        TipoProducto tipo = new TipoProducto(1);
        assertEquals(1, tipo.getIdTipoProducto());
        System.out.println("TipoProductoTest --> testConstructorConId");
    }

    @Test
    public void testConstructorConIdYNombre() {
        TipoProducto tipo = new TipoProducto(2, "Bebidas");
        assertEquals(2, tipo.getIdTipoProducto());
        assertEquals("Bebidas", tipo.getNombre());
        System.out.println("TipoProductoTest --> testConstructorConIdYNombre ");
    }

    @Test
    public void testSettersYGetters() {
        TipoProducto tipo = new TipoProducto();
        tipo.setIdTipoProducto(3);
        tipo.setNombre("Postres");
        tipo.setActivo(true);
        tipo.setObservaciones("Sin azúcar");

        List<ProductoDetalle> lista = new ArrayList<>();
        tipo.setProductoDetalleList(lista);

        assertEquals(3, tipo.getIdTipoProducto());
        assertEquals("Postres", tipo.getNombre());
        assertTrue(tipo.getActivo());
        assertEquals("Sin azúcar", tipo.getObservaciones());
        assertEquals(lista, tipo.getProductoDetalleList());
        System.out.println("TipoProductoTest --> testSettersYGetters ");
    }

    @Test
    public void testEqualsYHashCode() {
        TipoProducto t1 = new TipoProducto(5);
        TipoProducto t2 = new TipoProducto(5);
        TipoProducto t3 = new TipoProducto(6);
        TipoProducto tNull = new TipoProducto();

        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1, null);
        assertNotEquals(t1, "otro tipo");
        assertNotEquals(t1, tNull);

        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1.hashCode(), t3.hashCode());
        System.out.println("TipoProductoTest --> testEqualsYHashCode ");
    }

    @Test
    public void testToString() {
        TipoProducto tipo = new TipoProducto(10);
        String str = tipo.toString();
        assertTrue(str.contains("idTipoProducto=10"));
        System.out.println("TipoProductoTest --> testToString ");
    }

}
