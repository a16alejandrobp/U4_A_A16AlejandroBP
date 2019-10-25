package com.example.u4_a_alejandrobp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class U4_A_AlejandroBP extends AppCompatActivity {
    RadioButton rbtn_e;
    RadioButton rbtn_s;
    EditText et;
    Button engadir_sobrescribir;
    boolean sdDisponhible = false;
    boolean sdAccesoEscritura = false;
    File dirFicheiroSD;
    File rutaCompleta;
    public static String nomeFicheiro = "ficheiro_SD.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbtn_e = (RadioButton) findViewById(R.id.rbtn_e);
        rbtn_s = (RadioButton) findViewById(R.id.rbtn_s);
        et = (EditText) findViewById(R.id.et);
        engadir_sobrescribir = (Button) findViewById(R.id.engadir_sobrescribir);
        comprobarEstadoSD();
        establecerDirectorioFicheiro();
    }
    public void comprobarEstadoSD() {
        String estado = Environment.getExternalStorageState();
        Log.e("SD", estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponhible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
            sdDisponhible = true;
    }

    public void establecerDirectorioFicheiro() {

        if (sdDisponhible) {
            // dirFicheiroSD = Environment.getExternalStorageDirectory();
            dirFicheiroSD = getExternalFilesDir(null);
            rutaCompleta = new File(dirFicheiroSD.getAbsolutePath(), nomeFicheiro);

        }
    }
    public void onEngadirSobrescribirClick(View v) {

        boolean sobrescribir = false;

        sobrescribir = (rbtn_s.isChecked());

        et.setText("");

        if (sdAccesoEscritura) {

            try {

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaCompleta, sobrescribir));

                osw.write(et.getText() + "-"+ Calendar.getInstance().getTime()+"\n");
                osw.close();

                et.setText("");

            } catch (Exception ex) {
                Log.e("SD", "Error escribindo no ficheiro");
            }
        } else
            Toast.makeText(this, "A tarxeta SD non está en modo acceso escritura", Toast.LENGTH_SHORT).show();

    }

}
