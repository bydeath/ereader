package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.IBook;
@EBean
public class InitAction implements Action {

	@Bean(BookImpl.class)
	IBook book;
	@Override
	public Intent execute(Context arg0, Intent intent, ActionCallback arg2) {
		try {
			if(book.initBooks()){
				return intent;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
