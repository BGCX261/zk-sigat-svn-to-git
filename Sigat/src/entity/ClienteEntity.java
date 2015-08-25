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
 *
 * @author Diego
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
/**
 * Clase que se encarga de realizar el mapeo de la tabla de la base de datos
 * Cliente. Cuenta con un conjunto de metodos getter y setear para la manipular
 * los datos (POJO), utiliza Java Persistence API
 * @author Diego
 *
 */

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
    @OneToMany(mappedBy = "clientesIdclientes1")
    private List<ReservaEntity> reservasList;

    /**
     * Constructor vacio de la clase
     */
    
    public ClienteEntity() {
    }

    /**
     * Contructor de la clase
     * @param Integer idclientes
     */
    public ClienteEntity(Integer idclientes) {
        this.idclientes = idclientes;
    }

    /**
     * Metodo que se encarga de recuperar el id de la tabla
     * @return idclientes
     */
    public Integer getIdclientes() {
        return idclientes;
    }

    /**
     * Metodo que se encarga de asignar un id a la tabla
     * @param Integer idclientes
     */
    public void setIdclientes(Integer idclientes) {
        this.idclientes = idclientes;
    }

    /**
     * Metodo que se encarga de recuperar la razon social de la tabla 
     * @return razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }
    /**
     * Metodo que se encarga de asignar una razon social a la tabla y 
     * a su vez poner la fecha de alta
     * @param String razonSocial
     */
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        this.setFechaAlta(new Date());
    }

    /**
     * Metodo que se encarga de recuperar el cuit de la tabla
     * @return cuit
     */
    public String getCiut() {
        return ciut;
    }
    /**
     * Metodo que se encarga de asignar un cuit en la tabla
     * @param String ciut
     */
    public void setCiut(String ciut) {
        this.ciut = ciut;
    }
    /**
     * Metodo que se encarga de recuperar la direccion de la tabla
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * Metodo que se encarga de asignar una direccion en la tabla
     * @param String direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Metodo que se encarga de recuperar la direccion de mail de la tabla
     * @return mail
     */
    public String getMail() {
        return mail;
    }
    /**
     * Metodo que se encarga de asignar una direccion de mail en la tabla
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    /**
     * Metodo que se encarga de recuperar el telefono de la tabla
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * Metodo que se encarga de asignar un telefono en la tabla
     * @param String telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Metodo que se encarga de recuperar la fecha de alta en la que 
     * se creo el cliente de la tabla en el formato "dd 'de' MMMM 'de' yyyy"
     * @return fecha
     */
    
	public String getFechaAlta() {
    	SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    	String fecha=formateador.format(fechaAlta);
        return fecha;
    }
	/**
	 * Metodo que se encarga de asignar una fecha de alta de un cliente en la tabla
	 * @param fechaAlta
	 */
	
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    /**
     * Metodo que se encarga de recuperar un lista de las reservas.
     *  
     * @return reservasList
     */
    @XmlTransient
    public List<ReservaEntity> getReservasList() {
        return reservasList;
    }
    /**
     * Metodo que se encarga de asignar una lista de reservas en la tabla
     * @param List<ReservaEntity> reservasList
     */

    public void setReservasList(List<ReservaEntity> reservasList) {
        this.reservasList = reservasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclientes != null ? idclientes.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo que sobrescribe el metodo equals de la clase Object
     */
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
