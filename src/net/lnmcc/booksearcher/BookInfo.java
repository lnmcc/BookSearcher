package net.lnmcc.booksearcher;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BookInfo implements Parcelable {

	private String mTitle = "";
	private Bitmap mCover;
	private String mAuthor = "";
	private String mPublisher = "";
	private String mPublishDate = "";
	private String mISBN = "";
	private String mSummary = "";

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Bitmap getCover() {
		return mCover;
	}

	public void setCover(Bitmap cover) {
		mCover = cover;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String author) {
		mAuthor = author;
	}

	public String getPublisher() {
		return mPublisher;
	}

	public void setPublisher(String publicsher) {
		mPublisher = publicsher;
	}

	public String getPublishDate() {
		return mPublishDate;
	}

	public void setPublishDate(String publishDate) {
		mPublishDate = publishDate;
	}

	public String getISBN() {
		return mISBN;
	}

	public void setISBN(String isbn) {
		mISBN = isbn;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		mSummary = summary;
	}

	public static final Parcelable.Creator<BookInfo> CREATOR = new Creator<BookInfo>() {

		@Override
		public BookInfo[] newArray(int size) {
			return new BookInfo[size];
		}

		@Override
		public BookInfo createFromParcel(Parcel source) {
			BookInfo bookInfo = new BookInfo();
			bookInfo.mTitle = source.readString();
			bookInfo.mCover = source.readParcelable(Bitmap.class
					.getClassLoader());
			bookInfo.mAuthor = source.readString();
			bookInfo.mPublisher = source.readString();
			bookInfo.mPublishDate = source.readString();
			bookInfo.mISBN = source.readString();
			bookInfo.mSummary = source.readString();

			return bookInfo;
		}
	};

	public int describeContents() {
		return 0;
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(mTitle);
		dest.writeParcelable(mCover, flags);
		dest.writeString(mAuthor);
		dest.writeString(mPublisher);
		dest.writeString(mPublishDate);
		dest.writeString(mISBN);
		dest.writeString(mSummary);
	}
}
