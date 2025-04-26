/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class CarritoDTOTest {

    @Test
    public void testConstructorPorDefecto() {
        CarritoDTO carrito = new CarritoDTO(); //crea una nueva instancia 
        assertNotNull(carrito.getItemsCarrito()); // verifica que la lista no sea nula
        assertTrue(carrito.getItemsCarrito().isEmpty());
        assertNull(carrito.getTotal());
        System.out.println("CarritoDTOTest --> testConstructorPorDefecto ");
    }

    @Test
    public void testSettersYGetters() {
        CarritoDTO carrito = new CarritoDTO(); 
        //Crea una lista con dos objetos CarritoItemDTO 
        //Aún no tienen datos, pero sirven para probar que la lista funciona. 
        List<CarritoItemDTO> items = new ArrayList<>();
        CarritoItemDTO item1 = new CarritoItemDTO();
        CarritoItemDTO item2 = new CarritoItemDTO();
        items.add(item1);
        items.add(item2);

        BigDecimal total = new BigDecimal("25.50");

        carrito.setItemsCarrito(items);
        carrito.setTotal(total);

        assertEquals(2, carrito.getItemsCarrito().size());
        assertEquals(total, carrito.getTotal());
        System.out.println("CarritoDTOTest --> testSettersYGetters ");
    }

}
