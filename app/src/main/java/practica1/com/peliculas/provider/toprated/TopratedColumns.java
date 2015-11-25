package practica1.com.peliculas.provider.toprated;

import android.net.Uri;
import android.provider.BaseColumns;

import practica1.com.peliculas.provider.MoviesProvider;
import practica1.com.peliculas.provider.populars.PopularsColumns;
import practica1.com.peliculas.provider.toprated.TopratedColumns;

/**
 * Table of Top Rated Movies
 */
public class TopratedColumns implements BaseColumns {
    public static final String TABLE_NAME = "toprated";
    public static final Uri CONTENT_URI = Uri.parse(MoviesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String MOVIE_TITLE = "movie_title";

    public static final String MOVIE_RELEASE_DATE = "movie_release_date";

    public static final String MOVIE_POPULARITY = "movie_popularity";

    public static final String MOVIE_DESCRIPTION = "movie_description";

    public static final String MOVIE_IMAGE_URL = "movie_image_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_TITLE,
            MOVIE_RELEASE_DATE,
            MOVIE_POPULARITY,
            MOVIE_DESCRIPTION,
            MOVIE_IMAGE_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_TITLE) || c.contains("." + MOVIE_TITLE)) return true;
            if (c.equals(MOVIE_RELEASE_DATE) || c.contains("." + MOVIE_RELEASE_DATE)) return true;
            if (c.equals(MOVIE_POPULARITY) || c.contains("." + MOVIE_POPULARITY)) return true;
            if (c.equals(MOVIE_DESCRIPTION) || c.contains("." + MOVIE_DESCRIPTION)) return true;
            if (c.equals(MOVIE_IMAGE_URL) || c.contains("." + MOVIE_IMAGE_URL)) return true;
        }
        return false;
    }

}
