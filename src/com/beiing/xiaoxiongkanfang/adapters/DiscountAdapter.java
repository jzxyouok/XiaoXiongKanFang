package com.beiing.xiaoxiongkanfang.adapters;

import java.util.ArrayList;
import java.util.List;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.application.XxKanFApplication;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.entity.Discount;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DiscountAdapter extends BaseAdapter {

	private List<Discount> list;
	private Context context;

	private ListView listView;

	private String url;

	public DiscountAdapter(Context context) {
		this.context = context;
		list = new ArrayList<Discount>();
	}

	public void addData(List<Discount> newData) {
		list.addAll(newData);
		notifyDataSetChanged();
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (parent != null)
			listView = (ListView) parent;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.ac_discount_lv_item, parent, false);
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_name_tv);
			holder.addresstv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_address_tv);
			holder.preTv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_pre_tv);
			holder.valueTv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_value_tv);
			holder.unitTv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_unit_tv);
			holder.discountTv = (TextView) convertView
					.findViewById(R.id.ac_dc_item_discount_tv);
			holder.coverIv = (ImageView) convertView
					.findViewById(R.id.ac_dc_item_cover_iv);
			holder.getBtn = (Button) convertView
					.findViewById(R.id.ac_dc_item_get_btn);
			holder.dialBtn = (Button) convertView
					.findViewById(R.id.ac_dc_item_dial_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Discount discount = list.get(position);

		holder.nameTv.setText(discount.getName());
		holder.addresstv.setText(discount.getAddress());
		holder.preTv.setText(discount.getPrice_pre());
		holder.valueTv.setText(discount.getPrice_value());
		holder.unitTv.setText(discount.getPrice_unit());
		holder.discountTv.setText("”≈ª›£∫" + discount.getDiscount());

		url = discount.getCover();
		if (url != null) {
			holder.coverIv.setBackgroundResource(R.drawable.list_default_image);
			holder.coverIv.setTag(url);
			Bitmap bitmap = BitmapCache.getInstance().getBitmap(url);
			if (bitmap != null) {
				holder.coverIv.setBackground(ImageUtil.getBdFromBitmap(context,
						bitmap));
			} else {
				new ImageLoadAsyncTask(100, 100, new LoadImageListner() {
					@Override
					public void imageLoadSuccess(Bitmap bitmap) {
						if (bitmap != null) {
							ImageView cover = (ImageView) listView
									.findViewWithTag(url);
							if (cover != null)
								cover.setBackground(ImageUtil.getBdFromBitmap(
										context, bitmap));
						}
					}
				}).execute(url);
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView nameTv, addresstv, preTv, valueTv, unitTv, discountTv;
		ImageView coverIv;
		Button getBtn, dialBtn;
	}

}
