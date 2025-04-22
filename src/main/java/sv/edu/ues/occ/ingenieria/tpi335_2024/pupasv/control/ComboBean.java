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
import java.util.Map;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

@Stateless
@LocalBean
public class ComboBean extends AbstractDataPersistence<Combo> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    public EntityManager em;

    public ComboBean() {
        super(Combo.class);
    }

    // setter temporal solo para pruebas
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Combo> findRange(int first, int pageSize, boolean soloActivos) {
        if (first < 0 || pageSize < 0) { // 👈 Validación crítica
            throw new IllegalArgumentException("first y pageSize no pueden ser negativos");
        }

        String queryName = soloActivos ? "Combo.findActivos" : "Combo.findAll";
        return em.createNamedQuery(queryName, Combo.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long count(boolean soloActivos) {
        String queryName = soloActivos ? "Combo.countActivos" : "Combo.countAll";
        return em.createNamedQuery(queryName, Long.class)
                .getSingleResult();
    }

    public Combo findByIdWithDetalles(Integer idCombo) {
        return em.createNamedQuery("Combo.findWithDetalles", Combo.class)
                .setParameter("idCombo", idCombo)
                .getSingleResult();
    }
    
    
    
} 

