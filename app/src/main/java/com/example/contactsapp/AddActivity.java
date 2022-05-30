package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsapp.controller.ContactDAO;
import com.example.contactsapp.entities.Contact;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    Button btnSave;
    EditText txtName, txtPhone, txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Objects.requireNonNull(this.getSupportActionBar()).hide();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtMail = findViewById(R.id.txtMail);

        //add new Contact when buttonSave is clicked
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view ->

                addContact());
    }

    //add new Contact
    public void addContact(){
        if (validateInfo(txtName.getText().toString(), txtPhone.getText().toString(), txtMail.getText().toString())){
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

    //Regex
    public Boolean validateInfo(String name, String phone, String mail){
        final String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        final String mailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

        if (name.trim().length() == 0){
            txtName.requestFocus();
            txtName.setError("Field cannot empty");
            return false;
        }else if (!phone.matches(phoneRegex)){
            txtPhone.requestFocus();
            txtPhone.setError("wrong format");
            return false;
        }else if (!mail.trim().matches(mailRegex)){
            txtMail.requestFocus();
            txtMail.setError("email is not valid");
            return false;
        }
        return true;
    }
}