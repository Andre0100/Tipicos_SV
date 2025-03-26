/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author morales
 */
@Embeddable
public class ProductoDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_tipo_producto")
    private int idTipoProducto;
    @Basic(optional = false)
    @Column(name = "id_producto")
    private long idProducto;

    public ProductoDetallePK() {
    }

    public ProductoDetallePK(int idTipoProducto, long idProducto) {
        this.idTipoProducto = idTipoProducto;
        this.idProducto = idProducto;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoProducto;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoDetallePK)) {
            return false;
        }
        ProductoDetallePK other = (ProductoDetallePK) object;
        if (this.idTipoProducto != other.idTipoProducto) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoDetallePK[ idTipoProducto=" + idTipoProducto + ", idProducto=" + idProducto + " ]";
    }
    
}
