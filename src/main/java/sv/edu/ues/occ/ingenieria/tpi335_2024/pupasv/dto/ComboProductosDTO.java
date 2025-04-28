/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

/**
 *
 * @author morales
 */
public class ComboProductosDTO implements Serializable {

    private Long idProductoPrecio;
    private String nombre;
    private String observaciones;
    private BigDecimal precio;
    private Integer cantidad;
    private Date fechaValidezPrecio;

    public ComboProductosDTO(Producto producto, ProductoPrecio productoPrecio, Integer cantidad) {
        this.idProductoPrecio = productoPrecio.getIdProductoPrecio();
        this.nombre = producto.getNombre();
        this.observaciones = producto.getObservaciones();
        this.cantidad = cantidad;
        if (productoPrecio != null) {
            this.precio = productoPrecio.getPrecioSugerido();
            this.fechaValidezPrecio = productoPrecio.getFechaHasta();
        }
    } 
    
//    public ComboProductosDTO(Producto producto, ProductoPrecio productoPrecio, Integer cantidad) {
//    this.nombre = producto.getNombre();
//    this.observaciones = producto.getObservaciones();
//    this.cantidad = cantidad;
//    
//    // Verificar si productoPrecio es null antes de acceder a sus métodos
//    if (productoPrecio != null) {
//        this.idProductoPrecio = productoPrecio.getIdProductoPrecio();
//        this.precio = productoPrecio.getPrecioSugerido();
//        this.fechaValidezPrecio = productoPrecio.getFechaHasta();
//    } else {
//        // Si productoPrecio es null, asignar valores predeterminados
//        this.idProductoPrecio = null;
//        this.precio = BigDecimal.ZERO; // O el valor predeterminado que prefieras
//        this.fechaValidezPrecio = null; // O el valor predeterminado que prefieras
//    }
    
    public ComboProductosDTO() {
    } 
    
    //Getters Getters
    public Long getIdProductoPrecio() {
        return idProductoPrecio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Date getFechaValidezPrecio() {
        return fechaValidezPrecio;
    }
    
     // Setters
    public void setIdProductoPrecio(Long idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaValidezPrecio(Date fechaValidezPrecio) {
        this.fechaValidezPrecio = fechaValidezPrecio;
    }
    
}
