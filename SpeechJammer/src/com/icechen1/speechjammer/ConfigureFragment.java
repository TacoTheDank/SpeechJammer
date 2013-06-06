package com.icechen1.speechjammer;

import com.actionbarsherlock.view.MenuItem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ConfigureFragment extends Fragment {
	TextView headphone_status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.configure_fragment, container, false);
	    SeekBar mSeekbar = (SeekBar) v.findViewById(R.id.seekBar);
	    mSeekbar.setProgress(200);
	    final TextView seekTime = (TextView) v.findViewById(R.id.current_delay);
	    seekTime.setText("200 ms");
	    mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
	    {
	       public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	       {
	            seekTime.setText(progress + " ms");
	            MainActivity.delay_time = progress;
	       }

	      public void onStartTrackingTouch(SeekBar seekBar) {}

	      public void onStopTrackingTouch(SeekBar seekBar) {}
	    });
		//Set up headphone unplugged failsafe
		getActivity().registerReceiver(new HeadsetConnectionReceiver(), 
                new IntentFilter(Intent.ACTION_HEADSET_PLUG));
	    headphone_status = (TextView) v.findViewById(R.id.headphone_status);
        return v;
    }
    
	public class HeadsetConnectionReceiver extends BroadcastReceiver {
		public boolean headsetConnected = false;
		 public void onReceive(Context context, Intent intent) {
			  if (intent.hasExtra("state")){
			   if (headsetConnected && intent.getIntExtra("state", 0) == 0){
				   headsetConnected = false;
				   headphone_status.setText(getResources().getString (R.string.headphone_status2));
			   }
			   else if (!headsetConnected && intent.getIntExtra("state", 0) == 1){
				   headsetConnected = true;
				   headphone_status.setText(getResources().getString (R.string.headphone_status1));
			   }

			  }
		 }
	}

}