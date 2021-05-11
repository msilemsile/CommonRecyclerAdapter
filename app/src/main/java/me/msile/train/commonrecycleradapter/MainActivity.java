package me.msile.train.commonrecycleradapter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import me.msile.train.commonrecycleradapter.adapter.CommonRecyclerAdapter;
import me.msile.train.commonrecycleradapter.custom.CustomData;
import me.msile.train.commonrecycleradapter.custom.CustomDataViewHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private CommonRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        rvContent = findViewById(R.id.rv_content);
        recyclerAdapter = new CommonRecyclerAdapter();
        recyclerAdapter.addItemInfo(R.layout.item_custom_data, CustomData.class, CustomDataViewHolder.class);
        rvContent.setAdapter(recyclerAdapter);
    }

    public void addCustomData(View v){
        recyclerAdapter.addData(new CustomData("customData"+new Random().nextInt(100)));
    }

    public void addPlaceHolder1(View v){
        recyclerAdapter.addLayout(R.layout.item_place_holder_lay1);
    }

    public void addPlaceHolder2(View v){
        recyclerAdapter.addLayout(R.layout.item_place_holder_lay2);
    }

}