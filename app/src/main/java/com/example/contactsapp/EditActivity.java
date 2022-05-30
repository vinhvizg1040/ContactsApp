package com.example.contactsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;


public class EditActivity extends AppCompatActivity {

    EditText txteName, txtePhone, txteMail;
    Button btnDelete, btnSave, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        this.getSupportActionBar().hide();


        txteName = findViewById(R.id.txteName);
        txtePhone = findViewById(R.id.txtePhone);
        txteMail = findViewById(R.id.txteMail);

        //Delete
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> deleteButton());

        //Save
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> saveButton());

        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(view -> dialContactPhone());


        showInfo();


    }

    public void showInfo() {
        Contact contact;
        contact = (Contact) getIntent().getSerializableExtra("contact");

        txteName.setText(contact.getName());
        txteMail.setText(contact.getEmail());
        txtePhone.setText(contact.getPhone());
    }

    public void deleteButton() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure Delete " + txteName.getText().toString() + " ?");

        builder.setPositiveButton("NO", (dialog, which) -> dialog.dismiss());

        builder.setNegativeButton("YES", (dialog, which) -> {
            Contact contact;
            contact = (Contact) getIntent().getSerializableExtra("contact");

            ContactDAO dao = new ContactDAO(getApplicationContext());
            dao.removeContact(contact.getId());

            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent);

            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void saveButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("NO", (dialog, which) -> dialog.dismiss());

        builder.setNegativeButton("YES", (dialog, which) -> {
            ContactDAO dao = new ContactDAO(getApplicationContext());

            Contact contact;
            contact = (Contact) getIntent().getSerializableExtra("contact");

            contact.setName(txteName.getText().toString());
            contact.setPhone(txtePhone.getText().toString());
            contact.setEmail(txteMail.getText().toString());

            dao.editContact(contact);
            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent);

            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void dialContactPhone() {
        final String phoneNumber = txtePhone.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


}