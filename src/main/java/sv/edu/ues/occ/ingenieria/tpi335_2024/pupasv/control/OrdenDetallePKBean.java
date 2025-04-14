package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK;

@Stateless
@LocalBean
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
