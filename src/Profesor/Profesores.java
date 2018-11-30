/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profesor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author elton
 */
@Entity
@Table(name = "profesores")
@NamedQueries({
    @NamedQuery(name = "Profesores.findAll", query = "SELECT p FROM Profesores p")})
public class Profesores implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void add(Profesores persona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprofesores")
    private Integer idprofesores;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "ci")
    private String ci;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;

    public Profesores() {
    }

    public Profesores(Integer idprofesores) {
        this.idprofesores = idprofesores;
    }

    public Integer getIdprofesores() {
        return idprofesores;
    }

    public void setIdprofesores(Integer idprofesores) {
        this.idprofesores = idprofesores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofesores != null ? idprofesores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesores)) {
            return false;
        }
        Profesores other = (Profesores) object;
        if ((this.idprofesores == null && other.idprofesores != null) || (this.idprofesores != null && !this.idprofesores.equals(other.idprofesores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profesor.Profesores[ idprofesores=" + idprofesores + " ]";
    }
    
}
