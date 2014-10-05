package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class Track implements Parcelable {
  public static final @NotNull Track EMPTY = new Track();

  public @NotNull                         Artist  artist;
  public                                  int     loved;
  public @NotNull                         String  name;
  public @NotNull                         String  url;
  public @NotNull                         Image[] image;
  public @JsonProperty("@attr") @Nullable Attr    attr;

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest, int flags) {
    dest.writeParcelable(this.artist, 0);
    dest.writeInt(this.loved);
    dest.writeString(this.name);
    dest.writeString(this.url);
    dest.writeParcelableArray(this.image, flags);
    dest.writeParcelable(this.attr, 0);
  }

  private Track(Parcel in) {
    this.artist = in.readParcelable(Artist.class.getClassLoader());
    this.loved = in.readInt();
    this.name = in.readString();
    this.url = in.readString();
    this.image = (Image[]) in.readParcelableArray(Image[].class.getClassLoader());
    this.attr = in.readParcelable(Attr.class.getClassLoader());
  }

  public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
    public Track createFromParcel(@NotNull Parcel source) {return new Track(source);}

    @NotNull public Track[] newArray(int size) {return new Track[size];}
  };
}
