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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import jakarta.validation.Valid; 
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ComboBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ComboProductosDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

/**
 *
 * @author andrea
 */

@Path("combo")
public class ComboResource implements Serializable {

    @Inject
    private ComboBean CBean;

    private static final Logger LOG = Logger.getLogger(ComboResource.class.getName());

    // Endpoint para obtener productos agrupados por tipo con sus precios
    @Path("/por-combo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductosAgrupadosPorCombo() {
        try {
            Map<String, List<ComboProductosDTO>> productosPorCombo = CBean.getProductosAgrupadosPorCombo();
            return Response.ok(productosPorCombo).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al obtener productos por tipo", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error al obtener productos: " + e.getMessage())
                         .build();
        }
    }
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
        @QueryParam("first") @DefaultValue("0") int first,
        @QueryParam("max")   @DefaultValue("30") int max,
        @QueryParam("activos") @DefaultValue("true") boolean activos
    ) {
        try {
            if (first < 0 || max <= 0 || max > 50) {
                return Response.status(422)
                    .header("error", "first >= 0, 1 <= max <= 50")
                    .build();
            }
            List<Combo> encontrados = CBean.findRange(first, max, activos);
            long total = CBean.count(activos);
            return Response.ok(encontrados)
                .header("Total-Combos", total)
                .build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(422).header("error", "ID nulo").build();
        }
        try {
            Combo combo = CBean.findByIdWithDetalles(id.intValue());
            if (combo == null) {
                return Response.status(404).header("error", "Combo no encontrado").build();
            }
            return Response.ok(combo).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Combo combo, @Context UriInfo uriInfo) {
        if (combo == null || combo.getIdCombo() != null) {
            return Response.status(422).header("error", "ID debe ser null al crear").build();
        }
        try {
            CBean.create(combo);
            Long newId = combo.getIdCombo();
            UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(newId.toString());
            return Response.created(ub.build()).build();
        } catch (IllegalArgumentException ia) {
            return Response.status(400).entity(ia.getMessage()).build();
        } catch (IllegalStateException ie) {
            return Response.status(409).entity(ie.getMessage()).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Combo combo) {
        if (combo == null || combo.getIdCombo() == null) {
            return Response.status(422).header("error", "ID requerido para actualizar").build();
        }
        try {
            CBean.update(combo);
            return Response.ok().build();
        } catch (IllegalArgumentException ia) {
            return Response.status(400).entity(ia.getMessage()).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(422).header("error", "ID nulo").build();
        }
        try {
            CBean.delete(CBean.findByIdWithDetalles(id.intValue()));
            return Response.noContent().build();
        } catch (IllegalArgumentException ia) {
            return Response.status(400).entity(ia.getMessage()).build();
        } catch (IllegalStateException ie) {
            // p.ej. no eliminar si tiene reservas asociadas
            return Response.status(409).entity(ie.getMessage()).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }      
}