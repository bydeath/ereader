package cn.dengzhiguo.eread.activity;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.PlayVoiceAction_;
import cn.dengzhiguo.eread.activity.actions.SaveBookAction_;
import cn.dengzhiguo.eread.activity.actions.TranslateAction_;
import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.FileUtil;
import cn.dengzhiguo.eread.bo.IBook;
import cn.dengzhiguo.eread.bo.IFile;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.modal.Part;
import cn.dengzhiguo.eread.modal.Symbol;
import cn.dengzhiguo.eread.modal.TranslateInfo;
import cn.dengzhiguo.eread.widget.EBook;
import cn.dengzhiguo.eread.widget.OnBookSetOver;
import cn.dengzhiguo.eread.widget.OnPage;
import cn.dengzhiguo.eread.widget.OnTextSelectListener;
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
		long start=System.currentTimeMillis();
		try {
			InputStream input=new FileInputStream(book.getFile());
			ebook.setLastRead(book.getLastread());
			ebook.setInputStream(input);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ebook.setTextSelectListener(new OnTextSelectListener() {
			
			@Override
			public void onSelected(String txt) {
				mvc.invokeAction(TranslateAction_.class, null, new Intent().putExtra("word", txt.toLowerCase()), 
						null, false,0);
			}
		});
		ebook.setOnBookSetOver(new OnBookSetOver() {
			
			@Override
			public void over(int page, int curPage) {
				book.setLastreadpage(curPage+1);
				book.setPage(page);
				mvc.invokeAction(SaveBookAction_.class, null, new Intent().putExtra("book", book), 
						null, false,0);
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
	}
	@Click(R.id.layoutTranslate)
	public void bookClick(){
		layoutTrans.setVisibility(View.GONE);
	}
	@Response(action=TranslateAction_.class)
	public void getTranslationResult(Context context,Intent intent){
		TranslateInfo result=(TranslateInfo)intent.getSerializableExtra("result");
		StringBuffer rlt=new StringBuffer();
		layoutTrans.setVisibility(View.VISIBLE);
		txtWord.setText(intent.getStringExtra("word"));
		rlt.append(intent.getStringExtra("word"));
		if(result!=null)
		{
			txtPhen.setText("");
			txtPham.setText("");
			txtParts.setText("");
			if(result.getSymbols()!=null){
				showVoice(result.getSymbols());
				showParts(result.getSymbols());
			}
			
		}
		Log.d("UI", rlt.toString());
	}
	private String voiceEn;
	private String voiceAm;
	private String voiceTts;
	@Click(R.id.imgVoiceEn)
	public void clickVoiceEn(){
		mvc.invokeAction(PlayVoiceAction_.class, null, new Intent().putExtra("url",( voiceEn==null||voiceEn.equals(""))?voiceTts:voiceEn), null, false,1);
	}
	@Click(R.id.imgVoiceAm)
	public void clickVoiceAm(){
		mvc.invokeAction(PlayVoiceAction_.class, null, new Intent().putExtra("url", ( voiceAm==null||voiceAm.equals(""))?voiceTts:voiceAm), null, false,1);
	}
	private void showVoice(List<Symbol> symbols){
		Symbol symbol=symbols.get(0);
		txtPhen.setText(String.format("英：[%s]", symbol.getPh_en()));
		txtPham.setText(String.format("美：[%s]", symbol.getPh_am()));
		voiceEn=symbol.getPh_en_mp3();
		voiceAm=symbol.getPh_am_mp3();
		voiceTts=symbol.getPh_tts_mp3();
		
	}
	private void showParts(List<Symbol> symbols){
		StringBuffer rlt=new StringBuffer();
		for(Symbol symbol:symbols){
			for(Part part:symbol.getParts()){
				rlt.append(part.getPart());
				for(String mean:part.getMeans())
				{
					rlt.append(mean);
					rlt.append(";");
				}
				rlt.append("\n");
			}
		}
		txtParts.setText(rlt.toString());
	}
	
}
