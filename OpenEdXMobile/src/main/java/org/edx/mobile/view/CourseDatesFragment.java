package org.edx.mobile.view;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.edx.mobile.core.IEdxEnvironment;
import org.edx.mobile.model.api.EnrolledCoursesResponse;

public class CourseDatesFragment extends AuthenticatedWebViewFragment {
    public static Bundle getArguments(@NonNull IEdxEnvironment environment,
                                      @NonNull EnrolledCoursesResponse courseData) {
        final StringBuilder courseInfoUrl = new StringBuilder(64);
        courseInfoUrl.append(environment.getConfig().getApiHostURL())
                .append("/courses/")
                .append(courseData.getCourse().getId())
                .append("/course/mobile_dates_fragment");
        Bundle args = new Bundle();
        args.putString(ARG_URL, courseInfoUrl.toString());
        return args;
    }

    public static CourseDatesFragment newInstance(@NonNull IEdxEnvironment environment,
                                                  @NonNull EnrolledCoursesResponse courseData) {
        CourseDatesFragment fragment = new CourseDatesFragment();
        fragment.setArguments(getArguments(environment, courseData));
        return fragment;
    }
}
