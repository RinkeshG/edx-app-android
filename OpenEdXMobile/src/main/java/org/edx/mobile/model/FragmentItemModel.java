package org.edx.mobile.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.joanzapata.iconify.Icon;

/**
 * A class containing all data required to {@link android.support.v4.view.PagerAdapter PagerAdapter}
 * and {@link android.support.design.widget.TabLayout TabLayout} to initialize a fragment/tab.
 */
public class FragmentItemModel {
    private static String ERROR_MSG_INSTANTIATION = "Unable to instantiate fragment %s: make " +
            "sure class name exists, is public, and has an empty constructor that is public.";

    @NonNull
    private final Class<? extends Fragment> fragmentClass;
    @NonNull
    private final CharSequence title;
    @Nullable
    private final Icon icon;
    @Nullable
    private final Bundle args;
    @Nullable
    private FragmentStateListener listener;

    public FragmentItemModel(@NonNull Class<? extends Fragment> fragmentClass, @NonNull CharSequence title) {
        this(fragmentClass, title, null, null, null);
    }

    public FragmentItemModel(@NonNull Class<? extends Fragment> fragmentClass,
                             @NonNull CharSequence title, Icon icon, FragmentStateListener listener) {
        this(fragmentClass, title, icon, null, listener);
    }

    public FragmentItemModel(@NonNull Class<? extends Fragment> fragmentClass, @NonNull CharSequence title,
                             Icon icon, Bundle args, FragmentStateListener listener) {
        if (args != null) {
            args.setClassLoader(fragmentClass.getClassLoader());
        }
        this.fragmentClass = fragmentClass;
        this.title = title;
        this.icon = icon;
        this.args = args;
        this.listener = listener;
    }

    @NonNull
    public CharSequence getTitle() {
        return title;
    }

    @Nullable
    public Icon getIcon() {
        return icon;
    }

    @Nullable
    public FragmentStateListener getListener() {
        return listener;
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

    public interface FragmentStateListener {
        void onFragmentSelected();
    }
}
