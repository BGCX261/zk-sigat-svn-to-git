/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase que se encarga de realizar el mapeo de la tabla de la base de datos
 * Comodidades. Cuenta con un conjunto de metodos getter y setear para la manipular
 * los datos (POJO), utiliza Java Persistence API
 * @author Diego
 */
@Entity
@Table(name = "departamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DepartamentoEntity.findAll", query = "SELECT d FROM DepartamentoEntity d"),
    @NamedQuery(name = "DepartamentoEntity.findByIdDepartamento", query = "SELECT d FROM DepartamentoEntity d WHERE d.idDepartamento = :idDepartamento"),
    @NamedQuery(name = "DepartamentoEntity.findByNombre", query = "SELECT d FROM DepartamentoEntity d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DepartamentoEntity.findByColor", query = "SELECT d FROM DepartamentoEntity d WHERE d.color = :color"),
    @NamedQuery(name = "DepartamentoEntity.findByTarifa", query = "SELECT d FROM DepartamentoEntity d WHERE d.tarifa = :tarifa"),
    @NamedQuery(name = "DepartamentoEntity.findByPlazas", query = "SELECT d FROM DepartamentoEntity d WHERE d.plazas = :plazas"),
    @NamedQuery(name = "DepartamentoEntity.findByComodidades1", query = "SELECT d FROM DepartamentoEntity d WHERE d.comodidades1 = :comodidades1"),
    @NamedQuery(name = "DepartamentoEntity.findByComodidades2", query = "SELECT d FROM DepartamentoEntity d WHERE d.comodidades2 = :comodidades2"),
    @NamedQuery(name = "DepartamentoEntity.findByComodidades3", query = "SELECT d FROM DepartamentoEntity d WHERE d.comodidades3 = :comodidades3")})
public class DepartamentoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_departamento")
    private Integer idDepartamento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "color")
    private String color;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tarifa")
    private Float tarifa;
    @Column(name = "plazas")
    private Integer plazas;
    @Column(name = "comodidades1")
    private String comodidades1;
    @Column(name = "comodidades2")
    private String comodidades2;
    @Column(name = "comodidades3")
    private String comodidades3;
    @OneToMany(mappedBy = "departamentosIdDepartamento")
    private List<Comodidades> comodidadesList;
    @OneToMany(mappedBy = "departamentosIdDepartamento1")
    private List<ReservaEntity> reservasList;

    /**
     * Constructor vacio de la clase
     */
    public DepartamentoEntity() {
    }
    /**
     * Constructor de la clase
     */
    public DepartamentoEntity(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * Metodo que se encarga de recuperar el id de la tabla
     * @return idDepartamento
     */
    public Integer getIdDepartamento() {
        return idDepartamento;
    }
    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param Integer idDepartamento
     */

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * Metodo que se encarga de recuperar el nombre de la tabla
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que se encarga de asignar un nombre al Departamento de la tabla
     * @param String nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que se encarga de recuperar el color del departamento
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Metodo que se encarga de asignar un color a la tabla
     * @param String color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Metodo que se encarga de recuperar la tarifa de la tabla Departamento
     * @return tarifa
     */
    public Float getTarifa() {
        return tarifa;
    }

    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param Float tarifa
     */
    public void setTarifa(Float tarifa) {
        this.tarifa = tarifa;
    }
    
    /**
     * Metodo que se encarga de recuperar la cantidad de plazas de la tabla Departamentos
     * @return plazas
     */
    public Integer getPlazas() {
        return plazas;
    }
    
    /**
     * Metodo que se encarga de asignar la cantidad de plazas del Departamento
     * @param Integer plazas
     */
    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }
    
    /**
     * Metodo que se encarga de recuperar las comodidades de la tabla
     * @return comodidades1
     */
    public String getComodidades1() {
        return comodidades1;
    }
    
    /**
     * Metodo que se encarga de asignar las comodidades en la tabla
     * @param String comodidades1
     */

    public void setComodidades1(String comodidades1) {
        this.comodidades1 = comodidades1;
    }

    /**
     * Metodo que se encarga de recuperar las comodidades de la tabla
     * @return comodidades2
     */
    public String getComodidades2() {
        return comodidades2;
    }

    /**
     * Metodo que se encarga de asignar las comodidades en la tabla
     * @param String comodidades2
     */
    public void setComodidades2(String comodidades2) {
        this.comodidades2 = comodidades2;
    }

    /**
     * Metodo que se encarga de recuperar las comodidades de la tabla
     * @return comodidades3
     */
    public String getComodidades3() {
        return comodidades3;
    }

    /**
     * Metodo que se encarga de asignar las comodidades en la tabla
     * @param String comodidades3
     */
    public void setComodidades3(String comodidades3) {
        this.comodidades3 = comodidades3;
    }
    /**
     * Metodo que se encarga de recuperar la lista de comodidades de la tabla
     * @return comodidades1
     */
    @XmlTransient
    public List<Comodidades> getComodidadesList() {
        return comodidadesList;
    }
    
    /**
     * Metodo que se encarga de asignar una lista de comodidades en la tabla
     * @param Integer idcomodidades
     */
    public void setComodidadesList(List<Comodidades> comodidadesList) {
        this.comodidadesList = comodidadesList;
    }

    /**
     * Metodo que se encarga de recuperar la lista de las reservas de la tabla
     * @return comodidades1
     */
    @XmlTransient
    public List<ReservaEntity> getReservasList() {
        return reservasList;
    }
    /**
     * Metodo que se encarga de asignar Lista de reservas a la tabla
     * @param List<ReservaEntity> reservasList
     */
    public void setReservasList(List<ReservaEntity> reservasList) {
        this.reservasList = reservasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartamento != null ? idDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoEntity)) {
            return false;
        }
        DepartamentoEntity other = (DepartamentoEntity) object;
        if ((this.idDepartamento == null && other.idDepartamento != null) || (this.idDepartamento != null && !this.idDepartamento.equals(other.idDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepartamentoEntity[ idDepartamento=" + idDepartamento + " ]";
    }
    
}
