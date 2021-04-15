package com.example.hp.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places=new ArrayList<String>();
    static ArrayList<LatLng> locations =new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences( "com.example.dck.memorableplacess",Context.MODE_PRIVATE);



        ArrayList<String> latitude = new ArrayList<>();
        ArrayList<String> longitude = new ArrayList<>();

        places.clear();
        latitude.clear();
        longitude.clear();
        locations.clear();



        try {

            places =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            latitude=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
            longitude =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons",ObjectSerializer.serialize(new ArrayList<String>())));

        }catch (Exception e){
            e.printStackTrace();
        }

        if (places.size()>0 && latitude.size() > 0 && longitude.size() >0){
            if (places.size() == latitude.size() && places.size() == longitude.size()){
                for (int i = 0 ; i< latitude.size() ; i++){

                    locations.add(new LatLng(Double.parseDouble(latitude.get(i)),Double.parseDouble(latitude.get(i))));
                }
            }
        }else {
            places.add("add a new places....");
            locations.add(new LatLng(0,0));

        }





        ListView listView = (ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placeNumber",i);

                startActivity(intent);
            }
        });



    }
}