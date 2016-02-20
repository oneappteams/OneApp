package mind.com.oneapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mind.com.oneapp.R;

/**
 * Created by susendran on 12/11/15.
 */
public class ListAdapter extends ArrayAdapter {
    List<String> sender;
    Context context;
    List<String > tex;
    public ListAdapter(Context context,  List<String> sender,List<String > tex) {
        super(context, R.layout.itemlistrow, sender);
        this.context=context;
        this.sender=sender;
        this.tex=tex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

            TextView tt1 = (TextView) v.findViewById(R.id.title);
            TextView tt2 = (TextView) v.findViewById(R.id.desc);

            if (tt1 != null) {
                tt1.setText(sender.get(position));
            }

            if (tt2 != null) {
                tt2.setText(tex.get(position));
            }
        return v;
    }

}

