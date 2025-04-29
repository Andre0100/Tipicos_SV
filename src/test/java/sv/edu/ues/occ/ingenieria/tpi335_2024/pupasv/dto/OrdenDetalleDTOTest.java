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
public class OrdenDetalleDTOTest {
    
     @Test
    void testSettersAndGetters() {
        System.out.println("OrdenDetalleDTOTest --> testSettersAndGetters");

        OrdenDetalleDTO dto = new OrdenDetalleDTO();

        dto.setIdOrden(1L);
        dto.setIdProductoPrecio(10L);
        dto.setCantidad(3);
        dto.setPrecio(BigDecimal.valueOf(4.50));

        assertEquals(1L, dto.getIdOrden());
        assertEquals(10L, dto.getIdProductoPrecio());
        assertEquals(3, dto.getCantidad());
        assertEquals(BigDecimal.valueOf(4.50), dto.getPrecio());
    }

    @Test
    void testConstructorVacio() {
        System.out.println("OrdenDetalleDTOTest --> testConstructorVacio");

        OrdenDetalleDTO dto = new OrdenDetalleDTO();
        assertNull(dto.getIdOrden());
        assertNull(dto.getIdProductoPrecio());
        assertNull(dto.getCantidad());
        assertNull(dto.getPrecio());
    }
    
}
