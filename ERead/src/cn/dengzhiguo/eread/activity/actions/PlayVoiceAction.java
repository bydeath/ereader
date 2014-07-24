package cn.dengzhiguo.eread.activity.actions;

import java.io.IOException;

import mobi.zhangying.cyclops.Action;
import mobi.zhangying.cyclops.ActionCallback;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
@EBean
public class PlayVoiceAction implements Action {
	@Override
	public Intent execute(Context context, Intent intent, ActionCallback callback) {
		
		MediaPlayer player=MediaPlayer.create(context, Uri.parse(intent.getStringExtra("url")));
		AudioManager audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE); 
		Log.d("UI", "volumn:"+audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC));
		
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
