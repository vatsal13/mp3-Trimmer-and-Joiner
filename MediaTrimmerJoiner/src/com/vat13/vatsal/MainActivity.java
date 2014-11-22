package com.vat13.vatsal;

import java.io.File;
import android.view.View.OnTouchListener;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import android.view.View.OnClickListener;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

class Mp3filter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		// TODO Auto-generated method stub
		return (arg1.endsWith(".mp3"));
	}
}

public class MainActivity extends ListActivity {

	private static final String SD_PATH = new String("/storage/sdcard0/Music/");
	private List<String> songs = new ArrayList<String>();
	private MediaPlayer mp = new MediaPlayer();

	public Button b, b2, b3;
	public SeekBar musicSeekBar;
	public RadioButton o, s, o2, s2;
	public String audioPath, arg1, arg2, g;
	public TextView t1, t2, t3, t4;
	public EditText e;
	public int start, end, dur;
	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		updatePL();
		b = (Button) findViewById(R.id.button1);
		b3 = (Button) findViewById(R.id.button3);
		o = (RadioButton) findViewById(R.id.radioButton1);
		s = (RadioButton) findViewById(R.id.radioButton2);
		o2 = (RadioButton) findViewById(R.id.RadioButton03);
		s2 = (RadioButton) findViewById(R.id.RadioButton04);
		e = (EditText) findViewById(R.id.editText1);
		t1 = (TextView) findViewById(R.id.textView1);
		t2 = (TextView) findViewById(R.id.textView2);
		t3 = (TextView) findViewById(R.id.TextView03);
		t4 = (TextView) findViewById(R.id.TextView04);
		e.setText("enter some name");
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.stop();

			}
		});
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// .................

				g = e.getText().toString();
				if (g.equals("enter some name")) {
					e.setText("enter some name");

				}
				// .................
				else {
					Timesplit ts = new Timesplit();
					ts.trim(audioPath, start, end, dur, g);
					updatePL();
				}

			}
		});
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Test o = new Test();
				// o.d();
				g = e.getText().toString();
				// new Thread(new Runnable() {
				// public void run() {
				FileOutputStream fos = null;
				FileInputStream fis1 = null;
				FileInputStream fis2 = null;
				try {

					fos = new FileOutputStream("/storage/sdcard0/Music/" + g
							+ ".mp3");
					fis1 = new FileInputStream(arg1);
					fis2 = new FileInputStream(arg2);
					File f = new File("/storage/sdcard0/Music/" + g + ".mp3");
					SequenceInputStream sis = new SequenceInputStream(fis1,
							fis2);
					byte[] buffer = new byte[(1024 * 2)];
					int x = 0;
					// while((x=fis1.read(buffer))!=-1){
					// fos.write(buffer, 0, x);
					// }
					// fis1.close();
					// fis2 = new FileInputStream(arg2);

					while ((x = sis.read(buffer)) != -1) {
						fos.write(buffer, 0, x);

					}
					// fis2.close();
					// fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ................................

				// }
				// }).start();
				updatePL();

			}
		});

		musicSeekBar = (SeekBar) findViewById(R.id.seekBar1);

		// musicSeekBar = (SeekBar) findViewById(R.id.SeekBar01);

		musicSeekBar.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				seekChange(v);

				return false;
			}

		});
		// ............
	}

	public void startPlayProgressUpdater() {
		musicSeekBar.setProgress(mp.getCurrentPosition());

		if (mp.isPlaying()) {
			Runnable notification = new Runnable() {
				public void run() {

					startPlayProgressUpdater();
				}
			};
			handler.postDelayed(notification, 1000);
		} else {
			mp.pause();

			musicSeekBar.setProgress(0);
		}
	}

	private void seekChange(View v) {
		if (mp.isPlaying()) {
			int t;
			SeekBar sb = (SeekBar) v;
			mp.seekTo(sb.getProgress());
			t = mp.getCurrentPosition();
			dur = mp.getDuration();
			if (o.isChecked()) {
				start = t;
				t1.setText("" + t);
			}

			if (s.isChecked()) {
				end = t;
				t2.setText("" + t);
			}
		}
	}

	protected void onListItemClick(ListView list, View view, int position,
			long id) {

		boolean t;

		try {
			if (o2.isChecked()) {
				arg1 = SD_PATH + songs.get(position);
				t3.setText("" + songs.get(position));
			}

			if (s2.isChecked()) {
				arg2 = SD_PATH + songs.get(position);
				t4.setText("" + songs.get(position));
			}

			mp.reset();
			// mp.setDataSource(SD_PATH + songs.get(position));
			// mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			FileDescriptor fd = null;
			audioPath = SD_PATH + songs.get(position);
			FileInputStream fis = new FileInputStream(audioPath);
			fd = fis.getFD();

			mp.reset();
			mp.setDataSource(fd);
			mp.prepare();

			// mp.setOnCompletionListener(new OnCompletionListener() {
			// public synchronized void onCompletion(MediaPlayer arg0) {
			//
			// }
			// });
			musicSeekBar.setMax(mp.getDuration());
			mp.start();
			startPlayProgressUpdater();

		} catch (Exception e) {
			Log.v(getString(R.string.app_name), e.getMessage());
		}
	}

	private void updatePL() {
		// TODO Auto-generated method stub
		File home = new File(SD_PATH);
		songs.clear();
		if (home.listFiles(new Mp3filter()).length > 0) {

			for (File file : home.listFiles(new Mp3filter())) {

				songs.add(file.getName());
			}

			ArrayAdapter<String> songlist = new ArrayAdapter<String>(this,
					R.layout.unlucky13, songs);
			setListAdapter(songlist);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
