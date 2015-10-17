package com.beiing.xiaoxiongkanfang.fragments;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.XinFangDetailActivity;
import com.beiing.xiaoxiongkanfang.R.id;
import com.beiing.xiaoxiongkanfang.application.XxKanFApplication;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.XinFangList;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.XinFang;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;
import com.beiing.xiaoxiongkanfang.utils.LogUtil;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class XinFangMapFragment extends Fragment implements OnClickListener {

	public static XinFangMapFragment newInStance(String cityid, String cityName) {
		XinFangMapFragment xfmf = new XinFangMapFragment();
		Bundle bundle = new Bundle();
		bundle.putString(XxKanFKeys.CITY_ID, cityid);
		bundle.putString(XxKanFKeys.CITY_NAME, cityName);
		xfmf.setArguments(bundle);
		return xfmf;
	}

	private MapView mapView;

	private BaiduMap bdMap;// ��ͼ������

	private TextView titleTv, tipTv;
	private ImageButton leftIb, rightIb;

	private int curPage = 1;// ��ǰҳ

	private String cityId;
	private String cityName;

	private List<XinFang> xfList;

	private String total;// �ܸ���

	private RelativeLayout infoView;// ������Ϣ������
	private ImageView info_cover;
	private TextView avgPreTv, avgTv, avgUnitTv, aroundTv;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cityId = getArguments().getString(XxKanFKeys.CITY_ID);
		cityName = getArguments().getString(XxKanFKeys.CITY_NAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_xinfang_map, null);
		mapView = (MapView) view.findViewById(R.id.fm_xinfang_map_mapview);
		titleTv = (TextView) view.findViewById(R.id.fm_xinfang_map_title);
		tipTv = (TextView) view.findViewById(R.id.fm_xinfang_map_tip);
		leftIb = (ImageButton) view.findViewById(R.id.fm_xinfang_map_left_ib);
		rightIb = (ImageButton) view.findViewById(R.id.fm_xinfang_map_right_ib);

		leftIb.setOnClickListener(this);
		rightIb.setOnClickListener(this);

		// ��ʼ��������
		infoView = (RelativeLayout) view
				.findViewById(R.id.fm_xinfang_info_window_rl);
		info_cover = (ImageView) view.findViewById(R.id.fm_xf_map_cover);
		avgPreTv = (TextView) view.findViewById(R.id.fm_xf_map_average_pre_tv);
		avgTv = (TextView) view.findViewById(R.id.fm_xf_map_average_tv);
		avgUnitTv = (TextView) view
				.findViewById(R.id.fm_xf_map_average_unit_tv);
		aroundTv = (TextView) view.findViewById(R.id.fm_xf_map_around_tv);

		infoView.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		titleTv.setText("��ʾ" + cityName + "����¥��");
		bdMap = mapView.getMap();
		xfList = new ArrayList<XinFangList.XinFang>();

		loadFangToMap(curPage);

		markerClickEvent();// ��ͼ�ϱ�ǵ���¼�

		bdMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				bdMap.hideInfoWindow();
				if (infoView.isShown())
					infoView.setVisibility(View.GONE);
			}
		});
	}

	private void markerClickEvent() {
		// TODO ��ͼ�ϱ�ǵ���¼�
		bdMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker maker) {
				XinFang fang = (XinFang) maker.getExtraInfo().get("xfInfo");
				showInfoWindow(fang);
				TextView tv = getMarkerTv(fang.getFname(),
						R.drawable.map_tips_info_blue);
				// ������Ϣ����InfoWindow
				InfoWindow infoWindow = new InfoWindow(tv, maker.getPosition(),
						0);
				bdMap.showInfoWindow(infoWindow);
				return true;
			}
		});
	}

	private void showInfoWindow(XinFang fang) {
		infoView.setTag(fang);
		avgPreTv.setText(fang.getPrice_pre());
		avgTv.setText(fang.getPrice_value());
		avgUnitTv.setText(fang.getPrice_unit());
		aroundTv.setText("�ܱ�" + fang.getFaroundlowprice() + "-"
				+ fang.getFaroundhighprice());
		String url = fang.getFcover();
		if (url != null) {
			Bitmap bm = BitmapCache.getInstance().getBitmap(url);
			if (bm != null)
				info_cover.setBackground(ImageUtil.getBdFromBitmap(
						getActivity(), bm));
			else {
				new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
					@Override
					public void imageLoadSuccess(Bitmap bitmap) {
						info_cover.setBackground(ImageUtil.getBdFromBitmap(
								getActivity(), bitmap));
					}
				}).execute(url);
			}
		}

		infoView.setVisibility(View.VISIBLE);
	}

	private void loadFangToMap(final int curPage) {
		// TODO ��curPage*10 ~ curPage*10+10֮��ķ������һ��ͼ�굽��ͼ��
		if (xfList.size() > (curPage - 1) * 10) {
			addMarkers(curPage);
		} else {
			new LoadAsyncTask(getActivity(), new LoadListener() {
				@Override
				public void loadSeccess(Object result) {
					if (result != null) {
						XinFangList xinFangList = (XinFangList) result;
						total = xinFangList.getTotal();
						xfList.addAll(xinFangList.getXfList());
						// �����������һҳ���ݵ��·�
						addMarkers(curPage);
						tipTv.setText(10 * (curPage - 1) + 1 + "-"
								+ xfList.size() + " (��" + total + "��¥��)");
					}
				}
			}).execute(getUrl(curPage), LoadAsyncTask.LOAD_XINFANG_LIST);
		}
	}

	// ���һ����ǵ���ͼ��
	private void addMarkers(int curPage) {
		bdMap.clear();// �����һ��
		XinFang fang = null;
		LatLng latLng = null;
		for (int i = 10 * (curPage - 1); i < xfList.size(); i++) {
			// ʵ������עͼ���������
			fang = xfList.get(i);
			if (fang != null) {
				latLng = new LatLng(Double.parseDouble(fang.getLat()),
						Double.parseDouble(fang.getLng()));

				TextView tv = getMarkerTv(fang.getFname(),
						R.drawable.map_tips_info);

				BitmapDescriptor fBdf = BitmapDescriptorFactory.fromView(tv);
				tv.destroyDrawingCache();
				OverlayOptions oo = new MarkerOptions().position(latLng)
						.icon(fBdf).zIndex(9).draggable(true);

				Bundle bundle = new Bundle();
				Marker marker = (Marker) bdMap.addOverlay(oo);
				bundle.putSerializable("xfInfo", fang);
				marker.setExtraInfo(bundle);
			}
		}
		// ����ͼ������λ���ƶ�����ע��λ���ϣ�������ͼ��ļ�����10��
		bdMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLng, 11));
	}

	// ����һ��TextView ���
	private TextView getMarkerTv(String name, int bgRes) {
		TextView tv = new TextView(getActivity());
		tv.setTextSize(13f);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundResource(bgRes);
		tv.setText(name);
		return tv;
	}

	private String getUrl(int curPage) {
		return String.format(XxKanFUrls.LOOKING_NEWHOUSE, curPage, cityId);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fm_xinfang_map_left_ib:
			if (curPage > 1) {
				loadFangToMap(--curPage);
				tipTv.setText(10 * (curPage - 1) + 1 + "-" + xfList.size()
						+ " (��" + total + "��¥��)");
			}

			break;
		case R.id.fm_xinfang_map_right_ib:
			loadFangToMap(++curPage);
			break;

		case R.id.fm_xinfang_info_window_rl:
			XinFang fang = (XinFang) v.getTag();
			XinFangDetailActivity.startXinFangDetailActivity(getActivity(),
					fang.getFid(), fang.getFcover());
			break;
		}

		if (curPage == 1)
			leftIb.setVisibility(View.GONE);
		else
			leftIb.setVisibility(View.VISIBLE);
	}

}
