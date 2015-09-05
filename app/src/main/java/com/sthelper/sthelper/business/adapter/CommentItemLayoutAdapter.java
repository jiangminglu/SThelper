package com.sthelper.sthelper.business.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.RatingBar;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.Comment;

public class CommentItemLayoutAdapter extends BaseAdapter {

    private List<Comment> objects = new ArrayList<Comment>();

    private Context context;
    private LayoutInflater layoutInflater;

    public CommentItemLayoutAdapter(Context context,List<Comment> list) {
        this.context = context;
        this.objects = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.comment_item_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.commentTelTv = (TextView) convertView.findViewById(R.id.comment_tel_tv);
            viewHolder.commentRating = (RatingBar) convertView.findViewById(R.id.comment_rating);
            viewHolder.commentContentTv = (TextView) convertView.findViewById(R.id.comment_content_tv);
            viewHolder.commentTimeTv = (TextView) convertView.findViewById(R.id.comment_time_tv);

            convertView.setTag(viewHolder);
        }
        initializeViews(objects.get(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Comment object, ViewHolder holder) {
        holder.commentContentTv.setText(object.content);
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(object.account.substring(0,3));
//        stringBuffer.append("****");
//        stringBuffer.append(object.account.substring(object.account.length()-5,object.account.length()-1));


        holder.commentTelTv.setText(object.nickname+"    "+ object.account);
        holder.commentRating.setRating(object.score);
        holder.commentTimeTv.setText(object.create_time);
    }

    protected class ViewHolder {
        private TextView commentTelTv;
        private RatingBar commentRating;
        private TextView commentContentTv;
        private TextView commentTimeTv;
    }
}
