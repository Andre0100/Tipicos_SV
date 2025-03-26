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
public class ComboDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_combo")
    private long idCombo;
    @Basic(optional = false)
    @Column(name = "id_producto")
    private long idProducto;

    public ComboDetallePK() {
    }

    public ComboDetallePK(long idCombo, long idProducto) {
        this.idCombo = idCombo;
        this.idProducto = idProducto;
    }

    public long getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(long idCombo) {
        this.idCombo = idCombo;
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
        hash += (int) idCombo;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboDetallePK)) {
            return false;
        }
        ComboDetallePK other = (ComboDetallePK) object;
        if (this.idCombo != other.idCombo) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetallePK[ idCombo=" + idCombo + ", idProducto=" + idProducto + " ]";
    }
    
}
