package org.edx.mobile.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.widget.IconImageView;

import org.edx.mobile.R;
import org.edx.mobile.base.BaseFragment;
import org.edx.mobile.core.IEdxEnvironment;
import org.edx.mobile.model.api.EnrolledCoursesResponse;

import roboguice.inject.InjectExtra;

public class AdditionalResourcesFragment extends BaseFragment {
    @Inject
    private IEdxEnvironment environment;

    @InjectExtra(Router.EXTRA_COURSE_DATA)
    private EnrolledCoursesResponse courseData;

    public static AdditionalResourcesFragment newInstance() {
        AdditionalResourcesFragment fragment = new AdditionalResourcesFragment();
        return fragment;
    }

    public AdditionalResourcesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_additional_resources, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        ViewHolder holder;
        final LinearLayout parent = (LinearLayout) view.findViewById(R.id.root);

        holder = createViewHolder(inflater, parent);

        holder.typeView.setIcon(FontAwesomeIcons.fa_file_text_o);
        holder.titleView.setText(R.string.handouts_title);
        holder.subtitleView.setText(R.string.handouts_subtitle);
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseData != null) {
                    environment.getRouter().showHandouts(getActivity(), courseData);
                }
            }
        });

        holder = createViewHolder(inflater, parent);

        holder.typeView.setIcon(FontAwesomeIcons.fa_bullhorn);
        holder.titleView.setText(R.string.announcement_title);
        holder.subtitleView.setText(R.string.announcement_subtitle);
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseData != null) {
                    environment.getRouter().showCourseAnnouncement(getActivity(), courseData);
                }
            }
        });
    }

    private ViewHolder createViewHolder(LayoutInflater inflater, LinearLayout parent) {
        ViewHolder holder = new ViewHolder();
        holder.rowView = inflater.inflate(R.layout.row_course_dashboard_list, parent, false);
        holder.typeView = (IconImageView) holder.rowView.findViewById(R.id.row_type);
        holder.titleView = (TextView) holder.rowView.findViewById(R.id.row_title);
        holder.subtitleView = (TextView) holder.rowView.findViewById(R.id.row_subtitle);
        parent.addView(holder.rowView);
        return holder;
    }

    private class ViewHolder {
        View rowView;
        IconImageView typeView;
        TextView titleView;
        TextView subtitleView;
    }
}
