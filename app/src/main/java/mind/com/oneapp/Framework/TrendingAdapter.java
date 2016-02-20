package mind.com.oneapp.Framework;

/**
 * Created by Kiran on 16-02-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mind.com.oneapp.AppConstants.Utils;
import mind.com.oneapp.R;


public class TrendingAdapter extends ArrayAdapter<RowData> {

    Context context;
    private ArrayList<RowData> rowData;

    public TrendingAdapter(Context context, int textViewResourceId, ArrayList<RowData> rowData) {
        super(context, textViewResourceId, rowData);
        this.context = context;
        this.rowData = rowData;
    }
public  class ViewHolder{
    TextView title;
    TextView subTitle;
    ImageView treningIcon;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (v == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.trends, null);
            viewHolder.title = (TextView) v.findViewById(R.id.title);
            viewHolder.subTitle = (TextView) v.findViewById(R.id.sub_title);
            viewHolder.treningIcon = (ImageView) v.findViewById(R.id.trending_icon);

            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)v.getTag();
        }
        RowData o = rowData.get(position);
        if (o != null) {

//            LinearLayout itemLL = (LinearLayout) v.findViewById(R.id.item_ll);

            viewHolder.title.setText(String.valueOf(o.getTitle()));
            viewHolder.subTitle.setText(String.valueOf(o.getSubTitle()));
            if(o.getCategory().equals("OverAll")){
                viewHolder.treningIcon.setImageResource(R.drawable.food_icon_white);
            } else if(o.getCategory().equals("News")){
                viewHolder.treningIcon.setImageResource(R.drawable.news_icon_white);
            }else if(o.getCategory().equals("Shopping Deals")){
                viewHolder.treningIcon.setImageResource(R.drawable.shop_icon_white);
            }else if(o.getCategory().equals("Travel")){
                viewHolder.treningIcon.setImageResource(R.drawable.travel_icon_white);
            }else if(o.getCategory().equals("Movies")){
                viewHolder.treningIcon.setImageResource(R.drawable.movies_icon_white);
            }else if(o.getCategory().equals("Restaurant")){
                viewHolder.treningIcon.setImageResource(R.drawable.food_icon_white);
            }



        }
        return v;
    }
}
