package com.lestin.yin.network;



import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/6 15:26
 * version:
 * description:
 */

public interface BaseApi {
//    /**
//     * 获取验证码
//     */
//    @POST("verifyCode/create")
//    Observable<LMecicalRecord> getVerifyCode(@Query("loginName") String mobile);
//

    /**
     * 获取诊断结果
     */
//    @POST("diagnosisApp")
//    Observable<EResponseDiagnossis> getDiagnossisData(@Body RequestBody route);
//
//    /**
//     * 获取诊断中的体征一级列表
//     */
//    @POST("getOneParts")
//    Observable<EBodyCheckFirst> getBodyFirst(@Body RequestBody route);
//
//    /**
//     * 获取诊断中的体征二级列表
//     */
//    @POST("getTwoParts")
//    Observable<EBodyCheckSecond> getBodySecond(@Query("onePartId") String phone);
//    /**
//     * 获取诊断中的体征二级列表
//     */
//    @POST("getTwoBodyParts")
//    Observable<EBodyCheckSecond> getTwoBodyParts(@Body RequestBody route);
//
//    /**
//     * 诊断添加疾病中一级列表
//     */
//    @POST("getSicknessTypes")
//    Observable<EDiseaseType> getDiseaseType();
//    /**
//     * 诊断添加疾病中二级列表
//     */
//    @POST("getSicknessMessages")
//    Observable<EDiseaseTypeDetail> getDiseaseTypeDetail(@Query("sicknessTypeId") String phone);
//
//    /**
//     * 根据二级列表获取症状
//     */
//    @POST("getSymptoms")
//    Observable<EBodyCheckDisease> getBodyDiseaseDetail(@Query("twoPartId") String phone);
//
//    /**
//     * 修改用户信息
//     */
//    @POST("editUserInfoApp")
//    Observable<EBaseString> editUserInfoApp(@Body RequestBody route);
//
//    /**
//     * 获取诊断后疾病详情(第一种类型)
//     */
//    @POST("diagnosisInfoApp")
//    Observable<EDiseaseDetail> getDiseaseDetail(@Body RequestBody route);
//
//    /**
//     * 获取诊断后疾病详情（第二种类型）
//     */
//    @POST("diagnosisInfoApp")
//    Observable<LEDiseaseDetailTwo> getDiseaseDetailTwo(@Body RequestBody route);
//
//    /**
//     * 获取诊断后疾病详情（第三种类型）
//     */
//    @POST("diagnosisInfoApp")
//    Observable<EDiseaseDetailThree> getDiseaseDetailThree(@Body RequestBody route);
//
//    /**
//     * 最终诊断中  诊断模型里选择其他模糊搜索
//     */
//    @GET("disListApp")
//    Observable<EModelVagueSearch> getModeVagueSeache(@Query("dis") String dis);
//
//    /**
//     * 登陆
//     */
//    @GET("appLogin")
//    Observable<EUser> login(@Query("phone") String phone, @Query("password") String passWord);
//
//    /**
//     * 注册
//     */
//    @GET("registerApp")
//    Observable<EBaseString> regist(@Query("phone") String phone, @Query("code") String code, @Query("password") String passWord);
//
//    /**
//     * 找回密码
//     */
//    @GET("findPasswordApp")
//    Observable<EBaseString> findPassWord(@Query("phone") String phone, @Query("code") String code, @Query("password") String passWord);
//
//    /**
//     * 修改密码
//     */
//    @GET("editPasswordApp")
//    Observable<EBaseString> editPassWord(@Query("phone") String phone, @Query("code") String code, @Query("newPassword") String passWord);
//
//    /**
//     * 获取验证码
//     */
//    @GET("sendCodeApp")
//    Observable<EBaseString> getConfirmCode(@Query("phone") String phone);
//
//    /**
//     * 最终诊断 保存
//     */
//    @POST("saveUserResApp")
//    Observable<EBaseString> saveDiagnossis(@Body RequestBody route);
//
//    /**
//     * 获取病历列表
//     */
//    @POST("getResultApp")
//    Observable<LMecicalRecord> getMedicalRecordList(@Body RequestBody route);
//
//    /**
//     * 获取病历列表详情
//     */
//    @POST("detailApp")
//    Observable<EMedicalRecordDetail> getMedicalRecordDetail(@Query("resId") String resId, @Query("remark") String remark);
//
//    /**
//     * 修改病历列表详情中建议检查项目中的状态
//     * 议检查修改的状态 0 未检查  1 正常  2 异常
//     */
//    @POST("editExamStateApp")
//    Observable<EBaseString> editExamStateApp(@Query("examId") int examId, @Query("state") int state);
//
//    /**
//     * 资质认证中选择身份
//     */
//    @POST("selectJob")
//    Observable<ESelectJob> selectJob();
//
//    /**
//     * 意见反馈
//     */
//    @Multipart
//    @POST("adviceFileApp")
//    Observable<EBaseString> addFeedBack(@QueryMap Map<String, String> params, @Part() List<MultipartBody.Part> requestBodyMap);
//
//    /**
//     * 意见反馈（不带图片）
//     */
//    @POST("adviceApp")
//    Observable<EBaseString> addFeedBack(@QueryMap Map<String, String> params);
//
//    /**
//     * 多张图片上传
//     * r imagesType; // realname (0 实名认证)  aptitude （1 资质认证)  head  (2 头像)  传0,1,2 （不能为空）
//     */
//    @Multipart
//    @POST("uploadImagesApp")
//    Observable<ResponseBody> uploadImageMulti(@QueryMap Map<String, String> params, @Part() List<MultipartBody.Part> requestBodyMap);
//
//    /**
//     * 图片上传
//     */
//    @Multipart
//    @POST("uploadImagesApp")
//    Observable<ResponseBody> uploadImage(@Query("userId") int userId, @Query("imagesType") int type, @Part MultipartBody.Part requestBodyMap);
    //
//    /**
//     * 普通用户注册
//     */
//    @POST("register")
//    Observable<BaseEntity> regist(@QueryMap Map<String, String> params);
//
//    /**
//     * 重设密码
//     */
//    @POST("resetPassword")
//    Observable<BaseEntity> resetPassword(@QueryMap Map<String, String> params);
}
