package com.sty.gmap.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.sty.gmap.navigation.util.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Shi Tianyi on 2020/2/2/0002.
 */
public class BaseRouteCalculateActivity extends AppCompatActivity implements AMapNaviListener,
        AMapNaviViewListener {
    private static final String TAG = BaseRouteCalculateActivity.class.getSimpleName();
    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;
    protected NaviLatLng mEndLatlng = new NaviLatLng(40.084894,116.603039);
    protected NaviLatLng mStartLatlng = new NaviLatLng(39.825934,116.342972);
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    protected List<NaviLatLng> mWayPointList = new ArrayList<NaviLatLng>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    }

    private void initView() {
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setUseInnerVoice(true);

        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mAMapNaviView != null) {
            mAMapNaviView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAMapNaviView != null) {
            mAMapNaviView.onPause();
        }

        //停止导航后，会触及底层stop，然后就不会再有回调了
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        if(mAMapNavi != null) {
            mAMapNavi.stopNavi();
            mAMapNavi.destroy();
        }
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功
        Log.i(TAG, "初始化成功");
    }

    @Override
    public void onStartNavi(int i) {
        //开始导航回调
        Log.i(TAG, "开始导航回调");
    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
    }

    @Override
    public void onGetNavigationText(int i, String s) {
        //播报类型和播报文字回调
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e(TAG, "--------------------------------------------");
        Log.i(TAG, "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i(TAG, "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e(TAG, "--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int i) {
        //到达途经点
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //多路径算路成功回调
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int naviMode) {
        //导航态车头模式，0：车头朝上状态；1：正北朝上模式
    }

    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }

    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Override
    public void onLockMap(boolean b) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d(TAG, "导航页面加载成功");
        Log.d(TAG, "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }
}
