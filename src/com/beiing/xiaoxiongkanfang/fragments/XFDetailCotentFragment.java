package com.beiing.xiaoxiongkanfang.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.model.LatLng;
import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.Agent;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.UnitData;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;
import com.beiing.xiaoxiongkanfang.utils.LogUtil;
import com.beiing.xiaoxiongkanfang.widget.RoundImageView;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class XFDetailCotentFragment extends Fragment implements OnClickListener {

	public static XFDetailCotentFragment newInstance(String houseId) {
		XFDetailCotentFragment xfcf = new XFDetailCotentFragment();
		Bundle bundle = new Bundle();
		bundle.putString(XxKanFKeys.HOUSE_ID, houseId);
		xfcf.setArguments(bundle);
		return xfcf;
	}

	String houseId;
	XinFangDetail xinFangDetail;

	// --------------------------------model1
	TextView model1_nameTv, model1_avgTv, model1_kaiPan, model1_AdressTv;
	LinearLayout model1_features_ll;// �����ص�
	ImageButton model1_tomapIb;
	// --------------------------------model2
	LinearLayout model2_ll;
	TextView model2_discountTv, model2_person_numTv;
	// --------------------------------model3
	LinearLayout model3_ll;
	TextView model3_titleTv, model3_summaryTv;
	// --------------------------------model4
	LinearLayout model4_ll;
	ImageView model4_cover1Iv, model4_cover2Iv;
	TextView model4_tag1Tv, model4_tag2Tv;
	// --------------------------------model5
	TextView model5_info1Tv, model5_info2Tv, model5_info3Tv;
	// --------------------------------model6
	TextView model6_adressTv;
	MapView model6_mapView;
	BaiduMap model6_bdMap;
	ImageView model6_snapshotIv;
	// --------------------------------model7
	TextView model7_agentnumTv, model7_lookallTv, model7_agent_nameTv,
			model7_agent_sloganTv;
	RoundImageView model7_agent_headIv;
	ImageButton model7_agent_dialIb;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		houseId = getArguments().getString(XxKanFKeys.HOUSE_ID);
		Log.i("--", "XFDetailCotentFragment === ��houseId = " + houseId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_xf_detail_content, null);

		initModel1(view);
		initModel2(view);
		initModel3(view);
		initModel4(view);
		initModel5(view);
		initModel6(view);
		initModel7(view);
		return view;
	}

	private void initModel7(View view) {
		// TODO ��ʼ��model7
		model7_agentnumTv = (TextView) view
				.findViewById(R.id.fm_xf_model7_agent_num_tv);
		model7_lookallTv = (TextView) view
				.findViewById(R.id.fm_xf_model7_agent_lookall_tv);
		model7_agent_nameTv = (TextView) view
				.findViewById(R.id.fm_xf_detail_model7_agent_name);
		model7_agent_sloganTv = (TextView) view
				.findViewById(R.id.fm_xf_detail_model7_agent_slogan);
		model7_agent_headIv = (RoundImageView) view
				.findViewById(R.id.fm_xf_detail_model7_agent_head);
		model7_agent_dialIb = (ImageButton) view
				.findViewById(R.id.fm_xf_detail_model7_agent_dial);
		model7_agent_dialIb.setOnClickListener(this);
	}

	private void initModel6(View view) {
		// TODO ��ʼ��model6
		model6_adressTv = (TextView) view
				.findViewById(R.id.fm_xf_model6_adress);
		model6_mapView = (MapView) view
				.findViewById(R.id.fm_xf_detail_model6_mapview);
		model6_bdMap = model6_mapView.getMap();
		model6_mapView.showScaleControl(false);
		model6_mapView.showZoomControls(false);
		model6_bdMap.getUiSettings().setAllGesturesEnabled(false);
		model6_snapshotIv = (ImageView) view
				.findViewById(R.id.fm_xf_model6_snapshot);
		model6_snapshotIv.setOnClickListener(this);
	}

	private void initModel5(View view) {
		// TODO ��ʼ��model5
		model5_info1Tv = (TextView) view
				.findViewById(R.id.fm_xf_model5_detail_info1);
		model5_info2Tv = (TextView) view
				.findViewById(R.id.fm_xf_model5_detail_info2);
		model5_info3Tv = (TextView) view
				.findViewById(R.id.fm_xf_model5_detail_info3);
	}

	private void initModel4(View view) {
		// TODO ��ʼ��model4
		model4_ll = (LinearLayout) view.findViewById(R.id.fm_xf_model4_ll);
		model4_cover1Iv = (ImageView) view
				.findViewById(R.id.fm_xf_model4_cover1_iv);
		model4_cover2Iv = (ImageView) view
				.findViewById(R.id.fm_xf_model4_cover2_iv);
		model4_tag1Tv = (TextView) view.findViewById(R.id.fm_xf_model4_tag1_tv);
		model4_tag2Tv = (TextView) view.findViewById(R.id.fm_xf_model4_tag2_tv);
		model4_ll.setOnClickListener(this);
	}

	private void initModel3(View view) {
		// TODO ��ʼ��model3
		model3_ll = (LinearLayout) view.findViewById(R.id.fm_xf_model3_ll);
		model3_titleTv = (TextView) view
				.findViewById(R.id.fm_xf_model3_titleTv);
		model3_summaryTv = (TextView) view
				.findViewById(R.id.fm_xf_model3_summaryTv);
		model3_ll.setOnClickListener(this);
	}

	private void initModel2(View view) {
		// TODO ��ʼ��model2
		model2_ll = (LinearLayout) view.findViewById(R.id.fm_xf_model2_ll);
		model2_discountTv = (TextView) view
				.findViewById(R.id.fm_xf_model2_discount_tv);
		model2_person_numTv = (TextView) view
				.findViewById(R.id.fm_xf_model2_tv);
		model2_ll.setOnClickListener(this);
	}

	private void initModel1(View view) {
		// TODO ��ʼ��ģ��1
		model1_nameTv = (TextView) view.findViewById(R.id.fm_xf_model1_nameTv);
		model1_avgTv = (TextView) view.findViewById(R.id.fm_xf_model1_avgTv);
		model1_kaiPan = (TextView) view
				.findViewById(R.id.fm_xf_model1_kaipanTv);
		model1_AdressTv = (TextView) view
				.findViewById(R.id.fm_xf_model1_addressTv);
		model1_features_ll = (LinearLayout) view
				.findViewById(R.id.fm_xf_model1_features_ll);
		model1_tomapIb = (ImageButton) view
				.findViewById(R.id.fm_xf_model1_tomap_ib);
		model1_tomapIb.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					xinFangDetail = (XinFangDetail) result;
					LogUtil.i("--", "xinFangDetail:" + xinFangDetail.toString());
					List<String> info = xinFangDetail.getInfo();
					Map<String, String> infoMap = getInfoMap(info);// ��ϸ��Ϣ

					showData123(infoMap);

					showDataModel4();

					showDataModel5(infoMap);

					showDataModel67(infoMap);
				}
			}
		}).execute(
				String.format(XxKanFUrls.NEW_HOUSE_INFO,
						Integer.parseInt(houseId)),
				LoadAsyncTask.LOAD_XINFANG_DETAIL);
	}

	private void showData123(Map<String, String> infoMap) {
		// ��ʾ����--model1
		String[] features = xinFangDetail.getFeatures().split(",");// �����ص�
		model1_nameTv.setText(xinFangDetail.getName());
		model1_avgTv.setText("����" + xinFangDetail.getPrice());
		model1_kaiPan.setText("���̣�" + infoMap.get("����ʱ��") + " ����");
		model1_AdressTv.setText("��ַ��" + infoMap.get("¥��λ��"));
		for (int i = 0; i < features.length; i++) {
			TextView tagTv = new TextView(getActivity());
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.rightMargin = 5;
			tagTv.setPadding(5, 5, 5, 5);
			tagTv.setLayoutParams(params);
			tagTv.setGravity(Gravity.CENTER);
			tagTv.setText(features[i]);
			tagTv.setBackgroundResource(R.drawable.bg_house_detail_tag);
			tagTv.setTextSize(12);
			model1_features_ll.addView(tagTv);
		}

		// ��ʾ����--model2
		model2_discountTv.setText(xinFangDetail.getDiscount());
		model2_person_numTv.setText(xinFangDetail.getWanttosigned() + "�˻�ȡ");

		// ��ʾ����--model3
		model3_titleTv.setText(xinFangDetail.getTitle());
		model3_summaryTv.setText(xinFangDetail.getSummary());
	}

	private void showDataModel4() {
		// ��ʾ����--model4
		List<UnitData> unitlist = xinFangDetail.getUnitlist();
		if (unitlist.size() >= 2) {
			model4_tag1Tv.setText(unitlist.get(0).getDesc());
			model4_tag2Tv.setText(unitlist.get(1).getDesc());
			new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
				@Override
				public void imageLoadSuccess(Bitmap bitmap) {
					model4_cover1Iv.setBackground(ImageUtil.getBdFromBitmap(
							getActivity(), bitmap));
				}
			}).execute(unitlist.get(0).getUrl());

			new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
				@Override
				public void imageLoadSuccess(Bitmap bitmap) {
					model4_cover2Iv.setBackground(ImageUtil.getBdFromBitmap(
							getActivity(), bitmap));
				}
			}).execute(unitlist.get(1).getUrl());
		}
	}

	private void showDataModel5(Map<String, String> infoMap) {
		// ��ʾ����--model5
		model5_info1Tv.append("��ҵ���:\t" + infoMap.get("��ҵ���") + "\n");
		model5_info1Tv.append("����ʱ��:\t" + infoMap.get("����ʱ��") + "\n");
		model5_info1Tv.append("��סʱ��:\t" + infoMap.get("��סʱ��") + "\n");
		model5_info1Tv.append("��ҵ    ��:\t" + infoMap.get("��ҵ��") + "\n");
		model5_info1Tv.append("����    ��:\t" + infoMap.get("������") + "\n");
		model5_info1Tv.append("�������:\t" + infoMap.get("�������") + "\n");

		model5_info2Tv.append("��Ȩ����:\t" + infoMap.get("��Ȩ����") + "\n");
		model5_info2Tv.append("�������:\t" + infoMap.get("�������") + "\n");
		model5_info2Tv.append("ռ�����:\t" + infoMap.get("ռ�����") + "\n");
		model5_info2Tv.append("�ܻ�    ��:\t" + infoMap.get("�ܻ���") + "\n");
		model5_info2Tv.append("�������:\t" + infoMap.get("�������") + "\n");

		model5_info3Tv.append("װ��״��:\t" + infoMap.get("װ��״��") + "\n");
		model5_info3Tv.append("�ݻ�    ��:\t" + infoMap.get("�ݻ���") + "\n");
		model5_info3Tv.append("�̻�    ��:\t" + infoMap.get("�̻���") + "\n");
		model5_info3Tv.append("��ҵ��˾:\t" + infoMap.get("��ҵ��˾") + "\n");
	}

	private void showDataModel67(Map<String, String> infoMap) {
		// ��ʾ����--model6
		model6_adressTv.setText(infoMap.get("¥��λ��"));
		LatLng ll = new LatLng(Double.parseDouble(xinFangDetail.getLat()),
				Double.parseDouble(xinFangDetail.getLng()));
		model6_bdMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(ll,
				15f));
		// ʵ������עͼ���������
		MarkerOptions options = new MarkerOptions().position(ll).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding));
		// ����ͼ�����ӵ���ͼ��
		model6_bdMap.addOverlay(options);

		try {
			// �����ٽ�ͼ����Ȼ��λ������δ���
			Thread.sleep(1000);
			// ����
			model6_bdMap.snapshot(new SnapshotReadyCallback() {
				@Override
				public void onSnapshotReady(Bitmap bitmap) {
					// TODO �����ɹ��ķ���
					model6_snapshotIv.setBackground(ImageUtil.getBdFromBitmap(
							getActivity(), bitmap));
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		// ��ʾ����--model7
		List<Agent> agent = xinFangDetail.getAgent();
		if (agent.size() > 0) {
			model7_agentnumTv.setText("��ҵ����(��" + agent.size() + "��)");
			model7_agent_nameTv.setText(agent.get(0).getName());
			model7_agent_sloganTv.setText(agent.get(0).getSlogan());
			String url = agent.get(0).getAvatar();
			new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
				@Override
				public void imageLoadSuccess(Bitmap bitmap) {
					if (bitmap != null)
						model7_agent_headIv.setBackground(ImageUtil
								.getBdFromBitmap(getActivity(), bitmap));
				}
			});

		}
	}

	private Map<String, String> getInfoMap(List<String> info) {
		Map<String, String> infoMap = new HashMap<String, String>();
		for (int i = 0; i < info.size(); i++) {
			// LogUtil.i("--", "info:" + info.get(i));
			String[] infos = info.get(i).split(":");
			infoMap.put(infos[0], infos[1]);
		}

		return infoMap;
	}

	@Override
	public void onClick(View v) {

	}

}
