
package zk_controllers;

import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import entity.ClienteEntity;
import zk_dao.ClientesCRUDDao;
 

/**
 * @author Damian
 * 
 */
public class ClientesCRUDController extends GenericForwardComposer {
	private static final long serialVersionUID = -8936483745992686754L;
	private ClienteEntity current = new ClienteEntity();
	private ClientesCRUDDao clientesDAO = new ClientesCRUDDao();

	public List<ClienteEntity> getAllClientes() {
		return clientesDAO.findAll();
	}

	public ClienteEntity getCurrent() {
		return current;
	}

	public void setCurrent(ClienteEntity current) {
		this.current = current;
	}

	public void onClick$add() {
		if (current != null) {
			clientesDAO.insert(current);
		}

	}

	public void onClick$update() {
		if(current != null){
			clientesDAO.update(current);
		}

	}

	public void onClick$delete() {
		if(current != null){
			clientesDAO.delete(current);
			current=null;
		}
	}

}
