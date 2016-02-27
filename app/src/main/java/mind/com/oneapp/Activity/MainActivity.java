package mind.com.oneapp.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mind.com.oneapp.Adapters.DropDownListAdapter;
import mind.com.oneapp.AppConstants.Utils;
import mind.com.oneapp.Framework.ActivityCommunicator;
import mind.com.oneapp.Framework.CustomViewPager;
import mind.com.oneapp.Framework.TabPageIndicatorLoc;
import mind.com.oneapp.Log.Flog;
import mind.com.oneapp.R;
import mind.com.oneapp.TabFragments.FifthTabFragment;
import mind.com.oneapp.TabFragments.FirstTabFragment;
import mind.com.oneapp.TabFragments.FourthTabFragment;
import mind.com.oneapp.TabFragments.SecondTabFragment;
import mind.com.oneapp.TabFragments.SixthTabFragment;
import mind.com.oneapp.TabFragments.ThirdTabFragment;


public class MainActivity extends FragmentActivity implements OnPageChangeListener, ActivityCommunicator {
    boolean resend = false;
    SearchAppTabAdapter mAdapter;
    TabPageIndicatorLoc mIndicator;
    TextView appNameTv;
    CustomViewPager mPager;
    ImageView menuView;
    ImageView searchAnim;
    DrawerLayout drawer;
    LinearLayout searchLl;
    public static List<String> CONTENT = new ArrayList<String>();
    public List<String> items = new ArrayList<String>();
    Fragment[] fragments = new Fragment[8];
    String category = "";
    public static final String My_preference = "preference";
    public static final String SESS = "sess_name";
    public static final String NAME = "master_name";
    public static final String MESSAGE = "master_type";

    public static boolean searchClickedFirstTab = false;
    public static boolean searchClickedSecondTab = false;
    public static boolean searchClickedThirdTab = false;
    public static boolean searchClickedFourthTab = false;
    public static boolean searchClickedFifthTab = false;
    public static boolean searchClickedSixthTab = false;
    //
    public static boolean homeClickedFirstTab = false;
    public static boolean homeClickedSecondTab = false;
    public static boolean homeClickedThirdTab = false;
    public static boolean homeClickedFourthTab = false;
    public static boolean homeClickedFifthTab = false;
    public static boolean homeClickedSixthTab = false;
    public EditText search;
    public String currentWeb = "";
    public String contentUrl = "";
    public ListView list;
    public int selectedTab = 1;
    public String tabConent = "";
    public Animation slideIn;
    private ActivityCommunicator activityCommunicator;
    String shareUrl;
    String shareTabTitle;
    public boolean isFromFirstTab = false;
    /*    TimerTask hourlyTask;
        Timer timer = null;
        TimerTask shareTimer = null;*/
    FirstTabFragment firstTabFragment;
    SecondTabFragment secondTabFragment;
    ThirdTabFragment thirdTabFragment;
    FourthTabFragment fourthTabFragment;
    FifthTabFragment fifthTabFragment;
    SixthTabFragment sixthTabFragment;
    private int categoryPosition;
    private String searchUrl = null;
    BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            if (action.equals("SMS")) {
                SharedPreferences sharedpreferences = getSharedPreferences(
                        My_preference, Context.MODE_PRIVATE);
                String sender = "" + sharedpreferences.getString(NAME, null);
                String text = "" + sharedpreferences.getString(MESSAGE, null);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Message from " + sender)
                        .setMessage("" + text)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
                // DO WHATEVER YOU WANT.
                unregisterReceiver(broadcast_reciever);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAdapter = null;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_tab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        search = (EditText) findViewById(R.id.search);

        searchAnim = (ImageView) findViewById(R.id.search_anim);
        searchLl = (LinearLayout) findViewById(R.id.search_ll);
        appNameTv = (TextView) findViewById(R.id.app_name_tv);
        activityCommunicator = (ActivityCommunicator) this;
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBut(search);
                    return true;
                }
                return false;
            }
        });

        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right);
        searchAnim.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                searchAnim.setVisibility(View.GONE);
                appNameTv.setVisibility(View.INVISIBLE);
                searchLl.setAnimation(slideIn);
                searchLl.animate();
                slideIn.start();
                slideIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        searchLl.setVisibility(View.VISIBLE);
                        appNameTv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        int newPos = 0;
        String searchContent = "";
        try {
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    newPos = 0;
                    searchContent = "";
                } else {
                    newPos = extras.getInt("tabPosition");
                    searchContent = extras.getString("tabSearchText");
                    categoryPosition = extras.getInt("tabCategoryPosition");
                    searchUrl = extras.getString("tabSearchUrl");
                }
            } else {
                newPos = (int) savedInstanceState.getSerializable("tabPosition");
                searchContent = (String) savedInstanceState.getSerializable("tabSearchText");
            }
        } catch (Exception e) {

        }
        if (categoryPosition != -1) {
            int tabPos = newPos + 1;
            int catPos = categoryPosition + 1;
            String tab = "tab" + tabPos + "" + catPos;
            SharedPreferences sharedpreferences = getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(tab, "true");
            editor.commit();
        }
        selectedCategoryTab(newPos + 1, null);
        if (!searchContent.equals("")) {
            search.setText(searchContent);
            searchBut(search);
        }
/*        mAdapter = null;
        CONTENT = new ArrayList<String>();
        CONTENT.addAll(Utils.getListSelected1(this));
        items = new ArrayList<String>();
        items.addAll(Utils.getList1());
        selectedTab = 1;
       *//* TextView firstTab = (TextView) findViewById(R.id.tab1);*//*
        tabConent = Utils.totalCategories().size() > 0 ? Utils.totalCategories().get(0) : "Search";


        mAdapter = new SearchAppTabAdapter(getSupportFragmentManager(), false);
        mPager = (CustomViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator = (TabPageIndicatorLoc) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setCurrentItem(0);
        mIndicator.setHorizontalFadingEdgeEnabled(false);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.notifyDataSetChanged();
        appNameTv.setText("One App - " + tabConent);*/
        ImageView createButton = (ImageView) findViewById(R.id.customize);
        createButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                initiatePopUp(items, null);

            }
        });
        //drawer items
        initialize();
        //flury event
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("class", this.getClass().getCanonicalName());
        parameters.put("info-msg", "MainActivity Created");
        Flog.fi(MainActivity.this, Utils.Event.VERBOSE, parameters);
        //
        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Map<String, String> parameterUsers = new HashMap<String, String>();
        parameterUsers.put("class", this.getClass().getCanonicalName());
        parameterUsers.put("info-msg", "User_Id: " + mngr.getDeviceId());
        Flog.fi(this, Utils.Event.USER_ID, parameterUsers);
       /* Timer timer = new Timer();
        hourlyTask = new TimerTask() {
            @Override
            public void run() {
                share(null);
            }
        };

        // schedule share to run every  10 minutes...
        timer.schedule(hourlyTask, 1000 * 60 * 10, 1000 * 60 * 10);   // 1000*60*10 every 10 minutes*/
    }


    private void initialize() {
        final ListView categoryList = (ListView) findViewById(R.id.categoryList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.nav_list_item, Utils.totalCategories()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.colorSecondaryTheme));
                return view;
            }
        };


        // Assign adapter to ListView
        categoryList.setAdapter(adapter);

        // ListView Item Click Listener
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                // String itemValue = (String) categoryList.getItemAtPosition(position);

                // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();*/
                selectedCategoryTab(itemPosition + 1, view);

            }

        });
    }


    public void selectedCategoryTab(int position, View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        switch (position) {

            case 1:
                setTabContent(position, view);
                selectedTab = 1;
                search.setHint("1-search: across Amazon, Flipkart etc..,");

                drawer.closeDrawers();
                if (Utils.getListSelected1(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected1(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList1());


                searchAnim.setVisibility(View.VISIBLE);


                search.setText(sharedpreferences.getString("" + selectedTab, ""));
                if (!search.getText().toString().equals("")) {
                    searchAnim.performClick();
                } else {
                    searchLl.setVisibility(View.GONE);
                    appNameTv.setVisibility(View.VISIBLE);
                    appNameTv.setText("" + tabConent);

                }

                refreshFragmentData();
                break;
            case 2:
                setTabContent(position, view);
                selectedTab = 2;
                search.setHint("1-search: across Jabong, Limeroad, Craftsvilla etc..,");
                drawer.closeDrawers();
                if (Utils.getListSelected2(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected2(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList2());


                searchAnim.setVisibility(View.VISIBLE);

                search.setText(sharedpreferences.getString("" + selectedTab, ""));
                if (!search.getText().toString().equals("")) {
                    searchAnim.performClick();
                } else {
                    searchLl.setVisibility(View.GONE);
                    appNameTv.setVisibility(View.VISIBLE);
                    appNameTv.setText("" + tabConent);
                }

                refreshFragmentData();
                break;
            case 3:
                setTabContent(position, view);
                search.setHint("1-search: across Google, Yahoo, Wiki etc..,");
                selectedTab = 3;
                drawer.closeDrawers();
                if (Utils.getListSelected3(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected3(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList3());

                searchAnim.setVisibility(View.VISIBLE);

                search.setText(sharedpreferences.getString("" + selectedTab, ""));
                if (!search.getText().toString().equals("")) {
                    searchAnim.performClick();
                } else {
                    searchLl.setVisibility(View.GONE);
                    appNameTv.setVisibility(View.VISIBLE);
                    appNameTv.setText("" + tabConent);
                }

               /* appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("One App - " + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);*/

                refreshFragmentData();
                break;
            case 4:
                setTabContent(position, view);
                selectedTab = 4;
                drawer.closeDrawers();
                if (Utils.getListSelected4(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }


                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected4(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList4());

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 5:
                setTabContent(position, view);
                selectedTab = 5;
                drawer.closeDrawers();
                if (Utils.getListSelected5(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected5(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList5());


                appNameTv.setText("" + tabConent);
                appNameTv.setVisibility(View.VISIBLE);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 6:
                setTabContent(position, view);
                selectedTab = 6;
                drawer.closeDrawers();
                if (Utils.getListSelected6(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected6(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList6());


                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 7:
                setTabContent(position, view);
                selectedTab = 7;

                drawer.closeDrawers();
                if (Utils.getListSelected7(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected7(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList7());


                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 8:
                setTabContent(position, view);
                selectedTab = 8;
                drawer.closeDrawers();
                if (Utils.getListSelected8(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected8(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList8());

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 9:
                setTabContent(position, view);
                selectedTab = 9;
                drawer.closeDrawers();
                if (Utils.getListSelected9(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected9(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList9());

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 10:
                setTabContent(position, view);
                selectedTab = 10;
                drawer.closeDrawers();
                if (Utils.getListSelected10(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected10(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList10());

/*                searchAnim.setVisibility(View.VISIBLE);

                search.setText(sharedpreferences.getString("" + selectedTab, ""));
                if (!search.getText().toString().equals("")) {
                    searchAnim.performClick();
                } else {
                    searchLl.setVisibility(View.GONE);
                    appNameTv.setVisibility(View.VISIBLE);
                    appNameTv.setText("One App - " + tabConent);
                }*/

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 11:
                setTabContent(position, view);
                selectedTab = 11;
                drawer.closeDrawers();
                if (Utils.getListSelected11(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected11(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList11());

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;
            case 12:
                setTabContent(position, view);
                selectedTab = 12;
                drawer.closeDrawers();
                if (Utils.getListSelected12(this).size() == 0) {
                    Toast.makeText(getBaseContext(), "No Category Selected!", Toast.LENGTH_LONG).show();
                }

                CONTENT = new ArrayList<String>();
                CONTENT.addAll(Utils.getListSelected12(this));
                items = new ArrayList<String>();
                items.addAll(Utils.getList12());

                appNameTv.setVisibility(View.VISIBLE);
                appNameTv.setText("" + tabConent);
                searchLl.setVisibility(View.GONE);
                searchAnim.setVisibility(View.GONE);

                refreshFragmentData();
                break;

        }
    }

    public void setTabContent(int position, View view) {
        if (view != null) {
            TextView tabText = (TextView) view;
            tabConent = tabText.getText().toString();
        } else {
            tabConent = Utils.totalCategories().get(position - 1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    if (mPager.getCurrentItem() == 0)
                        ((FirstTabFragment) mAdapter.mFirstFragment).onKeyPressDown(keyCode);
                    else if (mPager.getCurrentItem() == 1)
                        ((SecondTabFragment) mAdapter.mSecondFragment).onKeyPressDown(keyCode);
                    else if (mPager.getCurrentItem() == 2)
                        ((ThirdTabFragment) mAdapter.mThirdFragment).onKeyPressDown(keyCode);
                    else if (mPager.getCurrentItem() == 3)
                        ((FourthTabFragment) mAdapter.mFourthFragment).onKeyPressDown(keyCode);
                    else if (mPager.getCurrentItem() == 4)
                        ((FifthTabFragment) mAdapter.mFifthFragment).onKeyPressDown(keyCode);
                    else if (mPager.getCurrentItem() == 5)
                        ((SixthTabFragment) mAdapter.mSixthFragment).onKeyPressDown(keyCode);
                    else {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private PopupWindow pw;
    private HashMap<String, String> selectedStrings = new HashMap<String, String>();

    private void initiatePopUp(final List<String> items, TextView tv) {
        selectedStrings = new HashMap<String, String>();


        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get the pop-up window i.e.  drop-down layout
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pop_up_window, (ViewGroup) findViewById(R.id.PopUpView));
        Button selectBtn = (Button) layout.findViewById(R.id.select_btn);
        //get the view to which drop-down layout is to be anchored
        ImageView layout1 = (ImageView) findViewById(R.id.customize);
        pw = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {


            }
        });

        //provide the source layout for drop-down
        pw.setContentView(layout);

        //anchor the drop-down to bottom-left corner of 'layout1'
        pw.showAsDropDown(layout1);

        //populate the drop-down list
        list = (ListView) layout.findViewById(R.id.dropDownList);
        DropDownListAdapter adapter = new DropDownListAdapter(this, items, CONTENT);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    //

                    //
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                    checkBox.setChecked(!checkBox.isChecked());

                    if (checkBox.isChecked()) {

                        selectedStrings.put("tab" + selectedTab + (position + 1), "true");
                        //  editor.putString("tab" + selectedTab + (position + 1), "true");
                    } else {
                        selectedStrings.put("tab" + selectedTab + (position + 1), "null");
                        // editor.putString("tab" + selectedTab + (position + 1), "null");
                    }

                }
            }
        });

        selectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean changedCheck = false;
                SharedPreferences sharedpreferences = getSharedPreferences(
                        MainActivity.My_preference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                for (String mselectedStrings : selectedStrings.keySet()) {
                    // System.out.println(m.getKey()+" "+m.getValue());

                    editor.putString(mselectedStrings, selectedStrings.get(mselectedStrings));
                    //  if(selectedStrings.get(mselectedStrings).equalsIgnoreCase("true"))
                    changedCheck = true;
                }
                editor.commit();

                if (changedCheck) {
                    selectedCategoryTab(selectedTab, null);
                }
                pw.dismiss();
            }
        });


    }


    @Override
    protected void onNewIntent(Intent intent) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("class", this.getClass().getCanonicalName());
        parameters.put("info-msg", "WebSite Selected - " + MainActivity.CONTENT.get(position));
        Flog.fi(this, Utils.Event.VERBOSE, parameters);

        try {
            switch (position) {
                case 0:
                    firstTabFragment = (FirstTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    firstTabFragment.shaerUrl();
                    break;
                case 1:
                    secondTabFragment = (SecondTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    secondTabFragment.shaerUrl();
                    break;
                case 2:
                    thirdTabFragment = (ThirdTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    thirdTabFragment.shaerUrl();
                    break;
                case 3:
                    fourthTabFragment = (FourthTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    fourthTabFragment.shaerUrl();
                    break;
                case 4:
                    fifthTabFragment = (FifthTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    fifthTabFragment.shaerUrl();
                    break;
                case 5:
                    sixthTabFragment = (SixthTabFragment) mPager.getAdapter().instantiateItem(mPager, mPager.getCurrentItem());
                    sixthTabFragment.shaerUrl();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class SearchAppTabAdapter extends FragmentStatePagerAdapter {
        public Fragment mFirstFragment;
        public Fragment mSecondFragment;
        public Fragment mThirdFragment;
        public Fragment mFourthFragment;
        public Fragment mFifthFragment;
        public Fragment mSixthFragment;

        public SearchAppTabAdapter(FragmentManager fm, boolean isChildDisplayed) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            currentWeb = CONTENT.get(position);
            String contentHomeUrl = "";
            if (search.getText().toString().equals("") && searchUrl == null) {
                if (currentWeb.equals(Utils.tab11Value) || currentWeb.contains(Utils.tab11Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab11;
                } else if (currentWeb.equals(Utils.tab12Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab12;
                } else if (currentWeb.equals(Utils.tab13Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab13;
                } else if (currentWeb.equals(Utils.tab14Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab14;
                } else if (currentWeb.equals(Utils.tab15Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab15;
                } else if (currentWeb.equals(Utils.tab16Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab16;
                } else if (currentWeb.equals(Utils.tab21Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab21;
                } else if (currentWeb.equals(Utils.tab22Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab22;
                } else if (currentWeb.equals(Utils.tab23Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab23;
                } else if (currentWeb.equals(Utils.tab24Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab24;
                } else if (currentWeb.equals(Utils.tab25Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab25;
                } else if (currentWeb.equals(Utils.tab26Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab26;
                } else if (currentWeb.equals(Utils.tab31Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab31;
                } else if (currentWeb.equals(Utils.tab32Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab32;
                } else if (currentWeb.equals(Utils.tab33Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab33;
                } else if (currentWeb.equals(Utils.tab34Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab34;
                } else if (currentWeb.equals(Utils.tab35Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab35;
                } else if (currentWeb.equals(Utils.tab41Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab41;
                } else if (currentWeb.equals(Utils.tab42Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab42;
                } else if (currentWeb.equals(Utils.tab43Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab43;
                } else if (currentWeb.equals(Utils.tab44Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab44;
                } else if (currentWeb.equals(Utils.tab45Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab45;
                } else if (currentWeb.equals(Utils.tab46Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab46;
                } else if (currentWeb.equals(Utils.tab51Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab51;
                } else if (currentWeb.equals(Utils.tab52Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab52;
                } else if (currentWeb.equals(Utils.tab53Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab53;
                } else if (currentWeb.equals(Utils.tab54Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab54;
                } else if (currentWeb.equals(Utils.tab61Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab61;
                } else if (currentWeb.equals(Utils.tab62Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab62;
                } else if (currentWeb.equals(Utils.tab63Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab63;
                } else if (currentWeb.equals(Utils.tab71Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab71;
                } else if (currentWeb.equals(Utils.tab72Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab72;
                } else if (currentWeb.equals(Utils.tab73Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab73;
                } else if (currentWeb.equals(Utils.tab81Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab81;
                } else if (currentWeb.equals(Utils.tab82Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab82;
                } else if (currentWeb.equals(Utils.tab83Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab83;
                } else if (currentWeb.equals(Utils.tab84Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab84;
                } else if (currentWeb.equals(Utils.tab91Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab91;
                } else if (currentWeb.equals(Utils.tab92Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab92;
                } else if (currentWeb.equals(Utils.tab93Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab93;
                } else if (currentWeb.equals(Utils.tab94Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab94;
                } else if (currentWeb.equals(Utils.tab95Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab95;
                } else if (currentWeb.equals(Utils.tab96Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab96;
                } else if (currentWeb.equals(Utils.tab101Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab101;
                } else if (currentWeb.equals(Utils.tab102Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab102;
                } else if (currentWeb.equals(Utils.tab103Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab103;
                } else if (currentWeb.equals(Utils.tab104Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab104;
                } else if (currentWeb.equals(Utils.tab105Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab105;
                } else if (currentWeb.equals(Utils.tab111Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab111;
                } else if (currentWeb.equals(Utils.tab112Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab112;
                } else if (currentWeb.equals(Utils.tab113Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab113;
                } else if (currentWeb.equals(Utils.tab114Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab114;
                } else if (currentWeb.equals(Utils.tab121Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab121;
                } else if (currentWeb.equals(Utils.tab122Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab122;
                } else if (currentWeb.equals(Utils.tab123Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab123;
                } else if (currentWeb.equals(Utils.tab124Value)) {
                    contentUrl = Utils.HomeUrl.homeUrlTab124;
                }

                contentHomeUrl = contentUrl;
            } else {
                //Shop
                if (currentWeb.equals(Utils.tab11Value) || currentWeb.contains(Utils.tab11Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab11 + search.getText().toString() + "&cad=h";
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab11;
                } else if (currentWeb.equals(Utils.tab12Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab12 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab12;
                } else if (currentWeb.equals(Utils.tab13Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab13 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab13;
                } else if (currentWeb.equals(Utils.tab14Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab14 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab14;
                } else if (currentWeb.equals(Utils.tab15Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab15 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab15;
                } else if (currentWeb.equals(Utils.tab16Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab16 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab16;
                }
                //Fashion
                else if (currentWeb.equals(Utils.tab21Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab21 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab21;
                } else if (currentWeb.equals(Utils.tab22Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab22 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab22;
                } else if (currentWeb.equals(Utils.tab23Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab23 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab23;
                } else if (currentWeb.equals(Utils.tab24Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab24 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab24;
                } else if (currentWeb.equals(Utils.tab25Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab25 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab25;
                } else if (currentWeb.equals(Utils.tab26Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab26 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab26;
                }
                //Search
                else if (currentWeb.equals(Utils.tab31Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab31 + search.getText().toString() + "/?q=" + search.getText().toString() + "&tt=" + search.getText().toString() + "&rank=0";
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab31;
                } else if (currentWeb.equals(Utils.tab32Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab32 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab32;
                } else if (currentWeb.equals(Utils.tab33Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab33 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab33;
                } else if (currentWeb.equals(Utils.tab34Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab34 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab34;
                } else if (currentWeb.equals(Utils.tab35Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.SearchUrl.searchUrlTab35 + search.getText().toString();
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab35;
                }
                //Restaurant
                else if (currentWeb.equals(Utils.tab41Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab41;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab41;
                } else if (currentWeb.equals(Utils.tab42Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab42;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab42;
                } else if (currentWeb.equals(Utils.tab43Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab43;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab43;
                } else if (currentWeb.equals(Utils.tab44Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab44;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab44;
                } else if (currentWeb.equals(Utils.tab45Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab45;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab45;
                } else if (currentWeb.equals(Utils.tab46Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab46;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab46;
                }
                //Movies
                else if (currentWeb.equals(Utils.tab51Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab51;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab51;
                } else if (currentWeb.equals(Utils.tab52Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab52;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab52;
                } else if (currentWeb.equals(Utils.tab53Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab53;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab53;
                } else if (currentWeb.equals(Utils.tab54Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab54;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab54;
                }
                //Cabs
                else if (currentWeb.equals(Utils.tab61Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab61;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab61;
                } else if (currentWeb.equals(Utils.tab62Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab62;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab62;
                } else if (currentWeb.equals(Utils.tab63Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab63;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab63;
                }
                //Recharge
                else if (currentWeb.equals(Utils.tab71Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab71;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab71;
                } else if (currentWeb.equals(Utils.tab72Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab72;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab72;
                } else if (currentWeb.equals(Utils.tab73Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab73;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab73;
                }
                //Travel Explore
                else if (currentWeb.equals(Utils.tab81Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab81;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab81;
                } else if (currentWeb.equals(Utils.tab82Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab82;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab82;
                } else if (currentWeb.equals(Utils.tab83Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab83;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab83;
                } else if (currentWeb.equals(Utils.tab84Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab84;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab84;
                }
                //Travel Booking
                else if (currentWeb.equals(Utils.tab91Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab91;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab91;
                } else if (currentWeb.equals(Utils.tab92Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab92;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab92;
                } else if (currentWeb.equals(Utils.tab93Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab93;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab93;
                } else if (currentWeb.equals(Utils.tab94Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab94;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab94;
                } else if (currentWeb.equals(Utils.tab95Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab95;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab95;
                } else if (currentWeb.equals(Utils.tab96Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab96;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab96;
                }
                //News
                else if (currentWeb.equals(Utils.tab101Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab101;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab101;
                } else if (currentWeb.equals(Utils.tab102Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab102;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab102;
                } else if (currentWeb.equals(Utils.tab103Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab103;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab103;
                } else if (currentWeb.equals(Utils.tab104Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab104;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab104;
                } else if (currentWeb.equals(Utils.tab105Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab105;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab105;
                }
                //Games
                else if (currentWeb.equals(Utils.tab111Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab111;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab111;
                } else if (currentWeb.equals(Utils.tab112Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab112;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab112;
                } else if (currentWeb.equals(Utils.tab113Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab113;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab113;
                } else if (currentWeb.equals(Utils.tab114Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab114;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab114;
                }
                //Live Score
                else if (currentWeb.equals(Utils.tab121Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab121;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab121;
                } else if (currentWeb.equals(Utils.tab122Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab122;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab122;
                } else if (currentWeb.equals(Utils.tab123Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab123;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab123;
                } else if (currentWeb.equals(Utils.tab124Value)) {
                    if (searchUrl != null) {
                        contentUrl = searchUrl;
                        searchUrl = null;
                    } else {
                        contentUrl = Utils.HomeUrl.homeUrlTab124;
                    }
                    contentHomeUrl = Utils.HomeUrl.homeUrlTab124;
                }


            }
            switch (position) {

                case Utils.Search.SIXTH_TAB:
                    mSixthFragment = SixthTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mSixthFragment.setRetainInstance(true);
                    return mSixthFragment;

                case Utils.Search.FIFTH_TAB:
                    mFifthFragment = FifthTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mFifthFragment.setRetainInstance(true);
                    return mFifthFragment;

                case Utils.Search.FOURTH_TAB:
                    mFourthFragment = FourthTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mFourthFragment.setRetainInstance(true);
                    return mFourthFragment;

                case Utils.Search.THIRD_TAB:
                    mThirdFragment = ThirdTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mThirdFragment.setRetainInstance(true);
                    return mThirdFragment;

                case Utils.Search.SECOND_TAB:
                    mSecondFragment = SecondTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mSecondFragment.setRetainInstance(true);
                    return mSecondFragment;

                case Utils.Search.FIRST_TAB:
                    mFirstFragment = FirstTabFragment.newInstance(contentUrl, MainActivity.this, contentHomeUrl, currentWeb);
                    mFirstFragment.setRetainInstance(true);
                    return mFirstFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return MainActivity.CONTENT.size();
        }

        @SuppressLint("DefaultLocale")
        @Override
        public CharSequence getPageTitle(int position) {
            String name = MainActivity.CONTENT.get(position);
            StringBuffer res = new StringBuffer();
            String[] strArr = name.split(" ");
            for (String str : strArr) {
                char[] stringArray = str.trim().toCharArray();
                stringArray[0] = Character.toUpperCase(stringArray[0]);
                str = new String(stringArray);
                res.append(str).append(" ");
            }
            return res;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public void searchBut(View view) {
        if (!search.getText().toString().equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
            clearFragments();
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("" + selectedTab, search.getText().toString());
            editor.commit();
            searchClickedFirstTab = true;
            searchClickedSecondTab = true;
            searchClickedThirdTab = true;
            searchClickedFourthTab = true;
            searchClickedFifthTab = true;
            searchClickedSixthTab = true;
            mAdapter.notifyDataSetChanged();

        }
    }

    //closeAll


    public void refreshFragmentData() {
        clearFragments();
        mAdapter = null;
        mAdapter = new SearchAppTabAdapter(getSupportFragmentManager(), false);
        mPager = (CustomViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setEnabledSwipe(false);
        mIndicator = (TabPageIndicatorLoc) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        if (categoryPosition != -1) {
            mIndicator.setCurrentItem(categoryPosition);
            categoryPosition = -1;
        } else {
            mIndicator.setCurrentItem(0);
        }
        mIndicator.setHorizontalFadingEdgeEnabled(false);
        //    mIndicator.notifyDataSetChanged();
        mIndicator.setOnPageChangeListener(this);
        mIndicator.notifyDataSetChanged();
        homeClickedFirstTab = false;
        homeClickedSecondTab = false;
        homeClickedThirdTab = false;
        homeClickedFourthTab = false;
        homeClickedFifthTab = false;
        homeClickedSixthTab = false;
        searchClickedFirstTab = false;
        searchClickedSecondTab = false;
        searchClickedThirdTab = false;
        searchClickedFourthTab = false;
        searchClickedFifthTab = false;
        searchClickedSixthTab = false;
    }

    public void clearFragments() {
        if (mAdapter != null) {
            mAdapter.mFirstFragment = null;
            mAdapter.mSecondFragment = null;
            mAdapter.mThirdFragment = null;
            mAdapter.mFourthFragment = null;
            mAdapter.mFifthFragment = null;
            mAdapter.mSixthFragment = null;
        }
    }

    public void plan(View view) {
        drawer.closeDrawers();
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void closeAll(View views) {

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
            searchAnim.setVisibility(View.VISIBLE);
            searchLl.setVisibility(View.GONE);
            appNameTv.setVisibility(View.VISIBLE);
            appNameTv.setText("1-" + tabConent);
            SharedPreferences sharedpreferences = getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            for (int i = 0; i < CONTENT.size(); i++) {
                String title = CONTENT.get(i);
                editor.putString(title.toLowerCase(), "");
                editor.remove(title.toLowerCase());
            }
            editor.commit();

            homeClickedFirstTab = true;
            homeClickedSecondTab = true;
            homeClickedThirdTab = true;
            homeClickedFourthTab = true;
            homeClickedFifthTab = true;
            homeClickedSixthTab = true;
            search.setText("");
            editor.putString("" + selectedTab, search.getText().toString());
            editor.commit();
        } catch (Exception e) {
        }

        mAdapter.notifyDataSetChanged();

    }

    public void home(View view) {
       /* Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();*/
        try {
            if ((mAdapter.mFirstFragment) != null && mAdapter.mFirstFragment.isMenuVisible())
                ((FirstTabFragment) mAdapter.mFirstFragment).startHomePage();
            if ((mAdapter.mSecondFragment) != null && mAdapter.mSecondFragment.isMenuVisible())
                ((SecondTabFragment) mAdapter.mSecondFragment).startHomePage();
            if ((mAdapter.mThirdFragment) != null && mAdapter.mThirdFragment.isMenuVisible())
                ((ThirdTabFragment) mAdapter.mThirdFragment).startHomePage();
            if ((mAdapter.mFourthFragment) != null && mAdapter.mFourthFragment.isMenuVisible())
                ((FourthTabFragment) mAdapter.mFourthFragment).startHomePage();
            if ((mAdapter.mFifthFragment) != null && mAdapter.mFifthFragment.isMenuVisible())
                ((FifthTabFragment) mAdapter.mFifthFragment).startHomePage();
            if ((mAdapter.mSixthFragment) != null && mAdapter.mSixthFragment.isMenuVisible())
                ((SixthTabFragment) mAdapter.mSixthFragment).startHomePage();
        } catch (Exception e) {
        }
    }

    public void refresh(View view) {
        mAdapter.notifyDataSetChanged();
    }

    public void togg(View view) {
        //  drawer.openDrawer(Gravity.LEFT);
        //   home(null);
        //  finish();
        //   mAdapter.mFirstFragment.;

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void passDataToActivity(String tabTitle, String url) {
        shareUrl = url;
        shareTabTitle = tabTitle;
    }

    public void share(View view) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String text = "Check out the above link. Find 50+ apps in One App.\nDownload: ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "One App : " + shareTabTitle);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareUrl + " \n" + text + "\n" + Utils.PLAY_STORE_LINK);
        startActivity(Intent.createChooser(sharingIntent, "Share this useful info via..."));

    }

/*    @Override
    protected void onResume() {
        super.onResume();
        try {
            timer = new Timer();
            shareTimer = new TimerTask() {
                @Override
                public void run() {
                    share(null);
                }
            };
            // schedule share to run every  10 minutes...
            timer.schedule(shareTimer, 1000 * 60 * 1, 1000 * 60 * 1);   // 1000*60*10 every 10 minutes
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (shareTimer != null) {
                shareTimer.cancel();
                shareTimer = null;
            }

            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }
        } catch (Exception e) {

        }
    }*/

}
