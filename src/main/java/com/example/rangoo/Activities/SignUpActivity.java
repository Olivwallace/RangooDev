package com.example.rangoo.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rangoo.Interfaces.AuthCallback;
import com.example.rangoo.Model.User;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.StringUtils;
import com.example.rangoo.databinding.ActivitySignUpBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private String name;
    private String phone;
    private String address;
    private String birthday;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConclude.setEnabled(false);

        binding.txtName.addTextChangedListener(textWatcher);
        binding.txtCell.addTextChangedListener(textWatcher);
        binding.txtAdress.addTextChangedListener(textWatcher);
        binding.txtBirth.addTextChangedListener(textWatcher);
        binding.txtEmail.addTextChangedListener(textWatcher);
        binding.txtPassword.addTextChangedListener(textWatcher);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Data Nascimento")
                                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                        .build();

        binding.txtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        binding.txtBirth.setText(dateFormat.format(new Date(datePicker.getSelection())));
                    }
                });
            }
        });

        binding.btnConclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    protected User transformUserForm(){
        getBindings();
        return  new User(name, phone, address, birthday, email, password);
    }

    protected void signUpUser(){
        FirebaseNetwork.signUpUser(transformUserForm(), new AuthCallback() {
            @Override
            public void onSuccess(String UID) {
                Toast.makeText(getApplicationContext(), R.string.signUp_success, Toast.LENGTH_SHORT).show();
                GoTo.signInView(SignUpActivity.this);
            }

            @Override
            public void onError(String error) {
                Log.e("ERRO CADASTRO: ", error);
                Toast.makeText(getApplicationContext(), R.string.signUp_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            getBindings();

            boolean isValidName = !name.isEmpty();
            binding.txtName
            .setBackgroundResource(isValidName? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean isValidPhone = StringUtils.isValidPhone(phone);
            binding.txtCell
            .setBackgroundResource(isValidPhone? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean isValidAddress = !address.isEmpty();
            binding.txtAdress
            .setBackgroundResource(isValidAddress? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean isValidBirthday = StringUtils.isValidDate(birthday);
            binding.txtBirth
            .setBackgroundResource(isValidBirthday? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean isValidEmail = StringUtils.isValidEmail(email);
            binding.txtEmail
            .setBackgroundResource(isValidEmail? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean isValidPassword = StringUtils.isValidPassword(password);
            binding.txtPassword
            .setBackgroundResource(isValidPassword? R.drawable.edittext_correct_background : R.drawable.edittext_incorrect_background);

            boolean enable = isValidName && isValidPhone && isValidAddress && isValidBirthday && isValidEmail && isValidPassword;
            binding.btnConclude.setEnabled(enable);
        }
    };

    protected void getBindings(){
        name = binding.txtName.getText().toString();
        phone = binding.txtCell.getText().toString();
        address = binding.txtAdress.getText().toString();
        birthday = binding.txtBirth.getText().toString();
        email = binding.txtEmail.getText().toString();
        password = binding.txtPassword.getText().toString();
    }
}