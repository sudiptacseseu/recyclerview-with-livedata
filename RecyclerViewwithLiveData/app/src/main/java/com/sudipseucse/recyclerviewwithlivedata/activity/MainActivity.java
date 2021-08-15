package com.sudipseucse.recyclerviewwithlivedata.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.sudipseucse.recyclerviewwithlivedata.R;
import com.sudipseucse.recyclerviewwithlivedata.adapter.RecyclerViewListAdapter;
import com.sudipseucse.recyclerviewwithlivedata.model.HeaderData;
import com.sudipseucse.recyclerviewwithlivedata.model.SKU;
import com.sudipseucse.recyclerviewwithlivedata.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    TextView textViewTotalSKU, textViewTotalPrice;
    RecyclerViewListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewId);
        textViewTotalSKU = findViewById(R.id.textViewTotalSKU);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SKU sku1 = new SKU("Seylon Tea 250gm",120, 25, 0);
        SKU sku2 = new SKU("Seylon Tea 400gm",200, 15, 0);
        SKU sku3 = new SKU("Seylon Tea 1000gm",550, 20, 0);

        List<SKU> data = new ArrayList<>();
        data.add(sku1);
        data.add(sku2);
        data.add(sku3);

        adapter = new RecyclerViewListAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.getNumberPickerLiveData().observe(this, new Observer<HeaderData>() {
            @Override
            public void onChanged(HeaderData headerData) {
                mainViewModel.doCalculation(headerData);
                textViewTotalSKU.setText(String.valueOf(mainViewModel.totalSKUCount));
                textViewTotalPrice.setText(String.valueOf(mainViewModel.totalPrice));
            }
        });
    }
}