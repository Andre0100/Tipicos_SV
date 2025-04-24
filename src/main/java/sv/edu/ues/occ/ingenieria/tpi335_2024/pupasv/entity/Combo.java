/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 *
 * @author morales
 */
@Entity
@Table(name = "combo")
@NamedQueries({
    @NamedQuery(name = "Combo.findAll", query = "SELECT c FROM Combo c"),
    @NamedQuery(name = "Combo.findByIdCombo", query = "SELECT c FROM Combo c WHERE c.idCombo = :idCombo"),
    @NamedQuery(name = "Combo.findByNombre", query = "SELECT c FROM Combo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Combo.findByActivo", query = "SELECT c FROM Combo c WHERE c.activo = :true"), //campo activo debe existir
    @NamedQuery(name = "Combo.findByDescripcionPublica", query = "SELECT c FROM Combo c WHERE c.descripcionPublica = :descripcionPublica"),
//    @NamedQuery(name = "Combo.findWithDetalles",
//            query = "SELECT c FROM Combo c LEFT JOIN FETCH c.detalles WHERE c.id = :id"),
    @NamedQuery(name = "Combo.findActivos",
            query = "SELECT c FROM Combo c WHERE c.activo = true ORDER BY c.idCombo"),

    @NamedQuery(name = "Combo.countAll",
            query = "SELECT COUNT(c) FROM Combo c"),

    @NamedQuery(name = "Combo.countActivos",
            query = "SELECT COUNT(c) FROM Combo c WHERE c.activo = true"),

    @NamedQuery(name = "Combo.findWithDetalles",
            query = "SELECT c FROM Combo c LEFT JOIN FETCH c.ComboDetalleList WHERE c.idCombo = :idCombo"),
    @NamedQuery(name = "Combo.findRange", query = "SELECT c FROM Combo c"),
    @NamedQuery(
    name = "Combo.findAllNombres",
    query = "SELECT DISTINCT c.nombre FROM Combo c WHERE c.activo = true"
    ),
    @NamedQuery(
        name  = "Combo.findProductosConPrecios",
        query = "SELECT c.nombre, p, pp, cd.cantidad FROM Combo c " +
            "JOIN c.ComboDetalleList cd " +
            "JOIN cd.producto p " +
            "LEFT JOIN p.productoPrecioList pp ON " +
            "pp.fechaDesde = (SELECT MAX(pp2.fechaDesde) FROM ProductoPrecio pp2 " +
            "WHERE pp2.idProducto = p AND " +
            "(pp2.fechaHasta IS NULL OR pp2.fechaHasta >= CURRENT_DATE)) " +
            "WHERE c.activo = true " +
            "AND p.activo = true " +
            "AND cd.activo = true " +
            "ORDER BY c.nombre"
    )
})
public class Combo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "combo_seq")
    @SequenceGenerator(name = "combo_seq", sequenceName = "combo_id_combo_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id_combo")
    private Long idCombo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "descripcion_publica")
    private String descripcionPublica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combo")
    private List<ComboDetalle> ComboDetalleList;

    public Combo() {
    }

    public Combo(Long idCombo) {
        this.idCombo = idCombo;
    }

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDescripcionPublica() {
        return descripcionPublica;
    }

    public void setDescripcionPublica(String descripcionPublica) {
        this.descripcionPublica = descripcionPublica;
    }

    @JsonbTransient
    public List<ComboDetalle> getComboDetalleList() {
        return ComboDetalleList;
    }

    public void setComboDetalleList(List<ComboDetalle> comboDetalleList) {
        this.ComboDetalleList = comboDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCombo != null ? idCombo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Combo)) {
            return false;
        }
        Combo other = (Combo) object;
        if ((this.idCombo == null && other.idCombo != null) || (this.idCombo != null && !this.idCombo.equals(other.idCombo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo[ idCombo=" + idCombo + " ]";
    }
    
}

