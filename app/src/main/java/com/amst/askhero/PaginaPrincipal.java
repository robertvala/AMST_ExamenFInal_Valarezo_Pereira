package com.amst.askhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void buscarHeroe(View view) {

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());

        if (editTextHeroe.getText().toString().length()>=3){
            String url="https://www.superheroapi.com/api.php/3738068759615960/search/"+editTextHeroe.getText().toString();
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray myJsonArray = response.getJSONArray("results");
                        for (int i=0;i<myJsonArray.length();i++){
                            JSONObject myObject=myJsonArray.getJSONObject(i);
                            String id=myObject.getString("id");
                            String alterego=myObject.getJSONObject("biography").getString("full-name");
                            String nombre=myObject.getString("name");
                            HashMap habilidades=new HashMap();
                            habilidades.put("Inteligencia",myObject.getJSONObject("powerstats").getString("intelligence"));
                            habilidades.put("Fuerza",myObject.getJSONObject("powerstats").getString("strength"));
                            habilidades.put("Velocidad",myObject.getJSONObject("powerstats").getString("speed"));
                            habilidades.put("Durabilidad",myObject.getJSONObject("powerstats").getString("durability"));
                            habilidades.put("Poder",myObject.getJSONObject("powerstats").getString("power"));
                            habilidades.put("Combate",myObject.getJSONObject("powerstats").getString("combat"));
                            SuperHeroe superHeroe=new SuperHeroe(id,alterego,nombre,habilidades);
                            superHeroes.add(superHeroe);
                            System.out.println(superHeroe);
                        }
                        Intent intent=new Intent(context,Resultado.class);
                        //ssms
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("Superheroes",superHeroes);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        System.out.println("Error "+e);
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error"+error);
                }
            });
            queue.add(request);
        }
        else{
            Toast.makeText(this,"Busqueda invalida",Toast.LENGTH_SHORT).show();
            editTextHeroe.setText("");
        }
    }
}