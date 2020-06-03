package com.example.vedioplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    public  static int REQUEST_PERMISSION=1;
    File directiry;
    boolean bolean_permission;

    public static ArrayList<File> file=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.listvi1);

        directiry=new File("/mnt/");
        GridLayoutManager manager=new GridLayoutManager(MainActivity.this,3);
        recyclerView.setLayoutManager(manager);

        permissionVideo();
    }

    private void permissionVideo() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }

        }
        else {
            bolean_permission=true;
            getfile(directiry);
            myAdapter=new MyAdapter(getApplicationContext(),file);
            recyclerView.setAdapter(myAdapter);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                bolean_permission = true;
                getfile(directiry);
                myAdapter = new MyAdapter(getApplicationContext(), file);
                recyclerView.setAdapter(myAdapter);
            } else
                Toast.makeText(this, "Please Allow Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<File>getfile(File directiry) {

        File listFile[] = directiry.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                } else {
                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".mp4")) {
                        for (int j = 0; j < file.size(); j++) {
                            if (file.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {
                            }
                        }
                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            file.add(listFile[i]);
                        }
                    }
                }
            }

        }
        return file;
    }
}
