package com.meoyawn.remotelove.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/6/14
 */
@NoArgsConstructor
public class Image implements Parcelable {
  public @JsonProperty("#text") @NotNull String url;
  public @NotNull                        String size;

  @Override public int describeContents() { return 0; }

  @Override public void writeToParcel(@NotNull Parcel dest, int flags) {
    dest.writeString(this.url);
    dest.writeString(this.size);
  }

  private Image(Parcel in) {
    this.url = in.readString();
    this.size = in.readString();
  }

  public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
    public Image createFromParcel(@NotNull Parcel source) {return new Image(source);}

    @NotNull public Image[] newArray(int size) {return new Image[size];}
  };
}