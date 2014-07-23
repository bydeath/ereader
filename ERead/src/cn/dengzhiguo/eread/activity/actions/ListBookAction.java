package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.IBook;
import cn.dengzhiguo.eread.db.Book;
@EBean
public class ListBookAction implements Action {

	@Bean(BookImpl.class)
	IBook book;
	@Override
	public Intent execute(Context context, Intent intent, ActionCallback arg2) {
		intent.putExtra("result", book.getAll().toArray(new Book[0]));
		return intent;
	}

}
