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

public class ComboDetallePKTest {

    @Test
    public void testConstructorAndGetters() {
        ComboDetallePK comboDetallePK = new ComboDetallePK(10L, 20L);

        Assertions.assertEquals(10L, comboDetallePK.getIdCombo(), "idCombo debe ser 10");
        Assertions.assertEquals(20L, comboDetallePK.getIdProducto(), "idProducto debe ser 20");
    }

    @Test
    public void testSetters() {
        ComboDetallePK comboDetallePK = new ComboDetallePK();
        comboDetallePK.setIdCombo(5L);
        comboDetallePK.setIdProducto(15L);

        Assertions.assertEquals(5L, comboDetallePK.getIdCombo(), "idCombo debe ser 5");
        Assertions.assertEquals(15L, comboDetallePK.getIdProducto(), "idProducto debe ser 15");
    }

    @Test
    public void testEquals() {
        ComboDetallePK pk1 = new ComboDetallePK(1L, 2L);
        ComboDetallePK pk2 = new ComboDetallePK(1L, 2L);
        ComboDetallePK pk3 = new ComboDetallePK(2L, 3L);

        Assertions.assertEquals(pk1, pk2, "pk1 debe ser igual a pk2");
        Assertions.assertNotEquals(pk1, pk3, "pk1 no debe ser igual a pk3");
        Assertions.assertNotEquals(pk1, null, "pk1 no debe ser igual a null");
        Assertions.assertNotEquals(pk1, "otro objeto", "pk1 no debe ser igual a un objeto de otro tipo");
    }

    @Test
    public void testEqualsWithDifferentObjectType() {
        ComboDetallePK pk = new ComboDetallePK(1L, 2L);
        String other = "no es ComboDetallePK";

        Assertions.assertFalse(pk.equals(other), "Debe retornar false al comparar con un objeto de otro tipo");
    }

    @Test
    public void testEqualsDifferentIdProducto() {
        ComboDetallePK pk1 = new ComboDetallePK(1L, 2L);
        ComboDetallePK pk2 = new ComboDetallePK(1L, 3L); // diferente idProducto

        Assertions.assertFalse(pk1.equals(pk2), "Debe retornar false si los idProducto son distintos");
    }

    @Test
    public void testHashCode() {
        ComboDetallePK pk1 = new ComboDetallePK(1L, 2L);
        ComboDetallePK pk2 = new ComboDetallePK(1L, 2L);
        ComboDetallePK pk3 = new ComboDetallePK(2L, 3L);

        Assertions.assertEquals(pk1.hashCode(), pk2.hashCode(), "hashCode de pk1 y pk2 deben coincidir");
        Assertions.assertNotEquals(pk1.hashCode(), pk3.hashCode(), "hashCode de pk1 y pk3 no deben coincidir");
    }

    @Test
    public void testToString() {
        ComboDetallePK comboDetallePK = new ComboDetallePK(100L, 200L);
        String expected = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetallePK[ idCombo=100, idProducto=200 ]";

        Assertions.assertEquals(expected, comboDetallePK.toString(), "toString debe retornar el formato esperado");
    }

}
