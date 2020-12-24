package com.example.user.touchengtravelapp.Funtional;

/**
 * Created by user on 2018/9/16.
 */

import android.app.Activity;
import android.os.Handler;

import mbanje.kurt.fabbutton.FabButton;

/**
 * Created by kurt on 08 06 2015 .
 */
public class ProgressHelper {

    private int currentProgress = 0;
    private Handler handle = new Handler();
    private final FabButton button;
    private final Activity activity;

    public ProgressHelper(FabButton button, Activity activity) {
        this.button = button;
        this.activity = activity;
    }

    private Runnable getRunnable(final Activity activity) {
        return new Runnable() {
            @Override
            public void run() {
                currentProgress += 1;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setProgress(currentProgress);
                        if (currentProgress <= 100) {
                            handle.postDelayed(getRunnable(activity), 50);
                        } else new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                button.showProgress(false);
                            }
                        }, 500);

                    }
                });
            }
        };
    }

    public void startIndeterminate() {
        button.setIndeterminate(true);
        button.showProgress(true);
    }

    public void startDeterminate() {
        button.setIndeterminate(false);
        button.resetIcon();
        button.showShadow(false);
        currentProgress = 0;
        button.showProgress(true);
        button.setProgress(currentProgress);
        getRunnable(activity).run();
    }


}