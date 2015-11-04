package com.example.shubhambakshi.parseapp3;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shubhambakshi.parseapp3.Searchoperate.Task;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchResultsActivity extends ActionBarActivity {

    static String searchString;
    static String searchParam="FirstName";
    static String extrasearchParam="LastName";
    static SearchView searchView;
    static ArrayList<String> inputArray = new ArrayList<>();
    LinearLayout linlaHeaderProgress;
    ListView contactListView1;
    private TextView textView;
    private Button mapButton;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.contactlistview);
        handleIntent(getIntent());


    }
    private String validateSearchString(String searchString){
        searchString = searchString.trim();
        return searchString;
    }

    private void tokenizeSearchString(){
        StringTokenizer st = new StringTokenizer(searchString.toString());
        while(st.hasMoreTokens()){
            inputArray.add(st.nextToken());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_results, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchString = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            //Toast.makeText(getApplicationContext(),searchString,Toast.LENGTH_LONG).show();
            if(searchString.isEmpty()){
                Toast.makeText(getApplicationContext(),"Empty search field",Toast.LENGTH_LONG).show();
                return;
            }

            searchString = validateSearchString(searchString);
            tokenizeSearchString();

            Intent intent2 = new Intent(getApplicationContext(),Searchoperate.class);
            startActivity(intent2);



        }
    }

    @Override
    public void onBackPressed(){
        //Searchoperate.validList=new ArrayList<ParseObject>();
        //SearchResultsActivity.searchString=null;
        super.onBackPressed();
    }
}
