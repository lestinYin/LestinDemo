package com.lestin.yin.entity

import java.io.Serializable

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.entity
 * @ClassName:      EHome
 * @Description:     java类作用描述
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-07 15:41
 * @Version:        1.0
 */


data class EHome(
        var code: Int,// 200
        var status: String,// success
        var content: Content
) : Serializable

data class Content(
        var stores: List<Store>,
        var reviewInfos: List<ReviewInfo>
) : Serializable

data class ReviewInfo(
        var reviewId: String,// 1
        var userId: String,// 1
        var userName: String,//
        var darenNickName: String,//
        var storeId: String,// 1
        var storeName: String,// 唱吧麦颂
        var reviewTitle: String,//
        var reviewContent: String,// 可以说性价比超高的一家kTV，并没有因为是团券降低服务质量！音响、装潢、灯光一流，歌单全，还有午餐吃饱喝足唱够太爽了！
        var publicTime: String,// 2018/09/27
        var likeCount: Int,// 111
        var commentCount: Int,// 111
        var reviewPics: List<String>,
        var reviewerPhoto: String,// http://192.168.1.202:9000/user/user_photo/15.jpg
        var reviewerTag: String,// 种草达人
        var toStoreDistance: Int// 900
) : Serializable, IHomeType {
    override fun getType(): Int {
        return 300
    }
}



data class Store(
        var storeId: String,// 1
        var storeName: String,// 唱吧麦颂
        var storeTag: String,// ktv
        var storeTop: Int,// 0
        var visitedCount: Int,// 13434
        var reviewCount: Int,// 34312
        var recommenderId: String,// 1
        var recommenderName: String,// 阿里君
        var recommendReason: String,// 音效最棒
        var storePic: String,// http://192.168.1.202:9000/taurus/ktv/ktv1/1.jpg;http://192.168.1.202:9000/taurus/ktv/ktv1/2.jpg;http://192.168.1.202:9000/taurus/ktv/ktv1/3.jpg
        var recommenderPhoto: String,// http://192.168.1.202:9000/user/user_photo/15.jpg
        var recommenderTag: String,// 音乐达人
        var reviewersPhotos: List<String>,
        var toStoreDistance: Int,// 600
        var storeCover: String// http://192.168.1.202:9000/taurus/ktv/ktv1/1.jpg
) : Serializable , IHomeType {
    override fun getType(): Int {
        return 100
    }
}

data class Line(
        var storeId: String// 1
) : Serializable , IHomeType {
    override fun getType(): Int {
        return 200
    }
}





