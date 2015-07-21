package com.develop.binfactory.controlaccesorestom.controladores;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.clases.Trabajador;
import com.develop.binfactory.controlaccesorestom.clases.TrabajadorJSON;
import com.develop.binfactory.controlaccesorestom.fachadas.FachadaTrabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlTrabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;

import android.content.Context;
import android.database.Cursor;


public class CtrlTrabajador {
    public static ArrayList<Trabajador> getListado(String query, Context context) {
        try {
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaTrabajador.getListado(query, bd);
            cursor.moveToFirst();
            ArrayList<Trabajador> arregloTrabajador = new ArrayList<Trabajador>();
            Trabajador trabajador;
            while (cursor.isAfterLast() == false) {
                trabajador = new Trabajador(cursor);
                arregloTrabajador.add(trabajador);
                cursor.moveToNext();
            }
            bd.close();
            return arregloTrabajador;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlTrabajador.getListado");
            return null;
        }
    }

    public static boolean existeTrabajador(int trabajador_ID, Context context){
        boolean existe = false;
        Trabajador trabajador = getTrabajador(trabajador_ID, context);
        if(trabajador.fID > 0)
            existe = true;
        return existe;
    }

    public static ArrayList<TrabajadorJSON> getListadoJSON(String query, Context context) {
        try {
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaTrabajador.getListado(query, bd);
            cursor.moveToFirst();
            ArrayList<TrabajadorJSON> arregloTrabajador = new ArrayList<TrabajadorJSON>();
            TrabajadorJSON trabajador;
            while (cursor.isAfterLast() == false) {
                trabajador = new TrabajadorJSON(cursor);
                arregloTrabajador.add(trabajador);
                cursor.moveToNext();
            }
            bd.close();
            return arregloTrabajador;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlTrabajador.getListadoJSON");
            return null;
        }
    }

    public static Trabajador getTrabajador(int ID, Context context) {
        try {
            String query = "SELECT ID as _id" +
                    ",nombre" +
                    ",rut" +
                    ",cliente_proveedor_ID" +
                    " FROM Trabajador WHERE ID=" + ID;
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaTrabajador.getListado(query, bd);
            cursor.moveToFirst();
            Trabajador objeto = new Trabajador(cursor);
            bd.close();
            return objeto;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlTrabajador.getTrabajador");
            return null;
        }
    }

    public static Trabajador getTrabajadorPorRut(String rut, Context context) {
        try {
            String query = "SELECT ID as _id" +
                    ",nombre" +
                    ",rut" +
                    ",cliente_proveedor_ID" +
                    " FROM Trabajador WHERE rut LIKE '" + rut+ "'";
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaTrabajador.getListado(query, bd);
            cursor.moveToFirst();
            Trabajador objeto = new Trabajador(cursor);
            bd.close();
            return objeto;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlTrabajador.getTrabajadorPorRut");
            return null;
        }
    }

    public static int ingresar(Trabajador objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        int ultima_ID = FachadaTrabajador.ingresar(objeto, bd);
        bd.close();
        return ultima_ID;

    }

    public static int ingresarJSON(TrabajadorJSON objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        int ultima_ID = FachadaTrabajador.ingresarJSON(objeto, bd);
        bd.close();
        return ultima_ID;

    }

    public static void actualizar(Trabajador objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaTrabajador.actualizar(objeto, bd);
        bd.close();
    }

    public static void actualizarJSON(TrabajadorJSON objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaTrabajador.actualizarJSON(objeto, bd);
        bd.close();
    }

    public static void eliminar(int ID, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaTrabajador.eliminar(ID, bd);
        bd.close();
    }

    public static boolean existe(int ID, Context context) {
        try {
            String query = "SELECT ID as _id" + " FROM Trabajador WHERE ID=" + ID;
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaTrabajador.getListado(query, bd);
            cursor.moveToFirst();
            boolean retorna = false;
            if (cursor.getCount() > 0) {
                if (!cursor.isNull(0))
                    retorna = true;

            }
            cursor.close();
            bd.close();
            return retorna;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlTrabajador.existe");
            return false;
        }
    }
}

//------------------------------------------------------------------------------
//	FIN CONTROLADOR
//------------------------------------------------------------------------------
