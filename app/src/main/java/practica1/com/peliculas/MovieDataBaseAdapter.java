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
public class MovieDataBaseAdapter extends ArrayAdapter<Result>
{
        final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
        final private String POSTER_SIZE = "w185";
        DecimalFormat oneDecimal = new DecimalFormat("#.#");
        public MovieDataBaseAdapter(Context context, int resource, List<Result> objects)
        {
            super(context, resource, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Result peli = getItem(position);
            if (convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.main_listview_layout, parent, false);
            }
            TextView titulo = (TextView) convertView.findViewById(R.id.TVtitle);
            TextView popularidad = (TextView) convertView.findViewById(R.id.TVrating);
            TextView release = (TextView) convertView.findViewById(R.id.TVrelease);
            ImageView poster = (ImageView) convertView.findViewById(R.id.IVposter);

            titulo.setText("   "+peli.getTitle());
            popularidad.setText("   Rating: "+String.valueOf(oneDecimal.format(peli.getPopularity()))+"%");
            release.setText("   "+peli.getReleaseDate());
            Picasso.with(getContext()).load(POSTER_BASE_URL+POSTER_SIZE+peli.getPosterPath()).fit().into(poster);
            return convertView;
        }
}
