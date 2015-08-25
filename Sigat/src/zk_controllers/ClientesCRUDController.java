
package zk_controllers;

import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

import entity.ClienteEntity;
import zk_dao.ClientesCRUDDao;
 

/**
 * Clase controladorar de la vista del CRUD
 * @author Damian
 *
 */
public class ClientesCRUDController extends GenericForwardComposer {
	private static final long serialVersionUID = -8936483745992686754L;
	private ClienteEntity current = new ClienteEntity();
	private ClientesCRUDDao clientesDAO = new ClientesCRUDDao();
	private Textbox razonSocial;
	private Textbox ciut;
	private Textbox direccion;
	private Textbox mail;
	private Textbox telefono;
	
	/**
	 * Metodo que retorna todos los Clientes de la tabla
	 * 
	 * @return List<ClienteEntity> (lista de entidades de clientes)
	 */
	public List<ClienteEntity> getAllClientes() {
		return clientesDAO.findAll();
	}

	/**
	 * Metodo que retorna el cliente actual
	 * 
	 * @return ClienteEntity currentCliente
	 */
	public ClienteEntity getCurrent() {
		return current;
	}

	/**
	 * Seter de cliente actual
	 * @param ClienteEntity current
	 */
	public void setCurrent(ClienteEntity current) {
		this.current = current;
	}

	/**
	 * Metodo que realiza la accion al presionar el boton add
	 */
	public void onClick$add() {
		if (current != null) {
			clientesDAO.insert(current);
		}
		limpiarCampos();
	}

	/**
	 * Metodo que realiza la accion al presionar el boton update
	 */
	public void onClick$update() {
		if(current != null){
			clientesDAO.update(current);
		}
		limpiarCampos();
	}

	/**
	 * Metodo que realiza la accion al presionar el boton delete
	 */
	public void onClick$delete() {
		if(current != null){
			clientesDAO.delete(current);
			current=null;
		}
	}
	/**
	 * Metodo que se encarga de limpiar los campos de clientesCrud.zul
	 */
	public void limpiarCampos() {
		razonSocial.setText("");
		ciut.setText("");
		direccion.setText("");
		mail.setText("");
		telefono.setText("");
	}

}
