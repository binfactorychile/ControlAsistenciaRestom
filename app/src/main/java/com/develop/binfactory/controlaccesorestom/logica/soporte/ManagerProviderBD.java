package com.develop.binfactory.controlaccesorestom.logica.soporte;




import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ManagerProviderBD {
	
	private Context _context;
	private ManagerDB openhelper;
	private SQLiteDatabase db;
	public ManagerProviderBD(Context context){
		_context=context;
		this.openhelper =  new ManagerDB(this._context);
		
//		bdConnect=new BDCONNECT(this._context);
		Log.d("ManagerProviderBD:24 " ,"ok");
	}
	public ManagerProviderBD open(){
		this.db= openhelper.getWritableDatabase();
		Log.d("ManagerProviderBD:28 ","ok");
		return this;
	}
	public void close(){
		try
		{
		if(db!=null)
		{
			if(db.isOpen())
			{
				this.db.close();
			}
			else
			{
				Log.d("exc ","no est abierto");
			}
		}
		else
			Log.d("nullr ","db null");
		}
		catch (NullPointerException ex) {
			Utils.escribeLog(ex.getMessage() + " --  " + ex.getStackTrace() + " --" + ex.getCause());
			Log.d("nullr ","cay");
		}
	}
//	public void enListarDatos(){
//		openhelper.insertSite(db);
//		openhelper.insertEdificio(db);
//		openhelper.insertTipoUbicacion(db);
//		openhelper.insertFuncionalidadTelefono(db);
//	}
	public String getPath()
	{
	 return	db.getPath();
		
	}
	public boolean isOpen(){
		return db.isOpen();
	}
	public Cursor ejecutaConRetorno(String query)
	{
		try
		{
		//String temp= db.getPath();
//		escribeLog(query);
			Utils.escribeLog(query);
			//Log.d("ManagerProviderBD:71 ejecutaconretorno",query);
			Cursor fila=db.rawQuery(query, null);
		
			return fila;
		}
		catch(Exception ex)
		{
			Utils.escribeLog(ex,"ManagerProviderBD.ejecutaConRetorno");
			return null;
		}
	}
	
	public void ejecutaSinRetorno(String query)
	{
		Utils.escribeLog(query);
		db.execSQL(query);
		
	}

	public String guardarHistorial(String estado, String acces_usuario_ID,String nombre_tabla,String id_registro)
	{
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha_actualizacion = df.format(c.getTime());
		String query="INSERT INTO historial_cambio (fecha_actualizacion,acces_usuario_ID,estado,nombre_tabla,id_registro) VALUES ('" + fecha_actualizacion + "'," + acces_usuario_ID + ",'" + estado + "','" + nombre_tabla + "','" + id_registro + "')";
		ejecutaSinRetorno(query);
		Cursor cursor=ejecutaConRetorno("SELECT MAX(ID) maximo FROM historial_cambio");
		cursor.moveToPosition(0);
		return cursor.getString(0);		
	}
	public String guardarCampoAlterado(String nombre_campo, String valor_anterior, String valor_nuevo, String historial_cambio_ID, String estado)
	{
		if(valor_anterior!=null)
		{
			if(!valor_anterior.equals(valor_nuevo))
			{
				String query="INSERT INTO campo_alterado (nombre_campo,valor_anterior,valor_nuevo,historial_cambio_ID, estado) VALUES ('" + nombre_campo +  "','" + valor_anterior + "','" + valor_nuevo + "'," + historial_cambio_ID + ",'" + estado + "')";
				ejecutaSinRetorno(query);
				Cursor cursor=ejecutaConRetorno("SELECT MAX(ID) maximo FROM campo_alterado");
				cursor.moveToPosition(0);
				return cursor.getString(0);
			}
			else
				return "";
		}
		else
			return "";
			
	}
	

	
}
