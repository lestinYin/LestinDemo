package com.lestin.yin.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.future.taurus.api.model.MainModel
import com.lestin.yin.BuildConfig

import com.lestin.yin.Constants
import com.lestin.yin.MyApplication
import com.lestin.yin.R
import com.lestin.yin.entity.UserInfo
import com.lestin.yin.utils.DialogUtil
import com.lestin.yin.utils.jsonUtils.SPManager
import com.lestin.yin.utils.statubar.ImmersionBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus


/**
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description: activity基类
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 10:32
 * @Version: 1.0
 */

abstract class ABase : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()

    var spManager: SPManager = SPManager(MyApplication.context)
    var mUser: UserInfo? = null

    val mainModel: MainModel by lazy {
        MainModel()
    }
    var mImmersionBar: ImmersionBar? = null
    /**
     * 多种状态的 View 的切换
     */
//    protected var mLayoutStatusView: MultipleStatusView? = null

    var ACTION_FINISH = BuildConfig.APPLICATION_ID + ".intent.action.FINISH"

    private var mDialogUtil: DialogUtil? = null


    private val mExitReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_FINISH) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = layoutId()
        setContentView(layoutId)
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar!!.statusBarColor(R.color.colorPrimary)
        mUser = spManager.get(Constants.USER_INFO, UserInfo::class.java)

        try {
            EventBus.getDefault().register(this)
        } catch (e: Exception) {
        }


        val exitFilter = IntentFilter()
        exitFilter.addAction(ACTION_FINISH)
        registerReceiver(mExitReceiver, exitFilter)

        //所有子类都将继承这些相同的属性
        mImmersionBar!!.init()
        initView()
        initData()
        start()
        initListener()


    }

    private fun initListener() {
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

//    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
//        start()
//    }


    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 开始请求
     */
    abstract fun start()


    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
//        MyApplication.getRefWatcher(this)?.watch(this)
        EventBus.getDefault().unregister(this)

        if (mImmersionBar != null)
            mImmersionBar!!.destroy() //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
        unregisterReceiver(mExitReceiver)
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun showProgressDialog() {
        if (isShowRequestProgress()) {
            mDialogUtil = getProgressDialog()
            if (mDialogUtil != null) {
                mDialogUtil!!.showProgressDialog()
            }
        }
    }

    protected fun isShowProgressDialog(): Boolean {
        var showing = false
        if (mDialogUtil != null) {
            showing = mDialogUtil!!.isDialogShowing()
        }
        return showing
    }

    protected fun closeProgressDialog() {
        if (isShowRequestProgress()) {
            if (mDialogUtil != null) {
                mDialogUtil!!.closeProgressDialog()
            }
        }
    }

    internal fun getProgressDialog(): DialogUtil {
        if (mDialogUtil == null) {
            mDialogUtil = DialogUtil()
            mDialogUtil!!.createProgressDialog(this)
        }
        return mDialogUtil as DialogUtil
    }

    /**
     * 是否显示请求的加载对话框，默认显示。即在任何有网络请求的地方，在请求发送之前显示正在加载，并在加载完成后隐藏
     */
    protected fun isShowRequestProgress(): Boolean {
        return true
    }
}