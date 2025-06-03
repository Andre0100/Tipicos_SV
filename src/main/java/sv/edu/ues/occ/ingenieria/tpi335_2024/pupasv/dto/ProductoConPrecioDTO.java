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
public class ProductoConPrecioDTO implements Serializable{
    private Long idProductoPrecio;
    private String nombre;
    private String observaciones;
    private BigDecimal precio;
    private Date fechaValidezPrecio;
    
//    public ProductoConPrecioDTO(Producto producto, ProductoPrecio precio){
//        this.idProductoPrecio = precio.getIdProductoPrecio();
//        this.nombre = producto.getNombre();
//        this.observaciones = producto.getObservaciones();
//        if (precio != null) {
//            this.precio = precio.getPrecioSugerido();
//            this.fechaValidezPrecio = precio.getFechaHasta();
//        }
//    }
    
    
    public ProductoConPrecioDTO(Producto producto, ProductoPrecio precio){
    if (precio != null) {
        this.idProductoPrecio = precio.getIdProductoPrecio();
        this.precio = precio.getPrecioSugerido();
        this.fechaValidezPrecio = precio.getFechaHasta();
    }

    if (producto != null) {
        this.nombre = producto.getNombre();
        this.observaciones = producto.getObservaciones();
    }
}

    
    
    
    public ProductoConPrecioDTO() {
        // Constructor vacío necesario para Jackson
    }

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

    public Date getFechaValidezPrecio() {
        return fechaValidezPrecio;
    }
    
    
}
