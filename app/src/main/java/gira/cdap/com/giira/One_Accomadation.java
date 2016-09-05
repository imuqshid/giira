package gira.cdap.com.giira;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gira.cdap.com.giira.Task.DownloadImageTask;
import gira.cdap.com.giira.Task.getAccommodation;

public class One_Accomadation extends AppCompatActivity {

String accid,name,address,description,thumb,website,phoneNo,Email;

    ImageView IVaccimage,IVaccwebsite;
    TextView TVAccname,TVAccDesc,TVAccAddress,TVAccWebsite,TVAccPhone,TVAccEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one__accomadation);

        initalize();

        accid=getIntent().getExtras().getString("id");
        getAccommodation getAccommodation=new getAccommodation(getApplicationContext(),accid,One_Accomadation.this);
        getAccommodation.execute();





    }

    public void initalize()
    {
        IVaccimage=(ImageView)findViewById(R.id.ivaccimage);
        TVAccname=(TextView)findViewById(R.id.tvaccname);
        TVAccDesc=(TextView)findViewById(R.id.tvaccdescription);
        TVAccAddress=(TextView)findViewById(R.id.tvaccaddress);
        TVAccWebsite=(TextView)findViewById(R.id.tvaccwebsite);
        TVAccPhone=(TextView)findViewById(R.id.tvaccphoneno);
        TVAccEmail=(TextView)findViewById(R.id.tvaccemail);
        IVaccwebsite=(ImageView)findViewById(R.id.ivwebsiteacc);

    }

    public void setAccommodation(String name,String address,String descri, final String website,String phoneNO,String email,String thumb){
        this.name=name;
        this.address=address;
        this.description=descri;
        this.thumb=thumb;
        this.website=website;
        this.phoneNo=phoneNO;
        this.Email=email;

        TVAccname.setText(name);
        TVAccAddress.setText(address);
        TVAccDesc.setText(descri);
        TVAccWebsite.setText(website);
        TVAccPhone.setText(phoneNO);
        TVAccEmail.setText(email);


//        String url = new String(thumb);

        new DownloadImageTask(IVaccimage).execute(thumb);
//        InputStream is=new URL(url).openStream();
//        Bitmap bmp = BitmapFactory.decodeStream(is);
//        imageView.setImageBitmap(bmp);

        IVaccwebsite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(website));
                startActivity(intent);
            }
        });

    }

}
