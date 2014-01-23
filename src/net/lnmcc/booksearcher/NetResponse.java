package net.lnmcc.booksearcher;

public class NetResponse {

	private int mCode;
	private Object mMessage;
	
	public NetResponse(int code, Object message) {
		mCode = code;
		mMessage = message;
	}

	public int getCode() {
		return mCode;
	}

	public void setCode(int code) {
		mCode = code;
	}

	public Object getMessage() {
		return mMessage;
	}

	public void setMessage(Object message) {
		mMessage = message;
	}
}
