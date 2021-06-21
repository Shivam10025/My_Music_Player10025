package com.example.mymusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
// My name is sagar vvvvsgahhajajjssssssddddddd
    // i am your papa ji
    //this is sanyam
    // sanyam is my son
    ListView listView;
    String[] songs;
    ArrayAdapter<String> myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView  = (ListView)findViewById(R.id.listview);
        runtimepermission();
    }
    public void  runtimepermission(){
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    public ArrayList<File> findsong(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] file1 = file.listFiles();
        for(File singleFile : file1){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findsong(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }
    void display(){
        final ArrayList<File> mysong = findsong(Environment.getExternalStorageDirectory());
        if(mysong.size()==0){
            Toast.makeText(this, "Add New Song", Toast.LENGTH_LONG).show();
            return;
        }
        songs = new String[mysong.size()];
        songs[0] = "paani wala dance";
       for(int i=0;i<mysong.size();i++){
            songs[i] = mysong.get(i).getName().toString();
        }
        myadapter = new CustomAdapter(this , songs);
        listView.setAdapter(myadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songname = listView.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(),Player.class)
                    .putExtra("songs" , mysong).putExtra("songname" , songname)
                );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter song name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myadapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}