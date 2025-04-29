/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author morales
 */
public class OrdenDTO implements Serializable{
    private Long idOrden;
    private Date fecha;
    private String sucursal;
    private Boolean anulada;

    public OrdenDTO(Long idOrden, Date fecha, String sucursal, Boolean anulada) {
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.sucursal = sucursal;
        this.anulada = anulada;
    }

    public OrdenDTO() {
    }

    
    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }
    
    
}
