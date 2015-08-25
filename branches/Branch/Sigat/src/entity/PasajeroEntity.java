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
@Table(name = "pasajeros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasajeroEntity.findAll", query = "SELECT p FROM PasajeroEntity p"),
    @NamedQuery(name = "PasajeroEntity.findByIdPasajeros", query = "SELECT p FROM PasajeroEntity p WHERE p.idPasajeros = :idPasajeros"),
    @NamedQuery(name = "PasajeroEntity.findByApynom", query = "SELECT p FROM PasajeroEntity p WHERE p.apynom = :apynom"),
    @NamedQuery(name = "PasajeroEntity.findByDni", query = "SELECT p FROM PasajeroEntity p WHERE p.dni = :dni"),
    @NamedQuery(name = "PasajeroEntity.findByTelefono", query = "SELECT p FROM PasajeroEntity p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "PasajeroEntity.findByDireccion", query = "SELECT p FROM PasajeroEntity p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "PasajeroEntity.findByFechaAlta", query = "SELECT p FROM PasajeroEntity p WHERE p.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "PasajeroEntity.findByReservasIdReserva", query = "SELECT p FROM PasajeroEntity p WHERE p.reservasIdReserva = :reservasIdReserva")})
public class PasajeroEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pasajeros")
    private Integer idPasajeros;
    @Column(name = "apynom")
    private String apynom;
    @Column(name = "dni")
    private String dni;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "reservas_id_reserva")
    private Integer reservasIdReserva;
    @JoinTable(name = "pasajerosxreseva", joinColumns = {
        @JoinColumn(name = "pasajeros_id_pasajeros", referencedColumnName = "id_pasajeros")}, inverseJoinColumns = {
        @JoinColumn(name = "reservas_id_reserva", referencedColumnName = "id_reserva")})
    @ManyToMany
    private List<ReservaEntity> reservasList;

    public PasajeroEntity() {
    }

    public PasajeroEntity(Integer idPasajeros) {
        this.idPasajeros = idPasajeros;
    }

    public Integer getIdPasajeros() {
        return idPasajeros;
    }

    public void setIdPasajeros(Integer idPasajeros) {
        this.idPasajeros = idPasajeros;
    }

    public String getApynom() {
        return apynom;
    }

    public void setApynom(String apynom) {
        this.apynom = apynom;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Integer getReservasIdReserva() {
        return reservasIdReserva;
    }

    public void setReservasIdReserva(Integer reservasIdReserva) {
        this.reservasIdReserva = reservasIdReserva;
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
        hash += (idPasajeros != null ? idPasajeros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PasajeroEntity)) {
            return false;
        }
        PasajeroEntity other = (PasajeroEntity) object;
        if ((this.idPasajeros == null && other.idPasajeros != null) || (this.idPasajeros != null && !this.idPasajeros.equals(other.idPasajeros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PasajeroEntity[ idPasajeros=" + idPasajeros + " ]";
    }
    
}
