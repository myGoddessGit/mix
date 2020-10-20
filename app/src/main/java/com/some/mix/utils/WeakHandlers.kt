package com.some.mix.utils

import java.lang.ref.WeakReference
import java.util.concurrent.locks.Lock
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.NullPointerException
import java.util.concurrent.locks.ReentrantLock

/**
 * @author cyl
 * @date 2020/10/12
 */
class WeakHandlers {

    private var mCallback : Handler.Callback? = null
    private var mExec : ExecHandler? = null
    private val mLock = ReentrantLock()

    private val mRunnables = ChainedRef(mLock, null)

    constructor(){
        mCallback = null
        mExec = ExecHandler()
    }

    constructor(callback: Handler.Callback){
        mCallback = callback
        mExec = ExecHandler(WeakReference<Handler.Callback>(callback))
    }

    constructor(looper: Looper){
        mCallback = null
        mExec = ExecHandler(looper)
    }

    constructor(looper: Looper, callback: Handler.Callback){
        mCallback = callback
        mExec = ExecHandler(looper, WeakReference(callback))
    }

    fun post(r : Runnable) : Boolean{
        return mExec!!.post(wrapRunnable(r))
    }

    fun postAtTime(r : Runnable, uptimeMillis : Long) : Boolean{
        return mExec!!.postAtTime(wrapRunnable(r), uptimeMillis)
    }

    fun postAtTime(r: Runnable, token : Any, uptimeMillis : Long) : Boolean{
        return mExec!!.postAtTime(wrapRunnable(r), token, uptimeMillis)
    }

    fun postDelayed(r : Runnable, delayMills : Long) : Boolean {
        return mExec!!.postDelayed(wrapRunnable(r), delayMills)
    }

    fun postAtFrontOfQueue(r : Runnable) : Boolean{
        return mExec!!.postAtFrontOfQueue(wrapRunnable(r))
    }

    fun removeCallbacks(r : Runnable){
        val runnable = mRunnables.remove(r)
        if (runnable != null){
            mExec!!.removeCallbacks(runnable)
        }
    }

    fun removeCallbacks(r : Runnable, token: Any){
        val runnable = mRunnables.remove(r)
        if (runnable != null){
            mExec!!.removeCallbacks(runnable, token)
        }
    }

    fun sendMessage(msg: Message) : Boolean {
        return mExec!!.sendMessage(msg)
    }

    fun sendEmptyMessage(what : Int) : Boolean {
        return mExec!!.sendEmptyMessage(what)
    }

    fun sendEmptyMessageDelayed(what: Int, delayMills: Long) : Boolean{
        return mExec!!.sendEmptyMessageDelayed(what, delayMills)
    }

    fun sendEmptyMessageAtTime(what: Int, uptimeMillis: Long) : Boolean {
        return mExec!!.sendEmptyMessageAtTime(what, uptimeMillis)
    }

    fun sendMessageDelayed(msg : Message, delayMills: Long) : Boolean {
        return mExec!!.sendMessageDelayed(msg, delayMills)
    }

    fun sendMessageAtTime(msg: Message, uptimeMillis: Long) : Boolean {
        return mExec!!.sendMessageAtTime(msg, uptimeMillis)
    }

    fun sendMessageAtFrontOfQueue(msg: Message) : Boolean{
        return mExec!!.sendMessageAtFrontOfQueue(msg)
    }

    fun removeMessages(what: Int){
        mExec!!.removeMessages(what)
    }

    fun removeMessages(what: Int, any : Any){
        mExec!!.removeMessages(what, any)
    }

    fun removeCallbacksAndMessages(token: Any){
        mExec!!.removeCallbacksAndMessages(token)
    }

    fun hasMessages(what: Int) : Boolean {
        return mExec!!.hasMessages(what)
    }

    fun hasMessages(what: Int, any: Any) : Boolean{
        return mExec!!.hasMessages(what, any)
    }

    fun getLooper() : Looper{
        return mExec!!.looper
    }

    private fun wrapRunnable(r : Runnable?) : WeakRunnable{
        if (r == null){
            throw NullPointerException("Runnable can not be null")
        }
        val hardRef = ChainedRef(mLock, r)
        mRunnables.insertAfter(hardRef)
        return hardRef.wrapper!!
    }

    internal class ExecHandler : Handler{

        private val mCallback : WeakReference<Callback>?
        constructor(){
            mCallback = null
        }

        constructor(callback: WeakReference<Callback>){
            mCallback = callback
        }

        constructor(looper: Looper) : super(looper){
            mCallback = null
        }

        constructor(looper: Looper, callback: WeakReference<Callback>) : super(looper){
            mCallback = callback
        }

        override fun handleMessage(msg: Message?) {
            if (mCallback == null){
                return
            }
            val callback = mCallback.get() ?: return
            callback.handleMessage(msg)
        }
    }

    class WeakRunnable(private val mDelegate : WeakReference<Runnable>, private val mReference : WeakReference<ChainedRef>) : Runnable {
        override fun run() {
            mReference.get()?.remove()
            mDelegate.get()?.run()
        }
    }

    class ChainedRef() {
        var next : ChainedRef? = null
        var prev : ChainedRef? = null
        var runnable : Runnable? = null
        var wrapper : WeakRunnable? = null
        private var lock : Lock? = null
        constructor(lock : Lock, r : Runnable?) : this() {
            this.runnable = r
            this.lock = lock
            this.wrapper = WeakRunnable(WeakReference<Runnable>(r), WeakReference<ChainedRef>(this))
        }

        fun remove() : WeakRunnable{
            lock?.lock()
            try {
                if (prev != null){
                    prev?.next = next
                }
                if (next != null){
                    next?.prev = prev
                }
            } finally {
                lock?.unlock()
            }
            return wrapper!!
        }

        fun insertAfter(candidate : ChainedRef){
            lock?.lock()
            try {
                if (this.next != null){
                    this.next?.prev = candidate.next
                }
                candidate.next = this.next
                this.next = candidate
                candidate.prev = this
            } finally {
                lock?.unlock()
            }
        }

        fun remove(obj : Runnable) : WeakRunnable?{
            lock?.lock()
            try {
                var curr = this.next
                while (curr != null){
                    if (curr.runnable == obj){
                        return curr.remove()
                    }
                    curr = curr.next
                }
            }finally {
                lock?.unlock()
            }
            return null
        }
    }
}