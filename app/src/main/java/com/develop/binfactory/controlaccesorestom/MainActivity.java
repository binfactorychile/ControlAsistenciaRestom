package com.develop.binfactory.controlaccesorestom;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.develop.binfactory.controlaccesorestom.clases.Asistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.clases.Trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlTrabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;

import junit.framework.Test;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {

            Trabajador trabajador = new Trabajador();
            trabajador.fnombre = "Juan Perez";
            trabajador.frut = "19982580-1";
            CtrlTrabajador.ingresar(trabajador, this);

            trabajador.fnombre = "Pedro Picapiedra";
            trabajador.frut = "16973029-6";
            CtrlTrabajador.ingresar(trabajador, this);

            trabajador.fnombre = "Arnold Shuaseneguer";
            trabajador.frut = "7909826-4";
            CtrlTrabajador.ingresar(trabajador,this);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();

        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, TestFragment.newInstance(position + 1))
                    .commit();
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                mTitle = "asdads";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
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
                    boolean validation = validarRut(edt_rut.getText().toString());
                    ArrayList<Trabajador> arrTrabajador = CtrlTrabajador.getListado("select * from trabajador where rut like '" + edt_rut.getText().toString() + "'", v.getContext());
                    if (validation && (arrTrabajador.size() > 0)) {
                        Trabajador objTrabajador = arrTrabajador.get(0);
                        boolean isChecked = isCheckedToday(objTrabajador.getID(), v);
                        if (isChecked) {
                            edt_rut.setText("");
                            Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " ya se ha checkeado", Toast.LENGTH_SHORT).show();
                        } else {
                            registrarTrabajador(objTrabajador,v);
                            edt_rut.setText("");
                            Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " checkeado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Rut ingresado no existe", Toast.LENGTH_SHORT).show();
                    }
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

        private void registrarTrabajador(Trabajador objTrabajador, View v){
            Asistencia_trabajador objAsistenciaTrabajador = new Asistencia_trabajador();
            objAsistenciaTrabajador.fcliente_proveedor_ID = objTrabajador.fcliente_proveedor_ID;
            objAsistenciaTrabajador.ftrabajador_ID = objTrabajador.fID;
            objAsistenciaTrabajador.ffecha = Utils.getfechaHoraActualAlRevez();
            objAsistenciaTrabajador.ingresar(v.getContext());
        }

        private boolean isCheckedToday(int trabajador_ID, View v)
        {
            ManagerProviderBD bd=new ManagerProviderBD(v.getContext());
            bd.open();
            boolean checked = false;
            String fecha_actual = Utils.getfechaHoraActualAlRevez();
            String query= "SELECT cast((strftime('%s','" + fecha_actual + "') - strftime('%s',fecha)) AS REAL)/60/60 AS diferencia_horas FROM asistencia_trabajador WHERE trabajador_ID = " + trabajador_ID;
            Cursor cursor=bd.ejecutaConRetorno(query);
            cursor.moveToFirst();

            while(cursor.isAfterLast()==false)
            {
                double diferencia_horas = Double.parseDouble(cursor.getString(cursor.getColumnIndex("diferencia_horas")));
                Log.d("test", "Diferencia en horas: "+ diferencia_horas);
                if(diferencia_horas <= 3){
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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

}
