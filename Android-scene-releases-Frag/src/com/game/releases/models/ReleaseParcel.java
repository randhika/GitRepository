package com.game.releases.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReleaseParcel implements Parcelable {
	
	private String title;
	private String uri;
	private String description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeString(title);
		arg0.writeString(uri);
		arg0.writeString(description);
	}

	private void readFromParcel(Parcel parcel){
		this.title = parcel.readString();
		this.uri = parcel.readString();
		this.description = parcel.readString();
	}

	@Override
	public String toString() {
		return "ReleaseParcel [title=" + title + ", uri=" + uri
				+ ", description=" + description + "]";
	}

}
