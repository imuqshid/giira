package gira.cdap.com.giira;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import gira.cdap.com.giira.Task.serverURL;
import gira.cdap.com.giira.activity.LoginActivity;
import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
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

    String list_userid,list_username;

    AutoCompleteTextView autoCompleteTextView;
    private RecyclerView mRecyclerView,mRecyclerViewRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initalize();


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

        ImageButton notificationIcon = (ImageButton) findViewById(R.id.notificationicon);
        notificationIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        NotificationLayout.class);
                startActivity(i);
                finish();
            }
        });

        ImageButton homeIcon = (ImageButton) findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "You already in Dashboard", Toast.LENGTH_LONG)
                        .show();
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

    public void initalize()
    {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteUser);
        autoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this,   R.layout.list_user_card, R.id.results_user));
        autoCompleteTextView.setOnItemClickListener(this);


    }

    public ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        ArrayList resultgemorart=null;




        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            StringBuilder sb = new StringBuilder(serverURL.local_host_url+"giira/index.php/friends/retrivefriends?user="+input);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("user");


            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("name"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("name"));
                list_userid=predsJsonArray.getJSONObject(i).getString("id");
                list_username=predsJsonArray.getJSONObject(i).getString("name");
//                Toast.makeText(this, placeid, Toast.LENGTH_SHORT).show();


//                resultgemorart.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
            }
        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        //resultList.add("Add new Place");

        return resultList;
    }


    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        Intent intent=new Intent(this,UserProfileActivity.class);
        intent.putExtra("id", list_userid);
        intent.putExtra("name", list_username);

        startActivity(intent);

    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

//        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, int textView1) {
//            super(context,textView1);
//        }

        public GooglePlacesAutocompleteAdapter(Context context, int resource,
                                               int textViewResourceId) {

            super(context, resource, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();


                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }


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
