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
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ComboBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

/**
 *
 * @author andrea
 */

@Path("combo")
public class ComboResource implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ComboResource.class.getName());

    @Inject
    ComboBean comboBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRange(
            @QueryParam("first") @DefaultValue("0") int first,
            @QueryParam("page_Size") @DefaultValue("50") int pageSize,
            @QueryParam("activos") @DefaultValue("true") boolean soloActivos) {
        try {
            List<Combo> lista = comboBean.findRange(first, pageSize, soloActivos);
            long total = comboBean.count(soloActivos);
            LOGGER.log(Level.INFO, "Consultando combos: desde {0}, tamaño página {1}, solo activos: {2}", 
                    new Object[]{first, pageSize, soloActivos});
            return Response.ok(lista)
                    .header(RestResourceHeaderPattern.TOTAL_REGISTROS, total)
                    .build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar combos. Parámetros: first={0}, pageSize={1}, soloActivos={2}", 
                    new Object[]{first, pageSize, soloActivos});
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
            Combo combo = comboBean.findById(id);
            if (combo != null) {
                LOGGER.log(Level.INFO, "Combo encontrado ID: {0}", id);
                return Response.ok(combo).build();
            } else {
                LOGGER.log(Level.WARNING, "Combo no encontrado ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Combo no encontrado")
                        .build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar Combo ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{id}/detalles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findDetallesByComboId(@PathParam("id") Long id) {
        try {
            Combo combo = comboBean.findById(id);
            if (combo != null) {
                LOGGER.log(Level.INFO, "Consultando detalles del combo ID: {0}", id);
                return Response.ok(combo.getComboDetalleList()).build();
            } else {
                LOGGER.log(Level.WARNING, "Combo no encontrado al buscar detalles ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Combo no encontrado")
                        .build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar detalles del Combo ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Combo combo) {
        try {
            if (combo == null) {
                LOGGER.log(Level.WARNING, "Intento de crear Combo con datos nulos");
                return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_FALTANTE)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, RestResourceHeaderPattern.DETALLE_PARAMETRO_FALTANTE)
                        .build();
            }
            comboBean.create(combo);
            LOGGER.log(Level.INFO, "Combo creado exitosamente ID: {0}", combo.getIdCombo());
            return Response.status(Response.Status.CREATED).entity(combo).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear Combo: ");
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
    public Response update(@PathParam("id") Long id, Combo combo) {
        try {
            Combo existing = comboBean.findById(id);
            if (existing == null) {
                LOGGER.log(Level.WARNING, "Intento de actualizar Combo inexistente ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Combo no encontrado")
                        .build();
            }
            combo.setIdCombo(id); // Asegurar consistencia del ID
            comboBean.update(combo);
            LOGGER.log(Level.INFO, "Combo actualizado ID: {0}", id);
            return Response.ok(combo).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar Combo ID: {0}", id);
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
            Combo combo = comboBean.findById(id);
            if (combo == null) {
                LOGGER.log(Level.WARNING, "Intento de eliminar Combo inexistente ID: {0}", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .header(RestResourceHeaderPattern.DETALLE_ERROR, "Combo no encontrado")
                        .build();
            }
            comboBean.delete(combo);
            LOGGER.log(Level.INFO, "Combo eliminado ID: {0}", id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Combo ID: {0}", id);
            LOGGER.log(Level.SEVERE, "Detalle del error: ", e);
            return Response.serverError()
                    .header(RestResourceHeaderPattern.DETALLE_ERROR, "Error interno: " + e.getMessage())
                    .build();
        }
    }
}
