package com.example.bikerental.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bikerental.Database.CustomerDao;
import com.example.bikerental.Database.InsuranceDao;
import com.example.bikerental.Database.Project_Database;
import com.example.bikerental.Database.VehicleDao;
import com.example.bikerental.Model.Booking;
import com.example.bikerental.Model.Customer;
import com.example.bikerental.Model.Insurance;
import com.example.bikerental.Model.Vehicle;
import com.example.bikerental.R;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class BookingCompleteActivity extends AppCompatActivity {

    private Button back;

    //DRIVER DETAILS
    private TextView name, email, phoneNumber;

    //BOOKING SUMMARY
    private TextView bookingID, vehicleName, rate, totalDays, _pickup, _return, insurance, insuranceRate, totalCost;

    //DATABASE TABLE
    private CustomerDao customerDao;
    private VehicleDao vehicleDao;
    private InsuranceDao insuranceDao;

    //BOOKING
    private Booking booking;
    //INSURANCE
    private Insurance chosenInsurance;
    //VEHICLE
    private Vehicle vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        initComponents();
        listenHandler();
        displayCustomerInformation();
        displaySummary();
        displayTotalCost();
    }

    private void initComponents() {
        back = findViewById(R.id.back);

        //DRIVER DETAILS
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);

        //BOOKING SUMMARY
        vehicleName = findViewById(R.id.vehicleName);
        rate = findViewById(R.id.rate);
        totalDays = findViewById(R.id.totalDays);
        _pickup = findViewById(R.id.pickup);
        _return = findViewById(R.id.dropoff);

        //INSURANCE TYPE
        insurance = findViewById(R.id.insurance);
        insuranceRate = findViewById(R.id.insuranceRate);

        //TOTAL COST
        totalCost = findViewById(R.id.totalCost);

        //DATABASE TABLE
        customerDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .customerDao();
        vehicleDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .vehicleDao();
        insuranceDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .insuranceDao();

        //GET BOOKING OBJECT WHICH WAS PASSED FROM PREVIOUS PAGE
        booking = (Booking) getIntent().getSerializableExtra("BOOKING");
        chosenInsurance = insuranceDao.findInsurance(booking.getInsuranceID());
        vehicle = vehicleDao.findVehicle(booking.getVehicleID());

        bookingID = findViewById(R.id.bookingID);
    }

    private void listenHandler() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(BookingCompleteActivity.this,UserViewActivity.class);
                startActivity(homePage);
//                finish();
            }
        });
    }

    private void displayCustomerInformation() {
        Customer customer = customerDao.findUser(booking.getCustomerID());
        //DISPLAY DRIVER INFO
        name.setText(customer.getFullName());
        email.setText(customer.getEmail());
        phoneNumber.setText(customer.getPhoneNumber());

        bookingID.setText("BookingID: " + booking.getBookingID());
    }

    private void displaySummary(){

        vehicleName.setText(vehicle.fullTitle());
        rate.setText("$"+vehicle.getPrice()+"/Day");
        totalDays.setText(getDayDifference(booking.getPickupDate(),booking.getReturnDate())+" Days");
        _pickup.setText(booking.getPickupTime());
        _return.setText(booking.getReturnTime());

        insurance.setText(chosenInsurance.getCoverageType());
        insuranceRate.setText("$"+chosenInsurance.getCost());
    }

    private void displayTotalCost(){
        double cost = calculateTotalCost();
        totalCost.setText("$"+cost);
    }


    private long getDayDifference(Calendar start, Calendar end){
        return ChronoUnit.DAYS.between(start.toInstant(), end.toInstant())+2;
    }

    private double calculateTotalCost(){
        long _days = getDayDifference(booking.getPickupDate(),booking.getReturnDate());
        double _vehicleRate = vehicle.getPrice();
        double _insuranceRate = chosenInsurance.getCost();

        return (_days*_vehicleRate) + _insuranceRate;
    }


    public void onBackPressed(){
        super.onBackPressed();
        Intent homepage = new Intent(getApplicationContext(), UserViewActivity.class);
        homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
        startActivity(homepage);
    }
}
