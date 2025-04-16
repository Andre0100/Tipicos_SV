package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarritoDTO {
    private List<CarritoItemDTO> itemsCarrito = new ArrayList<>();
    private BigDecimal total;

    //Getters y Setters
    public List<CarritoItemDTO> getItemsCarrito() {
        return itemsCarrito;
    }

    public void setItemsCarrito(List<CarritoItemDTO> itemsCarrito) {
        this.itemsCarrito = itemsCarrito;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
