package com.example.administrator.buddy.ui.habit;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.AppType;
import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.bean.HabitDetailBean;
import com.example.administrator.buddy.bean.HabitDetailResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.utils.ArrayUtils;
import com.example.administrator.buddy.utils.DateUtils;
import com.example.administrator.buddy.view.MyItemView;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 自定义习惯编辑页面
 *
 * @author zhuj 2017/7/5 下午3:17
 */
public class UserHabitSystemEditActivity extends BaseActivity {

  private final int activity_alarm = 10;
  private final int activity_repeat_time = 11;
  private final int activity_repeat_type = 12;
  private final int activity_repeat_date = 13;

  @BindView(R.id.item_habit_repeat_type) MyItemView mItemHabitRepeatType;
  @BindView(R.id.item_habit_repeat_date) MyItemView mItemHabitRepeatDate;
  @BindView(R.id.item_habit_repeat_time) MyItemView mItemHabitRepeatTime;
  @BindView(R.id.iv_habit_ring) ImageView mIvHabitRing;
  @BindView(R.id.tv_habit_ring_name) TextView mTvHabitRingName;
  @BindView(R.id.btn_alarm_select) Button mBtnAlarmSelect;
  @BindView(R.id.tv_education_content) TextView mTvEducationContent;
  @BindView(R.id.tv_habit_kid_content) TextView mTvHabitKidContent;
  @BindView(R.id.tv_habit_expertTitle) TextView mTvHabitExpertTitle;
  @BindView(R.id.tv_response_child) TextView mTvResponseChild;
  @BindView(R.id.tv_playContent) TextView mTvPlayContent;
  @BindView(R.id.btn_confirm) Button mBtnConfirm;
  @BindView(R.id.iv_habit_radio_education) ImageView mIvHabitRadioEducation;
  @BindView(R.id.iv_habit_radio_response) ImageView mIvHabitRadioResponse;
  @BindView(R.id.iv_habit_radio_expert) ImageView mIvHabitRadioExpert;
  @BindView(R.id.sb_player) AppCompatSeekBar mSbPlayer;
  @BindView(R.id.tv_current_time) TextView mTvCurrentTime;
  @BindView(R.id.tv_total_time) TextView mTvTotalTime;
  @BindView(R.id.layout_radio) ConstraintLayout mLayoutRadio;

  @BindView(R.id.tv_title) TextView mTvTitle;

  HabitBean mHabitBean; //从首页 或 历史记录页面过来的，没有详情
  HabitDetailBean mDetailBean; //从管理 过来的， 又详情
  private HabitPresenter mPresenter;
  private MediaPlayer mMediaPlayer;

  private int nowYear, nowMonth, nowDay;

  private boolean isPlayRing;//是否在播放音频
  private ImageView mIvPlay;//正在播放的按钮

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_habit_system_edit);
    ButterKnife.bind(this);
    injectorPresenter();
    initViews();
  }

  private void initViews() {

    boolean isRadio = false;
    if (getIntent().hasExtra(AppString.KEY_HABIT_BEAN)) {
      //从首页列表过来的。都是简单类型数据
      mHabitBean = getIntent().getParcelableExtra(AppString.KEY_HABIT_BEAN);
      mPresenter.getHabitDetail(mHabitBean);
    } else if (getIntent().hasExtra(AppString.KEY_HABIT_DETAIL_BEAN)) {
      //已经是详情，就显示当前详情值
      mDetailBean = getIntent().getParcelableExtra(AppString.KEY_HABIT_DETAIL_BEAN);
      mTvTitle.setText(mDetailBean.getTitle());
      initDetailData();
      mPresenter.getHabitDetail(mDetailBean);
    }

    if (isRadio) { //是否为 音频模式
      mLayoutRadio.setVisibility(View.VISIBLE);
    } else {
      mLayoutRadio.setVisibility(View.GONE);
    }

    Calendar calendar = Calendar.getInstance();
    nowYear = calendar.get(Calendar.YEAR);
    //月份从0开始
    nowMonth = calendar.get(Calendar.MONTH) + 1;
    nowDay = calendar.get(Calendar.DAY_OF_MONTH);

    mSbPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!isPlayRing) return;
        if (mIvPlay != mIvHabitRadioExpert) return;
        if (fromUser) {
          //progress 进度是按秒， postion是按毫秒
          int position = progress * 1000;
          mMediaPlayer.seekTo(position);
        }
        String mPlayTime = DateUtils.showTimeAll(progress);
        mTvCurrentTime.setText(mPlayTime);
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  @OnClick(R.id.imageview_back) public void onBack() {
    finish();
  }

  @OnClick(R.id.item_habit_repeat_type) public void onRepeatType() {
    if (mDetailBean == null) {
      mPresenter.getHabitDetail(mHabitBean);
      return;
    }
    //ActivityUtils.startWheelRepeatType(this, activity_repeat_type, mDetailBean.getPlayFlag());
  }

  @OnClick(R.id.item_habit_repeat_date) public void onRepeatDate() {
    if (mDetailBean == null) {
      mPresenter.getHabitDetail(mHabitBean);
      return;
    }
    switch (mDetailBean.getPlayFlag()) {
      case AppType.REPERT_YEAR:
        //ActivityUtils.startWheelDateByYear(this, activity_repeat_date, nowMonth, nowDay);
        break;
      case AppType.REPERT_MONTH:
        //ActivityUtils.startWheelDateByMonth(this, activity_repeat_date, nowDay);
        break;
      case AppType.REPERT_NONE:
        //ActivityUtils.startWheelDateByNone(this, activity_repeat_date, nowYear, nowMonth, nowDay);
        break;
      case AppType.REPERT_WEEK:
        //ActivityUtils.startWeekDay(this, activity_repeat_date, mDetailBean.getRepeatExpression());
        break;
    }
  }

  @OnClick(R.id.item_habit_repeat_time) public void onRepeatTime() {
    if (TextUtils.isEmpty(mItemHabitRepeatType.getTextContent())) {
      showToast(R.string.toast_repeat_type_first);
      return;
    }
    //ActivityUtils.startWheelTime(this, activity_repeat_time, mItemHabitRepeatTime.getTextContent());
  }

  @OnClick(R.id.btn_alarm_select) public void onRingSelect() {
    //if (mDetailBean != null) {
    //  ActivityUtils.startUserHabitRing(this, activity_alarm, mDetailBean.getVoiceType(),
    //          mDetailBean.getContentId());
    //}
  }

  @OnClick(R.id.btn_confirm) public void onViewClicked() {
    if (TextUtils.isEmpty(mItemHabitRepeatType.getTextContent())) {
      showToast(R.string.toast_repeat_type_first);
      return;
    }
    if (TextUtils.isEmpty(mItemHabitRepeatDate.getTextContent())) {
      showToast(R.string.toast_repeat_date_first);
      return;
    }
    if (TextUtils.isEmpty(mItemHabitRepeatTime.getTextContent())) {
      showToast(R.string.toast_repeat_time_first);
      return;
    }
    if (mDetailBean.getVoiceType() == null) {
      showToast(R.string.toast_habit_ring_first);
      return;
    }
    if (mDetailBean.isAdd()) {
      mPresenter.modifyHabit(mDetailBean);
    } else {
      mPresenter.addHabit(mDetailBean);
    }
  }

  @OnClick({
          R.id.iv_habit_radio_education, R.id.iv_habit_radio_response, R.id.iv_habit_radio_expert,
          R.id.iv_habit_ring
  }) public void onViewClicked(View view) {
    if (mDetailBean == null) return;
    Log.w("test", " click 1 " + isPlayRing + " isVide-" + (mIvPlay == view));
    if (isPlayRing) {
      //如果在播放， 就停止当前播放的
      pausePlay();
      //stopPlay();
      //如果播放的正是当前的选中的， 就直接停止
      if (mIvPlay == view) return;
    }
    String ringUrl = null;
    switch (view.getId()) {
      case R.id.iv_habit_ring:
        ringUrl = mDetailBean.getVoiceUrl();
        break;
      case R.id.iv_habit_radio_education:
        ringUrl = mDetailBean.getEducationUrl();
        break;
      case R.id.iv_habit_radio_response:
        ringUrl = mDetailBean.getRespondUrl();
        break;
      case R.id.iv_habit_radio_expert:
        ringUrl = mDetailBean.getExpertVideoUrl();
        break;
    }
    if (TextUtils.isEmpty(ringUrl)) {
      Toast.makeText(this, R.string.toast_play_error, Toast.LENGTH_LONG).show();
      return;
    }
    //表示当前点击播放的， 是之前播放过的， 就直接start
    if (mIvPlay != null && mIvPlay == view) {
      if (mIvPlay == mIvHabitRadioExpert) {
        refreshPlayProgress();
      }
      mIvPlay.setBackgroundResource(R.drawable.btn_alarm_ring);
      mIvPlay.setSelected(true);
      mMediaPlayer.start();
      isPlayRing = true;
      Log.w("test", " click 2" + isPlayRing);
    } else {
      mIvPlay = (ImageView) view;
      play(ringUrl);
      Log.w("test", " click 3" + isPlayRing);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK) {
      return;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void success(Object successObj) {
    if (successObj instanceof HabitDetailResult) {
      mDetailBean = ((HabitDetailResult) successObj).getData();
      initDetailData();
      mTvHabitRingName.setText(mDetailBean.getVoiceName());
    } else if (successObj instanceof NetworkResult) {
      setResult(RESULT_OK);
      finish();
    }
    super.success(successObj);
  }

  @Override protected void onDestroy() {
    if (mPresenter != null) {
      mPresenter.destroy();
    }
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
    super.onDestroy();
  }

  @Override protected void onStop() {
    stopPlay();
    super.onStop();
  }

  @Override protected void onPause() {
    super.onPause();
  }

  private void initDetailData() {
    //如果是还未添加过的
    mItemHabitRepeatType.setTextContent(
            ArrayUtils.getRepeatTypeName(this, mDetailBean.getPlayFlag()));
    mItemHabitRepeatDate.setTextContent(mDetailBean.getRepeatDateShow());
    mItemHabitRepeatTime.setTextContent(mDetailBean.getTime());
    mTvEducationContent.setText(mDetailBean.getEducation());
    mTvPlayContent.setText(mDetailBean.getPlayContent());
    mTvHabitKidContent.setText(mDetailBean.getRespond());
  }

  private void injectorPresenter() {
    PresenterComponent component =
            DaggerPresenterComponent.builder().modelModule(new ModelModule(this)).build();
    mPresenter = component.getHabitPresenter();
  }

  public void play(String url) {
    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override public void onPrepared(MediaPlayer mp) {
          if (isFinishing()) {
            return;
          }
          //如果是 专家语音， 就显示时长
          if (mIvPlay == mIvHabitRadioExpert) {
            int time = mMediaPlayer.getDuration() / 1000;
            mSbPlayer.setMax(time);
            String timeFormat = String.format("%02d:%02d", time / 60, time % 60);
            mTvTotalTime.setText(timeFormat);
            mTvCurrentTime.setText("00:00");
            refreshPlayProgress();
          }

          mIvPlay.setBackgroundResource(R.drawable.btn_alarm_ring);
          mIvPlay.setSelected(true);
          mp.start();
        }
      });
      mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
        @Override public boolean onError(MediaPlayer mp, int what, int extra) {
          return true;
        }
      });
      mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override public void onCompletion(MediaPlayer mp) {
          if (isFinishing()) {
            return;
          }
          isPlayRing = false;
          Log.w("test", " click 4" + isPlayRing);
          mIvPlay.setSelected(false);
          //如果是 专家语音， 就显示时长
          if (mIvPlay == mIvHabitRadioExpert) {
            Log.w("test", " click 4 1__" + isPlayRing);
            int time = mMediaPlayer.getDuration() / 1000;
            mSbPlayer.setProgress(time);
            String mPlayTime = DateUtils.showTimeAll(time);
            mTvCurrentTime.setText(mPlayTime);
          }
        }
      });
    }
    try {
      ////停止其他音乐播放
      Intent freshIntent = new Intent();
      freshIntent.setAction("com.android.music.musicservicecommand.pause");
      freshIntent.putExtra("command", "pause");
      sendBroadcast(freshIntent);
      isPlayRing = true;
      mMediaPlayer.reset();
      mMediaPlayer.setDataSource(this, Uri.parse(url));
      mMediaPlayer.prepareAsync();
      Log.w("test", " click 5" + isPlayRing);
      mIvPlay.setBackgroundResource(R.mipmap.img_ring_download);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  Subscription mSubscriber;

  private void refreshPlayProgress() {
    unsubRefreshProgress();
    mSubscriber = Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
      @Override public void call(Long aLong) {
        if (mMediaPlayer.isPlaying()) {
          mSbPlayer.setProgress(mMediaPlayer.getCurrentPosition() / 1000);
        }
      }
    });
  }

  private void unsubRefreshProgress() {
    if (mSubscriber != null) {
      if (!mSubscriber.isUnsubscribed()) {
        mSubscriber.unsubscribe();
      }
      mSubscriber = null;
    }
  }

  private void pausePlay() {
    unsubRefreshProgress();
    if (mIvPlay != null) {
      mIvPlay.setBackgroundResource(R.drawable.btn_alarm_ring);
      mIvPlay.setSelected(false);
    }
    isPlayRing = false;
    if (mMediaPlayer != null) {
      mMediaPlayer.pause();
    }
  }

  private void stopPlay() {
    unsubRefreshProgress();
    if (mIvPlay != null) {
      mIvPlay.setBackgroundResource(R.drawable.btn_alarm_ring);
      mIvPlay.setSelected(false);
      mIvPlay = null;
    }
    isPlayRing = false;
    if (mMediaPlayer != null) {
      mMediaPlayer.stop();
      //mMediaPlayer.release();
    }
  }

}
