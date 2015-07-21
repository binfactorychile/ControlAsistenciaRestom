package com.develop.binfactory.controlaccesorestom.clases;

import java.sql.Date;
import java.util.ArrayList;
import java.lang.String;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;
import android.database.Cursor;
import android.content.Context;

public class Asistencia_trabajador {
    public int fID;
    public String ffecha;
    public int ftrabajador_ID;
    public int fcliente_proveedor_ID;
    public String fhorario;

    //CONSTRUCTOR
    public Asistencia_trabajador(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));
            ffecha = cursor.getString(cursor.getColumnIndex("fecha"));
            ftrabajador_ID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("trabajador_ID")));
            fcliente_proveedor_ID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));
            fhorario = cursor.getString(cursor.getColumnIndex("horario"));
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Asistencia_trabajador.Constructor");
        }
    }

    public Asistencia_trabajador() {
    }

    public int getID() {
        return fID;
    }

    public String getFecha() {
        if (ffecha != null)
            return ffecha;
        else
            return "";
    }

    public int getTrabajador_ID() {
        return ftrabajador_ID;
    }

    public int getCliente_proveedor_ID() {
        return fcliente_proveedor_ID;
    }

    public void setID(int ID) {
        this.fID = ID;
    }

    public void setFecha(String fecha) {
        this.ffecha = fecha;
    }

    public void setTrabajador_ID(int trabajador_ID) {
        this.ftrabajador_ID = trabajador_ID;
    }

    public void setCliente_proveedor_ID(int cliente_proveedor_ID) {
        this.fcliente_proveedor_ID = cliente_proveedor_ID;
    }

    public String getHorario() {
        return fhorario;
    }

    public void sethorario(String horario) {
        this.fhorario = horario;
    }

    public void actualizar(Context context) {
        try {
            CtrlAsistencia_trabajador.actualizar(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Asistencia_trabajador.actualizar");
        }
    }

    public int ingresar(Context context) {
        return CtrlAsistencia_trabajador.ingresar(this, context);
    }

    public void eliminar(Context context) {
        try {
            CtrlAsistencia_trabajador.eliminar(this.fID, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Asistencia_trabajador.eliminar");
        }
    }


    public Trabajador getTrabajador(Context context) {
        return CtrlTrabajador.getTrabajador(this.ftrabajador_ID, context);
    }

   /* public Cliente_proveedor getCliente_proveedor(Context context) {
        return CtrlCliente_proveedor.getCliente_proveedor(this.fcliente_proveedor_ID, context);
    }*/

}//fin clase lógica

	
	
