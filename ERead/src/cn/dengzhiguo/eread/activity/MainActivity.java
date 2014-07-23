package cn.dengzhiguo.eread.activity;

import mobi.zhangying.cyclops.MVCAdapter;
import mobi.zhangying.cyclops.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
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
import cn.dengzhiguo.eread.activity.actions.ListBookAction_;
import cn.dengzhiguo.eread.adapter.BookListAdapter;
import cn.dengzhiguo.eread.db.Book;

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
	@Response(action=ListBookAction_.class)
	public void searchResult(Context context,Intent intent){
		Book[] books=(Book[])intent.getSerializableExtra("result");
		if(books!=null){
			Log.d("UI", "books:"+books.length);
		}
		bookListAdpater.setBooks(books);
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		mvc.invokeAction(ListBookAction_.class, null, new Intent(), null, false,0);
	}
	
}
