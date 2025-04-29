package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoPrecioBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoBeanTest {

    @Mock
    ProductoPrecioBean ppBean;
    @InjectMocks
    CarritoBean cut;

    @Test
    void agregarItems(){
        System.out.println("CarritoBeanTest --> agregarItems");
        
        List<CarritoItemDTO> listProductos = new ArrayList<>();
        
        //Productos
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Pupusa");
        
        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("Pupusa especial");
        
        Producto producto3 = new Producto();
        producto3.setIdProducto(2L);
        producto3.setNombre("Pupusa premiun");
        
        //ProductoPrecio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
        
        ProductoPrecio productoPrecio2 = new ProductoPrecio();
        productoPrecio2.setIdProductoPrecio(2L);
        productoPrecio2.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio2.setIdProducto(producto2);
        
        ProductoPrecio productoPrecio3 = new ProductoPrecio();
        productoPrecio3.setIdProductoPrecio(3L);
        productoPrecio3.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio3.setIdProducto(producto3);
        
        
        when(ppBean.findById(1L)).thenReturn(productoPrecio);
        when(ppBean.findById(2L)).thenReturn(productoPrecio2);
        when(ppBean.findById(3L)).thenReturn(productoPrecio3);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");
        listProductos.add(item);

        CarritoItemDTO item2 = new CarritoItemDTO();
        item2.setIdProductoPrecio(2L);
        item2.setCantidad(4);
        item2.setObservaciones("Extra queso");
        listProductos.add(item2);


        CarritoItemDTO item3 = new CarritoItemDTO();
        item3.setIdProductoPrecio(3L);
        item3.setCantidad(5);
        item3.setObservaciones("Queso con Ajo"); 
        listProductos.add(item3);
        
        cut.agregarItem(listProductos);

        List<CarritoItemDTO> carrito = cut.obtenerItems();
        Assertions.assertEquals(3, carrito.size());

        CarritoItemDTO agreado = carrito.get(0);
        Assertions.assertEquals("Pupusa",agreado.getNombreProducto());
        Assertions.assertEquals(new BigDecimal("0.75"),agreado.getPrecio());
        Assertions.assertEquals(2, agreado.getCantidad());
        Assertions.assertEquals("Revuelta",agreado.getObservaciones());
        Assertions.assertEquals(1L,agreado.getIdProductoPrecio());
        
        CarritoItemDTO agreado2 = carrito.get(1);
        Assertions.assertEquals("Pupusa especial",agreado2.getNombreProducto());
        Assertions.assertEquals(new BigDecimal("0.75"),agreado2.getPrecio());
        Assertions.assertEquals(4, agreado2.getCantidad());
        Assertions.assertEquals("Extra queso",agreado2.getObservaciones());
        Assertions.assertEquals(2L,agreado2.getIdProductoPrecio());
        
        CarritoItemDTO agreado3 = carrito.get(2);
        Assertions.assertEquals("Pupusa premiun",agreado3.getNombreProducto());
        Assertions.assertEquals(new BigDecimal("0.75"),agreado3.getPrecio());
        Assertions.assertEquals(5, agreado3.getCantidad());
        Assertions.assertEquals("Queso con Ajo",agreado3.getObservaciones());
        Assertions.assertEquals(3L,agreado3.getIdProductoPrecio());

        verify(ppBean).findById(1L);
        verify(ppBean).findById(2L);
        verify(ppBean).findById(3L);

        //Excepción
        when(ppBean.findById(4L)).thenReturn(null);
        List<CarritoItemDTO> itemNull = new ArrayList();
        CarritoItemDTO itemInvalido = new CarritoItemDTO();
        itemInvalido.setIdProductoPrecio(4L);
        itemNull.add(itemInvalido);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cut.agregarItem((List<CarritoItemDTO>) itemNull);
        });
    }

    @Test
    void eliminarItem(){
        System.out.println("CarritoBeanTest --> eliminarItem");
        
        List<CarritoItemDTO> listProductos = new ArrayList<>();
        
        //Productos
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Pupusa");
        
        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("Pupusa especial");
        
         //ProductoPrecio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
        
        ProductoPrecio productoPrecio2 = new ProductoPrecio();
        productoPrecio2.setIdProductoPrecio(2L);
        productoPrecio2.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio2.setIdProducto(producto2);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);
        when(ppBean.findById(2L)).thenReturn(productoPrecio2);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");
        listProductos.add(item);

        CarritoItemDTO item2 = new CarritoItemDTO();
        item2.setIdProductoPrecio(2L);
        item2.setCantidad(4);
        item2.setObservaciones("Extra queso");
        listProductos.add(item2);

        cut.agregarItem(listProductos);
        Assertions.assertEquals(2, cut.obtenerItems().size());
        cut.eliminarItem(1L);
        cut.eliminarItem(2L);
        Assertions.assertEquals(cut.obtenerItems().isEmpty(), true);
    }

    @Test
    void limpiarCarrito() {
        
        System.out.println("CarritoBeanTest --> limpiarCarrito");
        
        List<CarritoItemDTO> listProductos = new ArrayList<>();
        //Productos
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Pupusa");
        
        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("Pupusa especial");
        
         //ProductoPrecio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
        
        ProductoPrecio productoPrecio2 = new ProductoPrecio();
        productoPrecio2.setIdProductoPrecio(2L);
        productoPrecio2.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio2.setIdProducto(producto2);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);
        when(ppBean.findById(2L)).thenReturn(productoPrecio2);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");
        listProductos.add(item);

        CarritoItemDTO item2 = new CarritoItemDTO();
        item2.setIdProductoPrecio(2L);
        item2.setCantidad(4);
        item2.setObservaciones("Extra queso");
        listProductos.add(item2);

        cut.agregarItem(listProductos);
        //Verifica que el carrito tiene items
        Assertions.assertFalse(cut.obtenerItems().isEmpty());
        cut.limpiarCarrito();
        //Verifica que el carrito está vacío
        Assertions.assertTrue(cut.obtenerItems().isEmpty());
    }

    @Test
    void calcularTotal() {
        System.out.println("CarritoBeanTest --> calcularTotal");
        
        List<CarritoItemDTO> listProductos = new ArrayList<>();
        //Productos
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Pupusa");
        
        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("Pupusa especial");
        
         //ProductoPrecio
        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);
        
        ProductoPrecio productoPrecio2 = new ProductoPrecio();
        productoPrecio2.setIdProductoPrecio(2L);
        productoPrecio2.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio2.setIdProducto(producto2);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);
        when(ppBean.findById(2L)).thenReturn(productoPrecio2);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");
        listProductos.add(item);

        CarritoItemDTO item2 = new CarritoItemDTO();
        item2.setIdProductoPrecio(2L);
        item2.setCantidad(4);
        item2.setObservaciones("Extra queso");
        listProductos.add(item2);

        cut.agregarItem(listProductos);
        
        BigDecimal totalEsperado = new BigDecimal("0.75").multiply(BigDecimal.valueOf(2))
                .add(new BigDecimal("0.75").multiply(BigDecimal.valueOf(4)));

        Assertions.assertEquals(totalEsperado, cut.calcularTotal());
    }

    @Test
    void actualizarItem() {
        System.out.println("CarritoBeanTest --> actualizarItem");

        // Datos iniciales
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Pupusa");

        ProductoPrecio productoPrecio = new ProductoPrecio();
        productoPrecio.setIdProductoPrecio(1L);
        productoPrecio.setPrecioSugerido(new BigDecimal("0.75"));
        productoPrecio.setIdProducto(producto);

        when(ppBean.findById(1L)).thenReturn(productoPrecio);

        CarritoItemDTO item = new CarritoItemDTO();
        item.setIdProductoPrecio(1L);
        item.setCantidad(2);
        item.setObservaciones("Revuelta");

        List<CarritoItemDTO> list = new ArrayList<>();
        list.add(item);

        cut.agregarItem(list);

        // Verificación antes de la actualización
        CarritoItemDTO antes = cut.obtenerItems().get(0);
        assertEquals(2, antes.getCantidad());
        assertEquals("Revuelta", antes.getObservaciones());

        // Actualización
        cut.actualizarItem(1L, 5, "Sin queso");

        CarritoItemDTO despues = cut.obtenerItems().get(0);
        assertEquals(5, despues.getCantidad());
        assertEquals("Sin queso", despues.getObservaciones());
    }
}
