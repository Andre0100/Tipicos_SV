package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;
import java.io.Serializable;

@Stateless
@LocalBean
public class ProductoPrecioBean extends AbstractDataPersistence<ProductoPrecio> implements Serializable {

    @PersistenceContext(unitName = "PupaPU")
    private EntityManager em;

    public ProductoPrecioBean() {
        super(ProductoPrecio.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
