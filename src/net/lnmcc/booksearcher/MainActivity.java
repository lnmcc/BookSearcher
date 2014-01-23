package net.lnmcc.booksearcher;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressDialog mProgressDialog;
	private Button mScanBtn;
	public DownloadHandler mHandler = new DownloadHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mScanBtn = (Button) findViewById(R.id.main_start_scan);
		mScanBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startScan();
			}
		});
	}

	private void startScan() {
		IntentIntegrator intergrator = new IntentIntegrator(this);
		intergrator.initiateScan();
	}

	private void startBookInfoDetailActivity(BookInfo bookInfo) {
		if (bookInfo == null) {
			return;
		}

		Intent intent = new Intent(this, BookInfoDetailActivity.class);
		intent.putExtra(BookInfo.class.getName(), bookInfo);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);

		if (scanResult == null || (scanResult.getContents() == null)) {
			Log.v(Utils.TAG, "User cancel scan by pressing back hardkey");
			return;
		}
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(getString(R.string.communicating));
		mProgressDialog.show();

		DownloadThread thread = new DownloadThread(BookAPI.URL_ISBN_BASE
				+ scanResult.getContents());
		thread.start();
	}

	private class DownloadThread extends Thread {

		private String mURL;

		public DownloadThread(String url) {
			super();
			mURL = url;
		}

		@Override
		public void run() {
			Message msg = Message.obtain();
			msg.obj = Utils.download(mURL);
			mHandler.sendMessage(msg);
		}
	}

	private static class DownloadHandler extends Handler {

		private MainActivity mActivity;

		public DownloadHandler(MainActivity activity) {
			super();
			mActivity = activity;
		}

		@Override
		public void handleMessage(Message msg) {
			if ((msg.obj == null) || (mActivity.mProgressDialog == null)) {
				return;
			}

			mActivity.mProgressDialog.dismiss();
			NetResponse response = (NetResponse) msg.obj;
			if (response.getCode() != BookAPI.RESPONSE_CODE_SUCCEED) {
				Toast.makeText(
						mActivity,
						"["
								+ response.getCode()
								+ "]: "
								+ mActivity.getString((Integer) response
										.getMessage()), Toast.LENGTH_LONG)
						.show();
			} else {
				mActivity.startBookInfoDetailActivity((BookInfo) response
						.getMessage());
			}

		}
	}
}
