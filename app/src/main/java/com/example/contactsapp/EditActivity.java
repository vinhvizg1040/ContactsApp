package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.contactsapp.entities.Contact;


public class EditActivity extends AppCompatActivity {

    EditText txteName, txtePhone, txteMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        this.getSupportActionBar().hide();

        showInfo();
    }

    public void showInfo() {
        Contact contact = new Contact();
        contact = (Contact) getIntent().getSerializableExtra("contact");

        txteName = findViewById(R.id.txteName);
        txtePhone = findViewById(R.id.txtePhone);
        txteMail = findViewById(R.id.txteMail);

        txteName.setText(contact.getName());
        txteMail.setText(contact.getEmail());
        txtePhone.setText(contact.getPhone());

    }
}