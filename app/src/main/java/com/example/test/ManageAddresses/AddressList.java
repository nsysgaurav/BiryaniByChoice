package com.example.test.ManageAddresses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.test.R;

public class AddressList extends AppCompatActivity
{
    Toolbar toolbar;
    Button add_new_address;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list);

        add_new_address=findViewById(R.id.add_new_address_btn);
        toolbar=findViewById(R.id.add_book_toolbar_id);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(AddressList.this, ManageAddresses.class));
            }
        });

    }
}
