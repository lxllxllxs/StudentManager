package com.yiyekeji.studentmanager.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.bean.Student;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by lxl on 2016/10/25.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Student> studentList;
    private boolean isShowSelect;
    public StudentAdapter(Context context, List<Student> studentList) {
        this.studentList=studentList;
        mInflater = LayoutInflater.from(context);

    }

    public void setEdit(){
        isShowSelect=true;
    }
    public boolean getEdit(){
        return isShowSelect;
    }

    public void notifyDataSetChanged(List<Student> studentList) {
        this.studentList = studentList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0)
        {
            super(arg0);
            AutoUtils.auto(arg0);
        }
        ImageView ivStatus;
        ImageView ivhead;
        TextView tvName;
        TextView tvStudentId;
        TextView tvAge;

        TextView tvSex;

        LinearLayout llContainer;
    }
        @Override
        public int getItemCount()
        {
            return studentList.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.item_student_adapter, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.ivStatus=(ImageView)view.findViewById(R.id.iv_status);
            viewHolder.ivhead=(ImageView)view.findViewById(R.id.iv_head);
            viewHolder.tvName= (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tvStudentId = (TextView) view.findViewById(R.id.tv_studentId);
            viewHolder.tvAge=(TextView)view.findViewById(R.id.tv_age);
            viewHolder.tvSex=(TextView)view.findViewById(R.id.tv_sex);
            viewHolder.llContainer=(LinearLayout)view.findViewById(R.id.ll_container);
            return viewHolder;
        }


        /**
         * 设置布局控件内容
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            Student student = studentList.get(i);
            if (!TextUtils.isEmpty(student.getHeadImg())){
                viewHolder.ivhead.setImageBitmap(BitmapFactory.decodeFile(student.getHeadImg()));
            }
            viewHolder.tvName.setText(student.getName());
            viewHolder.tvStudentId.setText(student.getStudentId());
            viewHolder.tvAge.setText(student.getAge());
            viewHolder.tvSex.setText(student.getSex());
            if (isShowSelect&&(viewHolder.ivStatus.getVisibility()==View.INVISIBLE)){
                viewHolder.ivStatus.setVisibility(View.VISIBLE);
            }
            if (student.isSelect()&&isShowSelect){
                viewHolder.ivStatus.setImageResource(R.mipmap.ic_select);
            }else {
                viewHolder.ivStatus.setImageResource(R.mipmap.ic_noselect);
            }
            if(mOnItemClickLitener!=null){
                viewHolder.llContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.llContainer, i);
                    }
                });
            }

            if(mOnItemLongClickLitener!=null){
                viewHolder.llContainer.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemLongClickLitener.onItemLongClick(viewHolder.llContainer, i);
                        notifyDataSetChanged();
                        return true;//拦截点击
                    }

                });
            }
        }

    /**
     * 自定义一个回调点击函数
     * @author lxl
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickLitener {
        void onItemLongClick(View view, int position);
    }

    public OnItemClickLitener mOnItemClickLitener;
    public OnItemLongClickLitener mOnItemLongClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public void setOnItemLongClickLitener(OnItemLongClickLitener mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
    }

}
