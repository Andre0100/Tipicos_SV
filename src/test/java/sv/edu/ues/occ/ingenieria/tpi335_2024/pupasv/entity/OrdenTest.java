/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class OrdenTest {
    
    @Test
    public void testConstructorConId() {
        Orden orden = new Orden(1L);
        assertEquals(1L, orden.getIdOrden());
        System.out.println("OrdenTest --> testConstructorConId");
    }

    @Test
    public void testSetAndGetFecha() {
        Orden orden = new Orden();
        Date fecha = new Date();
        orden.setFecha(fecha);
        assertEquals(fecha, orden.getFecha());
        System.out.println("OrdenTest --> testSetAndGetFecha");
    }

    @Test
    public void testSetAndGetSucursal() {
        Orden orden = new Orden();
        String sucursal = "S01";
        orden.setSucursal(sucursal);
        assertEquals(sucursal, orden.getSucursal());
        System.out.println("OrdenTest --> testSetAndGetSucursal"); 
    }

    @Test
    public void testSetAndGetAnulada() {
        Orden orden = new Orden();
        orden.setAnulada(true);
        assertTrue(orden.getAnulada()); 
        System.out.println("OrdenTest --> testSetAndGetAnulada"); 
    }

    @Test
    public void testSetAndGetOrdenDetalleList() {
        Orden orden = new Orden();
        List<OrdenDetalle> detalles = new ArrayList<>();
        orden.setOrdenDetalleList(detalles);
        assertEquals(detalles, orden.getOrdenDetalleList()); 
        System.out.println("OrdenTest --> testSetAndGetOrdenDetalleList");
    }

    @Test
    public void testSetAndGetPagoList() {
        Orden orden = new Orden();
        List<Pago> pagos = new ArrayList<>();
        orden.setPagoList(pagos);
        assertEquals(pagos, orden.getPagoList());
        System.out.println("OrdenTest --> testSetAndGetPagoList");
    }

    @Test
    public void testEqualsTrue() {
        Orden orden1 = new Orden(1L);
        Orden orden2 = new Orden(1L);
        assertTrue(orden1.equals(orden2));
        System.out.println("OrdenTest --> testEqualsTrue");
    }

    @Test
    public void testEqualsFalse_DifferentId() {
        Orden orden1 = new Orden(1L);
        Orden orden2 = new Orden(2L);
        assertFalse(orden1.equals(orden2));
        System.out.println("OrdenTest --> testEqualsFalse_DifferentId");
    }

    @Test
    public void testEqualsFalse_NullId() {
        Orden orden1 = new Orden();
        Orden orden2 = new Orden(2L);
        assertFalse(orden1.equals(orden2));
        System.out.println("OrdenTest --> testEqualsFalse_NullId");
    }

    @Test
    public void testEqualsFalse_DifferentType() {
        Orden orden = new Orden(1L);
        assertFalse(orden.equals("NotAnOrden"));
        System.out.println("OrdenTest --> testEqualsFalse_DifferentType");
    }

    @Test
    public void testHashCode() {
        Orden orden = new Orden(1L);
        assertEquals(Objects.hashCode(1L), orden.hashCode());
        System.out.println("OrdenTest --> testHashCode");
    }

    @Test
    public void testToString() {
        Orden orden = new Orden(1L);
        String expected = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden[ idOrden=1 ]";
        assertEquals(expected, orden.toString()); 
        System.out.println("OrdenTest --> testToString");
    }
    
}
