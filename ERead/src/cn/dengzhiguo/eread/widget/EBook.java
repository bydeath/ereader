package cn.dengzhiguo.eread.widget;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class EBook extends View {

	public EBook(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// private String text;
	private float paddingTop = 20;
	private float paddingLeft = 20;
	private float paddingRight = 20;
	private float paddingBottom = 20;
	private float linePadding = 20;
	private float lineMargin = 1.5f;
	private List<List<String>> pages = new ArrayList<List<String>>();
	private InputStream inputStream;
	List<String> page = new ArrayList<String>();
	StringBuffer line = new StringBuffer();
	float lineHeight = 0;
	int curPage = -1;
	float mMove = 0;
	float lineWidth = 0;
	float lastBlankLineWidth = 0;
	int linesPerPage = 0;
	private OnTextSelectListener textSelectListener;
	private OnBookSetOver onBookSetOver;
	private OnPage onPage;
	public OnTextSelectListener getTextSelectListener() {
		return textSelectListener;
	}
	private int lastRead;

	public int getLastRead() {
		return lastRead;
	}

	public void setLastRead(int lastRead) {
		this.lastRead = lastRead;
	}

	public void setTextSelectListener(OnTextSelectListener textSelectListener) {
		this.textSelectListener = textSelectListener;
	}
	int totalRead=0;
	private void newLine(int lastBlank) {
		if (line.length() == 0)
			return;
		
		page.add(line.substring(0, lastBlank == 0 ? line.length() : lastBlank));
		totalRead+= lastBlank == 0 ? line.length():lastBlank;
		
		if(totalRead>lastRead&&curPage<0){
			curPage=this.pages.size();
		}
		line = line.delete(0, lastBlank == 0 ? line.length() : lastBlank);
		lastBlank = 0;
		if (page.size() >= linesPerPage) {
			newPage();
		}
	}

	private void newPage() {
		pages.add(page);
		page = new ArrayList<String>(linesPerPage);
	}

	private void getLineHeight(Paint paint) {
		Rect bounds = new Rect();
		paint.getTextBounds("Ay", 0, 2, bounds);
		lineHeight = bounds.height() * lineMargin;
		linesPerPage = (int) (this.getHeight() / lineHeight);
	}

	long start = 0;
	float charWidth = 0;
	int minLenOnLine = 0;
	int mesureTime = 0;
	float[] charWidths = new float[127];

	public void readBook(InputStream input) throws Exception {
		start = System.currentTimeMillis();
		byte[] buffer = new byte[10240];
		int r = 0;
		Paint paint = this.getTextPaint();
		for (int i = 10; i <= 0x7a; i++) {
			charWidths[i] = paint.measureText(String.valueOf((char) i));
		}
		getLineHeight(paint);
		int lastBlank = 0;
		float padding = paddingLeft + paddingRight;
		lineWidth = padding;
		while ((r = input.read(buffer)) > 0) {
			for (int i = 0; i < r; i++) {
				
				char c = (char) buffer[i];
				line.append(c);
				lineWidth += getCharWidth(c);
				if (c == '\n') {
					if (lineWidth > this.getWidth()) {
						newLine(lastBlank);
						lineWidth = lineWidth - lastBlankLineWidth + padding;
						lastBlankLineWidth = padding;
					} else {
						newLine(0);
						lineWidth = padding;
						lastBlankLineWidth = padding;
					}
				} else {
					
					if (c == ' ') {
						if (lineWidth > this.getWidth()) {

							newLine(lastBlank);
							lineWidth = lineWidth - lastBlankLineWidth
									+ padding;
							lastBlankLineWidth = padding;

						} else {
							lastBlank = line.length();
							lastBlankLineWidth = lineWidth;
						}

					}
				}

			}
		}

		newLine(0);
		newPage();
		if(onBookSetOver!=null)
			onBookSetOver.over(pages.size(), curPage);
		Log.d("UI", String.format("total:%d pages:%d",
				(System.currentTimeMillis() - start), pages.size()));
	}

	private float mesureLine(String txt, Paint paint) {
		if (txt.length() <= minLenOnLine)
			return 0;
		float w = paddingLeft + getTextWidth(txt, paint) + paddingRight;
		mesureTime++;
		return w;
	}

	private float getTextWidth(String txt, Paint paint) {
		float w = 0;
		for (int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if (c < 127) {
				w += charWidths[(int) c];
			}
		}
		return w;
		// return paint.measureText(txt);
	}

	public EBook(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public EBook(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	Handler handler=new Handler();
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBack(canvas);
		paintText(canvas);
		if (this.inputStream != null && pages == null || pages.size() == 0) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						readBook(inputStream);
						inputStream.close();
						inputStream = null;
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								invalidate();
							}
						});
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
			

		}
	}

	private void paintText(Canvas canvas) {
		if (pages == null || pages.size() == 0 ||curPage<0)
			return;
		paintText(canvas, mMove + paddingLeft, pages.get(curPage));
		if (mMove < 0) {
			if (curPage < pages.size() - 1)
				paintText(canvas, this.getWidth() + mMove + paddingLeft,
						pages.get(curPage + 1));
		}
		if (mMove > 0) {
			if (curPage >= 1)
				paintText(canvas, mMove - this.getWidth() + paddingLeft,
						pages.get(curPage - 1));
		}
	}

	private void drawBack(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xfffff6D0);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
	}

	private Paint getTextPaint() {
		Typeface font = Typeface.SANS_SERIF;
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(18 * this.getContext().getResources()
				.getDisplayMetrics().density);
		paint.setTypeface(font);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		return paint;
	}

	private void paintText(Canvas canvas, float left, List<String> line) {
		float height = lineHeight;
		Paint textPaint = this.getTextPaint();
		for (int i = 0; i < line.size(); i++) {
			if (i == selectedLine) {
				Paint hilightPaint = new Paint();
				hilightPaint.setColor(0xffFAE15A);
				canvas.drawRect(new Rect(
						(int) (selectedStartX + left - paddingLeft),
						(int) (height - lineHeight + linePadding),
						(int) (selectedEndx + left - paddingLeft),
						(int) (height + linePadding)), hilightPaint);
			}
			canvas.drawText(line.get(i), left, height, textPaint);

			height += lineHeight;
		}

	}

	private GestureDetector gestureDetector = new GestureDetector(
			this.getContext(), new GestureDetector.OnGestureListener() {

				@Override
				public boolean onSingleTapUp(MotionEvent event) {
					return false;
				}

				@Override
				public void onShowPress(MotionEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float x, float y) {
					if (e1 == null || e2 == null)
						return false;

					mMove = (int) (e2.getAxisValue(MotionEvent.AXIS_X) - e1
							.getAxisValue(MotionEvent.AXIS_X));
					EBook.this.invalidate();
					return false;
				}

				@Override
				public void onLongPress(MotionEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float x,
						float y) {
					return false;
				}

				@Override
				public boolean onDown(MotionEvent arg0) {
					// TODO Auto-generated method stub
					return false;
				}
			});

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (mMove != 0) {
				if (mMove < 0) {
					if (curPage == pages.size() - 1
							|| Math.abs(mMove) < getWidth() / 4)
						animationMove(mMove, 0);
					else
						animationMove(mMove, 0 - this.getWidth());
				} else {
					if (curPage == 0 || Math.abs(mMove) < getWidth() / 4)
						animationMove(mMove, 0);
					else
						animationMove(mMove, this.getWidth());
				}
			} else {
				click(event);
				return false;
			}

		}
		return true;
	}

	private int selectedLine = 0;
	private float selectedStartX = 0;
	private float selectedEndx = 0;

	private void click(MotionEvent event) {

		int line = 0;
		int height = 0;
		for (int i = 0; i < pages.get(curPage).size(); i++) {
			height += lineHeight;
			if (height > event.getY()) {
				line = i;
				break;
			}
		}
		String lineWords = pages.get(curPage).get(line);
		int stringEnd = lineWords.length();
		int stringStart = 0;
		float width = paddingLeft;
		selectedStartX = width;
		for (int i = 0; i < lineWords.length(); i++) {
			char c = lineWords.charAt(i);
			width += getCharWidth(c);
			if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
				if (width >= event.getX()) {
					stringEnd = i;
					selectedEndx = width;
					break;
				} else {
					stringStart = i + 1;
					selectedStartX = width;
				}
			}
		}

		// //("UI", "start:"+stringStart);
		if (width > event.getX()) {
			selectedLine = line;
			// Log.d("UI", "click:" + lineWords.substring(stringStart,
			// stringEnd));
			this.invalidate();
			if (textSelectListener != null) {
				textSelectListener.onSelected(lineWords.substring(stringStart,
						stringEnd));
			}
		}
	}

	private float getCharWidth(char c) {
		if (c <= 127) {
			return charWidths[(int) c];
		} else
			return 0;
	}

	private void animationMove(final float from, final float to) {
		ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		final float moveStart = from;
		animation.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// ("UI", "onAnimationStart");

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// Log.d("UI", "onAnimationRepeat");

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// Log.d("UI", "onAnimationEnd:" + moveStart + ",width:"
				// + getWidth());
				if (mMove < 0 && Math.abs(moveStart) > getWidth() / 4) {
					// next
					if (curPage < (pages.size() - 1)) {
						curPage++;
						selectedLine = -1;
					}
				}
				if (mMove > 0 && moveStart > getWidth() / 4) {

					if (curPage > 0) {
						curPage--;
						selectedLine = -1;
					}
				}
				int lastRead=0;
				for(int i=0;i<curPage;i++){
					for(String line:pages.get(i)){
						lastRead+=line.length();
					}
				}
				lastRead++;
				if(onPage!=null)
					onPage.pageChange(curPage, pages.size(),lastRead);
				mMove = 0;

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// Log.d("UI", "onAnimationCancel");

			}

		});
		animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator va) {

				if (moveStart > 0) {
					mMove = moveStart + ((float) va.getAnimatedValue())
							* (to - from);
				} else {
					mMove = moveStart + ((float) va.getAnimatedValue())
							* (to - from);
				}
				// Log.d("UI", "onAnimationUpdate:"+mMove);
				invalidate();
			}
		});
		animation.setDuration(500);
		animation.start();
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OnBookSetOver getOnBookSetOver() {
		return onBookSetOver;
	}

	public void setOnBookSetOver(OnBookSetOver onBookSetOver) {
		this.onBookSetOver = onBookSetOver;
	}

	public OnPage getOnPage() {
		return onPage;
	}

	public void setOnPage(OnPage onPage) {
		this.onPage = onPage;
	}

}
