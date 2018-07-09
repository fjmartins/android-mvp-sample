package com.itsmefabio.anothermaps.view.base;

import com.itsmefabio.anothermaps.data.DataManagerInterface;

public interface IBasePresenter<T> {
    void subscribe(T view);
    void setDataManager(DataManagerInterface dataManager);
    void unsubscribe();
}
