package com.codeogenic.opensources;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by clem_gumbs on 2/10/17.
 */

public class ListItem implements Serializable, Parcelable {
    private String heading;
    private String body;
    private String url;
    private Uri imageUri;


    public ListItem(){

    }

    public ListItem(String heading, String body,String url ) {
        this.url = url;
        this.heading = heading;
        this.body = body;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ListItem){
            ListItem o = (ListItem)obj;
            return this.heading.equals(o.heading);

        }else {
            return false;
        }

    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return title of item
     */
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.heading);
        dest.writeString(this.body);
        dest.writeString(this.url);
        dest.writeParcelable(this.imageUri, flags);
    }

    protected ListItem(Parcel in) {
        this.heading = in.readString();
        this.body = in.readString();
        this.url = in.readString();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel source) {
            return new ListItem(source);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
