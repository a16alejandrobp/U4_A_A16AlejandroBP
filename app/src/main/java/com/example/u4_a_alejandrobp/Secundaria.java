package com.example.u4_a_alejandrobp;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Secundaria extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<String>();

		froitas.add("Pera");
		froitas.add("Mazá");
		froitas.add("Plátano");

    Spinner spin;
    ListView lv;
    boolean sdDisponhible = false;
    boolean sdAccesoEscritura = false;
    File dirFicheiroSD;
    File rutaCompleta;
    public static String nomeFicheiro = "ficheiro_SD.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comprobarEstadoSD();
        establecerDirectorioFicheiro();
        String linha = "";
        lv.setText(linha);

        if (sdDisponhible) {
            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaCompleta)));

                while ((linha = br.readLine()) != null)
                    data.add(linha + "\n");

                br.close();

            } catch (Exception ex) {
                Toast.makeText(this, "Problemas lendo o ficheiro", Toast.LENGTH_SHORT).show();
                Log.e("SD", "Erro lendo o ficheiro. ");

            }
        } else
            Toast.makeText(this, "A tarxeta SD non está dispoñible", Toast.LENGTH_SHORT).show();

        // Enlace do adaptador cos datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        // Opcional: layout usuado para representar os datos no Spinner
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adaptador);


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
}
