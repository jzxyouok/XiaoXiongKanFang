package com.beiing.xiaoxiongkanfang.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollBottomScrollView extends ScrollView {

	private ScrollBottomListener scrollBottomListener;

	public ScrollBottomScrollView(Context context) {
		super(context);
	}

	public ScrollBottomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollBottomScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (getScrollY() == getChildAt(getChildCount() - 1).getBottom()
				+ getPaddingBottom() - getHeight()) {
			scrollBottomListener.scrollBottom();
		}
	}

	/**
	 * ������ķ������scrollviewǶ��listview�Ĺ��ܺ����listview�����Ƕ�̬�ı�ģ�<br>
	 * ����һ�������б���棬�����Ƕ�̬���飬�����������б������ҳ���ȼ������飬<br>
	 * �ټ��������б��ȼ��������Ժ�scrollview���Զ�������listview��<br>
	 * ����������Ϣֻ��ʾһ���ֻ�����ȫ��ʾ���ˣ�����취��<br>
	 * ���Զ����scrollview����дscrollview�е����·����������䷵��ֵ��Ϊ0���ɣ�<br>
	 */
	@Override
	protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
		return 0;
	}

	public void setScrollBottomListener(
			ScrollBottomListener scrollBottomListener) {
		this.scrollBottomListener = scrollBottomListener;
	}

	public interface ScrollBottomListener {
		public void scrollBottom();
	}

}