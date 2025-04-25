/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class PagoDetalleTest {

    @Test
    public void testConstructoresYGettersSetters() {
        PagoDetalle pagoDetalle = new PagoDetalle();

        Long id = 1L;
        BigDecimal monto = new BigDecimal("29.99");
        String observaciones = "Pago parcial";
        Pago pago = new Pago(99L);

        pagoDetalle.setIdPagoDetalle(id);
        pagoDetalle.setMonto(monto);
        pagoDetalle.setObservaciones(observaciones);
        pagoDetalle.setIdPago(pago);

        assertEquals(id, pagoDetalle.getIdPagoDetalle());
        assertEquals(monto, pagoDetalle.getMonto());
        assertEquals(observaciones, pagoDetalle.getObservaciones());
        assertEquals(pago, pagoDetalle.getIdPago());
        System.out.println("PagoDetalleTest --> testConstructoresYGettersSetters...");

    }

    @Test
    public void testConstructorConId() {
        PagoDetalle pagoDetalle = new PagoDetalle(10L);
        assertEquals(10L, pagoDetalle.getIdPagoDetalle());
        System.out.println("PagoDetalleTest --> testConstructorConId...");

    }

    @Test
    public void testEqualsYHashCode() {
        PagoDetalle p1 = new PagoDetalle(1L);
        PagoDetalle p2 = new PagoDetalle(1L);
        PagoDetalle p3 = new PagoDetalle(2L);
        PagoDetalle pNull1 = new PagoDetalle();
        PagoDetalle pNull2 = new PagoDetalle();

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "no es PagoDetalle");
        assertEquals(pNull1, pNull2);
        System.out.println("PagoDetalleTest --> testEqualsYHashCode...");

    }

    @Test
    public void testEqualsDiferenteIdPagoDetalle() {
        PagoDetalle p1 = new PagoDetalle(null);
        PagoDetalle p2 = new PagoDetalle(5L);
        assertNotEquals(p1, p2);
        System.out.println("PagoDetalleTest --> testEqualsDiferenteIdPagoDetalle...");

    }

    @Test
    public void testToString() {

        PagoDetalle pagoDetalle = new PagoDetalle(77L);
        String result = pagoDetalle.toString();
        assertTrue(result.contains("idPagoDetalle=77"));
        System.out.println("PagoDetalleTest --> testToString...");
    }

}
