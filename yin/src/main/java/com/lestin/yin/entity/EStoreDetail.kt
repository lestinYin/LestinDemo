package com.lestin.yin.entity

import java.io.Serializable

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.entity
 * @ClassName:      EStoreDetail
 * @Description:     java类作用描述
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-08 15:18
 * @Version:        1.0
 */
data class EStoreDetail(
        var code: Int,// 200
        var status: String,// success
        var content: StoreDetailContent
) : Serializable
data class StoreDetailContent(
        var storeInfo: StoreInfo,
        var reviewInfos: List<ReviewInfo>
) : Serializable

data class StoreInfo(
        var ownerId: String,// b8186ff1-53c9-5c04-901f-572befa95111
        var storeId: String,// 1
        var storeName: String,// 唱吧麦颂
        var storeIndex: Int,// 0
        var storeIndustry: String,// play
        var storeTag: String,// ktv
        var storeDesc: String,// ktv
        var storeTop: Int,// 0
        var storePhone: String,//
        var storeCity: String,// 北京市
        var storeProvince: String,// 北京市
        var storeAddress: String,// 通州区通朝大街世纪星城71号楼1单元801
        var storeHours: String,// 9
        var avgPrice: Int,// 120
        var visitedCount: Int,// 13434
        var recommenderId: String,// 1
        var recommenderName: String,// 阿里君
        var recommendReason: String,// 音效最棒
        var storeDistance: Double,// 14943.301008204271
        var isOpen: Boolean,// true
        var storePics: List<String>

) : Serializable



