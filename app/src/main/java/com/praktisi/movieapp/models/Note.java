package com.praktisi.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "note")
public class Note implements Parcelable {

	@SerializedName("date")
	private String date;
	@PrimaryKey(autoGenerate = true)
	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("desc")
	private String desc;

	protected Note(Parcel in) {
		date = in.readString();
		id = in.readInt();
		title = in.readString();
		desc = in.readString();
	}

	public Note(
			String date,
			int id,
			String title,
			String desc
	) {
		this.date = date;
		this.id = id;
		this.title = title;
		this.desc = desc;
	}

	public static final Creator<Note> CREATOR = new Creator<Note>() {
		@Override
		public Note createFromParcel(Parcel in) {
			return new Note(in);
		}

		@Override
		public Note[] newArray(int size) {
			return new Note[size];
		}
	};

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	@Override
 	public String toString(){
		return 
			"Note{" + 
			"date = '" + date + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",desc = '" + desc + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeString(date);
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(desc);
	}
}