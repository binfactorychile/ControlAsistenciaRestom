package com.develop.binfactory.controlaccesorestom.fachadas;
import com.develop.binfactory.controlaccesorestom.clases.*;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import android.content.Context;
import android.database.Cursor;


public class FachadaTrabajador {

	public static Cursor getListado(String query, ManagerProviderBD bd)
	{
		try
	{
		
		Cursor cursor=bd.ejecutaConRetorno(query);
		return cursor;
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.getListado");
		return null;
	}
	}
	
	public static int ingresarJSON(TrabajadorJSON objeto,ManagerProviderBD bd)
	{
		try
	{
		
		String query="INSERT INTO Trabajador (" +
		"nombre" +
		",rut" +
		",cliente_proveedor_ID" +
		") VALUES (" +
		"'" + objeto.getNombre() + "'" +
		",'" + objeto.getRut() + "'" +
		",'" + objeto.getCliente_proveedor_ID() + "'" +
		")";
		
		bd.ejecutaSinRetorno(query);
		Cursor cursor=bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Trabajador");
		cursor.moveToPosition(0);
		return Integer.valueOf(cursor.getString(0));
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.ingresar");
		return 0;
	}
	}
	
	public static int ingresar(Trabajador objeto,ManagerProviderBD bd)
	{
		try
	{
		
		String query="INSERT INTO Trabajador (" +
		"nombre" +
		",rut" +
		",cliente_proveedor_ID" +
		") VALUES (" +
		"'" + objeto.getNombre() + "'" +
		",'" + objeto.getRut() + "'" +
		",'" + objeto.getCliente_proveedor_ID() + "'" +
		")";
		
		bd.ejecutaSinRetorno(query);
		Cursor cursor=bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Trabajador");
		cursor.moveToPosition(0);
		return Integer.valueOf(cursor.getString(0));
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.ingresar");
		return 0;
	}
	}
	public static void actualizar(Trabajador objeto, ManagerProviderBD bd)
	{
		try
	{
		String query="UPDATE Trabajador SET " +
		"nombre='" + objeto.getNombre() + "'" +
		",rut='" + objeto.getRut() + "'" +
		",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
		" WHERE ID=" + objeto.getID();
		
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.actualizar");
	}
	}
	
	public static void actualizarJSON(TrabajadorJSON objeto, ManagerProviderBD bd)
	{
		try
	{
		String query="UPDATE Trabajador SET " +
		"nombre='" + objeto.getNombre() + "'" +
		",rut='" + objeto.getRut() + "'" +
		",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
		" WHERE ID=" + objeto.getID();
		
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.actualizar");
	}
	}
	
	
	public static void eliminar(int ID, ManagerProviderBD bd)
	{
		try
	{
		String query="DELETE FROM Trabajador WHERE ID=" + ID;
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaTrabajador.eliminar");
	}
	}
	
	}//Fin Clase
	
