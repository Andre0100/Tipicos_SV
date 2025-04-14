package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle;
import java.io.Serializable;

@Stateless
@LocalBean
public class ComboDetalleBean extends AbstractDataPersistence<ComboDetalle> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;

    public ComboDetalleBean() {
        super(ComboDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
