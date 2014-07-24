package cn.dengzhiguo.eread.activity;

import java.io.FileInputStream;
import java.io.InputStream;

import mobi.zhangying.cyclops.MVCAdapter;
import mobi.zhangying.cyclops.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.PlayVoiceAction_;
import cn.dengzhiguo.eread.activity.actions.SaveBookAction_;
import cn.dengzhiguo.eread.activity.actions.TranslateAction_;
import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.FileUtil;
import cn.dengzhiguo.eread.bo.IBook;
import cn.dengzhiguo.eread.bo.IFile;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.db.Newword;
import cn.dengzhiguo.eread.widget.EBook;
import cn.dengzhiguo.eread.widget.OnBookSetOver;
import cn.dengzhiguo.eread.widget.OnPage;
import cn.dengzhiguo.eread.widget.OnTextSelectListener;

import com.umeng.analytics.MobclickAgent;
@EActivity(R.layout.activity_book)
public class BookActivity extends Activity {

	@ViewById(R.id.ebook)
	EBook ebook;
	@ViewById(R.id.txtWord)
	TextView txtWord;
	@ViewById(R.id.txtPhen)
	TextView txtPhen;
	@ViewById(R.id.txtPham)
	TextView txtPham;
	@ViewById(R.id.txtParts)
	TextView txtParts;
	@ViewById(R.id.imgVoiceEn)
	ImageView imgPlayen;
	@ViewById(R.id.imgVoiceAm)
	ImageView imgPlayam;
	@ViewById(R.id.layoutTranslate)
	RelativeLayout layoutTrans;
	@ViewById(R.id.pgbWaiting)
	ProgressBar pgbWaiting;
	@ViewById(R.id.skbPage)
	SeekBar skbPage;
	MVCAdapter mvc=null;
	Book book=null;
	@Bean(FileUtil.class)
	IFile fileUtil;
	@Bean(BookImpl.class)
	IBook bookBo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		book=(Book)this.getIntent().getSerializableExtra("book");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mvc=new MVCAdapter(this);
		mvc.register(this);
	}
	@AfterViews
	public void afterView(){
		
		try {
			ebook.setFontSize(18);
			ebook.setBackcolor(0xfffff6D0);
			InputStream input=new FileInputStream(book.getFile());
			ebook.setLastRead(book.getLastread());
			ebook.setInputStream(input);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ebook.setTextSelectListener(new OnTextSelectListener() {
			
			@Override
			public void onSelected(String txt) {
				Log.d("UI", "select:"+txt);
				mvc.invokeAction(TranslateAction_.class, null, new Intent().putExtra("word", txt.toLowerCase()), 
						null, false,0);
				showTranslate(txt.toLowerCase());
			}
		});
		ebook.setOnBookSetOver(new OnBookSetOver() {
			
			@Override
			public void over(int page, int curPage) {
				book.setLastreadpage(curPage+1);
				book.setPage(page);
				mvc.invokeAction(SaveBookAction_.class, null, new Intent().putExtra("book", book), 
						null, false,0);
				skbPage.setMax(page);
				skbPage.setProgress(curPage);
				
			}
		});
		ebook.setOnPage(new OnPage() {
			
			@Override
			public void pageChange(int curPage, int page, int lastRead) {
				book.setLastreadpage(curPage+1);
				book.setPage(page);
				book.setLastread(lastRead);
				mvc.invokeAction(SaveBookAction_.class, null, new Intent().putExtra("book", book), 
						null, false,0);
				
			}
		});
		skbPage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				ebook.setScrolling(false);
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				ebook.setScrolling(true);
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				ebook.pageto(progress);
				
			}
		});
	}
	@Click(R.id.layoutTranslate)
	public void bookClick(){
		layoutTrans.setVisibility(View.GONE);
	}
	
	private void showTranslate(String word){
		layoutTrans.setVisibility(View.VISIBLE);
		txtWord.setText(word);
		txtPhen.setText("");
		txtPham.setText("");
		txtParts.setText("");
		pgbWaiting.setVisibility(View.VISIBLE);

	}
	
	@Response(action=TranslateAction_.class)
	public void getTranslationResult(Context context,Intent intent){
		Newword result=(Newword)intent.getSerializableExtra("result");
		pgbWaiting.setVisibility(View.GONE);
		if(result!=null)
		{
			if(result.getEn()!=null)
			{
				txtPhen.setText(String.format("英：[%s]", result.getEn()));
			}
			if(result.getAm()!=null)
			{
				txtPham.setText(String.format("美：[%s]", result.getAm()));
			}
			mNewword=result;
			txtParts.setText(result.getParts());
			
		}else{
			Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
		}
		
	}
	private Newword mNewword;
	@Click(R.id.imgVoiceEn)
	public void clickVoiceEn(){
		String voice=(mNewword.getVoiceEn()==null || "".equals(mNewword.getVoiceEn()))?
				mNewword.getVoiceTts():mNewword.getVoiceEn();
		this.playvoice(voice);
	}
	private void playvoice(String url){
		if(url!=null)
			mvc.invokeAction(PlayVoiceAction_.class, null, new Intent().putExtra("url",url), null, false,1);
		else
			Toast.makeText(this, "没有语音", Toast.LENGTH_SHORT).show();

	}
	@Click(R.id.imgVoiceAm)
	public void clickVoiceAm(){
		String voice=(mNewword.getVoiceAm()==null || "".equals(mNewword.getVoiceAm()))?
				mNewword.getVoiceTts():mNewword.getVoiceAm();
		this.playvoice(voice);
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);  
        int curVolumn = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);  
        int maxVolumn= audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
		{
	
		       audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn-maxVolumn/10, AudioManager.FLAG_PLAY_SOUND);
		       
		       return false;
		}
		if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)
		{

		       audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC,  curVolumn+maxVolumn/10, AudioManager.FLAG_PLAY_SOUND);
		       
		       return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
