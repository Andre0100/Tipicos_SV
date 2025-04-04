/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoDetalle;

/**
 *
 * @author morales
 */
public class ProductoDetalleBean extends AbstractDataPersistence<ProductoDetalle> implements Serializable{

    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;
    
  
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    
    public ProductoDetalleBean(){
        super(ProductoDetalle.class);
    }
    
}
