package net.lnmcc.booksearcher;

public class BookAPI {

	public static final String URL_ISBN_BASE = "https://api.douban.com/v2/book/isbn/";
	public static final int RESPONSE_CODE_SUCCEED = 200;
	public static final int RESPONSE_CODE_ERROR_NET_EXCEPTION = -1;
	public static final int RESPONSE_CODE_ERROR_BOOK_NOT_FOUND = 404;
	
	public static final String TAG_ERROR_CODE = "code";
	public static final String TAG_TITLE = "title";
	public static final String TAG_COVER = "image";
	public static final String TAG_AUTHOR = "author";
	public static final String TAG_PUBLISHER = "";
	public static final String TAG_PUBLISH_DATE = "";
	public static final String TAG_ISBN = "";
	public static final String TAG_SUMMARY = "summary";
}


