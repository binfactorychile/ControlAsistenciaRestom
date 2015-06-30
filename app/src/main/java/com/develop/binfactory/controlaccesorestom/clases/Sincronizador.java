package com.develop.binfactory.controlaccesorestom.clases;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalHashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


import com.develop.binfactory.controlaccesorestom.controladores.CtrlAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlTrabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Sincronizador {

    // private static final String NAMESPACE =
    // private static final String NAMESPACE
    // ="http://192.168.0.102/webservice/web/";
    // private static final String NAMESPACE
    // ="http://www.brisaexpress.cl/webservice2k657k/produccion/";
    //
    // private static final String NAMESPACE =
    // "http://www.brisaexpress.cl/webservice2k657k/test/";
    // private static final String NAMESPACE =
    // "http://www.brisaexpress.cl/webservice2k657k/produccion/";
    private static final String NAMESPACE = "http://192.168.1.210/webservice_restom/web/";

    // "http://www.onevoice.cl/ws_one_voice/Service.asmx";
    // private static String URL="http://192.168.2.:82/Service.asmx";
    // private static String URL = "http://192.168.2.12:83/Service1.asmx";

    // private static String URL = "http://192.168.2.9:83/Service1.asmx";
    private static String URL = NAMESPACE + "service.php";

    // http://192.168.2.11:83/Service1.asmx/getUsuarios
    private SoapObject request = null;
    private SoapSerializationEnvelope envelope = null;
    private SoapPrimitive resultsRequestSOAP = null;
    Gson gson;
    private ManagerProviderBD db;
    // public String acces_usuario_ID;
    private Context context;
    private HttpTransportSE transporte;
    public String mac_address;
    private String usuario_ID;

    public Sincronizador(Context _context, String usuario_ID) {
        db = new ManagerProviderBD(_context);
        this.context = _context;
        mac_address = getMacAddress();
        this.usuario_ID = usuario_ID;
    }

    public String traerDatosServidorPrincipal() {
        // limpiarTablas();
        String resultado;

        PropertyInfo pi = new PropertyInfo();
        pi.setName("mac_address");
        pi.setValue(mac_address);
        resultado = inicioPoblarBD("getTrabajadores", pi);
        if (resultado == "error") {
            Utils.escribeLog("Error en getTrabajadores, tablet->"
                    + mac_address);
        }

        return resultado;
    }

    public String enviarAsistenciasTrabajador() {
        String resultado = "";
        ArrayList arrSincroIDs = getSincronizacionTrabajadoresIDs();
        if (arrSincroIDs.size() > 0) {
            PropertyInfo pi = new PropertyInfo();
            pi.setName("mac_address");
            pi.setValue(mac_address);
            resultado = inicioPoblarBD("registrarAsistenciasTrabajador", pi);
            if (resultado == "error") {
                Utils.escribeLog("Error en registrarAsistenciasTrabajador, tablet->"
                        + mac_address);
            } else {
                //el resultado es ok, se procede a eliminar los registros de sincronizacion_trabajador
                ManagerProviderBD bd = new ManagerProviderBD(context);
                bd.open();
                for (int i = 0; i < arrSincroIDs.size(); i++) {
                    int ID = (int) arrSincroIDs.get(i);
                    String query = "DELETE FROM sincronizacion_trabajador WHERE ID=" + ID;
                    bd.ejecutaSinRetorno(query);
                }
                bd.close();
            }
        }
        return resultado;
    }

    private String getMacAddress() {
        WifiManager wifiMan = (WifiManager) this.context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        if (wifiInf.getIpAddress() != 0) {
            String macAddr = wifiInf.getMacAddress().toLowerCase();
            return macAddr;
        } else {
            return "no";
        }
    }


    private void limpiaVenta(String venta_ID) {
        // db.open();
        db.ejecutaSinRetorno("DELETE FROM detalle_venta WHERE venta_ID="
                + venta_ID);
        db.ejecutaSinRetorno("DELETE FROM venta WHERE ID=" + venta_ID);
        // db.close();
    }

    private String inicioPoblarBD(String funcionWS, PropertyInfo pi) {
        String posicionError = "1";
        request = new SoapObject(NAMESPACE, funcionWS);
        if (pi != null)
            request.addProperty(pi);

        if (funcionWS == "registrarAsistenciasTrabajador") {
            //obtener los id
            String query = "SELECT at.* " +
                    "from asistencia_trabajador AS at, sincronizacion_asistencia AS sa" +
                    "WHERE at.ID = sa.registro_ID";
            ArrayList<Asistencia_trabajador> arrAsistenciaTrabajador = CtrlAsistencia_trabajador.getListado(query, context);
            gson = new Gson();
            String arrSincroAsistenciaJSON = gson.toJson(arrAsistenciaTrabajador);
            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("arrSincroAsistenciaJSON");
            pi2.setValue(arrSincroAsistenciaJSON);
            request.addProperty(pi2);
        }
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.encodingStyle = "utf-8";
        envelope.enc = SoapSerializationEnvelope.ENC2003;
        envelope.xsd = SoapEnvelope.XSD;
        envelope.xsi = SoapEnvelope.XSI;
        envelope.dotNet = false; // se asigna true para el caso de que el WS sea
        // de dotNet
        envelope.setAddAdornments(false);

        envelope.setOutputSoapObject(request);
        try {
            if (transporte == null)
                transporte = new HttpTransportSE(URL);

            transporte
                    .setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            // db.open();
            transporte.call("http://tempuri.org/" + funcionWS, envelope);
            resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            posicionError = "2";
            String strJSON = resultsRequestSOAP.toString();

            posicionError = "3";

            if (funcionWS == "getTrabajadores")
                getTrabajadores(strJSON);
            posicionError = "4";
            // db.close();
            return "ok";

        } catch (IOException e) {
            Utils.escribeLog(e, "inicioPoblarBD->" + funcionWS + "."
                    + posicionError);
            return "error";
        } catch (XmlPullParserException e) {
            Utils.escribeLog(e, "inicioPoblarBD->" + funcionWS + "."
                    + posicionError);
            return "error";
        } catch (Exception e) {
            Utils.escribeLog(e, "inicioPoblarBD->" + funcionWS + "."
                    + posicionError);

            return "error";

        }

    }

    private ArrayList getSincronizacionTrabajadoresIDs() {
        ArrayList arrSincroTrabajadoresID = new ArrayList();
        String query = "select ID from sincronizacion_asistencia";
        ManagerProviderBD bd = new ManagerProviderBD(context);
        bd.open();
        Cursor cursor = bd.ejecutaConRetorno(query);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            int sincronizacion_ID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));
            arrSincroTrabajadoresID.add(sincronizacion_ID);
            cursor.moveToNext();
        }
        bd.close();
        return arrSincroTrabajadoresID;
    }

    private String consultarWS(String funcionWS, PropertyInfo pi) {
        request = new SoapObject(NAMESPACE, funcionWS);
        if (pi != null)
            request.addProperty(pi);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false; // se asigna true para el caso de que el WS sea
        // de dotNet
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        String posicionError = "1";
        try {
            if (transporte == null)
                transporte = new HttpTransportSE(URL);
            posicionError = "2";
            // db.open();
            transporte.call("http://tempuri.org/" + funcionWS, envelope);
            posicionError = "3";
            resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            posicionError = "4";
            String strJSON = resultsRequestSOAP.toString();

            return strJSON;

        } catch (IOException e) {
            Utils.escribeLog(e, "consultarWS->" + funcionWS + "."
                    + posicionError);
            return "error";
        } catch (XmlPullParserException e) {
            Utils.escribeLog(e, "consultarWS->" + funcionWS + "."
                    + posicionError);
            return "error";
        }

    }

    private void getTrabajadores(String listaJSON) {
        // se crea el objeto que ayuda deserealizar la cadena JSON
        gson = new Gson();

        try {
            if (!listaJSON.equals("vacio")) {

                Type tipoArreglo = new TypeToken<ArrayList<Trabajador>>() {
                }.getType();
                ArrayList<Trabajador> arrTrabajador = new ArrayList();
                arrTrabajador = gson.fromJson(listaJSON, tipoArreglo);
                ArrayList arrSincronizacionID = new ArrayList();
                for (Trabajador trabajador : arrTrabajador) {
                    if (!CtrlTrabajador.existeTrabajador(trabajador.getRut(), context))
                        trabajador.ingresar(this.context);
                    else
                        trabajador.actualizar(context);

                    if (trabajador.fsincronizacion_ID > 0) {
                        arrSincronizacionID.add(trabajador.fsincronizacion_ID);
                    }
                }

                eliminarSincronizaTabletTrabajadores(arrSincronizacionID);
            }
        } catch (Exception e) {
            Utils.escribeLog(e, "getUsuarios");
        }
    }

    private void eliminarSincronizaTabletTrabajadores(ArrayList arrSincronizacionTablet) {
        request = new SoapObject(NAMESPACE,
                "eliminarSincronizaTabletTrabajadores");

        PropertyInfo pi = new PropertyInfo();

        //TODO: llenar el arreglo
        String arrJsonSincronizacionTablet = "";

        arrJsonSincronizacionTablet = gson.toJson(arrSincronizacionTablet);
        pi.setName("arrSincronizacionTablet");
        pi.setValue(arrJsonSincronizacionTablet);
        request.addProperty(pi);
        guardarEnWs("eliminarSincronizaTabletTrabajadores");
    }

    private void addPropertyRequest(String nombreAtributo, Cursor cursor) {
        PropertyInfo pi = new PropertyInfo();
        pi = new PropertyInfo();
        pi.setName(nombreAtributo);
        pi.setValue(cursor.getString(cursor.getColumnIndex(nombreAtributo)));
        request.addProperty(pi);

    }

    private void addPropertyRequest(String nombreAtributo, String valor) {
        PropertyInfo pi = new PropertyInfo();
        pi = new PropertyInfo();
        pi.setName(nombreAtributo);
        pi.setValue(valor);
        request.addProperty(pi);

    }


	/*public String enviarDatosServidorPrincipal() {
        String posicionError = "1";
		try {
			db.open();
			clsMantenimiento mantenimiento = new clsMantenimiento(context);
			posicionError = "2";
			String mensajeMantenimiento = mantenimiento.respaldarBD();
			posicionError = "3";
			if (mensajeMantenimiento.equals("ok")) {
				PropertyInfo pi;
				// (string nombre, string direccion, string ciudad, string
				// razon_social, string telefono, string giro, string rut,
				// string usuario_ID)

				posicionError = "4";

				String query = "SELECT ID,usuario_ID,cuenta_credito_ID,fecha,numero, total_descuento,total_bruto,cliente_proveedor_ID,observacion FROM venta WHERE estado LIKE '3'";
				Cursor curVentas = db.ejecutaConRetorno(query);
				posicionError = "16";
				curVentas.moveToFirst();
				String venta_ID;
				String venta_local_ID;
				String detalle_venta_ID;
				Cursor curDetalleVentas;

				// ingresarVentas(string usuario_ID, string cuenta_credito_ID,
				// string fecha_venta, string total_descuento, string
				// total_bruto,
				// string cliente_proveedor_ID, string coordenadas_GPS)
				Venta venta;
				ventaJson ventaJson;
				Detalle_ventaJson detalleVentaJson;
				gson = new Gson();
				String jsonVenta = "";
				String arrJsonDetalleVenta = "";
				while (curVentas.isAfterLast() == false) {
					ventaJson = new ventaJson(curVentas);
					posicionError = "17";
					venta_local_ID = curVentas.getString(curVentas
							.getColumnIndex("ID"));

					jsonVenta = gson.toJson(ventaJson);
					query = "SELECT producto_ID,cantidad,precio_unitario,descuento,iva,total,estado,venta_ID FROM detalle_venta WHERE venta_ID="
							+ curVentas.getString(curVentas
									.getColumnIndex("ID"));
					curDetalleVentas = db.ejecutaConRetorno(query);
					posicionError = "18";
					curDetalleVentas.moveToFirst();
					ArrayList<Detalle_ventaJson> arrDetalleVenta = new ArrayList<Detalle_ventaJson>();
					Detalle_venta detalleVenta;
					request = new SoapObject(NAMESPACE,
							"ingresaDetalleVentasJsonMin");
					posicionError = "19";
					Producto producto=new Producto();
					while (curDetalleVentas.isAfterLast() == false) {
						detalleVentaJson = new Detalle_ventaJson(
								curDetalleVentas);
						posicionError = "20";
						producto=CtrlProducto.getProducto(detalleVentaJson.f2, context);
						producto.fstock_actual=producto.fstock_actual-detalleVentaJson.f3;
						producto.actualizar(context);
						arrDetalleVenta.add(detalleVentaJson);
						curDetalleVentas.moveToNext();
					}
					arrJsonDetalleVenta = gson.toJson(arrDetalleVenta);
					// gson.fromJson(json, classOfT)
					pi = new PropertyInfo();
					pi.setName("arreglo_json");
					pi.setValue(arrJsonDetalleVenta);
					request.addProperty(pi);
					posicionError = "21";
					pi = new PropertyInfo();
					pi.setName("venta_json");
					pi.setValue(jsonVenta);
					request.addProperty(pi);
					posicionError = "22";
					String resultado = guardarEnWs("ingresaDetalleVentasJsonMin");
					posicionError = "23";
					curDetalleVentas.close();

					// request = new SoapObject(NAMESPACE,
					// "finalizarIngresoVenta");
					// pi = new PropertyInfo();
					// pi.setName("venta_ID");
					// pi.setValue(venta_ID);
					// request.addProperty(pi);
					// resultadoIngresoVenta =
					// guardarEnWs("finalizarIngresoVenta");
					if (resultado.equals("error")) {
						return "error";
					} else {
						limpiaVenta(venta_local_ID);
					}
					posicionError = "24";
					// }// if venta
					curVentas.moveToNext();

				}
				curVentas.close();

			}
			db.close();
			return mensajeMantenimiento;

		} catch (Exception e) {
			Utils.escribeLog(e, "enviarDatosServidorPrincipal->"
					+ posicionError);
			return "error";
		}
	}*/

    private String guardarEnWs(String funcionWS) {
        String posicionError = "1";
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false; // se asigna true para el caso de que el WS sea
        // de dotNet
        envelope.setAddAdornments(false);

        List<HeaderProperty> headers = new ArrayList<HeaderProperty>();
        // AndroidHttpClient.getMinGzipSize(context.getContentResolver());
        // HeaderProperty headerProperty=new HeaderProperty("Accept-Encoding",
        // "gzip");
        // headers.add(headerProperty);

        envelope.setOutputSoapObject(request);
        if (transporte == null)
            transporte = new HttpTransportSE(URL);
        try {
            posicionError = "2";
            transporte.call("http://tempuri.org/" + funcionWS, envelope,
                    headers);
            resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            posicionError = "3";
            String resultado = resultsRequestSOAP.toString();
            posicionError = "4";
            return resultado;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Utils.escribeLog(e, "guardarEnWs." + funcionWS + "."
                    + posicionError);
            Log.d("excepcion personalizada", e.getMessage());
            e.printStackTrace();
            return "error";
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            Utils.escribeLog(e, "guardarEnWs." + funcionWS + "."
                    + posicionError);
            Log.d("excepcion personalizada", e.getMessage());
            e.printStackTrace();
            return "error";
        } catch (Exception e) {
            Utils.escribeLog(e, "guardarEnWs." + funcionWS + "."
                    + posicionError);
            return "error";
        }
    }

    public Object soap(String METHOD_NAME, String SOAP_ACTION,
                       String NAMESPACE, String URL) throws IOException,
            XmlPullParserException {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("mac_address", "00:90:a2:42:57:f2");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        httpTransport.call(SOAP_ACTION, envelope);
        Object result = envelope.getResponse();
        return result;
    }

    public boolean existeConectividad() {
        request = new SoapObject(NAMESPACE, "existeConectividad");
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // PropertyInfo pi = new PropertyInfo();
        //
        // pi.setName("mac_address");
        // pi.setValue("00:90:a2:42:57:f2");
        //
        // request.addProperty(pi);
        envelope.dotNet = false;// true; // se asigna true para el caso de que
        // el WS sea de .NET
        // ////////

        // envelope.encodingStyle = SoapEnvelope.ENC2003;
        envelope.setAddAdornments(false);

        // envelope.implicitTypes = true;

        // ////////
        envelope.setOutputSoapObject(request);
        if (transporte == null)
            transporte = new HttpTransportSE(URL);

        try {

            transporte.debug = true;
            // transporte.requestDump="";

            transporte.call(NAMESPACE + "existeConectividad", envelope);
            // transporte.call("http://tempuri.org/getUsuarios", envelope);
            (new MarshalHashtable()).register(envelope);
            resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            return true;

        } catch (IOException e) {
            Utils.escribeLog(e, "existeConectividad");
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e) {
            Utils.escribeLog(e, "existeConectividad");
            e.printStackTrace();
            return false;
        }

    }

    public boolean existeConectividad(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No s�lo wifi, tambi�n GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deber�a no ser tan �apa
        for (int i = 0; i < 2; i++) {
            // �Tenemos conexi�n? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        if (bConectado)
            bConectado = existeConectividad();

        return bConectado;
    }
}
