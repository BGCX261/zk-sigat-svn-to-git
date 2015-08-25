package zk_controllers;

import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import controller.exceptions.PreexistingEntityException;
import entity.Pasajerosxreseva;
import zk_dao.PasajeroxReservaDAO;
 

/**
 * Clase controladora de la vista de pasajeros por reserva
 * 
 * @author Damian
 * 
 */
public class PasajeroxreservaCRUDController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pasajerosxreseva current = new Pasajerosxreseva();
	private PasajeroxReservaDAO paReservaDAO = new PasajeroxReservaDAO(); 

	/**
	 * Retorna la lista de pasajeros por reserva
	 * @return List<Pasajerosxreseva> pasajeroXreserva
	 */
	public List<Pasajerosxreseva> getAllPasajeroReserva() {
		return paReservaDAO.findAll();
	}

	/**
	 * getter pasajero por reserva actual
	 * 
	 * @return Pasajerosxreseva pasXreserva
	 */
	public Pasajerosxreseva getCurrent() {
		return current;
	}

	/**
	 * Setter pasajero por reserva actual
	 * 
	 * @param Pasajerosxreserva currentPasajXreserva
	 */
	public void setCurrent(Pasajerosxreseva current) {
		this.current = current;
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton add
	 * de la vista
	 * 
	 * @throws PreexistingEntityException
	 * @throws Exception
	 */
	public void onClick$add() throws PreexistingEntityException, Exception {
		if (current != null) {
			paReservaDAO.insert(current); 
		}

	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton update
	 * de la vista
	 */
	public void onClick$update() {
		if(current != null){
			paReservaDAO.update(current);
		}

	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton delete
	 * de la vista
	 */
	public void onClick$delete() {
		if(current != null){
			paReservaDAO.delete(current);
			current=null;
		}
	}
	
	

}
