package com.beiing.xiaoxiongkanfang.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.application.XxKanFApplication;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.entity.XinFangList;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.BookMark;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.XinFang;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;

public class XinFangListAdapter extends BaseAdapter {

	private Context context;

	private List<XinFang> list;

	private ListView listView;

	public XinFangListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<XinFangList.XinFang>();
	}

	public void addData(List<XinFang> data) {
		list.addAll(data);
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
					R.layout.fm_xinfang_item, parent, false);
			holder.coverIv = (ImageView) convertView
					.findViewById(R.id.fm_xinfang_cover);
			holder.nameTv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_name);
			holder.regionTv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_region);
			holder.priceTv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_price);
			holder.addressTv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_address);
			holder.tag1Tv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_tag1);
			holder.tag2Tv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_tag2);
			holder.tag3Tv = (TextView) convertView
					.findViewById(R.id.fm_xinfang_tag3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		XinFang fang = list.get(position);
		if(fang != null){
			holder.nameTv.setText(fang.getFname());
			holder.regionTv.setText(fang.getFregion());
			holder.priceTv.setText(fang.getFpricedisplaystr());
			holder.addressTv.setText(fang.getFaddress());
			initTag(holder, fang);

			final String imgUrl = fang.getFcover();
			if (imgUrl != null) {
				holder.coverIv.setBackgroundResource(R.drawable.list_default_image);
				holder.coverIv.setTag(imgUrl);
				Bitmap bitmap = BitmapCache.getInstance().getBitmap(imgUrl);
				if (bitmap != null)
					holder.coverIv.setBackground(ImageUtil.getBdFromBitmap(context,
							bitmap));
				else {
					new ImageLoadAsyncTask(100, 100, new LoadImageListner() {
						@Override
						public void imageLoadSuccess(Bitmap bitmap) {
							ImageView head = (ImageView) listView
									.findViewWithTag(imgUrl);
							if (head != null)
								head.setImageBitmap(bitmap);
						}
					}).execute(imgUrl);
				}
			}
		}
		return convertView;
	}

	private void initTag(ViewHolder holder, XinFang fang) {
		List<BookMark> marks = fang.getBookMarks();
		if (marks.size() == 0)
			return;
		try {
			holder.tag1Tv.setText(marks.get(0).getTag());
			holder.tag2Tv.setText(marks.get(1).getTag());
			holder.tag3Tv.setText(marks.get(2).getTag());
		} catch (Exception e) {
			return;
		}
	}

	class ViewHolder {
		ImageView coverIv;
		TextView nameTv, regionTv, priceTv, addressTv, tag1Tv, tag2Tv, tag3Tv;

	}

}
