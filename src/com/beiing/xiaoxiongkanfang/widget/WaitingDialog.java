package com.beiing.xiaoxiongkanfang.widget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;

/**
 * �Զ�����ضԻ���
 * 
 * @author Administrator
 * 
 */
public class WaitingDialog {

	private Dialog dialog;
	private Window window;
	private Context context;
	private ImageView imageView;
	public WaitingDialog(Context context) {
		this.context = context;
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View dialogView = LayoutInflater.from(context).inflate(R.layout.waiting_dialg, null);
		imageView = (ImageView) dialogView.findViewById(R.id.waiting_iv);
		dialog.setContentView(dialogView);
		window = dialog.getWindow();
		//����ȫ��
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = context.getResources().getDisplayMetrics().widthPixels;
		params.height = context.getResources().getDisplayMetrics().heightPixels;
		window.setAttributes(params);
		AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
		anim.start();
	}
	
	public void setCanceble(boolean isCanceble){
		dialog.setCancelable(isCanceble);
	}
	
	public void setAlpha(float alpha){
		WindowManager.LayoutParams lp = window.getAttributes();
		// ����͸����Ϊalpha
		lp.alpha = alpha;
		window.setAttributes(lp);
	}
	
	public void show(){
		if(dialog != null && !dialog.isShowing() && context != null)
			dialog.show();
	}
	
	public void dismiss(){
		if(dialog.isShowing())
			dialog.dismiss();
	}
	
}



