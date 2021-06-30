package com.pensees.guard.face.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

import java.util.HashMap

/**
 * 智能安装功能的实现类。
 * 原文地址：http://blog.csdn.net/guolin_blog/article/details/47803149
 * @author guolin
 * @since 2015/12/7
 */
class AutoInstallAccessibilityService : AccessibilityService() {

    internal var handledMap: MutableMap<Int, Boolean> = HashMap()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val nodeInfo = event.source
        if (nodeInfo != null) {
            val eventType = event.eventType
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handledMap[event.windowId] == null) {
                    val handled = iterateNodesAndHandle(nodeInfo)
                    if (handled) {
                        handledMap[event.windowId] = true
                    }
                }
            }
        }
    }

    private fun iterateNodesAndHandle(nodeInfo: AccessibilityNodeInfo?): Boolean {
        if (nodeInfo != null) {
            val childCount = nodeInfo.childCount
            if ("android.widget.Button" == nodeInfo.className) {
                val nodeContent = nodeInfo.text.toString()
                Log.d("TAG", "content is $nodeContent")
                if ("安装" == nodeContent
                    || "完成" == nodeContent
                    || "确定" == nodeContent
                ) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            } else if ("android.widget.ScrollView" == nodeInfo.className) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
            for (i in 0 until childCount) {
                val childNodeInfo = nodeInfo.getChild(i)
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true
                }
            }
        }
        return false
    }

    override fun onInterrupt() {}

}
