package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import java.io.Serializable;
import jakarta.persistence.*;

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
    @NamedQuery(name = "ComboDetalle.findByActivo", query = "SELECT c FROM ComboDetalle c WHERE c.activo = :activo")
})
public class ComboDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ComboDetallePK comboDetallePK;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @MapsId("idCombo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_combo", referencedColumnName = "id_combo")
    private Combo combo;
    
    @MapsId("idProducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
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
        if (!(object instanceof ComboDetalle)) {
            return false;
        }
        ComboDetalle other = (ComboDetalle) object;
        return (this.comboDetallePK != null || other.comboDetallePK == null)
                && (this.comboDetallePK == null || this.comboDetallePK.equals(other.comboDetallePK));
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle[ comboDetallePK=" + comboDetallePK + " ]";
    }
}
