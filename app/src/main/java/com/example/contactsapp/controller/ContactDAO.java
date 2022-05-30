package com.example.contactsapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contactsapp.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends SQLiteOpenHelper {

    public ContactDAO(Context context) {
        super(context, "Contact.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Contact (id integer primary key autoincrement, name varchar(40)" +
                ", email varchar(40), phone varchar(40))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists Contact";
        sqLiteDatabase.execSQL(sql);
    }

    public void addNewContact(Contact contact) {
       SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());

        db.insert("Contact", null, values);
        close();
    }

    public void removeContact(int id){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("Contact","id=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public List<Contact> findAll(){
        List<Contact> contacts = new ArrayList<>();
        String sql = "select * from Contact";
        SQLiteDatabase db = getWritableDatabase();
        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();

                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setEmail(cursor.getString(2));
                    contact.setPhone(cursor.getString(3));

                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        }
        return contacts;
    }

    public void editContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());
        db.update("Contact", values, "id=?", new String[]{String.valueOf(contact.getId())});
    }
}
