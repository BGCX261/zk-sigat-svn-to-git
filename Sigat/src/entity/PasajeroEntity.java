/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase que se encarga de realizar el mapeo de la tabla de la base de datos
 * PasajeroEntity. Cuenta con un conjunto de metodos getter y setear para la manipular
 * los datos (POJO), utiliza Java Persistence API
 * @author Diego
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasajerosIdPasajeros")
    private List<Pasajerosxreseva> pasajerosxresevaList;
    
    /**
     * Constructor vacio de la clase
     */
    public PasajeroEntity() {
    }
    
    /**
     * Constructor vacio
     * @param Integer idPasajeros
     */   
    public PasajeroEntity(Integer idPasajeros) {
        this.idPasajeros = idPasajeros;
    }

    /**
     * Metodo que se encarga de recuperar el id de la tabla
     * @return idPasajeros
     */
    public Integer getIdPasajeros() {
        return idPasajeros;
    }
    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param Integer idPasajeros
     */
    
    public void setIdPasajeros(Integer idPasajeros) {
        this.idPasajeros = idPasajeros;
    }

    /**
     * Metodo que se encarga de recuperar el Apellido y Nombre de la tabla
     * @return apynom
     */
    public String getApynom() {
        return apynom;
    }
    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param String apynom
     */
    public void setApynom(String apynom) {
        this.apynom = apynom;
        this.setFechaAlta(new Date());
    }
    /**
     * Metodo que se encarga de recuperar el dni de la tabla
     * @return dni
     */
    public String getDni() {
        return dni;
    }
    /**
     * Metodo que se encarga de asignar un dni a la tabla
     * @param String dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    /**
     * Metodo que se encarga de recuperar el telefono de la tabla
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * Metodo que se encarga de asignar un telefono a la tabla
     * @param String telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * Metodo que se encarga de recuperar la direccion de la tabla
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * Metodo que se encarga de asignar una direccion a la tabla
     * @param String direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Metodo que se encarga de recuperar la fecha de la tabla
     * @return fecha
     */
    public String getFechaAlta() {
    	SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    	String fecha=formateador.format(fechaAlta);
        return fecha;
    }
    /**
     * Metodo que se encarga de asignar una fecha a la tabla
     * @param Date fechaAlta
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    /**
     * Metodo que se encarga de recuperar el id de la reserva de la tabla
     * @return reservasIdReserva
     */

    public Integer getReservasIdReserva() {
        return reservasIdReserva;
    }
    /**
     * Metodo que se encarga de asignar un id a una reserva a la tabla
     * @param Integer reservasIdReserva
     */
    public void setReservasIdReserva(Integer reservasIdReserva) {
        this.reservasIdReserva = reservasIdReserva;
    }
    /**
     * Metodo que se encarga de recuperar una lista de pasajerosxreserva de la tabla
     * @return pasajerosxresevaList
     */
    @XmlTransient
    public List<Pasajerosxreseva> getPasajerosxresevaList() {
        return pasajerosxresevaList;
    }
    /**
     * Metodo que se encarga de asignar una List<Pasajerosxreseva> pasajerosxresevaList a la tabla
     * @param List<Pasajerosxreseva> pasajerosxresevaList
     */
    public void setPasajerosxresevaList(List<Pasajerosxreseva> pasajerosxresevaList) {
        this.pasajerosxresevaList = pasajerosxresevaList;
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
