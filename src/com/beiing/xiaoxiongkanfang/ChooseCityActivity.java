package com.beiing.xiaoxiongkanfang;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.adapters.CityListAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFConfigs;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.City;
import com.beiing.xiaoxiongkanfang.widget.SideBar;
import com.beiing.xiaoxiongkanfang.widget.SideBar.OnTouchingLetterChangedListener;

public class ChooseCityActivity extends Activity {

	private ListView cityListView;
	private SideBar sideBar;
	private TextView textDialog;// 选择时显示的提示框

	private CityListAdapter adapter;
	private List<City> list;

	private String curCityId;

	private EditText cityEdit;
	private TextView locationTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);
		getWindow().setWindowAnimations(R.style.activity_enter_exit_1);
		cityListView = (ListView) findViewById(R.id.ac_choose_city_lv);
		cityEdit = (EditText) findViewById(R.id.ac_choose_city_edit);
		locationTv = (TextView) LayoutInflater.from(this).inflate(
				R.layout.ac_choose_city_location_layout, null);
		cityListView.addHeaderView(locationTv);
		curCityId = getIntent().getStringExtra(XxKanFKeys.CITY_ID);
		loadData();

		initSideBar();

		itemClickEvent();
	}

	private void itemClickEvent() {
		cityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String cityId = null;
				String cityName = null;
				if(position == 0){
					//当前定位城市
					finish();//暂时直接finish
				} else {
					cityId = list.get(position - 1).getCityid();
					cityName = list.get(position - 1).getCityname();
				}
				Intent intent = new Intent();
				intent.putExtra(XxKanFKeys.CITY_ID, cityId);
				intent.putExtra(XxKanFKeys.CITY_NAME, cityName);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/**
	 * 侧边栏初始化-监听事件
	 */
	private void initSideBar() {
		// TODO 初始化侧边栏
		sideBar = (SideBar) findViewById(R.id.ac_choose_city_sidbar);
		textDialog = (TextView) findViewById(R.id.ac_choose_city_dialog);
		sideBar.setCharacters(XxKanFConfigs.chs)
				.setTextSize(30)
				.setTouchedBgColor(Color.GRAY)
				.setDefaultTextColor(
						getResources().getColor(R.color.main_cinya));
		sideBar.setTextView(textDialog);

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO 右边轴滑动监听
//				textDialog.setText(s);
				// 设置listView显示的位置
				setSelection(s);
			}
		});
	}

	/**
	 * 滑动时：显示对应字母的城市
	 * 
	 * @param s
	 */
	protected void setSelection(String s) {
		// TODO 设置listView显示的位置
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				String ss = list.get(i).getCityname();
				int loc = ss.length() > 1 ? 2 : 1;
				if (TextUtils.equals(s, ss.substring(0, loc))) {
					cityListView.setSelection(i);
					break;
				}
			}
	}

	private void loadData() {
		// TODO 加载数据
		new LoadAsyncTask(getApplicationContext(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				// TODO 下载城市列表成功
				if (result != null) {
					list = (List<City>) result;
					List<City> tmp = new ArrayList<City>();
					City nowLabel = new City();
					nowLabel.setCityname("当前选择城市").setCategory(City.CATE_LABEL);
					tmp.add(nowLabel);
					for (int i = 0; i < list.size(); i++) {
						String citiId = list.get(i).getCityid();
						if (citiId != null && citiId.equals(curCityId)) {
							tmp.add(list.get(i));
							break;
						}
					}

					nowLabel = new City();
					nowLabel.setCityname("热门城市").setCategory(City.CATE_LABEL);
					tmp.add(nowLabel);
					int count = 0;
					for (int i = 0; i < list.size(); i++) {
						String name = list.get(i).getCityname();
						if (name.equals("北京") || name.equals("上海")
								|| name.equals("广州") || name.equals("深圳")
								|| name.equals("杭州")) {
							tmp.add(list.get(i));
							count++;
						}
						if (count == 5)
							break;
					}

					list.addAll(0, tmp);
					adapter = new CityListAdapter(getApplicationContext(), list);
					cityListView.setAdapter(adapter);
				}
			}
		}).execute(XxKanFUrls.CHOICE_CITY, LoadAsyncTask.LOAD_CITY_LIST);
	}

	public void onCancel(View v) {
		finish();
	}

}
