package com.example.spork.searchview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView emptyView;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // list
        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.emptyList);

        adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.star_wars));
        listView.setAdapter(adapter); // show list


        // list item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                //Toast.makeText(SearchActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

                // wanna start another activity? with this knowledge? go for it
                launchNewActivity(adapterView.getItemAtPosition(i).toString());
            }
        });

        listView.setEmptyView(emptyView); // show no results found
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.wtf("yo", "query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // listen to the query the user is typing and show the result from adapter that holds our array
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void launchNewActivity(String word) {
        Intent intent = new Intent(SearchActivity.this, OtherActivity.class);
        intent.putExtra("query", word);
        startActivity(intent);
    }
}
