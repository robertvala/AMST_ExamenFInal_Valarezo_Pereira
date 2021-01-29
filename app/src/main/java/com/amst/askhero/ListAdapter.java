package com.amst.askhero;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amst.askhero.ListElement;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {
    private List<ListElement> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata=itemList;
    }
    @Override
    public int getItemCount(){return  mdata.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.list_element,null);
        return  new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mdata.get(position));
        holder.setOnClickListeners();
    }

    public void setItems(List<ListElement> items){
        mdata=items;
    }


    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnMostrar,btnOcultar;
        TextView txtOPcion,txtPuntaje,txtKey;
        RelativeLayout cvOpcion;
        String key1;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int score=0;
        String estado="";

        ViewHolder(View itemView){
            super(itemView);
            btnMostrar=itemView.findViewById(R.id.btnMostar);
            btnOcultar=itemView.findViewById(R.id.btnOcultar);
            txtOPcion=itemView.findViewById(R.id.txtOpcion);
            txtPuntaje= itemView.findViewById(R.id.txtPuntaje);
            cvOpcion=itemView.findViewById(R.id.cvOPcion);
            txtKey=itemView.findViewById(R.id.txtKey);


        }
        void cargarMarcador(){
            // Read from the database
            DatabaseReference myRef = database.getReference("Marcador");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    score=Integer.parseInt(dataSnapshot.child("Pregunta").getValue().toString());

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        void revisarEstado(){
            // Read from the database
            String path=txtKey.getText().toString();
            DatabaseReference referencia = database.getReference("Preguntas").child(key1).child("opciones").child(path).child("visible");
            referencia.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    estado=dataSnapshot.getValue().toString();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        void bindData(final ListElement item){
            cargarMarcador();

            txtOPcion.setText(item.getOpcion());
            txtPuntaje.setText(item.getValor());
            cvOpcion.setBackgroundColor(Color.parseColor(item.getColor()));
            txtKey.setText(item.getKey2());
            key1=item.getKey1();
            revisarEstado();

        }

        public void setOnClickListeners() {
            btnMostrar.setOnClickListener(this);
            btnOcultar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnMostar:
                    String path=txtKey.getText().toString();
                    DatabaseReference myRef = database.getReference("Preguntas").child(key1).child("opciones").child(path).child("visible");
                    myRef.setValue("true");
                    int suma=Integer.parseInt(txtPuntaje.getText().toString());
                    if(estado.equals("false")) {
                        score = suma + score;
                        enviarMarcador();
                    }

                    break;
                case R.id.btnOcultar:
                    String path2=txtKey.getText().toString();
                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef2 = database2.getReference("Preguntas").child(key1).child("opciones").child(path2).child("visible");
                    myRef2.setValue("false");
                    int resta=Integer.parseInt(txtPuntaje.getText().toString());
                    if(estado.equals("true")) {
                        score = score - resta;
                        enviarMarcador();
                    }
                    break;
            }

        }

        public void enviarMarcador(){
            DatabaseReference myref=database.getReference("Marcador").child("Pregunta");
            myref.setValue(score);
        }
    }



}
