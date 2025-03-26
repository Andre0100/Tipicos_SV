/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK;

/**
 *
 * @author morales
 */
public class OrdenDetallePKBean extends AbstractDataPersistence<OrdenDetallePK> implements Serializable{

    @PersistenceContext(unitName = "pupaPU")
    EntityManager  em;
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public OrdenDetallePKBean(){
        super(OrdenDetallePK.class);
    }
    
}
