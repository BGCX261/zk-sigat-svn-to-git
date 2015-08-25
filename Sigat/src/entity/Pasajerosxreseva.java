/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@Entity
@Table(name = "pasajerosxreseva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasajerosxreseva.findAll", query = "SELECT p FROM Pasajerosxreseva p"),
    @NamedQuery(name = "Pasajerosxreseva.findByIdPasajeroReserva", query = "SELECT p FROM Pasajerosxreseva p WHERE p.idPasajeroReserva = :idPasajeroReserva"),
    @NamedQuery(name = "Pasajerosxreseva.findByApynom", query = "SELECT p FROM Pasajerosxreseva p WHERE p.apynom = :apynom"),
    @NamedQuery(name = "Pasajerosxreseva.findByDni", query = "SELECT p FROM Pasajerosxreseva p WHERE p.dni = :dni"),
    @NamedQuery(name = "Pasajerosxreseva.findByDireccion", query = "SELECT p FROM Pasajerosxreseva p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Pasajerosxreseva.findByFecha", query = "SELECT p FROM Pasajerosxreseva p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Pasajerosxreseva.findByIdReserva", query = "SELECT p FROM Pasajerosxreseva p WHERE p.reservasIdReserva = :idReserva"),
    @NamedQuery(name = "Pasajerosxreseva.findByIdPasajeros", query = "SELECT p FROM Pasajerosxreseva p WHERE p.pasajerosIdPasajeros = :idPasajero")})
public class Pasajerosxreseva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Pasajero_Reserva")
    private Integer idPasajeroReserva;
    @Column(name = "apynom")
    private String apynom;
    @Column(name = "dni")
    private String dni;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "reservas_id_reserva", referencedColumnName = "id_reserva")
    @ManyToOne(optional = false)
    private ReservaEntity reservasIdReserva;
    @JoinColumn(name = "pasajeros_id_pasajeros", referencedColumnName = "id_pasajeros")
    @ManyToOne(optional = false)
    private PasajeroEntity pasajerosIdPasajeros;

    public Pasajerosxreseva() {
    }

    public Pasajerosxreseva(Integer idPasajeroReserva) {
        this.idPasajeroReserva = idPasajeroReserva;
    }

    public Integer getIdPasajeroReserva() {
        return idPasajeroReserva;
    }

    public void setIdPasajeroReserva(Integer idPasajeroReserva) {
        this.idPasajeroReserva = idPasajeroReserva;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ReservaEntity getReservasIdReserva() {
        return reservasIdReserva;
    }

    public void setReservasIdReserva(ReservaEntity reservasIdReserva) {
        this.reservasIdReserva = reservasIdReserva;
    }

    public PasajeroEntity getPasajerosIdPasajeros() {
        return pasajerosIdPasajeros;
    }

    public void setPasajerosIdPasajeros(PasajeroEntity pasajerosIdPasajeros) {
        this.pasajerosIdPasajeros = pasajerosIdPasajeros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasajeroReserva != null ? idPasajeroReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasajerosxreseva)) {
            return false;
        }
        Pasajerosxreseva other = (Pasajerosxreseva) object;
        if ((this.idPasajeroReserva == null && other.idPasajeroReserva != null) || (this.idPasajeroReserva != null && !this.idPasajeroReserva.equals(other.idPasajeroReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pasajerosxreseva[ idPasajeroReserva=" + idPasajeroReserva + " ]";
    }
    
}
