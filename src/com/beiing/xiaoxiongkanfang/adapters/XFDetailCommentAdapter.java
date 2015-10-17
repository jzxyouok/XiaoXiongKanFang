package com.beiing.xiaoxiongkanfang.adapters;

import java.util.ArrayList;
import java.util.List;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetailComment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class XFDetailCommentAdapter extends BaseAdapter {

	private Context context;
	private List<XinFangDetailComment> list;

	public XFDetailCommentAdapter(Context context) {
		this.context = context;
		list = new ArrayList<XinFangDetailComment>();
	}

	public void addData(List<XinFangDetailComment> newData) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fm_xf_detail_comment_item, parent, false);
			holder.userTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_user_tv);
			holder.scoreTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_score_tv);
			holder.summaryTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_summary_tv);
			holder.advTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_adv_tv);
			holder.disadvTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_disadv_tv);
			holder.dateTv = (TextView) convertView
					.findViewById(R.id.fm_xf_detail_com_date_tv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		XinFangDetailComment comment = list.get(position);
		holder.userTv.setText(comment.getUser());
		if(comment.getScore() != null && !TextUtils.isEmpty(comment.getScore()))
			holder.scoreTv.setText("评分：" + comment.getScore());
		else
			holder.scoreTv.setVisibility(View.GONE);
		
		if(comment.getSummary() != null && !TextUtils.isEmpty(comment.getSummary()))
			holder.summaryTv.setText("总结：" + comment.getSummary());
		else
			holder.summaryTv.setVisibility(View.GONE);
		
		if(comment.getAdv() != null && !TextUtils.isEmpty(comment.getAdv()))
			holder.advTv.setText("优点：" + comment.getAdv());
		else
			holder.advTv.setVisibility(View.GONE);
		
		if(comment.getDisadv() != null && !TextUtils.isEmpty(comment.getDisadv()))
			holder.disadvTv.setText("缺点：" + comment.getDisadv());
		else
			holder.disadvTv.setVisibility(View.GONE);
		
		holder.dateTv.setText(comment.getDate());
		return convertView;
	}

	class ViewHolder {
		TextView userTv, scoreTv, summaryTv, advTv, disadvTv, dateTv;
	}

}
