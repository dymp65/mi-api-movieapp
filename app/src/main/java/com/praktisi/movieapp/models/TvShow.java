package com.praktisi.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tv_show")
public class TvShow implements Parcelable {

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("name")
    private String name;

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("vote_count")
    private int voteCount;

    protected TvShow(Parcel in) {
        firstAirDate = in.readString();
        overview = in.readString();
        originalLanguage = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        originalName = in.readString();
        popularity = in.readDouble();
        voteAverage = in.readDouble();
        name = in.readString();
        id = in.readInt();
        adult = in.readByte() != 0;
        voteCount = in.readInt();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public TvShow(
            String firstAirDate,
            String overview,
            String originalLanguage,
            String posterPath,
            String backdropPath,
            String originalName,
            double popularity,
            double voteAverage,
            String name,
            int id,
            boolean adult,
            int voteCount
    ) {
        this.firstAirDate = firstAirDate;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalName = originalName;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.name = name;
        this.id = id;
        this.adult = adult;
        this.voteCount = voteCount;
    }

    public void setFirstAirDate(String firstAirDate){
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate(){
        return firstAirDate;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getOverview(){
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage){
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage(){
        return originalLanguage;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public void setBackdropPath(String backdropPath){
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath(){
        return backdropPath;
    }

    public void setOriginalName(String originalName){
        this.originalName = originalName;
    }

    public String getOriginalName(){
        return originalName;
    }

    public void setPopularity(double popularity){
        this.popularity = popularity;
    }

    public double getPopularity(){
        return popularity;
    }

    public void setVoteAverage(double voteAverage){
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setAdult(boolean adult){
        this.adult = adult;
    }

    public boolean isAdult(){
        return adult;
    }

    public void setVoteCount(int voteCount){
        this.voteCount = voteCount;
    }

    public int getVoteCount(){
        return voteCount;
    }

    @Override
    public String toString(){
        return
                "TvShow{" +
                        "first_air_date = '" + firstAirDate + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",original_name = '" + originalName + '\'' +
                        ",popularity = '" + popularity + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",adult = '" + adult + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalName);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeInt(this.voteCount);
    }
}
