/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoDetalleBean;

/**
 *
 * @author morales
 */
@Path("/productoDetalle")
public class ProductoDetalleResource implements Serializable{

    @Inject
    private ProductoDetalleBean pDBean;
    
     @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response ProductoList(
            @QueryParam("first")
            @DefaultValue("0")
            int first,
            @QueryParam("max")
            @DefaultValue("20")
            int max
            ){
        
        try {
            if(first >= 0 && max >= 0 && max <= 50){
                List encontrados = pDBean.findRange(first, max);
                int total = pDBean.count();
                Response.ResponseBuilder builder = Response.ok(encontrados).
                        header("Total-Productos", total).
                        type(MediaType.APPLICATION_JSON);
                
                return builder.build();
            }else{
                return Response.status(422).header("wrong parameter, first:", first+",max: "+max  ).header("wrong parameter : max","s").build();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
    
}
