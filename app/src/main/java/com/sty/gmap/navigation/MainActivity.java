package com.sty.gmap.navigation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sty.gmap.navigation.util.MapUtils;
import com.sty.gmap.navigation.util.PermissionUtils;
import com.sty.gmap.navigation.widget.ActionSheet;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private double dstLongitude = 112.6151311400;
    private double dstLatitude = 35.0777482200;
    private static final String ADDRESS = "河南省济源第一中学";

    private ActionSheet mActionSheet;
    /**
     * 需要进行检测的权限数组
     */
    private String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        initView();
    }

    private void initView() {
        Button btnGaoDeNavigate = findViewById(R.id.btn_gao_de_navigate);
        btnGaoDeNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.checkPermissions(MainActivity.this, needPermissions)) {
                    gotoNaviActivity();
                } else {
                    PermissionUtils.requestPermissions(MainActivity.this, needPermissions);
                }
            }
        });

        Button btnOutLinkNavigate = findViewById(R.id.btn_out_link_navigate);
        btnOutLinkNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    private void gotoNaviActivity() {
        startActivity(new Intent(MainActivity.this, TrunkRouteCalculateActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        Log.e("sty", "onRequestPermissionsResult");
        if (requestCode == PermissionUtils.REQUEST_PERMISSIONS_CODE) {
            if (!PermissionUtils.verifyPermissions(paramArrayOfInt)) {
                PermissionUtils.showMissingPermissionDialog(this);
            } else {
                gotoNaviActivity();
            }
        }
    }


    private void showBottomDialog() {
        mActionSheet = new ActionSheet.DialogBuilder(this)
                .addSheet("高德地图", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if (MapUtils.isGdMapInstalled()) {
                            mActionSheet.dismiss();
                            MapUtils.openGaoDeNavi(mContext,0, 0, null, dstLatitude, dstLongitude, "河南省济源第一中学");
                        } else {
                            Toast.makeText(mContext, "请先安装高德地图", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addSheet("腾讯地图", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if (MapUtils.isTencentMapInstalled()) {
                            mActionSheet.dismiss();
                            MapUtils.openTencentMap(mContext,0, 0, null, dstLatitude, dstLongitude, "河南省济源第一中学");
                        } else {
                            Toast.makeText(mContext, "请先安装腾讯地图", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addSheet("百度地图", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if (MapUtils.isBaiduMapInstalled()) {
                            mActionSheet.dismiss();
                            //经测试不经转换的坐标反而是正确的
                            double[] lot_lat = MapUtils.gaoDeToBaidu(dstLongitude, dstLatitude);
//                    double longitude = lot_lat[0];
//                    double latitude = lot_lat[1];
                            double longitude = dstLongitude;
                            double latitude = dstLatitude;
                            Log.i("sty", "latitude: " + latitude + "   longitude: " + longitude);
                            MapUtils.openBaiDuNavi(mContext,0, 0, null, dstLatitude, dstLongitude, "河南省济源第一中学");
                        } else {
                            Toast.makeText(mContext, "请先安装百度地图", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActionSheet.dismiss();
                    }
                })
                .create();
    }
}
