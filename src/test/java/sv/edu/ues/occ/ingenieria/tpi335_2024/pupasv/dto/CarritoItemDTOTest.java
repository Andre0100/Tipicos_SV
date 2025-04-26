/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class CarritoItemDTOTest {
    
     @Test
    public void testSettersYGetters() {
        CarritoItemDTO item = new CarritoItemDTO();

        Long idProductoPrecio = 10L;
        String nombreProducto = "Yuca con chicharrón";
        int cantidad = 3;
        BigDecimal precio = new BigDecimal("2.50");
        String observaciones = "Sin cebolla";

        item.setIdProductoPrecio(idProductoPrecio);
        item.setNombreProducto(nombreProducto);
        item.setCantidad(cantidad);
        item.setPrecio(precio);
        item.setObservaciones(observaciones);

        assertEquals(idProductoPrecio, item.getIdProductoPrecio());
        assertEquals(nombreProducto, item.getNombreProducto());
        assertEquals(cantidad, item.getCantidad());
        assertEquals(precio, item.getPrecio());
        assertEquals(observaciones, item.getObservaciones());

        System.out.println("✓ testSettersYGetters ejecutado");
    }

    @Test
    public void testValoresPorDefecto() {
        CarritoItemDTO item = new CarritoItemDTO();

        assertNull(item.getIdProductoPrecio());
        assertNull(item.getNombreProducto());
        assertEquals(0, item.getCantidad());
        assertNull(item.getPrecio());
        assertNull(item.getObservaciones());

        System.out.println("✓ testValoresPorDefecto ejecutado");
    }
    
}
