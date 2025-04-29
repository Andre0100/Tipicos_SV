package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.OrdenDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

@Stateless
@LocalBean
public class OrdenBean extends AbstractDataPersistence<Orden> implements Serializable{

    @PersistenceContext(unitName = "PupaPU")
    public EntityManager em;

    @Inject
    ProductoPrecioBean productoPrecioBean;
    @Inject
    OrdenDetalleBean ordenDetalleBean;

    public OrdenBean() {
        super(Orden.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em; 
    }

    public OrdenDTO crearOrdenCarrito(List<CarritoItemDTO> itemsCarrito, String sucursal){
        if(itemsCarrito == null || itemsCarrito.isEmpty()){

            throw new IllegalArgumentException("El carrito está vacío.");
        }
        Orden orden = new Orden();
        orden.setSucursal(sucursal);
        orden.setFecha(new Date());
        orden.setAnulada(false);

        this.create(orden);
        getEntityManager().flush(); // Forzar sincronización con la base de datos ya que no se esta usando un bean


        if(orden.getIdOrden() == null){
            throw new IllegalArgumentException("No se pudo crear el ID de la orden.");
        }

        List<OrdenDetalle> ordenDetalles = new ArrayList<>();

        for(CarritoItemDTO item : itemsCarrito){
            ProductoPrecio productoPrecio = productoPrecioBean.findById(item.getIdProductoPrecio());
            /*if(productoPrecio == null){
                throw new IllegalArgumentException("El ProductoPrecio con el ID" + item.getIdProductoPrecio() + "no existe.");
            }*/
            OrdenDetallePK ordenDetallePK = new OrdenDetallePK(orden.getIdOrden(), item.getIdProductoPrecio());
            OrdenDetalle ordenDetalle = new OrdenDetalle();
            ordenDetalle.setOrdenDetallePK(ordenDetallePK);
            ordenDetalle.setOrden(orden);
            ordenDetalle.setProductoPrecio(productoPrecio);
            ordenDetalle.setCantidad(item.getCantidad());
            ordenDetalle.setPrecio(item.getPrecio());

            ordenDetalles.add(ordenDetalle);
            ordenDetalleBean.create(ordenDetalle);
        }

        orden.setOrdenDetalleList(ordenDetalles);
        
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setIdOrden(orden.getIdOrden());
        ordenDTO.setSucursal(orden.getSucursal());
        ordenDTO.setFecha(orden.getFecha());
        ordenDTO.setAnulada(orden.getAnulada());
       
        
        return ordenDTO;
    }
}
