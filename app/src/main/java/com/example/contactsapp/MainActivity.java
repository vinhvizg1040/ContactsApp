package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contactsapp.controller.ContactBaseAdapter;
import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ArrayList<Contact> contacts;

    ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.getSupportActionBar().hide();

        //if click btnAdd -> go to AddActivity
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        //get All Contact when start
        lvContacts = findViewById(R.id.lvContacts);
        GetContacts();
        getContact();
    }

    private void GetContacts() {
        ContactDAO dao = new ContactDAO(this);
        try {
            contacts = (ArrayList<Contact>) dao.findAll();
            //set baseAdapter to listview
            ContactBaseAdapter baseAdapter = new ContactBaseAdapter(contacts, this, dao);
            lvContacts.setAdapter(baseAdapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getContact() {
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ContactDAO dao = new ContactDAO(getApplicationContext());
                    contacts = (ArrayList<Contact>) dao.findAll();
                    ContactBaseAdapter baseAdapter = new ContactBaseAdapter(contacts, MainActivity.this, dao);
                    Contact contact = new Contact();
                    contact = (Contact) baseAdapter.getItem(i);

                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("contact", contact);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}