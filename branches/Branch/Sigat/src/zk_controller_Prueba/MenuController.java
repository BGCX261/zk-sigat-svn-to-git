package zk_controller_Prueba;

import java.util.LinkedList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

public class MenuController extends GenericForwardComposer {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;
	Window principal;
	Toolbar toolbar;
	List<Component> heap = new LinkedList<Component>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Component window = Executions.createComponents("/index.zul",
				principal, null);
		heap.add(window);
	}

	/**
	 * Metodos de Eventos del Menú
	 */

	// public void onClick$(Event evt) {
	//
	// System.out.println("HOLAA!!!");
	// }

	

	public void onClick$bConsultarCliente$wMenu$iMenu() {

		Component window = Executions.createComponents(
				"/zul/administracion/cliente/consultarCliente.zul", principal,
				null);
		heap.add(window);
	}

	public void onClick$btn_calendar() {
		Component window = Executions.createComponents(
				"/index.zul", principal,
				null);
		heap.add(window);
	}

	public void onClick$btn_clientes() {
		Component window = Executions.createComponents(
				"/clientesCrud.zul", principal,
				null);
		heap.add(window);
	}
	
	public void onClick$btn_pasajeros() {
		
		Component window = Executions.createComponents(
				"/pasajerosCrud.zul", principal,
				null);
		heap.add(window);
	}

	public void onClick$btn_conf(){
		Component window = Executions.createComponents(
				"/departamentos.zul", principal,
				null);
		heap.add(window);
	}

	public void onClose(Event evt) {

		for (Component comp : heap) {

			if (((Window) comp).getTitle() == ((Window) evt.getTarget())
					.getTitle()) {
				System.out.println("Eliminando Ventana: "
						+ ((Window) evt.getTarget()).getTitle());
				heap.remove(comp);
				comp.detach();

			}
		}
		for (Object comp : principal.getChildren()) {

			System.out.println("Dashboard: " + comp.toString());

		}
		principal.getChildren().clear();
		Component window = Executions.createComponents(
				"DepartamentoZul/Menu2.zul", principal, null);
		heap.add(window);
	}
}
