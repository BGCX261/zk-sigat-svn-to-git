package zk_controllers;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.lang.Strings;
//import org.zkoss.zk.au.out.AuClearWrongValue;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sun.java.swing.plaf.windows.resources.windows;

import entity.DepartamentoEntity;

public class deptoCRUDControler extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listbox personslb;
	Textbox nametb;
	Listbox titlelb;
	Window main;
	Window nuevoDeptoDialog = null;

	AnnotateDataBinder binder;

	List<entity.DepartamentoEntity> model = new ArrayList<entity.DepartamentoEntity>();
	List<String> titleModel = new ArrayList<String>();
	DepartamentoEntity selected;

	public deptoCRUDControler() {
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		/*
		 * comp.setVariable(comp.getId() + "Ctrl", this, true);
		 * 
		 * model.add(new Person("Brian", "Engineer")); model.add(new
		 * Person("John", "Tester")); model.add(new Person("Sala", "Manager"));
		 * model.add(new Person("Peter", "Architect"));
		 * 
		 * titleModel.add(""); titleModel.add("Engineer");
		 * titleModel.add("Tester"); titleModel.add("Manager");
		 * titleModel.add("Architect");
		 * 
		 * binder = new AnnotateDataBinder(comp); binder.loadAll();
		 */
	}

	public List getModel() {
		return model;
	}

	public List getTitleModel() {
		return titleModel;
	}

	public DepartamentoEntity getSelected() {
		return selected;
	}

	public void setSelected(DepartamentoEntity selected) {
		this.selected = selected;
	}

	public void onClick$add() {
		// llamar a la ventana nuevoDepto
		
		
		nuevoDeptoDialog = (Window) Executions.createComponents(
				"nuevoDepto.zul", main, null);

	}

	public void onClick$update() {
		if (selected == null) {
			alert("Nothing selected");
			return;
		}
		// selected.setName(getSelectedName());
		// selected.setTitle(getSelectedTitle());
		binder.loadAll();
	}

	public void onClick$delete() {
		if (selected == null) {
			alert("Nothing selected");
			return;
		}
		model.remove(selected);
		selected = null;

		binder.loadAll();
	}

	public void onSelect$titlelb() {
		closeErrorBox(new Component[] { titlelb });
	}

	public void onSelect$personslb() {
		closeErrorBox(new Component[] { nametb, titlelb });
	}

	private void closeErrorBox(Component[] comps) {
		for (Component comp : comps) {
			// Executions.getCurrent().addAuResponse(null,new
			// AuClearWrongValue(comp));
		}
	}

	private String getSelectedTitle() throws WrongValueException {
		int index = titlelb.getSelectedIndex();
		if (index < 1) {
			throw new WrongValueException(titlelb, "Must selecte one!");
		}

		return titleModel.get(index);
	}

	private String getSelectedName() throws WrongValueException {
		String name = nametb.getValue();
		if (Strings.isBlank(name)) {
			throw new WrongValueException(nametb, "Must not blank!");
		}
		return name;
	}

}
