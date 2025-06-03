/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ProductoConPrecioDTOTest {
    
     @Test
    void testConstructorVacio() {
        System.out.println("ProductoConPrecioDTOTest --> testConstructorVacio");

        ProductoConPrecioDTO dto = new ProductoConPrecioDTO();

        assertNull(dto.getIdProductoPrecio());
        assertNull(dto.getNombre());
        assertNull(dto.getObservaciones());
        assertNull(dto.getPrecio());
        assertNull(dto.getFechaValidezPrecio());
    }

    @Test
    void testConstructorConProductoYPrecioNulos() {
        System.out.println("ProductoConPrecioDTOTest --> testConstructorConProductoYPrecioNulos");

        ProductoConPrecioDTO dto = new ProductoConPrecioDTO(null, null);

        assertNull(dto.getIdProductoPrecio());
        assertNull(dto.getNombre());
        assertNull(dto.getObservaciones());
        assertNull(dto.getPrecio());
        assertNull(dto.getFechaValidezPrecio());
    }
    
}
