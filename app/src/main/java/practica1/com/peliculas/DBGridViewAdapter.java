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


/**
 * Created by 48089748z on 18/11/15.
 */
public class DBGridViewAdapter extends SimpleCursorAdapter
{
    final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";
    Context context;

    public void setFrom(String[] from) {
        this.from = from;
    }

    String [] from;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DBGridViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
        this.from=from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Cursor myCursor = getCursor();
        myCursor.moveToPosition(position);

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.gridview_layout, parent, false);
        }
        TextView titulo = (TextView) convertView.findViewById(R.id.TVgridViewTitle);
        ImageView poster = (ImageView) convertView.findViewById(R.id.IVposter);

        titulo.setText(myCursor.getString(myCursor.getColumnIndex(from[0])));
        String posterPath = myCursor.getString(myCursor.getColumnIndex(from[1]));

        Picasso.with(context).load(POSTER_BASE_URL+POSTER_SIZE+posterPath).fit().into(poster);
        return convertView;
    }
}
