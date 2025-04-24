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
public class ComboTest {
    
     @Test
    public void testConstructorAndGettersSetters() {
        Combo combo = new Combo();
        combo.setIdCombo(1L);
        combo.setNombre("Combo Tipico");
        combo.setActivo(true);
        combo.setDescripcionPublica("Combo típico salvadoreño con bebida");

        List<ComboDetalle> detalles = new ArrayList<>();
        ComboDetalle detalle = new ComboDetalle();
        detalle.setCombo(combo);
        detalles.add(detalle);
        combo.setComboDetalleList(detalles);

        assertEquals(1L, combo.getIdCombo());
        assertEquals("Combo Tipico", combo.getNombre());
        assertTrue(combo.getActivo());
        assertEquals("Combo típico salvadoreño con bebida", combo.getDescripcionPublica());
        assertNotNull(combo.getComboDetalleList());
        assertEquals(1, combo.getComboDetalleList().size());
        assertEquals(combo, combo.getComboDetalleList().get(0).getCombo());
    }

    @Test
    public void testEqualsAndHashCode() {
        Combo combo1 = new Combo(1L);
        Combo combo2 = new Combo(1L);
        Combo combo3 = new Combo(2L);

        assertEquals(combo1, combo2);
        assertNotEquals(combo1, combo3);
        assertEquals(combo1.hashCode(), combo2.hashCode());
        assertNotEquals(combo1.hashCode(), combo3.hashCode());
    }

    @Test
    public void testEqualsWithNullId() {
        Combo combo1 = new Combo();
        Combo combo2 = new Combo();

        assertEquals(combo1, combo2); // Ambos tienen id nulo, se considera igual en esta implementación
    }

    @Test
    public void testToString() {
        Combo combo = new Combo(5L);
        String expected = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo[ idCombo=5 ]";
        assertEquals(expected, combo.toString());
    }
    
}
