package com.example.rangoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.databinding.ActivityEmptyHomeBinding;

import java.util.ArrayList;

public class EmptyHomeActivity extends AppCompatActivity {

    ActivityEmptyHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmptyHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.btnWeekmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.weekMenu(EmptyHomeActivity.this, new ArrayList<>());
            }
        });
    }

    protected void goToPerfilView(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }



}