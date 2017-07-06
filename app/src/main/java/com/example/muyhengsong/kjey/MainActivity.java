package com.example.muyhengsong.kjey;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txt_item_name, txt_category, txt_returndate, txt_note, txt_borrower;
    private ImageView img;
    private Button btn_save;
    static final int DIALOG_ID = 0;
    private int borrow_year, borrow_month, day;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_item_filled);

        borrow_year = calendar.get(Calendar.YEAR);
        borrow_month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);

        txt_item_name = (EditText) findViewById(R.id.txt_item_name);
        txt_borrower = (EditText) findViewById(R.id.txt_borrower);
        txt_category = (EditText) findViewById(R.id.txt_category);
        txt_returndate = (EditText) findViewById(R.id.txt_returndate);
        txt_note = (EditText) findViewById(R.id.txt_note);
        btn_save = (Button) findViewById(R.id.btn_save_item);
        img = (ImageView) findViewById(R.id.item_image);

        txt_returndate.setClickable(true);
        txt_borrower.setClickable(true);
        txt_category.setClickable(true);

        txt_returndate.setOnClickListener(this);
        txt_borrower.setOnClickListener(this);
        txt_category.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_returndate:
                showDialog(DIALOG_ID);
                break;

            case R.id.txt_borrower:
                Intent intent = new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
                break;

            case R.id.txt_category:
                intent = new Intent(MainActivity.this, CategoryItem.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.btn_save_item:
                Toast.makeText(getApplicationContext(),"Btn_save",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_image:
                selectImage();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                if(uri != null) {
                    Cursor c = null;
                    try {
                        c = getContentResolver().query(uri,
                                new String[] {
                                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}
                                , null, null, null);

                        if(c != null && c.moveToFirst()) {
                            String number = c.getString(0);
                            String displayName = c.getString(1);
                            txt_borrower.setText(displayName);
                        }
                    }
                    finally {
                        if(c != null && !c.isClosed())
                            c.close();
                    }
                }
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

        }

        if(requestCode == 3 && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }

        if(requestCode == 4 && resultCode == Activity.RESULT_OK){
            try {
                if(data == null) return;
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, datepickerListenner, borrow_year, borrow_month, day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerListenner = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            borrow_year = year;
            borrow_month = month;
            day = dayOfMonth;
            txt_returndate.setText(borrow_month+"/"+day+"/"+borrow_year);
        }
    };

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 3);
                }
                else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 4);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
