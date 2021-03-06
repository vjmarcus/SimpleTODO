package com.vjmarcus.simpletodo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView listView;
    Button mButton;
    EditText mEditText;
    private static final String TAG = "MyApp";
    String mUserText;
    FileOutputStream mFileOutputStream = null;
    FileInputStream mFileInputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText_new_item);

        listView = (ListView) findViewById(R.id.listview);
        items = new ArrayList<>();
        readItems();
        itemAdapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.support_simple_spinner_dropdown_item, items);
        listView.setAdapter(itemAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.dialog_message);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        items.remove(position);
                        itemAdapter.notifyDataSetChanged();
                        writeItems();
                        Toast.makeText(MainActivity.this, "Deleated", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        mButton = (Button) findViewById(R.id.button_add_item);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Button mButton PUSH");
                mUserText = mEditText.getText().toString();
                if (TextUtils.isEmpty(mUserText)) {
                    mEditText.setError("Поле не заполнено!");
                } else {
                    items.add(mEditText.getText().toString());
                    mEditText.setText("");
                    writeItems();
                    itemAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    void readItems() {

        File filesDir = getFilesDir();
        File file = new File(filesDir, "newToDo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
            Log.v(TAG, "readItems worked");
        } catch (IOException e) {
            items = new ArrayList<String>();
            Log.v(TAG, "readItems ERROR  - " + e.toString());
        }


    }


    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "newToDo.txt");
        try {
            FileUtils.writeLines(file, items);
            Log.v(TAG, "writeItems worked");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "writeItems ERROR");
        }

    }

}
