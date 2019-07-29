package com.shangtao.base.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class DataAdapter<T> extends BaseAdapter{


  public List<T> dataSource = new ArrayList<T>(0);
  @Override
  public int getCount(){
    return dataSource.size();
  }
  @Override
  public Object getItem(int position){
    return position;
  }
  @Override
  public long getItemId(int position){
    return position;
  }


}
