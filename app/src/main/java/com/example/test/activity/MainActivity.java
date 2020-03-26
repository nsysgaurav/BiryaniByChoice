package com.example.test.activity;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.BAckgrounddata.GetData;
import com.example.test.ManageAddresses.ManageAddresses;
import com.example.test.Model.FoodModel;
import com.example.test.Model.UserDetails;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ShimmerFrameLayout mShimmerViewContainer;
    private FusedLocationProviderClient client;
    private TabLayout tabbar;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText locText;
    private ImageButton manageAddress;
    Button add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn;

    TextView cart_amount, items_total, add_new_serv_name, add_new_serving_size, bottom_sheet_view_cart_btn;

    TextView username;
    ImageView user_image;
    LinearLayout bottomsheet;
    SwipeRefreshLayout pulltorefresh;
    RelativeLayout add_new_serving_layout;
    ArrayList<FoodModel> foodModels;
    final String url = "http://122.160.81.156:8081/biryaniweb/food";
    SessionManager sessionManager;

    UserDetails userDetails;


    Location loc;
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pulltorefresh = findViewById(R.id.pulltorefresh);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        cart_amount = findViewById(R.id.cart_amout);
        items_total = findViewById(R.id.items_total);
        bottomsheet = findViewById(R.id.bottom_sheet);
        add_new_serving_layout = findViewById(R.id.bottom_sheet_add_new_serv_layout);

        manageAddress = findViewById(R.id.locationButton);
        toolbar = findViewById(R.id.toolbar);
        locText = findViewById(R.id.locationText);
        client = LocationServices.getFusedLocationProviderClient(this);
        add_new_serv_btn = findViewById(R.id.add_new_serving_btn);
        repeat_last_serv_btn = findViewById(R.id.repeat_last_serving_btn);
        close_new_serv_layout_btn = findViewById(R.id.close_new_serv_layout_btn);
        add_new_serv_name = findViewById(R.id.serving_food_name);
        add_new_serving_size = findViewById(R.id.item_serving);
        bottom_sheet_view_cart_btn = findViewById(R.id.bottom_sheet_view_cart_btn);

        sessionManager = new SessionManager(MainActivity.this);

        //need to be change--->
        requestPerm();


        manageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Want to change Address");
                dialog.setMessage("Please select the Manage Address option to add Your address manually");
                dialog.setPositiveButton("Manage Address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, ManageAddresses.class);
                        startActivity(i);
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
        });


        tabbar = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.view_pager);

        //calling navigation view for the click listners


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.customer_name);
        user_image = header.findViewById(R.id.profileimage);

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Customer_Profile.class));
            }
        });


        mShimmerViewContainer.startShimmerAnimation();

        //added by Lalit kumar on 20.march.2020 at 17:17 PM
        userDetails = sessionManager.getUserSession();
        if (userDetails.getUsername() != null) {
            username.setText(userDetails.getUsername());
        }


        GetData data = new GetData();
        data.getalldata(url, getApplicationContext(), new GetData.CallBack() {
            @Override
            public void onSuccess(ArrayList<FoodModel> foodModelsAll, ArrayList<FoodModel> foodModelsveg, ArrayList<FoodModel> foodModelsegnon, ArrayList<FoodModel> foodlist_northindian) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                //For Tab view this View pager can be used
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPagerAdapter.addfragment(new Tab1(foodModelsAll, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                        add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                        bottom_sheet_view_cart_btn), "All");
                viewPagerAdapter.addfragment(new Tab2(foodModelsveg, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                        add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                        bottom_sheet_view_cart_btn), "Biryani");
                viewPagerAdapter.addfragment(new BlankFragment(foodModelsegnon, cart_amount, items_total, bottomsheet,
                        add_new_serving_layout, add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                        bottom_sheet_view_cart_btn), "Italian");

                viewPagerAdapter.addfragment(new Italian_fragment(foodlist_northindian, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                        add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                        bottom_sheet_view_cart_btn), "North indian");
                viewPager.setAdapter(viewPagerAdapter);
                tabbar.setupWithViewPager(viewPager);
            }
        });


        setupToolbar();
        pulltorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetData data = new GetData();
                data.getalldata(url, getApplicationContext(), new GetData.CallBack() {
                    @Override
                    public void onSuccess(ArrayList<FoodModel> foodModelsAll, ArrayList<FoodModel> foodModelsveg, ArrayList<FoodModel> foodModelsegnon, ArrayList<FoodModel> foodlist_northindian) {
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        //For Tab view this View pager can be used
                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                        viewPagerAdapter.addfragment(new Tab1(foodModelsAll, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                                add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                                bottom_sheet_view_cart_btn), "All");
                        viewPagerAdapter.addfragment(new Tab2(foodModelsveg, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                                add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                                bottom_sheet_view_cart_btn), "Biryani");
                        viewPagerAdapter.addfragment(new BlankFragment(foodModelsegnon, cart_amount, items_total, bottomsheet,
                                add_new_serving_layout, add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                                bottom_sheet_view_cart_btn), "Italian");

                        viewPagerAdapter.addfragment(new Italian_fragment(foodlist_northindian, cart_amount, items_total, bottomsheet, add_new_serving_layout,
                                add_new_serv_btn, repeat_last_serv_btn, close_new_serv_layout_btn, add_new_serv_name, add_new_serving_size,
                                bottom_sheet_view_cart_btn), "North indian");
                        viewPager.setAdapter(viewPagerAdapter);
                        tabbar.setupWithViewPager(viewPager);

                        Toast.makeText(MainActivity.this, "Data Refreshed", Toast.LENGTH_SHORT).show();

                        pulltorefresh.setRefreshing(false);
                    }
                });
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmerAnimation();
    }

    public void requestPerm() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(this, "permission nai hai be", Toast.LENGTH_SHORT).show();
        } else {
            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    loc = location;
                    if (location != null) {
                        System.out.println("location mai ye aa raha hai" + location.toString());
                        //Toast.makeText(MainActivity.this, loc.toString(), Toast.LENGTH_SHORT).show();
                        getaddress();
                    } else {
                        System.out.println("mar ja kutte");
                    }
                }
            });
        }

    }

    public void getaddress() {

        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            System.out.println("ye address mai hai" + addresses);
        } catch (IOException e) {
            System.out.println("han service ki exception me ye hai" + addresses);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("bahi illegal argument fek raha hai");
        }


        if (addresses == null || addresses.size() == 0) {
            System.out.println("address kahli hia bc");
        } else {
            Address address = addresses.get(0);

            ArrayList<String> addressFragment = new ArrayList<>();

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragment.add(address.getAddressLine(i));
            }
            System.out.println("ye address aa rahae hai" + addressFragment);
            locText.setText(addressFragment.get(0));
            // Toast.makeText(this, addressFragment.get(0), Toast.LENGTH_LONG).show();
        }
    }


    public void setupToolbar() {
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    // this is for openning the new activity from drawer menu
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                Intent i = new Intent(this, AboutUs.class);
                onBackPressed();
                startActivity(i);

                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                Intent intent = new Intent(this, Contactus.class);
                onBackPressed();
                startActivity(intent);

                Toast.makeText(this, "Contact Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Intent intent1 = new Intent(this, Customer_Profile.class);
                onBackPressed();
                startActivity(intent1);
                break;
            case R.id.address_book:
                Intent i3 = new Intent(this, ManageAddresses.class);
                onBackPressed();
                startActivity(i3);
                break;
            case R.id.loginbtn:
                Intent i4 = new Intent(this, Login.class);
                onBackPressed();
                startActivity(i4);
                break;
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
}

