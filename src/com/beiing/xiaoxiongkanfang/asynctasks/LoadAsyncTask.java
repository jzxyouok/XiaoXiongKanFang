package com.beiing.xiaoxiongkanfang.asynctasks;

import java.io.InputStream;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.beiing.xiaoxiongkanfang.utils.HttpTools;
import com.beiing.xiaoxiongkanfang.utils.JsonUtil;
import com.beiing.xiaoxiongkanfang.utils.LogUtil;
import com.beiing.xiaoxiongkanfang.utils.StreamUtil;

public class LoadAsyncTask extends AsyncTask<Object, Void, Object> {

	private LoadListener loadListener;
	private Context context;

	/**
	 * ���س����б�
	 */
	public static final int LOAD_CITY_LIST = 0x100;

	/**
	 * ������Ѷ�б�
	 */
	public static final int LOAD_INFO_LIST = 0x101;

	/**
	 * ������Ѷ����
	 */
	public static final int LOAD_INFO_DETAIL = 0x102;

	/**
	 * ���ع����Ϣ
	 */
	public static final int LOAD_AD = 0x103;

	/**
	 * ����������Ϣ
	 */
	public static final int LOAD_INFO_COMMENT = 0x104;

	/**
	 * �����·��б�
	 */
	public static final int LOAD_XINFANG_LIST = 0x105;

	/**
	 * �·�����
	 */
	public static final int LOAD_XINFANG_DETAIL = 0x106;

	/**
	 * �·���������
	 */
	public static final int LOAD_XINFANG_DETAIL_COMMENT = 0x107;

	/**
	 * �����Ż�
	 */
	public static final int LOAD_DISCOUNT = 0x108;

	public LoadAsyncTask(Context context, LoadListener loadListener) {
		this.context = context;
		this.loadListener = loadListener;
	}

	// ��һ��������url���ڶ��������Ǽ��ص�����
	@Override
	protected Object doInBackground(Object... params) {
		Object info = null;
		String url = params[0].toString();
		int type = Integer.valueOf(params[1].toString());
		if (url != null) {
			try {
				byte[] buffer = null;
				String jsonStr = null;
				if(type != LOAD_CITY_LIST){
					buffer = HttpTools.doGet(url);
					jsonStr = new String(buffer, "utf-8");
				}
				// LogUtil.i("--", "jsonStr========" + jsonStr);
				switch (type) {
				case LOAD_CITY_LIST:
					InputStream is = context.getResources().getAssets()
							.open("city_list.txt");
					buffer = StreamUtil.readStream(is);
					jsonStr = new String(buffer, "gbk");
					info = JsonUtil.parseCity(jsonStr);
					break;

				case LOAD_INFO_LIST:
					info = JsonUtil.parseInfo(jsonStr);
					break;

				case LOAD_INFO_DETAIL:
					info = JsonUtil.parseInfoDetail(jsonStr);
					break;

				case LOAD_AD:
					info = JsonUtil.parseAds(jsonStr);
					break;

				case LOAD_INFO_COMMENT:
					info = JsonUtil.parseInfoComment(jsonStr);
					break;

				case LOAD_XINFANG_LIST:
					info = JsonUtil.parseXfList(jsonStr);
					break;

				case LOAD_XINFANG_DETAIL:
					info = JsonUtil.parseXfDetail(jsonStr);

				case LOAD_XINFANG_DETAIL_COMMENT:
					info = JsonUtil.parseXfDetailComments(jsonStr);

				case LOAD_DISCOUNT:
					info = JsonUtil.parseDiscounts(jsonStr);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if(result != null)
			loadListener.loadSeccess(result);
	}

	public interface LoadListener {
		public void loadSeccess(Object result);
	}

}
