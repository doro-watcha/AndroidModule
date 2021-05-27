package com.goddoro.module.utils

import io.reactivex.subjects.PublishSubject


/**
 * created By DORO 5/10/21
 */

object Broadcast {

    val cardFindBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val profileUpdateSuccess : PublishSubject<Unit> = PublishSubject.create()
}