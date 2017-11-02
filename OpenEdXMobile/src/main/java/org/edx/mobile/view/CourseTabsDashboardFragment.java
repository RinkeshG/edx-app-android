package org.edx.mobile.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import org.edx.mobile.R;
import org.edx.mobile.base.BaseFragment;
import org.edx.mobile.core.IEdxEnvironment;
import org.edx.mobile.databinding.FragmentCourseTabsDashboardBinding;
import org.edx.mobile.logger.Logger;
import org.edx.mobile.model.FragmentItemModel;
import org.edx.mobile.model.api.EnrolledCoursesResponse;
import org.edx.mobile.util.FileUtil;
import org.edx.mobile.util.ResourceUtil;
import org.edx.mobile.view.adapters.FragmentItemPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectExtra;

public class CourseTabsDashboardFragment extends BaseFragment {
    protected final Logger logger = new Logger(getClass().getName());

    private FragmentCourseTabsDashboardBinding binding;

    @Inject
    IEdxEnvironment environment;

    @InjectExtra(Router.EXTRA_COURSE_DATA)
    private EnrolledCoursesResponse courseData;


    @NonNull
    public static CourseTabsDashboardFragment newInstance() {
        return new CourseTabsDashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(courseData.getCourse().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_tabs_dashboard, container, false);
        initializeTabs();
        return binding.getRoot();
    }

    public void initializeTabs() {
        // Get Frags list
        final List<FragmentItemModel> frags = getTabsFragsList();
        // Init tabs
        final TabLayout tabLayout = binding.tabLayout;
        TabLayout.Tab tab;
        for (FragmentItemModel fragmentItem : frags) {
            tab = tabLayout.newTab();
            IconDrawable iconDrawable = new IconDrawable(getContext(), fragmentItem.getIcon());
            iconDrawable.colorRes(getContext(), R.color.edx_brand_primary_base);
            tab.setIcon(iconDrawable);
            tab.setContentDescription(fragmentItem.getTitle());
            tabLayout.addTab(tab);
        }
        // Init view pager
        final FragmentItemPagerAdapter adapter = new FragmentItemPagerAdapter(this.getActivity().getSupportFragmentManager(), frags);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                getActivity().setTitle(frags.get(position).getTitle());
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public List<FragmentItemModel> getTabsFragsList() {
        ArrayList<FragmentItemModel> frags = new ArrayList<>();
        frags.add(new FragmentItemModel(new TestFragment(), courseData.getCourse().getName(), FontAwesomeIcons.fa_list_alt));
        frags.add(new FragmentItemModel(new TestFragment(), "Videos", FontAwesomeIcons.fa_film));
        frags.add(new FragmentItemModel(CourseDiscussionTopicsFragment.newInstance(), "Discussions", FontAwesomeIcons.fa_comments_o));
        frags.add(new FragmentItemModel(getCourseDatesFragment(), "Important Dates", FontAwesomeIcons.fa_calendar));
        frags.add(new FragmentItemModel(AdditionalResourcesFragment.newInstance(), "Additional Resources", FontAwesomeIcons.fa_ellipsis_h));
        return frags;
    }

    public Fragment getCourseDatesFragment() {
        final StringBuilder courseInfoUrl = new StringBuilder(64);
        courseInfoUrl.append(environment.getConfig().getApiHostURL())
                .append("/courses/")
                .append(courseData.getCourse().getId())
                .append("/info");
        String javascript;
        try {
            javascript = FileUtil.loadTextFileFromAssets(getContext(), "js/filterHtml.js");
        } catch (IOException e) {
            logger.error(e);
            javascript = null;
        }
        if (!TextUtils.isEmpty(javascript)) {
            final CharSequence functionCall = ResourceUtil.getFormattedString(
                    "filterHtmlByClass('date-summary-container', '{not_found_message}');",
                    "not_found_message", getString(R.string.no_course_dates_to_display)
            );
            // Append function call in javascript
            javascript += functionCall;
        }
        return AuthenticatedWebViewFragment.newInstance(courseInfoUrl.toString(), javascript);
    }

    public static class TestFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView tv = new TextView(getContext());
            tv.setText("Content coming soon!");
            tv.setGravity(Gravity.CENTER);
            return tv;
        }
    }
}
