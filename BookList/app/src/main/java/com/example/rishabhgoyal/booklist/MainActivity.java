package com.example.rishabhgoyal.booklist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search=(Button)findViewById(R.id.search);
        final int internet=1;
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.INTERNET},
                    internet);
        }else {

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText search1=(EditText)findViewById(R.id.search_text);
                    String searchString=search1.getText().toString();
                    FetchData fetch=new FetchData();
                    AsyncTask<String, Void, ArrayList<Result>> getData = fetch.execute(searchString);
                    try{
                        ListView lv = (ListView) findViewById(R.id.list);
                        lv.setEmptyView(findViewById(R.id.empty_text));
                        String[] authors = new String[getData.get().size()];
                        for (int i = 0; i < getData.get().size(); i++) {
                            authors[i] = "Name: " + String.valueOf(getData.get().get(i).getmTitle()) + "\n" +
                                    "Author: " + String.valueOf(getData.get().get(i).getmAuthour()) + "\n" +
                                    "PageCuont: "+ String.valueOf(getData.get().get(i).getPagecount())+"\n"+
                                    "Publisher: " + String.valueOf(getData.get().get(i).getmPublisher() + "\n" +
                                    "Rating: "+ String.valueOf(getData.get().get(i).getmRating()));
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                R.layout.list_item,
                                R.id.text_1,
                                authors);
                        lv.setAdapter(arrayAdapter);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

    }
}