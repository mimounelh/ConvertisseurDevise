package com.example.convertisseurdevise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private Spinner spDeviseDepart;
    private Spinner spDeviseArrive;
    private Button btnConvertir;
    private EditText etValeur;
    private TextView tvResultatConversion;
    private TextView tvTauxChange;

    private static final double TAUX_DOLLAR_EURO = 0.95;
    private static final double TAUX_EURO_DOLLAR = 1.05;
    private static final double TAUX_LIVRE_EURO = 1.19;
    private static final double TAUX_EURO_LIVRE = 0.84;
    private static final double TAUX_DOLLAR_LIVRE = 0.80;
    private static final double TAUX_LIVRE_DOLLAR = 1.25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrayDevises,
                android.R.layout.simple_spinner_dropdown_item);

        spDeviseDepart = (Spinner) findViewById(R.id.spDeviseDepart);
        spDeviseArrive = (Spinner) findViewById(R.id.spDeviseArrive);
        spDeviseDepart.setAdapter(adapter);
        spDeviseArrive.setAdapter(adapter);

        btnConvertir = (Button) findViewById(R.id.btnConvertir);
        etValeur = (EditText) findViewById(R.id.etValeur);
        tvResultatConversion = (TextView) findViewById(R.id.tvResultatConversion);
        tvTauxChange = (TextView) findViewById(R.id.tvTauxChange);

        // Création des listeners
        spDeviseDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkInput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDeviseArrive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkInput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void checkInput() {
        CharSequence result = "";
        String deviseDepart = spDeviseDepart.getSelectedItem().toString();
        String deviseArrive = spDeviseArrive.getSelectedItem().toString();
        String valeur = etValeur.getText().toString();

        if (deviseDepart == deviseArrive)
            result = "Les devises ne peuvent pas être les mêmes";

        /*
        if (valeur.isEmpty())
            result = "Vous devez introduir une valeur"; */

        if (result.length() == 0) {
            // TODO: Rajouter le code dans le listener du bouton
            //convertirDevise(Double.parseDouble(valeur),deviseDepart, deviseArrive);
        }
        else {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    private void convertirDevise(double valeur, String deviseDepart, String deviseArrive){
        Double resultatConversion = 0.0;

        if (deviseDepart == "Euro") {
            if (deviseArrive == "Dollar")
                resultatConversion = valeur * TAUX_EURO_DOLLAR;
            if (deviseArrive == "Livre")
                resultatConversion = valeur * TAUX_EURO_LIVRE;
        }

        if (deviseDepart == "Dollar") {
            if (deviseArrive == "Euro")
                resultatConversion = valeur * TAUX_DOLLAR_EURO;
            if (deviseArrive == "Livre")
                resultatConversion = valeur * TAUX_DOLLAR_LIVRE;
        }

        if (deviseDepart == "Livre") {
            if (deviseArrive == "Euro")
                resultatConversion = valeur * TAUX_LIVRE_EURO;
            if (deviseArrive == "Dollar")
                resultatConversion = valeur * TAUX_LIVRE_DOLLAR;
        }

        tvResultatConversion.setText(resultatConversion.toString());
    }
}