package com.beiing.xiaoxiongkanfang.widget;

import com.beiing.xiaoxiongkanfang.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * @ClassName SpinnerButton
 * @Description TODO ��android4.0 Spinner����Ч��
 */
public class SpinnerButton extends Button {

	private Context mContext;
	/** ����PopupWindow */
	private UMSpinnerDropDownItems mPopupWindow;
	/** ���������ļ�ResourceId */
	private int mResId;
	/** ���������ļ����������� */
	private ViewCreatedListener mViewCreatedListener;

	public SpinnerButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initButton(context);
	}

	public SpinnerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initButton(context);
	}

	public SpinnerButton(Context context, final int resourceId,
			ViewCreatedListener mViewCreatedListener) {
		super(context);
		setResIdAndViewCreatedListener(resourceId, mViewCreatedListener);
		initButton(context);
	}

	private void initButton(Context context) {
		this.mContext = context;
		// UMSpinnerButton�����¼�
		setOnClickListener(new UMSpinnerButtonOnClickListener());
	}

	public PopupWindow getPopupWindow() {
		return mPopupWindow;
	}

	public void setPopupWindow(UMSpinnerDropDownItems mPopupWindow) {
		this.mPopupWindow = mPopupWindow;
	}

	public int getResId() {
		return mResId;
	}

	/**
	 * @Description: TODO ������������
	 */
	public void dismiss() {
		mPopupWindow.dismiss();
		WindowManager.LayoutParams params = ((Activity) mContext).getWindow()
				.getAttributes();
		params.alpha = 1f;
		((Activity) mContext).getWindow().setAttributes(params);
	}

	/**
	 * @Description: TODO �������������ļ�,�������ļ�����������
	 * @param @param mResId ���������ļ�ID
	 * @param @param mViewCreatedListener �����ļ�����������
	 */
	public void setResIdAndViewCreatedListener(int mResId,
			ViewCreatedListener mViewCreatedListener) {
		this.mViewCreatedListener = mViewCreatedListener;
		// ���������ļ�id
		this.mResId = mResId;
		// ��ʼ��PopupWindow
		mPopupWindow = new UMSpinnerDropDownItems(mContext);
	}
	
	
	
	

	/**
	 * UMSpinnerButton�ĵ���¼�
	 */
	class UMSpinnerButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (mPopupWindow != null) {
				if (!mPopupWindow.isShowing()) {
					// ����PopupWindow����,�˳���ʽ
					mPopupWindow.setAnimationStyle(R.style.Animation_dropdown);
					// ����popupWindow����x���λ��
					int lx = (SpinnerButton.this.getWidth()
							- mPopupWindow.getmViewWidth() - 7) / 2;
					// showPopupWindow
					mPopupWindow.showAsDropDown(SpinnerButton.this, lx, -5);

//					WindowManager.LayoutParams params = ((Activity) mContext)
//							.getWindow().getAttributes();
//					params.alpha = 0.7f;
//					((Activity) mContext).getWindow().setAttributes(params);

				}
			}
		}
	}

	/**
	 * @ClassName UMSpinnerDropDownItems
	 * @Description TODO ��������
	 */
	public class UMSpinnerDropDownItems extends PopupWindow {

		private Context mContext;
		/** ������ͼ�Ŀ�� */
		private int mViewWidth;
		/** ������ͼ�ĸ߶� */
		private int mViewHeight;

		public UMSpinnerDropDownItems(Context context) {
			super(context);
			this.mContext = context;
			setBackgroundColor(Color.WHITE);
			loadViews();
		}

		/**
		 * @Description: TODO ���ز����ļ�
		 * @param
		 * @return void
		 * @throws
		 */
		private void loadViews() {
			// ���ּ��������ز����ļ�
			LayoutInflater inflater = LayoutInflater.from(mContext);
			final View v = inflater.inflate(mResId, null);
			// ����view���
			onMeasured(v);

			// ��������
			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setContentView(v);
			setFocusable(true);

			// ���ò��ִ������������Ա���ʵ�������ֿؼ�����
			if (mViewCreatedListener != null) {
				mViewCreatedListener.onViewCreated(v);
			}
		}

		/**
		 * @Description: TODO ����View����
		 * @param @param v
		 */
		private void onMeasured(View v) {
			int w = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			v.measure(w, h);
			mViewWidth = v.getMeasuredWidth();
			mViewHeight = v.getMeasuredHeight();
		}

		public int getmViewWidth() {
			return mViewWidth;
		}

		public void setmViewWidth(int mViewWidth) {
			this.mViewWidth = mViewWidth;
		}

		public int getmViewHeight() {
			return mViewHeight;
		}

		public void setmViewHeight(int mViewHeight) {
			this.mViewHeight = mViewHeight;
		}

	}

	/**
	 * @ClassName ViewCreatedListener
	 * @Description TODO ���ִ�����������ʵ�������ֿؼ�����
	 */
	public interface ViewCreatedListener {
		void onViewCreated(View v);
	}
}
