package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_MS_allkinds;
import com.technology.yuyi.lzh_utils.MyGridView;

import java.util.List;

/**
 * Created by wanyu on 2017/3/14.
 */

public class MS_allkinds_ExAdapter extends BaseExpandableListAdapter {
    private List<bean_MS_allkinds.CategoryBean> listK;//存放所有分类的view
    private childSelectListener listener;
    private Context context;
    public MS_allkinds_ExAdapter(Context context,List<bean_MS_allkinds.CategoryBean>  listK,childSelectListener listener){
        this.context=context;
        this.listK=listK;
        this.listener=listener;
    }
    @Override
    public int getGroupCount() {
        return listK.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<bean_MS_allkinds.CategoryBean.ChildrenBean>li= listK.get(groupPosition).getChildren();

        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listK.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<bean_MS_allkinds.CategoryBean.ChildrenBean>li= listK.get(groupPosition).getChildren();
        return li==null?null:li.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        parentHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.ms_allkinds_ex_parentv,null);
            hodler=new parentHodler();
            hodler.ms_allkinds_ex_parent_title= (TextView) convertView.findViewById(R.id.ms_allkinds_ex_parent_title);
            convertView.setTag(hodler);
        }
        hodler= (parentHodler) convertView.getTag();
        hodler.ms_allkinds_ex_parent_title.setText(listK.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHodler hodler;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.ms_allkinds_ex_childv,null);
            hodler=new ChildHodler();
            hodler.ms_allkinds_Ex_child_gridview= (MyGridView) convertView.findViewById(R.id.ms_allkinds_Ex_child_gridview);
            convertView.setTag(hodler);
        }
        hodler= (ChildHodler) convertView.getTag();
        List<bean_MS_allkinds.CategoryBean.ChildrenBean>li= listK.get(groupPosition).getChildren();
        MS_allkindssort_Adapter adapter=new MS_allkindssort_Adapter(li,context);
        hodler.ms_allkinds_Ex_child_gridview.setAdapter(adapter);
        hodler.ms_allkinds_Ex_child_gridview.setTag(li);
        hodler.ms_allkinds_Ex_child_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<bean_MS_allkinds.CategoryBean.ChildrenBean>lit= (List<bean_MS_allkinds.CategoryBean.ChildrenBean>) parent.getTag();
                Toast.makeText(context,"当前点击-pos-"+position+"--id==="+lit.get(position).getId()+"name==="+lit.get(position).getName(),Toast.LENGTH_SHORT).show();
                listener.onChildSelect(Integer.parseInt(lit.get(position).getId()),lit.get(position).getName());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class parentHodler{
        TextView ms_allkinds_ex_parent_title;
    }
    class ChildHodler{
        MyGridView ms_allkinds_Ex_child_gridview;
    }



    public interface childSelectListener{
        void onChildSelect(int ChildId,String ChildName);
    }
}
