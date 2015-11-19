package practica1.com.peliculas;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import practica1.com.peliculas.Json.Result;
import practica1.com.peliculas.provider.movie.MovieColumns;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment
{
    DecimalFormat oneDecimal = new DecimalFormat("#.#");
    TextView title;
    TextView popularity;
    TextView release;
    TextView description;
    ImageView poster;
    final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";

    public DetailsActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View detailsFragment = inflater.inflate(R.layout.fragment_details, container, false);

        Cursor myCursor = (Cursor) getActivity().getIntent().getExtras().get("cursor");
        title = (TextView) detailsFragment.findViewById(R.id.TVtitleDetails);
        popularity = (TextView) detailsFragment.findViewById(R.id.TVratingDetails);
        release = (TextView) detailsFragment.findViewById(R.id.TVreleaseDetails);
        description = (TextView) detailsFragment.findViewById(R.id.TVdescriptionDetails);
        poster = (ImageView) detailsFragment.findViewById(R.id.IVposterDetails);


        String tit = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_TITLE));
        String rating = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_POPULARITY));
        String releaseDate = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_RELEASE_DATE));
        String desc = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_DESCRIPTION));
        String posterPath = myCursor.getString(myCursor.getColumnIndex(MovieColumns.MOVIE_IMAGE_URL));

        title.setText(tit);
        popularity.setText(rating+" %");
        release.setText   ("Release Date:  "+releaseDate);
        description.setText("DESCRIPTION:\n"+desc);
        Picasso.with(getContext()).load(POSTER_BASE_URL+POSTER_SIZE+posterPath).fit().into(poster);

        return detailsFragment;
    }
}
