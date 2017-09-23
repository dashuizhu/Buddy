package com.example.administrator.buddy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import com.example.administrator.buddy.adapter.HabitRVAdapter;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.PresenterModule;
import com.example.administrator.buddy.presenter.LoginPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/8/16 14:41.
 */
public class MainFragmentHabit extends BaseFragment
        implements BGARefreshLayout.BGARefreshLayoutDelegate {
    protected ListView mlv;
    //protected HabitAdapter mAdapter;
    protected HabitRVAdapter mAdapter;
    protected List<HabitResult.DataBean> mList;
    public Thread thread;
    LoginPresenter mLoginPresenter;
    RecyclerView mView;
    protected BGARefreshLayout mLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //对View中控件的操作方法
        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build();
        mLoginPresenter = authenticationComponent.getLoginPresenter();
        View view = inflater.inflate(R.layout.fragment_habit, container, false);
        mLayout = (BGARefreshLayout) view.findViewById(R.id.gharl_bgar);
        initRefreshLayout(mLayout);
        //mlv = (RecyclerView) view.findViewById(R.id.tv_fraghabit);
        reg();
        mView = (RecyclerView) view.findViewById(R.id.tv_fraghabit);
        mView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        //mAdapter =new HabitAdapter(getContext() ,mList);
        mAdapter = new HabitRVAdapter(getContext(), mList);
        mView.setAdapter(mAdapter);
        return view;
    }

    @Override public void success(final Object o) {
        super.success(o);

        mList = (List<HabitResult.DataBean>) o;
        mAdapter.setList(mList);//将构造器里的mlist重新赋值
        mAdapter.notifyDataSetChanged();
        //Observable.just(o)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Subscriber<Object>() {
        //            @Override public void onCompleted() {
        //            }
        //
        //            @Override public void onError(Throwable e) {
        //            }
        //            @Override public void onNext(Object o) {
        //
        //                //mLayout.endRefreshing();
        //            }
        //        });

        //if (o instanceof List) {
        //    //mList = (List<HabitBean>) o;得到的mlist不能更新主线程的mlist用message方法封装得到的o传递给Handler更新UI
        //    //mAdapter.notifyDataSetChanged();
        //    Message mess= new Message();
        //    mess.obj = o;
        //    mess.what = 1;
        //    mHandler.sendMessage(mess);
        //}

    }

    //Handler mHandler = new Handler(){
    //    @Override public void handleMessage(Message msg) {
    //        super.handleMessage(msg);
    //        switch (msg.what) {
    //            case 1:
    //                mList = (List<HabitBean>) msg.obj;
    //                mAdapter.setList(mList);//将构造器里的mlist重新赋值
    //                mAdapter.notifyDataSetChanged();//刷新构造器
    //                break;
    //        }
    //    }
    //};
    public void reg() {

        mLayout.beginRefreshing();
    }

    //private void requestGet() {
    //    try {
    //        String baseUrl = "http://47.92.49.151:8080/api/devices/777777777777777/habits/today?userId=4bc7e2383c404841b6b66b18ac1c9321&access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4";
    //      //  StringBuilder tempParams = new StringBuilder();
    //       // String requestUrl = baseUrl + tempParams.toString();
    //        // 新建一个URL对象
    //        URL url = new URL(baseUrl);
    //        // 打开一个HttpURLConnection连接
    //        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    //        // 设置连接主机超时时间
    //        urlConn.setConnectTimeout(5 * 1000);
    //        //设置从主机读取数据超时
    //        urlConn.setReadTimeout(5 * 1000);
    //        // 设置是否使用缓存  默认是true
    //        urlConn.setUseCaches(true);
    //        // 设置为Post请求
    //        urlConn.setRequestMethod("GET");
    //        //urlConn设置请求头信息
    //        //设置请求中的媒体类型信息。
    //        urlConn.setRequestProperty("Content-Type", "application/json");
    //        //设置客户端与服务连接类型
    //        urlConn.addRequestProperty("Connection", "Keep-Alive");
    //        // 开始连接
    //        urlConn.connect();
    //        // 判断请求是否成功
    //        if (urlConn.getResponseCode() == 200) {
    //            // 获取返回的数据
    //            String result = streamToString(urlConn.getInputStream());
    //            if (result==null){
    //                reg();
    //            }else {
    //                Log.e("habit", "Get方式请求成功，result--->" + result);
    //                parseJSONObject(result);
    //            }
    //        } else {
    //            Log.e("habit", "Get方式请求失败");
    //
    //        }
    //        // 关闭连接
    //        urlConn.disconnect();
    //    } catch (Exception e) {
    //        Log.e("habit", e.toString());
    //    }
    //    mHandler.sendEmptyMessage(3);
    //}

    //protected void parseJSONObject (String jsonData)  {
    //
    //    try {
    //        JSONObject jsonobjiect =   new JSONObject(jsonData);
    //        int code = jsonobjiect.getInt("code");
    //        if(code== 0){
    //            JSONArray data = jsonobjiect.getJSONArray("data");
    //            for(int i =0 ; i<data.length();i++){
    //                HabitBean map =new HabitBean();
    //                JSONObject json  = data.getJSONObject(i);
    //                String title =json.getString("title");
    //                String playTime = json.getString("playTime");
    //                int state = json.getInt("state");
    //                int playState = json.getInt("playState");
    //                map.setTitle(title);
    //                map.setPlayTime(playTime);
    //                map.setState(state);
    //                map.setPlayState(playState);
    //                mList.add(map);
    //            }
    //            mHandler.sendEmptyMessage(1);
    //        }
    //    }catch (Exception e){
    //        e.printStackTrace();
    //    }
    //}

    //Handler mHandler =new Handler(){
    //    @Override public void handleMessage(Message msg) {
    //        super.handleMessage(msg);
    //        switch (msg.what){
    //            case 1:
    //                mAdapter.notifyDataSetChanged();
    //                break;
    //            case 2:
    //
    //                break;
    //            case 3 :
    //                mLoadDialog.dismiss();
    //                break;
    //        }
    //    }
    //};

    @Override public void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout 设置代理
        mLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        //// 设置下拉刷新和上拉加载更多的风格
        mLayout.setRefreshViewHolder(refreshViewHolder);
        //// 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        //// 设置正在加载更多时不显示加载更多控件
        //// mRefreshLayout.setIsShowLoadingMoreView(false);
        //// 设置正在加载更多时的文本
        //mLayout.setLoadingMoreText(loadingMoreText);
        //// 设置整个加载更多控件的背景颜色资源 id
        //refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
        //// 设置整个加载更多控件的背景 drawable 资源 id
        //refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        //// 设置下拉刷新控件的背景颜色资源 id
        //refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        //// 设置下拉刷新控件的背景 drawable 资源 id
        //refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        //// 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        //mLayout.setCustomHeaderView(mBanner, false);
        //// 可选配置  -------------END
    }

    @Override public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mLoginPresenter.habitMdel();
    }

    @Override public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        return false;
    }

    @Override public void shutDialg() {

        mHandler.sendEmptyMessage(1);
    }

    Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mLayout.endRefreshing();
                    break;
            }
        }
    };
    //public void beginRefreshing() {
    //    mLayout.beginRefreshing();
    //}
    //
    //// 通过代码方式控制进入加载更多状态
    //public void beginLoadingMore() {
    //    mLayout.beginLoadingMore();
    //}
}