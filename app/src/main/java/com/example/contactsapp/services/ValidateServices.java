package com.example.contactsapp.services;

import android.widget.EditText;

public class ValidateServices {
    //Regex
    public Boolean validateInfo(EditText name, EditText phone, EditText mail){
        final String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        final String mailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

        if (name.getText().toString().trim().length() == 0){
            name.requestFocus();
            name.setError("Field cannot empty");
            return false;
        }else if (!phone.getText().toString().matches(phoneRegex)){
            phone.requestFocus();
            phone.setError("wrong format");
            return false;
        }
//        else if (!mail.getText().toString().trim().matches(mailRegex)){
//            mail.requestFocus();
//            mail.setError("email is not valid");
//            return false;
//        }
        return true;
    }
}
