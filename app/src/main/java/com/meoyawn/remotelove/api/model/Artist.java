package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class Artist implements Parcelable {
  public @NotNull String  name;
  public @NotNull Image[] image;

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeParcelableArray(this.image, flags);
  }

  private Artist(Parcel in) {
    this.name = in.readString();
    this.image = (Image[]) in.readParcelableArray(Image[].class.getClassLoader());
  }

  public static final Creator<Artist> CREATOR = new Creator<Artist>() {
    public Artist createFromParcel(@NotNull Parcel source) {return new Artist(source);}

    @NotNull public Artist[] newArray(int size) {return new Artist[size];}
  };
}