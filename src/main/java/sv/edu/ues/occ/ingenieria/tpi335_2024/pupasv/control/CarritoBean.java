package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.CarritoItemDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CarritoBean implements Serializable {

    private  List<CarritoItemDTO> itemsCarrito = new ArrayList<>();

    @Inject
    ProductoPrecioBean productoPrecioBean;

    public void agregarItem(List<CarritoItemDTO> items) {
        
        for(CarritoItemDTO itemNuevo : items ){
            //busca el productoprecio
            
            var productoPrecio = productoPrecioBean.findById(itemNuevo.getIdProductoPrecio());
            if (productoPrecio == null) {
                throw new IllegalArgumentException("El producto no ha sido encontrado.");
            }
            
            //Verificar si ya existe en el carrito
            var itemExiste = itemsCarrito.stream()
                    .filter(item -> item.getIdProductoPrecio().equals(itemNuevo.getIdProductoPrecio()))
                    .findFirst();
            
            if(itemExiste.isPresent()){
                //Si existe sumar las cantidades
               CarritoItemDTO item = itemExiste.get();
               item.setCantidad(item.getCantidad() + itemNuevo.getCantidad());
            }else{
                itemNuevo.setNombreProducto(productoPrecio.getIdProducto().getNombre());
                itemNuevo.setPrecio(productoPrecio.getPrecioSugerido());
                itemNuevo.setObservaciones(itemNuevo.getObservaciones());
                itemNuevo.setCantidad(itemNuevo.getCantidad());
                itemsCarrito.add(itemNuevo);
                System.out.println("Item agregado"+ itemNuevo);
            }
            
           
        }
    
    }

    public void eliminarItem(Long idProductoPrecio) {
        itemsCarrito.removeIf(i -> i.getIdProductoPrecio().equals(idProductoPrecio));
    }
    public List<CarritoItemDTO> obtenerItems() {
        return itemsCarrito;
    }

    public void limpiarCarrito() {
        itemsCarrito.clear();
    }

    public BigDecimal calcularTotal() {
        return itemsCarrito.stream()
                .map(i -> i.getPrecio().multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    //metodo para actualizar items
    public void actualizarItem(Long idProductoPrecio, int nuevaCantidad, String nuevaObservaciones){
        try {
            itemsCarrito.stream()
                    .filter(item -> item.getIdProductoPrecio().equals(idProductoPrecio))
                    .findFirst()
                    .ifPresentOrElse(
                        item -> {
                            item.setCantidad(nuevaCantidad);
                            item.setObservaciones(nuevaObservaciones);
                        },
                        () -> {
                            throw new IllegalArgumentException("No se encontró el producto con ID: " +idProductoPrecio+
                                    "en el carrito");
                        }
                    );     
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
