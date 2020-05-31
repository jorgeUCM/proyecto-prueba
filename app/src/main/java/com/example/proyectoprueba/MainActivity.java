//Autor: Jorge Millán García

package com.example.proyectoprueba;


import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Se guarda el nombre de los canales y la url de la imagen en un arrayLis
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> imageLinks = new ArrayList<>();

    private ArrayList<String> NameCinema = new ArrayList<>();
    private ArrayList<String> imageLinksCinema = new ArrayList<>();

    private View sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            //Se obtiene el fichero JSON de la carpeta de Assets
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //Se guarda un array con todos los canales.
            JSONArray userArray = obj.getJSONArray("response");

            for (int i = 0; i < userArray.length(); i++) {
               //por cada canal, se crea un objeto con toda la informacion de dicho canal
                JSONObject userDetail = userArray.getJSONObject(i);
                JSONArray imgsArray = userDetail.getJSONArray("attachments");
                JSONObject imgsDetail = imgsArray.getJSONObject(0);// para obtener la url de la imagen

                if(userDetail.getString("category").equals("Cine")){// Con este if guardamos los nombres y las urls de los canales de categoria de cine
                    NameCinema.add(userDetail.getString("name"));
                    imageLinksCinema.add(imgsDetail.getString("value"));
                }
                // guardamos todos los nombres y url de los canales.
                Name.add(userDetail.getString("name"));
               imageLinks.add(imgsDetail.getString("value"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ToggleButton toggle = (ToggleButton) findViewById(R.id.switch1); // switch para cambiar de modo
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {// si esta activado se visualizaran solo los canales de categoria cine
                    Toast.makeText(MainActivity.this, "Modo cine Activado", Toast.LENGTH_SHORT).show();
                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, NameCinema, imageLinksCinema);
                    recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

                } else { // Si no esta activado se visualizaran todos los canales
                    Toast.makeText(MainActivity.this, "Modo cine Desactivado", Toast.LENGTH_SHORT).show();
                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, Name, imageLinks);
                    recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                }
            }
        });

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, Name, imageLinks); //carga inicial de todos los canales.
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public String loadJSONFromAsset() { // funcion para cargar un JSON de la carpeta de Assets
        String json = null;
        try {
            InputStream is = getAssets().open("GetChannelList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}