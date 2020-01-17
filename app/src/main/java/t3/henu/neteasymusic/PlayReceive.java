package t3.henu.neteasymusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import t3.henu.left_library.GYB_solve.Activities.NetWork.MusicNetWork;
import t3.henu.left_library.GYB_solve.Activities.SongInfo;

public class PlayReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bun=intent.getBundleExtra("Bunde");
        SongInfo songInfo= (SongInfo) bun.getParcelable("songinfo");
        if(MainActivity.text_singer!=null){
            MainActivity.text_singer.setText(songInfo.getSinger());
        }
        if(MainActivity.text_song!=null){
            MainActivity.text_song.setText(songInfo.getSong());
        }


        if (t3.henu.left_library.GYB_solve.MainActivity.t_singer != null) {
            t3.henu.left_library.GYB_solve.MainActivity.t_singer.setText(songInfo.getSinger());
            t3.henu.left_library.GYB_solve.MainActivity.t_songname.setText(songInfo.getSong());
        }
        boolean status= (boolean) intent.getExtras().get("play_status");
        RequestQueue mQueue = MusicNetWork.getmRequestqueue(context);
        //Toast.makeText(context,(t3.henu.left_library.GYB_solve.MainActivity1.imageView==null)+"::"+songInfo.getPucUrl(),Toast.LENGTH_SHORT).show();
        Bitmap bitmap = SongInfo.getBitMap(context, (int) songInfo.getAlbumId());
        if (bitmap != null) {
            MainActivity.play_imageView.setImageBitmap(bitmap);
        } else {

            ImageRequest imageRequest = new ImageRequest(songInfo.getPucUrl(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {


                    MainActivity.play_imageView.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MainActivity.play_imageView.setImageResource(R.drawable.icon_touxiang);
                }
            });
            mQueue.add(imageRequest);
        }
        if(MainActivity.btn_play!=null){
            if(status==false){
                MainActivity.btn_play.setImageResource(t3.henu.left_library.R.drawable.icon_play1);
            }else{
                MainActivity.btn_play.setImageResource(t3.henu.left_library.R.drawable.icon_pause);
            }
        }

    }
}
