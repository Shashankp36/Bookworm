package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Final_date_time extends AppCompatActivity {

    public static ArrayList<SampleData> finalDataList;
    private Myadapter simpleadapter;
    private Button main_page;

    String meeting_id;
    String meeting_name;
    ArrayList<String> date_arr = new ArrayList<String>();
    ArrayList<String> time_arr = new ArrayList<String>();
    ArrayList<String> acceptor_names = new ArrayList<String>();
    ArrayList<Integer> acceptor_no = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_date_time);
        this.InitializeTimeData();

        ListView listView = (ListView)findViewById(R.id.listView);
        simpleadapter = new Myadapter(this,finalDataList);
        listView.setAdapter(simpleadapter);

        TextView code=findViewById(R.id.code);
        TextView name=findViewById(R.id.name);
        TextView group_name=findViewById(R.id.GroupName);

        TextView final_date_time=findViewById(R.id.Final_date_time);

        main_page=findViewById(R.id.Button9);


        group_name.setText("Meeting Name: "+meeting_name);
        code.setText("Meeting ID: "+meeting_id);

        main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }

    private void InitializeTimeData() {
        Bundle bundle = getIntent().getExtras();
        meeting_id = bundle.getString("meeting_id");
        meeting_name = bundle.getString("meeting_name");
        date_arr= bundle.getStringArrayList("date_arr");
        time_arr= bundle.getStringArrayList("time_arr");
        acceptor_names= bundle.getStringArrayList("acceptor_names");
        acceptor_no= bundle.getIntegerArrayList("acceptor_number");



        finalDataList = new ArrayList<SampleData>();

       for(int j=0; j<time_arr.size(); j++) {
           finalDataList.add(new SampleData(date_arr.get(j), time_arr.get(j)+ "\n("+ acceptor_names.get(j)+")" + "\n" +"Number of acceptors: " + Integer.toString(acceptor_no.get(j))));
       }



    }

}

