package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoBeanTest {
    
    @InjectMocks
    private ProductoBean cut;

    @Mock
    private TipoProductoBean tPBean;

    @Mock
    private EntityManager em;

    @Test
    void getEntityManager(){
        System.out.println("TipoProductoBean --> getEntityManager");
        assertEquals(em, cut.getEntityManager());
    }

    @Test
    public void findByActivo() {
        System.out.println("ProductoBeanTest --> findByActivo");
        TypedQuery<Producto> queryActivo = mock(TypedQuery.class);
        when(em.createNamedQuery("Producto.findByActivo", Producto.class)).thenReturn(queryActivo);
        when(queryActivo.setParameter("activo", true)).thenReturn(queryActivo);
        when(queryActivo.getResultList()).thenReturn(List.of(new Producto()));  // Simulamos que se devuelven productos

        // Ejecuta el método
        List<Producto> resultado = cut.findByActivo();

        // Verificación
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "La lista no debe estar vacía");

        // Excepción
        when(queryActivo.getResultList()).thenThrow(new RuntimeException("Database error"));

        // Ejecuta el método con una excepción
        List<Producto> resultadoConExcepcion = cut.findByActivo();

        // Verificación de la excepción
        assertNotNull(resultadoConExcepcion);
        assertTrue(resultadoConExcepcion.isEmpty(), "La lista debe estar vacía debido a la excepción");
    }


    @Test
    public void getProductosAgrupadosPorTipo() {
        System.out.println("ProductoBeanTest --> getProductosAgrupadosPorTipo");
        // Simula tipos de productos devueltos por tipo producto bean
        List<String> tiposProducto = List.of("bebida", "comida");
        when(tPBean.findByNombreTipoProductoName()).thenReturn(tiposProducto);

        // Crea productos y precios simulados
        Producto productoBebida = new Producto();
        productoBebida.setIdProducto(1L);
        productoBebida.setNombre("Coca Cola");

        ProductoPrecio precioBebida = new ProductoPrecio();
        precioBebida.setIdProductoPrecio(1L);
        precioBebida.setPrecioSugerido(new BigDecimal("1.50"));
        precioBebida.setFechaHasta(new Date());

        Producto productoComida = new Producto();
        productoComida.setIdProducto(2L);
        productoComida.setNombre("Hamburguesa");

        ProductoPrecio precioComida = new ProductoPrecio();
        precioComida.setIdProductoPrecio(2L);
        precioComida.setPrecioSugerido(new BigDecimal("3.75"));
        precioComida.setFechaHasta(new Date());

        // Mock para el query de bebida
        TypedQuery<Object[]> queryBebida = mock(TypedQuery.class);
        when(em.createNamedQuery("Producto.findByTipoConPrecio", Object[].class)).thenReturn(queryBebida);
        when(queryBebida.setParameter("nombreTipo", "bebida")).thenReturn(queryBebida);
        when(queryBebida.getResultList()).thenReturn(Collections.singletonList(new Object[]{productoBebida, precioBebida}));

        // Mock para el query de comida
        TypedQuery<Object[]> queryComida = mock(TypedQuery.class);
        when(em.createNamedQuery("Producto.findByTipoConPrecio", Object[].class)).thenReturn(queryComida);
        when(queryComida.setParameter("nombreTipo", "comida")).thenReturn(queryComida);
        when(queryComida.getResultList()).thenReturn(Collections.singletonList(new Object[]{productoComida, precioComida}));

        //Returns
        when(em.createNamedQuery("Producto.findByTipoConPrecio", Object[].class))
                .thenReturn(queryBebida)
                .thenReturn(queryComida);

        //Ejecución del método
        Map<String, List<ProductoConPrecioDTO>> resultado = cut.getProductosAgrupadosPorTipo();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        List<ProductoConPrecioDTO> bebidas = resultado.get("bebida");
        assertNotNull(bebidas);
        assertEquals(1, bebidas.size());
        assertEquals("Coca Cola", bebidas.get(0).getNombre());
        assertEquals(new BigDecimal("1.50"), bebidas.get(0).getPrecio());

        List<ProductoConPrecioDTO> comidas = resultado.get("comida");
        assertNotNull(comidas);
        assertEquals(1, comidas.size());
        assertEquals("Hamburguesa", comidas.get(0).getNombre());
        assertEquals(new BigDecimal("3.75"), comidas.get(0).getPrecio());
    }
}
