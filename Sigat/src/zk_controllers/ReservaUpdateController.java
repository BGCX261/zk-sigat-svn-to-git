package zk_controllers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import controller.ClientesJpaController;
import controller.DepartamentosJpaController;
import entity.ClienteEntity;
import entity.DepartamentoEntity;
import zk_data.Colors;
import zk_models.MyCalendarModel;
import zk_models.ReservasItem;

public class ReservaUpdateController extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Window updateMyEntry;
	Combobox deptoType;
	Textbox nameText;
	Textbox contactoText;
	Textbox direText;
	Textbox telefText;
	Textbox tarifaText;
	Textbox adelantoText;
	Listbox datosPas;
	int idDepto = 0;
	int idCliente = 0;
	private String colorRetorno;

	// Textbox tbText;
	// Define los estados de ocupacion(Reservado, Ocupado)
	private String estadoOcupacion = "Reservado";
	// Guarda el departamento que llama al update
	private String controlDepto = "";

	public void prepareWindow(int left, int top, ReservasItem ni) {
		updateMyEntry.setLeft(left + "px");
		updateMyEntry.setTop(top + "px");
		controlDepto = ni.getDepto();

		/********************** CLientes *******************************/
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		//PasajerosxresevaJpaController pasajeroJPA= new PasajerosxresevaJpaController(emf);
		/***************************************************/

		// Busca cliente por id de tabla reserva para cargar ventana
		ClienteEntity cliente = clienteResController.findClientes(ni
				.getIDCliente());
		idCliente = 0;
		nameText.setValue(cliente.getRazonSocial());
		contactoText.setValue(ni.getNombreContacto());
		direText.setValue(cliente.getDireccion());
		telefText.setValue(cliente.getTelefono());
		tarifaText.setValue(String.valueOf(ni.getTarifa()));
		adelantoText.setValue(String.valueOf(ni.getAdelanto()));
		deptoType.setValue(ni.getDepto());
		
	}

	public void onClick$btnUpdateNews() {
		CalendarsEvent evt = ((CalendarsEvent) updateMyEntry
				.getAttribute("calevent"));

		Window win = (Window) updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win
				.getVariable("cal", false);
		Chart piechart = (Chart) win.getVariable("piechart", false);

		ReservasItem ni = ((ReservasItem) updateMyEntry.getAttribute("ni"));
		
		/********************** CLientes *******************************/
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		ClienteEntity cliente = new ClienteEntity();
		/***************************************************/

		// Controla si se modifico en el combobox, en caso caso de modificacion
		// controla que no haya superposicion de dtos.
		if (!controlDepto.equals(deptoType.getSelectedItem().getLabel())) {
			if (MyCalendarModel.dao.superposicion(ni.getBeginDate(),
					ni.getEndDate(), deptoType.getSelectedItem().getLabel())) {
				try {
					Messagebox.show(
							"Superposicion de Departamento en rango de fecha.",
							"Error", Messagebox.OK, Messagebox.ERROR);
				} catch (InterruptedException e) {
					throw UiException.Aide.wrap(e);
				}
				return;
			}
		}
		// Cargo datos en la tabla de clientes como carga rapida
		if (idCliente == 0 && !nameText.getValue().equals("")) {

			cliente.setRazonSocial(nameText.getText());
			cliente.setTelefono(telefText.getText());
			cliente.setDireccion(direText.getText());
			clienteResController.create(cliente);
			// Define id que se va a cargar en tabla reservas
			idCliente = cliente.getIdclientes();
		}


		ni.setContentColor(colorRetorno);
		// Define color borde segun estado
		ni.setHeaderColor(Colors.getColorEstado(estadoOcupacion));

		ni.setBeginDate(ni.getBeginDate());
		ni.setEndDate(ni.getEndDate());
		ni.setNombreContacto(contactoText.getValue());
		ni.setDepto(deptoType.getSelectedItem().getLabel());
		ni.setTarifa(Float.parseFloat(tarifaText.getValue()));
		ni.setAdelanto(Float.parseFloat(adelantoText.getValue()));
		ni.setContent("Anticipo: " + ni.getAdelanto() + ", "
				+ ni.getNombreContacto() + ", " + nameText.getText() + " - "
				+ ni.getDepto());
		ni.setLocked(false);

		MyCalendarModel.dao.updateNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();

		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());

		evt.clearGhost();

		updateMyEntry.setVisible(false);
	}

	public void onClick$btnDeleteNews() {
		ReservasItem ni = ((ReservasItem) updateMyEntry.getAttribute("ni"));
		
		Window win = (Window) updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win
				.getVariable("cal", false);
		Chart piechart = (Chart) win.getVariable("piechart", false);

		MyCalendarModel.dao.deleteNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();

		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());

		updateMyEntry.setVisible(false);
	}

	public void onClick$btnCancel() {
		CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent) updateMyEntry
				.getAttribute("calevent"));
		updateMyEntry.setVisible(false);
		evt.clearGhost();
	}
	// Habilita el cambio de cliente
	public void onClick$btnCambioClienteReserva() {
		idCliente = 0;
		nameText.setReadonly(false);
		telefText.setReadonly(false);
		direText.setReadonly(false);
		nameText.setText("");
		telefText.setText("");
		direText.setText("");
	}
	// Carga los datos en ventana con datos de lista de Departamentos
	public void getDatosDepto(Integer id) {

		idDepto = id;

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoController = new DepartamentosJpaController(
				emf);
		DepartamentoEntity depto = deptoController.findDepartamentos(idDepto);

		String color = depto.getColor();

		System.out.println("Valor que devuelve el comoitem: " + idDepto);
		System.out.println("El color del departamento: " + color);

		if (color.equals("Rojo")) {
			colorRetorno = "red";
		} else if (color.equals("Azul")) {
			colorRetorno = "blue";
		} else if (color.equals("Verde")) {
			colorRetorno = "green";

		} else if (color.equals("Khaki")) {
			colorRetorno = "khaki";
		} else if (color.equals("Violeta")) {
			colorRetorno = "purple";
		}

	}
	// Carga los datos en ventana con datos de lista de clientes
	public void getDatosCliente(String clienteid) {
		// Recibe ID cliente reserva
		idCliente = Integer.valueOf(clienteid);

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		ClienteEntity client = clienteResController.findClientes(idCliente);
		// Setea ventana con datos del cliente seleccionado en bandvox
		nameText.setText(client.getRazonSocial());
		direText.setText(client.getDireccion());
		telefText.setText(client.getTelefono());

	}

}
