package cn.dengzhiguo.eread.adapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.view.BookView;
import cn.dengzhiguo.eread.view.BookView_;
@EBean
public class BookListAdapter extends BaseAdapter {

	private Book[] books;
	@RootContext
	Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return books!=null?books.length:0;
	}

	@Override
	public Object getItem(int p) {
		// TODO Auto-generated method stub
		return books[p];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		BookView view=(BookView)convertView;
		if(view==null){
			view=BookView_.build(context);
		}
		view.bind(books[position]);
		return view;
	}

	public Book[] getBooks() {
		return books;
	}

	public void setBooks(Book[] books) {
		this.books = books;
		this.notifyDataSetChanged();
	}
	

}
