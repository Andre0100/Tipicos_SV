/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.integrationTests;

import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;
import testing.BaseIntegrationAbstract;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoBeanIT extends BaseIntegrationAbstract{
    
    @Test
    @Order(1)
    public void insertar(){
        System.out.println("ProductoBeanIT -------------> Insertar");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        Producto registro1 = new Producto();
        registro1.setNombre("Pupusa Revuelta");
        registro1.setActivo(true);
        registro1.setObservaciones("Pupa con varios ingredientes");

        Producto registro2 = new Producto();
        registro2.setNombre("Pupusa de Queso");
        registro2.setActivo(true);
        registro2.setObservaciones("Pupa solamente de queso");

        Producto registro3 = new Producto();
        registro3.setNombre("Pupusa de Ayote");
        registro3.setActivo(true);
        registro3.setObservaciones("Pupa de Ayote");
        
        try {
            cut.em.getTransaction().begin();
            cut.create(registro1);
            cut.create(registro2);
            cut.create(registro3);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(registro1);
            Assertions.assertNotNull(registro2);
            Assertions.assertNotNull(registro3);
            System.out.println("Registro Creado con ID "+registro1.getIdProducto());
            System.out.println("Registro Creado con ID "+registro2.getIdProducto());
            System.out.println("Registro Creado con ID "+registro3.getIdProducto());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            try {
                cut.em.getTransaction().rollback();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
    }
    
    @Test
    @Order(2)
    public void testContar(){
        System.out.println("ProductoBeanIT ------------> Contar");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        int esperado = 3;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    
    @Test
    @Order(3)
    public void testBuscarPorId(){
        System.out.println("ProductoBeanIT -------------> Buscar por Id");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            Producto registro = cut.findById(3L);
            Assertions.assertNotNull(registro);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }
    }
    
    
    @Test
    @Order(4)
    public void testActualizar(){
        System.out.println("ProductoBeanIT -------------> Actualizar");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            Producto registro = cut.findById(3L);
            Assertions.assertNotNull(registro);
            registro.setNombre("Pupusa de jalapeño");
            
            cut.em.getTransaction().begin();
            cut.update(registro);
            cut.em.getTransaction().commit();
            Assertions.assertEquals("Pupusa de jalapeño", registro.getNombre());
            System.out.println("Registro Actualizado");
        } catch (Exception e) {
        
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            try {
                cut.em.getTransaction().rollback();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    
    @Test
    @Order(5)
    public void testListarTodos() {
        System.out.println("ProductoBeanIT --------------> Listar Todos");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        List<Producto> resultados = cut.findRange(0, 10);
        Assertions.assertNotNull(resultados);
        Assertions.assertFalse(resultados.isEmpty());
        Assertions.assertEquals(3, resultados.size()); 
        
        //resultados.forEach(tp -> System.out.println("ID: " + tp.getIdProducto() + 
                                             //  ", Nombre: " + tp.getNombre()));
    }
    
    @Test
    @Order(6)
    public void testFindByActivo() {
        System.out.println("ProductoBeanIT --------------> findByActivo");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        cut.em = em;

        try {
            em.getTransaction().begin();

            Producto activo1 = new Producto();
            activo1.setNombre("Pupusa Revuelta Activa");
            activo1.setActivo(true);
            em.persist(activo1);

            Producto activo2 = new Producto();
            activo2.setNombre("Pupusa Queso Activa");
            activo2.setActivo(true);
            em.persist(activo2);

            Producto inactivo = new Producto();
            inactivo.setNombre("Pupusa Inactiva");
            inactivo.setActivo(false);
            em.persist(inactivo);

            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        List<Producto> productosActivos = cut.findByActivo();
        Assertions.assertNotNull(productosActivos);
        Assertions.assertFalse(productosActivos.isEmpty());
        Assertions.assertTrue(productosActivos.stream().allMatch(Producto::getActivo));

        //System.out.println("Productos activos encontrados: " + productosActivos.size());
        //productosActivos.forEach(p -> System.out.println(p.getNombre()));
    }
    
    
    
    
    
      @Test
    @Order(7)
    public void testEliminar(){
        System.out.println("ProductoBeanIT --------------> Eliminar");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            Producto registro = cut.findById(3L);
           
            Assertions.assertNotNull(registro);
           
            cut.em.getTransaction().begin();
            cut.delete(registro);
           
            cut.em.getTransaction().commit();   
            
            Producto eliminado = cut.findById(3L);
            Assertions.assertNull(eliminado);
            System.out.println("Registro Eliminado");
            
            

        } catch (Exception e) {
        
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            try {
                cut.em.getTransaction().rollback();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    
    
    @Test
    @Order(8)
    public void testGetProductosAgrupadosPorTipo() {
        System.out.println("ProductoBeanIT --------------> getProductosAgrupadosPorTipo");
        ProductoBean cut = new ProductoBean();
        EntityManager em = emf.createEntityManager();
        cut.em = em;

        TipoProductoBean mockTpBean = new TipoProductoBean() {
            @Override
            public List<String> findByNombreTipoProductoName() {
                return List.of("Bebida", "Comida");
            }
        };
        cut.tPBean = mockTpBean;

        try {
            em.getTransaction().begin();

            TipoProducto bebidas = new TipoProducto();
            bebidas.setNombre("Bebida");
            em.persist(bebidas);


            TipoProducto comidas = new TipoProducto();
            comidas.setNombre("Comida");
            em.persist(comidas);

            Producto bebida1 = new Producto();
            bebida1.setNombre("Refresco");
            em.persist(bebida1);

            ProductoPrecio precioBebida1 = new ProductoPrecio();
            precioBebida1.setIdProducto(bebida1);
            precioBebida1.setPrecioSugerido(BigDecimal.valueOf(0.75));
            em.persist(precioBebida1);

            Producto comida1 = new Producto();
            comida1.setNombre("Pupusa");
            em.persist(comida1);

            ProductoPrecio precioComida1 = new ProductoPrecio();
            precioComida1.setIdProducto(comida1);
            precioComida1.setPrecioSugerido(BigDecimal.valueOf(0.75));
            em.persist(precioComida1);
            
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        // Ejecutar prueba
        Map<String, List<ProductoConPrecioDTO>> resultados = cut.getProductosAgrupadosPorTipo();

        // Verificaciones
        Assertions.assertNotNull(resultados);   
        Assertions.assertFalse(resultados.isEmpty());
        Assertions.assertTrue(resultados.containsKey("Bebida"));
        Assertions.assertTrue(resultados.containsKey("Comida"));

//        System.out.println("Resultados agrupados por tipo:");
//        resultados.forEach((tipo, productos) -> {
//            System.out.println("Tipo: " + tipo);
//            productos.forEach(p -> System.out.println(" - " + p.getNombre() + 
//                                                   ": $" + p.getPrecio()));
//        });

    }
    

}
