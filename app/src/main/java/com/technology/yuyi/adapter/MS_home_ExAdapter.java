package com.technology.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.MS_allkinds_activity;
import com.technology.yuyi.activity.MS_drugInfo_activity;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.MyIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/15.
 */

public class MS_home_ExAdapter extends BaseExpandableListAdapter{

//    List<Map<String,Object>>listInfo=new ArrayList<>();
//    for (int i=0;i<listCategory.size();i++){//大类
//        Map<String,Object>mp=new HashMap<>();
//        mp.put("name",listCategory.get(i).getName());
//        mp.put("id",listCategory.get(i).getId());
//        List<Map<String,String>>lit=new ArrayList<>();
//        for (int j=0;j<listDrugs.size();j++){//药品
//            if (listCategory.get(i).getId()==listDrugs.get(j).getCategoryId1()){
//                Map<String,String>m=new HashMap<>();
//                m.put("childname",listDrugs.get(j).getDrugsName());
//                m.put("childid",listDrugs.get(j).getPrice()+"");
//                m.put("childurl",listDrugs.get(j).getPicture());
//    m.put("childprice",listDrugs.get(j).getPrice()+"");
//                lit.add(m);
//            }
//        }
//        mp.put("child",lit);
//        listInfo.add(mp);
//    }
    private Context context;
    private List<Map<String,Object>>list;
    public MS_home_ExAdapter(List<Map<String,Object>>list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getGroupCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Map<String,String>>li= (List<Map<String, String>>) list.get(groupPosition).get("child");
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Map<String,String>>li= (List<Map<String, String>>) list.get(groupPosition).get("child");
        return li==null?0:li.size();
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
        ParentHodler hodler;
        if (convertView==null){
            hodler=new ParentHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.ms_home_ex_parent,null);
            hodler.ms_home_textfg= (TextView) convertView.findViewById(R.id.ms_txt);
            convertView.setTag(hodler);
        }
            hodler= (ParentHodler) convertView.getTag();
            hodler.ms_home_textfg.setText(""+list.get(groupPosition).get("name"));
            return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHodler hodler;
        if (convertView==null){
            hodler=new ChildHodler();
            convertView=LayoutInflater.from(context).inflate(R.layout.ms_home_ex_child,null);
            hodler.ms_home_ex_child_grid= (MyGridView) convertView.findViewById(R.id.ms_home_ex_child_grid);
            convertView.setTag(hodler);
        }
        hodler= (ChildHodler) convertView.getTag();
        List<Map<String,String>>li= (List<Map<String, String>>) list.get(groupPosition).get("child");
        List<Map<String,String>>lit=new ArrayList<>();
        if (li!=null&&li.size()>3){
            for (int i=0;i<3;i++){
                lit.add(li.get(i));
            }
            hodler.ms_home_ex_child_grid.setAdapter(new MS_home_DailyGridViewAdapter(lit,context));
        }
        else {
            hodler.ms_home_ex_child_grid.setAdapter(new MS_home_DailyGridViewAdapter(li,context));
        }
        hodler.ms_home_ex_child_grid.setTag(li);
        hodler.ms_home_ex_child_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Map<String,String>>lis= (List<Map<String, String>>) parent.getTag();
                //intent.putExtra(MyIntent.intent_MS_drugInfo,listAlldrgus.get(position).getId());
                Intent intent=new Intent(context, MS_drugInfo_activity.class);
                intent.putExtra(MyIntent.intent_MS_drugInfo,Integer.parseInt(lis.get(position).get("childid")));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    class ParentHodler{
        TextView ms_home_textfg;
    }

    class ChildHodler{
       MyGridView ms_home_ex_child_grid;
    }
}
