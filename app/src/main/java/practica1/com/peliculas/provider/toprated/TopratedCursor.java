package practica1.com.peliculas.provider.toprated;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import practica1.com.peliculas.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code toprated} table.
 */
public class TopratedCursor extends AbstractCursor implements TopratedModel {
    public TopratedCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TopratedColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code movie_title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieTitle() {
        String res = getStringOrNull(TopratedColumns.MOVIE_TITLE);
        return res;
    }

    /**
     * Get the {@code movie_release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieReleaseDate() {
        String res = getStringOrNull(TopratedColumns.MOVIE_RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code movie_popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviePopularity() {
        String res = getStringOrNull(TopratedColumns.MOVIE_POPULARITY);
        return res;
    }

    /**
     * Get the {@code movie_description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieDescription() {
        String res = getStringOrNull(TopratedColumns.MOVIE_DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code movie_image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieImageUrl() {
        String res = getStringOrNull(TopratedColumns.MOVIE_IMAGE_URL);
        return res;
    }
}
