/**
 * 
 */
package zk_controllers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controller.DepartamentosJpaController;

import entity.DepartamentoEntity;

import zk_data.Colors;
import zk_models.ReservasItem;

/**
 * @author Sebastian
 *
 */
public class ModificarDeptoController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Window depWin;
	private Window winModDepto;
	private Button btnSalir;
	private Button btnGuardar;
	private Textbox comodidades;
	private Textbox plaza;
	private Textbox tarifa;
	private Combobox color;
	private Textbox nombre;

	/**
	 *
	 *
	 */
	
	public void prepareWindow(int left, int top, DepartamentoEntity depto) {
		winModDepto.setLeft(left + "px");
		winModDepto.setTop(top + "px");
		
		
		comodidades.setValue(depto.getComodidades());
		tarifa.setValue(depto.getTarifa().toString());
		plaza.setValue(depto.getPlazas().toString());
		depto.setColor(color.getSelectedItem().getLabel());
		color.setValue(depto.getColor());
		nombre.setValue(depto.getNombre());
		
		
		
}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub

	}

	public void onClick$btnSalir() {
		//TODO: please check if you have use "self" or zscript functions here.
		winModDepto.setVisible(false);

	}

	public void onClick$btnGuardar() {
		
		Window win = (Window)winModDepto.getParent();
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");
		

		try {
			
			
			// instancio el manejador de la Entity
			DepartamentosJpaController deptoController = new DepartamentosJpaController(
					emf);

			// Creo un nuevo objeto del Entity
			DepartamentoEntity depto = new DepartamentoEntity();

			// Casteo el ReservasItem recibido al Entity
			
			depto.setNombre(nombre.getValue());
			depto.setColor(color.getSelectedItem().getLabel());
			depto.setComodidades(comodidades.getValue());
			depto.setPlazas(Integer.parseInt(plaza.getValue()));
			depto.setTarifa(Float.valueOf(tarifa.getValue()));
			

			

			// inserto en la base de datos
			try {
				deptoController.create(depto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {

			ex.printStackTrace();
			// System.out.println(SQL.insertNewsItem(ni));
		} finally {
			try {
				// cierro el emf
				emf.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		
		winModDepto.setVisible(false);
	}

	public void onSelect$color() {
		//TODO: please check if you have use "self" or zscript functions here.


	}

}
