package com.example.lab4_nuratiqah;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonAdd;
    ListView listViewCourses;

    DatabaseReference databaseCourses;

    ArrayList<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        // connect database
        databaseCourses = FirebaseDatabase.getInstance().getReference("courses");

        // link UI
        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAddData);
        listViewCourses = findViewById(R.id.ListViewCourses);

        courseList = new ArrayList<>();

        // button click
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });
    }

    private void addCourse() {

        String name = editTextName.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {

            String id = databaseCourses.push().getKey();

            Course course = new Course(id, name);

            databaseCourses.child(id).setValue(course);

            Toast.makeText(this, "Data added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please enter data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                courseList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Course course = postSnapshot.getValue(Course.class);
                    courseList.add(course);
                }

                CourseList adapter = new CourseList(MainActivity.this, courseList);
                listViewCourses.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}