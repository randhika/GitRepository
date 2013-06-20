package com.game.releases.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReleaseParcel implements Parcelable {
	
	private String title;
	private String uri;
	public static final Parcelable.Creator<ReleaseParcel> CREATOR = new Parcelable.Creator<ReleaseParcel>() {

		@Override
		public ReleaseParcel createFromParcel(Parcel source) {
			return new ReleaseParcel(source);
		}

		@Override
		public ReleaseParcel[] newArray(int size) {
			return new ReleaseParcel[size];
		}
		
	};	
	
	public ReleaseParcel(){}
	
	public ReleaseParcel(Parcel parcel){
		readFromParcel(parcel);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeString(title);
		arg0.writeString(uri);
	}

	private void readFromParcel(Parcel parcel){
		this.title = parcel.readString();
		this.uri = parcel.readString();
	}

	@Override
	public String toString() {
		return "Title=" + title + ", Uri=" + uri;
	}
	
	
}
