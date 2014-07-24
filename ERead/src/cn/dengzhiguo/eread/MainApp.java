package cn.dengzhiguo.eread;

import mobi.zhangying.cyclops.MVCAdapter;
import mobi.zhangying.cyclops.MVCController;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import cn.dengzhiguo.eread.activity.actions.InitAction_;

public class MainApp extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MVCController.Bind(this);
		MVCAdapter mvc=new MVCAdapter(this);
		mvc.register(this);
		mvc.invokeAction(InitAction_.class, null, null, null, true,-1);
            
	}

}
