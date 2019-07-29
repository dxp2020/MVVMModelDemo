package com.mula.lbsloc;

import android.app.Notification;
import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mula.base.delegate.LocationDelegate;
import com.mula.base.tools.L;
import com.mula.base.tools.date.DateUtils;
import com.mula.base.tools.map.LatLng;
import com.mula.base.tools.map.LocationUtils;

import de.greenrobot.event.EventBus;

public class LocationService implements LocationDelegate {
	private final static long locationTimeOutTimeMill = 3000L;//定位超时时间
	private LocationClient client = null;
	private LocationClientOption mOption,DIYoption;
	private Object  objLock = new Object();
	private boolean isSuccess = false;
	private boolean isGoogleMap = false;
	private long startLocationTimemill;
	private BDLocation location;


	public LocationService(Context locationContext){
		init(locationContext);
	}

	public LocationService(Context locationContext,Boolean isGoogleMap){
		init(locationContext);
		this.isGoogleMap = isGoogleMap;
	}

	private void init(Context locationContext){
		synchronized (objLock) {
			if(client == null){
				client = new LocationClient(locationContext);
				client.setLocOption(getDefaultLocationClientOption());
				registerListener(mListener);
			}
		}
	}

	private void registerListener(BDAbstractLocationListener listener){
		if(listener != null){
			client.registerLocationListener(listener);
			isSuccess = true;
		}
	}

	private void unregisterListener(BDAbstractLocationListener listener){
		if(listener != null){
			client.unRegisterLocationListener(listener);
			isSuccess = false;
		}
	}

	/***
	 *
	 * @param option 定位配置参数
	 * @return isSuccessSetOption
	 */
	public boolean setLocationOption(LocationClientOption option){
		boolean isSuccess = false;
		if(option != null){
			if(client.isStarted())
				client.stop();
			DIYoption = option;
			client.setLocOption(option);
		}
		return isSuccess;
	}

	/***
	 *
	 * @return DefaultLocationClientOption  默认O设置
	 */
	public LocationClientOption getDefaultLocationClientOption(){
		if(mOption == null){
			mOption = new LocationClientOption();
			mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
			mOption.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
			mOption.setOpenGps(true);
			mOption.setScanSpan(2000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
			mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
			mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
			mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
			mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
			mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
			mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
			mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
			mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集

			mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
		}
		return mOption;
	}

	/**
	 *
	 * @return DIYOption 自定义Option设置
	 */
	public LocationClientOption getOption(){
		if(DIYoption == null) {
			DIYoption = new LocationClientOption();
		}
		return DIYoption;
	}

	public void start(){
		synchronized (objLock) {
			if(client != null && !client.isStarted()){
				client.start();
				startLocationTimemill = System.currentTimeMillis();
			}
			if(!isSuccess){
				registerListener(mListener);
			}
		}
	}

	public void stop(){
		synchronized (objLock) {
			if(client != null && client.isStarted()){
				client.stopIndoorMode();
				client.stop();
				startLocationTimemill = 0;
			}
			if(isSuccess){
				unregisterListener(mListener);
			}
		}
	}

	public boolean isObtainLocationTimeOut(){
		if(startLocationTimemill==0){
			return true;
		} else
			return (System.currentTimeMillis() - startLocationTimemill) > locationTimeOutTimeMill;
	}

	public long getLocationTimeOutTimeMill(){
		return locationTimeOutTimeMill;
	}

	@Override
	public void enableLocInForeground(Notification notification) {
		if(client != null){
			client.enableLocInForeground(1002,notification);
		}
	}

	@Override
	public void disableLocInForeground() {
		if(client != null){
			client.disableLocInForeground(true);
		}
	}

	public boolean requestHotSpotState(){
		return client.requestHotSpotState();
	}

	public Location getLocation() {
		if (location!=null) {
			Location mLocation = new Location(getLocationProvider());
			mLocation.setBearing(location.getDirection());
			mLocation.setLatitude(location.getLatitude());
			mLocation.setLongitude(location.getLongitude());
			mLocation.setAccuracy(location.getRadius());
			mLocation.setSpeed(location.getSpeed());
			return mLocation;
		} else {
			return null;
		}
	}

	private String getLocationProvider(){
		if(location.getLocType() == BDLocation.TypeGpsLocation){
			return "GPS";
		}else if(location.getLocType() == BDLocation.TypeNetWorkLocation){
			return "Network";
		}else if(location.getLocType() == BDLocation.TypeOffLineLocation){
			return "Offline";
		}
		return null;
	}

	/*****
	 *
	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				if(location.getFloor()!=null){
					client.startIndoorMode();
				}

				StringBuffer sb = new StringBuffer(256);
				sb.append("定位时间 : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\tlocType : ");// 定位类型
				sb.append(location.getLocType());
				sb.append("\tlocType description : ");// *****对应的定位类型说明*****
				sb.append(location.getLocTypeDescription());
				sb.append("\tlatitude : ");// 纬度
				sb.append(location.getLatitude());
				sb.append("\tlontitude : ");// 经度
				sb.append(location.getLongitude());

				switch (location.getLocType()){
					case BDLocation.TypeGpsLocation:
					case BDLocation.TypeNetWorkLocation:
					case BDLocation.TypeOffLineLocation:
						if (!isGoogleMap) {
							//在国内进行经纬度转换
							LatLng latLng = LocationUtils.gcj02ToWgs84(new LatLng(location.getLatitude(),location.getLongitude()));
							location.setLatitude(latLng.latitude);
							location.setLongitude(latLng.longitude);
						}
						if (location.getLatitude()!=4.9E-324&&location.getLongitude()!=4.9E-324) {
							startLocationTimemill = System.currentTimeMillis();
						}
						LocationService.this.location = location;
						EventBus.getDefault().post(getLocation());
						break;
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\tgps status : ");
					sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
					sb.append("\n定位类型 : ");
					sb.append("gps定位成功");

				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					sb.append("\n定位类型 : ");
					sb.append("网络定位成功");

				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\n定位类型 : ");
					sb.append("离线定位");

				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\n定位类型 : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\n定位类型 : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");

				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\n定位类型 : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

				}
				sb.append("\t半径 : ");// 半径
				sb.append(location.getRadius());
				sb.append("\tCityCode : "+location.getCityCode());// 城市
				sb.append(" "+location.getAddrStr());
				sb.append("\t是否室内: ");// *****返回用户室内外判断结果*****
				sb.append(location.getFloor() != null?("是："+location.getBuildingName()):"否");
				sb.append("\tlocationdescribe: ");
				sb.append(location.getLocationDescribe()+"\n");// 位置语义化信息
                sb.append("                ");
				L.e(sb.toString());
			}
		}

	};

}
