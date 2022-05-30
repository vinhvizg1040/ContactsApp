package com.example.contactsapp.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contactsapp.R;
import com.example.contactsapp.entities.Contact;

import java.util.ArrayList;

public class ContactBaseAdapter extends BaseAdapter {

    ArrayList<Contact> contacts;
    Activity context;
    ContactDAO dao;

    public ContactBaseAdapter(ArrayList<Contact> contacts, Activity context, ContactDAO dao) {
        this.contacts = contacts;
        this.context = context;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contacts.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewContact;
        if (view==null){
            viewContact = View.inflate(viewGroup.getContext(), R.layout.contact, null);
        }else {
            viewContact = view;
        }
        Contact employee = (Contact) getItem(i);
        ((TextView)viewContact.findViewById(R.id.tvName)).setText(employee.getName());
        ((TextView)viewContact.findViewById(R.id.tvEmail)).setText(employee.getEmail());
        ((TextView)viewContact.findViewById(R.id.tvPhone)).setText(employee.getPhone());
        return viewContact;
    }
}
