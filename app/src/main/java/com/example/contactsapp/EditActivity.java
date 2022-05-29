package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;

import java.util.ArrayList;


public class EditActivity extends AppCompatActivity {

    EditText txteName, txtePhone, txteMail;
    Button btnDelete, btnSave, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        this.getSupportActionBar().hide();


        txteName = findViewById(R.id.txteName);
        txtePhone = findViewById(R.id.txtePhone);
        txteMail = findViewById(R.id.txteMail);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton();
            }
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton();
            }
        });

        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone();
            }
        });


        showInfo();
    }

    public void showInfo() {
        Contact contact = new Contact();
        contact = (Contact) getIntent().getSerializableExtra("contact");

        txteName.setText(contact.getName());
        txteMail.setText(contact.getEmail());
        txtePhone.setText(contact.getPhone());
    }

    public void deleteButton(){

        Contact contact = new Contact();
        contact = (Contact) getIntent().getSerializableExtra("contact");

        ContactDAO dao = new ContactDAO(this);
        dao.removeContact(contact.getId());

        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void saveButton(){
        ContactDAO dao = new ContactDAO(this);

        Contact contact = new Contact();
        contact = (Contact) getIntent().getSerializableExtra("contact");

        contact.setName(txteName.getText().toString());
        contact.setPhone(txtePhone.getText().toString());
        contact.setEmail(txteMail.getText().toString());

        dao.editContact(contact);
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void dialContactPhone(){
        final String phoneNumber = txtePhone.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}