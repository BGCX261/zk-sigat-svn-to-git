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
 *
 * @author Sebastian
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
    @NamedQuery(name = "DepartamentoEntity.findByComodidades", query = "SELECT d FROM DepartamentoEntity d WHERE d.comodidades = :comodidades")})
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
    @Column(name = "comodidades")
    private String comodidades;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departamentosIdDepartamento1")
    private List<ReservaEntity> reservasList;

    public DepartamentoEntity() {
    }

    public DepartamentoEntity(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getTarifa() {
        return tarifa;
    }

    public void setTarifa(Float tarifa) {
        this.tarifa = tarifa;
    }

    public Integer getPlazas() {
        return plazas;
    }

    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }

    public String getComodidades() {
        return comodidades;
    }

    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }

    @XmlTransient
    public List<ReservaEntity> getReservasList() {
        return reservasList;
    }

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
        return "javaapplication3.Departamentos[ idDepartamento=" + idDepartamento + " ]";
    }
    
}
