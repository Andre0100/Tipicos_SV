/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ProductoDetalleTest {

    @Test
    public void testConstructores() {
        ProductoDetalle detalle = new ProductoDetalle();
        assertNull(detalle.getProductoDetallePK());
        assertNull(detalle.getActivo());
        assertNull(detalle.getObservaciones());

        ProductoDetallePK pk = new ProductoDetallePK(1, 100L);
        ProductoDetalle detalle2 = new ProductoDetalle(pk);
        assertEquals(pk, detalle2.getProductoDetallePK());

        ProductoDetalle detalle3 = new ProductoDetalle(2, 200L);
        assertEquals(new ProductoDetallePK(2, 200L), detalle3.getProductoDetallePK());

        System.out.println("ProductoDetalleTest --> testConstructores");
    }

    @Test
    public void testGettersAndSetters() {
        ProductoDetalle detalle = new ProductoDetalle();
        ProductoDetallePK pk = new ProductoDetallePK(1, 1L);
        Producto producto = new Producto();
        TipoProducto tipo = new TipoProducto();

        detalle.setProductoDetallePK(pk);
        detalle.setActivo(true);
        detalle.setObservaciones("Observaciones de prueba");
        detalle.setProducto(producto);
        detalle.setTipoProducto(tipo);

        assertEquals(pk, detalle.getProductoDetallePK());
        assertTrue(detalle.getActivo());
        assertEquals("Observaciones de prueba", detalle.getObservaciones());
        assertEquals(producto, detalle.getProducto());
        assertEquals(tipo, detalle.getTipoProducto());

        System.out.println(" ProductoDetalleTest --> testGettersAndSetters");
    }

    @Test
    public void testEqualsAndHashCode() {
        ProductoDetallePK pk1 = new ProductoDetallePK(1, 1L);
        ProductoDetallePK pk2 = new ProductoDetallePK(1, 1L);
        ProductoDetalle detalle1 = new ProductoDetalle(pk1);
        ProductoDetalle detalle2 = new ProductoDetalle(pk2);

        assertEquals(detalle1, detalle2);
        assertEquals(detalle1.hashCode(), detalle2.hashCode());

        ProductoDetalle detalle3 = new ProductoDetalle();
        assertNotEquals(detalle1, detalle3);

        System.out.println("ProductoDetalleTest --> testEqualsAndHashCode");
    }

    @Test
    public void testToString() {
        ProductoDetallePK pk = new ProductoDetallePK(5, 99L);
        ProductoDetalle detalle = new ProductoDetalle(pk);
        String toString = detalle.toString();
        assertTrue(toString.contains("productoDetallePK="));
        assertTrue(toString.contains("5"));
        assertTrue(toString.contains("99"));

        System.out.println("ProductoDetalleTest --> testToString");
    }

}
