package com.example.searchcermati;

import android.os.Parcel;
import android.os.Parcelable;

public class modelUsers implements Parcelable {
    private String username;
    private String avatar;
    private String url;

    public modelUsers(String username, String avatar, String url) {
        this.username = username;
        this.avatar = avatar;
        this.url = url;
    }


    public static final Creator<modelUsers> CREATOR = new Creator<modelUsers>() {
        @Override
        public modelUsers createFromParcel(Parcel in) {
            return new modelUsers(in);
        }

        @Override
        public modelUsers[] newArray(int size) {
            return new modelUsers[size];
        }
    };

    public modelUsers(Parcel in) {
        username = in.readString();
        avatar = in.readString();
        url = in.readString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(avatar);
        dest.writeString(url);

    }
}
