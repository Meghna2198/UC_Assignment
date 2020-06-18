package com.mmit.uc_assignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    private ArrayList<String> personNames = new ArrayList<String>();
    TextView count;
    CustomAdapter customAdapter;
//    final String[] from = new String[] { DatabaseHelper._ID,
//            DatabaseHelper.SUBJECT};
//
//    final int[] to = new int[] { R.id.id, R.id.title};

    public ArrayList<String> getPersonNames() {
        return personNames;
    }

    public void setPersonNames(ArrayList<String> personNames) {
        this.personNames = personNames;
    }

    public void insertPersonName(String name) {
        this.personNames.add(name);
    }

    public void deletePersonName(String name) {
        this.personNames.remove(name);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();

        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customAdapter = new CustomAdapter(MainActivity.this, personNames);

        personNames = dbManager.listItems();
        if (personNames.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            customAdapter = new CustomAdapter(this, personNames);
            recyclerView.setAdapter(customAdapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no task in the database. Start adding now", Toast.LENGTH_LONG).show();
        }




     //  recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            createToDoDialog();
//            Intent i1 = new Intent(this, AddActivity.class);
//            startActivity(i1);
            break;

        case R.id.reset:

        case R.id.about:

        case R.id.exit:
            finish();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    private void createToDoDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_record, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add ToDo Task");
        final AlertDialog alertDialog = dialogBuilder.create();

        final EditText editText = (EditText) dialogView.findViewById(R.id.subject_edittext);
        Button addTodoBtn = dialogView.findViewById(R.id.add_record);
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editText.getText().toString();
                insertPersonName(name);
                dbManager.insert(name);
                customAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }

        });
        alertDialog.show();

    }

//    public void onStop(){
//        super.onStop();
//    }


}
