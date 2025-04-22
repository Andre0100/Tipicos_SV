/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.BaseIntegrationAbstract;
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
        System.out.println("ComboBean ----------> Insertar");
        ComboBean cut = new ComboBean();
        
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        
        try {
            
        } catch (Exception e) {
        }
         
    }
    
}
