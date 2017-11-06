package org.edx.mobile.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.joanzapata.iconify.Icon;

/**
 * Data class representing a page.
 */
public class Item {
    private static String ERROR_MSG_INSTANTIATION = "Unable to instantiate fragment %s: make " +
            "sure class name exists, is public, and has an empty constructor that is public.";

    @NonNull
    private final Class<? extends Fragment> fragmentClass;
    @Nullable
    private final Bundle args;
    @NonNull
    private final CharSequence title;
    @Nullable
    private final Icon icon;

    public Item(@NonNull Class<? extends Fragment> fragmentClass, @NonNull CharSequence title) {
        this(fragmentClass, null, title, null);
    }

    public Item(@NonNull Class<? extends Fragment> fragmentClass, Bundle args,
                @NonNull CharSequence title, Icon icon) {
        if (args != null) {
            args.setClassLoader(fragmentClass.getClassLoader());
        }
        this.fragmentClass = fragmentClass;
        this.args = args;
        this.title = title;
        this.icon = icon;
    }

    @NonNull
    public CharSequence getTitle() {
        return title;
    }

    @Nullable
    public Icon getIcon() {
        return icon;
    }

    public Fragment generateFragment() {
        final Fragment fragment;
        try {
            fragment = fragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new Fragment.InstantiationException(
                    String.format(ERROR_MSG_INSTANTIATION, fragmentClass), e);
        } catch (IllegalAccessException e) {
            throw new Fragment.InstantiationException(
                    String.format(ERROR_MSG_INSTANTIATION, fragmentClass), e);
        }
        fragment.setArguments(args);
        return fragment;
    }
}
