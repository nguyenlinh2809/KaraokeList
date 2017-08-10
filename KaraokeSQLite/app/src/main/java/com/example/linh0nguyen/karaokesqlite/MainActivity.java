package com.example.linh0nguyen.karaokesqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import dev.com.vn.adapter.SongAdapter;
import dev.com.vn.database.DBHelper;
import dev.com.vn.model.Song;

public class MainActivity extends AppCompatActivity {
    public static String CODE = "code";
    public static String TITLE = "title";
    public static String DETAIL = "detail";
    public static String IS_FAVORITE = "is_favorite";
    public static final String database_name = "karaoke_db.sqlite";
    public static final String TABLE_NAME = "songsVN";
    ListView lvSong;
    ListView lvSongFavorite;
    ArrayList<Song> listSongFavorite;
    ArrayList<Song> listSong;
    SongAdapter adapterSong;
    TabHost host;
    SQLiteDatabase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        loadData();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listSong = searchBaiHat(newText);
                adapterSong = new SongAdapter(MainActivity.this, R.layout.song_row, listSong);
                lvSong.setAdapter(adapterSong);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private ArrayList<Song> searchBaiHat(String tuKhoa){
        data = DBHelper.copyDataFileToApp(MainActivity.this, database_name);
        ArrayList<Song> listSearch = new ArrayList<Song>();
        String query = "select "+CODE+", "+TITLE+", "+DETAIL+", "+IS_FAVORITE+" From "+TABLE_NAME+" Where "+TITLE+" LIKE '%"+tuKhoa+"%'";
        Cursor cursor = data.rawQuery(query, null);
        for(int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String code = cursor.getString(0);
            String title = cursor.getString(1);
            String detail = cursor.getString(2);
            String isFavorite = cursor.getString(3);
            Song song = new Song(code, title, detail, isFavorite);
            listSearch.add(song);
        }
        return listSearch;
    }

    private void loadData() {
        data = DBHelper.copyDataFileToApp(MainActivity.this, database_name);
        Cursor cursor = data.rawQuery("select * from songsVN", null);
        for(int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String code = cursor.getString(1);
            String title = cursor.getString(2);
            String detail = cursor.getString(3);
            String title_raw = cursor.getString(4);
            String detail_raw = cursor.getString(5);
            String isFavorite = cursor.getString(6);
            Song song = new Song(code, title, detail, title_raw, detail_raw, isFavorite);
            listSong.add(song);
        }
        adapterSong = new SongAdapter(MainActivity.this, R.layout.song_row, listSong);
        lvSong.setAdapter(adapterSong);
        data.close();
    }

    private void addEvents() {
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "t1":
                        doTab1();
                        break;
                    case "t2":
                        doTab2();
                        break;
                }
            }
        });
    }

    private void doTab2() {
        listSongFavorite.clear();
        for(Song song:listSong){
            if(song.getIsFavorite().equalsIgnoreCase("Y")){
                listSongFavorite.add(song);
            }
        }
        Log.d("favorite", listSongFavorite.toString());
        adapterSong = new SongAdapter(MainActivity.this, R.layout.song_row, listSongFavorite);
        lvSongFavorite.setAdapter(adapterSong);

    }

    private void doTab1() {
        listSong.clear();
        loadData();
    }

    private void addControls() {
        host = (TabHost) findViewById(android.R.id.tabhost);
        host.setup();

        TabHost.TabSpec tab1 = host.newTabSpec("t1");
        tab1.setIndicator("Danh sách bài hát");
        tab1.setContent(R.id.tab1);
        host.addTab(tab1);

        TabHost.TabSpec tab2 = host.newTabSpec("t2");
        tab2.setIndicator("Bài hát yêu thích");
        tab2.setContent(R.id.tab2);
        host.addTab(tab2);

        host.setCurrentTab(0);

        lvSong = (ListView) findViewById(R.id.lvSong);
        listSong = new ArrayList<Song>();

        lvSongFavorite = (ListView) findViewById(R.id.lvSongFavorite);
        listSongFavorite = new ArrayList<Song>();

    }
}
