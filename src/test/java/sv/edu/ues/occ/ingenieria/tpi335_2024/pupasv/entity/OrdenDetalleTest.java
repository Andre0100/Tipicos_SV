/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class OrdenDetalleTest {

    @Test
    public void testConstructorAndGetters() {
        OrdenDetallePK ordenDetallePK = new OrdenDetallePK(1L, 2L);
        OrdenDetalle ordenDetalle = new OrdenDetalle(ordenDetallePK, 5);
        ordenDetalle.setPrecio(new BigDecimal("100.50"));
        ordenDetalle.setObservaciones("Sin observaciones");

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testConstructorAndGetters...");

        Assertions.assertEquals(1L, ordenDetalle.getOrdenDetallePK().getIdOrden(), "idOrden debe ser 1");
        Assertions.assertEquals(2L, ordenDetalle.getOrdenDetallePK().getIdProductoPrecio(), "idProductoPrecio debe ser 2");
        Assertions.assertEquals(5, ordenDetalle.getCantidad(), "La cantidad debe ser 5");
        Assertions.assertEquals(new BigDecimal("100.50"), ordenDetalle.getPrecio(), "El precio debe ser 100.50");
        Assertions.assertEquals("Sin observaciones", ordenDetalle.getObservaciones(), "Las observaciones deben ser 'Sin observaciones'");
    }

    @Test
    public void testSetters() {
        OrdenDetalle ordenDetalle = new OrdenDetalle(new OrdenDetallePK(1L, 2L), 10);
        ordenDetalle.setPrecio(new BigDecimal("150.75"));
        ordenDetalle.setObservaciones("Observaciones modificadas");

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testSetters...");

        Assertions.assertEquals(new BigDecimal("150.75"), ordenDetalle.getPrecio(), "El precio debe ser 150.75");
        Assertions.assertEquals("Observaciones modificadas", ordenDetalle.getObservaciones(), "Las observaciones deben ser 'Observaciones modificadas'");
    }

    @Test
    public void testEquals() {
        OrdenDetallePK pk1 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk2 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk3 = new OrdenDetallePK(2L, 3L);

        OrdenDetalle ordenDetalle1 = new OrdenDetalle(pk1, 3);
        OrdenDetalle ordenDetalle2 = new OrdenDetalle(pk2, 3);
        OrdenDetalle ordenDetalle3 = new OrdenDetalle(pk3, 4);

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testEquals...");

        Assertions.assertEquals(ordenDetalle1, ordenDetalle2, "ordenDetalle1 debe ser igual a ordenDetalle2");
        Assertions.assertNotEquals(ordenDetalle1, ordenDetalle3, "ordenDetalle1 no debe ser igual a ordenDetalle3");
        Assertions.assertNotEquals(ordenDetalle1, null, "ordenDetalle1 no debe ser igual a null");
        Assertions.assertNotEquals(ordenDetalle1, "otro objeto", "ordenDetalle1 no debe ser igual a un objeto de otro tipo");
    }

    @Test
    public void testHashCode() {
        OrdenDetallePK pk1 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk2 = new OrdenDetallePK(1L, 2L);
        OrdenDetallePK pk3 = new OrdenDetallePK(2L, 3L);

        OrdenDetalle ordenDetalle1 = new OrdenDetalle(pk1, 3);
        OrdenDetalle ordenDetalle2 = new OrdenDetalle(pk2, 3);
        OrdenDetalle ordenDetalle3 = new OrdenDetalle(pk3, 4);

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testHashCode...");

        Assertions.assertEquals(ordenDetalle1.hashCode(), ordenDetalle2.hashCode(), "hashCode de ordenDetalle1 y ordenDetalle2 deben coincidir");
        Assertions.assertNotEquals(ordenDetalle1.hashCode(), ordenDetalle3.hashCode(), "hashCode de ordenDetalle1 y ordenDetalle3 no deben coincidir");
    }

    @Test
    public void testToString() {
        OrdenDetallePK ordenDetallePK = new OrdenDetallePK(1L, 2L);
        OrdenDetalle ordenDetalle = new OrdenDetalle(ordenDetallePK, 5);
        ordenDetalle.setPrecio(new BigDecimal("100.50"));
        ordenDetalle.setObservaciones("Sin observaciones");

        String expected = "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle[ ordenDetallePK=sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK[ idOrden=1, idProductoPrecio=2 ] ]";

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testToString...");

        Assertions.assertEquals(expected, ordenDetalle.toString(), "toString debe retornar el formato esperado");
    }

    // También puedes agregar pruebas para las relaciones @ManyToOne
    // Por ejemplo, asegurándote que las entidades asociadas sean correctamente inyectadas
    @Test
    public void testRelacionOrdenYProductoPrecio() {
        OrdenDetallePK pk = new OrdenDetallePK(1L, 2L);
        OrdenDetalle ordenDetalle = new OrdenDetalle(pk, 5);

        Orden orden = new Orden();
        orden.setIdOrden(1L);
        ordenDetalle.setOrden(orden);

        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(2L);
        ordenDetalle.setProductoPrecio(productoPrecio);

        // Log de lo que estamos probando
        System.out.println("OrdenDetalle --> testRelacionOrdenYProductoPrecio...");

        Assertions.assertNotNull(ordenDetalle.getOrden(), "La orden no debe ser nula");
        Assertions.assertNotNull(ordenDetalle.getProductoPrecio(), "El productoPrecio no debe ser nulo");
    }

}
