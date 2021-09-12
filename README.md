# CommonRecyclerAdapter

通用recycler adapter

implementation "io.github.msilemsile:commonrecycleradapter:1.0.0"

# Use
```


//初始化
CommonRecyclerAdapter recyclerAdapter = new CommonRecyclerAdapter();
recyclerAdapter = new CommonRecyclerAdapter(false);
recyclerAdapter.addViewHolderFactory(new CustomDataViewHolder.Factory());
recyclerAdapter.addViewHolderFactory(new SameDataViewHolder1.Factory());
recyclerAdapter.addViewHolderFactory(new SameDataViewHolder2.Factory());
recyclerAdapter.addViewHolderFactory(new SameDataViewHolder3.Factory());

//添加自定义数据布局
recyclerAdapter.addData(new CustomData("customData"+new Random().nextInt(100)));

//添加相同数据类型 不同type布局
recyclerAdapter.addData(new SameData(1));

recyclerAdapter.addData(new SameData(2));

recyclerAdapter.addData(new SameData(3));

```

![Image](https://github.com/msilemsile/CommonRecyclerAdapter/blob/master/demo.gif)
