package com.beiing.xiaoxiongkanfang.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Beiing
 *
 */
public class SideBar extends View {

	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	/**
	 * ��ʾ���ַ���--Ĭ��
	 */
	private String[] characters = { "#", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	/**
	 * ѡ�б�ʶ
	 */
	private int choose = -1;

	/**
	 * ����
	 */
	private Paint paint;

	/**
	 * ѡ����ʾ��
	 */
	private TextView mTextDialog;

	/**
	 * �����С
	 */
	private int textSize = 20;

	/**
	 * Ĭ��������ɫ
	 */
	private int defaultTextColor = Color.BLACK;

	/**
	 * ѡ��������ɫ
	 */
	private int selectedTextColor = Color.YELLOW;

	/**
	 * ����ʱ������ɫ
	 */
	private int touchedBgColor = Color.LTGRAY;;

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SideBar(Context context) {
		this(context, null, 0);
	}

	/**
	 * ������ʾ�ַ���
	 * 
	 * @param chs
	 */
	public SideBar setCharacters(String[] chs) {
		if (chs != null && chs.length != 0)
			this.characters = chs;
		return this;
		
	}

	/**
	 * ������ʾ��
	 */
	public SideBar setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
		return this;
	}

	/**
	 * ����ѡ��ʱ��ʾ��������ɫ
	 * 
	 * @param color
	 */
	public SideBar setSelectedTextColor(int color) {
		if (color != 0)
			this.selectedTextColor = color;
		return this;
	}

	/**
	 * ����Ĭ����ʾ��������ɫ
	 * 
	 * @param color
	 */
	public SideBar setDefaultTextColor(int color) {
		if (color != 0)
			this.defaultTextColor = color;
		return this;
	}

	/**
	 * ���������С
	 * 
	 * @param textSize
	 */
	public SideBar setTextSize(int textSize) {
		if (textSize != 0)
			this.textSize = textSize;
		return this;
	}

	/**
	 * ���ô���ʱ����ɫ
	 * 
	 * @param color
	 */
	public SideBar setTouchedBgColor(int color) {
		if (color != 0)
			this.touchedBgColor = color;
		return this;
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	/**
	 * ��д�������
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ��ȡ����ı䱳����ɫ.
		int height = getHeight();// ��ȡ��Ӧ�߶�
		int width = getWidth(); // ��ȡ��Ӧ���
		int singleHeight = height / characters.length;// ��ȡÿһ����ĸ�ĸ߶�

		for (int i = 0; i < characters.length; i++) {
			if (!isInEditMode()) {
				paint.setColor(defaultTextColor);
			}
			
			paint.setTextSize(textSize);
			// ѡ�е�״̬
			if (i == choose) {
				paint.setColor(selectedTextColor);
				paint.setFakeBoldText(true);
			}
			// x��������м�-�ַ�����ȵ�һ��.
			float xPos = width / 2 - paint.measureText(characters[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(characters[i], xPos, yPos, paint);
			paint.reset();// ���û���
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// ���y����
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * characters.length);// ���y������ռ�ܸ߶ȵı���*characters����ĳ��Ⱦ͵��ڵ��b�еĸ���.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.TRANSPARENT);// ͸��
			choose = -1;
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundColor(touchedBgColor);// ѡ��ʱ���ñ���ɫ
			if (oldChoose != c) {
				if (c >= 0 && c < characters.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(characters[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(characters[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choose = c;
					invalidate();
				}
			}
			break;
		}
		return true;
	}

	/**
	 * ���⹫���ķ���
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * �ӿ�
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}