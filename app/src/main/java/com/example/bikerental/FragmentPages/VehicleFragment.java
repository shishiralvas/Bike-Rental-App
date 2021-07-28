package com.example.bikerental.FragmentPages;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bikerental.ActivityPages.VehicleInfoActivity;
import com.example.bikerental.Adapter.VehicleAdapter;
import com.example.bikerental.Database.Project_Database;
import com.example.bikerental.Database.VehicleDao;
import com.example.bikerental.Model.Vehicle;
import com.example.bikerental.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment implements VehicleAdapter.onVehicleListener{
    
    private String selectVehicleCategory;
    private VehicleDao vehicleDao;

    private RecyclerView recyclerView;
    private ArrayList<Vehicle> list;
    private VehicleAdapter adapter;

    public VehicleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        initComponents(view);
        
        return view;
    }

    private void initComponents(View view) {

        selectVehicleCategory = getArguments().getString("CATEGORY");

        vehicleDao = Room.databaseBuilder(getContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .vehicleDao();


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = (ArrayList<Vehicle>)vehicleDao.getCategoryVehicle(selectVehicleCategory);
        adapter = new VehicleAdapter(getContext(), list,this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onClick(int position) {
        Intent vehicleInfoPage = new Intent(getActivity(), VehicleInfoActivity.class);
        vehicleInfoPage.putExtra("VEHICLE",list.get(position));
        startActivity(vehicleInfoPage);
    }


    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
}
