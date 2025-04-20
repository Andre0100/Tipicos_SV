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
import java.util.List;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

@Stateless
@LocalBean
public class ComboBean extends AbstractDataPersistence<Combo> implements Serializable{

    @PersistenceContext(unitName = "PupaPU")
    
    EntityManager em;
    
    public ComboBean(){
        super(Combo.class);
    }
    
    @Override
    public EntityManager getEntityManager(){
        return em;
    }

    public List<Combo> findRange(int first, int pageSize, boolean soloActivos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long count(boolean soloActivos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
