package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private Spinner fromSpinner, toSpinner;
    private EditText sourceEdt;
    private Button bt;
    private TextView translatedText;

    //Source Array of Strings - Spinner's Data
    String[] fromLanguages =
            {"from", "English","African","Arabic","Hindi","Bengali","Catalan","Urdu"};

    String[] toLanguage =
            {"to", "English","African","Arabic","Hindi","Bengali","Catalan","Urdu"};

    //Permissions
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}