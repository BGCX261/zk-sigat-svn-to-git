package zk_controllers;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Window;

import controller.exceptions.IllegalOrphanException;
import zk_dao.DepartamentoCRUDDao;
import entity.DepartamentoEntity;


public class DepartamentoCRUDController extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DepartamentoEntity current = new DepartamentoEntity();
	private DepartamentoCRUDDao deptoDAO = new DepartamentoCRUDDao();
	
	
	
	public List<DepartamentoEntity> getAllDepartamentos() {
		return deptoDAO.findAll();
	}

	public DepartamentoEntity getCurrent() {
		return current;
	}

	public void setCurrent(DepartamentoEntity current) {
		this.current = current;
	}

	public void onClick$add() {
		if (current != null) {
			deptoDAO.insert(current);
		}
	}

	public void onClick$update() {
		if(current != null){
			deptoDAO.update(current);
		}

	}

	public void onClick$delete() {
		if(current != null){
			try {
				deptoDAO.delete(current);
			} catch (IllegalOrphanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			current=null;
		}
	}
	

}
