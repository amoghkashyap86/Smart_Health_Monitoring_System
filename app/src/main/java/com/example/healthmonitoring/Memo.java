package com.example.healthmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Memo extends AppCompatActivity {
    static ArrayList<String> notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.newNotes){
            Intent intent=new Intent(getApplicationContext(),Notes.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ListView listView=(ListView) findViewById(R.id.list);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(" com.example.healthmonitoring", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null) {
            notes.add("1st note");
        }else {
            notes=new ArrayList(set);
        }
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Notes.class);
                intent.putExtra("notesId",i);
                startActivity(intent);
                return true;

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete=i;
                new AlertDialog.Builder(Memo.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("are u sure")
                        .setMessage("do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete );
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(" com.example.memo", Context.MODE_PRIVATE);
                                HashSet<String> set =new HashSet<>(Memo.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("no!",null)
                        .show();

                return true;
            }
        });

    }
}
