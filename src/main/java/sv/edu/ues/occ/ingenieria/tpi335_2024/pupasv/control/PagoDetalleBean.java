package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.PagoDetalle;
import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class PagoDetalleBean extends AbstractDataPersistence<PagoDetalle> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;

    public PagoDetalleBean() {
        super(PagoDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    //Setters para pruebas
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
