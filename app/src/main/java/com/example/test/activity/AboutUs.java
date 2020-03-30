package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.RangeValueIterator;
import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {

   Toolbar toolbar;
  public static WebView webView;
  public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbar=findViewById(R.id.aboutusToolbar);


      toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Element adselement = new Element();
        adselement.setTitle("Advertize here");

        View aboutuspage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.finallogo)
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(adselement)
                .addGroup("Contact with us ")
                .addEmail("nsyshospitality@gmail.com")
                .addWebsite("biryanibychoice.com")
                .addPlayStore("Biryani By Choice")
                .addFacebook("BiryaniByChoice")
                .addItem(CreateCopyright())
                .create();
            setContentView(aboutuspage);
    }

   public Element CreateCopyright(){
        Element Copyright = new Element();
        final String copyright =String.format("Copyright %d by BiryaniByChoice", Calendar.getInstance().get(Calendar.YEAR));
        Copyright.setTitle(copyright);
        Copyright.setIcon(R.mipmap.ic_launcher);
        Copyright.setGravity(Gravity.CENTER);
        Copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUs.this, copyright, Toast.LENGTH_SHORT).show();
            }
        });

    return Copyright;
    }

}
