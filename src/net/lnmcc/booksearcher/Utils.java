package net.lnmcc.booksearcher;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Utils {

	public static String TAG = "BookSearcher";

	public static NetResponse download(String url) {
		Log.v(TAG, "download from: " + url);

		NetResponse ret = downloadFromDouban(url);

		JSONObject message = null;

		try {
			message = new JSONObject(String.valueOf(ret.getMessage()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		switch (ret.getCode()) {
		case BookAPI.RESPONSE_CODE_SUCCEED:
			ret.setMessage(parseBookInfo(message));
			break;

		default:
			int errorCode = parseErrorCode(message);
			ret.setCode(errorCode);
			ret.setMessage(getErrorMessage(errorCode));
			break;
		}

		return ret;
	}

	private static NetResponse downloadFromDouban(String url) {
		HttpURLConnection conn = null;
		NetResponse response = null;

		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			response = new NetResponse(conn.getResponseCode(),
					conn.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	private static BookInfo parseBookInfo(JSONObject json) {
		if (json == null) {
			return null;
		}

		BookInfo bookInfo = null;

		try {
			bookInfo = new BookInfo();
			bookInfo.setTitle(json.getString(BookAPI.TAG_TITLE));
			bookInfo.setCover(downloadBitmap(json.getString(BookAPI.TAG_COVER)));
			bookInfo.setAuthor(parseJSONArray2String(
					json.getJSONArray(BookAPI.TAG_AUTHOR), " "));
			bookInfo.setPublisher(json.getString(BookAPI.TAG_PUBLISHER));
			bookInfo.setPublishDate(json.getString(BookAPI.TAG_PUBLISH_DATE));
			bookInfo.setISBN(json.getString(BookAPI.TAG_ISBN));
			bookInfo.setSummary(json.getString(BookAPI.TAG_SUMMARY).replace(
					"\n", "\n\n"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bookInfo;
	}

	private static int getErrorMessage(int errorCode) {
		int ret = R.string.error_message_default;

		switch (errorCode) {
		case BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION:
			ret = R.string.error_message_net_exception;
			break;
		case BookAPI.RESPONSE_CODE_ERROR_BOOK_NOT_FOUND:
			ret = R.string.error_message_book_not_found;
			break;
		default:
			break;
		}
		return ret;
	}

	private static int parseErrorCode(JSONObject json) {
		int ret = BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION;

		if (json == null) {
			return ret;
		}

		try {
			ret = json.getInt(BookAPI.TAG_ERROR_CODE);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private static String parseJSONArray2String(JSONArray json, String split) {
		if ((json == null) || (json.length() < 1)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < json.length(); i++) {
			try {
				sb = sb.append(json.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			sb = sb.append(split);
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	private static Bitmap downloadBitmap(String url) {
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		Bitmap bm = null;

		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			is = conn.getInputStream();
			bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bm;
	}
}
