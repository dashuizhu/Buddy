package com.example.administrator.buddy.ui.device;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.AppType;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceRelationBean;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.device.presenter.DeviceContactsPresenter;
import com.example.administrator.buddy.utils.AppUtils;
import com.example.administrator.buddy.view.HeaderView;
import com.example.administrator.buddy.view.MyItemView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.PhotoPicker;


/**
 * 手表联系人编辑界面
 *
 * @author zhuj
 * @date 2017/6/16 下午4:04
 */
public class DeviceContactsEditActivity extends BaseActivity {

  @BindView(R.id.headerView) HeaderView mHeaderView;
  @BindView(R.id.item_contacts_name) MyItemView mItemContactsName;
  @BindView(R.id.item_contacts_head) RelativeLayout mItemContactsHead;
  @BindView(R.id.edit_contacts_name) EditText mEditContactsName;
  @BindView(R.id.edit_contacts_phone) EditText mEditContactsPhone;
  @BindView(R.id.img_contacts_right) ImageView mTextViewMyItemArrows;
  @BindView(R.id.btn_confirm) Button mBtnConfirm;
  @BindView(R.id.textView_my_item_head) SimpleDraweeView mTextViewMyItemHead;
  @BindView(R.id.textView_my_item_title) TextView mTextViewMyItemTitle;

  private DeviceRelationBean mRelationBean;

  private static final int RESULT_FOR_RELATION = 0x1;
  private DeviceContactsPresenter mPresenter;
  private DeviceContactsBean mContactsBean;
  private String headUrl = "";//本地图片路径
  private int relation = 0;
  private boolean isAddContacts;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device_contacts_edit);
    ButterKnife.bind(this);
    initViews();
    injectorPresenter();
  }

  private void initViews() {
    //得到返回以前使用putExtra（）添加的项目的值
    mContactsBean = getIntent().getParcelableExtra(AppString.KEY_DEVICE_CONTACTS);

    if (mContactsBean == null) {
      mHeaderView.setTitle(R.string.title_device_contacts_add);
      mBtnConfirm.setText(R.string.label_confirm_add);
      mItemContactsName.setTextContent(getString(R.string.relation_other));
      mTextViewMyItemHead.setBackgroundResource(R.mipmap.img_relation_other);
      mItemContactsHead.setEnabled(false);
      isAddContacts = true;
      mContactsBean = new DeviceContactsBean();
    } else {
      relation = mContactsBean.getRelation();
      mHeaderView.setTitle(R.string.title_device_contacts_edit);
      mBtnConfirm.setText(R.string.label_confirm);
      if (mContactsBean.getRelation() == AppType.RELATION_OTHER) {
        mItemContactsHead.setEnabled(false);
        mTextViewMyItemArrows.setBackgroundResource(R.mipmap.list_right_arrow);
      }else{
        mTextViewMyItemArrows.setBackgroundResource(R.mipmap.list_right_null);
        mItemContactsHead.setEnabled(false);
      }
      DeviceRelationBean relationBean =
          new DeviceRelationBean(mContactsBean.getRelation(), mContactsBean.getName());
      if(mContactsBean.getAvatar()!=null && mContactsBean.getAvatar().length()>1){
        mTextViewMyItemHead.setImageURI(Uri.parse(mContactsBean.getAvatar()));
      }else{
        mTextViewMyItemHead.setBackgroundResource(relationBean.getRelationResSmall());
      }
      mItemContactsName.setTextContent(getRelationName(mContactsBean.getRelation()));
      mEditContactsName.setText(mContactsBean.getName());
      mEditContactsPhone.setText(mContactsBean.getMobile());
      AppUtils.initSelecton(mEditContactsName);
      AppUtils.initSelecton(mEditContactsPhone);
      isAddContacts = false;
    }
  }

  private String getRelationName(int relation){
    String relationName ;
    switch (relation) {
      case AppType.RELATION_FATHER:
        relationName = "爸爸";
        break;
      case AppType.RELATION_MOTHER:
        relationName = "妈妈";
        break;
      case AppType.RELATION_GRAND_FATHER:
        relationName = "爷爷";
        break;
      case AppType.RELATION_GRAND_MOTHER:
        relationName = "奶奶";
        break;
      case AppType.RELATION_GRAND_PA:
        relationName = "外公";
        break;
      case AppType.RELATION_GRAND_MA:
        relationName = "外婆";
        break;
      case AppType.RELATION_OTHER:
      default:
        relationName = "其他";
        break;
    }
    return relationName;
  }

  private void injectorPresenter() {
    PresenterComponent component =
        DaggerPresenterComponent.builder().modelModule(new ModelModule(this)).build();
    mPresenter = component.getDeviceContactsPresenter();
  }

  @Override public void success(Object successObj) {
    if (successObj instanceof NetworkResult) {
      setResult(RESULT_OK, getIntent());
      finish();
    }
    super.success(successObj);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK) {
      return;
    }
    if (requestCode == RESULT_FOR_RELATION) {
      mRelationBean = data.getParcelableExtra(AppString.KEY_RELATION_BEAN);
      relation = mRelationBean.getRelation();
      headUrl = "";
      if (relation == AppType.RELATION_OTHER) {
        mTextViewMyItemArrows.setBackgroundResource(R.mipmap.list_right_arrow);
        mItemContactsHead.setEnabled(false);
      }else{
        mTextViewMyItemArrows.setBackgroundResource(R.mipmap.list_right_null);
        mItemContactsHead.setEnabled(false);
      }
      String contacts = mRelationBean.getName();
      if(!contacts.equals("其他")){
        mEditContactsName.setText(contacts);
      }
      mItemContactsName.setTextContent(mRelationBean.getName());
      mTextViewMyItemHead.setImageResource(mRelationBean.getRelationResSmall());
      mContactsBean.setRelation(mRelationBean.getRelation());
    }else {
      List<String> photos = null;
      if (data != null) {
        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
      }
      if (photos != null && photos.size() > 0) {
         headUrl= photos.get(0);
      }
      mTextViewMyItemHead.setImageURI(Uri.parse("file://"+headUrl));
      //mUploadPresenter.uploadImage(headUrl);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @OnClick(R.id.layout_header_back) public void onBack() {
    finish();
  }



  private boolean isCheck() {
     if (TextUtils.isEmpty(mEditContactsName.getText().toString().trim())) {
      showToast("请输入昵称");
      return false;
    } else if (TextUtils.isEmpty(mEditContactsPhone.getText().toString().trim())) {
      showToast("请输入电话号码");
      return false;
    }
    return true;
  }

  @OnClick({ R.id.item_contacts_name, R.id.btn_confirm,R.id.item_contacts_head }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.item_contacts_name:

        Intent intent = new Intent(this, DeviceRelationSelectActivity.class);
        intent.putExtra(AppString.KEY_RELATION, relation);
        startActivityForResult(intent, RESULT_FOR_RELATION);

        break;
      case R.id.btn_confirm:
        if (!isCheck()) {
          return;
        }
        mContactsBean.setName(mEditContactsName.getText().toString().trim());
        mContactsBean.setMobile(mEditContactsPhone.getText().toString().trim());
        mContactsBean.setRelation(relation);
        List<DeviceContactsBean> list = new ArrayList<>();
        //判断是否需要选取自定义头像
          mContactsBean.setAvatar(relation + "");
          list.add(mContactsBean);
          if (isAddContacts) {
            mPresenter.addContacts(list);
          } else {
            mPresenter.modifyContacts(list);
          }

        break;
      case R.id.item_contacts_head:
        //takePhoto();
        break;
    }
  }

  //private void takePhoto(){
  //  new RxPermissions(this).request(Manifest.permission.CAMERA,
  //      Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
  //    @Override public void call(Boolean aBoolean) {
  //      if (aBoolean) {
  //        //添加图片
  //        PhotoPicker.builder()
  //            .setPhotoCount(1)
  //            .setShowCamera(false)
  //            .setPreviewEnabled(false)
  //            .start(DeviceContactsEditActivity.this);
  //      } else {
  //        ToastUtils.toast(R.string.toast_permission_camera_error);
  //      }
  //    }
  //  });
  //}

}
