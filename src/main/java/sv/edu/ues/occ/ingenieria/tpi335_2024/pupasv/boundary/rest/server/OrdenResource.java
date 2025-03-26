/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.io.Serializable;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

/**
 *
 * @author morales
 */


@Path("orden")
public class OrdenResource extends AbstractDataSource<Orden> implements Serializable{

    
    @Inject
    OrdenBean ordenBean;
    
    
    @Override
    public AbstractDataPersistence<Orden> getBean() {
        return ordenBean;
    }

    @Override
    public Integer getId(Orden registro) {
        return 0;
    }

    @Override
    public String getClassName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
