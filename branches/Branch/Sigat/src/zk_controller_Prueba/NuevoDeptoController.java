/**
 * 
 */
package zk_controller_Prueba;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controller.DepartamentosJpaController;
import entity.DepartamentoEntity;
import entity.ReservaEntity;

/**
 * @author Sebastian
 * 
 */
public class NuevoDeptoController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window winNuevoDepto;
	private Button btnSalir;
	private Button btnGuardar;
	private Textbox comodidades;
	private Textbox plaza;
	private Textbox tarifa;
	private Textbox nombre;
	private Combobox color;
	
	

	/**
	 *
	 *
	 */

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub
		comodidades.setText("");
		plaza.setText("");
		nombre.setText("");
		tarifa.setText("");

	}

	public void onClick$btnGuardar() {
		
		Window win = (Window)winNuevoDepto.getParent();
		
		// creo el entity manager factory
		
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
				win.getChildren();
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
		winNuevoDepto.setVisible(false);
		
		
	}

}
