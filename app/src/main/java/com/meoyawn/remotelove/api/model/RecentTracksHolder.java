package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class RecentTracksHolder implements Parcelable {
  public @NotNull RecentTracks recenttracks = new RecentTracks();

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest,
                                      int flags) {dest.writeParcelable(this.recenttracks, 0);}

  private RecentTracksHolder(Parcel in) {
    this.recenttracks = in.readParcelable(RecentTracks.class.getClassLoader());
  }

  public static final Parcelable.Creator<RecentTracksHolder> CREATOR = new Parcelable
      .Creator<RecentTracksHolder>() {
    public RecentTracksHolder createFromParcel(@NotNull Parcel source) {
      return new RecentTracksHolder
          (source);
    }

    @NotNull public RecentTracksHolder[] newArray(int size) {return new RecentTracksHolder[size];}
  };
}