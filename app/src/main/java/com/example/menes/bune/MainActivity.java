package com.example.menes.bune;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements EntranceAdapter.OnItemClickListener {

    private EntranceAdapter entranceadapter;
    private RecyclerView  entrance;
    ProgressDialog progressDialog;
    List<RetroAlbum> rows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<RetroAlbum>> call = service.getAllAlbums();
        call.enqueue(new Callback<List<RetroAlbum>>() {
            @Override
            public void onResponse(Call<List<RetroAlbum>> call, Response<List<RetroAlbum>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    rows = response.body();
                    generateDataList(rows);
                }
                else
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<RetroAlbum>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<RetroAlbum> albumList) {
        entrance = findViewById(R.id.customRecyclerView);
        entranceadapter = new EntranceAdapter(this,albumList);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(MainActivity.this);
        entrance.setLayoutManager(LayoutManager);
        entranceadapter.setOnItemClickListener(this);
        entrance.setAdapter(entranceadapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MasonryActivity.class);
        intent.putExtra("id", rows.get(position).getId());
        startActivity(intent);
    }
}

