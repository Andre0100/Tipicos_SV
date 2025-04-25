/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class OrdenDetallePKTest {

    @Test
    public void testConstructoresYGetters() {
        OrdenDetallePK pk = new OrdenDetallePK(10L, 20L);

        Assertions.assertEquals(10L, pk.getIdOrden(), "idOrden debe ser 10");
        Assertions.assertEquals(20L, pk.getIdProductoPrecio(), "idProductoPrecio debe ser 20");
        System.out.println("OrdenDetallePK --> testConstructoresYGetters...");

    }

    @Test
    public void testSetters() {
        OrdenDetallePK pk = new OrdenDetallePK();
        pk.setIdOrden(5L);
        pk.setIdProductoPrecio(15L);

        Assertions.assertEquals(5L, pk.getIdOrden(), "idOrden debe ser 5");
        Assertions.assertEquals(15L, pk.getIdProductoPrecio(), "idProductoPrecio debe ser 15");
        System.out.println("OrdenDetallePK --> testSetters...");

    }

    @Test
    public void testEquals() {
        OrdenDetallePK pk1 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk2 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk3 = new OrdenDetallePK(3L, 4L);

        Assertions.assertEquals(pk1, pk2, "pk1 debe ser igual a pk2");
        Assertions.assertNotEquals(pk1, pk3, "pk1 no debe ser igual a pk3");
        Assertions.assertNotEquals(pk1, null, "pk1 no debe ser igual a null");
        Assertions.assertNotEquals(pk1, "otro tipo", "pk1 no debe ser igual a otro tipo de objeto");
        System.out.println("OrdenDetallePK --> testEquals...");

    }

    @Test
    public void testHashCode() {
        OrdenDetallePK pk1 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk2 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk3 = new OrdenDetallePK(3L, 4L);

        Assertions.assertEquals(pk1.hashCode(), pk2.hashCode(), "hashCode de pk1 y pk2 deben coincidir");
        Assertions.assertNotEquals(pk1.hashCode(), pk3.hashCode(), "hashCode de pk1 y pk3 no deben coincidir");
        System.out.println("OrdenDetallePK --> testHashCode...");

    }

    @Test
    public void testToString() {

        OrdenDetallePK pk = new OrdenDetallePK(7L, 8L);
        String expected = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK[ idOrden=7, idProductoPrecio=8 ]";
        Assertions.assertEquals(expected, pk.toString(), "toString debe coincidir con el valor esperado");
        System.out.println("OrdenDetallePK --> testToString...");
    }

    @Test
    public void testEqualsConIdsDiferentes() {
        OrdenDetallePK pk1 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk2 = new OrdenDetallePK(1L, 3L); // mismo idOrden, distinto idProductoPrecio

        Assertions.assertNotEquals(pk1, pk2, "pk1 no debe ser igual a pk2 si cambia idProductoPrecio");
        System.out.println("OrdenDetallePK --> testEqualsConIdsDiferentes...");
    }

}
