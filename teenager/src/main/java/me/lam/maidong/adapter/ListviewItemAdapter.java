package me.lam.maidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.entity.newsEntity;


public class ListviewItemAdapter extends BaseAdapter {

    public List<newsEntity.NewsListEntity> objects ;

    private Context context;
    private LayoutInflater layoutInflater;
    BitmapUtils bitmapUtils;
    public ListviewItemAdapter(Context context, List<newsEntity.NewsListEntity> objects) {
      //  Log.e("ffff", objects.size() + "objects传递过来的");
        this.objects=objects;
        bitmapUtils = new BitmapUtils(context);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public List<newsEntity.NewsListEntity> getObjects() {
        return objects;
    }

    public void add(List<newsEntity.NewsListEntity> dataEntities){
        objects.addAll(dataEntities);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public newsEntity.NewsListEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item_news, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.bt = (Button) convertView.findViewById(R.id.data_fenglei);
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final newsEntity.NewsListEntity object, ViewHolder holder) {
        bitmapUtils.display(holder.imageView1, object.getImgUrl());
       /* holder.imageView1.setImageBitmap(drawableToBitamp());*/
        holder.textView1.setText(object.getArticleTitle());
        if(object.getContent().length()<2){
            holder.textView3.setText("无");
        }else{
            if(object.getContent().length()>20){
                holder.textView3.setText(object.getContent().substring(0,19)+"...");
            }else{
                holder.textView3.setText(object.getContent());
            }


        }
        holder.bt.setFocusable(false);
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.e("ffff", object.getArticleCategoryID()+"点击了listview中的按钮");
            }
        });

        if(object.getArticleCategoryID().equals("1")){

            holder.bt.setText("新闻动态");
        }else if(object.getArticleCategoryID().equals("2")){
            holder.bt.setText("阳光体育");
        }else if(object.getArticleCategoryID().equals("3")){
            holder.bt.setText("健身指导");
        }else if(object.getArticleCategoryID().equals("4")){
            holder.bt.setText("健身新闻");
        }else if(object.getArticleCategoryID().equals("5")){
            holder.bt.setText("系统通知");
        }else if(object.getArticleCategoryID().equals("7")){
            holder.bt.setText("线下活动");
        }else if(object.getArticleCategoryID().equals("8")){
            holder.bt.setText("运动宝典");
        }
    }
    protected class ViewHolder {
        private ImageView imageView1;
        private TextView textView1;
        private TextView textView3;
        private Button bt;

    }

    private Bitmap bitmap;

    private void drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        bitmap = bd.getBitmap();
    }


}
