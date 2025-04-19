/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;

/**
 *
 * @author morales
 */
@Path("/producto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ProductoResource.class.getName());

       
    @Inject
    private ProductoBean PBean;
    
    
    // Endpoint para obtener productos agrupados por tipo con sus precios
    @Path("/por-tipo")
    @GET
    public Response getProductosAgrupadosPorTipo() {
        try {
            Map<String, List<ProductoConPrecioDTO>> productosPorTipo = PBean.getProductosAgrupadosPorTipo();
            return Response.ok(productosPorTipo).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al obtener productos por tipo", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error al obtener productos: " + e.getMessage())
                         .build();
        }
    }
    
   
    @GET
    public Response ProductoList(
            @QueryParam("first")
            @DefaultValue("0")
            int first,
            @QueryParam("max")
            @DefaultValue("30")
            int max
            ){
        
        try {
            if(first >= 0 && max >= 0 && max <= 50){
                List encontrados = PBean.findRange(first, max);
                int total = PBean.count();
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
    
    
    
    @Path("/{id}/detalle}")
    @GET
    public Response findById(@PathParam("id") Long id) {
        if (id != null) {
            try {
                Producto encontrado= PBean.findById(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado);
                    return builder.build();
                }
                return Response.status(404).header("not-found-id", id).build();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("wrong-parameter : id", id).build();
    }

       
    @POST
    public Response create(Producto producto, @Context UriInfo uriInfo){
        if(producto != null && producto.getIdProducto() == null){
            try {
                PBean.create(producto);
                
                if(producto.getIdProducto() != null){
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(producto.getIdProducto()));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("process-error","Record couldnt be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
         return Response.status(500).header("Wrong-parameter", producto).build();
    }
    
    @PUT
    public Response update(Producto producto, @Context UriInfo uriInfo){
        if (producto != null && producto.getIdProducto() != null ) {
            try {
                PBean.update(producto);
                if (producto.getIdProducto() !=null) {

                    return Response.status(200).build();
                }
                return Response.status(500).header("process-error","Record couldnt be updated").build();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(500).header("Wrong-parameter", producto).build();
    }
    
    @Path("/activos")
    @GET
    public Response ProductosActivos(
            @QueryParam("first")
            @DefaultValue("0")
            int first,
            @QueryParam("max")
            @DefaultValue("30")
            int max){
        try {
            if(first >= 0 && max >= 0 && max <= 50){
                List encontrados = PBean.findByActivo();
                Response.ResponseBuilder builder = Response.ok(encontrados).
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

