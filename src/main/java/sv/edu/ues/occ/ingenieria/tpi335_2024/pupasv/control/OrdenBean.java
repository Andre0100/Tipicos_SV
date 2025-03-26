/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

/**
 *
 * @author morales
 */
@Stateless
@LocalBean
public class OrdenBean extends AbstractDataPersistence<Orden> implements Serializable{

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;
    @Override
    public EntityManager getEntityManager() {
        return em; 
    }
    
    public OrdenBean(){
        super(Orden.class);
    }
    
}
