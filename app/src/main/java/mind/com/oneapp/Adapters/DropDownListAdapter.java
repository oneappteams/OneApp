package mind.com.oneapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mind.com.oneapp.R;

public class DropDownListAdapter extends BaseAdapter {

	private List<String> mListItems;
	private List<String> mContentSelected;
	private LayoutInflater mInflater;
	//private TextView mSelectedItems;
	private static int selectedCount = 0;
	private static String firstSelected = "";
	private ViewHolder holder;
	private static String selected = "";	//shortened selected values representation
/*
	public static String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		DropDownListAdapter.selected = selected;
	}*/

	public DropDownListAdapter(Context context, List<String> items,
							   List<String> contentSelected) {
		mListItems = new ArrayList<String>();
		mListItems.addAll(items);
		mInflater = LayoutInflater.from(context);
	//	mContentSelected = contentSelected;
		mContentSelected = new ArrayList<String>();
		mContentSelected.addAll(contentSelected);
	//	mSelectedItems = tv;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.drop_down_list_row, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.SelectOption);
			holder.chkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			holder.customizeDrop = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv.setText(mListItems.get(position));
		holder.chkbox.setChecked(false);
		//final int position1 = position;
		for (int i = 0; i < mContentSelected.size(); i++) {
			if (mContentSelected.get(i).equalsIgnoreCase(mListItems.get(position))) {
				holder.chkbox.setChecked(true);
			}
		}
		/*convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//	if(holder.chkbox.isChecked())
				holder.chkbox.setChecked(!holder.chkbox.isChecked());

				holder.chkbox.performClick();

				//	CheckBox cb = (CheckBox) v.get(0);
				//	else
				//		holder.chkbox.setChecked(true);
			}
		});

		holder.chkbox.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				CheckBox cb = (CheckBox) v;
				// country.setSelected(cb.isChecked());
				if (cb.isChecked())
				{
					holder.chkbox.setChecked(holder.chkbox.isChecked());
					//DataHolder.checkedChilds.put(selectedKeys.get(position), selectedList.get(position));
				}
				else
				{
					holder.chkbox.setChecked(holder.chkbox.isChecked());
					//DataHolder.checkedChilds.remove(selectedKeys.get(position));
				}
			}
		});*/
	//	holder.chkbox.setChecked(true);
	//	holder.chkbox.setSelected(true);
		//whenever the checkbox is clicked the selected values textview is updated with new selected values
/*		holder.chkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	setText(position1);
				holder.chkbox.setChecked(holder.chkbox.isChecked() ? false:true);
			}
		});*/


		return convertView;
	}



	private class ViewHolder {
		TextView tv;
		CheckBox chkbox;
		LinearLayout customizeDrop;
	}
}
