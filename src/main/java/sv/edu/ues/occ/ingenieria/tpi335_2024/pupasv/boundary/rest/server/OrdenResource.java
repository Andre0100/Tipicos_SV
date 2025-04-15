/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

/**
 *
 * @author morales
 */


@Path("orden")
public class OrdenResource implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OrdenResource.class.getName());

    @Inject
    OrdenBean ordenBean;
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRange(
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("page_Size") @DefaultValue("50") int pageSize) {
        try {
            List<Orden> lista = ordenBean.findRange(first, pageSize);
            long total = ordenBean.count();
            LOGGER.log(Level.INFO, "Consultando órdenes: desde {0}, tamaño página {1}", new Object[]{first, pageSize});
            return Response.ok(lista)
                    .header(RestResourceHeaderPattern.TOTAL_REGISTROS, total)
                    .build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar órdenes. Parámetros: first={0}, pageSize={1}", new Object[]{first, pageSize});
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, "Error interno del servidor")
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        try {
            Orden orden = ordenBean.findById(id);
            if (orden != null) {
                LOGGER.log(Level.INFO, "Orden encontrada ID: {0}", id);
                return Response.ok(orden).build();
            } else {
                LOGGER.log(Level.WARNING, "Orden no encontrada ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Orden no encontrada")
                        .build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar Orden ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Orden orden) {
        try {
            if (orden == null) {
                LOGGER.log(Level.WARNING, "Intento de crear Orden con datos nulos");
                return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_FALTANTE)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, RestResourceHeaderPattern.DETALLE_PARAMETRO_FALTANTE)
                        .build();
            }
            ordenBean.create(orden);
            LOGGER.log(Level.INFO, "Orden creada exitosamente ID: {0}", orden.getIdOrden());
            return Response.status(Response.Status.CREATED).entity(orden).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear Orden: ");
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, "Datos inválidos: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Orden orden) {
        try {
            Orden existing = ordenBean.findById(id);
            if (existing == null) {
                LOGGER.log(Level.WARNING, "Intento de actualizar Orden inexistente ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Orden no encontrada")
                        .build();
            }
            orden.setIdOrden(id); // Asegurar consistencia del ID
            ordenBean.update(orden);
            LOGGER.log(Level.INFO, "Orden actualizada ID: {0}", id);
            return Response.ok(orden).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar Orden ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, "Error en actualización: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            Orden orden = ordenBean.findById(id);
            if (orden == null) {
                LOGGER.log(Level.WARNING, "Intento de eliminar Orden inexistente ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Orden no encontrada")
                        .build();
            }
            ordenBean.delete(orden);
            LOGGER.log(Level.INFO, "Orden eliminada ID: {0}", id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Orden ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, "Error interno: " + e.getMessage())
                    .build();
        }
    }
}