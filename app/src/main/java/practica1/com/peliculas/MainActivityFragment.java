package practica1.com.peliculas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import practica1.com.peliculas.Json.FilmService;
import practica1.com.peliculas.Json.Result;
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
    private MovieService service;
    private MovieDataBaseAdapter myAdapter;
    private ArrayList<Result> items;
    private ListView myList;
    private TextView myFilms;
    private Retrofit retrofit;
    final private String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final private String APY_KEY = "db94687026da4c4526fdd35d2e7b2f10";
    //FULL LINK POPULARS:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10
    //FULL LINK BEST RATED:   http://api.themoviedb.org/3/movie/top_rated?api_key=db94687026da4c4526fdd35d2e7b2f10

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
        View fragmento = inflater.inflate(R.layout.fragment_main, container, false);

        retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieService.class);
        items = new ArrayList<>();
        myFilms = (TextView) fragmento.findViewById(R.id.TVmyFilms); //Asignem variables amb les ids corresponents
        myList = (ListView) fragmento.findViewById(R.id.LVmyList);
        myAdapter = new MovieDataBaseAdapter(getContext(), 0, items); //Creem un adaptador per al ListView
        myList.setAdapter(myAdapter); //Afegim el adaptador al ListView
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Result selectedFilm = (Result) parent.getItemAtPosition(position);
                Intent detailsActivity = new Intent(getContext(), DetailsActivity.class);
                detailsActivity.putExtra("selectedFilm", selectedFilm);
                startActivity(detailsActivity);
            }
        });
        return fragmento;
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
        return super.onOptionsItemSelected(item);
    }
    public void refresh()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        switch (preferences.getString("filmsToShow" ,"0"))
        {
            case "0":
            {
                refreshPopulars();
                break;
            }
            case "1":
            {
                refreshTopRated();
                break;
            }
        }
    }
    public void refreshPopulars()
    {
        Call<FilmService> call = service.getPopulars(APY_KEY); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService resultado = response.body();
                myAdapter.clear(); //Borrem el contingut del ListView;
                myAdapter.addAll(resultado.getResults()); //Fiquem tot el contingut del arrayList que guarda els titols i puntuacions que hem extret del Json al ListView
            }
            public void onFailure(Throwable t) {}});
    }

    public void refreshTopRated()
    {
        Call<FilmService> call = service.getTopRated(APY_KEY); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService resultado = response.body();
                myAdapter.clear(); //Borrem el contingut del ListView
                myAdapter.addAll(resultado.getResults()); //Fiquem tot el contingut del arrayList que guarda els titols i puntuacions que hem extret del Json al ListView
            }
            public void onFailure(Throwable t) {}});
    }
    public interface MovieService
    {
        @GET("popular") //Segona part de la URL que conte el Json
        Call<FilmService> getPopulars(
                @Query ("api_key") String api_key);

        @GET("top_rated") //Segona part de la URL que conte el Json
        Call<FilmService> getTopRated(
                @Query ("api_key") String api_key);
    }
}








