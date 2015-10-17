package com.beiing.xiaoxiongkanfang.adapters;

import java.util.List;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.application.XxKanFApplication;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.entity.InfoComment;
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

public class InfoCommentAdapter extends BaseAdapter {

	private Context context;
	private List<InfoComment> list;
	
	private ListView listView;

	public InfoCommentAdapter(Context context, List<InfoComment> list) {
		this.context = context;
		this.list = list;
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
		if(parent != null)
			listView = (ListView) parent;
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fm_info_comments_item, parent, false);
			holder.head = (ImageView) convertView
					.findViewById(R.id.fm_info_comts_item_head);
			holder.nickTv = (TextView) convertView
					.findViewById(R.id.fm_info_comts_item_nick);
			holder.timeLocTv = (TextView) convertView
					.findViewById(R.id.fm_info_comts_item_timeloc);
			holder.contentTv = (TextView) convertView
					.findViewById(R.id.fm_info_comts_item_cotent);
			holder.replyTv = (TextView) convertView
					.findViewById(R.id.fm_info_comts_item_reply);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		InfoComment comment = list.get(position);
		holder.nickTv.setText(comment.getNick());
		holder.timeLocTv.setText(comment.getTimeLoc());
		holder.contentTv.setText(comment.getContent());
		if(comment.getIsreply() == 1){
			holder.replyTv.setText(comment.getReply());
		}

		final String headUrl = comment.getHead();
		if(headUrl != null){
			holder.head.setBackgroundResource(R.drawable.list_default_image);
			holder.head.setTag(headUrl);
			Bitmap bitmap = BitmapCache.getInstance().getBitmap(headUrl);
			if (bitmap != null) {
				holder.head.setBackground(ImageUtil.getBdFromBitmap(context,
						bitmap));
			} else {
				new ImageLoadAsyncTask(100, 100, new LoadImageListner() {
					@Override
					public void imageLoadSuccess(Bitmap bitmap) {
						ImageView head = (ImageView) listView.findViewWithTag(headUrl);
						if(head != null)
							head.setImageBitmap(bitmap);
					}
				}).execute(headUrl);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView head;
		TextView nickTv, timeLocTv, contentTv, replyTv;
	}

}
