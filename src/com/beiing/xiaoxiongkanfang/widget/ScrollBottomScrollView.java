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
	 * 用上面的方法完成scrollview嵌套listview的功能后，如果listview数据是动态改变的，<br>
	 * 比如一个留言列表界面，上面是动态详情，下面是留言列表，进入该页面先加载详情，<br>
	 * 再加载留言列表，等加载完留言后，scrollview会自动滚动到listview，<br>
	 * 导致详情信息只显示一部分或者完全显示不了，解决办法，<br>
	 * 在自定义的scrollview中重写scrollview中的如下方法，并将其返回值设为0即可：<br>
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