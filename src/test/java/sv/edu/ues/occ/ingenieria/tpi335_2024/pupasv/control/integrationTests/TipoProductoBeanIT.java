/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.integrationTests;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;
import testing.BaseIntegrationAbstract;
import testing.ContainerExtension;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class TipoProductoBeanIT extends BaseIntegrationAbstract{
    
    @Test
    @Order(1)
    public void testInsertar(){
        System.out.println("TipoProductoBeanIT ----------> Insertar");
        
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        TipoProducto registro1 = new TipoProducto();
        registro1.setNombre("bebida");
        registro1.setActivo(true);
        TipoProducto registro2 = new TipoProducto();
        registro2.setNombre("comida");
        registro2.setActivo(true);
        TipoProducto registro3 = new TipoProducto();
        registro3.setNombre("tipicos");
        registro3.setActivo(true);
        
        try {
            cut.em.getTransaction().begin();
            cut.create(registro1);
            cut.create(registro2);
            cut.create(registro3);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(registro1.getIdTipoProducto());
            Assertions.assertNotNull(registro2.getIdTipoProducto());
            Assertions.assertNotNull(registro3.getIdTipoProducto());
            System.out.println("Regsitro creado"+ registro1.getIdTipoProducto());
            System.out.println("Regsitro creado"+ registro2.getIdTipoProducto());
            System.out.println("Regsitro creado"+ registro3.getIdTipoProducto());
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
    public void testContar() {
        System.out.println("TipoProductoBeanIT ------------> Contar");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        int esperado = 4;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    
    @Test
    @Order(3)
    public void testFindByNombreTipoProductoName() {
        System.out.println("TipoProductoBeanIT --------------> findByNombreTipoProductoName");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();

        cut.em = em;

        List<String> nombres = cut.findByNombreTipoProductoName();
        
        Assertions.assertNotNull(nombres);
        Assertions.assertFalse(nombres.isEmpty());

        Assertions.assertTrue(nombres.contains("bebida"));
        Assertions.assertTrue(nombres.contains("comida"));
        Assertions.assertTrue(nombres.contains("tipicos"));

    }
    
    
    @Test
    @Order(4)
    public void testBuscarPorId() {
        System.out.println("TipoProductoBeanIT -------------> Buscar por Id");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            TipoProducto registro = cut.findById(5);
            Assertions.assertNotNull(registro);
            Assertions.assertEquals(5, registro.getIdTipoProducto());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    @Test
    @Order(5)
    public void testActualizar() {
        System.out.println("TipoProductoBeanIT -------------> Actualizar");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            TipoProducto registro = cut.findById(5);
            Assertions.assertNotNull(registro);
            registro.setNombre("Platos principales");
            registro.setObservaciones("Incluye platos fuertes");
            
            cut.em.getTransaction().begin();
            cut.update(registro);
            cut.em.getTransaction().commit();
            
            Assertions.assertEquals("Platos principales", registro.getNombre());
            System.out.println("Registro Actualizado: ID " + registro.getIdTipoProducto());
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
    @Order(6)
    public void testEliminar() {
        System.out.println("TipoProductoBeanIT --------------> Eliminar");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            TipoProducto registro = cut.findById(5);
            Assertions.assertNotNull(registro);
            
            cut.em.getTransaction().begin();
            cut.delete(registro);
            cut.em.getTransaction().commit();
            
            TipoProducto eliminado = cut.findById(5);
            Assertions.assertNull(eliminado);
            System.out.println("Registro Eliminado: ID 3");
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
    @Order(7)
    public void testListarTodos() {
        System.out.println("TipoProductoBeanIT --------------> Listar Todos");
        TipoProductoBean cut = new TipoProductoBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        List<TipoProducto> resultados = cut.findRange(0, 10);
        Assertions.assertNotNull(resultados);
        Assertions.assertFalse(resultados.isEmpty());
        
        resultados.forEach(tp -> System.out.println("ID: " + tp.getIdTipoProducto() + 
                                               ", Nombre: " + tp.getNombre()));
    }
}
