package com.develop.binfactory.controlaccesorestom.clases;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;

import android.database.Cursor;
import android.content.Context;

public class Asistencia_trabajadorJSON {
    public int f0;//ID
    public String f1;//fecha
    public int f2;//trabajador_ID
    public int f3;//cliente_proveedor_ID
    public String f4;//horario

    //CONSTRUCTOR
    public Asistencia_trabajadorJSON(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));

            f1 = cursor.getString(cursor.getColumnIndex("fecha"));
            f2 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("trabajador_ID")));
            f3 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));
            f4 = cursor.getString(cursor.getColumnIndex("horario"));
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Asistencia_trabajador.Constructor");
        }
    }

    public Asistencia_trabajadorJSON() {
    }

    public int getID() {
        return f0;
    }

    public String getFecha() {
        if (f1 != null)
            return f1;
        else
            return "";
    }

    public int getTrabajador_ID() {
        return f2;
    }

    public int getCliente_proveedor_ID() {
        return f3;
    }

    public void setID(int ID) {
        this.f0 = ID;
    }

    public void setFecha(String fecha) {
        this.f1 = fecha;
    }

    public void setTrabajador_ID(int trabajador_ID) {
        this.f2 = trabajador_ID;
    }

    public void setCliente_proveedor_ID(int cliente_proveedor_ID) {
        this.f3 = cliente_proveedor_ID;
    }

    public String getHorario() {
        return f4;
    }

    public void setHorario(String horario) {
        this.f4 = horario;
    }

    public void actualizar(Context context) {
        try {
            CtrlAsistencia_trabajador.actualizarJSON(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Asistencia_trabajador.actualizarJSON");
        }
    }

    public int ingresar(Context context) {
        return CtrlAsistencia_trabajador.ingresarJSON(this, context);
    }

}//fin clase lógica

	
	
