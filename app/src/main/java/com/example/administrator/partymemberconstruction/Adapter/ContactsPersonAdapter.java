package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.ContactsPersonBean;
import com.example.administrator.partymemberconstruction.ContactsPersonActivity;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.CustomView.CustomerGridView;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class ContactsPersonAdapter extends BaseAdapter {
    List<ContactsPersonBean.UserListBean> list;
    int screenWidth;
    public ContactsPersonAdapter() {
        super();
    }

    Context context;

    public ContactsPersonAdapter(List<ContactsPersonBean.UserListBean> list, Context context,int width) {
        this.list = list;
        this.context = context;
        screenWidth=width;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getTypes();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        ContactsPersonBean.UserListBean userListBean = list.get(position);
        if (convertView == null) {
            if (userListBean.getTypes() == 1) {
                view = LayoutInflater.from(context).inflate(R.layout.contacts_person_list_two_layout, null);
                viewHolder1 = new ViewHolder1(view);
                view.setTag(viewHolder1);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.contacts_person_list_one_layout, null);
                viewHolder2 = new ViewHolder2(view);
                view.setTag(viewHolder2);
            }

        } else {
            view = convertView;
            if (userListBean.getTypes() == 1)
                viewHolder1 = (ViewHolder1) view.getTag();
            else
                viewHolder2 = (ViewHolder2) view.getTag();
        }
        if (userListBean.getTypes() == 1) {
            String ai_cover_imgurl = userListBean.getAi_Cover_Imgurl();//文章图片
            if (ai_cover_imgurl != null) {
                ai_cover_imgurl = ai_cover_imgurl.replace(" ", "");
                Picasso.with(context).load(ai_cover_imgurl == "" ? "www" : ai_cover_imgurl).into(viewHolder1.img);
            }
            String ai_publish_date = userListBean.getAi_Publish_Date();
            if (ai_publish_date != null) {
                String[] split = ai_publish_date.split(" ");
                viewHolder1.time.setText(split[0]);
            }
            int ai_agree_counts = userListBean.getAi_Agree_Counts();
            int ai_read_counts = userListBean.getAi_Read_Counts();
            viewHolder1.readNum.setText(ai_read_counts + "");
            viewHolder1.goodNum.setText(ai_agree_counts + "");
            String ai_abstract = userListBean.getAi_Abstract();
            String ai_theme_name = userListBean.getAi_Theme_Name();
            viewHolder1.title.setText(ai_theme_name + "");
            Log.e("line", "" + viewHolder1.title.getLineCount());
            viewHolder1.content.setText(ai_abstract + "");
        } else {
            Picasso.with(context).load(MyApplication.otherHead.replace(" ", "")).error(R.mipmap.default_head).into(viewHolder2.headImg);
            String ai_publish_person = userListBean.getAi_Publish_Person() + "";
            viewHolder2.name.setText(ai_publish_person);
            String ai_publish_date = userListBean.getAi_Publish_Date();
            viewHolder2.time.setText("" + ai_publish_date);

            viewHolder2.goodNum.setText(userListBean.getAi_Agree_Counts() + "");
            viewHolder2.isReadNum.setText(userListBean.getAi_Read_Counts() + "");

            String ai_text = userListBean.getAi_Text();
            String[] split = ai_text.split("</p>");
            if (split.length >= 0) {
                String substring = split[0].substring(split[0].lastIndexOf(">") + 1, split[0].length());
                viewHolder2.content.setText(substring + "");
                TextPaint paint = viewHolder2.content.getPaint();
                float v = paint.measureText(substring + "");
                Log.e("s", v + "宽度");
                int textWidth = v / screenWidth == 0 ? 1 : screenWidth;
                                     if(textWidth>=2){
                         viewHolder2.isShow.setVisibility(View.VISIBLE);
                         viewHolder2.content.setMaxLines(2);
                         viewHolder2.content.setEllipsize(TextUtils.TruncateAt.END);
                     }
                viewHolder2.isShow.setClickable(true);
                                     viewHolder2.isShow.setTag(viewHolder2.content);
                viewHolder2.isShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView v1 = (TextView) v;
                        CharSequence text = v1.getText();
                        TextView textView = (TextView) v.getTag();
                        if(text.toString().contains("展开")){
                            textView.setMaxLines(100);
                            v1.setText("（收起）");
                        }else{
                            textView.setMaxLines(2);
                            textView.setEllipsize(TextUtils.TruncateAt.END);
                            v1.setText("（展开）");
                        }
                    }
                });
                //
//                viewHolder2.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                 @Override
//                public boolean onPreDraw() {
//                                       //这个回掉会调用多次，获取玩行数后记得注销监听
//                     viewHolder2.content.getViewTreeObserver().removeOnPreDrawListener(this);
//                     viewHolder2.content.getViewTreeObserver().addOnPreDrawListener(null);
//                                         //如果内容显示的行数大于限定显示行数
//                     if(viewHolder2.content.getLineCount()>=2){
//                         viewHolder2.isShow.setVisibility(View.VISIBLE);
//                         viewHolder2.content.setLines(2);
//                         viewHolder2.content.setEllipsize(TextUtils.TruncateAt.END);
//                     }
//                                          return true;
//                                       }
//             });

                if (split.length > 1) {
                    String[] split1 = split[1].split(",");
                    if (split1.length >= 0) {
                        List<String> list = new ArrayList<>();
                        for (String url : split1) {
                            list.add(url);
                            ImgGrideAdapter imgGrideAdapter = new ImgGrideAdapter(context, list);
                            viewHolder2.imgList.setAdapter(imgGrideAdapter);
                            viewHolder2.imgList.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }
        }


        return view;
    }


    class ViewHolder1 {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.clock)
        ImageView clock;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.goodNum)
        TextView goodNum;
        @BindView(R.id.good)
        TextView good;
        @BindView(R.id.readNum)
        TextView readNum;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder2 {
        @BindView(R.id.headImg)
        CircleImageView headImg;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.clock)
        ImageView clock;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.imgList)
        CustomerGridView imgList;
        @BindView(R.id.isRead)
        TextView isRead;
        @BindView(R.id.isReadNum)
        TextView isReadNum;
        @BindView(R.id.goodNum)
        TextView goodNum;
        @BindView(R.id.good)
        ImageView good;
        @BindView(R.id.commentsNum)
        TextView commentsNum;
        @BindView(R.id.comments)
        ImageView comments;
        @BindView(R.id.isShow)
        TextView isShow;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
