package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import cn.dengzhiguo.eread.modal.TranslateInfo;
import cn.dengzhiguo.eread.net.DictNetHandler;
import cn.dengzhiguo.eread.net.IDictNetHandler;
@EBean
public class TranslateAction implements Action {

	@Bean(DictNetHandler.class)
	IDictNetHandler dictHandler;
	@Override
	public Intent execute(Context context, Intent intent, ActionCallback callback) {
		String word=intent.getStringExtra("word");
		TranslateInfo rlt=dictHandler.translate(word);
		intent.putExtra("result", rlt);
		return intent;
	}

}
