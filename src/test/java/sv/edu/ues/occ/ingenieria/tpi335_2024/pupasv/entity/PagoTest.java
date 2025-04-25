/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class PagoTest {
    
    @Test
    public void testConstructoresYGettersSetters() {
        
        Pago pago = new Pago();

        Long idPago = 1L;
        Date fecha = new Date();
        String metodoPago = "Tarjeta";
        String referencia = "REF123";
        Orden orden = new Orden();
        List<PagoDetalle> pagoDetalles = new ArrayList<>();

        pago.setIdPago(idPago);
        pago.setFecha(fecha);
        pago.setMetodoPago(metodoPago);
        pago.setReferencia(referencia);
        pago.setIdOrden(orden);
        pago.setPagoDetalleList(pagoDetalles);

        assertEquals(idPago, pago.getIdPago());
        assertEquals(fecha, pago.getFecha());
        assertEquals(metodoPago, pago.getMetodoPago());
        assertEquals(referencia, pago.getReferencia());
        assertEquals(orden, pago.getIdOrden());
        assertEquals(pagoDetalles, pago.getPagoDetalleList());
        System.out.println("PagoTest --> testConstructoresYGettersSetters...");
    }

    @Test
    public void testConstructorConId() {
        System.out.println("PagoTest --> testConstructorConId...");
        Pago pago = new Pago(99L);
        assertEquals(99L, pago.getIdPago());
    }

    @Test
    public void testEqualsYHashCode() {
        System.out.println("PagoTest --> testEqualsYHashCode...");
        Pago p1 = new Pago(1L);
        Pago p2 = new Pago(1L);
        Pago p3 = new Pago(2L);
        Pago pNullId1 = new Pago();
        Pago pNullId2 = new Pago();

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "otro objeto");

        // Ambos sin ID deben ser iguales en equals según implementación
        assertEquals(pNullId1, pNullId2);
    }

    @Test
    public void testEqualsDiferenteIdPago() {
        System.out.println("PagoTest --> testEqualsDiferenteIdPago...");
        Pago p1 = new Pago(null);
        Pago p2 = new Pago(5L);
        assertNotEquals(p1, p2); // cobertura de: this.idPago == null && other.idPago != null
    }

    @Test
    public void testToString() {
        System.out.println("PagoTest --> testToString...");
        Pago pago = new Pago(100L);
        String result = pago.toString();
        assertTrue(result.contains("idPago=100"));
    }
    
}
