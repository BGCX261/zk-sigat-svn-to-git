package zk_controllers;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controller.ClientesJpaController;

import entity.ClienteEntity;
import zk_data.Colors;
import zk_models.MyCalendarModel;
import zk_models.ReservasItem;

public class ReservaCreationController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Window createMyEntry, cliReservaWin, cliReservaWin1;
	Combobox deptoType;
	Textbox nameText;
	Textbox contactoText;
	Textbox direText;
	Textbox telefText;
	Textbox tarifaText;
	Textbox adelantoText;
	Bandbox bd;
	int idCliente=0;
	ReservasItem nis = new ReservasItem();
	//Define los estados de ocupacion(Reservado, Ocupado)
	private String estadoOcupacion= "Reservado";

	public void prepareWindow(int left, int top) {
		deptoType.setSelectedIndex(0);
		createMyEntry.setLeft(left + "px");
		createMyEntry.setTop(top + "px");
	
		nameText.setText("");
		contactoText.setText("");
		direText.setText("");
		telefText.setText("");
		tarifaText.setText("");
		adelantoText.setText("");
		
		

	}

	public void onClick$btnAddReserva1() throws InterruptedException {
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));

		Window win = (Window) createMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win
				.getVariable("cal", false);
		Chart piechart = (Chart) win.getVariable("piechart", false);



		
		nameText.setText("");
		contactoText.setText("");
		direText.setText("");
		telefText.setText("");
		tarifaText.setText("");
		adelantoText.setText("");
		
		
		

	}

	public void onClick$btnAddReserva() throws InterruptedException {
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));

		Window win = (Window) createMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win
				.getVariable("cal", false);
		Chart piechart = (Chart) win.getVariable("piechart", false);
		/**********************CLientes*******************************/
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		/***************************************************/
	
		

		int selectedColor = deptoType.getSelectedIndex();

		if (selectedColor == -1)
			selectedColor = 0;

		String color = Colors._colors[selectedColor];
		
		//Define el color segun el estado de la reserva
		String colorEstado= Colors.getColorEstado(estadoOcupacion);		 
		ReservasItem ni = new ReservasItem();


		if (MyCalendarModel.dao.superposicion(evt.getBeginDate(), evt.getEndDate(), deptoType.getSelectedItem().getLabel())){
			try {
				Messagebox.show(
						"Superposicion de Departamento en rango de fecha.",
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				throw UiException.Aide.wrap(e);
			}
			return;
		}

		/*********************************************/
		if(idCliente==0)
		{
			
			ClienteEntity cliente= new ClienteEntity();
			cliente.setRazonSocial(nameText.getText());
			cliente.setTelefono(telefText.getText());
			cliente.setDireccion(direText.getText());
			clienteResController.create(cliente);
		}
		
		/********************************************/

		
		ni.setBeginDate(evt.getBeginDate());
		ni.setEndDate(evt.getEndDate());
		ni.setNombreContacto(contactoText.getValue());
		ni.setDepto(deptoType.getSelectedItem().getLabel());
		ni.setTarifa(Float.parseFloat(tarifaText.getValue()));
		ni.setAdelanto(Float.parseFloat(adelantoText.getValue()));	
		ni.setIDCliente(idCliente);
		//Define el borde a mostrar en la reserva
		ni.setHeaderColor(colorEstado);
		ni.setContentColor(color);
	//	ni.setContent("Anticipo: "+ni.getAdelanto()+", "+ni.getNombreContacto() +", "+ ni.getNombre() +" - "+ ni.getDepto());
		ni.setLocked(false);





		MyCalendarModel.dao.insertNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();

		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());

		evt.clearGhost();
		createMyEntry.setVisible(false);
	}

	
	
	
	
	public void onClick$btnCancel() {
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));
		createMyEntry.setVisible(false);
		evt.clearGhost();
	}
	
	public void getDatosCliente() {
		//Recibe ID cliente reserva
		idCliente= Integer.valueOf(bd.getValue());
		
		// creo el emf
				EntityManagerFactory emf = Persistence
						.createEntityManagerFactory("calendarPU");

				// instancio el manejador de la Entity
				ClientesJpaController clienteResController = new ClientesJpaController(
						emf);
				ClienteEntity client= clienteResController.findClientes(idCliente);
				nameText.setText(client.getRazonSocial());
				direText.setText(client.getDireccion());
				telefText.setText(client.getTelefono());
		
		}
		
	
	
}
