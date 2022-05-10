package com.example.convertisseurdevise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
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
import java.util.Dictionary;


public class MainActivity extends AppCompatActivity {

    private Spinner spDeviseDepart;
    private Spinner spDeviseArrive;
    private Button btnConvertir;
    private EditText etValeur;
    private TextView tvResultatConversion;

    private static final double TAUX_DOLLAR_EURO = 0.95;
    private static final double TAUX_EURO_DOLLAR = 1.05;
    private static final double TAUX_LIVRE_EURO = 1.19;
    private static final double TAUX_EURO_LIVRE = 0.84;
    private static final double TAUX_DOLLAR_LIVRE = 0.80;
    private static final double TAUX_LIVRE_DOLLAR = 1.25;

    protected final class conversionErrors {
        public static final String SAME_CURRENCY_ERROR = "Les devises ne peuvent pas être les mêmes.";
        public static final String EMPTY_VALUE_ERROR = "Vous devez introduir une valeur.";
    }

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


        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    String deviseDepart = spDeviseDepart.getSelectedItem().toString();
                    String deviseArrive = spDeviseArrive.getSelectedItem().toString();
                    String valeur = etValeur.getText().toString();

                    double resultatConversion = convertirDevise(Double.parseDouble(valeur),deviseDepart, deviseArrive);
                    tvResultatConversion.setText(String.valueOf(resultatConversion));
                }
            }
        });
    }

    private Boolean checkInput() {
        boolean isValid = true;
        String[] errorMsg = new String[2];  // Deux erreurs possibles uniquement
        String deviseDepart = spDeviseDepart.getSelectedItem().toString();
        String deviseArrive = spDeviseArrive.getSelectedItem().toString();
        String valeur = etValeur.getText().toString();

        if (deviseDepart.equals(deviseArrive))
            errorMsg[0] = conversionErrors.SAME_CURRENCY_ERROR;

        if (valeur.isEmpty())
            errorMsg[1] = conversionErrors.EMPTY_VALUE_ERROR;

        for (String error: errorMsg) {
            if (error != null && error.length() != 0){
                isValid = false;
                displayMessage(error);
            }
        }

        return isValid;
    }

    private Double convertirDevise(double valeur, String deviseDepart, String deviseArrive){
        double resultatConversion = 0.0;

        if (deviseDepart.equals("Euro")) {
            if (deviseArrive.equals("Dollar"))
                resultatConversion = valeur * TAUX_EURO_DOLLAR;
            if (deviseArrive.equals("Livre Sterling"))
                resultatConversion = valeur * TAUX_EURO_LIVRE;
        }

        if (deviseDepart.equals("Dollar")) {
            if (deviseArrive.equals("Euro"))
                resultatConversion = valeur * TAUX_DOLLAR_EURO;
            if (deviseArrive.equals("Livre Sterling"))
                resultatConversion = valeur * TAUX_DOLLAR_LIVRE;
        }

        if (deviseDepart.equals("Livre Sterling")) {
            if (deviseArrive.equals("Euro"))
                resultatConversion = valeur * TAUX_LIVRE_EURO;
            if (deviseArrive.equals("Dollar"))
                resultatConversion = valeur * TAUX_LIVRE_DOLLAR;
        }

        return resultatConversion;
    }

    private void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}