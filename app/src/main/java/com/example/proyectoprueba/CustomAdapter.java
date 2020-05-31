//Autor: Jorge Millán García


package com.example.proyectoprueba;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

   private ArrayList<String> Name;
    private ArrayList<String> imageLinks = new ArrayList<>();
   private Context context;
    private String link = "https://orangetv.orange.es/stv/api/rtv/v1/images"; // Parte de la url necesaria para poder encontrar la imagen en internet

    public CustomAdapter(Context context, ArrayList<String> Name, ArrayList<String> imageLinks) {
        this.context = context;
        this.Name = Name;
        this.imageLinks = imageLinks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.canales_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.name.setText(Name.get(position)); // actualizamos el nombre

        Picasso.get().load(link+imageLinks.get(position)).into(holder.image);// actualizamos la imagen

        holder.itemView.setOnClickListener(new View.OnClickListener() { // En case de hacer click en un canal se mostrará por pantalla un mensaje con el nombre del canal.
            @Override
            public void onClick(View view) {

                Toast.makeText(context, Name.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return Name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);// textView que nos vamos a referir
            this.image = (ImageView) itemView.findViewById(R.id.imageView);// imagen que nos vamos a referir
        }
    }
}