package com.vjmarcus.simpletodo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView listView;
    Button mButton;
    EditText mEditText;
    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.editText_new_item);

        listView = (ListView) findViewById(R.id.listview);
        items = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.support_simple_spinner_dropdown_item, items);
        listView.setAdapter(itemAdapter);
        for (int i = 0; i < 40; i++) {
            items.add(i + " item");
        }
        // setupListViewListener();

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
                items.add(mEditText.getText().toString());
                mEditText.setText("");
                itemAdapter.notifyDataSetChanged();


            }
        });


    }

}
