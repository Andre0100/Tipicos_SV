/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import testing.BaseIntegrationAbstract;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import testing.ContainerExtension;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class ComboBeanIT extends BaseIntegrationAbstract{
    
    @Test
    @Order(1)
    public void insertar(){
        System.out.println("ComboBeanIT ----------> Insertar");
        ComboBean cut = new ComboBean();
        
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        Combo combo1 = new Combo();
        combo1.setNombre("Combo familiar");
        combo1.setActivo(true);
        combo1.setDescripcionPublica("Combo de 10 pupusas + gaseosas");
        
        Combo combo2 = new Combo();
        combo2.setNombre("Combo Rapido");
        combo2.setActivo(true);
        combo2.setDescripcionPublica("Combo 2 Hamburguesas + bebida");
        
        try {
            cut.em.getTransaction().begin();
            cut.create(combo1);
            cut.create(combo2);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(combo1.getIdCombo());
            Assertions.assertNotNull(combo2.getIdCombo());

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
        System.out.println("ComboBeanIT  -------   ContarIT");
        ComboBean cut = new ComboBean();
        
        EntityManager em = emf.createEntityManager();
       
        cut.em = em;
        
        int esperado = 2;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    @Test
    @Order(3)
    public void actualizar(){
        System.out.println("ComboBeanIT  -------   actualizar");
        ComboBean cut = new ComboBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        
        try {
            //obtener un registro
            Combo registro =  cut.findById(1L);
            registro.setNombre("Combo fin de semana");
            
            cut.em.getTransaction().begin();
            cut.update(registro);
            cut.em.getTransaction().commit();
            Assertions.assertEquals("Combo fin de semana", registro.getNombre());
            System.out.println("Registro Actualizado"+ registro.getNombre());
   
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
    @Order(4)
    public void eliminar(){
        System.out.println("OrdenBeanIT ------------> ");
        ComboBean cut = new ComboBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        try {
            //Obtener registro a eliminar
            Combo registro = cut.findById(2L);
            Assertions.assertNotNull(registro);
            
            cut.em.getTransaction().begin();
            cut.delete(registro);
            cut.em.getTransaction().commit();
            
            Combo eliminado = cut.findById(2L);
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
}
