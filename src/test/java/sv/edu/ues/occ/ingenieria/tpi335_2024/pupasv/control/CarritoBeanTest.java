package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarritoBeanTest {

    @Mock
    ProductoPrecioBean ppBean;
    @InjectMocks
    CarritoBean cut;

    @Test
    void agregarItem(){
        System.out.println("CarritoBeanTest --> agregarItem");
        //Producto
        Producto producto = new Producto();
        producto.setNombre("Pupusa");
        //ProductoPrecio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");

        cut.agregarItem(item);

        List<CarritoItemDTO> carrito = cut.obtenerItems();
        Assertions.assertEquals(1, carrito.size());

        CarritoItemDTO agreado = carrito.get(0);
        Assertions.assertEquals("Pupusa",agreado.getNombreProducto());
        Assertions.assertEquals(new BigDecimal("0.75"),agreado.getPrecio());
        Assertions.assertEquals(2, agreado.getCantidad());
        Assertions.assertEquals("Revuelta",agreado.getObservaciones());
        Assertions.assertEquals(1L,agreado.getIdProductoPrecio());

        verify(ppBean).findById(1L);

        //Excepción
        when(ppBean.findById(2L)).thenReturn(null);
        CarritoItemDTO itemNull = new CarritoItemDTO();
        itemNull.setIdProductoPrecio(2L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cut.agregarItem(itemNull);
        });
    }

    @Test
    void eliminarItem(){
        System.out.println("CarritoBeanTest --> eliminarItem");
        //Producto
        Producto producto = new Producto();
        producto.setNombre("Pupusa");
        //Producto Precio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);

        cut.agregarItem(item);
        Assertions.assertEquals(1, cut.obtenerItems().size());
        cut.eliminarItem(1L);
        Assertions.assertEquals(cut.obtenerItems().isEmpty(), true);
    }

    @Test
    void limpiarCarrito() {
        System.out.println("CarritoBeanTest --> limpiarCarrito");
        //Producto
        Producto producto = new Producto();
        producto.setNombre("Pupusa");
        //Producto Precio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);

        cut.agregarItem(item);
        //Verifica que el carrito tiene items
        Assertions.assertFalse(cut.obtenerItems().isEmpty());
        cut.limpiarCarrito();
        //Verifica que el carrito está vacío
        Assertions.assertTrue(cut.obtenerItems().isEmpty());
    }

    @Test
    void calcularTotal() {
        System.out.println("CarritoBeanTest --> calcularTotal");
        //Producto #1
        Producto producto = new Producto();
        producto.setNombre("Pupusa");
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
        //Producto #2
        Producto producto2 = new Producto();
        producto2.setNombre("Refresco");
        ProductoPrecio productoPrecio2 = new ProductoPrecio();
        productoPrecio2.setIdProductoPrecio(2L);
        productoPrecio2.setPrecioSugerido(new BigDecimal("0.50"));
        productoPrecio2.setIdProducto(producto2);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);
        when(ppBean.findById(2L)).thenReturn(productoPrecio2);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);

        CarritoItemDTO item2 = new CarritoItemDTO();
        item2.setIdProductoPrecio(2L);
        item2.setCantidad(3);

        cut.agregarItem(item);
        cut.agregarItem(item2);

        BigDecimal totalEsperado = new BigDecimal("0.75").multiply(BigDecimal.valueOf(2))
                .add(new BigDecimal("0.50").multiply(BigDecimal.valueOf(3)));

        Assertions.assertEquals(totalEsperado, cut.calcularTotal());
    }

}
