# CommonRecyclerAdapter

通用recycler adapter

# Use
```

CommonRecyclerAdapter recyclerAdapter = new CommonRecyclerAdapter();
recyclerAdapter.addItemInfo(R.layout.item_custom_data, CustomData.class, CustomDataViewModel.class);
rvContent.setAdapter(recyclerAdapter);

//添加自定义数据布局
recyclerAdapter.addData(new CustomData("customData"+new Random().nextInt(100)));
//添加占位布局     
recyclerAdapter.addLayout(R.layout.item_place_holder_lay1);
//添加占位布局
recyclerAdapter.addLayout(R.layout.item_place_holder_lay2);

```

![Image](https://github.com/msilemsile/CommonRecyclerAdapter/blob/master/demo.gif)
