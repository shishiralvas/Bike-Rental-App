package com.example.bikerental.ActivityPages;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.room.Room;

import com.example.bikerental.Database.CustomerDao;
import com.example.bikerental.Database.InsuranceDao;
import com.example.bikerental.Database.Project_Database;
import com.example.bikerental.Database.VehicleCategoryDao;
import com.example.bikerental.Database.VehicleDao;
import com.example.bikerental.Model.Customer;
import com.example.bikerental.Model.Insurance;
import com.example.bikerental.Model.Vehicle;
import com.example.bikerental.Model.VehicleCategory;
import com.example.bikerental.R;
import com.example.bikerental.Session.Session;


public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private TextView forgotPass;
    private Button login;

    private EditText email;
    private EditText password;

    private Project_Database db;

    private Button customer;
    private Button vehicleCategory;
    private Button vehicle;

    private Button populate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //IF USER ALREADY LOGGED IN => REDIRECT TO HOME PAGE
        Boolean isLoggedIn = Boolean.valueOf(Session.read(LoginActivity.this,"isLoggedIn","false"));
        if(isLoggedIn){
            Intent homePage = new Intent(LoginActivity.this,UserViewActivity.class);
            startActivity(homePage);
        }

        initComponents();
        clickListenHandler();

    }

    //This will initialize all the clickable components in Login page
    private void initComponents(){

        //Register Button
        register = findViewById(R.id.register);

        //Login Button
        login = findViewById(R.id.login);

        //Forgot Password Button
        forgotPass = findViewById(R.id.forgot_password);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        customer = findViewById(R.id.customer);
        vehicleCategory = findViewById(R.id.vehicleCategory);
        vehicle = findViewById(R.id.vehicle);

        populate = findViewById(R.id.populate);

        db = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries().build();
    }



    //This will handle all the click events on the login page
    private void clickListenHandler(){

        //Register Listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerPage);
            }
        });

        //Login Listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDao customerDao = db.customerDao();
                Customer check = customerDao.findUser(email.getText().toString(),password.getText().toString());

                if(check != null){
                    Session.save(LoginActivity.this,"customerID",check.getCustomerID()+"");
                    Session.save(LoginActivity.this,"isLoggedIn","true");

                    Intent homePage = new Intent(LoginActivity.this,UserViewActivity.class);
                    homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homePage);
                }else{
                    toast("Unsuccessful");
                }
            }
        });

        //Forgot Password Listener
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.vehicleCategoryDao().updateQuantity("Standard");
                db.vehicleCategoryDao().updateQuantity("Sport");
                db.vehicleCategoryDao().updateQuantity("Touring");
                toast("Updated All");
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDao customerDao = db.customerDao();
                for(Customer c: customerDao.getAll()){
                    Log.d("MainActivity", "CUSTOMER => " + c.toString());
                }
            }
        });

        vehicleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleCategoryDao vehicleCategoryDao = db.vehicleCategoryDao();
                for(VehicleCategory c: vehicleCategoryDao.getAllCategory()){
                    Log.d("MainActivity", "VEHICLE CATEGORY => " + c.toString());
                }
            }
        });

        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleDao vehicleDao = db.vehicleDao();
                for(Vehicle c: vehicleDao.getAll()){
                    Log.d("MainActivity", "VEHICLE => " + c.toString());
                }
            }
        });


        populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleCategoryDao vehicleCategoryDao = db.vehicleCategoryDao();
                VehicleDao vehicleDao = db.vehicleDao();
                InsuranceDao insuranceDao = db.insuranceDao();

                VehicleCategory vc1 = new VehicleCategory("standard",100,-47032,"https://www.linkpicture.com/q/1-removebg-preview_4.png");
                VehicleCategory vc2 = new VehicleCategory("sport",101,-13936668,"https://www.linkpicture.com/q/2-removebg-preview_1.png");
               // VehicleCategory vc3 = new VehicleCategory("sports",102,-4068,"https://images.dealer.com/ddc/vehicles/2019/Lamborghini/Huracan/Coupe/trim_LP5802_b8a819/perspective/side-left/2019_76.png");
                VehicleCategory vc4 = new VehicleCategory("touring",103,-3092272,"https://www.linkpicture.com/q/3-removebg-preview_2.png");
              //  VehicleCategory vc5 = new VehicleCategory("van",104,-9539986,"https://st.motortrend.com/uploads/sites/10/2016/12/2017-mercedes-benz-metris-base-passenger-van-side-view.png");

                vehicleCategoryDao.insert(vc1);
                vehicleCategoryDao.insert(vc2);
              //  vehicleCategoryDao.insert(vc3);
                vehicleCategoryDao.insert(vc4);
              //  vehicleCategoryDao.insert(vc5);


                Vehicle v1 = new Vehicle(273,200,2,50,"bajaj","pulsar",2021,"standard",true,"https://www.linkpicture.com/q/4-removebg-preview_2.png");
                Vehicle v2 = new Vehicle(285,250,2,62,"suzuki","gs150r",2021,"standard",true,"https://www.linkpicture.com/q/5-removebg-preview_3.png");
                Vehicle v3 = new Vehicle(287,265,2,95,"tvs","sport",2021,"standard",true,"https://www.linkpicture.com/q/6-removebg-preview.png");

                Vehicle v4 = new Vehicle(265,180,2,38,"pierer","ktm rc",2021,"sport",true,"https://www.linkpicture.com/q/7-removebg-preview_3.png");
                Vehicle v5 = new Vehicle(229,120,2,49,"yamaha","mt 15",2021,"sport",true,"https://www.linkpicture.com/q/8-removebg-preview.png");
                Vehicle v6 = new Vehicle(219,500,2,35,"honda","cbr",2021,"sport",true,"https://www.linkpicture.com/q/7-removebg-preview_4.png");

                Vehicle v7 = new Vehicle(297,157,2,41,"eicher","classic",2021,"touring",true,"https://www.linkpicture.com/q/10-removebg-preview.png");
                Vehicle v8 = new Vehicle(298,121,2,40,"eicher","bullet",2021,"touring",false,"https://www.linkpicture.com/q/11-removebg-preview.png");
                Vehicle v9 = new Vehicle(299,156,2,35,"eicher","thunderbird",2021,"touring",true,"https://www.linkpicture.com/q/12-removebg-preview_1.png");

                vehicleDao.insert(v1);
                vehicleDao.insert(v2);
                vehicleDao.insert(v3);
                vehicleDao.insert(v4);
                vehicleDao.insert(v5);
                vehicleDao.insert(v6);
                vehicleDao.insert(v7);
                vehicleDao.insert(v8);
                vehicleDao.insert(v9);

                Insurance i1 = new Insurance("None",0);
                Insurance i2 = new Insurance("Basic",15);
                Insurance i3 = new Insurance("Premium",25);
                insuranceDao.insert(i1);
                insuranceDao.insert(i2);
                insuranceDao.insert(i3);

            }
        });
    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
}
