package cn.dengzhiguo.eread.activity.actions;

import java.io.IOException;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
@EBean
public class PlayVoiceAction implements Action {
	
	@Override
	public Intent execute(Context context, Intent intent, ActionCallback callback) {
		Log.d("UI", "play:"+intent.getStringExtra("url"));
		MediaPlayer player=MediaPlayer.create(context, Uri.parse(intent.getStringExtra("url")));
		try {
			//player.prepare();
			player.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}

}
