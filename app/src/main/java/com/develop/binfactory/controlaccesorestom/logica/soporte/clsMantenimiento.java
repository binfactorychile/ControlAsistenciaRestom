package com.develop.binfactory.controlaccesorestom.logica.soporte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.util.Log;

public class clsMantenimiento{

	private ManagerProviderBD db;
	public clsMantenimiento(Context context){
		 db= new ManagerProviderBD(context);
	}
    private void actualizarBD(){
    	
		db.open();
		//db.ejecutaSinRetorno("ALTER TABLE telefono ADD num_serie_IBM TEXT");
		/*
		db.ejecutaSinRetorno("ALTER TABLE usuario_telefono ADD TAG TEXT");
		db.ejecutaSinRetorno("ALTER TABLE telefono ADD mac_address TEXT");
		db.ejecutaSinRetorno("ALTER TABLE usuario_telefono ADD estado_envio TEXT");
		db.ejecutaSinRetorno("ALTER TABLE telefono ADD estado_envio TEXT");		
		db.ejecutaSinRetorno("ALTER TABLE ubicacion ADD estado_envio TEXT");
		db.ejecutaSinRetorno("ALTER TABLE toma_datos_terreno ADD estado_envio TEXT");
		db.ejecutaSinRetorno("ALTER TABLE historial_cambio ADD nombre_tabla TEXT");
		db.ejecutaSinRetorno("ALTER TABLE historial_cambio ADD id_registro TEXT");
		
		
		
		db.ejecutaSinRetorno("ALTER TABLE usuario_telefono ADD id_externo INTEGER");
		db.ejecutaSinRetorno("ALTER TABLE telefono ADD id_externo INTEGER");
		db.ejecutaSinRetorno("ALTER TABLE ubicacion ADD id_externo INTEGER");
		db.ejecutaSinRetorno("ALTER TABLE toma_datos_terreno ADD id_externo INTEGER");
		*/
		
		
//		db.ejecutaSinRetorno("ALTER TABLE campo_alterado ADD nombre_campo TEXT");
		
		db.close();
    }
    public String respaldarBD() {
        Log.e("Databasehealper", "********************************");
        try {
        	//					/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD
        	//					/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String fecha_actualizacion = df.format(c.getTime());
            
        	db.open();
        	
            //File f1 = new File("/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD");
        	String path=db.getPath();
            File f1 = new File(path);
            if (f1.exists()) {
                //File f2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+ "/My_App_Database.db");
            	File f2 = new File("/mnt/sdcard/Download/respaldoSigcomMeson" + fecha_actualizacion + ".db");
            	
                f2.createNewFile();
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
            db.close();
            return "ok";
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            return ex.getMessage() + " in the specified directory.";
            //System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return e.getMessage();
        }
//        Log.e("Databasehealper", "********************************");
    }
    public void restaurarBD() {
        Log.e("Databasehealper", "********************************");
        try {
        	//					/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD
        	//					/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String fecha_actualizacion = df.format(c.getTime());
            
       	   db.open();
        	
            //File f1 = new File("/data/data/binfactory.app.one_voice_final/databases/one_voice_terreno_BD");
        	String path_BD=db.getPath();
        	db.close();
            File file_BD_actual = new File(path_BD);
            if (file_BD_actual.exists()) {
                //File f2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+ "/My_App_Database.db");
            	//File file_BD_nueva = new File("/mnt/sdcard/Download/temp/respaldoBDoneVoice12_03_2012.db");
            	//File file_BD_nueva = new File("/mnt/sdcard/Download/temp/respaldoBDoneVoice22_03_2012_coloso_inicial.db");
            	File file_BD_nueva = new File("/mnt/sdcard/Download/respaldoSigcom22_03_2014_14_10_02.db");
            	if (file_BD_nueva.exists())
            	{
                //f2.createNewFile();
	                InputStream in = new FileInputStream(file_BD_nueva);
	                OutputStream out = new FileOutputStream(file_BD_actual);
	                byte[] buf = new byte[1024];
	                int len;
	                while ((len = in.read(buf)) > 0) {
	                    out.write(buf, 0, len);
	                }
	                in.close();
	                out.close();
            	}
            }
//            db.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Log.e("Databasehealper", "********************************");
    }	
}
