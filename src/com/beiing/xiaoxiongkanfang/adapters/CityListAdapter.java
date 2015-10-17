package com.beiing.xiaoxiongkanfang.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.entity.City;

public class CityListAdapter extends BaseAdapter {

	private Context context;
	private List<City> list;
	
	public CityListAdapter(Context context, List<City> list){
		this.context = context;
		this.list = list;
	}
	
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getType();
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return City.getTypeCount();
	}

	@Override
	public boolean isEnabled(int position) {
		if(getItemViewType(position) == City.TYPE_LABEL)
			return false;
		return super.isEnabled(position);
	}


	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(list.get(position).getCityid());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = null;
		if(convertView == null){
			if(getItemViewType(position)==City.TYPE_LABEL){
				convertView=LayoutInflater.from(context).inflate(R.layout.ac_city_item_label,null);
			}else{
				convertView=LayoutInflater.from(context).inflate(R.layout.ac_city_item_city,null);
			}
			
			textView = (TextView) convertView.findViewById(R.id.ac_city_item_tv);
			convertView.setTag(textView);
		} else {
			textView = (TextView) convertView.getTag();
		}
		
		textView.setText(list.get(position).getCityname());
		
		return convertView;
	}
	
}



