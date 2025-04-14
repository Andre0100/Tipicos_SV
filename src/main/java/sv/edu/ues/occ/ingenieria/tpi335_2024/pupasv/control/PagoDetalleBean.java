package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.PagoDetalle;
import java.io.Serializable;

@Stateless
@LocalBean
public class PagoDetalleBean extends AbstractDataPersistence<PagoDetalle> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    private EntityManager em;

    public PagoDetalleBean() {
        super(PagoDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
