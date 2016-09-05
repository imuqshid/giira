package gira.cdap.com.giira;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import gira.cdap.com.giira.activity.LoginActivity;
import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab;
    private ImageButton btnLinknear;
    private static final int PLACE_PICKER_REQUEST = 1;

    private SQLiteHandler db;
    private SessionManager session;

    ImageButton homeIcon;
    ImageButton tourIcon;
    ImageButton eventIcon;
    ImageButton disasterIcon;
    ImageButton profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }



        tourIcon = (ImageButton) findViewById(R.id.tourIcon);
        tourIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        TourFragment.class);
                startActivity(i);
                finish();
            }
        });

        eventIcon = (ImageButton) findViewById(R.id.eventIcon);
        eventIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        EventPlanningFragment.class);
                startActivity(i);
                finish();
            }
        });

        disasterIcon = (ImageButton) findViewById(R.id.disasterIcon);
        disasterIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        DisasterFragment.class);
                startActivity(i);
                finish();
            }
        });

        profileIcon = (ImageButton) findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        ProfileFragment.class);
                startActivity(i);
                finish();
            }
        });

        btnLinknear = (ImageButton) findViewById(R.id.checkin);

        btnLinknear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        CheckinActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
