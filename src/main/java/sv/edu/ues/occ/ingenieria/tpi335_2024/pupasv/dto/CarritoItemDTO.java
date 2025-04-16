package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.math.BigDecimal;

public class CarritoItemDTO {
    private Long idProductoPrecio;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precio;
    private String observaciones;

    //Getters y Setters
    public Long getIdProductoPrecio() {
        return idProductoPrecio;
    }

    public void setIdProductoPrecio(Long idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
