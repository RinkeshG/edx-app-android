package org.edx.mobile.model;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.joanzapata.iconify.Icon;

public class FragmentItemModel {
    @NonNull
    private final Fragment fragment;
    @NonNull
    private final String title;
    @NonNull
    private final Icon icon;

    public FragmentItemModel(@NonNull Fragment fragment, @NonNull String title, @NonNull Icon icon) {
        this.fragment = fragment;
        this.title = title;
        this.icon = icon;
    }

    @NonNull
    public Fragment getFragment() {
        return fragment;
    }

    @NonNull
    public Icon getIcon() {
        return icon;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}
