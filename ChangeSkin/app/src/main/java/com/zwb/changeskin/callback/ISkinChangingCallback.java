package com.zwb.changeskin.callback;

/**
 * Created by zwb
 * Description
 * Date 2017/6/16.
 */

public interface ISkinChangingCallback {

    void onStart();

    void onError();

    void onComplete();

    public static DefaultSkinChangingCallback DEFAULT_SKIN_CHANGING_CALLBACK = new DefaultSkinChangingCallback();

    public class DefaultSkinChangingCallback implements ISkinChangingCallback {
        @Override
        public void onStart() {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onComplete() {

        }
    }
}
