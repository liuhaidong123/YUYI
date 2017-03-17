package com.technology.yuyi.lzh_utils;

/**
 * Created by wanyu on 2017/3/14.
 */

public interface Ip {
   public final static String url_F="http://192.168.1.55:8080/yuyi/";
   public final static String url="http://192.168.1.55:8080/yuyi/";

   //添加家庭用户的接口
   public final static String interface_addFamilyUser="homeuser/save.do";



   //药品商城全部药品接口（只用于查询全部药品）
//   192.168.1.43:8080/yuyi/drugs/findall.do?start=0&limit=10
   public final static String interface_MS_home_allDrugs="drugs/findall.do?";


   //药品商城首页数据
   public final static String inteface_MS_home_date="category/listTreeDrugs.do";



   //药品全部分类
   public final static String interface_MS_allkinds="category/listAllTree.do";

   //获取某一小类药品的请求http://192.168.1.42:8080/yuyi/drugs/getcid2.do?start=0&limit=10&cid2=14
   public final static String interface_MS_smallDrugs="drugs/getcid2.do?";

   //获取某一大类的药品http://192.168.1.42:8080/yuyi/drugs/getcid1.do?start=0&limit=10&cid1=1
   public final static String interface_MS_largeDrugs="drugs/getcid1.do?";



   //查询药品详细信息的url与接口
//   http://192.168.1.43:8080/yuyi/drugs/getid.do?id=3
   public final static String url_DrugInfo="http://192.168.1.42:8080/yuyi/";
   public final static String interface_DrugInfo="drugs/getid.do?";


   //图片url
   public final static String imagePth_F="http://192.168.1.42:8080/yuyi";
   public final static String imagePth="http://192.168.1.55:8080/yuyi";

}
