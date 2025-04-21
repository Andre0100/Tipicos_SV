package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;

@Path("/carrito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarritoResource {

    @Inject
    CarritoBean carritoBean;

      @POST
    public Response agregarItems(List<CarritoItemDTO> items) {
        carritoBean.agregarItem(items);
        return Response.ok().build();
    }

    @GET
    public Response obtenerCarrito() {
        CarritoDTO carrito = new CarritoDTO();
        carrito.setItemsCarrito(carritoBean.obtenerItems());
        carrito.setTotal(carritoBean.calcularTotal());
        return Response.ok(carrito).build();
    }

    @DELETE
    @Path("/{idProductoPrecio}")
    public Response eliminarItem(@PathParam("idProductoPrecio") Long idProductoPrecio) {
        carritoBean.eliminarItem(idProductoPrecio);
        return Response.ok().build();
    }
}
