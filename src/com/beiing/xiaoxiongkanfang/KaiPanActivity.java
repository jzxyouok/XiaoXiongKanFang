package com.beiing.xiaoxiongkanfang;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.fragments.XinFangListFragment;

public class KaiPanActivity extends Activity {

	public static void startKaiPanActivity(Context context, String cityId){
		Intent intent = new Intent(context, KaiPanActivity.class);
		intent.putExtra(XxKanFKeys.CITY_ID, cityId);
		context.startActivity(intent);
	}
	
	FragmentManager fmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaipan);
		String cityId = getIntent().getStringExtra(XxKanFKeys.CITY_ID);
		fmManager = getFragmentManager();
		fmManager
				.beginTransaction()
				.add(R.id.ac_kp_fl,
						XinFangListFragment.newInStance(cityId,
								XxKanFUrls.NEWEST_HOUSE)).commit();
	}
	
	public void onBackClick(View v){
		finish();
	}

}







