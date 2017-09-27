package me.lam.maidong.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import me.lam.maidong.R;

public class TieziItemAdapter extends BaseAdapter {

    private List<tieziTestEntity> objects = new ArrayList<tieziTestEntity>();

    private Context context;
    private LayoutInflater layoutInflater;

    public TieziItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        for(int i=0;i<5;i++){
            objects.add(new tieziTestEntity());
        }


    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public tieziTestEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.tiezi_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.imageButton);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.imageButton2 = (ImageButton) convertView.findViewById(R.id.imageButton2);
            viewHolder.imageButton3 = (ImageButton) convertView.findViewById(R.id.imageButton3);
            viewHolder.imageButton4 = (ImageButton) convertView.findViewById(R.id.imageButton4);
            viewHolder.imageView5 = (ImageView) convertView.findViewById(R.id.imageView5);
            viewHolder.textView73 = (TextView) convertView.findViewById(R.id.textView73);
            viewHolder.imageButton5 = (ImageButton) convertView.findViewById(R.id.imageButton5);
            viewHolder.textView74 = (TextView) convertView.findViewById(R.id.textView74);
            viewHolder.imageButton6 = (ImageButton) convertView.findViewById(R.id.imageButton6);
            viewHolder.textView75 = (TextView) convertView.findViewById(R.id.textView75);
            viewHolder.textView76 = (TextView) convertView.findViewById(R.id.textView76);

            convertView.setTag(viewHolder);
        }
        initializeViews((tieziTestEntity)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(tieziTestEntity object, ViewHolder holder) {
        holder.textView73.setText("哈哈哈哈");
        holder.textView75.setText("哈");
    }

    protected class ViewHolder {
        private ImageButton imageButton;
    private TextView title;
    private TextView content;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageView imageView5;
    private TextView textView73;
    private ImageButton imageButton5;
    private TextView textView74;
    private ImageButton imageButton6;
    private TextView textView75;
    private TextView textView76;
    }
}
