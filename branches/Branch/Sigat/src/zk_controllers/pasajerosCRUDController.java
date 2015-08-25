/**
 * 
 */
package zk_controllers;

import java.util.List;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import entity.PasajeroEntity;
import zk_dao.PasajerosCRUDDao;

/**
 * @author Damian
 * 
 */
public class pasajerosCRUDController extends GenericForwardComposer {
	private static final long serialVersionUID = -8936483745992686754L;
	private PasajeroEntity current = new PasajeroEntity();
	private PasajerosCRUDDao pasajerosDAO = new PasajerosCRUDDao();

	public List<PasajeroEntity> getAllPasajeros() {
		return pasajerosDAO.findAll();
	}

	public PasajeroEntity getCurrent() {
		return current;
	}

	public void setCurrent(PasajeroEntity current) {
		this.current = current;
	}

	public void onClick$add() {
		if (current != null) {
			pasajerosDAO.insert(current);
		}

	}

	public void onClick$update() {
		if(current != null){
			pasajerosDAO.update(current);
		}

	}

	public void onClick$delete() {
		if(current != null){
			pasajerosDAO.delete(current);
			current=null;
		}
	}

}
