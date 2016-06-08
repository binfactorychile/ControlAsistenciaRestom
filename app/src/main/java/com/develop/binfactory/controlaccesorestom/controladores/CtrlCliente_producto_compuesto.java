package com.develop.binfactory.controlaccesorestom.controladores;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.clases.Cliente_producto_compuesto;
import com.develop.binfactory.controlaccesorestom.clases.Cliente_producto_compuestoJSON;
import com.develop.binfactory.controlaccesorestom.fachadas.FachadaCliente_producto_compuesto;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlCliente_producto_compuesto;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;

import android.content.Context;
import android.database.Cursor;


public class CtrlCliente_producto_compuesto {
    public static ArrayList<Cliente_producto_compuesto> getListado(String query, Context context) {
        try {
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaCliente_producto_compuesto.getListado(query, bd);
            cursor.moveToFirst();
            ArrayList<Cliente_producto_compuesto> arregloCliente_producto_compuesto = new ArrayList<Cliente_producto_compuesto>();
            Cliente_producto_compuesto cliente_producto_compuesto;
            while (cursor.isAfterLast() == false) {
                cliente_producto_compuesto = new Cliente_producto_compuesto(cursor);
                arregloCliente_producto_compuesto.add(cliente_producto_compuesto);
                cursor.moveToNext();
            }
            bd.close();
            return arregloCliente_producto_compuesto;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlCliente_producto_compuesto.getListado");
            return null;
        }
    }

    public static ArrayList<Cliente_producto_compuestoJSON> getListadoJSON(String query, Context context) {
        try {
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaCliente_producto_compuesto.getListado(query, bd);
            cursor.moveToFirst();
            ArrayList<Cliente_producto_compuestoJSON> arregloCliente_producto_compuesto = new ArrayList<Cliente_producto_compuestoJSON>();
            Cliente_producto_compuestoJSON cliente_producto_compuesto;
            while (cursor.isAfterLast() == false) {
                cliente_producto_compuesto = new Cliente_producto_compuestoJSON(cursor);
                arregloCliente_producto_compuesto.add(cliente_producto_compuesto);
                cursor.moveToNext();
            }
            bd.close();
            return arregloCliente_producto_compuesto;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlCliente_producto_compuesto.getListadoJSON");
            return null;
        }
    }

    public static Cliente_producto_compuesto getCliente_producto_compuesto(int ID, Context context) {
        try {
            String query = "SELECT ID as _id" +
                    ",producto_compuesto_ID" +
                    ",cliente_proveedor_ID" +
                    ",precio" +
                    ",nombre_producto" +
                    " FROM Cliente_producto_compuesto WHERE ID=" + ID;
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaCliente_producto_compuesto.getListado(query, bd);
            cursor.moveToFirst();
            Cliente_producto_compuesto objeto = new Cliente_producto_compuesto(cursor);
            bd.close();
            return objeto;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "CtrlCliente_producto_compuesto.getCliente_producto_compuesto");
            return null;
        }
    }

    public static int ingresar(Cliente_producto_compuesto objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        int ultima_ID = FachadaCliente_producto_compuesto.ingresar(objeto, bd);
        bd.close();
        return ultima_ID;

    }

    public static int ingresarJSON(Cliente_producto_compuestoJSON objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        int ultima_ID = FachadaCliente_producto_compuesto.ingresarJSON(objeto, bd);
        bd.close();
        return ultima_ID;

    }

    public static void actualizar(Cliente_producto_compuesto objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaCliente_producto_compuesto.actualizar(objeto, bd);
        bd.close();
    }

    public static void actualizarJSON(Cliente_producto_compuestoJSON objeto, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaCliente_producto_compuesto.actualizarJSON(objeto, bd);
        bd.close();
    }

    public static void eliminar(int ID, Context context) {
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        FachadaCliente_producto_compuesto.eliminar(ID, bd);
        bd.close();
    }

    public static boolean existe(int ID, Context context) {
        try {
            String query = "SELECT ID as _id" + " FROM Cliente_producto_compuesto WHERE ID=" + ID;
            ManagerProviderBD bd = new ManagerProviderBD(context);
            bd.open();
            Cursor cursor = FachadaCliente_producto_compuesto.getListado(query, bd);
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
            Utils.escribeLog(ex, "CtrlCliente_producto_compuesto.existe");
            return false;
        }
    }
}

//------------------------------------------------------------------------------
//	FIN CONTROLADOR
//------------------------------------------------------------------------------
