package gira.cdap.com.giira;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import gira.cdap.com.giira.Task.AddEventTask;
import gira.cdap.com.giira.Task.AddTeamTask;

/**
 * Created by Muqshid on 7/30/2016.
 */
public class NewTeam extends AppCompatActivity implements View.OnClickListener {

    private TextView pageTitle;
    private static final int ADD_MEMBER = 9000;
    EditText name,description,mem1,mem2,mem3, mem4,mem5,mem6,mem7,mem8,mem9, mem10;
    int count =1;

    ImageButton homeIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_team);

        pageTitle = (TextView)findViewById(R.id.pageTitle);
        pageTitle.setText("New Team");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        EventPlanningFragment.class);
                startActivity(i);
                finish();


            }
        });

        getElements();


        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
        TextView tv = new TextView(this);
        tv.setVisibility(View.VISIBLE);
        tv.setText("Add member");
        tv.setTextSize(15);
        tv.setPaddingRelative(25,0,25,0);
        tv.setTextColor(Color.BLUE);
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        tv.setId(ADD_MEMBER);
        tv.setOnClickListener(this);

        ll.addView(tv);



    }


    @Override
    public void onClick(View v) {
        //add TextInput Layouts

        for(int i = 0; i < 1; i++) {

            LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
            TextInputEditText ti = new TextInputEditText(this);
            ti.setHint("Member Name " + count);
            ti.setId(count);
            ti.setTextSize(15);
            ti.setPaddingRelative(25,48,25,48);
            ti.setVisibility(View.VISIBLE);
            ll.addView(ti);
            count++;
//            Toast toast = Toast.makeText(this, "New Member field Added!", Toast.LENGTH_LONG);

        }
    }

    private void getElements() {

        name= (EditText) findViewById(R.id.name);
        description= (EditText) findViewById(R.id.description);
    }

    public void addTeam(View v){
        String namef=name.getText().toString();
        String descriptionf=description.getText().toString();

        if (mem1 != null || mem2 != null || mem3 != null || mem4 != null || mem5 != null){
            String mem1f = mem1.getText().toString();
            String mem2f = mem2.getText().toString();
            String mem3f = mem3.getText().toString();
            String mem4f = mem4.getText().toString();
            String mem5f = mem5.getText().toString();

            AddTeamTask addTeamTask = new AddTeamTask(getApplicationContext(), namef, descriptionf, mem1f, mem2f, mem3f, mem4f, mem5f, this);
            addTeamTask.execute();
            Toast toast = Toast.makeText(this, "With Mem", Toast.LENGTH_LONG);


        }

        else
        {
            AddTeamTask addTeamTask = new AddTeamTask(getApplicationContext(), namef, descriptionf, this);
            addTeamTask.execute();
            Toast toast = Toast.makeText(this, "Without Mem", Toast.LENGTH_LONG);
        }



    }
}
