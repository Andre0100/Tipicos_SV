/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import testing.BaseIntegrationAbstract;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import testing.ContainerExtension;

/**
 *
 * @author morales
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ContainerExtension.class)
public class OrdenBeanIT extends BaseIntegrationAbstract{
    
                
    @Test
    @Order(2)
    public void testContar(){
        System.out.println("OrdenBean  -------   ContarIT");
        OrdenBean cut = new OrdenBean();
        
        EntityManager em = emf.createEntityManager();
       
        cut.em = em;
        
        int esperado = 3;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    @Test
    @Order(1)
    public void insertar(){
        System.out.println("OrdenBean  -------   Insertar");
        
        OrdenBean cut = new OrdenBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        Orden registro = new Orden();
        registro.setFecha(new Date()); 
        registro.setFecha(new Date()); 
        registro.setSucursal("A001"); 
        registro.setAnulada(false); 
        registro.setAnulada(false); 
        
        Orden registro2 = new Orden();
        registro2.setFecha(new Date()); 
        registro2.setSucursal("ZARZA");
        registro2.setAnulada(false); 
        
        Orden registro3 = new Orden();
        registro3.setFecha(new Date()); 
        registro3.setSucursal("ZARZA"); 
        registro3.setAnulada(false); 
        try {
            cut.em.getTransaction().begin();
            cut.create(registro);
            cut.create(registro2);
            cut.create(registro3);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(registro.getIdOrden());
            System.out.println("Registro Creado con ID "+registro.getIdOrden());
            System.out.println("Registro Creado con ID "+registro2.getIdOrden());
            System.out.println("Registro Creado con ID "+registro3.getIdOrden());
            
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
    @Order(3)
    public void actualizar(){
        System.out.println("OrdenBean  -------   actualizar");
        OrdenBean cut = new OrdenBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        
        try {
            //obtener un registro
            Orden registro =  cut.findById(3L);
            registro.setAnulada(true);
            
            cut.em.getTransaction().begin();
            cut.update(registro);
            cut.em.getTransaction().commit();
            Assertions.assertEquals(true, registro.getAnulada());
            System.out.println("Registro Actualizado"+ registro.getAnulada());
   
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
        System.out.println("OrdenBean  -------   eliminar");
        OrdenBean cut = new OrdenBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        try {
            //obtener un registro
            Orden registro =  cut.findById(3L);
            Assertions.assertNotNull(registro);
            
            cut.em.getTransaction().begin();
            cut.delete(registro);
            cut.em.getTransaction().commit();
            
            Orden eliminado =  cut.findById(3L);
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
