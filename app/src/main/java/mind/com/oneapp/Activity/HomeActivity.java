package mind.com.oneapp.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quantumgraph.sdk.QG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mind.com.oneapp.AppConstants.Utils;
import mind.com.oneapp.Framework.AsyncResult;
import mind.com.oneapp.Framework.DB;
import mind.com.oneapp.Framework.DownloadTrendingDataTask;
import mind.com.oneapp.Framework.RowData;
import mind.com.oneapp.Framework.TrendingAdapter;
import mind.com.oneapp.Log.Flog;
import mind.com.oneapp.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends Activity {
    private View mContentView;
    CustomGridAdapter adapter;
    GridView grid;
    TextView textCatOne;
    TextView textCatTwo;
    TextView textCatThree;
    TextView textCatFour;
    public String textCatSOne;
    public String textCatSTwo;
    public String textCatSThree;
    public String textCatSFour;
    public EditText search;
    public String selectedSearchName = "";
    public int selectedTabSearch = 1;
    private int tabCategoryPosition = -1;
    public String syncedEmailId = "";

    TextView treningTv;
    TextView categoryTv;
    TextView trendingTvLine;
    TextView trendingTvLine2;
    LinearLayout trendingListLl;
    LinearLayout trendingItemLl;
    LinearLayout skipLl;
    LinearLayout introGvLl;
    ArrayList<RowData> rowData = new ArrayList<RowData>();
    ListView trengingLv;
    RelativeLayout emptyProgress;
    TrendingAdapter trendingAdapter = null;
    ImageView trendingAll, trendingNews, trendingShopping, trendingTravel, trendingMovies, trendingRestaurent;
    //    private SQLiteDatabase sqlDb;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.explore_screen);
        db = new DB(this);
//        sqlDb = db.getWritableDatabase();

        Context context = getApplicationContext();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches() && account.name.contains("gmail.com")) {
                syncedEmailId = account.name;
            }
        }

        sendData();
        grid = (GridView) findViewById(R.id.gridviewIntro);
        grid.setAdapter(adapter = new CustomGridAdapter(this, Utils.totalCategories(), Utils.totalCategoriesIcons()));

        search = (EditText) findViewById(R.id.search);
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

        textCatOne = (TextView) findViewById(R.id.search_cat1);
        textCatTwo = (TextView) findViewById(R.id.search_cat2);
        textCatThree = (TextView) findViewById(R.id.search_cat3);
        textCatFour = (TextView) findViewById(R.id.search_cat4);
        textCatSOne = textCatOne.getText().toString();
        textCatSTwo = textCatTwo.getText().toString();
        textCatSThree = textCatThree.getText().toString();
        textCatSFour = textCatFour.getText().toString();

        treningTv = (TextView) findViewById(R.id.trending_tv);
        categoryTv = (TextView) findViewById(R.id.category_tv);
                //
        trendingTvLine = (TextView) findViewById(R.id.trending_tv_line);
        trendingTvLine2 = (TextView) findViewById(R.id.trending_tv_line_2);
        trendingTvLine.setVisibility(View.VISIBLE);
        trendingTvLine2.setVisibility(View.INVISIBLE);
        trendingListLl = (LinearLayout) findViewById(R.id.trending_list_ll);
        trendingItemLl = (LinearLayout) findViewById(R.id.trending_item_ll);
        skipLl = (LinearLayout) findViewById(R.id.skip_ll);
        introGvLl = (LinearLayout) findViewById(R.id.intro_gv_ll);
        trengingLv = (ListView) findViewById(R.id.trending_lv);
        emptyProgress = (RelativeLayout) findViewById(R.id.emptyProgress);

        trendingAll = (ImageView) findViewById(R.id.trending_all);
        trendingNews = (ImageView) findViewById(R.id.trending_news);
        trendingShopping = (ImageView) findViewById(R.id.trending_shopping);
        trendingTravel = (ImageView) findViewById(R.id.trending_travel);
        trendingMovies = (ImageView) findViewById(R.id.trending_movies);
        trendingRestaurent = (ImageView) findViewById(R.id.trending_restaurant);

        textCatOne.setText(Html.fromHtml("<u>" + textCatSOne + "<u>"));
        textCatTwo.setText(Html.fromHtml("" + textCatSTwo + ""));
        textCatThree.setText(Html.fromHtml("" + textCatSThree + ""));
        textCatFour.setText(Html.fromHtml("" + textCatSFour + ""));
        search.setHint("1-search: across Amazon, Flipkart etc..,");

       /* categoryTv.setPaintFlags(0);
        treningTv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);*/
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        selectedSearchName = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                RadioButton bn = (RadioButton) findViewById(checkedId);
                selectedSearchName = bn.getText().toString();
                if (checkedId == R.id.search_radio1) {
                    selectedTabSearch = 1;
                    search.setHint("1-search: across Amazon, Flipkart etc..,");

                   /* Toast.makeText(getApplicationContext(), "choice: Silent",
                            Toast.LENGTH_SHORT).show();*/
                } else if (checkedId == R.id.search_radio2) {
                    selectedTabSearch = 3;
                    search.setHint("1-search: across Google, Yahoo, Wiki etc..,");
                    /*Toast.makeText(getApplicationContext(), "choice: Sound",
                            Toast.LENGTH_SHORT).show();*/
                } else {
                    selectedTabSearch = 2;
                    search.setHint("1-search: across Jabong, Limeroad etc..,");
                    /*Toast.makeText(getApplicationContext(), "choice: Vibration",
                            Toast.LENGTH_SHORT).show();*/
                }
            }

        });
        //flurry
        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("class", this.getClass().getCanonicalName());
            parameters.put("info-msg", "User_Id: " + mngr.getDeviceId());
            parameters.put("user_email_id", syncedEmailId);
            Flog.fi(this, Utils.Event.USER_ID, parameters);
            //
            QG qg = QG.getInstance(getApplicationContext());
            qg.setEmail(syncedEmailId);
            qg.setUserId(mngr.getDeviceId());
            parameters = new HashMap<String, String>();
            parameters.put("class", this.getClass().getCanonicalName());
            parameters.put("info-msg", "Home Created");
            Flog.fi(HomeActivity.this, Utils.Event.VERBOSE, parameters);
        } catch (Exception e) {

        }

        treningTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trendingTvLine.setVisibility(View.VISIBLE);
                trendingTvLine2.setVisibility(View.INVISIBLE);
                trending(null, 0);
            }
        });

        // Basic Yozio initialization
        //  Yozio.initialize(this);

/*            SharedPreferences sharedpreferences = getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("HomeActivityFirst",true);
            editor.commit();
            try {

                Intent intent = this.getIntent();
                Uri data = intent.getData();
                if (data != null) {
                    String strData = data.toString();
                    parseLink(strData);
                } else {
                    trending(null, 0);
                }
            } catch (Exception e) {

            }*/

        try {

        //    if(sharedpreferences.getBoolean("HomeActivityFirst",true)) {
                /*editor.putBoolean("HomeActivityFirst",false);
                editor.commit();*/
                Intent intent = this.getIntent();
                Uri data = intent.getData();
                if (data != null) {
                    String strData = data.toString();
                    parseLink(strData);
                } else {
                    trending(null, 0);
                }
          //  }
        } catch (Exception e) {


        }


        trengingLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowData rowData = (RowData) parent.getItemAtPosition(position);
                String path = rowData.getUrl();
                parseLink(path);
                SharedPreferences sharedpreferences = getSharedPreferences(
                        MainActivity.My_preference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("trendingCatInnerPos",position);
                editor.commit();
            }
        });
        //  Toast.makeText(getApplicationContext(), deepLinkCategory + "", Toast.LENGTH_SHORT).show();
    }


    /* protected void onActivityResult(final int requestCode, final int resultCode,
                                     final Intent data) {
         if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
             String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
             Toast.makeText(getApplicationContext(), accountName,
                     Toast.LENGTH_LONG).show();
         }
     }*/
    public void goTabWebActivity(int position, int categoryPosition, String searchUrl) {
        // String strName = null;
        // i.putExtra("STRING_I_NEED", strName);

        sendData();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tabPosition", position);
        intent.putExtra("tabCategoryPosition", categoryPosition);
        intent.putExtra("tabSearchUrl", searchUrl);
        intent.putExtra("tabSearchText", search.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        /*try {
            SharedPreferences sharedpreferences = getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
           if(sharedpreferences.getBoolean("HomeActivityFirst",true)) {
               editor.putBoolean("HomeActivityFirst",false);
               editor.commit();
               Intent intent = this.getIntent();
               Uri data = intent.getData();
               if (data != null) {
                   String strData = data.toString();
                   parseLink(strData);
               } else {
                   trending(null, 0);
               }
           }
        } catch (Exception e) {


        }*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
/*        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("HomeActivityFirst",false);
                editor.commit();*/
    }

    public void Skip(View view) {

        sendData();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tabPosition", 0);
        intent.putExtra("tabSearchText", search.getText().toString());
        startActivity(intent);
        finish();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("class", this.getClass().getCanonicalName());
        parameters.put("info-msg", "Home Page Search Skip Clicked");
        Flog.fi(HomeActivity.this, Utils.Event.SEARCH, parameters);
    }

    public void enter(View view) {
        sendData();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tabPosition", 0);
        intent.putExtra("tabSearchText", search.getText().toString());
        startActivity(intent);
        finish();
    }

    public void searchBut(View view) {
        if (!search.getText().toString().equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("" + selectedTabSearch, search.getText().toString());
            editor.commit();
            sendData();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("tabPosition", selectedTabSearch - 1);
            intent.putExtra("tabSearchText", search.getText().toString());
            startActivity(intent);
            finish();

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("class", this.getClass().getCanonicalName());
            parameters.put("info-msg", "Home Page Search Category " + selectedSearchName + " Clicked and Keyword = " + search.getText().toString());
            Flog.fi(HomeActivity.this, Utils.Event.SEARCH, parameters);

        }
    }

    public void searchCatOne(View view) {
        selectedTabSearch = 1;
/*        if (view != null) {
            TextView tabText = (TextView) view;
            tabConent = tabText.getText().toString();
            tabText.setText(Html.fromHtml("<u>" + tabConent + "<u>"));
        }*/
        textCatOne.setText(Html.fromHtml("<u>" + textCatSOne + "<u>"));
        textCatTwo.setText(Html.fromHtml("" + textCatSTwo + ""));
        textCatThree.setText(Html.fromHtml("" + textCatSThree + ""));
        textCatFour.setText(Html.fromHtml("" + textCatSFour + ""));
        search.setHint("1-search: across Amazon, Snapdeal etc..,");

    }

    public void searchCatTwo(View view) {
        selectedTabSearch = 2;
        textCatOne.setText(Html.fromHtml("" + textCatSOne + ""));
        textCatTwo.setText(Html.fromHtml("<u>" + textCatSTwo + "<u>"));
        textCatThree.setText(Html.fromHtml("" + textCatSThree + ""));
        textCatFour.setText(Html.fromHtml("" + textCatSFour + ""));
        search.setHint("1-search: across Google, Yahoo, Wiki etc..,");
    }

    public void searchCatThree(View view) {
        selectedTabSearch = 3;
        textCatOne.setText(Html.fromHtml("" + textCatSOne + ""));
        textCatTwo.setText(Html.fromHtml("" + textCatSTwo + ""));
        textCatThree.setText(Html.fromHtml("<u>" + textCatSThree + "<u>"));
        textCatFour.setText(Html.fromHtml("" + textCatSFour + ""));
        search.setHint("1-search: across Jabong, Limeroad, Craftsvilla etc..,");
    }

    public void searchCatFour(View view) {
        selectedTabSearch = 10;
        textCatOne.setText(Html.fromHtml("" + textCatSOne + ""));
        textCatTwo.setText(Html.fromHtml("" + textCatSTwo + ""));
        textCatThree.setText(Html.fromHtml("" + textCatSThree + ""));
        textCatFour.setText(Html.fromHtml("<u>" + textCatSFour + "<u>"));
    }


    public class CustomGridAdapter extends BaseAdapter {
        List<String> myList = new ArrayList<String>();
        List<Integer> myListIcons = new ArrayList<Integer>();

        LayoutInflater inflater;
        Context context;


        public CustomGridAdapter(Context context, List<String> myList, List<Integer> myListIcons) {
            this.myList = myList;
            this.myListIcons = myListIcons;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public String getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.category_list_item, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            //String currentListData = getItem(position);

            mViewHolder.tvTitle.setText(myList.get(position));
            // mViewHolder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(myListIcons.get(position), 0, 0, 0);
            //   mViewHolder.tvDesc.setText(currentListData.getDescription());
            mViewHolder.ivIcon.setImageResource(myListIcons.get(position));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goTabWebActivity(position, tabCategoryPosition, null);
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("class", this.getClass().getCanonicalName());
                    parameters.put("info-msg", "Home Page Search Category Clicked = " + myList.get(position));
                    Flog.fi(HomeActivity.this, Utils.Event.SEARCH, parameters);
                }
            });

            return convertView;
        }

        private class MyViewHolder {
            TextView tvTitle;//, tvDesc;
            ImageView ivIcon;

            public MyViewHolder(View item) {
                tvTitle = (TextView) item.findViewById(R.id.tvTitle);
                //  tvDesc = (TextView) item.findViewById(R.id.tvDesc);
                ivIcon = (ImageView) item.findViewById(R.id.ivIcon);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
/*        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        //     finish();

    }




    public void trending(View view, final int trendCategoryPosition) {
        try {
            if (Utils.isNetworkAvailable(this)) {
                treningTv.setEnabled(false);
                categoryTv.setEnabled(true);

                try {
                    SharedPreferences sharedpreferences = getSharedPreferences(
                            MainActivity.My_preference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if(sharedpreferences.getBoolean("HomeActivityFirst",true)) {
                        emptyProgress.setVisibility(View.VISIBLE);
                        editor.putBoolean("HomeActivityFirst", false);
                        editor.commit();
                        trendingOverAll(null);
                        new DownloadTrendingDataTask(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {
                                processJson(object, trendCategoryPosition);
                                emptyProgress.setVisibility(View.GONE);
                            }
                        }).execute(Utils.trendingSheet);
                    }
                    else{
                            switch (sharedpreferences.getInt("trendingCatPos",0)) {
                                case 0:
                                    trendingOverAll(null);
                                    break;
                                case 1:
                                    trendingNews(null);
                                    break;
                                case 2:
                                    trendingShoppingDeals(null);
                                    break;
                                case 3:
                                    trendingTravel(null);
                                    break;
                                case 4:
                                    trendingMovies(null);
                                    break;
                                case 5:
                                    trendingEatOrRestaurant(null);
                                    break;
                                default:
                                    trendingOverAll(null);
                                    break;

                        }

                        try {
                            trengingLv.smoothScrollToPosition(sharedpreferences.getInt("trendingCatInnerPos", 0));
                        }catch (Exception e){

                        }
                       // editor.commit();
                    }
                } catch (Exception e) {


                }


                /*categoryTv.setPaintFlags(0);
                treningTv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);*/
                introGvLl.setVisibility(View.GONE);
                skipLl.setVisibility(View.GONE);
                trendingListLl.setVisibility(View.VISIBLE);
                trendingItemLl.setVisibility(View.VISIBLE);
            } else {
                emptyProgress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        "Please check your internet connection and try again.", Toast.LENGTH_LONG)
                        .show();
                category(null);
            }
        } catch (Exception e) {
            emptyProgress.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                    "Something went wrong, Please try again.", Toast.LENGTH_LONG)
                    .show();
            category(null);
        }

    }

    public void category(View view) {
        treningTv.setEnabled(true);
        categoryTv.setEnabled(false);
        /*categoryTv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        treningTv.setPaintFlags(0);*/
        introGvLl.setVisibility(View.VISIBLE);
        skipLl.setVisibility(View.VISIBLE);
        trendingListLl.setVisibility(View.GONE);
        trendingItemLl.setVisibility(View.GONE);
        trendingTvLine.setVisibility(View.INVISIBLE);
        trendingTvLine2.setVisibility(View.VISIBLE);

    }

    public void trendingOverAll(View views) {
        try {
            resetTrendingBg();
            // trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            trendingAll.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
            trendingNews.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            trendingShopping.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            trendingTravel.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            trendingMovies.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
            Utils.trendingCategory = "OverAll";
            treningTv.setText("Trending");
            rowData.clear();
            rowData = db.getTrendingData(Utils.trendingCategory);
            trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
            trengingLv.setAdapter(trendingAdapter);
            if (trendingAdapter != null)
                trendingAdapter.notifyDataSetChanged();

            SharedPreferences sharedpreferences = getSharedPreferences(
                    MainActivity.My_preference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("trendingCatPos", 0);
            editor.commit();

            //trengingLv.smoothScrollToPosition(sharedpreferences.getInt("trendingCatInnerPos",0));
        }catch (Exception e){

        }
    }

    public void trendingNews(View view) {
        resetTrendingBg();
        trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingNews.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
        trendingShopping.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingTravel.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingMovies.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));

        Utils.trendingCategory = "News";
        treningTv.setText("News Trending");
        rowData.clear();
        rowData = db.getTrendingData(Utils.trendingCategory);
        trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
        trengingLv.setAdapter(trendingAdapter);
        if (trendingAdapter != null)
            trendingAdapter.notifyDataSetChanged();

        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("trendingCatPos",1);
        editor.commit();

    }

    public void trendingShoppingDeals(View view) {
        resetTrendingBg();
        trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingNews.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingShopping.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
        trendingTravel.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingMovies.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));

        Utils.trendingCategory = "Shopping Deals";
        treningTv.setText("Shopping Trending");
        rowData.clear();
        rowData = db.getTrendingData(Utils.trendingCategory);
        trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
        trengingLv.setAdapter(trendingAdapter);
        if (trendingAdapter != null)
            trendingAdapter.notifyDataSetChanged();
        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("trendingCatPos",2);
        editor.commit();
    }

    public void trendingTravel(View view) {
        resetTrendingBg();
        trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingNews.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingShopping.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingTravel.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
        trendingMovies.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));

        Utils.trendingCategory = "Travel";
        treningTv.setText("Travel Trending");
        rowData.clear();
        rowData = db.getTrendingData(Utils.trendingCategory);
        trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
        trengingLv.setAdapter(trendingAdapter);
        if (trendingAdapter != null)
            trendingAdapter.notifyDataSetChanged();

        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("trendingCatPos",3);
        editor.commit();
    }

    public void trendingMovies(View view) {
        resetTrendingBg();
        trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingNews.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingShopping.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingTravel.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingMovies.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
        trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));

        Utils.trendingCategory = "Movies";
        treningTv.setText("Movies Trending");
        rowData.clear();
        rowData = db.getTrendingData(Utils.trendingCategory);
        trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
        trengingLv.setAdapter(trendingAdapter);
        if (trendingAdapter != null)
            trendingAdapter.notifyDataSetChanged();

        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("trendingCatPos",4);
        editor.commit();
    }

    public void trendingEatOrRestaurant(View view) {
        resetTrendingBg();
        trendingAll.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingNews.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingShopping.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingTravel.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingMovies.setBackgroundColor(getResources().getColor(R.color.welcomeoffertext));
        trendingRestaurent.setBackgroundColor(getResources().getColor(R.color.selectedtrend));
        Utils.trendingCategory = "Restaurant";
        treningTv.setText("Restaurant Trending");
        rowData.clear();
        rowData = db.getTrendingData(Utils.trendingCategory);
        trendingAdapter = new TrendingAdapter(this, R.layout.trends, rowData);
        trengingLv.setAdapter(trendingAdapter);
        if (trendingAdapter != null)
            trendingAdapter.notifyDataSetChanged();
    }

    private void processJson(JSONObject object, int trendCategoryPosition) {

        try {
            JSONArray rows = object.getJSONArray("rows");
            if (!rowData.isEmpty()) {
                rowData.clear();
            }
            for (int r = 1; r < rows.length(); r++) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String category = columns.getJSONObject(0).optString("v");
                String title = columns.getJSONObject(1).optString("v");
                String subTitle = columns.getJSONObject(2).optString("v");
                String url = columns.getJSONObject(3).optString("v");

                RowData rowitem = new RowData(category, title, subTitle, url);

                rowData.add(rowitem);
            }
            db.insertTrendingData(rowData);
            //  trendingOverAll(null);

            try {
              /*  Intent intent = this.getIntent();
                Uri data = intent.getData();
                if (data == null) {
                    trendingOverAll(null);
                }
                else{*/



                switch (trendCategoryPosition) {
                    case 0:
                        trendingOverAll(null);
                        break;
                    case 1:
                        trendingNews(null);
                        break;
                    case 2:
                        trendingShoppingDeals(null);
                        break;
                    case 3:
                        trendingTravel(null);
                        break;
                    case 4:
                        trendingMovies(null);
                        break;
                    case 5:
                        trendingEatOrRestaurant(null);
                        break;
                    default:
                        trendingOverAll(null);
                        break;
                }
                //  }

            } catch (Exception e) {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> items = new ArrayList<String>();

    public void parseLink(String path) {
        try {
            String deepLinkCategory = "Home";
            String[] dataParts = path.split("/deeplinking/");
            String deepLinkCategoryParts = dataParts[1]; //idStr; //dataParts[1];
            String[] parts = null;
            if (deepLinkCategoryParts != null) {
                parts = deepLinkCategoryParts.split("/");
            }
            if (parts[0] != null) {
                deepLinkCategory = parts[0];
                if (deepLinkCategory.equalsIgnoreCase("trending")) {
                    int trendCategoryPosition = Integer.parseInt(parts[1]);
                   // setTabAllSelected(trendCategoryPosition);
                    trending(null, trendCategoryPosition);
                } else {
                    String deepLinkUrl = null;
                    if (parts.length >= 3) {
                        tabCategoryPosition = Integer.parseInt(parts[1]);
                        for (int i = 3; i < parts.length; i++) {
//                            deepLinkUrl = parts[2]+"/";
                            if (i == 3) {
                                deepLinkUrl = parts[2];
                                deepLinkUrl = deepLinkUrl + "/" + parts[i];
                            } else {
                                deepLinkUrl = deepLinkUrl + "/" + parts[i];
                            }
                        }
//                        deepLinkUrl = parts[2];
                    } else if (parts.length == 2) {
                        tabCategoryPosition = Integer.parseInt(parts[1]);
                        deepLinkUrl = null;
                    } else {
                        tabCategoryPosition = -1;
                        deepLinkUrl = null;
                    }

                  //  setTabAllSelected(tabCategoryPosition);
                    for (int i = 0; i < Utils.totalCategories().size(); i++) {
                        String category = Utils.totalCategories().get(i).replaceAll("\\s", "");
                        if (deepLinkCategory.equalsIgnoreCase(category)) {
                            setTabAllSelected(i);

                            switch (i + 1) {

                                case 1:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList1());

                                    break;
                                case 2:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList2());
                                    break;
                                case 3:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList3());
                                    break;
                                case 4:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList4());
                                    break;
                                case 5:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList5());
                                    break;
                                case 6:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList6());
                                    break;
                                case 7:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList7());
                                    break;
                                case 8:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList8());
                                    break;
                                case 9:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList9());
                                    break;
                                case 10:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList10());
                                    break;
                                case 11:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList11());
                                    break;
                                case 12:
                                    items = new ArrayList<String>();
                                    items.addAll(Utils.getList12());
                                    break;

                            }
                            SharedPreferences sharedpreferences = getSharedPreferences(
                                    MainActivity.My_preference, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                          //  String urlTotal = sharedpreferences.getString(items.get(tabCategoryPosition).toLowerCase(), "");
                          //  if(urlTotal.equals("")){
                            String    urlTotal =  deepLinkUrl;
                           // }else {
                              //  urlTotal += "_tokenizer_url:::search_app_" + deepLinkUrl;
                            //}
                            editor.putString(items.get(tabCategoryPosition).toLowerCase(), deepLinkUrl);
                            editor.commit();
                            goTabWebActivity(i, tabCategoryPosition, null);
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public void resetTrendingBg() {
        trendingAll.setBackgroundColor(Color.BLACK);
        trendingNews.setBackgroundColor(Color.BLACK);
        trendingShopping.setBackgroundColor(Color.BLACK);
        trendingTravel.setBackgroundColor(Color.BLACK);
        trendingMovies.setBackgroundColor(Color.BLACK);
        trendingRestaurent.setBackgroundColor(Color.BLACK);
    }

    public void setTabAllSelected(int tabCategoryPosition){
        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        switch (tabCategoryPosition ){
            case 0:
                editor.putString("tab11", "true");
                editor.putString("tab12", "true");
                editor.putString("tab13", "true");
                editor.putString("tab14", "true");
                editor.putString("tab15", "true");
                editor.putString("tab16", "true");
                break;
            case 1:
                editor.putString("tab21", "true");
                editor.putString("tab22", "true");
                editor.putString("tab23", "true");
                editor.putString("tab24", "true");
                editor.putString("tab25", "true");
                editor.putString("tab26", "true");
                break;
            case 2:
                editor.putString("tab31", "true");
                editor.putString("tab32", "true");
                editor.putString("tab33", "true");
                editor.putString("tab34", "true");
                editor.putString("tab35", "true");
                break;
            case 3:
                editor.putString("tab41", "true");
                editor.putString("tab42", "true");
                editor.putString("tab43", "true");
                editor.putString("tab44", "true");
                editor.putString("tab45", "true");
                editor.putString("tab46", "true");
                break;
            case 4:
                editor.putString("tab51", "true");
                editor.putString("tab52", "true");
                editor.putString("tab53", "true");
                editor.putString("tab54", "true");
                break;
            case 5:
                editor.putString("tab61", "true");
                editor.putString("tab62", "true");
                editor.putString("tab63", "true");
                break;
            case 6:
                editor.putString("tab71", "true");
                editor.putString("tab72", "true");
                editor.putString("tab73", "true");
                break;
            case 7:
                editor.putString("tab81", "true");
                editor.putString("tab82", "true");
                editor.putString("tab83", "true");
                editor.putString("tab84", "true");
                break;
            case 8:
                editor.putString("tab91", "true");
                editor.putString("tab92", "true");
                editor.putString("tab93", "true");
                editor.putString("tab94", "true");
                editor.putString("tab96", "true");
                break;
            case 9:
                editor.putString("tab101", "true");
                editor.putString("tab102", "true");
                editor.putString("tab103", "true");
                editor.putString("tab104", "true");
                editor.putString("tab105", "true");
                break;
            case 10:
                editor.putString("tab111", "true");
                editor.putString("tab112", "true");
                editor.putString("tab113", "true");
                editor.putString("tab114", "true");
                break;
            case 11:
                editor.putString("tab121", "true");
                editor.putString("tab122", "true");
                editor.putString("tab123", "true");
                editor.putString("tab124", "true");

            }

            editor.commit();

    }

    public void sendData() {
        SharedPreferences sharedpreferences = getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tab11", sharedpreferences.getString("tab11", "true"));
        editor.putString("tab12", sharedpreferences.getString("tab12", "true"));
        editor.putString("tab13", sharedpreferences.getString("tab13", "true"));
        editor.putString("tab14", sharedpreferences.getString("tab14", "true"));
        editor.putString("tab15", sharedpreferences.getString("tab15", "true"));
        editor.putString("tab16", sharedpreferences.getString("tab16", "true"));
        editor.putString("tab21", sharedpreferences.getString("tab21", "true"));
        editor.putString("tab22", sharedpreferences.getString("tab22", "true"));
        editor.putString("tab23", sharedpreferences.getString("tab23", "true"));
        editor.putString("tab24", sharedpreferences.getString("tab24", "true"));
        editor.putString("tab25", sharedpreferences.getString("tab25", "true"));
        editor.putString("tab26", sharedpreferences.getString("tab26", "true"));
        editor.putString("tab31", sharedpreferences.getString("tab31", "true"));
        editor.putString("tab32", sharedpreferences.getString("tab32", "true"));
        editor.putString("tab33", sharedpreferences.getString("tab33", "true"));
        editor.putString("tab34", sharedpreferences.getString("tab34", "true"));
        editor.putString("tab35", sharedpreferences.getString("tab35", "true"));
        editor.putString("tab41", sharedpreferences.getString("tab41", "true"));
        editor.putString("tab42", sharedpreferences.getString("tab42", "true"));
        editor.putString("tab43", sharedpreferences.getString("tab43", "true"));
        editor.putString("tab44", sharedpreferences.getString("tab44", "true"));
        editor.putString("tab45", sharedpreferences.getString("tab45", "true"));
        editor.putString("tab46", sharedpreferences.getString("tab46", "true"));
        editor.putString("tab51", sharedpreferences.getString("tab51", "true"));
        editor.putString("tab52", sharedpreferences.getString("tab52", "true"));
        editor.putString("tab53", sharedpreferences.getString("tab53", "true"));
        editor.putString("tab61", sharedpreferences.getString("tab61", "true"));
        editor.putString("tab62", sharedpreferences.getString("tab62", "true"));
        editor.putString("tab63", sharedpreferences.getString("tab63", "true"));
        editor.putString("tab71", sharedpreferences.getString("tab71", "true"));
        editor.putString("tab72", sharedpreferences.getString("tab72", "true"));
        editor.putString("tab73", sharedpreferences.getString("tab73", "true"));
        editor.putString("tab81", sharedpreferences.getString("tab81", "true"));
        editor.putString("tab82", sharedpreferences.getString("tab82", "true"));
        editor.putString("tab83", sharedpreferences.getString("tab83", "true"));
        editor.putString("tab91", sharedpreferences.getString("tab91", "true"));
        editor.putString("tab92", sharedpreferences.getString("tab92", "true"));
        editor.putString("tab93", sharedpreferences.getString("tab93", "true"));
        editor.putString("tab94", sharedpreferences.getString("tab94", "true"));
        editor.putString("tab96", sharedpreferences.getString("tab96", "true"));
        editor.putString("tab102", sharedpreferences.getString("tab102", "true"));
        editor.putString("tab103", sharedpreferences.getString("tab103", "true"));
        editor.putString("tab104", sharedpreferences.getString("tab104", "true"));
        editor.putString("tab105", sharedpreferences.getString("tab105", "true"));
        editor.putString("tab111", sharedpreferences.getString("tab111", "true"));
        editor.putString("tab112", sharedpreferences.getString("tab112", "true"));
        editor.putString("tab113", sharedpreferences.getString("tab113", "true"));
        editor.putString("tab121", sharedpreferences.getString("tab121", "true"));
        editor.putString("tab122", sharedpreferences.getString("tab122", "true"));
        editor.putString("tab123", sharedpreferences.getString("tab123", "true"));
        editor.putString("tab124", sharedpreferences.getString("tab124", "true"));


        editor.commit();
       /* startActivity(intent);
        finish();*/
    }
}
