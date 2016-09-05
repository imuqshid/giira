package gira.cdap.com.giira.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.NewTeam;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;


/**
 * Created by Perceptor on 6/13/2016.
 */

public class AddTeamTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String name,description,member1,member2,member3,member4,member5;
    NewTeam newTeam;

    Context context;


    public AddTeamTask(Context context, String name, String description, String member1, String member2, String member3, String member4, String member5, NewTeam newTeam)
    {
        this.context=context;
        this.name=name;
        this.description=description;
        this.member1=member1;
        this.member2=member2;
        this.member3=member3;
        this.member4=member4;
        this.member5=member5;
        this.newTeam=newTeam;
        /*this.member6=member6;
        this.member7=member7;
        this.member8=member8;
        this.member9=member9;
        this.member10=member10;*/

    }

    public AddTeamTask(Context context, String name, String description, NewTeam newTeam)
    {
        this.context=context;
        this.name=name;
        this.description=description;
        this.newTeam=newTeam;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();

        value.add(new BasicNameValuePair("name", name));
        value.add(new BasicNameValuePair("description", description));
        value.add(new BasicNameValuePair("member1", member1));
        value.add(new BasicNameValuePair("member2", member2));
        value.add(new BasicNameValuePair("member3", member3));
        value.add(new BasicNameValuePair("member4", member4));
        value.add(new BasicNameValuePair("member5", member5));/*
        value.add(new BasicNameValuePair("member6", member6));
        value.add(new BasicNameValuePair("member7", member7));
        value.add(new BasicNameValuePair("member8", member8));
        value.add(new BasicNameValuePair("member9", member9));
        value.add(new BasicNameValuePair("member10", member10));*/
        android.util.Log.d("Task","AddTeamTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/event/addteam",2,
                value);
        parsing = new JSONParser();

        try {
            JSONObject json = parsing.getJSONFromResponse(inputStream);
            result = String.valueOf(json.getBoolean("response"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result.matches("true"))

        {
            if(newTeam!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            if(newTeam!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
