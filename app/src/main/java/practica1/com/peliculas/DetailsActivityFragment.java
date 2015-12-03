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
    final private String POSTER_SIZE = "w342";
    long id = -1;
    public DetailsActivityFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailsFragment = inflater.inflate(R.layout.fragment_details, container, false);
        title = (TextView) detailsFragment.findViewById(R.id.TVtitleDetails);
        popularity = (TextView) detailsFragment.findViewById(R.id.TVratingDetails);
        release = (TextView) detailsFragment.findViewById(R.id.TVreleaseDetails);
        description = (TextView) detailsFragment.findViewById(R.id.TVdescriptionDetails);
        poster = (ImageView) detailsFragment.findViewById(R.id.IVposterDetails);

        id = getActivity().getIntent().getLongExtra("cursor_id", -1);
        if (id != -1)
        {
            cargarPelicula(detailsFragment, id);
        }
        return detailsFragment;
    }
    public void loadMovieFromActivity(Long id)
    {
        View view = getView();
        cargarPelicula(view, id);
    }
    private void cargarPelicula(View view, long id)
    {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (preferences.getString("filmsToShow", "0").equals("0"))
            {
                    Cursor myCursor = getContext().getContentResolver().query(
                            PopularsColumns.CONTENT_URI,
                            null,
                            PopularsColumns._ID + " = ?",
                            new String[]{String.valueOf(id)},
                            "_id");
                    if (myCursor != null)
                    {
                        myCursor.moveToNext();
                        title.setText(myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_TITLE)));
                        popularity.setText(myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_POPULARITY))+ " %");
                        release.setText("Release Date:  " +myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_RELEASE_DATE)));
                        description.setText("DESCRIPTION:\n" +myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_DESCRIPTION)));
                        Picasso.with(getContext()).load(POSTER_BASE_URL + POSTER_SIZE + myCursor.getString(myCursor.getColumnIndex(PopularsColumns.MOVIE_IMAGE_URL))).fit().into(poster);
                    }
            }
            else if (preferences.getString("filmsToShow", "0").equals("1"))
            {
                    Cursor myCursor = getContext().getContentResolver().query(
                            TopratedColumns.CONTENT_URI,
                            null,
                            TopratedColumns._ID + " = ?",
                            new String[]{String.valueOf(id)},
                            "_id");
                    if (myCursor != null)
                    {
                        myCursor.moveToNext();
                        title.setText(myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_TITLE)));
                        popularity.setText(myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_POPULARITY))+ " %");
                        release.setText("Release Date:  " +myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_RELEASE_DATE)));
                        description.setText("DESCRIPTION:\n" +myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_DESCRIPTION)));
                        Picasso.with(getContext()).load(POSTER_BASE_URL + POSTER_SIZE + myCursor.getString(myCursor.getColumnIndex(TopratedColumns.MOVIE_IMAGE_URL))).fit().into(poster);
                    }
            }
    }
}
