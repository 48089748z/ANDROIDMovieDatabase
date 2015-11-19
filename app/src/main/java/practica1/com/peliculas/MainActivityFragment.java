package practica1.com.peliculas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import practica1.com.peliculas.Json.FilmService;
import practica1.com.peliculas.Json.Result;
import practica1.com.peliculas.provider.movie.MovieColumns;
import practica1.com.peliculas.provider.movie.MovieContentValues;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{
    //private FilmGridViewAdapter GridViewAdapter;
    //private FilmListViewAdapter ListViewAdapter;
    //private ArrayList<Result> items = new ArrayList<>();
    private DBGridViewAdapter GridViewDBAdapter;
    private DBListViewAdapter ListViewDBAdapter;
    private MovieService service;
    private Cursor cursor;
    private GridView myGrid;
    private ListView myList;
    private Retrofit retrofit;
    final private String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final private String APY_KEY = "db94687026da4c4526fdd35d2e7b2f10";
    public static Integer PAGE = 1;
    //FULL LINK POPULARS PAGE 2:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10&page=2
    //FULL LINK POPULARS:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10
    //FULL LINK TOP RATED LINK:   http://api.themoviedb.org/3/movie/top_rated?api_key=db94687026da4c4526fdd35d2e7b2f10

    public MainActivityFragment(){}
    public void onStart()
    {
        super.onStart();
        refresh();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true); //Li indiquem que el fragment te Menu
        View mainFragment = inflater.inflate(R.layout.fragment_main, container, false);
        createRetrofit();


        //GridViewAdapter = new FilmGridViewAdapter(getContext(), 0, items); //Creem un adaptador per al ListView
        //myGrid.setAdapter(GridViewAdapter); //Afegim el adaptador al ListView
        myGrid = (GridView) mainFragment.findViewById(R.id.GVmyGrid);
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detailsActivity = new Intent(getContext(), DetailsActivity.class);
                detailsActivity.putExtra("cursor", (Serializable) cursor);
                startActivity(detailsActivity);}});

        //ListViewAdapter = new FilmListViewAdapter(getContext(), 0, items);
        //myList.setAdapter(ListViewAdapter);
        myList = (ListView) mainFragment.findViewById(R.id.LVmyList);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detailsActivity = new Intent(getContext(), DetailsActivity.class);
                detailsActivity.putExtra("cursor", (Serializable) cursor);
                startActivity(detailsActivity);}});

        GridViewDBAdapter = new DBGridViewAdapter(
                getContext(),
                R.layout.gridview_layout,
                null,
                new String[] { MovieColumns.MOVIE_TITLE, MovieColumns.MOVIE_IMAGE_URL },
                new int[] { R.id.TVgridViewTitle, R.id.IVposter },
                0);

        ListViewDBAdapter = new DBListViewAdapter(
                getContext(),
                R.layout.gridview_layout,
                null,
                new String[] { MovieColumns.MOVIE_TITLE,MovieColumns.MOVIE_POPULARITY, MovieColumns.MOVIE_RELEASE_DATE, MovieColumns.MOVIE_IMAGE_URL },
                new int[] { R.id.TVtitle, R.id.TVrating, R.id.TVrelease, R.id.IVposter },
                0);

        myGrid.setAdapter(GridViewDBAdapter);
        myList.setAdapter(ListViewDBAdapter);
        return mainFragment;
    }

    public void refresh()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow", "0"))
        {
            case "0":
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Popular Films");
                downloadPopulars();
                break;
            }
            case "1":
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Top Rated Films");
                downloadTopRated();
                break;
            }
        }

    }
    public void downloadPopulars()
    {
        Call<FilmService> call = service.getPopulars(APY_KEY, PAGE); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService filmsController = response.body();
                for (Result movie : filmsController.getResults())
                {
                    MovieContentValues values = new MovieContentValues();
                    values.putMovieTitle(movie.getTitle().toString());
                    values.putMovieReleaseDate(movie.getReleaseDate().toString());
                    values.putMoviePopularity(movie.getPopularity().toString());
                    values.putMovieDescription(movie.getOverview());
                    values.putMovieImageUrl(movie.getPosterPath());
                    getContext().getContentResolver().insert(MovieColumns.CONTENT_URI, values.values());
                }
                showMovies();
            }
            public void onFailure(Throwable t) {
                showMovies();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showMovies()
    {
         cursor = getContext().getContentResolver().query(
                MovieColumns.CONTENT_URI,
                null,
                null,
                null,
                "_id"
        );
        GridViewDBAdapter.swapCursor(cursor);
        ListViewDBAdapter.swapCursor(cursor);
    }

    public void downloadTopRated()
    {
        Call<FilmService> call = service.getTopRated(APY_KEY, PAGE); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService filmsController = response.body();
                for (Result movie : filmsController.getResults())
                {
                    MovieContentValues values = new MovieContentValues();
                    values.putMovieTitle(movie.getTitle().toString());
                    values.putMovieReleaseDate(movie.getReleaseDate().toString());
                    values.putMoviePopularity(movie.getPopularity().toString());
                    values.putMovieDescription(movie.getOverview());
                    values.putMovieImageUrl(movie.getPosterPath());
                    getContext().getContentResolver().insert(MovieColumns.CONTENT_URI, values.values());
                }
                showMovies();
            }

            public void onFailure(Throwable t) {
                showMovies();
            }
        });
    }
    public void createRetrofit()
    {
        retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieService.class);
    }
    public interface MovieService
    {
        @GET("popular") //Segona part de la URL que conte el Json
        Call<FilmService> getPopulars(
                @Query("api_key") String api_key,
                @Query("page") Integer page);

        @GET("top_rated") //Segona part de la URL que conte el Json
        Call<FilmService> getTopRated(
                @Query ("api_key") String api_key,
                @Query ("page") Integer page);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
            refresh(); //Quan clickem refresh al Menu s'executara aquest metode.
            return true;
        }
        if (id == R.id.action_gridview)
        {
            myList.setVisibility(View.GONE);
            myGrid.setVisibility(View.VISIBLE);
            return true;
        }
        if (id == R.id.action_listview)
        {
            myList.setVisibility(View.VISIBLE);
            myGrid.setVisibility(View.GONE);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}