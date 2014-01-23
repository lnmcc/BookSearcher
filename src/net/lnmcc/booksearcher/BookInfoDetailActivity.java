package net.lnmcc.booksearcher;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

public class BookInfoDetailActivity extends Activity {

	private TextView mtitle;
	private ImageView mCover;
	private TextView mAuthor;
	private TextView mPublisher;
	private TextView mPublishDate;
	private TextView mISBN;
	private TextView mSummary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);

		initViews();
		initData(getIntent().getParcelableExtra(BookInfo.class.getName()));
	}

	private void initViews() {
		mtitle = (TextView) findViewById(R.id.book_detail_title);
		mCover = (ImageView) findViewById(R.id.book_detail_cover);
		mAuthor = (TextView) findViewById(R.id.book_detail_author);
		mPublisher = (TextView) findViewById(R.id.book_detail_publisher);
		mPublishDate = (TextView) findViewById(R.id.book_detail_pubdate);
		mISBN = (TextView) findViewById(R.id.book_detail_isbn);
		mSummary = (TextView) findViewById(R.id.book_detail_summary);
	}
	
	private void initData(Parcelable data) {
		if(data == null) {
			return;
		}
		
		BookInfo bookInfo = (BookInfo)data;
		
		mtitle.setText(bookInfo.getTitle());
		mCover.setImageBitmap(bookInfo.getCover());
		mAuthor.setText(bookInfo.getAuthor());
		mPublishDate.setText(bookInfo.getPublishDate());
		mPublisher.setText(bookInfo.getPublisher());
		mISBN.setText(bookInfo.getISBN());
		mSummary.setText(bookInfo.getSummary());
	}

}
