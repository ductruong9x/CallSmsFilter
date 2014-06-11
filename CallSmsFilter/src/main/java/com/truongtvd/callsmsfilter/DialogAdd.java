package com.truongtvd.callsmsfilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by truongtvd on 6/10/14.
 */
public class DialogAdd extends Activity {

    private Context context;
    private String[] choose_spin = null;
    private Spinner spinner;
    private EditText edTitle, edContent, edPhone;
    private Button btnTest, btnContact, btnCall, btnCancel, btnFilter;
    private final static int PICK_CONTACT_REQUEST = 1;
    private final static int PICK_CALLLOG_REQUEST = 2;
    private final static int REQUEST_CODE_FILTER=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        setTitle(R.string.dialog_title);
        choose_spin = new String[]{getString(R.string.all), getString(R.string.smschoose), getString(R.string.call)};
        spinner = (Spinner) findViewById(R.id.spinner);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edContent = (EditText) findViewById(R.id.ed_content);
        edPhone = (EditText) findViewById(R.id.ed_phone);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnContact = (Button) findViewById(R.id.btn_import_contact);
        btnCall = (Button) findViewById(R.id.btn_import_call);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnFilter = (Button) findViewById(R.id.btn_filter);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(DialogAdd.this, android.R.layout.simple_spinner_item, choose_spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        initClickContact();
        initClickCallLog();
        initClickCancel();
        initClickFilter();

    }

    private void initClickFilter(){
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent();
                intent.putExtra("TEST",1);
                setResult(REQUEST_CODE_FILTER,intent);
                finish();

            }
        });

    }

    private void initClickCancel(){
    btnCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });


    }

    private void initClickContact() {
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
    }

    private void initClickCallLog() {
       btnCall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getCallLog();
           }
       });
    }


    public void getCallLog() {

        String[] callLogFields = { android.provider.CallLog.Calls._ID,
                android.provider.CallLog.Calls.NUMBER,
                android.provider.CallLog.Calls.CACHED_NAME /* im not using the name but you can*/};
        String viaOrder = android.provider.CallLog.Calls.DATE + " DESC";
        String WHERE = android.provider.CallLog.Calls.NUMBER + " >0"; /*filter out private/unknown numbers */

        final Cursor callLog_cursor = getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI, callLogFields,
                WHERE, null, viaOrder);

        AlertDialog.Builder myversionOfCallLog = new AlertDialog.Builder(DialogAdd.this);

        android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                callLog_cursor.moveToPosition(item);
                String number=callLog_cursor.getString(callLog_cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
                edPhone.setText(number+"");


                callLog_cursor.close();

            }
        };
        myversionOfCallLog.setCursor(callLog_cursor, listener,
                android.provider.CallLog.Calls.NUMBER);
        myversionOfCallLog.setTitle(getString(R.string.choose_number));
        myversionOfCallLog.create().show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                edPhone.setText(number + "");
            }
        } else if (requestCode == PICK_CALLLOG_REQUEST) {
            if (requestCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] callLogFields = {CallLog.Calls.NUMBER};
                Cursor cursor = getContentResolver()
                        .query(contactUri, callLogFields, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                String number = cursor.getString(column);
                edPhone.setText(number + "");
            }
        }
    }
}
