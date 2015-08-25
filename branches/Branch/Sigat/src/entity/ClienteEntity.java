/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sebastian
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteEntity.findAll", query = "SELECT c FROM ClienteEntity c"),
    @NamedQuery(name = "ClienteEntity.findByIdclientes", query = "SELECT c FROM ClienteEntity c WHERE c.idclientes = :idclientes"),
    @NamedQuery(name = "ClienteEntity.findByRazonSocial", query = "SELECT c FROM ClienteEntity c WHERE c.razonSocial = :razonSocial"),
    @NamedQuery(name = "ClienteEntity.findByCiut", query = "SELECT c FROM ClienteEntity c WHERE c.ciut = :ciut"),
    @NamedQuery(name = "ClienteEntity.findByDireccion", query = "SELECT c FROM ClienteEntity c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ClienteEntity.findByMail", query = "SELECT c FROM ClienteEntity c WHERE c.mail = :mail"),
    @NamedQuery(name = "ClienteEntity.findByTelefono", query = "SELECT c FROM ClienteEntity c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ClienteEntity.findByFechaAlta", query = "SELECT c FROM ClienteEntity c WHERE c.fechaAlta = :fechaAlta")})
public class ClienteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idclientes")
    private Integer idclientes;
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "ciut")
    private String ciut;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @OneToMany(mappedBy = "clientesIdclientes")
    private List<ReservaEntity> reservasList;

    public ClienteEntity() {
    }

    public ClienteEntity(Integer idclientes) {
        this.idclientes = idclientes;
    }

    public Integer getIdclientes() {
        return idclientes;
    }

    public void setIdclientes(Integer idclientes) {
        this.idclientes = idclientes;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCiut() {
        return ciut;
    }

    public void setCiut(String ciut) {
        this.ciut = ciut;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
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
        hash += (idclientes != null ? idclientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteEntity)) {
            return false;
        }
        ClienteEntity other = (ClienteEntity) object;
        if ((this.idclientes == null && other.idclientes != null) || (this.idclientes != null && !this.idclientes.equals(other.idclientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ClienteEntity[ idclientes=" + idclientes + " ]";
    }
    
}
