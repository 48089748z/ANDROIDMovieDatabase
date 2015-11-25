package practica1.com.peliculas;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import practica1.com.peliculas.provider.populars.PopularsColumns;
import practica1.com.peliculas.provider.toprated.TopratedColumns;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment
{
    TextView title;
    TextView popularity;
    TextView release;
    TextView description;
    ImageView poster;
    final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";
    private long itemId = -1;

    private String tit;
    private String rating;
    private String releaseDate;
    private String desc;
    private String posterPath;

    public DetailsActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View detailsFragment = inflater.inflate(R.layout.fragment_details, container, false);
        title = (TextView) detailsFragment.findViewById(R.id.TVtitleDetails);
        popularity = (TextView) detailsFragment.findViewById(R.id.TVratingDetails);
        release = (TextView) detailsFragment.findViewById(R.id.TVreleaseDetails);
        description = (TextView) detailsFragment.findViewById(R.id.TVdescriptionDetails);
        poster = (ImageView) detailsFragment.findViewById(R.id.IVposterDetails);

        itemId = getActivity().getIntent().getLongExtra("cursor_id", -1);
        if (itemId != -1)
        {
            cargarPelicula();
        }
        return detailsFragment;
    }
    private void cargarPelicula()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow", "0"))
        {
            case "0": {
                Cursor myCursor = getContext().getContentResolver().query(
                        PopularsColumns.CONTENT_URI,
                        null,
                        PopularsColumns._ID + " = ?",
                        new String[]{String.valueOf(itemId)},
                        "_id");
                if (myCursor != null)
                {
                    myCursor.moveToNext();
                    tit = myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_TITLE));
                    rating = myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_POPULARITY));
                    releaseDate = myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_RELEASE_DATE));
                    desc = myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_DESCRIPTION));
                    posterPath = myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_IMAGE_URL));
                }
            }
            case "1": {
                Cursor myCursor = getContext().getContentResolver().query(
                        TopratedColumns.CONTENT_URI,
                        null,
                        TopratedColumns._ID + " = ?",
                        new String[]{String.valueOf(itemId)},
                        "_id");
                if (myCursor != null) {
                    myCursor.moveToNext();
                    tit = myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_TITLE));
                    rating = myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_POPULARITY));
                    releaseDate = myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_RELEASE_DATE));
                    desc = myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_DESCRIPTION));
                    posterPath = myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_IMAGE_URL));
                }
            }
        }
        title.setText(tit);
        popularity.setText(rating + " %");
        release.setText("Release Date:  " + releaseDate);
        description.setText("DESCRIPTION:\n" + desc);
        Picasso.with(getContext()).load(POSTER_BASE_URL + POSTER_SIZE + posterPath).fit().into(poster);
    }
}
