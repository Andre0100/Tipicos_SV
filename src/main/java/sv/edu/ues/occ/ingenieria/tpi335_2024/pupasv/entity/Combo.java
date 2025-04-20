/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity;

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
import jakarta.persistence.Table;
import java.util.ArrayList;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle;

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
    @NamedQuery(name = "Combo.findWithDetalles",
            query = "SELECT c FROM Combo c LEFT JOIN FETCH c.detalles WHERE c.id = :id"),
    @NamedQuery(name = "Combo.findActivos",
            query = "SELECT c FROM Combo c WHERE c.activo = true ORDER BY c.idCombo"),

    @NamedQuery(name = "Combo.countAll",
            query = "SELECT COUNT(c) FROM Combo c"),

    @NamedQuery(name = "Combo.countActivos",
            query = "SELECT COUNT(c) FROM Combo c WHERE c.activo = true"),

    @NamedQuery(name = "Combo.findWithDetalles",
            query = "SELECT c FROM Combo c LEFT JOIN FETCH c.detalles WHERE c.idCombo = :idCombo"),
    @NamedQuery(name = "Combo.findRange", query = "SELECT c FROM Combo c")
})

public class Combo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)

    @Column(name = "id_combo")
    private Long idCombo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "descripcion_publica")
    private String descripcionPublica;

    @Column(name = "nombre_combo", nullable = false, length = 100)
    private String nombre_combo;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComboDetalle> detalles = new ArrayList<>();

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

    public List<ComboDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ComboDetalle> detalles) {
        this.detalles = detalles;
    }

}
