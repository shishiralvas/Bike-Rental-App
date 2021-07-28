package com.example.bikerental.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bikerental.Converter.Converter;
import com.example.bikerental.Model.Administrator;
import com.example.bikerental.Model.Billing;
import com.example.bikerental.Model.Booking;
import com.example.bikerental.Model.Customer;
import com.example.bikerental.Model.Insurance;
import com.example.bikerental.Model.Payment;
import com.example.bikerental.Model.Vehicle;
import com.example.bikerental.Model.VehicleCategory;


@Database(entities = {Customer.class,       VehicleCategory.class,  Vehicle.class,
                      Administrator.class,  Billing.class,          Booking.class,
                      Insurance.class,      Payment.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class Project_Database extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract VehicleCategoryDao vehicleCategoryDao();
    public abstract VehicleDao vehicleDao();
    public abstract AdministratorDao administratorDao();
    public abstract BillingDao billingDao();
    public abstract BookingDao bookingDao();
    public abstract InsuranceDao insuranceDao();
    public abstract PaymentDao paymentDao();
}

