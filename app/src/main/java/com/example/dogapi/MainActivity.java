package com.example.dogapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button buttonRandom;
    private ImageView imageViewDog;
    /*
     so you found API how is the data sent to us?

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wirewidgets();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DogService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DogService dogService = retrofit.create(DogService.class);
        Call<dog> dogCall = dogService.getRandomImageByBreed("hound");
        dogCall.enqueue(new Callback<dog>() {
            @Override
            public void onResponse(Call<dog> call, Response<dog> response) {
                dog foundDog = response.body();
                if (foundDog != null) {
                    Picasso.get().load(foundDog.getMessage()).into(imageViewDog);
                }
            }

            @Override
            public void onFailure(Call<dog> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomDog(dogService);
            }
        });

    }


    private void randomDog(DogService dogService) {
        Call<dog> dogCall = dogService.getRandomImageByBreed("hound");
        dogCall.enqueue(new Callback<dog>() {
            @Override
            public void onResponse(Call<dog> call, Response<dog> response) {
                dog foundDog = response.body();
                if (foundDog != null) {
                    Picasso.get().load(foundDog.getMessage()).into(imageViewDog);
                }
            }

            @Override
            public void onFailure(Call<dog> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wirewidgets() {
        buttonRandom = findViewById(R.id.button_main_random);
        imageViewDog = findViewById(R.id.imageView_main_dogImage);
    }

}
