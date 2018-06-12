# SearchView Widget Android Tutorial
This tutorial will cover how to create a custom toolbar for your Android app with search functionality. This replaces your normal ActionToolbar with your own custom toolbar. Traditionally SearchView Widget is used when there is a ListView element on the page where the search will filter through. 



# SearchView Widget - Custom Toolbar with Search Functionality
## Start from scratch
1. Create a new project

2. Choose empty activity (I named mine SearchActivity)

3. Change ```style.xml```
    
    - change the parent component to ```parent="Theme.AppCompat.Light.NoActionBar"```
    - this will remove the default ActionToolbar that comes packaged when you first create your project + empty activity

4. Create Toolbar and ListView 
    
    - Add a Toolbar: this is the base toolbar
        - id:toolbar
        - set width: match_parent
        - set height: wrap_content

    - Add a Textview: center horizontally and leave a small gap under the toolbar, this will become our "Sorry, no results found" message if our search comes up empty
        - id: emptyList
        - include in TextView ```android:visibility="gone"``` and ```android:text="Sorry, no results found"``` or something similar. 

    - Add ListView: display our data in a list format. This will cover our TextView if the data is displayed or if search results are found

        - id: listView
        - set width: match_parent
        - set height: wrap_content

5. Create your strings-array in ```strings.xml```. This will populate the ListView and these strings are what our SearchView will filter through. You can insert the example below and manipulate it to suit your needs.

    ```xml
    <string-array name="star_wars">
        <item>Leia Organa</item>
        <item>Han Solo</item>
        <item>Luke Skywalker</item>
        <item>Obi-Wan Kenobi</item>
        <item>R2-D2</item>
        <item>Anakin Skywalker</item>
        <item>Kylo Ren</item>
    </string-array>
    ```

6. Create a new Android Resource Directory
    - right click on ```res``` > New / Android Resource Directory
    - Directory name: ```menu```
    - Resource type: ```menu```
    - Source set: ```main``` (default)

7. Within your new ```menu``` directory, create a new Android Resource File
    - right click on ```menu``` > New / Menu Resource File
    - Filename: ```menu```
    - Source set: ```main``` (default)
    - Directory: ```menu``` (default)

8. Working in ```menu.xml```
    - add Menu Item and edit the xml to include:
        - ```id:action_search```
        - ```icon:"@android:drawable/ic_menu_search"```
        - ```app:actionViewClass="android.support.v7.widget.SearchView"``` - when widget in use, replace Toolbar UI with SearchView functionality
        - ```app:showAsAction="ifRoom|collapseActionView"``` - display widget icon when not in use


    It should look something like this when you're done:

    ```xml
    <item
        android:id="@+id/action_search"
        android:icon="@android:drawable/ic_menu_search"
        android:title="@string/search"
        app:actionViewClass="android.support.v7.widget.SearchView"
        app:showAsAction="ifRoom|collapseActionView"
        >
    </item>
    ```

9. Finally! Go to your SearchActivity. Let's get this going. We're going to start with our 4 private fields at class level

    - Toolbar (make sure you import the correct version: ```android.support.v7.widget.Toolbar```)
    - ListView
    - TextView
    - ArrayAdapter```<String>```

10. Inside ``onCreate()`` method we're going to set up our toolbar, our list view, and the event listener for a list view (this will listen for an list item selection from the user)

```java
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
```

11. Create a new method: ```public boolean onCreateOptionsMenu(Menu menu)```

    - this method will instantiate our menu.xml into a Menu object since we turned off the default ActionBar 

```java
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
```

12. Lastly, for my example when a list item is clicked a new activity is launched. You're welcome to replace this functionality with something else you need. I created a ```public void launchNewActivity(string query)``` which acts as a wrapper for Intent + startActivity combo. This also allows for reuse if you wanted to include a new launch combo for ```onQueryTextSubmit()``` in the previous step.

```java

public void launchNewActivity(String query) {
        Intent intent = new Intent(SearchActivity.this, OtherActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
```

13. Finishing touches:
    - created OtherActivity class to show taking a string from our list view and use in another Activity


To see the source code download or clone this repo!

For more information, I used these documents as reference.
- Full SearchView documentation: https://developer.android.com/reference/android/widget/SearchView 
- Using Action Views: https://developer.android.com/training/appbar/action-views 