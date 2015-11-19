package practica1.com.peliculas;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import practica1.com.peliculas.provider.movie.MovieColumns;

/**
 * Created by 48089748z on 19/11/15.
 */
public class DBListViewAdapter extends SimpleCursorAdapter
{
    final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";
    Context context;
    DecimalFormat oneDecimal = new DecimalFormat("#.#");

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DBListViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
    {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Cursor myCursor = getCursor();
        myCursor.moveToPosition(position);

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
        }

        TextView titulo = (TextView) convertView.findViewById(R.id.TVtitle);
        ImageView poster = (ImageView) convertView.findViewById(R.id.IVposter);
        TextView popularity = (TextView) convertView.findViewById(R.id.TVrating);
        TextView release = (TextView) convertView.findViewById(R.id.TVrelease);


        String title = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_TITLE));
        String rating = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_POPULARITY));
        String releaseDate = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_RELEASE_DATE));
        String posterPath = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_IMAGE_URL));


        titulo.setText(title);
        Picasso.with(context).load(POSTER_BASE_URL +POSTER_SIZE+posterPath).fit().into(poster);
        popularity.setText(rating+" %");
        release.setText(releaseDate);

        return convertView;
    }
}






















