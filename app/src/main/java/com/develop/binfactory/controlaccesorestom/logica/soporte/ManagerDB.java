package com.develop.binfactory.controlaccesorestom.logica.soporte;




import com.develop.binfactory.controlaccesorestom.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ManagerDB extends SQLiteOpenHelper {
	// nombre y version de la base de datos
	static final String NOMBRE_BD = "controlAccesoRestom1.0";
	static final int DATA_BASE_VERSION = 1;
	private SQLiteDatabase sqliteDBinstancia = null;


	private static final String DB_CREATE_TRABAJADOR = "CREATE TABLE trabajador (" +
	"ID INTEGER PRIMARY KEY AUTOINCREMENT" +
	",nombre TEXT" +
	",rut TEXT" +
	",cliente_proveedor_ID INTEGER" +
	");";

	private static final String DB_CREATE_ASISTENCIA_TRABAJADOR = "CREATE TABLE asistencia_trabajador (" +
			"ID INTEGER PRIMARY KEY AUTOINCREMENT" +
			",fecha TEXT" +
			",trabajador_ID INTEGER" +
			",cliente_proveedor_ID INTEGER" +
			",horario TEXT"+
			");";
	private static final String DB_CREATE_SINCRONIZACION_ASISTENCIA = "CREATE TABLE sincronizacion_asistencia (ID INTEGER PRIMARY KEY AUTOINCREMENT, registro_ID INTEGER NOT NULL);";
	/*private static final String DB_CREATE_SINCRONIZACION_ASISTENCIA = "CREATE TABLE \"sincronizacion_asistencia\" (" +
			"\"ID\" INTEGER AUTOINCREMENT," +
			"\"registro_ID\" INTEGER NOT NULL," +
			"PRIMARY KEY (\"ID\") ," +
			"CONSTRAINT \"fk_asistencia_trabajador\" FOREIGN KEY (\"registro_ID\") REFERENCES \"asistencia_trabajador\" (\"ID\")" +
			");";*/

	// CREATE TABLA DE CLIENTE_PRODUCTO_COMPUESTO
	private static final String DB_CREATE_CLIENTE_PRODUCTO_COMPUESTO = "CREATE TABLE cliente_producto_compuesto (" +
			"ID INTEGER PRIMARY KEY AUTOINCREMENT" +
			",producto_compuesto_ID INTEGER" +
			",cliente_proveedor_ID INTEGER" +
			",precio INTEGER" +
			",nombre_producto TEXT" +

			");";
	
	static final String NOMBRE_ALL_DB = "nombre";

	public ManagerDB(Context context) {
		super(context, NOMBRE_BD, null, DATA_BASE_VERSION);
	}

	
	
	@Override
	public void onCreate(SQLiteDatabase sqliteDBinstancia) {
		sqliteDBinstancia.execSQL(DB_CREATE_TRABAJADOR);
        sqliteDBinstancia.execSQL(DB_CREATE_ASISTENCIA_TRABAJADOR);
		sqliteDBinstancia.execSQL(DB_CREATE_SINCRONIZACION_ASISTENCIA);
		sqliteDBinstancia.execSQL(DB_CREATE_CLIENTE_PRODUCTO_COMPUESTO);
	}

	// CREACION DE INSTANCIA PARA ABRIR BASE DE BASE DE DATOS
	public synchronized void open() {
		this.sqliteDBinstancia = this.getWritableDatabase();
		Log.d("managerBD:162 ", "OK");

	}

	// CIERRE DE LA CONEXION CON LA BASE DE DATOS
	public synchronized void close() {
		try {
			if (sqliteDBinstancia != null) {
				if (sqliteDBinstancia.isOpen()) {
					Log.d("exc ", "bd cerrado");
					this.sqliteDBinstancia.close();
				} else {
					Log.d("exc ", "no est� abierto");
				}
			} else
				Log.d("nullr ", "db null");
		} catch (NullPointerException ex) {
			Utils.escribeLog(ex.getMessage() + " --  " + ex.getStackTrace()
					+ " --" + ex.getCause());
			Log.d("nullr ", "cay�");
		}
	}


	// FIN INSERT CATEGORIA
	// EJECUTAR CON RETORNO
	public Cursor ejecutaConRetorno(String query) {

		this.sqliteDBinstancia = this.getReadableDatabase();
		Log.d("ejecutaconretorno", query);
		Cursor fila = sqliteDBinstancia.rawQuery(query, null);
		return fila;
	}

	// FIN EJECUTA CON RETORNO


	@Override
	public void onUpgrade(SQLiteDatabase db, int vieja_version,
			int nueva_version) {

//		db.execSQL("drop table if exists cliente_proveedor");
//		
//		db.execSQL("drop table if exists cliente_proveedor");
//		db.execSQL("drop table if exists lista_precios");
//		db.execSQL("drop table if exists producto_join_lista_precios");
//		db.execSQL("drop table if exists precio_por_cliente");
//		db.execSQL(DB_CREATE_CLIENTE_PROVEEDOR);
//		db.execSQL(DB_CREATE_LISTA_PRECIOS);
//		db.execSQL(DB_CREATE_PRODUCTO_JOIN_LISTA_PRECIOS);
//		db.execSQL(DB_CREATE_PRECIO_POR_CLIENTE);
//		db.execSQL(DB_CREATE_PRECIO_POR_VOLUMEN);
//
//
//		onCreate(db);
	}

}
