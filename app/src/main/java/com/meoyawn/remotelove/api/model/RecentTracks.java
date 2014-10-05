package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class RecentTracks implements Parcelable {
  public @NotNull Track[] track;

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest, int flags) {
    dest.writeParcelableArray(this.track, flags);
  }

  private RecentTracks(Parcel in) {
    this.track = (Track[]) in.readParcelableArray(Track[].class.getClassLoader());
  }

  public static final Parcelable.Creator<RecentTracks> CREATOR = new Parcelable
      .Creator<RecentTracks>() {
    public RecentTracks createFromParcel(@NotNull Parcel source) {return new RecentTracks(source);}

    @NotNull public RecentTracks[] newArray(int size) {return new RecentTracks[size];}
  };
}