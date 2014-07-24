package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import cn.dengzhiguo.eread.bo.INewword;
import cn.dengzhiguo.eread.bo.NewwordImpl;
import cn.dengzhiguo.eread.db.Newword;
import cn.dengzhiguo.eread.net.DictNetHandler;
import cn.dengzhiguo.eread.net.IDictNetHandler;
@EBean
public class TranslateAction implements Action {

	@Bean(DictNetHandler.class)
	IDictNetHandler dictHandler;
	@Bean(NewwordImpl.class)
	INewword newwordBo;
	@Override
	public Intent execute(Context context, Intent intent, ActionCallback callback) {
		String word=intent.getStringExtra("word");
		Newword newword=newwordBo.findNewword(word);
		if(newword==null){
			newword=new Newword(dictHandler.translate(word));
			newwordBo.addNewword(newword);
		}
		intent.putExtra("result", newword);
		return intent;
	}
	
}
