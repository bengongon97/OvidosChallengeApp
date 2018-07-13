package com.example.menes.bune;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasonryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    MasonryAdapter adapter;
    List<RetroPhoto> rows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.masonry_layout);

        progressDialog = new ProgressDialog(MasonryActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Intent getNumbers= getIntent();
        Bundle bundle = getNumbers.getExtras();

        if(bundle != null)
        {
            Integer albumId = (Integer) bundle.get("id");

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<RetroPhoto>> call = service.getAlbumPhotos(albumId);

            final Configuration config = this.getResources().getConfiguration();


        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    rows = response.body();
                    generateDataList(rows, config);
                }
                else
                    Toast.makeText(MasonryActivity.this, "Request was not successful. Sorry :(", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MasonryActivity.this, "Response failed!", Toast.LENGTH_SHORT).show();
            }
        });

        }
    }

 @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
           portraitMode();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           landscapeMode();
        }
    };

    public void portraitMode () {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
    public void landscapeMode () {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void generateDataList(List<RetroPhoto> photoList, Configuration newConfig) {

        mRecyclerView = findViewById(R.id.masonryRecycler);

        onConfigurationChanged(newConfig);


        adapter = new MasonryAdapter(this, photoList);
        mRecyclerView.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
    }

}
