package com.example.test.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.ManageAddresses.ManageAddresses;
import com.example.test.Model.UserDetails;
import com.example.test.OrderCart.Cart;
import com.example.test.OrderCart.Myorders;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.adapter.PlansPagerAdapter;
import com.example.test.util.utility;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home_Screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TabLayout tab;
    ViewPager viewPager;
    Toolbar toolbar;
    EditText locationtext;
    TextView toolbarTitle;
    ArrayList<String> tabTitle = new ArrayList<>();
    ArrayList<HashMap<String, String>> cat_list = new ArrayList<>();
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    LinearLayout bottomsheet;
    TextView cart_amout,items_total;
    LinearLayout cart__product;

    TextView username; //added by Lalit kumar on 20.march.2020 at 05:52 PM

    Intent intent;
    SessionManager sessionManager;
    MenuItem nav_login, nav_profile, nav_orders, nav_address_book;
    UserDetails userDetails; //added by Lalit kumar on 20.march.2020 at 05:53 PM

   DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        databaseHelper=new DatabaseHelper(this);

        intent = getIntent();
        if (intent != null)
        {
            try
            {
                cat_list = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("All_cat_list");
            }
            catch (Exception e)
            {

            }

        }
        init();
        tabTitle.clear();
        setSupportActionBar(toolbar);
        setupToolbar();
        if (cat_list != null && !cat_list.isEmpty())
        {
            for (int l = 0; l < cat_list.size(); l++)
            {
                tab.addTab(tab.newTab().setText(cat_list.get(l).get("name")));
                tabTitle.add(cat_list.get(l).get("name"));
            }
        }
        else
            {
            get_all_category(this);
        }


        PlansPagerAdapter adapter = new PlansPagerAdapter(getSupportFragmentManager(), tab.getTabCount(), tabTitle, cat_list,bottomsheet, cart_amout,items_total);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        try {
            if(databaseHelper.get_the_total_quantity()!=null)
            {
                if(Integer.parseInt(databaseHelper.get_the_total_quantity()) >0)
                {
                    bottomsheet.setVisibility(View.VISIBLE);
                    String quintiy = databaseHelper.get_the_total_quantity();
                    String amount=databaseHelper.get_the_total_amount();
                    cart_amout.setText("₹"+amount);
                    items_total.setText(""+quintiy+" Item");
                }
                else
                {
                    bottomsheet.setVisibility(View.GONE);
                }

            }

        }
        catch (Exception e)
        {
            bottomsheet.setVisibility(View.GONE);
        }



    }


    public void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void init()
    {
        cart__product=(LinearLayout)findViewById(R.id.cart__product);
        cart_amout = findViewById(R.id.cart_amout);
        items_total = findViewById(R.id.items_total);
        tab = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomsheet = findViewById(R.id.bottom_sheet);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Home_Screen.this);
        View header = navigationView.getHeaderView(0);
        sessionManager = new SessionManager(Home_Screen.this);
        Menu menu = navigationView.getMenu();
        nav_profile=menu.findItem(R.id.profile);
        nav_orders=menu.findItem(R.id.orders);
        nav_address_book=menu.findItem(R.id.address_book);

        if(sessionManager.isLoggedIn()==false){
            nav_profile.setVisible(false);
            nav_address_book.setVisible(false);
            nav_orders.setVisible(false);
        }

        //below is added by Lalit kumar on 20.march.2020 at 17:58 PM
        nav_login = menu.findItem(R.id.loginbtn);

        if (sessionManager.isLoggedIn())
        {
            nav_login.setTitle("Logout");
        }
        else
            {
            nav_login.setTitle("Login");
            }
        username = header.findViewById(R.id.customer_name);
        userDetails = sessionManager.getUserSession();
        if (userDetails.getUsername() != null) {
            username.setText(userDetails.getUsername());
        }
        //added above by Lalit kumar on 20.march.2020 at 17:58 PM

        try
        {
            if(databaseHelper.get_the_total_quantity()!=null)
            {
                if(Integer.parseInt(databaseHelper.get_the_total_quantity()) >0)
                {
                    bottomsheet.setVisibility(View.VISIBLE);
                    String quintiy = databaseHelper.get_the_total_quantity();
                    String amount=databaseHelper.get_the_total_amount();
                    cart_amout.setText("₹"+amount);
                    items_total.setText(""+quintiy+" Item");
                }
                else
                {
                    bottomsheet.setVisibility(View.GONE);
                }

            }

        }
        catch (Exception e)
        {
            bottomsheet.setVisibility(View.GONE);
        }


       //methord for cart-->
        cart__product.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(utility.isOnline(Home_Screen.this)==true)
                {
                    if (sessionManager.isLoggedIn())
                    {
                        Intent in = new Intent(Home_Screen.this,Cart.class);
                        startActivity(in);

                    }
                    else
                    {

                        Toast.makeText(Home_Screen.this,"kindly login first!",Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Home_Screen.this,Login.class);
                        startActivity(in);

                    }
                }
                else
                {
                    Toast.makeText(Home_Screen.this,"kindly check your internet connection!",Toast.LENGTH_LONG).show();

                }




            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

            switch (id) {
                case R.id.orders:
                    if(sessionManager.isLoggedIn())
                    {
                        onBackPressed();
                        startActivity(new Intent(this, Myorders.class));
                        break;
                    }
                    else
                    {
                        Toast.makeText(this, "Please Login to see your Orders", Toast.LENGTH_SHORT).show();

                        break;
                    }

                case R.id.about:
                    Intent i = new Intent(this, AboutUs.class);
                    onBackPressed();
                    startActivity(i);

                    break;
                case R.id.contact:
                    Intent intent = new Intent(this, Contactus.class);
                    onBackPressed();
                    startActivity(intent);


                    break;
                case R.id.profile:
                    if(sessionManager.isLoggedIn())
                    {
                        userDetails=sessionManager.getUserSession();
                        Intent intent1 = new Intent(this, Customer_Profile.class);
                        intent1.putExtra("user_name",userDetails.getUsername());
                        onBackPressed();
                        startActivity(intent1);
                        break;
                    }
                    else{
                        Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
                        break;
                    }

                case R.id.address_book:
                    Intent i3 = new Intent(this, My_Adrress.class);
                    onBackPressed();
                    startActivity(i3);
                    break;
                case R.id.loginbtn:
                    //added by Lalit kumar on 20.march.2020 at 03:00 PM
                    if (sessionManager.isLoggedIn()) {
                        nav_login.setTitle("Logout");
                        sessionManager.logoutUser();
                        databaseHelper.delet_database();
                        Intent in=new Intent(Home_Screen.this,Home_Screen.class);
                        in.putExtra("All_cat_list",cat_list);
                        startActivity(in);
                        finish();
                    }
                    else
                    {

                        nav_login.setTitle("Login");
                        Intent i4 = new Intent(this, Login.class);
                        onBackPressed();
                        startActivity(i4);
                        break;
                    }
                    //added by Lalit kumar on 20.march.2020 at 03:00 PM

            }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cart, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    public void get_all_category(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

        String url = "http://3.6.27.167/api/cat/category-list";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());

                                int status = object.getInt("status");

                                if (status == 200) {

                                    if (cat_list != null) {
                                        cat_list.clear();
                                    }

                                    JSONArray jsonArray = object.getJSONArray("result");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        HashMap<String, String> cat_list_hash = new HashMap<String, String>();
                                        cat_list_hash.clear();
                                        String id = obj.getString("id");
                                        String name = obj.getString("name");
                                        String slug = obj.getString("slug");
                                        cat_list_hash.put("id", id);
                                        cat_list_hash.put("name", name);
                                        cat_list_hash.put("slug", slug);
                                        if (cat_list != null) {
                                            cat_list.add(cat_list_hash);
                                        }

                                    }
                                    if (cat_list != null && cat_list.size() > 0) {
                                        for (int l = 0; l < cat_list.size(); l++) {
                                            tab.addTab(tab.newTab().setText(cat_list.get(l).get("name")));
                                            tabTitle.add(cat_list.get(l).get("name"));
                                        }
                                        tab.notify();
                                    }


                                } else {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nsyskey", "082057a4d249514389bb90c6d50ecf23");
                params.put("catid", "");

                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

}
