package org.edx.mobile.view;

import android.support.v4.app.Fragment;

import org.edx.mobile.base.BaseSingleFragmentActivity;

class CourseTabsDashboardActivity extends BaseSingleFragmentActivity {
    @Override
    public Fragment getFirstFragment() {
        return CourseTabsDashboardFragment.newInstance();
    }
}
