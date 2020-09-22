package com.xcj.luck.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author cyl
 * @date 2020/8/4
 */
class IoMainScheduler <T> : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())