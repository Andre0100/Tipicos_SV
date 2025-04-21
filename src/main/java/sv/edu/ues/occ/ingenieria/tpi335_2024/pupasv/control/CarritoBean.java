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

@Named
@SessionScoped
public class CarritoBean implements Serializable {

    private final List<CarritoItemDTO> itemsCarrito = new ArrayList<>();

    @Inject
    ProductoPrecioBean productoPrecioBean;

    public void agregarItem(List<CarritoItemDTO> items) {
        
        for(CarritoItemDTO item : items ){
            var productoPrecio = productoPrecioBean.findById(item.getIdProductoPrecio());
            if (productoPrecio == null) {
                throw new IllegalArgumentException("El producto no ha sido encontrado.");
            }
            item.setNombreProducto(productoPrecio.getIdProducto().getNombre());
            item.setPrecio(productoPrecio.getPrecioSugerido());
            itemsCarrito.add(item);
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
}
