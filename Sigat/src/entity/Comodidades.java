/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que se encarga de realizar el mapeo de la tabla de la base de datos
 * Comodidades. Cuenta con un conjunto de metodos getter y setear para la manipular
 * los datos (POJO), utiliza Java Persistence API
 * @author Diego
 */
@Entity
@Table(name = "comodidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comodidades.findAll", query = "SELECT c FROM Comodidades c"),
    @NamedQuery(name = "Comodidades.findByIdcomodidades", query = "SELECT c FROM Comodidades c WHERE c.idcomodidades = :idcomodidades"),
    @NamedQuery(name = "Comodidades.findByNombre", query = "SELECT c FROM Comodidades c WHERE c.nombre = :nombre")})

public class Comodidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomodidades")
    private Integer idcomodidades;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "departamentos_id_departamento", referencedColumnName = "id_departamento")
    @ManyToOne
    private DepartamentoEntity departamentosIdDepartamento;

    /**
     * Constructor vacio de la clase
     */
    public Comodidades() {
    }
    
    /**
     * Contructor de la clase
     * @param Integer idcomodidades
     */
    public Comodidades(Integer idcomodidades) {
        this.idcomodidades = idcomodidades;
    }
    /**
     * Metodo que se encarga de recuperar el id de la tabla
     * @return idcomodidades
     */
    public Integer getIdcomodidades() {
        return idcomodidades;
    }
    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param Integer idcomodidades
     */
    public void setIdcomodidades(Integer idcomodidades) {
        this.idcomodidades = idcomodidades;
    }
    /**
     * Metodo que se encarga de recuperar el nombre de la tabla
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Metodo que se encarga de asignar un nombre en la tabla
     * @param String idclientes
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Metodo que se encarga de recuperar un departamento de la tabla
     * @return departamentosIdDepartamento
     */
    public DepartamentoEntity getDepartamentosIdDepartamento() {
        return departamentosIdDepartamento;
    }
    /**
     * Metodo que se encarga de asignar un departamento a la tabla
     * @param DepartamentoEntity departamentosIdDepartamento
     */
    public void setDepartamentosIdDepartamento(DepartamentoEntity departamentosIdDepartamento) {
        this.departamentosIdDepartamento = departamentosIdDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomodidades != null ? idcomodidades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comodidades)) {
            return false;
        }
        Comodidades other = (Comodidades) object;
        if ((this.idcomodidades == null && other.idcomodidades != null) || (this.idcomodidades != null && !this.idcomodidades.equals(other.idcomodidades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Comodidades[ idcomodidades=" + idcomodidades + " ]";
    }
    
}
