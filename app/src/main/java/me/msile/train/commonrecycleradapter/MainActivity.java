package me.msile.train.commonrecycleradapter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import me.msile.lib.commonrecycleradapter.CommonRecyclerAdapter;
import me.msile.train.commonrecycleradapter.custom.CustomData;
import me.msile.train.commonrecycleradapter.custom.CustomDataViewHolder;
import me.msile.train.commonrecycleradapter.samedata.SameData;
import me.msile.train.commonrecycleradapter.samedata.SameDataViewHolder1;
import me.msile.train.commonrecycleradapter.samedata.SameDataViewHolder2;
import me.msile.train.commonrecycleradapter.samedata.SameDataViewHolder3;
import me.msile.train.commonrecycleradapter.samedata.SameDataViewTypeHolder;

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
        recyclerAdapter = new CommonRecyclerAdapter(false);
        recyclerAdapter.addViewHolderFactory(new CustomDataViewHolder.Factory());
        recyclerAdapter.addViewHolderFactory(new SameDataViewHolder1.Factory());
        recyclerAdapter.addViewHolderFactory(new SameDataViewHolder2.Factory());
        recyclerAdapter.addViewHolderFactory(new SameDataViewHolder3.Factory());
        recyclerAdapter.setItemViewTypeHolder(SameData.class,new SameDataViewTypeHolder());
        rvContent.setAdapter(recyclerAdapter);
    }

    public void addCustomData(View v){
        recyclerAdapter.addData(new CustomData("customData"+new Random().nextInt(100)));
    }

    public void addSameData1(View v){
        recyclerAdapter.addData(new SameData(1));
    }

    public void addSameData2(View v){
        recyclerAdapter.addData(new SameData(2));
    }

    public void addSameData3(View v){
        recyclerAdapter.addData(new SameData(3));
    }

}