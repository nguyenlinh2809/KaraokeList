package dev.com.vn.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.linh0nguyen.karaokesqlite.MainActivity;
import com.example.linh0nguyen.karaokesqlite.R;

import java.util.ArrayList;
import java.util.List;

import dev.com.vn.database.DBHelper;
import dev.com.vn.model.Song;

/**
 * Created by Linh(^0^)Nguyen on 4/18/2017.
 */

public class SongAdapter extends ArrayAdapter<Song> {
    Context context;
    int myLayout;
    ArrayList<Song> listSong;

    public SongAdapter(Context context, int myLayout, ArrayList<Song> listSong) {
        super(context, myLayout, listSong);
        this.context = context;
        this.myLayout = myLayout;
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout, null);
            holder = new ViewHolder();
            holder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
            holder.imLike = (ImageButton) convertView.findViewById(R.id.imLike);
            holder.imDisLike = (ImageButton) convertView.findViewById(R.id.imDisLike);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Song song = listSong.get(position);
        holder.tvCode.setText(song.getCode());
        holder.tvDetail.setText(song.getDetail());
        holder.tvTitle.setText(song.getTitle());
        if(song.getIsFavorite().equalsIgnoreCase("N")){
            holder.imLike.setVisibility(View.VISIBLE);
            holder.imDisLike.setVisibility(View.INVISIBLE);
        }else if(song.getIsFavorite().equalsIgnoreCase("Y")){
            holder.imLike.setVisibility(View.INVISIBLE);
            holder.imDisLike.setVisibility(View.VISIBLE);
        }
        holder.imLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLike(position);
            }
        });
        holder.imDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDisLike(position);
            }
        });
        return convertView;
    }

    private void doDisLike(int position) {
        Song song = listSong.get(position);
        String update = "UPDATE "+ MainActivity.TABLE_NAME+" SET is_favorite = "+"'N'"+" WHERE code = "+song.getCode();
        SQLiteDatabase database = DBHelper.copyDataFileToApp(context, MainActivity.database_name);
        database.execSQL(update);
        database.close();
    }

    private void doLike(int position) {
        Song song = listSong.get(position);
        String update = "UPDATE "+ MainActivity.TABLE_NAME+" SET is_favorite = "+"'Y'"+" WHERE code = "+song.getCode();
        SQLiteDatabase database = DBHelper.copyDataFileToApp(context, MainActivity.database_name);
        database.execSQL(update);
        database.close();
    }

    private class ViewHolder{
        TextView tvCode, tvTitle, tvDetail;
        ImageButton imLike, imDisLike;
    }
}
