package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;

public class AddActivity extends AppCompatActivity {

    Button btnSave;
    EditText txtName, txtPhone, txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        this.getSupportActionBar().hide();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
    }

    public void addContact(){
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtMail = findViewById(R.id.txtMail);

        Contact contact = new Contact();
        contact.setEmail(txtMail.getText().toString());
        contact.setName(txtName.getText().toString());
        contact.setPhone(txtPhone.getText().toString());

        ContactDAO dao = new ContactDAO(this);
        dao.addNewContact(contact);

        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}