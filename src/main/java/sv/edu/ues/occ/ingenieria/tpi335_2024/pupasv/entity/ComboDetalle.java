/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author morales
 */
@Entity
@Table(name = "combo_detalle")
@NamedQueries({
    @NamedQuery(name = "ComboDetalle.findAll", query = "SELECT c FROM ComboDetalle c"),
    @NamedQuery(name = "ComboDetalle.findByIdCombo", query = "SELECT c FROM ComboDetalle c WHERE c.comboDetallePK.idCombo = :idCombo"),
    @NamedQuery(name = "ComboDetalle.findByIdProducto", query = "SELECT c FROM ComboDetalle c WHERE c.comboDetallePK.idProducto = :idProducto"),
    @NamedQuery(name = "ComboDetalle.findByCantidad", query = "SELECT c FROM ComboDetalle c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ComboDetalle.findByActivo", query = "SELECT c FROM ComboDetalle c WHERE c.activo = :activo")})
public class ComboDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComboDetallePK comboDetallePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "id_combo", referencedColumnName = "id_combo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Combo combo;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ComboDetalle() {
    }

    public ComboDetalle(ComboDetallePK comboDetallePK) {
        this.comboDetallePK = comboDetallePK;
    }

    public ComboDetalle(long idCombo, long idProducto) {
        this.comboDetallePK = new ComboDetallePK(idCombo, idProducto);
    }

    public ComboDetallePK getComboDetallePK() {
        return comboDetallePK;
    }

    public void setComboDetallePK(ComboDetallePK comboDetallePK) {
        this.comboDetallePK = comboDetallePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comboDetallePK != null ? comboDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboDetalle)) {
            return false;
        }
        ComboDetalle other = (ComboDetalle) object;
        if ((this.comboDetallePK == null && other.comboDetallePK != null) || (this.comboDetallePK != null && !this.comboDetallePK.equals(other.comboDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle[ comboDetallePK=" + comboDetallePK + " ]";
    }
    
}
