package org.edx.mobile.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.edx.mobile.base.BaseSingleFragmentActivity;


/**
 * Top level outline for the Course
 */
public class NewCourseOutlineActivity extends BaseSingleFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Handouts activity should not contain the drawer(Navigation Fragment).
        blockDrawerFromOpening();
    }

    @Override
    public Fragment getFirstFragment() {
        final Fragment fragment = new NewCourseOutlineFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }
}
