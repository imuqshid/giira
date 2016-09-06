package gira.cdap.com.giira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Muqshid on 9/6/2016.
 */
public class NotificationLayout extends Activity {
    private ImageButton homeIcon;
    private TextView pageTitle;
    TextView pageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notofication);

        /*pageIcon = (TextView) findViewById(R.id.pageIcon);
        pageIcon.setText(R.drawable.disaster);*/

        pageTitle = (TextView) findViewById(R.id.pageTitle);
        pageTitle.setText("Notifications");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}