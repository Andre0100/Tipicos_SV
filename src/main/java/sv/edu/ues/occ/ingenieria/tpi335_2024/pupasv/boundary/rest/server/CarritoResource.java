package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

@Path("carrito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarritoResource {

    private static final Logger LOGGER = Logger.getLogger(OrdenResource.class.getName());

    @Inject
    CarritoBean carritoBean;

    @POST
    public Response agregarItems(List<CarritoItemDTO> items) {
        try {
            // Validar que la lista de items no sea nula o vacía
            if (items == null || items.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("La lista de items no puede estar vacía o ser nula")
                        .build();
            }

            // Validar cada item individualmente
            for (CarritoItemDTO item : items) {
                if (item == null || item.getIdProductoPrecio()== null || item.getCantidad() <= 0) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Cada item debe tener un producto válido y una cantidad positiva")
                            .build();
                }
            }

            carritoBean.agregarItem(items);
            return Response.ok().build();

        } catch (IllegalArgumentException e) {
            // Captura excepciones de validación de negocio
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            return Response.serverError()
                    .entity("Ocurrió un error al procesar la solicitud: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response obtenerCarrito() {
        try {
            CarritoDTO carrito = new CarritoDTO();
            carrito.setItemsCarrito(carritoBean.obtenerItems());
            carrito.setTotal(carritoBean.calcularTotal());

            // Podría validarse si el carrito está vacío para devolver un código diferente
            if (carrito.getItemsCarrito() == null || carrito.getItemsCarrito().isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("El carrito está vacío")
                        .build();
            }

            return Response.ok(carrito).build();

        } catch (Exception e) {
            // Captura cualquier excepción inesperada
            return Response.serverError()
                    .entity("Ocurrió un error al obtener el carrito: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{idProductoPrecio}")
    public Response eliminarItem(@PathParam("idProductoPrecio") Long idProductoPrecio) {
        carritoBean.eliminarItem(idProductoPrecio);
        return Response.ok().build();
    }

}
