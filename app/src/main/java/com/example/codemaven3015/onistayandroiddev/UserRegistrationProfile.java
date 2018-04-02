package com.example.codemaven3015.onistayandroiddev;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Created by CodeMaven3015 on 3/8/2018.
 */

public class UserRegistrationProfile extends android.support.v4.app.Fragment {
    Button submitButton;
    EditText editTextFirstName,editTextLastName,editTextEmail,editTextMobile,editTextDoB;
    RadioGroup radioGroupGender;
    ImageView upload_photo;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.user_registration_profile,container,false);
        setWidgets(v);
        setOnClickButton();
        selectImageProfile();
        return v;
    }
    public void setWidgets(View v){
        submitButton = v.findViewById(R.id.submitButton);
        editTextFirstName = v.findViewById(R.id.editTextFirstName);
        editTextLastName = v.findViewById(R.id.editTextLastName);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        editTextMobile = v.findViewById(R.id.editTextMobile);
        editTextDoB = v.findViewById(R.id.editTextDoB);
        radioGroupGender = v.findViewById(R.id.radioGroupGender);
        upload_photo = v.findViewById(R.id.upload_photo);
        editTextDoB.setFocusable(false);
        MyEditTextDatePicker myEditTextDatePicker = new MyEditTextDatePicker(getContext(),editTextDoB);
    }
    public void selectImageProfile(){
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();//(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.putExtra("crop", "true");
                i.putExtra("outputX", 100);
                i.putExtra("outputY", 100);
                i.putExtra("scale", true);
                i.putExtra("return-data", true);
                // i.p
                startActivityForResult(Intent.createChooser(i,"Select Profile Picture"),10);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();//data.getStringExtra()
            Bitmap bitmap = null;
            bitmap = getScaledBitmap(uri);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            roundedBitmapDrawable.setAntiAlias(true);
            upload_photo.setImageDrawable(roundedBitmapDrawable);
        }
    }
    private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getActivity().getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=4;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
        }
        return thumb ;
    }
    public void setEditTextFocusable(Boolean flag){
        editTextFirstName.setFocusable(flag);
        editTextLastName.setFocusable(flag);
        editTextEmail.setFocusable(flag);
        editTextMobile.setFocusable(flag);
        editTextDoB.setFocusable(false);
        editTextFirstName.setFocusableInTouchMode(flag);
        editTextLastName.setFocusableInTouchMode(flag);
        editTextEmail.setFocusableInTouchMode(flag);
        editTextMobile.setFocusableInTouchMode(flag);
        editTextDoB.setFocusableInTouchMode(flag);


    }
    public void setOnClickButton(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitButton.getText().toString().toLowerCase().equals("edit")){
                    if(checkForValidation()) {
                        setEditTextFocusable(true);
                        submitButton.setText("SUMMIT");
                    }
                }else{
                    sendDataToServer();
                    setEditTextFocusable(false);
                    submitButton.setText("EDIT");

                }
            }
        });
    }
    public void sendDataToServer(){
        String firstName, lastName, dob, email, phone,gender;
        firstName = editTextFirstName.getText().toString().trim();
        lastName = editTextLastName.getText().toString().trim();
        dob = editTextDoB.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        phone = editTextMobile.getText().toString().trim();
        RadioButton radioButton = v.findViewById(radioGroupGender.getCheckedRadioButtonId());
        gender = radioButton.getText().toString();
    }
    public boolean getSelectedIndex(){
        int selectedIndex = radioGroupGender.getCheckedRadioButtonId();
        if(selectedIndex<0){
            showAlertMessage showAlertMessage = new showAlertMessage(getContext(),"Please select gender","Info");
            showAlertMessage.showMessage();
            return false;
        }else {
            return true;
        }
    }
    public boolean checkForValidation(){
        if(emptyFieldValidation() || emailValidation() || specialCharValidation() || getSelectedIndex()){
           return true;
        }
        return false;
    }
    public boolean emailValidation(){
        String email = editTextEmail.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            return false;
        }
    }
    public boolean emptyFieldValidation(){
        if(editTextFirstName.getText().toString().isEmpty()){
            editTextFirstName.setError("Please enter First Name");
            return false;
        }
        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("Please enter valid Email");
            return false;
        }
        if(editTextMobile.getText().toString().isEmpty()){
            editTextMobile.setError("Please enter valid Phone number ");
            return false;
        }
        if(editTextMobile.getText().toString().isEmpty()){
            editTextMobile.setError("Please select DoB ");
            return false;
        }
        return true;
    }
    public boolean specialCharValidation(){
        String s = editTextFirstName.toString();
        Pattern regex = Pattern.compile("[$&+;=\\\\?@#|/<>^*()%!]");

        if (regex.matcher(s).find()) {
            editTextFirstName.setError("Enter valid Name");
            return false;
        }
        if(regex.matcher(editTextLastName.getText()).find()){
            editTextLastName.setError("Enter valid Name");
            return false;
        }
        return true;
    }

}