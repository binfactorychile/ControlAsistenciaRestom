package com.develop.binfactory.controlaccesorestom.logica.soporte;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;



import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class Utils {
	public static String getVariablesSession(Context context) {
		ManagerProviderBD bd = new ManagerProviderBD(context);
		bd.open();
		Cursor cursor = bd
				.ejecutaConRetorno("SELECT ID,valor, clave FROM variable_sesion order by valor");// se
																									// cambi�
																									// ID_externo
																									// a
																									// ID
		String variables = "";
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			String temp = cursor.getString(cursor.getColumnIndex("ID"));
			temp = cursor.getString(cursor.getColumnIndex("clave"));
			temp = cursor.getString(cursor.getColumnIndex("valor"));
			variables += "\n id:"
					+ cursor.getString(cursor.getColumnIndex("ID")) + " cl:"
					+ cursor.getString(cursor.getColumnIndex("clave")) + " vl:"
					+ cursor.getString(cursor.getColumnIndex("valor"));
			cursor.moveToNext();
		}
		cursor.close();
		bd.close();
		return variables;
	}

	public static void escribeLog(String text) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy");
		String fecha = df.format(c.getTime());
		File logFile = new File("/mnt/sdcard/download/" + fecha + "sqllog.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));

			df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

			String fecha_hora = df.format(c.getTime());
			buf.append(fecha_hora + ":" + text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void escribeLog(Exception ex, String origen) {
		String error = "";
		if (ex != null) {
			if (ex.getMessage() != null) {
				error = "mensaje: " + ex.getMessage();
			}
			if (ex.getStackTrace() != null) {
				error += " - stacktrace: " + ex.getStackTrace().toString();
			}
			if (ex.getCause() != null) {
				error += " - causa: " + ex.getCause().toString();
			}
			if (origen != "") {
				error += " - origen: " + origen;
			}
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		String fecha = df.format(c.getTime());
		String text = fecha + " -  " + error;// "mensaje:" + ex.getMessage() +
												// "- stacktrace:" +
												// ex.getStackTrace().toString()
												// + ",causa:" +
												// ex.getCause().toString();

		df = new SimpleDateFormat("dd_MM_yyyy");// _HH_mm_ss");
		fecha = df.format(c.getTime());
		File logFile = new File("/mnt/sdcard/download/" + fecha + "log.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));

			String fecha_hora = df.format(c.getTime());
			buf.append(fecha_hora + ":" + text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void cargaSpinner(Context context, Spinner spinner,
			String nombre_tabla) {
		ManagerProviderBD bd = new ManagerProviderBD(context);
		bd.open();
		Cursor cursor = bd.ejecutaConRetorno("SELECT ID as _id, nombre FROM "
				+ nombre_tabla + " order by nombre");// se cambi� ID_externo a
														// ID
		SimpleCursorAdapter adaptador = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, cursor,
				new String[] { "nombre" }, new int[] { android.R.id.text1 });
		adaptador
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adaptador);
		cursor.close();
		bd.close();
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void muestraMensaje(String mensaje, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(mensaje).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static boolean tieneDatos(Cursor cursor) {
		if (cursor.getCount() > 0) {
			if (!cursor.isNull(0))
				return true;
			else
				return false;
		} else {
			return false;
		}

	}
	/*
	public static String decompress(String zipText) throws IOException {
		byte[] compressed = Base64.decode(zipText);
		if (compressed.length > 4) {
			GZIPInputStream gzipInputStream = new GZIPInputStream(
					new ByteArrayInputStream(compressed, 4,
							compressed.length - 4));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int value = 0; value != -1;) {
				value = gzipInputStream.read();
				if (value != -1) {
					baos.write(value);
				}
			}
			gzipInputStream.close();
			baos.close();
			String sReturn = new String(baos.toByteArray(), "UTF-8");
			return sReturn;
		} else {
			return "";
		}
	}*/

	public static int seleccionaItemSpinner(Spinner spinner, long valor) {
		int pos = 0;
		SpinnerAdapter adaptador = spinner.getAdapter();
		for (int i = 0; i < adaptador.getCount(); i++) {
			long temp = adaptador.getItemId(i);
			if (String.valueOf(adaptador.getItemId(i)).equals(
					String.valueOf(valor))) {
				return i;
				// sp_cargo.setSelection(i);
			}
		}
		return 0;
	}

	public static String completaCeroDiaMes(int diaMes) {
		if (diaMes < 10)
			return "0" + String.valueOf(diaMes);
		else
			return String.valueOf(diaMes);
	}

	public static String formatMoney(int valor) {
		NumberFormat formatea = NumberFormat.getInstance();
		return "$" + formatea.format(valor);
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static Integer cint(long valor) {
		return Integer.parseInt(String.valueOf(valor));
	}

	public static Integer cint(String valor) {
		return Integer.parseInt(valor);
	}

	public static class ProductoUtils {
		public static final String FLAG_ID_PRODUCTO = "ID_PRODUCTO";
		public static final String FLAG_CANTIDAD_PRODUCTO = "CANTIDAD_PRODUCTO";
	}

	public static String getfechaHoraActual() {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return df.format(c.getTime());

	}

	public static String getfechaHoraActualAlRevez() {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(c.getTime());

	}
}
