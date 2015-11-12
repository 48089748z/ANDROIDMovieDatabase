package practica1.com.peliculas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import practica1.com.peliculas.Json.Result;

/**
 * Created by 48089748z on 06/11/15.
 */
public class FilmGridViewAdapter extends ArrayAdapter<Result>
{
        final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
        final private String POSTER_SIZE = "w185";
        public FilmGridViewAdapter(Context context, int resource, List<Result> objects)
        {
            super(context, resource, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Result selectedFilm = getItem(position);
            if (convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.gridview_layout, parent, false);
            }
            TextView titulo = (TextView) convertView.findViewById(R.id.TVgridViewTitle);
            ImageView poster = (ImageView) convertView.findViewById(R.id.IVposter);
            titulo.setText(selectedFilm.getTitle());
            Picasso.with(getContext()).load(POSTER_BASE_URL+POSTER_SIZE+selectedFilm.getPosterPath()).fit().into(poster);
            return convertView;
        }
}
