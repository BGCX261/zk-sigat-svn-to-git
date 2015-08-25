package zk_models;

import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zul.SimplePieModel;

import zk_dao.ReservaDAO;
import zk_data.Colors;

public final class MyCalendarModel {
	
	//a static variable to the database functions
	public static ReservaDAO dao = new ReservaDAO();
	
	
	
	private SimpleCalendarModel _simpleCalendarModel;
	private SimplePieModel _simplePieModel;
	
	@SuppressWarnings("rawtypes")
	public MyCalendarModel() {
		_simpleCalendarModel = new SimpleCalendarModel();
		_simplePieModel = new SimplePieModel();
		
		int counts[] = {0, 0, 0};
		
		java.util.List lst = dao.selectAll();
		
		for(int i=0; i<lst.size(); i++) {
			ReservasItem ni = (ReservasItem) lst.get(i);
			_simpleCalendarModel.add(ni);
			
			int colorPos = Colors.getColorPosition(ni.getContentColor());
		
			if(colorPos >-1) {
				counts[colorPos]++;
			}
		}
		
		for(int j=0; j<3; j++) {
			Integer count = new Integer(counts[j]);
			_simplePieModel.setValue(Colors._type[j], count);
		}
	}

	public SimpleCalendarModel getSimpleCalendarModel() {
		return _simpleCalendarModel;
	}

	public SimplePieModel getSimplePieModel() {
		return _simplePieModel;
	}
}
