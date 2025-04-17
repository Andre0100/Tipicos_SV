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
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.BaseIntegrationAbstract;
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
    @Order(1)
    public void testContar(){
        System.out.println("ContarIT");
        OrdenBean cut = new OrdenBean();
        
        EntityManager em = emf.createEntityManager();
       
        cut.em = em;
        
        int esperado = 3;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    @Test
    @Order(2)
    public void insertar(){
        System.out.println("InsertarIT");
        
        OrdenBean cut = new OrdenBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        Orden registro = new Orden();
        registro.setFecha(new Date()); // Fecha actual
        registro.setSucursal("A001"); // Código de sucursal
        registro.setAnulada(false); // No anulada
        
        try {
            cut.em.getTransaction().begin();
            cut.create(registro);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(registro.getIdOrden());
            System.out.println("Registro Creado con ID "+registro.getIdOrden());
            
            
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
