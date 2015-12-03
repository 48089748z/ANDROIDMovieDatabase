package practica1.com.peliculas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnMovieSelectedListener
{
    public Toolbar toolbar;

    public void onMovieSelected(long id)
    {
        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet)
        {
            DetailsActivityFragment detailFragment = (DetailsActivityFragment)
                    getSupportFragmentManager().findFragmentById(R.id.detailFragment);
                    detailFragment.loadMovieFromActivity(id);
        }
        else
        {
            Intent detailsActivity = new Intent(this, DetailsActivity.class);
            detailsActivity.putExtra("cursor_id", id);
            startActivity(detailsActivity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Popular Films");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

