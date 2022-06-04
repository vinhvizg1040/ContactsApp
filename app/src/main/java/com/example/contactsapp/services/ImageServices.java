package com.example.contactsapp.services;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class ImageServices {
    public String saveImage(ImageView imgAvatar, Context context){
        //save image
        String path = null;

        imgAvatar.buildDrawingCache();
        Bitmap selectedImage = imgAvatar.getDrawingCache();

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, selectedImage.toString() + ".jpg");

        if (!file.exists()) {
            Log.d("path", file.toString());

            //save this to storge
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            //save this path -> database
            path = file.getPath();
        }
        return path;
    }

    public boolean deleteImage(String imagepath){
        return new File(imagepath).delete();
    }


}
