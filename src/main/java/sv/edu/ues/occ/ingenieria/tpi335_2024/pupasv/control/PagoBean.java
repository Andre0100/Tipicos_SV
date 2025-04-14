package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;
import java.io.Serializable;

@Stateless
@LocalBean
public class PagoBean extends AbstractDataPersistence<Pago> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;

    public PagoBean() {
        super(Pago.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
