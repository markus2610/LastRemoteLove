package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class Attr implements Parcelable {
  public boolean nowplaying;

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest,
                                      int flags) {dest.writeByte(nowplaying ? (byte) 1 : (byte) 0);}

  private Attr(Parcel in) {this.nowplaying = in.readByte() != 0;}

  public static final Parcelable.Creator<Attr> CREATOR = new Parcelable.Creator<Attr>() {
    public Attr createFromParcel(@NotNull Parcel source) {return new Attr(source);}

    @NotNull public Attr[] newArray(int size) {return new Attr[size];}
  };
}
