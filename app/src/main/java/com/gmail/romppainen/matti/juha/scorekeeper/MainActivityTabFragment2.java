package com.gmail.romppainen.matti.juha.scorekeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Juha on 28.12.2016.
 */

public class MainActivityTabFragment2 extends Fragment {

    String[] values = new String[]{"test one", "test two", "test three", "test four", "test " +
            "five", "test six" , "test seven", "test eight" , "test nine"};

    public MainActivityTabFragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.main_activity_tab_fragment_2, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_main_fragment_2);
        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(values);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            MyViewHolder(View v) {
                super(v);

                mTextView = (TextView) v.findViewById(R.id.course_name);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_item,
                    parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setText(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}