package com.example.rangoo.Utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StringUtils {

    public static boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    public static boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
        return password.matches(regex);
    }

    public static boolean isValidPhone(String phone){
        return phone.length() > 9 && phone.length() < 12;
    }

    public static boolean isValidDate(String date){
        boolean isValid = false;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d;

        try {
            d = dateFormat.parse(date);

            Calendar currentDate = Calendar.getInstance();
            currentDate.add(Calendar.YEAR, -14);
            Date minDate = currentDate.getTime();

            if(d != null) isValid = d.before(minDate);

        } catch (ParseException e) {
            Log.d("DATE ERROR:", "Falha ao converter data");
        }

        return isValid;
    }

    public static boolean validateForms(String name, String phone, String address,
                                        String birthday, String email, String pass){
        return isValidEmail(email) && isValidPassword(pass) && isValidDate(birthday)
                && isValidPhone(phone) && !name.isEmpty() && !address.isEmpty();
    }

}
