package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.CarritoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.PagoBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.PagoRequestDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;

@Path("pago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagoResource {

    @Inject
    PagoBean pagoBean;
    @Inject
    CarritoBean carritoBean;

    @POST
    public Response realizarPago(PagoRequestDTO pagoRequest){
        try {
            Pago pago = pagoBean.registrarPago(
                    pagoRequest.getIdOrden(),
                    pagoRequest.getMetodoPago(),
                    pagoRequest.getReferencia(),
                    pagoRequest.getMonto(),
                    pagoRequest.getObservaciones()
            );  
            //limpiar carrito
            carritoBean.limpiarCarrito();
            return Response.status(Response.Status.CREATED).entity(pago).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error interno: " + e.getMessage()).build();
        }
    }
}
