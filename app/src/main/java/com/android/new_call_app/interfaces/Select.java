package com.android.new_call_app.interfaces;

/**
 * Created By Ayoub aitouna
 */
public interface Select {
    enum Options {
        Download,
        Apply
    }

    void selectItem(int id, Options options);

}
