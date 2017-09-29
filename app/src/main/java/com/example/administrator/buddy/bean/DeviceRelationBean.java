package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import com.example.administrator.buddy.R;
import lombok.Data;

/**
 * 关系
 * @author zhuj 2017/7/8 下午6:50.
 */
@Data
public class DeviceRelationBean implements Parcelable {

  private String name;
  private boolean checked;
  @AppType.RelationType  private int relation;

  public DeviceRelationBean(int relation,  String name) {
    this.name = name;
    this.relation = relation;
  }

  public @DrawableRes int getRelationResBig() {
    int resId;
    switch (relation) {
      case AppType.RELATION_FATHER:
        resId = R.mipmap.img_relation_father_big;
        break;
      case AppType.RELATION_MOTHER:
        resId = R.mipmap.img_relation_mother_big;
        break;
      case AppType.RELATION_GRAND_FATHER:
        resId = R.mipmap.img_relation_grandfather_big;
        break;
      case AppType.RELATION_GRAND_MOTHER:
        resId = R.mipmap.img_relation_grandmother_big;
        break;
      case AppType.RELATION_GRAND_PA:
        resId = R.mipmap.img_relation_grandpa_big;
        break;
      case AppType.RELATION_GRAND_MA:
        resId = R.mipmap.img_relation_grandma_big;
        break;
      case AppType.RELATION_OTHER:
      default:
        resId = R.mipmap.img_relation_other_big;
        break;
    }
    return resId;
  }

  public @DrawableRes int getRelationResSmall() {
    int resId;
    switch (relation) {
      case AppType.RELATION_FATHER:
        resId = R.mipmap.img_relation_father;
        break;
      case AppType.RELATION_MOTHER:
        resId = R.mipmap.img_relation_mother;
        break;
      case AppType.RELATION_GRAND_FATHER:
        resId = R.mipmap.img_relation_grandfather;
        break;
      case AppType.RELATION_GRAND_MOTHER:
        resId = R.mipmap.img_relation_grandmother;
        break;
      case AppType.RELATION_GRAND_PA:
        resId = R.mipmap.img_relation_grandpa;
        break;
      case AppType.RELATION_GRAND_MA:
        resId = R.mipmap.img_relation_grandma;
        break;
      case AppType.RELATION_OTHER:
      default:
        resId = R.mipmap.img_relation_other;
        break;
    }
    return resId;
  }

  public DeviceRelationBean() {

  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    dest.writeInt(this.relation);
  }

  protected DeviceRelationBean(Parcel in) {
    this.name = in.readString();
    this.checked = in.readByte() != 0;
    this.relation = in.readInt();
  }

  public static final Creator<DeviceRelationBean> CREATOR =
          new Creator<DeviceRelationBean>() {
            @Override public DeviceRelationBean createFromParcel(Parcel source) {
              return new DeviceRelationBean(source);
            }

            @Override public DeviceRelationBean[] newArray(int size) {
              return new DeviceRelationBean[size];
            }
          };

}
