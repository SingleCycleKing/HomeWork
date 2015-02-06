package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cycle.single.task.R;

import java.util.ArrayList;

/**
 * Created by WZQ on 15-2-4.
 */
public class WordsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<String> name;
    private ArrayList<String> tags;

    public WordsAdapter(Context context, ArrayList<String> name, ArrayList<String> tags) {
        mInflater = LayoutInflater.from(context);
        this.name = name;
        this.tags = tags;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.word_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.word_name);
            viewHolder.tag = (TextView) convertView.findViewById(R.id.word_tag);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.title.setText(name.get(position));
        viewHolder.tag.setText(tags.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView tag;
    }


}
