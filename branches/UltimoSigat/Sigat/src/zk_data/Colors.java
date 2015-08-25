package zk_data;

public final class Colors {
	public static String[] _colors = {"green", "red","blue"};
	public static String[] _type = {"Departamento 1", "Departamento 2","Departamento 3"};
	public static String[] _colorsEstado = {"purple","blue"};
	public static String[] _typeEstado = {"Reservado", "Ocupado"};
	
	public static int getColorPosition(String color) {
		for(int i=0; i<_colors.length; i++) {
			if(color.equals(_colors[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static String getColorType(String color) {
		String ret = "";
		
		for(int i=0; i<_colors.length; i++) {
			if(color.equals(_colors[i])) {
				ret= _type[i];
			}
		}
		
		return ret;
	}
	
	//Devuelve el color segun es estado de la reserva
	public static String getColorEstado(String estado)
	{
		
		for(int i=0; i<_typeEstado.length; i++) {
			if(estado.equals(_typeEstado[i])) {
				return _colorsEstado[i];
			}
		}
		
		return "";
	}
	
	//Devuelve la posicion de un departamento en el arreglo
	public static int getDeptoPosition(String depto) {
		for(int i=0; i<_type.length; i++) {
			if(depto.equals(_type[i])) {
				return i;
			}
		}
		
		return -1;
	}
}
