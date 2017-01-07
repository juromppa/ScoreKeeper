package com.gmail.romppainen.matti.juha.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juha on 28.12.2016.
 */

public class MainActivityTabFragment4 extends Fragment {

    private DatabaseOperations mydb;
    private RecyclerView recyclerView;
    private TextView emptyView;

    public MainActivityTabFragment4() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DatabaseOperations(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.main_activity_tab_fragment_4, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_main_fragment_4);
        emptyView = (TextView) rootView.findViewById(R.id.rv_main_fragment_4_empty_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Course> courses = mydb.getAllCourses();
        MyAdapter adapter = new MyAdapter(courses);
        recyclerView.setAdapter(adapter);

        if (courses.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_new_course:
                DialogNewCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void DialogNewCourse() {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<Course> courses;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView par;

            MyViewHolder(View v) {
                super(v);

                name = (TextView) v.findViewById(R.id.course_list_name);
                par = (TextView) v.findViewById(R.id.course_list_par);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        MyAdapter(List data) {
            courses = data;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_item,
                    parent, false);
            // set the view's size, margins, paddings and layout parameters
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    Intent i = new Intent(getActivity(), CourseActivity.class);
                    i.putExtra("Course", courses.get(itemPosition));
                    startActivity(i);
                }
            });
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.name.setText(courses.get(position).getName());
            holder.par.setText(courses.get(position).getPar());
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }
}