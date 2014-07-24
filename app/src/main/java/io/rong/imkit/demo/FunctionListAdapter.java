package io.rong.imkit.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by zhjchen on 14-7-17.
 */
public class FunctionListAdapter extends BaseAdapter {
    private String[] mNameArray;
    private LayoutInflater mLayoutInflater;

    public FunctionListAdapter(Context context, String[] names) {
        mNameArray = names;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mNameArray == null)
            return 0;
        return mNameArray.length;
    }

    @Override
    public Object getItem(int position) {
        if (mNameArray == null || mNameArray.length >= position)
            return null;
        return mNameArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {

            convertView = mLayoutInflater.inflate(R.layout.item_function_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTextView.setText(mNameArray[position]);

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
    }
}
