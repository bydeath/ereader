package cn.dengzhiguo.eread.activity;

import mobi.zhangying.cyclops.MVCAdapter;
import mobi.zhangying.cyclops.Response;
import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.InitAction_;
import cn.dengzhiguo.eread.activity.actions.ListBookAction_;
import cn.dengzhiguo.eread.adapter.BookListAdapter;
import cn.dengzhiguo.eread.db.Book;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	MVCAdapter mvc;
	@ViewById(R.id.listBook)
	ListView listBook;
	@Bean
	BookListAdapter bookListAdpater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		mvc=new MVCAdapter(this);
		mvc.register(this);
		AdManager.getInstance( this).init("b8be4f17a6d4aecf", "8709a3edf605a02e", false);
		OffersManager.getInstance(this).onAppLaunch();
		
	}
	@AfterViews
	public void init(){
		listBook.setAdapter(bookListAdpater);
		mvc.invokeAction(ListBookAction_.class, null, new Intent(), null, false,0);
	}
	@ItemClick(R.id.listBook)
	public void openBook(Object obj){
		Book book=(Book)obj;
		Intent intent=new Intent(this,BookActivity_.class);
		intent.putExtra("book", book);
		this.startActivity(intent);
	}
	@Click(R.id.btnShare)
	public void share(){
		send("推荐个英语阅读器，可以在阅读时点击显示翻译并加入生词本，安卓下载地址： http://dengzg.b0.upaiyun.com/eread.apk");
	}
	@Click(R.id.btnSupport)
	public void doSupport(){
		OffersManager.getInstance(this).showOffersWall();
	}
	private void send(String content){
		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		startActivity(Intent.createChooser(intent, "分享到")); 
	}
	@Response(action=ListBookAction_.class)
	public void searchResult(Context context,Intent intent){
		Book[] books=(Book[])intent.getSerializableExtra("result");
		if(books!=null){
			Log.d("UI", "books:"+books.length);
		}
		bookListAdpater.setBooks(books);
	}
	@Response(action=InitAction_.class)
	public void bookUpdate(Context context,Intent intent){
		mvc.invokeAction(ListBookAction_.class, null, new Intent(), null, false,0);
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		MobclickAgent.onResume(this);
		mvc.invokeAction(ListBookAction_.class, null, new Intent(), null, false,0);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		
	}
	
}
