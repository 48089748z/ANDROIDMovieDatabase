package practica1.com.peliculas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import practica1.com.peliculas.Json.FilmService;
import practica1.com.peliculas.Json.Result;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{
    private MovieService service;
    private FilmGridViewAdapter GridViewAdapter;
    private FilmListViewAdapter ListViewAdapter;
    private ArrayList<Result> items;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true); //Li indiquem que el fragment te Menu
        View mainFragment = inflater.inflate(R.layout.fragment_main, container, false);
        retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieService.class);
        items = new ArrayList<>();

        myGrid = (GridView) mainFragment.findViewById(R.id.GVmyGrid);
        GridViewAdapter = new FilmGridViewAdapter(getContext(), 0, items); //Creem un adaptador per al ListView
        myGrid.setAdapter(GridViewAdapter); //Afegim el adaptador al ListView
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detailsActivity = new Intent(getContext(), DetailsActivity.class);
                detailsActivity.putExtra("selectedFilm", selectedFilm);
                startActivity(detailsActivity);
            }
        });

        myList = (ListView) mainFragment.findViewById(R.id.LVmyList);
        ListViewAdapter = new FilmListViewAdapter(getContext(), 0, items);
        myList.setAdapter(ListViewAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detailsActivity = new Intent(getContext(), DetailsActivity.class);
                detailsActivity.putExtra("selectedFilm", selectedFilm);
                startActivity(detailsActivity);
            }
        });
        return mainFragment;
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

    public void refresh()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow", "0"))
        {
            case "0":
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Popular Films");
                refreshPopulars();
                break;
            }
            case "1":
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Top Rated Films");
                refreshTopRated();
                break;
            }
        }

    }
    public void refreshPopulars()
    {
        Call<FilmService> call = service.getPopulars(APY_KEY, PAGE); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService resultado = response.body();
                GridViewAdapter.clear();
                GridViewAdapter.addAll(resultado.getResults());
                ListViewAdapter.clear();
                ListViewAdapter.addAll(resultado.getResults());
            }
            public void onFailure(Throwable t) {}});
    }

    public void refreshTopRated()
    {
        Call<FilmService> call = service.getTopRated(APY_KEY, PAGE); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService resultado = response.body();
                GridViewAdapter.clear();
                GridViewAdapter.addAll(resultado.getResults());
                ListViewAdapter.clear();
                ListViewAdapter.addAll(resultado.getResults());
            }
            public void onFailure(Throwable t) {}});
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
}








