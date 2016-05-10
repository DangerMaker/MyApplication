package com.example.administrator.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.adapter.MyBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by shand on 2016/5/10.
 */
public class LocationActivity extends BackBaseActivity implements PoiSearch.OnPoiSearchListener {

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    //获取纬度
                    latitude = aMapLocation.getLatitude();
                    //获取经度
                    longitude = aMapLocation.getLongitude();
                    aMapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);//定位时间
                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    String country = aMapLocation.getCountry();//国家信息
//                    String province = aMapLocation.getProvince();//省信息
//                    String city = aMapLocation.getCity();//城市信息
//                    String district = aMapLocation.getDistrict();//城区信息
                    //街道信息
                    street = aMapLocation.getStreet();
//                    String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
//                    String detail = aMapLocation.getLocationDetail();//定位信息描述
                    //城市编码
                    cityCode = aMapLocation.getCityCode();
//                    aMapLocation.getAdCode();//地区编码
                    queryLocation( street,cityCode);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("Map", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    @Bind(R.id.list)
    ListView list;

    private double latitude;
    private double longitude;
    private String cityCode;
    private String street;
    private PoiSearch poiSearch;
    private boolean state = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setCustomTitle("定位");
        initLocation();
    }

    private void queryLocation(String keyWord,String cityCode) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "风景名胜|道路附属设施|公司企业", cityCode);
        // keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        // 设置每页最多返回多少条poiitem
        query.setPageSize(100);
        //设置查第一页
        query.setPageNum(1);
        poiSearch = new PoiSearch(this, query);
        //设置周边搜索的中心点以及区域
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000));
        //设置数据返回的监听器
        poiSearch.setOnPoiSearchListener(this);
        //开始搜索
        poiSearch.searchPOIAsyn();
    }


    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//        退出时是否杀死service;
//        mLocationOption.setKillProcess(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mLocationClient.startLocation();//启动定位
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();//停止定位
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端
    }

    public void onPoiSearched(PoiResult result, int rCode) {
        //在回调函数中解析result获取POI信息
        //result.getPois()可以获取到PoiItem列表，Poi详细信息可参考PoiItem类
        //若当前城市查询不到所需Poi信息，可以通过result.getSearchSuggestionCitys()获取当前Poi搜索的建议城市
        //如果搜索关键字明显为误输入，则可通过result.getSearchSuggestionKeywords()方法得到搜索关键词建议
        //返回结果成功或者失败的响应码。1000为成功，其他为失败（详细信息参见网站开发指南-错误码对照表)
        final ArrayList<PoiItem> poiItems = result.getPois();
        LocationAdapter adapter = new LocationAdapter(this);
        if(poiItems != null && poiItems.size() != 0) {
            state = true;
            adapter.updateItems(poiItems);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("title",poiItems.get(position).getTitle());
                    setResult(3,intent);
                    finish();
                }
            });
        }else {
            state = false;
            final List<String> datas =  result.getSearchSuggestionKeywords();
            adapter.updateItems(datas);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("title",datas.get(position));
                    setResult(3,intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }


    class LocationAdapter extends MyBaseAdapter{

        public LocationAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(LocationActivity.this,R.layout.item_location_list,null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.distance = (TextView) convertView.findViewById(R.id.distance);
                holder.cityName = (TextView) convertView.findViewById(R.id.cityName);
                holder.businessArea = (TextView) convertView.findViewById(R.id.businessArea);
                holder.snippet = (TextView) convertView.findViewById(R.id.snippet);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(state){
                PoiItem item = (PoiItem) getItem(position);
                holder.title.setText(item.getTitle());
                holder.distance.setText("相聚"+item.getDistance()+"米");
                holder.cityName.setText(item.getCityName());
                holder.businessArea.setText(item.getBusinessArea());
                holder.snippet.setText(item.getSnippet());
            }else {
                String item = (String) getItem(position);
                holder.title.setText(item);
            }

            return convertView;
        }
    }

    class ViewHolder{
        public TextView title;
        public TextView distance;
        public TextView cityName;
        public TextView businessArea;
        public TextView snippet;
    }
}
