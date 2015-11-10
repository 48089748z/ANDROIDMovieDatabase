package practica1.com.peliculas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import practica1.com.peliculas.Json.Result;

public class DetailsActivity extends AppCompatActivity
{
    DecimalFormat oneDecimal = new DecimalFormat("#.#");
    TextView title;
    TextView popularity;
    TextView release;
    TextView description;
    ImageView poster;
    final private String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title = (TextView) findViewById(R.id.TVtitleDetails);
        popularity = (TextView) findViewById(R.id.TVratingDetails);
        release = (TextView) findViewById(R.id.TVreleaseDetails);
        description = (TextView) findViewById(R.id.TVdescriptionDetails);
        poster = (ImageView) findViewById(R.id.IVposterDetails);

        Result selectedFilm = (Result) getIntent().getExtras().get("selectedFilm");
        title.setText     ("\n "+selectedFilm.getTitle().toString().toUpperCase());
        popularity.setText("\n Rating:  "+(oneDecimal.format(selectedFilm.getPopularity())+"%".toUpperCase()));
        release.setText   (" Release:  "+selectedFilm.getReleaseDate());
        description.setText("\nDESCRIPTION:\n"+selectedFilm.getOverview());
        Picasso.with(this).load(POSTER_BASE_URL+POSTER_SIZE+selectedFilm.getPosterPath()).fit().into(poster);
    }
}