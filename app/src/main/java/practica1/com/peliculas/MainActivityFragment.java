package practica1.com.peliculas;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import java.io.IOException;

import practica1.com.peliculas.Json.FilmService;
import practica1.com.peliculas.Json.Result;
import practica1.com.peliculas.provider.populars.PopularsColumns;
import practica1.com.peliculas.provider.populars.PopularsContentValues;
import practica1.com.peliculas.provider.toprated.TopratedColumns;
import practica1.com.peliculas.provider.toprated.TopratedContentValues;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    //private FilmGridViewAdapter GridViewAdapter;
    //private FilmListViewAdapter ListViewAdapter;
    //private ArrayList<Result> items = new ArrayList<>();
    final private String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final private String APY_KEY = "db94687026da4c4526fdd35d2e7b2f10";
    private DBGridViewAdapter GridViewDBAdapter;
    private DBListViewAdapter ListViewDBAdapter;
    private MovieService service;
    private GridView myGrid;
    private ListView myList;
    private Retrofit retrofit;
    private Integer PAGE = 1;
    private SharedPreferences preferences;

    private OnMovieSelectedListener listener;
    //FULL LINK POPULARS PAGE 2:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10&page=2
    //FULL LINK POPULARS:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10
    //FULL LINK TOP RATED LINK:   http://api.themoviedb.org/3/movie/top_rated?api_key=db94687026da4c4526fdd35d2e7b2f10

    public MainActivityFragment(){}
    public void onStart()
    {
        super.onStart();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow", "0"))
        {
            case "0":
            {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popular Films");
                getLoaderManager().restartLoader(0, null, this);
                GridViewDBAdapter.setFrom(new String[]{PopularsColumns.MOVIE_TITLE, PopularsColumns.MOVIE_IMAGE_URL});
                ListViewDBAdapter.setFrom(new String[] {PopularsColumns.MOVIE_TITLE,PopularsColumns.MOVIE_POPULARITY, PopularsColumns.MOVIE_RELEASE_DATE, PopularsColumns.MOVIE_IMAGE_URL });
                break;
            }
            case "1":
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Top Rated Films");
                getLoaderManager().restartLoader(0, null, this);
                GridViewDBAdapter.setFrom(new String[]{TopratedColumns.MOVIE_TITLE, TopratedColumns.MOVIE_IMAGE_URL});
                ListViewDBAdapter.setFrom(new String[] {TopratedColumns.MOVIE_TITLE,TopratedColumns.MOVIE_POPULARITY, TopratedColumns.MOVIE_RELEASE_DATE, TopratedColumns.MOVIE_IMAGE_URL });
                break;
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true); //Li indiquem que el fragment te Menu
        View mainFragment = inflater.inflate(R.layout.fragment_main, container, false);

        getLoaderManager().initLoader(0, null, this);
        createRetrofit();

        //GridViewAdapter = new FilmGridViewAdapter(getContext(), 0, items); //Creem un adaptador per al ListView
        //myGrid.setAdapter(GridViewAdapter); //Afegim el adaptador al ListView
        myGrid = (GridView) mainFragment.findViewById(R.id.GVmyGrid);
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onMovieSelected(id);
            }});

        //ListViewAdapter = new FilmListViewAdapter(getContext(), 0, items);
        //myList.setAdapter(ListViewAdapter);
        myList = (ListView) mainFragment.findViewById(R.id.LVmyList);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onMovieSelected(id);
            }});

        GridViewDBAdapter = new DBGridViewAdapter(
                getContext(),
                R.layout.gridview_layout,
                null,
                new String[] { PopularsColumns.MOVIE_TITLE, PopularsColumns.MOVIE_IMAGE_URL },
                new int[] { R.id.TVgridViewTitle, R.id.IVposter },
                0);

        ListViewDBAdapter = new DBListViewAdapter(
                getContext(),
                R.layout.gridview_layout,
                null,
                new String[] { PopularsColumns.MOVIE_TITLE,PopularsColumns.MOVIE_POPULARITY, PopularsColumns.MOVIE_RELEASE_DATE, PopularsColumns.MOVIE_IMAGE_URL },
                new int[] { R.id.TVtitle, R.id.TVrating, R.id.TVrelease, R.id.IVposter },
                0);

        myGrid.setAdapter(GridViewDBAdapter);
        myList.setAdapter(ListViewDBAdapter);
        return mainFragment;
    }
    public void refresh()
    {
        //BORRAMOS TODA LA BASE DE DATOS Y DESCARGAMOS LA INFORMACION DE INTERNET ACTUALIZADA
        getContext().getContentResolver().delete(
                PopularsColumns.CONTENT_URI,
                null,
                null);
        getContext().getContentResolver().delete(
                TopratedColumns.CONTENT_URI,
                null,
                null);

        UpdateAllMoviesFromInternet uamfi = new UpdateAllMoviesFromInternet();
        uamfi.execute();
    }
    class UpdateAllMoviesFromInternet extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] params)
        {
            downloadMovies();
            return null;
        }
    }
    public void downloadMovies()
    {
        try
        {
            Call<FilmService> call1 = service.getPopulars(APY_KEY, PAGE);
            Response<FilmService> response1 = call1.execute();
            FilmService filmsController1 = response1.body();
            for (Result movie : filmsController1.getResults())
            {
                PopularsContentValues values = new PopularsContentValues();
                values.putMovieTitle(movie.getTitle().toString());
                values.putMovieReleaseDate(movie.getReleaseDate().toString());
                values.putMoviePopularity(movie.getPopularity().toString());
                values.putMovieDescription(movie.getOverview());
                values.putMovieImageUrl(movie.getPosterPath());
                getContext().getContentResolver().insert(PopularsColumns.CONTENT_URI, values.values());
            }

            Call<FilmService> call2 = service.getTopRated(APY_KEY, PAGE);
            Response<FilmService> response2 = call2.execute();
            FilmService filmsController2 = response2.body();
            for (Result movie : filmsController2.getResults())
            {
                TopratedContentValues values = new TopratedContentValues();
                values.putMovieTitle(movie.getTitle().toString());
                values.putMovieReleaseDate(movie.getReleaseDate().toString());
                values.putMoviePopularity(movie.getPopularity().toString());
                values.putMovieDescription(movie.getOverview());
                values.putMovieImageUrl(movie.getPosterPath());
                getContext().getContentResolver().insert(TopratedColumns.CONTENT_URI, values.values());
            }
        }
        catch (IOException e) {}
    }

    public void createRetrofit()
    {
        retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieService.class);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow", "0"))
        {
            case "0":
            {
                return new CursorLoader(getContext(), //CONDICION POPULARES
                        PopularsColumns.CONTENT_URI,
                        null,
                        null,
                        null,
                        "_id");
            }
            case "1":
            {
                return new CursorLoader(getContext(), //CONDICION TOP RATED
                        TopratedColumns.CONTENT_URI,
                        null,
                        null,
                        null,
                        "_id");
            }
            default: return null;
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        GridViewDBAdapter.swapCursor(data);
        ListViewDBAdapter.swapCursor(data);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        GridViewDBAdapter.swapCursor(null);
        ListViewDBAdapter.swapCursor(null);
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
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (OnMovieSelectedListener) context;

    }
    public interface OnMovieSelectedListener
    {
        void onMovieSelected(long id);
    }
}