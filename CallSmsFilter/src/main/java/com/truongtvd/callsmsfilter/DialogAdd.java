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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.truongtvd.callsmsfilter.view.NotificationHelper;

/**
 * Created by truongtvd on 6/10/14.
 */
public class DialogAdd extends Activity {

    private Context context;
    private String[] choose_spin = null;
    private Spinner spinner;
    private EditText edTitle, edContent, edPhone, edName;
    private Button btnTest, btnContact, btnCall, btnCancel, btnFilter;
    private final static int PICK_CONTACT_REQUEST = 1;
    private final static int PICK_CALLLOG_REQUEST = 2;
    private final static int REQUEST_CODE_FILTER = 5;
    private int TYPE = 0;
    private int show = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        setTitle(R.string.dialog_title);
        choose_spin = new String[]{getString(R.string.do_not_show), getString(R.string.show)};
        spinner = (Spinner) findViewById(R.id.spinner);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edContent = (EditText) findViewById(R.id.ed_content);
        edPhone = (EditText) findViewById(R.id.ed_phone);
        edName = (EditText) findViewById(R.id.ed_name);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnContact = (Button) findViewById(R.id.btn_import_contact);
        btnCall = (Button) findViewById(R.id.btn_import_call);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnFilter = (Button) findViewById(R.id.btn_filter);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(DialogAdd.this, android.R.layout.simple_spinner_item, choose_spin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        show = 1;
                        edTitle.setEnabled(false);
                        edContent.setEnabled(false);
                        btnTest.setEnabled(false);
                        break;
                    case 1:
                        show = 2;
                        edTitle.setEnabled(true);
                        edContent.setEnabled(true);
                        btnTest.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initClickContact();
        initClickCallLog();
        initClickCancel();
        initClickFilter();
        initClickTest();


    }


    private void initClickTest(){
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationHelper notificationHelper=new NotificationHelper(DialogAdd.this);
                notificationHelper.createNotificaion(edTitle.getText()+"",edContent.getText()+"");
            }
        });
    }

    private void initClickFilter() {
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edPhone.getText() + "")) {
                    Toast.makeText(DialogAdd.this, getString(R.string.input_phone), Toast.LENGTH_SHORT).show();

                } else {
                    if (show == 2) {
                        if (TextUtils.isEmpty(edTitle.getText() + "")) {
                            Toast.makeText(DialogAdd.this, getString(R.string.input_title), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(edContent.getText() + "")) {
                            Toast.makeText(DialogAdd.this, getString(R.string.input_content), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("PHONE", edPhone.getText() + "");
                            intent.putExtra("NAME", edName.getText() + "");
                            intent.putExtra("TITLE", edTitle.getText() + "");
                            intent.putExtra("CONTENT", edContent.getText() + "");
                            intent.putExtra("SHOW", show);
                            intent.putExtra("TYPE", 1);
                            setResult(REQUEST_CODE_FILTER, intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("PHONE", edPhone.getText() + "");
                        intent.putExtra("NAME", edName.getText() + "");
                        intent.putExtra("TITLE", edTitle.getText() + "");
                        intent.putExtra("CONTENT", edContent.getText() + "");
                        intent.putExtra("SHOW", show);
                        intent.putExtra("TYPE", 1);
                        setResult(REQUEST_CODE_FILTER, intent);
                        finish();
                    }
                }

            }
        });

    }

    private void initClickCancel() {
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

        String[] callLogFields = {android.provider.CallLog.Calls._ID,
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
                String number = callLog_cursor.getString(callLog_cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
                edPhone.setText(number + "");


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
