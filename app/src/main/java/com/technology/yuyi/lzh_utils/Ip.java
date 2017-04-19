package com.technology.yuyi.lzh_utils;

/**
 * Created by wanyu on 2017/3/14.
 */

public interface Ip {
   public final static String url_F="http://59.110.169.148:8080/";
   public final static String url="http://59.110.169.148:8080/";

   //获取我的药品状态接口http://192.168.1.55:8080/yuyi/prescription/findList.do?token=DAF338120FEDDDC8310B881B459353C0
   public final static String interface_MyDrugState="prescription/findList.do?";

   //获取最新10消息http://192.168.1.55:8080/yuyi/message/findList.do?
   public final static String interface_getPushMsg="message/findList.do?";
   //获取聊天医生id的请求http://192.168.1.35:8080/yuyi/physician/doctory.do
   public final static String interface_getDocID="physician/doctory.do";

   //获取家庭用户电子病历列表（我的家庭用户,不是我的）//http://localhost:8080/yuyi/medical/homeuserMedicalTime.do?id=1
   public final static String interface_famiUserEleList="medical/homeuserMedicalTime.do?";

   //获取家庭用户电子病历列表详细信息（我的家庭用户,不是我的http://localhost:8080/yuyi/medical/homeuserMedicalDetails.do?mid=10
   public final static String interface_famiUserEleMsg="medical/homeuserMedicalDetails.do?";

   //获取融云token的请求personal/rongyuToken.do?personalid=18782931356
   public final static String interface_RongToken="personal/rongyuToken.do?";

   //   获取电子病历列表http://localhost:8080/yuyi/medical/token.do?token=1EE359830D68AF676396B06029CCFA61
   public final static String interface_medicalRecordList="medical/token.do?";

   //   电子病历信息://localhost:8080/yuyi/medical/token.do?token=1EE359830D68AF676396B06029CCFA61
   public final static String interface_medicalRecordMsg="medical/get.do?";


   //获取个人信息接口http://localhost:8080/yuyi/personal/get.do?token=C0700876FB2F9BEC156AC039F894E92B
   public final static String interface_UserMsg="personal/get.do?";

   //个人信息修改:http://localhost:8080/yuyi/personal/save.do?token=C0700876FB2F9BEC156AC039F894E92B&idCard=515251635262&age=26
   public final static String interface_UserMsgRevise="personal/save.do?";

   //添加家庭用户的接口
   public final static String interface_addFamilyUser="homeuser/save.do?";

   //获取家庭用户列表http://192.168.1.55:8080/yuyi/homeuser/findList.do?token=6DD620E22A92AB0AED590DB66F84D064
   public static String interfce_ListFamilyUser="homeuser/findList.do?";

   //删除家庭用户http://192.168.1.55:8080/yuyi/homeuser/delete.do?token=6DD620E22A92AB0AED590DB66F84D064&id=123
   public static String interfce_DeleteFamilyUser="homeuser/delete.do?";

   //意见反馈页面:http://localhost:8080/yuyi/feedback//save.do?content=“”&contact=192873637&token=2E8B4C79121FBC6CB1377B190C663F52
   public final static String interface_User_feedus="feedback//save.do?";

   //获取用户默认地址的接口 token=07503A48193E12437B25F38471141FEA
   public final static String interface_User_Address="address/get.do?";

   //获取用户默认地址的接口 token=07503A48193E12437B25F38471141FEA
   public final static String interface_User_editAddress="address/save.do?";

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


   //关于宇医http://192.168.1.55:8080/yuyi/aboutUs/get.do
   public final static String interface_My_Settings_AboutOur="aboutUs/get.do";

   //联系我们http://192.168.1.55:8080/yuyi/contactUs/getph.do
   public final static String interface_My_Settings_ContactOur="contactUs/getph.do";

   //查询药品详细信息的url与接口
//   http://192.168.1.43:8080/yuyi/drugs/getid.do?id=3
   public final static String interface_DrugInfo="drugs/getid.do?";

   //获取验证码的接口
   public final static String interface_SmsCode="/personal/vcode.do?";

   //图片url
   public final static String imagePth_F="http://59.110.169.148:8080";
   public final static String imagePth="http://59.110.169.148:8080";

}
