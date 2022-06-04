package com.example.contactsapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;
import com.example.contactsapp.services.ImageServices;
import com.example.contactsapp.services.ValidateServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    Button btnSave;
    EditText txtName, txtPhone, txtMail;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Objects.requireNonNull(this.getSupportActionBar()).hide();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtMail = findViewById(R.id.txtMail);

        imgAvatar = findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(this::openFileChooser);
        //add new Contact when buttonSave is clicked
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> addContact());
    }

    //add new Contact
    public void addContact() {
        ValidateServices validateServices = new ValidateServices();
        if (validateServices.validateInfo(txtName, txtPhone, txtMail)) {
            Contact contact = new Contact();
            contact.setEmail(txtMail.getText().toString());
            contact.setName(txtName.getText().toString());
            contact.setPhone(txtPhone.getText().toString());

            ImageServices imageServices = new ImageServices();
            contact.setImgPath(imageServices.saveImage(imgAvatar, getApplicationContext()));


            ContactDAO dao = new ContactDAO(this);
            dao.addNewContact(contact);

            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    int requestcode = 1;

    public void openFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestcode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestcode && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                imgAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}