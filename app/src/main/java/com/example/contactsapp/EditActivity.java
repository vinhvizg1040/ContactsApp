package com.example.contactsapp;

import android.app.Activity;
import android.app.AlertDialog;
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
import java.io.InputStream;


public class EditActivity extends AppCompatActivity {

    EditText txteName, txtePhone, txteMail;
    Button btnDelete, btnSave, btnCall;
    ImageView imgEditAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        this.getSupportActionBar().hide();


        txteName = findViewById(R.id.txteName);
        txtePhone = findViewById(R.id.txtePhone);
        txteMail = findViewById(R.id.txteMail);

        imgEditAvatar = findViewById(R.id.imgEditAvatar);
        imgEditAvatar.setOnClickListener(this::openFileChooser);

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

        try {
            Uri imageUri = Uri.fromFile(new File(contact.getImgPath()));
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imgEditAvatar.setImageBitmap(bitmap);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("e",e.getMessage());
        }
    }

    public void deleteButton() {
        //show yes/no dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure Delete " + txteName.getText().toString() + " ?");

        builder.setPositiveButton("NO", (dialog, which) -> dialog.dismiss());

        builder.setNegativeButton("YES", (dialog, which) -> {
            Contact contact;
            contact = (Contact) getIntent().getSerializableExtra("contact");

            ContactDAO dao = new ContactDAO(getApplicationContext());
            dao.removeContact(contact.getId());

            //delete image
            ImageServices imageServices = new ImageServices();
            imageServices.deleteImage(contact.getImgPath());

            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent);

            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void saveButton() {
        ValidateServices validateServices = new ValidateServices();
        if (validateServices.validateInfo(txteName, txtePhone, txteMail)){
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

                //delete image
                ImageServices imageServices = new ImageServices();
                imageServices.deleteImage(contact.getImgPath());
                //save image
                contact.setImgPath(imageServices.saveImage(imgEditAvatar, getApplicationContext()));

                dao.editContact(contact);

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void dialContactPhone() {
        final String phoneNumber = txtePhone.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
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

                imgEditAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

}