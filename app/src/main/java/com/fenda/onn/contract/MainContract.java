package com.fenda.onn.contract;

import com.fenda.onn.common.base.BaseView;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc :
 */
public interface MainContract {
    interface IMainView extends BaseView {
    }

    interface IMainPresenter {
        void processWeather();
    }
}
