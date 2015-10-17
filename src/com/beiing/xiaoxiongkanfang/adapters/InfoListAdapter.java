package com.beiing.xiaoxiongkanfang.adapters;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.application.XxKanFApplication;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.entity.City;
import com.beiing.xiaoxiongkanfang.entity.Information;
import com.beiing.xiaoxiongkanfang.utils.FileUtil;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InfoListAdapter extends BaseAdapter {

	private Context context;

	private List<Information> list;

	private ListView listView;

	public InfoListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<Information>();
	}

	public void addData(List<Information> data) {
		list.addAll(data);
		notifyDataSetChanged();
	}

	public void clear() {
		list.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return Information.getTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		return list.get(position).getItemViewType();
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
		ViewHoler holder = null;
		if (convertView == null) {
			holder = new ViewHoler();
			if (getItemViewType(position) == Information.NORMAL_PIC) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.ac_main_lv_item_1, parent, false);
				holder.summaryTv = (TextView) convertView
						.findViewById(R.id.ac_main_lv_item_summary);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.ac_main_lv_item_2, parent, false);
			}
			holder.picIv = (ImageView) convertView
					.findViewById(R.id.ac_main_lv_item_pic);
			holder.titleTv = (TextView) convertView
					.findViewById(R.id.ac_main_lv_item_title);
			holder.commentsCountTv = (TextView) convertView
					.findViewById(R.id.ac_main_lv_item_commentcount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHoler) convertView.getTag();
		}
		Information info = list.get(position);
		holder.titleTv.setText(info.getTitle());

		if (holder.summaryTv != null)
			holder.summaryTv.setText(info.getSummary());

		if (info.getCommentcount() != 0)
			holder.commentsCountTv.setText(info.getCommentcount() + "ÆÀ");

		if (getItemViewType(position) == Information.NORMAL_PIC) {
			holder.picIv.setBackgroundResource(R.drawable.list_default_image);
		} else {
			holder.picIv
					.setBackgroundResource(R.drawable.news_three_default_img);
		}

		final String imgurl = info.getThumbnail();
		if (imgurl != null) {
			holder.picIv.setTag(imgurl);
			Bitmap bitmap = BitmapCache.getInstance().getBitmap(imgurl);
			if (bitmap != null) {
				holder.picIv.setBackground(ImageUtil.getBdFromBitmap(context,
						bitmap));
			} else {
				new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
					@Override
					public void imageLoadSuccess(Bitmap bitmap) {
						if (bitmap != null) {
							ImageView head = (ImageView) listView
									.findViewWithTag(imgurl);
							if (head != null)
								head.setBackground(ImageUtil.getBdFromBitmap(
										context, bitmap));
						}
					}
				}).execute(imgurl);
			}
		}

		return convertView;
	}

	class ViewHoler {
		ImageView picIv;
		TextView titleTv;
		TextView summaryTv;
		TextView commentsCountTv;
	}

}
