package zk_controllers;

import java.util.List;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

import controller.exceptions.IllegalOrphanException;
import zk_dao.DepartamentoCRUDDao;
import entity.DepartamentoEntity;

/**
 * Clase controller de la vista del CRUD
 * 
 * @author Sebastian
 *
 */
public class DepartamentoCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DepartamentoEntity current = new DepartamentoEntity();
	private DepartamentoCRUDDao deptoDAO = new DepartamentoCRUDDao();
	private Textbox nombre;
	private Combobox color;
	private Decimalbox tarifa;
	private Textbox plazas;
	private Combobox comod1;
	private Combobox comod2;
	private Combobox comod3;
	private String nombreComodidades = null;

	/**
	 * Metodo que retorna todos los departamentos
	 * @return List<DepartamentoEntity> departamentos
	 */
	public List<DepartamentoEntity> getAllDepartamentos() {
		return deptoDAO.findAll();
	}

	/**
	 * Metodo que retorna el departamento actual
	 * 
	 * @return DepartamentoEntity currentDepartamento
	 */
	public DepartamentoEntity getCurrent() {
		return current;
	}

	/**
	 * Seter del departamento actual
	 * @param DepartamentoEntity currentDepartamento
	 */
	public void setCurrent(DepartamentoEntity current) {
		this.current = current;
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton add
	 * de la vista
	 */
	public void onClick$add() {
		if (current != null) {
			current.setComodidades1(nombreComodidades);
			deptoDAO.insert(current);
		}
		limpiarCampos();
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton update
	 * de la vista
	 */
	public void onClick$update() {
		if (current != null) {

			try {

				if (nombreComodidades != null) {
					current.setComodidades1(nombreComodidades);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			deptoDAO.update(current);

		}
		limpiarCampos();
	}

	/**
	 * Metodo que se encarga de la accion a tomar al presionar el boton delete
	 * de la vista
	 * @throws IllegalOrphanException
	 */
	public void onClick$delete() throws IllegalOrphanException {
		if (current != null) {

			deptoDAO.delete(current);
			current = null;
		}
	}

	/**
	 * Metodo que obtiene el dato de la vista y se encarga de retornarlo
	 * 
	 * @param String nombre
	 * @return String comodidades
	 */
	public String getDatosComodidades1(String s) {

		nombreComodidades = s;
		return nombreComodidades;
	}

	/**
	 * Metodo que se encarga de limpiar los campos de departamento.zul
	 */
	public void limpiarCampos() {
		nombre.setText("");
		color.setText("");
		tarifa.setText("");
		plazas.setText("");
		comod1.setText("");
		comod2.setText("");
		comod3.setText("");
	}

}
