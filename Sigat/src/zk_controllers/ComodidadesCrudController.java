
package zk_controllers;

import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

import controller.exceptions.PreexistingEntityException;
import entity.Comodidades;
import zk_dao.ComodidadesCrudDao;
 

/**
 * 
 * Clase controladorar de la vista del CRUD
 * @author Damian
 * 
 */
public class ComodidadesCrudController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Comodidades current = new Comodidades();
	private ComodidadesCrudDao comodidadesDAO = new ComodidadesCrudDao();
	private Textbox nombre;
	
	/**
	 * Metodo que retorna Todas las comodidades
	 * 
	 * @return List<Comodidades> allComodidades
	 */
	public List<Comodidades> getAllComodidades() {
		return comodidadesDAO.findAll();
	}

	/**
	 * Metodo que retorna la comodidad actual
	 * @return Comodidades currentComodidades
	 */
	public Comodidades getCurrent() {
		return current;
	}

	/**
	 * Seter de la comodidad actual
	 * @param Comodidades current
	 */
	public void setCurrent(Comodidades current) {
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
			comodidadesDAO.insert(current); 
		}
		limpiarCampos();
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton update
	 * de la vista
	 */
	public void onClick$update() {
		if(current != null){
			comodidadesDAO.update(current);
		}
		limpiarCampos();

	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton delete
	 * de la vista
	 */
	public void onClick$delete() {
		if(current != null){
			comodidadesDAO.delete(current);
			current=null;
		}
	}
	
	/**
	 * Metodo que se encarga de limpiar los campos de comodidades.zul
	 */
	public void limpiarCampos() {
		nombre.setText("");
		
	}
	

}
