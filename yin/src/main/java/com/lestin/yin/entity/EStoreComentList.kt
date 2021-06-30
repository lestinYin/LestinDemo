package com.lestin.yin.entity

/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.entity
 * @ClassName:      EStoreComentList
 * @Description:     java类作用描述
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-08 17:50
 * @Version:        1.0
 */

data class EStoreComentList(
		var code: Int,// 200
		var status: String,// success
		var content: StoreComentListContent
)

data class StoreComentListContent(
		var storeComments: MutableList<StoreComment>,
		var storeCommentNum: Int// 0
)

data class StoreComment(
		var userId: String,// 1
		var userName: String,//
		var userPhoto: String,//
		var content: String,// 这家ktv很好找，离地铁站近，装修大气，设备也不错，不足就是我本来是订的小包，结果到了之后服务员才说没有小包了，只能再买两张劵换到中包。。。。。还有最无力吐槽的一点，一张劵一份餐，我买了四张劵，四份。。。。。但是我是一个人去的啊！！服务员要求必须点四份主食，点其他东西比如水都是要另外算钱的。。。。。
		var publishTime: String// 2019-03-27T17:24:04Z
)