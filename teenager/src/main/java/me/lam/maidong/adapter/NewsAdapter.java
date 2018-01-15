package me.lam.maidong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.entity.msgCallBack;

public class NewsAdapter extends BaseAdapter {

    private List<msgCallBack.MessageListEntity> objects;
    private Context context;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context, List<msgCallBack.MessageListEntity> objects) {
        this.objects = objects;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public msgCallBack.MessageListEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item_message, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.data_time);

            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(msgCallBack.MessageListEntity object, ViewHolder holder) {

      //  Log.e("initializeViewsobject",object.toString());
        holder.imageView1.setImageResource(R.drawable.xiaoxi);
       /* holder.imageView1.setImageBitmap(drawableToBitamp());*/
        holder.textView1.setText(object.getMessageTitle());

        if(object.getMessageContent().length()>18){
            holder.textView3.setText(object.getMessageContent().substring(0, 18) + "...");
        }else {
            holder.textView3.setText(object.getMessageContent());
        }

       /* int j = 0;
        String str = object.getMessageDate().substring(0, 10).toString();*/
        holder.tvTime.setText(object.getMessageDate());

      /*  Log.e("bety", str + "sgsdg");
        for (int i = 1; i < str.length(); i++) {
            if (str.substring(i - 1, i).equals(" ")) {
                Log.e("bety", i + "sgsdg");
                j = i;
            }
        }
        if (j == 0) {
            holder.tvTime.setText(object.getMessageDate().substring(0, 10));
        } else {
            holder.tvTime.setText(object.getMessageDate().substring(0, j));
        }*/


    }

    protected class ViewHolder {
        private ImageView imageView1;
        private TextView textView1;
        private TextView textView3;
        private TextView tvTime;
    }


}
