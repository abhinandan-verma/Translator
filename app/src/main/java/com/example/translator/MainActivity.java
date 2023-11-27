package com.example.translator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private Spinner fromSpinner, toSpinner;
    private EditText sourceEdt;
    //Permissions
    private final  int REQUEST_CODE = 1;
    private TextView translatedText;
    //Source Array of Strings - Spinner's Data
    String[] fromLanguages =
            {"from", "English","Afrikaans","Arabic","Hindi","Bengali","Catalan","Urdu","Tamil","Marathi"};

    String[] toLanguage =
            {"to", "English","Afrikaans","Arabic","Hindi","Bengali","Catalan","Urdu","Tamil","Marathi"};
    String LanguageCode, fromLanguageCode, toLanguageCode ;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = findViewById(R.id.spinner1);
        toSpinner = findViewById(R.id.spinner2);
        sourceEdt = findViewById(R.id.editText);
        btn = findViewById(R.id.btn);
        translatedText = findViewById(R.id.textView2);


        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               fromLanguageCode = GetLanguageCode(fromLanguages[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter fromAdapter =
                new ArrayAdapter(this, R.layout.spinner_item, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        //Spinner 2
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguageCode = GetLanguageCode(toLanguage[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter toAdapter =
                new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translatedText.setText("");

                if (sourceEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter text First", Toast.LENGTH_SHORT).show();
                }else if(fromLanguageCode.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter Source Language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Final Language", Toast.LENGTH_SHORT).show();
                }else{
                    TranslateText(fromLanguageCode, toLanguageCode, sourceEdt.getText().toString()) ;
                }

                sourceEdt.getText().clear();
            }
        });

    }

    private void TranslateText(String fromLanguageCode, String toLanguageCode, String src) {

        translatedText.setText("Downloading Language Model...");

        try {
            TranslatorOptions options =
                    new TranslatorOptions.Builder()
                            .setSourceLanguage(fromLanguageCode).setTargetLanguage(toLanguageCode).build();

            Translator translator = Translation.getClient(options);

            DownloadConditions conditions = new DownloadConditions.Builder().build();

            translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    translatedText.setText("Translating...");

                    translator.translate(src).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            translatedText.setText(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to Translate", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to download the Language", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String GetLanguageCode(String language) {
        String languageCode = " ";

        switch (language){
            case "English" :
                languageCode = TranslateLanguage.ENGLISH;
                break;
            case "Afrikaans" :
                languageCode = TranslateLanguage.AFRIKAANS;
                break;
            case "Arabic" :
               languageCode = TranslateLanguage.ARABIC;
               break;
            case "Hindi" :
                languageCode = TranslateLanguage.HINDI;
                break;
            case "Bengali" :
                languageCode = TranslateLanguage.BENGALI;
                break;
            case "Catalan" :
                languageCode = TranslateLanguage.CATALAN;
                break;
            case "Urdu" :
                languageCode = TranslateLanguage.URDU;
                break;
            case "Tamil" :
                languageCode = TranslateLanguage.TAMIL;
                break;
            case "Marathi" :
                languageCode = TranslateLanguage.MARATHI;
                break;
            default:
                languageCode = " ";

        }
        return languageCode;
    }
}