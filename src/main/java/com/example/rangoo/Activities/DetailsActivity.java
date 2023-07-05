package com.example.rangoo.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;
import com.example.rangoo.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Food food = getIntent().getExtras().getParcelable(getString(R.string._FOOD_TO_DETAIL));
        detailFood(food);
    }

    protected  void detailFood(Food food){
        if(food != null){
            binding.nameDetails.setText(food.getName());

            Glide.with(DetailsActivity.this)
                    .load(food.getImageUrl())
                    .override(200, 200)
                    .centerCrop()
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .error(R.drawable.perfil_image_user)
                    .into(binding.imageDetails);

            binding.descriptionDetails.setText(food.getDescription());
        }
    }

}