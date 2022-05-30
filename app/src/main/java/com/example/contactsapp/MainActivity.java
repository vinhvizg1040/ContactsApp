package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsapp.controller.ContactBaseAdapter;
import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ArrayList<Contact> contacts;
    SearchView txtSearch;
    ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.getSupportActionBar().hide();



        //if click btnAdd -> go to AddActivity
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        //search
        txtSearch = findViewById(R.id.txtSearch);
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ContactDAO dao = new ContactDAO(getApplicationContext());
                ArrayList<Contact> contact = (ArrayList<Contact>) dao.findAll();
                ArrayList<Contact> co = new ArrayList<>();
                for (Contact cont : contact) {
                    if (cont.getName().toLowerCase().contains(s)) {
                        co.add(cont);
                    }
                }
                ContactBaseAdapter baseAdapter = new ContactBaseAdapter(co, MainActivity.this, dao);
                lvContacts.setAdapter(baseAdapter);
                return false;
            }
        });

        //get All Contact when start
        lvContacts = findViewById(R.id.lvContacts);
        GetContacts();
        getContact();
    }

    //getAll Contact
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

    //getOne Contact when item in listView clicked
    private void getContact() {
        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                ContactDAO dao = new ContactDAO(getApplicationContext());
                contacts = (ArrayList<Contact>) dao.findAll();
                ContactBaseAdapter baseAdapter = new ContactBaseAdapter(contacts, MainActivity.this, dao);
                Contact contact;
                contact = (Contact) baseAdapter.getItem(i);

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //send this data (contact)
                intent.putExtra("contact", contact);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}