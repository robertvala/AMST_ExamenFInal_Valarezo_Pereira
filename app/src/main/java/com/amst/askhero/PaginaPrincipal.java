package com.amst.askhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PaginaPrincipal extends AppCompatActivity {
    Button btnBuscar;
    EditText editTextHeroe;
    ArrayList<SuperHeroe> superHeroes= new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        editTextHeroe=(EditText)findViewById(R.id.editTextHeroe);
    }
}