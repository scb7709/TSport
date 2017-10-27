package me.lam.maidong.ClenderUtil;

import android.app.Activity;


/**
 * 该类是视图控制类的基本类
 * 
 */
public abstract class TViewControllerBase{
	public ACache aCache;// 缓存框架
	public Activity mActivity;

	String code;
	public TViewControllerBase(Activity activity,String code) {
		this.mActivity = activity;
	/*	this.aCache = ACache.get(mActivity);*/

		initObject();
	}

	public abstract void initObject();

	public abstract void requestData();

	/*public void saveOrderNumber(Object obj, String key) {
		SaveData saveData = new SaveData();
		saveData.setTime(TimeUtil.getCurrentTime());
		saveData.setObj(obj);

		aCache.put(key, saveData);
	}*/

	/*public SaveData obtainOrderNumber(String key) {
		return (SaveData) aCache.getAsObject(key);
	}*/
	
	/*public boolean isNeedLogin() {
		SaveData data = obtainOrderNumber(preferences.PREFERENCE_LOGIN);
		if(data == null){
			return true;
		}
		long loginTime = data.getTime();
		if (loginTime == 0L) {
			return true;
		} else {
			long currentTime = TimeUtil.getCurrentTime();
			long time_space = 72 * 60 * 60 * 1000;
			if ((currentTime - loginTime) > time_space) {
				return true;
			} else {
				return false;
			}
		}
	}*/
}