package com.appazal.dondoff;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			toggleMobileData(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finish();
	}

	private void toggleMobileData(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final Class conmanClass = Class.forName(conman.getClass().getName());
	    final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
	    connectivityManagerField.setAccessible(true);
	    final Object connectivityManager = connectivityManagerField.get(conman);
	    final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
	    final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    final Method getMobileDataStateMethod = connectivityManagerClass.getDeclaredMethod("getMobileDataEnabled");
	    setMobileDataEnabledMethod.setAccessible(true);
	    getMobileDataStateMethod.setAccessible(true);
	    
	    if((Boolean)getMobileDataStateMethod.invoke(connectivityManager)) {
		    setMobileDataEnabledMethod.invoke(connectivityManager, false);
	    	Toast.makeText(context, "Data OFF", Toast.LENGTH_SHORT).show();
	    } else {
		    setMobileDataEnabledMethod.invoke(connectivityManager, true);
	    	Toast.makeText(context, "Data ON", Toast.LENGTH_SHORT).show();
	    }
	}
}
