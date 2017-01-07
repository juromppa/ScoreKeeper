package com.gmail.romppainen.matti.juha.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Juha on 7.1.2017.
 */

public class CourseActivity extends AppCompatActivity  {

    private DatabaseOperations mydb;
    private Course course;
    private TextView course_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent i = getIntent();
        course = i.getParcelableExtra("Course");

        mydb = new DatabaseOperations(this);

        course_name = (TextView) findViewById(R.id.course_activity_name);
        course_name.setText(course.getName());


        TextView created = (TextView) findViewById(R.id.course_activity_created);
        created.setText(Utils.ConvertDate(course.getCreated()));

        TextView par = (TextView) findViewById(R.id.course_activity_par);
        par.setText(course.getPar());
    }
}
