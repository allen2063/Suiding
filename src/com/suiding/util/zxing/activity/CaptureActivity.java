package com.suiding.util.zxing.activity;

//import java.io.IOException;
//import java.util.Vector;

import android.app.Activity;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Bitmap;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;

//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.Result;
//import com.suiding.activity.R;
//import com.suiding.util.zxing.camera.CameraManager;
//import com.suiding.util.zxing.decoding.CaptureActivityHandler;
//import com.suiding.util.zxing.decoding.InactivityTimer;
//import com.suiding.util.zxing.view.ViewfinderView;
/**
 * 扫描二维码
 * @author zhaojin
 *
 */
public class CaptureActivity extends Activity implements Callback {

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

//	private CaptureActivityHandler handler;
//	private ViewfinderView viewfinderView;
//	private boolean hasSurface;
//	private Vector<BarcodeFormat> decodeFormats;
//	private String characterSet;
//	private InactivityTimer inactivityTimer;
//	private MediaPlayer mediaPlayer;
//	private boolean playBeep;
//	private static final float BEEP_VOLUME = 0.10f;
//	private boolean vibrate;
//	private Button cancelScanButton;
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_camera);
//		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
//		CameraManager.init(getApplication());
//		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
//		hasSurface = false;
//		inactivityTimer = new InactivityTimer(this);
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	protected void onResume() {
//		super.onResume();
//		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
//		SurfaceHolder surfaceHolder = surfaceView.getHolder();
//		if (hasSurface) {
//			initCamera(surfaceHolder);
//		} else {
//			surfaceHolder.addCallback(this);
//			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		}
//		decodeFormats = null;
//		characterSet = null;
//
//		playBeep = true;
//		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
//		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
//			playBeep = false;
//		}
//		initBeepSound();
//		vibrate = true;
//		
//		//quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		if (handler != null) {
//			handler.quitSynchronously();
//			handler = null;
//		}
//		CameraManager.get().closeDriver();
//	}
//
//	@Override
//	protected void onDestroy() {
//		inactivityTimer.shutdown();
//		super.onDestroy();
//	}
//	
//	/**
//	 * Handler scan result
//	 * @param result
//	 * @param barcode
//	 */
//	public void handleDecode(Result result, Bitmap barcode) {
//		inactivityTimer.onActivity();
//		playBeepSoundAndVibrate();
////		String resultString = result.getText();
////		//FIXME
////		if (resultString.equals("") || !resultString.startsWith("~~ontheway$")) {
////			Toast.makeText(CaptureActivity.this, "这个东东不是'在途人'专用名片！", Toast.LENGTH_LONG).show();
////		}else {
//////			System.out.println("Result:"+resultString);
////			Intent resultIntent = new Intent();
////			Bundle bundle = new Bundle();
////			bundle.putString("result", resultString);
////			resultIntent.putExtras(bundle);
////			this.setResult(RESULT_OK, resultIntent);
////		}
//		CaptureActivity.this.finish();
//	}
//	
//	private void initCamera(SurfaceHolder surfaceHolder) {
//		try {
//			CameraManager.get().openDriver(surfaceHolder);
//		} catch (IOException ioe) {
//			return;
//		} catch (RuntimeException e) {
//			return;
//		}
//		if (handler == null) {
//			handler = new CaptureActivityHandler(this, decodeFormats,
//					characterSet);
//		}
//	}
//
//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width,
//			int height) {
//
//	}
//
//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		if (!hasSurface) {
//			hasSurface = true;
//			initCamera(holder);
//		}
//
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		hasSurface = false;
//
//	}
//
//	public ViewfinderView getViewfinderView() {
//		return viewfinderView;
//	}
//
//	public Handler getHandler() {
//		return handler;
//	}
//
//	public void drawViewfinder() {
//		viewfinderView.drawViewfinder();
//
//	}
//
//	private void initBeepSound() {
//		if (playBeep && mediaPlayer == null) {
//			// The volume on STREAM_SYSTEM is not adjustable, and users found it
//			// too loud,
//			// so we now play on the music stream.
//			setVolumeControlStream(AudioManager.STREAM_MUSIC);
//			mediaPlayer = new MediaPlayer();
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			mediaPlayer.setOnCompletionListener(beepListener);
//
//			AssetFileDescriptor file = getResources().openRawResourceFd(
//					R.raw.beep);
//			try {
//				mediaPlayer.setDataSource(file.getFileDescriptor(),
//						file.getStartOffset(), file.getLength());
//				file.close();
//				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//				mediaPlayer.prepare();
//			} catch (IOException e) {
//				mediaPlayer = null;
//			}
//		}
//	}
//
//	private static final long VIBRATE_DURATION = 200L;
//
//	private void playBeepSoundAndVibrate() {
//		if (playBeep && mediaPlayer != null) {
//			mediaPlayer.start();
//		}
//		if (vibrate) {
//			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//			vibrator.vibrate(VIBRATE_DURATION);
//		}
//	}
//
//	/**
//	 * When the beep has finished playing, rewind to queue up another one.
//	 */
//	private final OnCompletionListener beepListener = new OnCompletionListener() {
//		public void onCompletion(MediaPlayer mediaPlayer) {
//			mediaPlayer.seekTo(0);
//		}
//	};

}