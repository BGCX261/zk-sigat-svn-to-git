/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sebastian
 */
@Entity
@Table(name = "reservas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaEntity.findAll", query = "SELECT r FROM ReservaEntity r"),
    @NamedQuery(name = "ReservaEntity.findByIdReserva", query = "SELECT r FROM ReservaEntity r WHERE r.idReserva = :idReserva"),
    @NamedQuery(name = "ReservaEntity.findByFechaInicio", query = "SELECT r FROM ReservaEntity r WHERE r.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ReservaEntity.findByFechaFin", query = "SELECT r FROM ReservaEntity r WHERE r.fechaFin = :fechaFin"),
    @NamedQuery(name = "ReservaEntity.findByTitulo", query = "SELECT r FROM ReservaEntity r WHERE r.titulo = :titulo"),
    @NamedQuery(name = "ReservaEntity.findByContenido", query = "SELECT r FROM ReservaEntity r WHERE r.contenido = :contenido"),
    @NamedQuery(name = "ReservaEntity.findByColorBorde", query = "SELECT r FROM ReservaEntity r WHERE r.colorBorde = :colorBorde"),
    @NamedQuery(name = "ReservaEntity.findByColorBarra", query = "SELECT r FROM ReservaEntity r WHERE r.colorBarra = :colorBarra"),
    @NamedQuery(name = "ReservaEntity.findByIsLoked", query = "SELECT r FROM ReservaEntity r WHERE r.isLoked = :isLoked"),
    @NamedQuery(name = "ReservaEntity.findByDepto", query = "SELECT r FROM ReservaEntity r WHERE r.depto = :depto"),
    @NamedQuery(name = "ReservaEntity.findByEstado", query = "SELECT r FROM ReservaEntity r WHERE r.estado = :estado"),
    @NamedQuery(name = "ReservaEntity.findByTarifa", query = "SELECT r FROM ReservaEntity r WHERE r.tarifa = :tarifa"),
    @NamedQuery(name = "ReservaEntity.findByAdelanto", query = "SELECT r FROM ReservaEntity r WHERE r.adelanto = :adelanto"),
    @NamedQuery(name = "ReservaEntity.findByNombreContacto", query = "SELECT r FROM ReservaEntity r WHERE r.nombreContacto = :nombreContacto"),
    @NamedQuery(name = "ReservaEntity.findByRol", query = "SELECT r FROM ReservaEntity r WHERE r.rol = :rol"),
    @NamedQuery(name = "ReservaEntity.findByDepartamentosIdDepartamento", query = "SELECT r FROM ReservaEntity r WHERE r.departamentosIdDepartamento = :departamentosIdDepartamento"),
    @NamedQuery(name = "ReservaEntity.findByClientesIdclientes", query = "SELECT r FROM ReservaEntity r WHERE r.clientesIdclientes = :clientesIdclientes")})
public class ReservaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva")
    private Integer idReserva;
    @Column(name = "fecha_inicio")
    private BigInteger fechaInicio;
    @Column(name = "fecha_fin")
    private BigInteger fechaFin;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "Contenido")
    private String contenido;
    @Column(name = "color_borde")
    private String colorBorde;
    @Column(name = "color_barra")
    private String colorBarra;
    @Column(name = "isLoked")
    private Short isLoked;
    @Column(name = "depto")
    private String depto;
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tarifa")
    private Float tarifa;
    @Column(name = "adelanto")
    private Float adelanto;
    @Column(name = "nombre_contacto")
    private String nombreContacto;
    @Column(name = "rol")
    private String rol;
    @Column(name = "departamentos_id_departamento")
    private Integer departamentosIdDepartamento;
    @Column(name = "clientes_idclientes")
    private Integer clientesIdclientes;
    @JoinColumn(name = "departamentos_id_departamento1", referencedColumnName = "id_departamento")
    @ManyToOne
    private DepartamentoEntity departamentosIdDepartamento1;
    @JoinColumn(name = "clientes_idclientes1", referencedColumnName = "idclientes")
    @ManyToOne
    private ClienteEntity clientesIdclientes1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservasIdReserva")
    private List<Pasajerosxreseva> pasajerosxresevaList;

    public ReservaEntity() {
    }

    public ReservaEntity(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public BigInteger getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(BigInteger fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigInteger getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(BigInteger fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getColorBorde() {
        return colorBorde;
    }

    public void setColorBorde(String colorBorde) {
        this.colorBorde = colorBorde;
    }

    public String getColorBarra() {
        return colorBarra;
    }

    public void setColorBarra(String colorBarra) {
        this.colorBarra = colorBarra;
    }

    public Short getIsLoked() {
        return isLoked;
    }

    public void setIsLoked(Short isLoked) {
        this.isLoked = isLoked;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Float getTarifa() {
        return tarifa;
    }

    public void setTarifa(Float tarifa) {
        this.tarifa = tarifa;
    }

    public Float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(Float adelanto) {
        this.adelanto = adelanto;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getDepartamentosIdDepartamento() {
        return departamentosIdDepartamento;
    }

    public void setDepartamentosIdDepartamento(Integer departamentosIdDepartamento) {
        this.departamentosIdDepartamento = departamentosIdDepartamento;
    }

    public Integer getClientesIdclientes() {
        return clientesIdclientes;
    }

    public void setClientesIdclientes(Integer clientesIdclientes) {
        this.clientesIdclientes = clientesIdclientes;
    }

    public DepartamentoEntity getDepartamentosIdDepartamento1() {
        return departamentosIdDepartamento1;
    }

    public void setDepartamentosIdDepartamento1(DepartamentoEntity departamentosIdDepartamento1) {
        this.departamentosIdDepartamento1 = departamentosIdDepartamento1;
    }

    public ClienteEntity getClientesIdclientes1() {
        return clientesIdclientes1;
    }

    public void setClientesIdclientes1(ClienteEntity clientesIdclientes1) {
        this.clientesIdclientes1 = clientesIdclientes1;
    }

    @XmlTransient
    public List<Pasajerosxreseva> getPasajerosxresevaList() {
        return pasajerosxresevaList;
    }

    public void setPasajerosxresevaList(List<Pasajerosxreseva> pasajerosxresevaList) {
        this.pasajerosxresevaList = pasajerosxresevaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaEntity)) {
            return false;
        }
        ReservaEntity other = (ReservaEntity) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservaEntity[ idReserva=" + idReserva + " ]";
    }
    
}
