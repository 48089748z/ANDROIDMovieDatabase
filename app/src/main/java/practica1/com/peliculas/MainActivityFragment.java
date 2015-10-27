package practica1.com.peliculas;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class MainActivityFragment extends Fragment {

    private MovieDbService service;
    private ArrayAdapter<String> myAdapter;
    private ArrayList<String> items;
    private ListView myList;
    private TextView myFilms;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        setHasOptionsMenu(true); //Li indiquem que el fragment te Menu
        View fragmento = inflater.inflate(R.layout.fragment_main, container, false);

        items = new ArrayList<>();
        myFilms = (TextView) fragmento.findViewById(R.id.TVmyFilms); //Asignem variables amb les ids corresponents
        myList = (ListView) fragmento.findViewById(R.id.LVmyList);
        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items); //Creem un adaptador per al ListView
        myList.setAdapter(myAdapter); //Afegim el adaptador al ListView
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //items.remove(position);
                //myAdapter.notifyDataSetChanged();
                return true;
            }
        });
        myAdapter.add("Peli per defecte 1"); //Primer introduim pelicules inventades al List View per comprovar que funciona
        myAdapter.add("Peli per defecte 2");
        myAdapter.add("Peli per defecte 3");
        myAdapter.add("Peli per defecte 4");
        myAdapter.add("Peli per defecte 5");
        myAdapter.add("Peli per defecte 6");
        myAdapter.add("Peli per defecte 7");

        return fragmento;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh(); //Quan clickem refresh al Menu s'executara aquest metode.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh()
    {

        final String URL_POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/"; //Primera part de la URL que conte el Json de pelicules populars
        final String APY_KEY = "db94687026da4c4526fdd35d2e7b2f10"; //La meva APY_KEY de la Base de dades de pelicules
        //popular?api_key=db94687026da4c4526fdd35d2e7b2f10

        Retrofit retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(URL_POPULAR_MOVIES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieDbService.class);
        ;
        Call<FilmService> call = (Call<FilmService>) service.pelisPopulares(APY_KEY); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                ArrayList<String> popularFilms = new ArrayList<>();
                FilmService resultado = response.body();
                for (Result list : resultado.getResults()) //Per cada resultat agafarem titol i puntuació de la seva popularitat.
                {
                    String title = list.getTitle();
                    Double popularity = list.getPopularity();
                    popularFilms.add(title+": "+String.valueOf(popularity)); //Els afegirem a un arrayList en comptes de ficarlos al ListView per questions d'eficiencia
                }
                myAdapter.clear(); //Borrem el contingut del ListView
                myAdapter.addAll(popularFilms); //Fiquem tot el contingut del arrayList que guarda els titols i puntuacions que hem extret del Json al ListView
            }
            public void onFailure(Throwable t) {

            }
        });

    }
    public interface MovieDbService //
    {
        @GET("popular") //Segona part de la URL que conte el Json
        Call<FilmService> pelisPopulares(
                @Query ("api_key") String api_key);
    }

}





