/**
 * 
 */
package zk_controllers;

import java.util.List;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

import controller.exceptions.IllegalOrphanException;
import entity.PasajeroEntity;
import zk_dao.PasajerosCRUDDao;

/**
 * Clase controladora del CRUD de pasajeros
 * 
 * @author Damian
 * 
 */
public class pasajerosCRUDController extends GenericForwardComposer {
	private static final long serialVersionUID = -8936483745992686754L;
	private PasajeroEntity current = new PasajeroEntity();
	private PasajerosCRUDDao pasajerosDAO = new PasajerosCRUDDao();
	private Textbox apynom;
	private Textbox dni;
	private Textbox telefono;
	private Textbox direccion;

	/**
	 * Metodo que retorna la lista de pasajeros
	 * 
	 * @return List<PasajerosEntity> allPasajeros
	 */
	public List<PasajeroEntity> getAllPasajeros() {
		return pasajerosDAO.findAll();
	}

	/**
	 * Getter del pasajero actual
	 * 
	 * @return PasajeroEntity currentPasajero
	 */
	public PasajeroEntity getCurrent() {
		return current;
	}

	/**
	 * Setter del pasajero Actual
	 * 
	 * @param PasajeroEntity currentPasajero
	 */
	public void setCurrent(PasajeroEntity current) {
		this.current = current;
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton add
	 * de la vista
	 */
	public void onClick$add() {
		if (current != null) {
			pasajerosDAO.insert(current);
		}
		limpiarCampos();
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton update
	 * de la vista
	 */
	public void onClick$update() {
		if(current != null){
			pasajerosDAO.update(current);
		}
		limpiarCampos();
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton delete
	 * de la vista
	 * 
	 * @throws IllegalOrphanException
	 */
	public void onClick$delete() throws IllegalOrphanException {
		if(current != null){
			pasajerosDAO.delete(current);
			current=null;
		}
	}
	/**
	 * Metodo que se encarga de limpiar los campos de departamento.zul
	 */
	public void limpiarCampos() {
		apynom.setText("");
		dni.setText("");
		telefono.setText("");
		direccion.setText("");
	}

}
