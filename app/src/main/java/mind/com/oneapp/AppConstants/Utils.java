package mind.com.oneapp.AppConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import mind.com.oneapp.Activity.MainActivity;
import mind.com.oneapp.Framework.CustomViewPager;
import mind.com.oneapp.Framework.ResizeAnimation;
import mind.com.oneapp.R;

public final class Utils {

    public static boolean isDebug = false;
    public static String trendingCategory = "OverAll";
    public static int totalCategories = 10;
    public static final String PLAY_STORE_LINK = "https://goo.gl/JtqvUh";
    public static final String trendingSheet  =   "https://spreadsheets.google.com/tq?key=13-iLAMaLbllXPMHn2PzgOzPkQ6gH0cHcVjPtuoHftxg";// "https://docs.google.com/spreadsheets/d/13-iLAMaLbllXPMHn2PzgOzPkQ6gH0cHcVjPtuoHftxg";//"https://spreadsheets.google.com/tq?key=1aExfdoHv2TRdr9MeYpKubrJE20YzmwyVfUgTsfkgTSo";

    public interface Search {
        public static final int FIRST_TAB = 0;
        public static final int SECOND_TAB = 1;
        public static final int THIRD_TAB = 2;
        public static final int FOURTH_TAB = 3;
        public static final int FIFTH_TAB = 4;
        public static final int SIXTH_TAB = 5;

    }

    public static final class Event {
        public static final String SEARCH = "Home_Search";
        public static final String VERBOSE = "Verbose";
        public static final String WARNING = "Warning";
        public static final String EXCEPTION = "Exception";
        public static final String USER_ID = "User_Id";
    }
    /*

    Shop
Fashion
Search
Restaurant
Movies
Cabs
Wallet/Recharge
Travel Explore
Travel Booking
News
Games
Live Scores
     */

    public static List<String> totalCategories() {
        List<String> categorylist = new ArrayList<>();
/*        categorylist.add("Search");
        categorylist.add("Shopping");
        categorylist.add("Eat-Out/Order");
        categorylist.add("Recharge");
        categorylist.add("Cabs");
        categorylist.add("Movies");
        categorylist.add("Fashion");
        categorylist.add("Travel Discovery");
        categorylist.add("Travel Booking");
        categorylist.add("News");
        categorylist.add("Live Scores");
        categorylist.add("Games");*/


        categorylist.add("Shop Now!");
        categorylist.add("Love Fashion");
        categorylist.add("Search & Video");
        categorylist.add("Restaurants");
        categorylist.add("Movie Tickets");
        categorylist.add("Get your Cab");
        categorylist.add("Recharge Now!");
        categorylist.add("Holiday & You");
        categorylist.add("Flight,Bus,Hotel");
        categorylist.add("Today's News");
        categorylist.add("Play Games");
        categorylist.add("Cricket Scores");
        return categorylist;
    }

    public static List<Integer> totalCategoriesIcons() {
        List<Integer> categorylist = new ArrayList<>();
        categorylist.add(R.drawable.shop_icon_white);
        categorylist.add(R.drawable.fashion_icon_white);
        categorylist.add(R.drawable.search_icon_white);
        categorylist.add(R.drawable.food_icon_white);
        categorylist.add(R.drawable.movies_icon_white);
        categorylist.add(R.drawable.cabs_icon_white);
        categorylist.add(R.drawable.recharge_icon_white);
        categorylist.add(R.drawable.explore_icon_white);
        categorylist.add(R.drawable.travel_icon_white);
        categorylist.add(R.drawable.news_icon_white);
        categorylist.add(R.drawable.games_icon_white);
        categorylist.add(R.drawable.live_icon_white);
        return categorylist;
    }

    public static List<String> getAllHomeUrlList() {

        List<String> allHomeUrl = new ArrayList<>();
        //Shop
        allHomeUrl.add(HomeUrl.homeUrlTab11);
        allHomeUrl.add(HomeUrl.homeUrlTab12);
        allHomeUrl.add(HomeUrl.homeUrlTab13);
        allHomeUrl.add(HomeUrl.homeUrlTab14);
        allHomeUrl.add(HomeUrl.homeUrlTab15);
        allHomeUrl.add(HomeUrl.homeUrlTab16);

        //Fashion
        allHomeUrl.add(HomeUrl.homeUrlTab21);
        allHomeUrl.add(HomeUrl.homeUrlTab22);
        allHomeUrl.add(HomeUrl.homeUrlTab23);
        allHomeUrl.add(HomeUrl.homeUrlTab24);
        allHomeUrl.add(HomeUrl.homeUrlTab25);
        allHomeUrl.add(HomeUrl.homeUrlTab26);

        //Search
        allHomeUrl.add(HomeUrl.homeUrlTab31);
        allHomeUrl.add(HomeUrl.homeUrlTab32);
        allHomeUrl.add(HomeUrl.homeUrlTab33);
        allHomeUrl.add(HomeUrl.homeUrlTab34);
        allHomeUrl.add(HomeUrl.homeUrlTab35);
//        allHomeUrl.add(HomeUrl.homeUrlTab36);

        //Restaurant
        allHomeUrl.add(HomeUrl.homeUrlTab41);
        allHomeUrl.add(HomeUrl.homeUrlTab42);
        allHomeUrl.add(HomeUrl.homeUrlTab43);
        allHomeUrl.add(HomeUrl.homeUrlTab44);
        allHomeUrl.add(HomeUrl.homeUrlTab45);
        allHomeUrl.add(HomeUrl.homeUrlTab46);

        //Movies
        allHomeUrl.add(HomeUrl.homeUrlTab51);
        allHomeUrl.add(HomeUrl.homeUrlTab52);
        allHomeUrl.add(HomeUrl.homeUrlTab53);
        allHomeUrl.add(HomeUrl.homeUrlTab54);
//        allHomeUrl.add(HomeUrl.homeUrlTab55);
//        allHomeUrl.add(HomeUrl.homeUrlTab56);

        //Cabs
        allHomeUrl.add(HomeUrl.homeUrlTab61);
        allHomeUrl.add(HomeUrl.homeUrlTab62);
        allHomeUrl.add(HomeUrl.homeUrlTab63);
//        allHomeUrl.add(HomeUrl.homeUrlTab64);
//        allHomeUrl.add(HomeUrl.homeUrlTab65);
//        allHomeUrl.add(HomeUrl.homeUrlTab66);

        //Recharge
        allHomeUrl.add(HomeUrl.homeUrlTab71);
        allHomeUrl.add(HomeUrl.homeUrlTab72);
        allHomeUrl.add(HomeUrl.homeUrlTab73);
//        allHomeUrl.add(HomeUrl.homeUrlTab74);
//        allHomeUrl.add(HomeUrl.homeUrlTab75);
//        allHomeUrl.add(HomeUrl.homeUrlTab76);

        //Travel Explore
        allHomeUrl.add(HomeUrl.homeUrlTab81);
        allHomeUrl.add(HomeUrl.homeUrlTab82);
        allHomeUrl.add(HomeUrl.homeUrlTab83);
        allHomeUrl.add(HomeUrl.homeUrlTab84);
//        allHomeUrl.add(HomeUrl.homeUrlTab85);
//        allHomeUrl.add(HomeUrl.homeUrlTab86);

        //Travel booking
        allHomeUrl.add(HomeUrl.homeUrlTab91);
        allHomeUrl.add(HomeUrl.homeUrlTab92);
        allHomeUrl.add(HomeUrl.homeUrlTab93);
        allHomeUrl.add(HomeUrl.homeUrlTab94);
        allHomeUrl.add(HomeUrl.homeUrlTab95);
        allHomeUrl.add(HomeUrl.homeUrlTab96);

        //News
        allHomeUrl.add(HomeUrl.homeUrlTab101);
        allHomeUrl.add(HomeUrl.homeUrlTab102);
        allHomeUrl.add(HomeUrl.homeUrlTab103);
        allHomeUrl.add(HomeUrl.homeUrlTab104);
        allHomeUrl.add(HomeUrl.homeUrlTab105);
//        allHomeUrl.add(HomeUrl.homeUrlTab106);

        //Games
        allHomeUrl.add(HomeUrl.homeUrlTab111);
        allHomeUrl.add(HomeUrl.homeUrlTab112);
        allHomeUrl.add(HomeUrl.homeUrlTab113);
        allHomeUrl.add(HomeUrl.homeUrlTab114);
//        allHomeUrl.add(HomeUrl.homeUrlTab115);
//        allHomeUrl.add(HomeUrl.homeUrlTab116);

        //Live Scores
        allHomeUrl.add(HomeUrl.homeUrlTab121);
        allHomeUrl.add(HomeUrl.homeUrlTab122);
        allHomeUrl.add(HomeUrl.homeUrlTab123);
        allHomeUrl.add(HomeUrl.homeUrlTab124);
//        allHomeUrl.add(HomeUrl.homeUrlTab125);
//        allHomeUrl.add(HomeUrl.homeUrlTab126);

        return allHomeUrl;

    }

    public static boolean isHomeUrl(String curentUrl) {
        boolean exist = false;
        List<String> allHomeUrl = getAllHomeUrlList();
        for (int i = 0; i < allHomeUrl.size(); i++) {
            if (allHomeUrl.contains(curentUrl)) {
                exist = true;
            }
        }

        return exist;
    }

    public static final class HomeUrl {


        //Shop
        public static final String homeUrlTab11 = "http://www.amazon.in/?tag=onap-21";
        public static final String homeUrlTab12 = "https://www.flipkart.com/?affid=oneapptea";
        public static final String homeUrlTab13 = "http://m.snapdeal.com/";
        public static final String homeUrlTab14 = "http://tracking.vcommission.com/aff_c?offer_id=1022&aff_id=46723";
        public static final String homeUrlTab15 = "http://tracking.vcommission.com/aff_c?offer_id=122&aff_id=46723";
        public static final String homeUrlTab16 = "http://tracking.vcommission.com/aff_c?offer_id=446&aff_id=46723";

        //Fashion
        public static final String homeUrlTab21 = "http://tracking.vcommission.com/aff_c?offer_id=126&aff_id=46723";
        public static final String homeUrlTab22 = "http://tracking.vcommission.com/aff_c?offer_id=280&aff_id=46723";
        public static final String homeUrlTab23 =    "http://www.myntra.com/";//"http://tracking.vcommission.com/aff_c?offer_id=318&aff_id=46723";
        public static final String homeUrlTab24 = "http://tracking.vcommission.com/aff_c?offer_id=102&aff_id=46723";
        public static final String homeUrlTab25 = "http://www.craftsvilla.com";
        public static final String homeUrlTab26 = "http://voonik.com";

        //Search
        public static final String homeUrlTab31 = "https://www.google.co.in/?gws_rd=ssl";
        public static final String homeUrlTab32 = "https://in.yahoo.com/";
        public static final String homeUrlTab33 = "https://www.bing.com/";
        public static final String homeUrlTab34 = "https://en.m.wikipedia.org/wiki/Main_Page";
        public static final String homeUrlTab35 = "https://m.youtube.com";
//        public static final String homeUrlTab36 = "contentUrlTab16";

        //Restaurant
        public static final String homeUrlTab41 = "https://www.zomato.com/";
        public static final String homeUrlTab42 = "http://tracking.vcommission.com/aff_c?offer_id=180&aff_id=46723";
        public static final String homeUrlTab43 = "https://www.foodpanda.in/";
        public static final String homeUrlTab44 = "https://www.swiggy.com/";
        public static final String homeUrlTab45 = "http://www.dineout.co.in/";
        public static final String homeUrlTab46 = "http://www.freshmenu.com/";

        //Movies
        public static final String homeUrlTab51 = "https://in.bookmyshow.com/bengaluru/movies/";
        public static final String homeUrlTab52 = "http://m.imdb.com/";
        public static final String homeUrlTab53 = "http://www.bangaloremirror.com/entertainment/reviews/";
        public static final String homeUrlTab54 = "http://www.hotstar.com";
//        public static final String homeUrlTab55 = "contentUrlTab65";
//        public static final String homeUrlTab56 = "contentUrlTab66";

        //Cabs
        public static final String homeUrlTab61 = "https://m.uber.com/";
        public static final String homeUrlTab62 = "https://www.merucabs.com/smart   ";
        public static final String homeUrlTab63 = "http://www.zoomcar.com/bangalore/";
        //        public static final String homeUrlTab64 = "contentUrlTab54";
//        public static final String homeUrlTab65 = "contentUrlTab55";
//        public static final String homeUrlTab66 = "contentUrlTab56";

        //Recharge
        public static final String homeUrlTab71 = "https://www.freecharge.in/mobile";
        public static final String homeUrlTab72 = "http://tracking.vcommission.com/aff_c?offer_id=1022&aff_id=46723";
        public static final String homeUrlTab73 = "https://m.mobikwik.com/";
//        public static final String homeUrlTab74 = "contentUrlTab44";
//        public static final String homeUrlTab75 = "contentUrlTab45";
//        public static final String homeUrlTab76 = "contentUrlTab46";

        //Travel Explore
        public static final String homeUrlTab81 = "https://www.tripadvisor.in";
        public static final String homeUrlTab82 = "http://m.holidayiq.com/";
        public static final String homeUrlTab83 = "http://www.thrillophilia.com";
        public static final String homeUrlTab84 = "http://www.triphobo.com/?website=1";
//        public static final String homeUrlTab85 = "contentUrlTab85";
//        public static final String homeUrlTab86 = "contentUrlTab86";

        //Travel Booking
        public static final String homeUrlTab91 = "http://tracking.vcommission.com/aff_c?offer_id=647&aff_id=46723";
        public static final String homeUrlTab92 = "http://www.cleartrip.com/";
        public static final String homeUrlTab93 = "https://www.irctc.co.in/eticketing/loginHome.jsf";
        public static final String homeUrlTab94 = "http://m.redbus.in/";
        public static final String homeUrlTab95 = "http://www.abhibus.com/mobile";
        public static final String homeUrlTab96 = "https://www.airbnb.com";

        //News
        public static final String homeUrlTab101 = "http://m.dailyhunt.in/news/india/english";
        public static final String homeUrlTab102 = "http://m.thehindu.com/";
        public static final String homeUrlTab103 = "http://m.timesofindia.com/";
        public static final String homeUrlTab104 = "http://m.economictimes.com/";
        public static final String homeUrlTab105 = "http://m.firstpost.com/";
//        public static final String homeUrlTab106 = "contentUrlTab106";

        //Games
        public static final String homeUrlTab111 = "http://m.silvergames.com/game/world-cup-penalty/";
        public static final String homeUrlTab112 = "http://m.silvergames.com/en/angry-flappy-birds/";
        public static final String homeUrlTab113 = "http://m.silvergames.com/en/streetrace-fury";
        public static final String homeUrlTab114 = "http://m.silvergames.com/en/2048";
//        public static final String homeUrlTab115 = "contentUrlTab125";
//        public static final String homeUrlTab116 = "contentUrlTab126";

        //Live Scores
        public static final String homeUrlTab121 = "http://www.espncricinfo.com/";
        public static final String homeUrlTab122 = "http://m.cricbuzz.com/";
        public static final String homeUrlTab123 = "http://m.goal.com/s/en-india";
        public static final String homeUrlTab124 = "http://www.football365.com";
//        public static final String homeUrlTab125 = "contentUrlTab115";
//        public static final String homeUrlTab126 = "contentUrlTab116";


    }

    public static final class SearchUrl {
        /*

1 Shop  2
2 Fashion 7
3 Search 1
4 Restaurant 3
5 Movies 6
6 Cabs 5
7 Wallet/Recharge 4
8 Travel Explore 8
9 Travel Booking 9
10 News 10
11 Games 12
12 Live Scores 11
*/


        //Shop
        public static final String searchUrlTab11 = "http://www.amazon.in/gp/aw/s/ref=nb_sb_noss?tag=onap-21&k=";
        public static final String searchUrlTab12 = "https://www.flipkart.com/search?affid=oneapptea&q=";
        public static final String searchUrlTab13 = "https://m.snapdeal.com/search?keyword=";
        public static final String searchUrlTab14 = "https://paytm.com/shop/search?q=";
        public static final String searchUrlTab15 = "http://m.shopclues.com/search?q=";
        public static final String searchUrlTab16 = "http://m.naaptol.com/m/search.html?type=srch_catlg&kw=";

        //Fasion
        public static final String searchUrlTab21 = "http://m.jabong.com/find/";
        public static final String searchUrlTab22 = "http://www.limeroad.com/search/";
      //  public static final String searchUrlTab23 = "http://www.koovs.com/";
      public static final String searchUrlTab23 = "http://www.myntra.com/";
        public static final String searchUrlTab24 = "http://m.yepme.com/search/";
        public static final String searchUrlTab25 = "http://www.craftsvilla.com/searchresults?searchby=product&q=";
        public static final String searchUrlTab26 = "http://www.voonik.com/search_terms/search_for_user?utf8=%E2%9C%93&term=";

        //Search
        public static final String searchUrlTab31 = "https://www.google.co.in/search?q=";
        public static final String searchUrlTab32 = "https://in.search.yahoo.com/search?p=";
        public static final String searchUrlTab33 = "https://www.bing.com/search?q=";
        public static final String searchUrlTab34 = "https://en.m.wikipedia.org/w/index.php?search=";
        public static final String searchUrlTab35 = "https://m.youtube.com";
//        public static final String searchUrlTab36 = "searchUrlTab16";
    }


    public static final String FlurryUniqueCode = "2SZV28DH6H6G7WCNTWYT";
    public static final String TAG = "Search App";
    public static SharedPreferences sharedpreferences;
    public static List<String> list1 = new ArrayList<>();
    public static List<String> list2 = new ArrayList<>();
    public static List<String> list3 = new ArrayList<>();
    public static List<String> list4 = new ArrayList<>();
    public static List<String> list5 = new ArrayList<>();
    public static List<String> list6 = new ArrayList<>();
    public static List<String> list7 = new ArrayList<>();
    public static List<String> list8 = new ArrayList<>();
    public static List<String> list9 = new ArrayList<>();
    public static List<String> list10 = new ArrayList<>();
    public static List<String> list11 = new ArrayList<>();
    public static List<String> list12 = new ArrayList<>();
    public static List<String> list = new ArrayList<>();
    /*

1 Shop  2
2 Fashion 7
3 Search 1
4 Restaurant 3
5 Movies 6
6 Cabs 5
7 Wallet/Recharge 4
8 Travel Explore 8
9 Travel Booking 9
10 News 10
11 Games 12
12 Live Scores 11
*/


    public static String tab11Value = "Amazon";

    public static String tab12Value = "Flipkart";

    public static String tab13Value = "Snapdeal";

    public static String tab14Value = "Paytm";

    public static String tab15Value = "Shopclues";

    public static String tab16Value = "Naaptol";

    public static String tab21Value = "Jabong";

    public static String tab22Value = "Limeroad";

    public static String tab23Value = "Myntra";

    public static String tab24Value = "Yepme";

    public static String tab25Value = "Craftsvilla";

    public static String tab26Value = "Voonik";

    public static String tab31Value = "Google";

    public static String tab32Value = "Yahoo";

    public static String tab33Value = "Bing";

    public static String tab34Value = "Wikipedia";

    public static String tab35Value = "Youtube";

    //public static String tab36Value = "tab16Value";

    public static String tab41Value = "Zomato";

    public static String tab42Value = "Dominos";

    public static String tab43Value = "Foodpanda";

    public static String tab44Value = "Swiggy";

    public static String tab45Value = "Dineout";

    public static String tab46Value = "Freshmenu";

    public static String tab51Value = "Bookmyshow";

    public static String tab52Value = "IMDB";

    public static String tab53Value = "Reviews";

    public static String tab54Value = "Hotstar";

    public static String tab61Value = "Uber";

    public static String tab62Value = "Meru";

    public static String tab63Value = "Zoomcar";

    public static String tab71Value = "Freecharge";

    public static String tab72Value = "Paytm Recharge";

    public static String tab73Value = "Mobikwik";

    public static String tab81Value = "TripAdvisor";

    public static String tab82Value = "HolidayIQ";

    public static String tab83Value = "Thrillophilia";

    public static String tab84Value = "TripHobo";

    public static String tab91Value = "Makemytrip";

    public static String tab92Value = "Cleartrip";

    public static String tab93Value = "IRCTC";

    public static String tab94Value = "Redbus";

    public static String tab95Value = "Abhibus";

    public static String tab96Value = "Airbnb";

    public static String tab101Value = "Newshunt";

    public static String tab102Value = "Hindu";

    public static String tab103Value = "TOI";

    public static String tab104Value = "Economic Times";

    public static String tab105Value = "Firstpost";

    public static String tab111Value = "Football";

    public static String tab112Value = "Flappy Bird";

    public static String tab113Value = "Racing";

    public static String tab114Value = "Puzzle";

    public static String tab121Value = "Cricinfo";

    public static String tab122Value = "Cricbuzz";

    public static String tab123Value = "Goal";

    public static String tab124Value = "Football365";


    public static List<String> getListSelected1(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab11 = "" + sharedpreferences.getString("tab11", null);
        String tab12 = "" + sharedpreferences.getString("tab12", null);
        String tab13 = "" + sharedpreferences.getString("tab13", null);
        String tab14 = "" + sharedpreferences.getString("tab14", null);
        String tab15 = "" + sharedpreferences.getString("tab15", null);
        String tab16 = "" + sharedpreferences.getString("tab16", null);

        list1 = new ArrayList<>();

        if (!tab11.equals("null")) {
            list1.add(tab11Value);
        }
        if (!tab12.equals("null")) {
            list1.add(tab12Value);
        }
        if (!tab13.equals("null")) {
            list1.add(tab13Value);
        }
        if (!tab14.equals("null")) {
            list1.add(tab14Value);
        }
        if (!tab15.equals("null")) {
            list1.add(tab15Value);
        }
        if (!tab16.equals("null")) {
            list1.add(tab16Value);
        }
        return list1;
    }

    public static List<String> getList1() {


        list1 = new ArrayList<>();


        list1.add(tab11Value);

        list1.add(tab12Value);


        list1.add(tab13Value);

        list1.add(tab14Value);
        list1.add(tab15Value);
        list1.add(tab16Value);
        return list1;

    }


    //2
    public static List<String> getListSelected2(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab21 = "" + sharedpreferences.getString("tab21", null);
        String tab22 = "" + sharedpreferences.getString("tab22", null);
        String tab23 = "" + sharedpreferences.getString("tab23", null);
        String tab24 = "" + sharedpreferences.getString("tab24", null);
        String tab25 = "" + sharedpreferences.getString("tab25", null);
        String tab26 = "" + sharedpreferences.getString("tab26", null);

        list2 = new ArrayList<>();


        if (!tab21.equals("null")) {
            list2.add(tab21Value);
        }
        if (!tab22.equals("null")) {
            list2.add(tab22Value);
        }
        if (!tab23.equals("null")) {
            list2.add(tab23Value);
        }
        if (!tab24.equals("null")) {
            list2.add(tab24Value);
        }
        if (!tab25.equals("null")) {
            list2.add(tab25Value);
        }
        if (!tab26.equals("null")) {
            list2.add(tab26Value);
        }
        return list2;

    }

    //2
    public static List<String> getList2() {


        list2 = new ArrayList<>();


        list2.add(tab21Value);

        list2.add(tab22Value);


        list2.add(tab23Value);

        list2.add(tab24Value);

        list2.add(tab25Value);

        list2.add(tab26Value);


        return list2;
    }


    //3
    public static List<String> getListSelected3(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab31 = "" + sharedpreferences.getString("tab31", null);
        String tab32 = "" + sharedpreferences.getString("tab32", null);
        String tab33 = "" + sharedpreferences.getString("tab33", null);
        String tab34 = "" + sharedpreferences.getString("tab34", null);
        String tab35 = "" + sharedpreferences.getString("tab35", null);
        //       String tab36 = "" + sharedpreferences.getString("tab36", null);

        list3 = new ArrayList<>();


        if (!tab31.equals("null")) {
            list3.add(tab31Value);
        }
        if (!tab32.equals("null")) {
            list3.add(tab32Value);
        }
        if (!tab33.equals("null")) {
            list3.add(tab33Value);
        }
        if (!tab34.equals("null")) {
            list3.add(tab34Value);
        }
        if (!tab35.equals("null")) {
            list3.add(tab35Value);
        }
        //     if (!tab36.equals("null")) {
        //       list3.add(tab36Value);
        // }
        return list3;
    }

    //3
    public static List<String> getList3() {


        list3 = new ArrayList<>();


        list3.add(tab31Value);

        list3.add(tab32Value);

        list3.add(tab33Value);

        list3.add(tab34Value);

        list3.add(tab35Value);

        //    list3.add(tab36Value);

        return list3;
    }


    //4
    public static List<String> getListSelected4(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab41 = "" + sharedpreferences.getString("tab41", null);
        String tab42 = "" + sharedpreferences.getString("tab42", null);
        String tab43 = "" + sharedpreferences.getString("tab43", null);
        String tab44 = "" + sharedpreferences.getString("tab44", null);
        String tab45 = "" + sharedpreferences.getString("tab45", null);
        String tab46 = "" + sharedpreferences.getString("tab46", null);


        list4 = new ArrayList<>();

        if (!tab41.equals("null")) {
            list4.add(tab41Value);
        }
        if (!tab42.equals("null")) {
            list4.add(tab42Value);
        }
        if (!tab43.equals("null")) {
            list4.add(tab43Value);
        }
        if (!tab44.equals("null")) {
            list4.add(tab44Value);
        }
        if (!tab45.equals("null")) {
            list4.add(tab45Value);
        }
        if (!tab46.equals("null")) {
            list4.add(tab46Value);
        }
        return list4;
    }

    //4
    public static List<String> getList4() {


        list4 = new ArrayList<>();


        list4.add(tab41Value);

        list4.add(tab42Value);

        list4.add(tab43Value);

        list4.add(tab44Value);

        list4.add(tab45Value);

        list4.add(tab46Value);

        return list4;
    }


    //5
    public static List<String> getListSelected5(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);


        String tab51 = "" + sharedpreferences.getString("tab51", null);
        String tab52 = "" + sharedpreferences.getString("tab52", null);
        String tab53 = "" + sharedpreferences.getString("tab53", null);
        String tab54 = "" + sharedpreferences.getString("tab54", null);


        list5 = new ArrayList<>();

        if (!tab51.equals("null")) {
            list5.add(tab51Value);
        }
        if (!tab52.equals("null")) {
            list5.add(tab52Value);
        }
        if (!tab53.equals("null")) {
            list5.add(tab53Value);
        }
        if (!tab54.equals("null")) {
            list5.add(tab54Value);
        }

        return list5;
    }

    //5
    public static List<String> getList5() {


        list5 = new ArrayList<>();


        list5.add(tab51Value);

        list5.add(tab52Value);


        list5.add(tab53Value);

        list5.add(tab54Value);


        return list5;

    }


    //6
    public static List<String> getListSelected6(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab61 = "" + sharedpreferences.getString("tab61", null);
        String tab62 = "" + sharedpreferences.getString("tab62", null);
        String tab63 = "" + sharedpreferences.getString("tab63", null);
        // String tab64 = "" + sharedpreferences.getString("tab64", null);


        list6 = new ArrayList<>();

        if (!tab61.equals("null")) {
            list6.add(tab61Value);
        }
        if (!tab62.equals("null")) {
            list6.add(tab62Value);
        }
        if (!tab63.equals("null")) {
            list6.add(tab63Value);
        }
/*        if (!tab64.equals("null")) {
            list6.add(tab64Value);
        }*/
        return list6;
    }


    //6
    public static List<String> getList6() {


        list6 = new ArrayList<>();


        list6.add(tab61Value);

        list6.add(tab62Value);

        list6.add(tab63Value);

        return list6;
    }


    //7
    public static List<String> getListSelected7(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab71 = "" + sharedpreferences.getString("tab71", null);
        String tab72 = "" + sharedpreferences.getString("tab72", null);
        String tab73 = "" + sharedpreferences.getString("tab73", null);
/*        String tab74 = "" + sharedpreferences.getString("tab74", null);
        String tab75 = "" + sharedpreferences.getString("tab75", null);
        String tab76 = "" + sharedpreferences.getString("tab76", null);*/

        list7 = new ArrayList<>();


        if (!tab71.equals("null")) {
            list7.add(tab71Value);
        }
        if (!tab72.equals("null")) {
            list7.add(tab72Value);
        }
        if (!tab73.equals("null")) {
            list7.add(tab73Value);
        }
/*        if (!tab74.equals("null")) {
            list7.add(tab74Value);
        }
        if (!tab75.equals("null")) {
            list7.add(tab75Value);
        }
        if (!tab76.equals("null")) {
            list7.add(tab76Value);
        }*/
        return list7;
    }

    //7
    public static List<String> getList7() {

        list7 = new ArrayList<>();


        list7.add(tab71Value);


        list7.add(tab72Value);


        list7.add(tab73Value);

/*
        list7.add(tab74Value);

        list7.add(tab75Value);

        list7.add(tab76Value);*/

        return list7;
    }


    //8
    public static List<String> getListSelected8(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab81 = "" + sharedpreferences.getString("tab81", null);
        String tab82 = "" + sharedpreferences.getString("tab82", null);
        String tab83 = "" + sharedpreferences.getString("tab83", null);
        String tab84 = "" + sharedpreferences.getString("tab84", null);

        list8 = new ArrayList<>();

        if (!tab81.equals("null")) {
            list8.add(tab81Value);
        }
        if (!tab82.equals("null")) {
            list8.add(tab82Value);
        }
        if (!tab83.equals("null")) {
            list8.add(tab83Value);
        }
        if (!tab84.equals("null")) {
            list8.add(tab84Value);
        }
        return list8;
    }


    //8
    public static List<String> getList8() {

        //list8.clear();

        list8 = new ArrayList<>();
        list8.add(tab81Value);


        list8.add(tab82Value);


        list8.add(tab83Value);


        list8.add(tab84Value);

        return list8;

    }


    //9
    public static List<String> getListSelected9(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab91 = "" + sharedpreferences.getString("tab91", null);
        String tab92 = "" + sharedpreferences.getString("tab92", null);
        String tab93 = "" + sharedpreferences.getString("tab93", null);
        String tab94 = "" + sharedpreferences.getString("tab94", null);
        String tab95 = "" + sharedpreferences.getString("tab95", null);
        String tab96 = "" + sharedpreferences.getString("tab96", null);

        list9 = new ArrayList<>();

        if (!tab91.equals("null")) {
            list9.add(tab91Value);
        }
        if (!tab92.equals("null")) {
            list9.add(tab92Value);
        }
        if (!tab93.equals("null")) {
            list9.add(tab93Value);
        }
        if (!tab94.equals("null")) {
            list9.add(tab94Value);
        }
        if (!tab95.equals("null")) {
            list9.add(tab95Value);
        }
        if (!tab96.equals("null")) {
            list9.add(tab96Value);
        }
        return list9;
    }

    //9

    public static List<String> getList9() {

        //list9.clear();

        list9 = new ArrayList<>();
        list9.add(tab91Value);


        list9.add(tab92Value);


        list9.add(tab93Value);


        list9.add(tab94Value);
        list9.add(tab95Value);
        list9.add(tab96Value);

        return list9;

    }


    public static List<String> getListSelected10(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab101 = "" + sharedpreferences.getString("tab101", null);
        String tab102 = "" + sharedpreferences.getString("tab102", null);
        String tab103 = "" + sharedpreferences.getString("tab103", null);
        String tab104 = "" + sharedpreferences.getString("tab104", null);
        String tab105 = "" + sharedpreferences.getString("tab105", null);

        list10 = new ArrayList<>();

        if (!tab101.equals("null")) {
            list10.add(tab101Value);
        }
        if (!tab102.equals("null")) {
            list10.add(tab102Value);
        }
        if (!tab103.equals("null")) {
            list10.add(tab103Value);
        }
        if (!tab104.equals("null")) {
            list10.add(tab104Value);
        }
        if (!tab105.equals("null")) {
            list10.add(tab105Value);
        }

        return list10;
    }

    public static List<String> getList10() {

        //list10.clear();

        list10 = new ArrayList<>();
        list10.add(tab101Value);

        list10.add(tab102Value);

        list10.add(tab103Value);

        list10.add(tab104Value);

        list10.add(tab105Value);

        return list10;

    }

    public static List<String> getListSelected11(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab111 = "" + sharedpreferences.getString("tab111", null);
        String tab112 = "" + sharedpreferences.getString("tab112", null);
        String tab113 = "" + sharedpreferences.getString("tab113", null);
        String tab114 = "" + sharedpreferences.getString("tab114", null);

        list11 = new ArrayList<>();

        if (!tab111.equals("null")) {
            list11.add(tab111Value);
        }
        if (!tab112.equals("null")) {
            list11.add(tab112Value);
        }
        if (!tab113.equals("null")) {
            list11.add(tab113Value);
        }
        if (!tab114.equals("null")) {
            list11.add(tab114Value);
        }


        return list11;
    }

    public static List<String> getList11() {

        //list10.clear();

        list11 = new ArrayList<>();
        list11.add(tab111Value);

        list11.add(tab112Value);

        list11.add(tab113Value);

        list11.add(tab114Value);

        return list11;

    }

    public static List<String> getListSelected12(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(
                MainActivity.My_preference, Context.MODE_PRIVATE);

        String tab121 = "" + sharedpreferences.getString("tab121", null);
        String tab122 = "" + sharedpreferences.getString("tab122", null);
        String tab123 = "" + sharedpreferences.getString("tab123", null);
        String tab124 = "" + sharedpreferences.getString("tab124", null);

        list12 = new ArrayList<>();

        if (!tab121.equals("null")) {
            list12.add(tab121Value);
        }
        if (!tab122.equals("null")) {
            list12.add(tab122Value);
        }
        if (!tab123.equals("null")) {
            list12.add(tab123Value);
        }
        if (!tab124.equals("null")) {
            list12.add(tab124Value);
        }


        return list12;
    }

    public static List<String> getList12() {

        //list10.clear();

        list12 = new ArrayList<>();
        list12.add(tab121Value);

        list12.add(tab122Value);

        list12.add(tab123Value);

        list12.add(tab124Value);

        return list12;

    }


    //    RelativeLayout topLayout;
//    CustomViewPager mPager;
    private static final int SHOW = 170;//To show header and tab view
    private static final int Hide = 0;//To hide header and tab view
    private static final int THRESHOLD = 120;
    private static int maxThreshold = 0;

    public static void setTopLayoutHeight(int t, RelativeLayout topLayout, CustomViewPager mPager, int height) {

        if (maxThreshold < t) {
            maxThreshold = t;
            setTopLayoutAnim(Hide, topLayout, mPager);
        } else if (t < maxThreshold - THRESHOLD) {
            maxThreshold -= THRESHOLD;
            setTopLayoutAnim(height, topLayout, mPager);
        } else if (t == 0) {
            maxThreshold = 0;
            setTopLayoutAnim(height, topLayout, mPager);
        }
    }

    public static void setTopLayoutAnim(int height, RelativeLayout topLayout, CustomViewPager mPager) {
        //Disable swipe if top layout(header and tab view) is not visible
//        mPager.setEnabledSwipe(height != 0);
        //Setting animation to top layout by changing height
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) topLayout.getLayoutParams();
        ResizeAnimation a = new ResizeAnimation(topLayout, lp.width, lp.height, lp.width, height);
        topLayout.startAnimation(a);
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

}
