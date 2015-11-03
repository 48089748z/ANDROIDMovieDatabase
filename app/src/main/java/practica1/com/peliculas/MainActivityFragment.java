package practica1.com.peliculas;

import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
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
    private getPopularService serviceA;
    private getTopRatedService serviceB;
    private ArrayAdapter<String> myAdapter;
    private ArrayList<String> items;
    private ListView myList;
    private TextView myFilms;
    private Retrofit retrofit;
    final private String BASE_URL = "http://api.themoviedb.org/3/movie/";
    final private String APY_KEY = "db94687026da4c4526fdd35d2e7b2f10";
    //FULL LINK POPULARS:     http://api.themoviedb.org/3/movie/popular?api_key=db94687026da4c4526fdd35d2e7b2f10
    //FULL LINK BEST RATED:   http://api.themoviedb.org/3/movie/top_rated?api_key=db94687026da4c4526fdd35d2e7b2f10

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        setHasOptionsMenu(true); //Li indiquem que el fragment te Menu
        View fragmento = inflater.inflate(R.layout.fragment_main, container, false);

        retrofit = new Retrofit.Builder()  //Creem el Retrofit, per ferho haurem de ficar les linees corresponents al build.gradle (Module: app)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
        myAdapter.add("SHOWING DEFAULT");
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
        serviceA = retrofit.create(getPopularService.class);
        Call<FilmService> call = serviceA.pelisPopulares(APY_KEY); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                FilmService resultado = response.body();
                ArrayList<String> arrayFilms = new ArrayList<>();
                for (Result list : resultado.getResults()) //Per cada resultat agafarem titol i puntuació de la seva popularitat.
                {

                    String title = list.getTitle();
                    Double popularity = list.getPopularity();
                    arrayFilms.add(title+": "+String.valueOf(popularity)); //Els afegirem a un arrayList en comptes de ficarlos al ListView per questions d'eficiencia
                }
                myAdapter.clear(); //Borrem el contingut del ListView
                myAdapter.add("SHOWING POPULAR");
                myAdapter.addAll(arrayFilms); //Fiquem tot el contingut del arrayList que guarda els titols i puntuacions que hem extret del Json al ListView
            }
            public void onFailure(Throwable t) {}});
    }

    public void refreshTopRated()
    {
        serviceB = retrofit.create(getTopRatedService.class);
        Call<FilmService> call = serviceB.pelisPopulares(APY_KEY); //Fem un call en segon pla
        call.enqueue(new Callback<FilmService>()
        {
            @Override
            public void onResponse(Response<FilmService> response, Retrofit retrofit)
            {
                ArrayList<String> arrayFilms = new ArrayList<>();
                FilmService resultado = response.body();
                for (Result list : resultado.getResults()) //Per cada resultat agafarem titol i puntuació de la seva popularitat.
                {
                    String title = list.getTitle();
                    Double popularity = list.getPopularity();
                    arrayFilms.add(title+": "+String.valueOf(popularity)); //Els afegirem a un arrayList en comptes de ficarlos al ListView per questions d'eficiencia
                }
                myAdapter.clear(); //Borrem el contingut del ListView
                myAdapter.add("SHOWING TOP RATED");
                myAdapter.addAll(arrayFilms); //Fiquem tot el contingut del arrayList que guarda els titols i puntuacions que hem extret del Json al ListView
            }
            public void onFailure(Throwable t) {}});
    }
    public interface getPopularService
    {
        @GET("popular") //Segona part de la URL que conte el Json
        Call<FilmService> pelisPopulares(
                @Query ("api_key") String api_key);
    }
    public interface getTopRatedService
    {
        @GET("top_rated") //Segona part de la URL que conte el Json
        Call<FilmService> pelisPopulares(
                @Query ("api_key") String api_key);
    }
}

/*  TOT AIXO ES UNA PROVA DE COM MOSTRAR IMATGES


    new ImageDownloader(posters).execute("http://vignette3.wikia.nocookie.net/pokemon/images/1/16/025Pikachu_OS_anime_10.png/revision/20150102074354");
    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    */






