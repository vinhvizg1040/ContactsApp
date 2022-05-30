package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;


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

        //Delete
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton();
            }
        });

        //Save
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

    public void deleteButton() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure Delete " + txteName.getText().toString() + " ?");

        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Contact contact = new Contact();
                contact = (Contact) getIntent().getSerializableExtra("contact");

                ContactDAO dao = new ContactDAO(getApplicationContext());
                dao.removeContact(contact.getId());

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void saveButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactDAO dao = new ContactDAO(getApplicationContext());

                Contact contact = new Contact();
                contact = (Contact) getIntent().getSerializableExtra("contact");

                contact.setName(txteName.getText().toString());
                contact.setPhone(txtePhone.getText().toString());
                contact.setEmail(txteMail.getText().toString());

                dao.editContact(contact);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void dialContactPhone() {
        final String phoneNumber = txtePhone.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


}