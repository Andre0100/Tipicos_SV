/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author andrea
 */
public class ComboDTO implements Serializable{
    
    
    
    
    private Long idCombo;

    @NotBlank(message = "El nombre del combo es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "Los detalles del combo son obligatorios")
    @Size(min = 1, message = "El combo debe tener al menos un producto")
    private List<ComboDetalleDTO> detalles;

    // Constructores
    public ComboDTO() {
    }

    public ComboDTO(Long idCombo, String nombre, String descripcion, 
                   BigDecimal precio, Boolean activo, List<ComboDetalleDTO> detalles) {
        this.idCombo = idCombo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.activo = activo;
        this.detalles = detalles;
    }

    // Getters y Setters
    public Long getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Long idCombo) {
        this.idCombo = idCombo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<ComboDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ComboDetalleDTO> detalles) {
        this.detalles = detalles;
    }

    // Método toString() para logging
    @Override
    public String toString() {
        return "ComboDTO{" + "idCombo=" + idCombo + ", nombre=" + nombre + 
               ", precio=" + precio + ", activo=" + activo + '}';
    }
}


