package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;

@Stateless
@LocalBean
public class OrdenDetalleBean extends AbstractDataPersistence<OrdenDetalle> implements Serializable{
    
    @PersistenceContext(unitName = "PupaPU")
    
    EntityManager em;
    
    public OrdenDetalleBean(){
        super(OrdenDetalle.class);
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
}
