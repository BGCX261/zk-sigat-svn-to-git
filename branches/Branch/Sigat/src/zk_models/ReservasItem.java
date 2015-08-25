package zk_models;

import org.zkoss.calendar.impl.SimpleCalendarEvent;


public class ReservasItem extends SimpleCalendarEvent {
	
	
	private int _news_item;
	private String telefono;
	private float tarifa;
	private String rol;
	private float adelanto;
	private String nombreContacto;
	private String depto;
	private String direccion;
	private String dni;
	private String estado;
	private String nombre;
	//id cliente
	private int idClie;

	public int getNews_item() {
		return _news_item;
	}
	
	public void setNews_item(int id) {
		_news_item = id;
	}
	
	public ReservasItem() {
		
	}
	
	
	
	public float getAdelanto() {
		return this.adelanto;
	}

	public void setAdelanto(float adelanto) {
		this.adelanto = adelanto;
	}

	public String getNombreContacto() {
		return this.nombreContacto;
	}

	public void setNombreContacto(String apellido) {
		this.nombreContacto = apellido;
	}

	
	
	public String getDepto() {
		return this.depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return this.rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	/******************************/
	public int getIDCliente() {
		return this.idClie;
	}
	public void setIDCliente(int idCliente) {
		this.idClie = idCliente;
	}

	/*********************************/

	public float getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(float tarifa) {
		this.tarifa = tarifa;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	


	
	
	
	
}
