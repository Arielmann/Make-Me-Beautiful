package com.example.home.makemebeautiful.user_profile.user_info;

import android.support.v4.app.Fragment;

/**
 * Created by home on 8/7/2016.
 */
public abstract class GenericSettingsFrag extends Fragment {
    protected abstract void tryToUpdateData();
    private GenericSettingsModel model;

    public GenericSettingsModel getModel() {
        return model;
    }

    public void setModel(GenericSettingsModel model) {
        this.model = model;
    }
}
