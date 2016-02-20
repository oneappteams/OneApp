package mind.com.oneapp.Log;

import android.content.Context;
import android.util.Log;

import com.flurry.android.FlurryAgent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import mind.com.oneapp.BuildConfig;
import mind.com.oneapp.AppConstants.Utils;

public class Flog
{
	public static void fv(Context cx, String eventId, String msg)
	{
		v(msg);
	}
	
	public static void fi(Context cx, String eventId, String msg)
	{
		try
		{
			i(cx.getClass().getCanonicalName() + " : " + eventId + " : " + msg);
			//
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("class", cx.getClass().getCanonicalName());
			parameters.put("info-msg", msg);
			//
			FlurryAgent.logEvent(eventId, parameters);
		}
		catch (Exception e)
		{
			i(msg);
		}
	}
	
	public static void fi(Context cx, String eventId, Map<String, String> parameters)
	{
		try
		{
			for (String key : parameters.keySet())
				i(cx.getClass().getCanonicalName() + " : " + eventId + " : " + key + " : " + parameters.get(key));
			FlurryAgent.logEvent(eventId, parameters);
		}
		catch (Exception e)
		{
			for (String key : parameters.keySet())
				i(key + " : " + parameters.get(key));
		}
	}
	
	public static void fw(Context cx, String eventId, String msg)
	{
		try
		{
			w(cx.getClass().getCanonicalName() + " : " + eventId + " : " + msg);
		}
		catch (Exception e)
		{
			w(msg);
		}
	}
	
	public static void fe(Context cx, String eventId, String msg)
	{
		try
		{
			e(cx.getClass().getCanonicalName() + " : " + eventId + " : " + msg);
		}
		catch (Exception e)
		{
			e(msg);
		}
	}
	
	public static void fe(Context cx, String eventId, Throwable tr)
	{
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			tr.printStackTrace(pw);
			//
			sw.close();
			pw.close();
			//
			fe(cx, eventId, sw.toString());
		}
		catch (Exception e)
		{
			e(tr);
		}
	}
	
	public static void fwtf(Context cx, String msg)
	{
		try
		{
			wtf(cx.getClass().getCanonicalName() + " : WTF : " + msg);
		}
		catch (Exception e)
		{
			wtf(msg);
		}
	}
	
	public static void fwtf(Context cx, Throwable tr)
	{
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			tr.printStackTrace(pw);
			//
			sw.close();
			pw.close();
			//
			fwtf(cx, sw.toString());
		}
		catch (Exception e)
		{
			wtf(tr);
		}
	}
	
	public static void v(String msg)
	{
		if (!BuildConfig.DEBUG && !Utils.isDebug) return;
		Log.v(Utils.TAG, msg);
		// logToFile("VERBOSE", msg, null);
	}
	
	private static void i(String msg)
	{
		if (!BuildConfig.DEBUG  && !Utils.isDebug) return;
		Log.i(Utils.TAG, msg);
		// logToFile("INFO", msg, null);
	}
	
	private static void w(String msg)
	{
		if (!BuildConfig.DEBUG && !Utils.isDebug) return;
		Log.w(Utils.TAG, msg);
		// logToFile("WARNING", msg, null);
	}
	
	private static void e(String msg)
	{
		Log.e(Utils.TAG, msg);
		// logToFile("EXCEPTION", msg, null);
	}
	
	private static void e(Throwable tr)
	{
		Log.e(Utils.TAG, tr.getMessage(), tr);
		// logToFile("EXCEPTION", tr.toString(), tr);
	}
	
	private static void wtf(String msg)
	{
		Log.println(Log.ASSERT, Utils.TAG, msg);
		// logToFile("WTF", msg, null);
	}
	
	private static void wtf(Throwable tr)
	{
		Log.println(Log.ASSERT, Utils.TAG, tr.toString());
		// logToFile("WTF", tr.toString(), tr);
	}
	

}
