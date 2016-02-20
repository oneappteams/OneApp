package mind.com.oneapp.TabFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

import mind.com.oneapp.Activity.HomeActivity;
import mind.com.oneapp.Activity.MainActivity;
import mind.com.oneapp.AppConstants.Utils;
import mind.com.oneapp.Framework.ActivityCommunicator;
import mind.com.oneapp.Framework.CustomViewPager;
import mind.com.oneapp.Framework.ObservableWebView;
import mind.com.oneapp.R;

/**
 * Created by Idris on 23/12/15.
 */


public class FourthTabFragment extends Fragment {

    ObservableWebView webViewPast;
    ObservableWebView webViewPresent;
    private ProgressBar spinner;
    private ProgressBar progress;
    private static String sessionUrl = null;
    private static String homeUrl = null;
    public static Activity activity;
    public static String tabTitle;
    public boolean onBackClicked = false;
    RelativeLayout topLayout;
    CustomViewPager mPager;
    LinearLayout tapRetryLinear;
    private ActivityCommunicator activityCommunicator;
    public static FourthTabFragment newInstance(String contentUrl, Activity screen, String homePageUrl, String tabName) {
        FourthTabFragment fragment = new FourthTabFragment();
        activity = screen;
        sessionUrl = contentUrl;
        homeUrl = homePageUrl;
        tabTitle = tabName;
        return fragment;
    }

    public void onKeyPressDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                try {
                    if ( null != activity && Utils.isNetworkAvailable(activity)) {
                        tapRetryLinear.setVisibility(View.GONE);
                        onBackClicked = true;
                        String current = "" + webViewPresent.getUrl();
                        SharedPreferences sharedpreferences = activity.getSharedPreferences(
                                MainActivity.My_preference, Context.MODE_PRIVATE);
                        if (!current.equalsIgnoreCase("null")) {
                            if(Utils.isHomeUrl(current)){
                                webViewPresent.clearHistory();
                                webViewPast.clearHistory();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(tabTitle.toLowerCase(), "");
                                editor.commit();
                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);
                                activity.finish();
                                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                                return;
                            }

                        }
                        current = "" + webViewPast.getUrl();
                        if (!current.equalsIgnoreCase("null")) {
                            if(Utils.isHomeUrl(current)){
                                webViewPresent.clearHistory();
                                webViewPast.clearHistory();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(tabTitle.toLowerCase(), "");
                                editor.commit();
                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);
                                activity.finish();
                                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                                return;
                            }

                        }
                        if (webViewPresent.canGoBack()) {
                            webViewPresent.goBack();
                            webViewPresent.setVisibility(View.VISIBLE);
                            webViewPast.setVisibility(View.GONE);
                        } else {
                            webViewPresent.clearHistory();

                            String urlTotal = sharedpreferences.getString(tabTitle.toLowerCase(), "");
                            List<String> urlList = Arrays.asList(urlTotal.split("_tokenizer_url:::search_app_"));
                            if (urlList != null && urlList.size() > 1 && !urlList.get(0).equals("")) {
                                String urlLastNo = "";
                                for (int i = 0; i < urlList.size(); i++) {
                                    String s = urlList.get(i);
                                    if (i <= (urlList.size() - 3))
                                        urlLastNo += s + "_tokenizer_url:::search_app_";
                                    else if (i == (urlList.size() - 2))
                                        urlLastNo += s;
                                }
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(tabTitle.toLowerCase(), urlLastNo);
                                editor.commit();
                                if(Utils.isHomeUrl(current)){
                                    editor.putString(tabTitle.toLowerCase(), "");
                                    editor.commit();
                                    webViewPresent.clearHistory();
                                    webViewPast.clearHistory();
                                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                } else {
                                    webViewPresent.setVisibility(View.GONE);
                                    webViewPast.setVisibility(View.VISIBLE);
                                    if (current.equalsIgnoreCase(urlList.get(urlList.size() - 1)))
                                        webViewPast.loadUrl(urlList.get(urlList.size() - 2));
                                    else
                                        webViewPast.loadUrl(urlList.get(urlList.size() - 1));
                                    // clearHistory = true;

                                }
                            } else if (urlList != null && urlList.size() > 0 && !urlList.get(0).equals("")) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(tabTitle.toLowerCase(), "");
                                editor.commit();
                                webViewPresent.clearHistory();
                                webViewPast.clearHistory();
                                if(Utils.isHomeUrl(current)){
                                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                } else {
                                    webViewPresent.setVisibility(View.GONE);
                                    webViewPast.setVisibility(View.VISIBLE);
                                    webViewPast.loadUrl(homeUrl);

                                }
                            } else      if(Utils.isHomeUrl(current)){
                                {
                                    webViewPresent.clearHistory();
                                    webViewPast.clearHistory();
                                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                                    startActivity(intent);
                                    activity.finish();

                                }
                            } else {
                                webViewPresent.clearHistory();
                                webViewPast.clearHistory();
                                webViewPresent.setVisibility(View.GONE);
                                webViewPast.setVisibility(View.VISIBLE);
                                webViewPast.loadUrl(homeUrl);

                            }
                        }
                    } else {
                        tapRetryLinear.setVisibility(View.VISIBLE);
                    }
                }catch(Exception e){

                }
        }
    }

    @Override
    public void onStop() {
        Log.e("DEBUG", "onStop of SecHomeFragment");
        super.onStop();
        if(null != activity) {
            SharedPreferences sharedpreferences = activity.getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String historyUrl = "";
            WebBackForwardList mWebBackForwardList = webViewPresent.copyBackForwardList();


            String urlTotal = sharedpreferences.getString(tabTitle.toLowerCase(), "");
            for (int i = 0; i < mWebBackForwardList.getSize(); i++) {
                historyUrl = mWebBackForwardList.getItemAtIndex(i).getUrl();


                if (urlTotal.equals("")) {
                    urlTotal = historyUrl;
                } else {
                    urlTotal += "_tokenizer_url:::search_app_" + historyUrl;
                }


            }
            editor.putString(tabTitle.toLowerCase(), urlTotal);
            editor.commit();
            webViewPresent.clearHistory();
        }



    }

    public void startHomePage(){
        MainActivity.homeClickedFourthTab = false;
        if(null != activity) {
            SharedPreferences sharedpreferences = activity.getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(tabTitle.toLowerCase(), "");
            editor.commit();
        }
        if( isMenuVisible() && activity !=null) {
            if (Utils.isNetworkAvailable(activity)) {
                tapRetryLinear.setVisibility(View.GONE);
                webViewPresent.setVisibility(View.VISIBLE);
                webViewPast.setVisibility(View.GONE);
                webViewPresent.loadUrl(homeUrl);
                webViewPresent.clearHistory();
                webViewPast.clearHistory();
            } else {
                tapRetryLinear.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // context = activity;
    }

    public boolean _areLecturesLoaded = false;
    public boolean activityLoaded = false;

    @Override
    public void setMenuVisibility(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        try{
        if (!isVisibleToUser) {

            try
            {
                if (webViewPast != null)
                {
                    webViewPast.onPause();
                }
                if (webViewPresent != null)
                {
                    webViewPresent.onPause();
                }
            }
            catch (Exception e)
            {}

        }
        else {
            if (activity != null && Utils.isNetworkAvailable(activity)) {
                if(tapRetryLinear!=null){
                    tapRetryLinear.setVisibility(View.GONE);
                }
                try {
                    if (webViewPresent != null) {
                        webViewPresent.onResume();
                    }

                    if (webViewPast != null) {
                        webViewPast.onResume();
                    }
                } catch (Exception e) {
                }

                if (isVisibleToUser && !_areLecturesLoaded && activityLoaded) {
                    _areLecturesLoaded = true;
                    activityLoaded = false;
                    SharedPreferences sharedpreferences = activity.getSharedPreferences(
                            MainActivity.My_preference, Context.MODE_PRIVATE);
                    String urlTotal = sharedpreferences.getString(tabTitle.toLowerCase(), "");
                    List<String> urlList = Arrays.asList(urlTotal.split("_tokenizer_url:::search_app_"));
                    if (MainActivity.homeClickedFourthTab) {
                        startHomePage();
                    } else {
                        if (MainActivity.searchClickedFourthTab) {
                            MainActivity.searchClickedFourthTab = false;
                            webViewPresent.setVisibility(View.VISIBLE);
                            webViewPast.setVisibility(View.GONE);
                            webViewPresent.loadUrl(sessionUrl);
                        } else {
                            if (urlList != null && urlList.size() > 0 && !urlList.get(0).equals("")) {
                                webViewPresent.setVisibility(View.GONE);
                                webViewPast.setVisibility(View.VISIBLE);
                                webViewPast.loadUrl(urlList.get(urlList.size() - 1));
                            } else {
                                webViewPresent.setVisibility(View.VISIBLE);
                                webViewPast.setVisibility(View.GONE);
                                webViewPresent.loadUrl(sessionUrl);

                            }
                        }
                    }
                }
            } else if(tapRetryLinear != null){
                tapRetryLinear.setVisibility(View.VISIBLE);
            }
        }
    }
    catch (Exception e)
    {}

    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            if (webViewPast != null)
            {
                webViewPast.onPause();
            }
            if (webViewPresent != null)
            {
                webViewPresent.onPause();
            }
        }
        catch (Exception e)
        {}
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            if(Utils.isNetworkAvailable(activity)) {
                tapRetryLinear.setVisibility(View.GONE);
                if (webViewPresent != null) {
                    webViewPresent.onResume();
                }

                if (webViewPast != null) {
                    webViewPast.onResume();
                }
            }else{
                tapRetryLinear.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        activityLoaded = true;
        activityCommunicator =(ActivityCommunicator)activity;
        if(!_areLecturesLoaded && isMenuVisible()) {
            setMenuVisibility(true);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.first_tab_fragment, null);
        if(activity != null) {
            spinner = (ProgressBar) v.findViewById(R.id.progressBar);
            spinner.setVisibility(View.VISIBLE);
            progress = (ProgressBar) v.findViewById(R.id.progressBarHorizontal);
            progress.setMax(100);
            progress.getProgressDrawable().setColorFilter(activity.getResources().getColor(R.color.colorPrimaryOrange), PorterDuff.Mode.SRC_IN);
            topLayout = (RelativeLayout) activity.findViewById(R.id.top_layout);
            mPager = (CustomViewPager) activity.findViewById(R.id.pager);
            tapRetryLinear = (LinearLayout) v.findViewById(R.id.tapRetryLinear);
            webViewPresent = (ObservableWebView) v.findViewById(R.id.webPresent);
            WebSettings webSettingViewPresent = webViewPresent.getSettings();
            webSettingViewPresent.setUseWideViewPort(true);

            webViewPresent.setWebViewClient(new WebViewClient());
            webSettingViewPresent.setSavePassword(true);
            webSettingViewPresent.setGeolocationDatabasePath(activity.getFilesDir().getPath());
            webSettingViewPresent.setPluginState(WebSettings.PluginState.ON);
            webSettingViewPresent.setSupportZoom(true);       //Zoom Control on web (You don't need this
            webSettingViewPresent.setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
            webSettingViewPresent.setJavaScriptCanOpenWindowsAutomatically(true);


            webSettingViewPresent.setJavaScriptEnabled(true);
            webSettingViewPresent.setDomStorageEnabled(true);
            webSettingViewPresent.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettingViewPresent.setAppCacheEnabled(true);
            webSettingViewPresent.setDatabaseEnabled(true);

            webSettingViewPresent.setJavaScriptEnabled(true);
            webSettingViewPresent.setGeolocationEnabled(true);
            webViewPresent.setWebChromeClient(new GeoWebChromeClient());
            webSettingViewPresent.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettingViewPresent.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettingViewPresent.setAppCacheEnabled(true);
            webViewPresent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webSettingViewPresent.setDomStorageEnabled(true);
            webSettingViewPresent.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettingViewPresent.setUseWideViewPort(true);
            webSettingViewPresent.setSavePassword(true);
            webSettingViewPresent.setSaveFormData(true);
            webSettingViewPresent.setEnableSmoothTransition(true);


            webViewPast = (ObservableWebView) v.findViewById(R.id.webPast);
            WebSettings webSettingViewPast = webViewPast.getSettings();
            webSettingViewPast.setUseWideViewPort(true);

            webViewPast.setWebViewClient(new WebViewClient());
            webSettingViewPast.setSavePassword(true);
            webSettingViewPast.setGeolocationDatabasePath(activity.getFilesDir().getPath());
            webSettingViewPast.setPluginState(WebSettings.PluginState.ON);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            webSettingViewPast.setSupportZoom(false);       //Zoom Control on web (You don't need this
            webSettingViewPast.setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
            webSettingViewPast.setJavaScriptCanOpenWindowsAutomatically(true);


            webSettingViewPast.setJavaScriptEnabled(true);
            webSettingViewPast.setDomStorageEnabled(true);
            webSettingViewPast.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettingViewPast.setAppCacheEnabled(true);
            webSettingViewPast.setDatabaseEnabled(true);

            webSettingViewPast.setJavaScriptEnabled(true);
            webSettingViewPast.setGeolocationEnabled(true);
            webViewPast.setWebChromeClient(new GeoWebChromeClient());
            webSettingViewPast.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettingViewPast.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettingViewPast.setAppCacheEnabled(true);
            webViewPast.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webSettingViewPast.setDomStorageEnabled(true);
            webSettingViewPast.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettingViewPast.setUseWideViewPort(true);
            webSettingViewPast.setSavePassword(true);
            webSettingViewPast.setSaveFormData(true);
            webSettingViewPast.setEnableSmoothTransition(true);

           /* final RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.top_layout);
            ViewTreeObserver vto = layout.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    height = layout.getMeasuredHeight();

                }
            });
            webViewPast.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
                @Override
                public void onScroll(int l, int t) {
                    Utils.setTopLayoutHeight(t, topLayout, mPager, height);
                }
            });
            webViewPresent.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
                @Override
                public void onScroll(int l, int t) {
                    Utils.setTopLayoutHeight(t, topLayout, mPager, height);
                }
            });
            tapRetryLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setMenuVisibility(true);
                }
            });*/

            webViewPast.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    sessionUrl = url;
                    onResume();
                    spinner.setVisibility(View.VISIBLE);
                    activityCommunicator.passDataToActivity(tabTitle, url);
                    System.out.println("your current url when webpage loading.." + url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String current = url;
                    spinner.setVisibility(View.GONE);
                    activityCommunicator.passDataToActivity(tabTitle, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!Utils.isNetworkAvailable(activity)) {
                        tapRetryLinear.setVisibility(View.VISIBLE);
                        return true;
                    }
                    if (!url.startsWith("http")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                    webViewPresent.setVisibility(View.VISIBLE);
                    webViewPast.setVisibility(View.GONE);
                    webViewPresent.loadUrl(url);
                    return false;
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    tapRetryLinear.setVisibility(View.VISIBLE);
                }


            });

            webViewPresent.setWebViewClient(new WebViewClient() {


                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    onResume();
                    spinner.setVisibility(View.VISIBLE);
                    activityCommunicator.passDataToActivity(tabTitle, url);
                    System.out.println("your current url when webpage loading.." + url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    spinner.setVisibility(View.GONE);
                    activityCommunicator.passDataToActivity(tabTitle, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!Utils.isNetworkAvailable(activity)) {
                        tapRetryLinear.setVisibility(View.VISIBLE);
                        return true;
                    }
                    if (!url.startsWith("http")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }

                    return false;
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    tapRetryLinear.setVisibility(View.VISIBLE);
                }

            });
        }

        return v;

    }

    private  int height = 0;

    public class GeoWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            callback.invoke(origin, true, false);
        }

        @Override
        public void onProgressChanged(WebView view, int pro) {
            progress.setProgress(pro);
            if (pro > 30) {
                spinner.setVisibility(View.GONE);
            }
            if (pro >= 100) {
                progress.setVisibility(View.GONE);
            }
            else{
                progress.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, pro);
        }
    }

    public void shaerUrl(){
        if(webViewPresent.isShown() && webViewPresent.getUrl()!=null){
            activityCommunicator.passDataToActivity(tabTitle, webViewPresent.getUrl());
        }else if(webViewPast.isShown() && webViewPast.getUrl()!=null){
            activityCommunicator.passDataToActivity(tabTitle, webViewPast.getUrl());
        }
    }
}