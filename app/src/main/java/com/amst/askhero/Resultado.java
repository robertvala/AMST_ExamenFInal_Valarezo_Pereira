package com.amst.askhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Resultado extends AppCompatActivity {
    ListView listHeroes;
    TextView txtResultado;
List<SuperHeroe> superheroes= new ArrayList<>();
List<String> info= new ArrayList<>();
Context context=this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        listHeroes=(ListView)findViewById(R.id.listHeroes);
        txtResultado=(TextView)findViewById(R.id.txtResultado);
        ArrayAdapter adaptador= new ArrayAdapter(context, android.R.layout.simple_list_item_1,info);
        listHeroes.setAdapter(adaptador);

        txtResultado.setText("Resultados: "+info.size());
        listHeroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,PerfilHeroe.class);
                intent.putExtra("Superheroe",superheroes.get(position));
                startActivity(intent);
                finish();
            }
        });

    }
}