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

        // list view && adapter
        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.emptyList);

        // get the string array from your strings.xml file
        adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.star_wars));

        listView.setAdapter(adapter); // show list
        listView.setEmptyView(emptyView); // show no results found

        // list item click --> start new activity with string
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                //The method underneath is a wrapper for my own Intent + startActivity combo taking a string as a parameter.
                String query = adapterView.getItemAtPosition(i).toString();
                launchNewActivity(query);
            }

        });
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        // Grab our menu.xml and inflate it into our window
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        /*
        Remember we edited our menu.xml to include an search menu item that had:
            - an action view as SearchView
            - and set to "ifRoom|collapseActionView"

            This is where the functionality of SearchView comes to play.
            We set the widget to collapse when not in use and if there is room in the Toolbar.
            Once the user clicks on our Search icon, the method below hooks up to our Search
            menu item SearchView.
         */
        SearchView searchView = (SearchView) search.getActionView();

        // Using SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // You can fire off an intent with the search query in here if that's what you need for your app
                Log.wtf("yo", "query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Listen to the query the user is typing and filter through the data our adapter is holding
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void launchNewActivity(final String query) {
        Intent intent = new Intent(SearchActivity.this, OtherActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
