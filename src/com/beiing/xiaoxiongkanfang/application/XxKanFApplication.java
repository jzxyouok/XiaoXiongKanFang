package com.beiing.xiaoxiongkanfang.application;


import java.util.LinkedList;
import java.util.Queue;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.cache.FileCache;

/**
 * ����ȫ�ֱ���
 * @author Administrator
 *
 */
public class XxKanFApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());// ��ʼ����ͼ
		FileCache.newInstance(getApplicationContext());
		BitmapCache.newInstance();
	}
	
}
