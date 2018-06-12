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
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // list view && adapter
        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.emptyList);

        adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.star_wars));
        listView.setAdapter(adapter); // show list
        listView.setEmptyView(emptyView); // show no results found

        // list item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                // I used this initially to ensure the click listener works
                // Toast.makeText(SearchActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

                /*
                Wanna start another activity instead? Go for it.
                This is a boilerplate if you want to use the text string in the item as data to be passed to another Activity of your choice.
                In my example I open another Activity that says "Hi" + string value of the list item.

                The method underneath is a wrapper for my own Intent + startActivity combo taking a string as a parameter.
                */
                launchNewActivity(adapterView.getItemAtPosition(i).toString());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Grab our menu.xml and inflate it into our window
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        /*
        Remember we edited our menu.xml to include an search menu item that had:
            - an action view as SearchView
            - and set to "ifRoom|collapseActionView"

            This is where the functionality of SearchView comes to play.
            We set the widget to collapse when not in use and if there is room in the Toolbar. However once the user clicks on our Search icon,
            the method below hooks up our Search menu item to the appropriate ActionView that will handle our Search action.
         */
        SearchView searchView = (SearchView) search.getActionView();

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

    public void launchNewActivity(String query) {
        Intent intent = new Intent(SearchActivity.this, OtherActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
