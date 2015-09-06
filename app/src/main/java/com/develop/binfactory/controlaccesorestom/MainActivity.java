package com.develop.binfactory.controlaccesorestom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.develop.binfactory.controlaccesorestom.clases.Asistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.clases.Sincronizador;
import com.develop.binfactory.controlaccesorestom.clases.Trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlTrabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.logica.soporte.clsMantenimiento;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import junit.framework.Test;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    //FLAGS
    public static int FLAG_DESAYUNO_INICIO = 500;
    public static int FLAG_DESAYUNO_TERMINO = 1100;
    public static int FLAG_ALMUERZO_INICIO = 1101;
    public static int FLAG_ALMUERZO_TERMINO = 1700;
    public static int FLAG_CENA_INICIO = 1701;
    public static int FLAG_CENA_TERMINO = 100;

    private ProgressDialog dialog;
    String resultado_sincro;//
    String mensaje_respuesta_sincro;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE );

        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dialog = new ProgressDialog(this);//
        dialog.setMessage("Sincronizando datos. Por favor espere...");
        dialog.setTitle("Sincronizando");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        SincronizadorNormal sincronormal = new SincronizadorNormal();
        if (position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, TestFragment.newInstance(position + 1))
                    .commit();

        } else if (position == 1) {
            clsMantenimiento mantenimiento = new clsMantenimiento(this);
            String resultado = mantenimiento.respaldarBD();
            sincronormal.origen = Integer.toString(position);
            sincronormal.execute("HOLI", "JKJK");

        } else if (position == 2) {
            clsMantenimiento mantenimiento = new clsMantenimiento(this);
            String resultado = mantenimiento.respaldarBD();
            sincronormal.origen = Integer.toString(position);
            sincronormal.execute("HOLI", "JKJK");
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Registro Asistencia";
                break;
            case 2:
                mTitle = "Sincronizar Trabajadores";
                break;
            case 3:
                mTitle = "Sincronizar Asistencias";
                break;
        }
    }
    private void estaCheckeado()
    {
        String horario = "";
        //determinar en que horario se encuentra
        if(Utils.getHorarioComidas(FLAG_DESAYUNO_INICIO, FLAG_DESAYUNO_TERMINO))
            horario = "desayuno";
        else if (Utils.getHorarioComidas(FLAG_ALMUERZO_INICIO, FLAG_ALMUERZO_TERMINO))
            horario = "almuerzo";
        else if (Utils.getHorarioComidas(FLAG_CENA_INICIO, FLAG_CENA_TERMINO))
            horario = "cena";

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    ////////sincronizacion de los trabajadores y sus asistencias::::+
    public void enviarAsistencias() {

    }

    private class SincronizadorNormal extends AsyncTask<String, Float, Integer> {
        public String origen = "";

        protected void onPreExecute() {
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show(); // Mostramos el di�logo antes de comenzar
        }

        protected Integer doInBackground(String[] arrString) {

            Sincronizador sincro = new Sincronizador(MainActivity.this,
                    "1");// ((VariablesSesion)getApplication()).acces_usuario_ID);
            if (!sincro.mac_address.equalsIgnoreCase("no")) {
                if (sincro.existeConectividad(MainActivity.this)) {
                    resultado_sincro = "correcto";
                    if (origen.equals("1")) {
                        mensaje_respuesta_sincro = sincro
                                .enviarAsistenciasTrabajador();
                        if (mensaje_respuesta_sincro.equals("ok")) {

                            dialog.dismiss();
                        } else {
                            resultado_sincro = "error_envio";

                        }
                    } else if (origen.equals("2")) {
                        mensaje_respuesta_sincro = sincro.traerDatosServidorPrincipal();
                        if (mensaje_respuesta_sincro.equals("ok")) {

                            dialog.dismiss();
                        } else {
                            resultado_sincro = "error_envio";

                        }
                    }


                } else {
                    dialog.dismiss();
                    resultado_sincro = "error_conexion";
                    // Toast.makeText(getApplicationContext(),"No existe conectividad",
                    // Toast.LENGTH_SHORT).show();

                }
            } else {
                resultado_sincro = "error_no_conectada";
            }
            return 1;
        }

        protected void onProgressUpdate(Float[] valores) {
            int p = Math.round(100 * valores[0]);
            dialog.setProgress(p);
        }

        protected void onPostExecute(Integer bytes) {

            dialog.dismiss();

            if (resultado_sincro.equals("error_conexion")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_envio")) {
                Utils.muestraMensaje(mensaje_respuesta_sincro,
                        MainActivity.this);
            } else if (resultado_sincro.equals("error")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("ok_admin")) {
                Utils.muestraMensaje(
                        "Ahora debe ingresar con un vendedor y sincronizar",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_no_conectada")) {
                Utils.muestraMensaje(
                        "La tablet no se encuentra conectada a la misma red WIFI",
                        MainActivity.this);
            }

        }

        private void sincronizarDatos(View v) {

        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class TestFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private EditText edt_rut;
        private Button btn_uno;
        private Button btn_dos;
        private Button btn_tres;
        private Button btn_cuatro;
        private Button btn_cinco;
        private Button btn_seis;
        private Button btn_siete;
        private Button btn_ocho;
        private Button btn_nueve;
        private Button btn_cero;
        private Button btn_guion;
        private Button btn_ok;
        private Button btn_k;
        private ImageButton btn_delete;

        public static TestFragment newInstance(int sectionNumber) {
            TestFragment fragment = new TestFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public TestFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test, container, false);
            edt_rut = (EditText) rootView.findViewById(R.id.edt_rut);
            btn_uno = (Button) rootView.findViewById(R.id.btn_uno);
            btn_dos = (Button) rootView.findViewById(R.id.btn_dos);
            btn_tres = (Button) rootView.findViewById(R.id.btn_tres);
            btn_cuatro = (Button) rootView.findViewById(R.id.btn_cuatro);
            btn_cinco = (Button) rootView.findViewById(R.id.btn_cinco);
            btn_seis = (Button) rootView.findViewById(R.id.btn_seis);
            btn_siete = (Button) rootView.findViewById(R.id.btn_siete);
            btn_ocho = (Button) rootView.findViewById(R.id.btn_ocho);
            btn_nueve = (Button) rootView.findViewById(R.id.btn_nueve);
            btn_cero = (Button) rootView.findViewById(R.id.btn_cero);
            btn_k = (Button) rootView.findViewById(R.id.btn_k);
            btn_guion = (Button) rootView.findViewById(R.id.btn_guion);
            btn_delete = (ImageButton) rootView.findViewById(R.id.btn_delete);
            btn_ok = (Button) rootView.findViewById(R.id.btn_ok);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.oneClickListener();
        }

        private void oneClickListener() {
            btn_cero.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "0");
                }
            });
            btn_uno.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "1");
                }
            });

            btn_dos.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "2");
                }
            });

            btn_tres.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "3");
                }
            });

            btn_cuatro.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "4");
                }
            });

            btn_cinco.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "5");
                }
            });

            btn_seis.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "6");
                }
            });

            btn_siete.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "7");
                }
            });

            btn_ocho.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "8");
                }
            });

            btn_nueve.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "9");
                }
            });
            btn_k.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    edt_rut.setText(edt_rut.getText() + "k");
                }
            });

            btn_guion.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String texto = edt_rut.getText().toString();
                    if (texto.length() >= 7)
                        edt_rut.setText(edt_rut.getText() + "-");
                }
            });

            btn_delete.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String[] split = edt_rut.getText().toString().split("");
                    edt_rut.setText("");
                    for (int i = 0; i < split.length; i++) {
                        if (i != split.length - 1) {
                            edt_rut.setText(edt_rut.getText().toString() + split[i]);
                        }

                    }
                }
            });

            btn_ok.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    testsincro(v);

                    /*boolean validation = validarRut(edt_rut.getText().toString());
                    String query = "select * from trabajador where rut like '" + edt_rut.getText().toString() + "'";
                    ArrayList<Trabajador> arrTrabajador = CtrlTrabajador.getListado(query, v.getContext());
                    if (validation && (arrTrabajador.size() > 0)) {
                        Trabajador objTrabajador = arrTrabajador.get(0);
                        String horario = getHorario();
                        boolean isChecked = estaCheckeado(objTrabajador.getID(), v,horario);
                        if (isChecked) {
                            edt_rut.setText("");
                            //Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " ya se ha checkeado", Toast.LENGTH_SHORT).show();
                            SuperToast superToast = new SuperToast(getActivity());
                            superToast.setDuration(SuperToast.Duration.LONG);
                            superToast.setText("Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " ya se ha checkeado");
                            superToast.setTextSize(40);
                            superToast.setIcon(SuperToast.Icon.Dark.EXIT, SuperToast.IconPosition.LEFT);
                            superToast.show();
                        } else {
                            int resultado = registrarTrabajador(objTrabajador, v);
                            if (resultado > 0) {

                                boolean resultado2 = registrarSincronizacionAsistencia(resultado, v);
                            }

                            edt_rut.setText("");
                            //Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " checkeado", Toast.LENGTH_SHORT).show();
                            SuperToast superToast = new SuperToast(getActivity());
                            superToast.setDuration(SuperToast.Duration.LONG);
                            superToast.setText("Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " checkeado");
                            superToast.setTextSize(40);
                            superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
                            superToast.show();
                        }
                    } else {
                        //Toast.makeText(getActivity(), "Rut ingresado no existe", Toast.LENGTH_SHORT).show();
                        edt_rut.setText("");
                        SuperToast superToast = new SuperToast(getActivity());
                        superToast.setDuration(SuperToast.Duration.LONG);
                        superToast.setText("Rut ingresado no existe");
                        superToast.setTextSize(40);
                        superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
                        superToast.show();
                    }*/
                }
            });

            btn_delete.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    edt_rut.setText("");
                    return true;
                }
            });
        }

        private int registrarTrabajador(Trabajador objTrabajador, View v) {
            int resultado = 0;
            Asistencia_trabajador objAsistenciaTrabajador = new Asistencia_trabajador();
            objAsistenciaTrabajador.fcliente_proveedor_ID = objTrabajador.fcliente_proveedor_ID;
            objAsistenciaTrabajador.ftrabajador_ID = objTrabajador.fID;
            objAsistenciaTrabajador.ffecha = Utils.getfechaHoraActualAlRevez();
            objAsistenciaTrabajador.fhorario = getHorario();
            resultado = objAsistenciaTrabajador.ingresar(v.getContext());
            return resultado;
        }

        private boolean registrarSincronizacionAsistencia(int trabajador_ID, View v) {

            try {
                ManagerProviderBD bd = new ManagerProviderBD(v.getContext());
                String query = "insert into sincronizacion_asistencia (registro_ID) values('" + trabajador_ID + "')";
                bd.open();
                bd.ejecutaSinRetorno(query);
                bd.close();
            } catch (Exception ex) {
                Utils.escribeLog(ex, "MainActivity.registrarSincronizacionAsistencia");
                return false;
            }
            return true;
        }
        private String getHorario()
        {
            String horario = "";
            if(Utils.getHorarioComidas(FLAG_DESAYUNO_INICIO, FLAG_DESAYUNO_TERMINO))
                horario = "desayuno";
            else if (Utils.getHorarioComidas(FLAG_ALMUERZO_INICIO, FLAG_ALMUERZO_TERMINO))
                horario = "almuerzo";
            else if (Utils.getHorarioComidas(FLAG_CENA_INICIO, FLAG_CENA_TERMINO))
                horario = "cena";
            return horario;
        }
        private boolean estaCheckeado(int trabajador_ID, View v, String horario)
        {
            String fecha_actual = Utils.getfechaHoraActualAlRevez();

            //determinar en que horario se encuentra


            Toast.makeText(getActivity(), "Horario:" + horario, Toast.LENGTH_SHORT).show();
            String query = "SELECT * FROM asistencia_trabajador WHERE date('" + fecha_actual+"') LIKE date(fecha) and horario = '" + horario + "' and trabajador_ID = " + trabajador_ID;
            ArrayList<Asistencia_trabajador> arrAsistenciaTrabajador = CtrlAsistencia_trabajador.getListado(query, v.getContext());
            if(arrAsistenciaTrabajador.size() > 0){
                return  true;
            }
            return false;
        }

        private boolean isCheckedToday(int trabajador_ID, View v) {
            ManagerProviderBD bd = new ManagerProviderBD(v.getContext());
            bd.open();
            boolean checked = false;
            String fecha_actual = Utils.getfechaHoraActualAlRevez();
            String query = "SELECT cast((strftime('%s','" + fecha_actual + "') - strftime('%s',fecha)) AS REAL)/60/60 AS diferencia_horas FROM asistencia_trabajador WHERE diferencia_horas <= 3 and trabajador_ID = " + trabajador_ID;
            Cursor cursor = bd.ejecutaConRetorno(query);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                double diferencia_horas = Double.parseDouble(cursor.getString(cursor.getColumnIndex("diferencia_horas")));
                Log.d("test", "Diferencia en horas: " + diferencia_horas);
                if (diferencia_horas <= 3) {
                    checked = true;
                }
                cursor.moveToNext();
            }
            bd.close();

            return checked;
        }


        private boolean validarRut(String rut) {

            boolean validation = false;
            try {
                rut = rut.toUpperCase();
                rut = rut.replace(".", "");
                rut = rut.replace("-", "");
                int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

                char dv = rut.charAt(rut.length() - 1);

                int m = 0, s = 1;
                for (; rutAux != 0; rutAux /= 10) {
                    s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
                }
                if (dv == (char) (s != 0 ? s + 47 : 75)) {
                    validation = true;
                }

            } catch (java.lang.NumberFormatException e) {
            } catch (Exception e) {
            }
            return validation;
        }

        public void testsincro(View v)
        {

            ArrayList<Trabajador> arrTrab = CtrlTrabajador.getListado("select * from trabajador",v.getContext());
            for(Trabajador objTrabajador : arrTrab) {
                int resultado = registrarTrabajador(objTrabajador, v);
                if (resultado > 0) {

                    boolean resultado2 = registrarSincronizacionAsistencia(resultado, v);
                }
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

}
