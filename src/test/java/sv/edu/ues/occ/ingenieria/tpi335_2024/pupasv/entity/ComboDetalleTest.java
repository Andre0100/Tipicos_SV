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
public class ComboDetalleTest {
    
     @Test
    public void testConstructorAndGetters() {
        // Crear un objeto ComboDetalle utilizando el constructor
        ComboDetallePK pk = new ComboDetallePK(1L, 1L);
        ComboDetalle comboDetalle = new ComboDetalle(pk);
        
        // Asignar valores a los atributos
        comboDetalle.setCantidad(10);
        comboDetalle.setActivo(true);
        
        // Verificar que los valores asignados sean los correctos
        Assertions.assertEquals(pk, comboDetalle.getComboDetallePK());
        Assertions.assertEquals(10, comboDetalle.getCantidad());
        Assertions.assertTrue(comboDetalle.getActivo());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Crear objetos ComboDetalle con los mismos valores
        ComboDetallePK pk1 = new ComboDetallePK(1L, 1L);
        ComboDetalle comboDetalle1 = new ComboDetalle(pk1);
        comboDetalle1.setCantidad(10);
        comboDetalle1.setActivo(true);
        
        ComboDetallePK pk2 = new ComboDetallePK(1L, 1L);
        ComboDetalle comboDetalle2 = new ComboDetalle(pk2);
        comboDetalle2.setCantidad(10);
        comboDetalle2.setActivo(true);

        // Test equals
        Assertions.assertTrue(comboDetalle1.equals(comboDetalle2), "Los objetos deberían ser iguales");

        // Test hashCode
        Assertions.assertEquals(comboDetalle1.hashCode(), comboDetalle2.hashCode(), "Los hashcodes deberían ser iguales");
    }

    @Test
    public void testSettersAndGetters() {
        // Crear objeto ComboDetalle
        ComboDetallePK pk = new ComboDetallePK(1L, 2L);
        ComboDetalle comboDetalle = new ComboDetalle(pk);
        
        // Modificar los valores usando setters
        comboDetalle.setCantidad(15);
        comboDetalle.setActivo(false);
        
        // Verificar que los valores se hayan actualizado correctamente
        Assertions.assertEquals(15, comboDetalle.getCantidad());
        Assertions.assertFalse(comboDetalle.getActivo());
    }
    
    @Test
    public void testToString() {
        ComboDetallePK pk = new ComboDetallePK(1L, 1L);
        ComboDetalle comboDetalle = new ComboDetalle(pk);
        
        String expectedString = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle[ comboDetallePK=" + pk + " ]";
        
        // Test toString method
        Assertions.assertEquals(expectedString, comboDetalle.toString());
    }
    
}
