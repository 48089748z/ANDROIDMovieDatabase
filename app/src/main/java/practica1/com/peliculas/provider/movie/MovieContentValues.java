package practica1.com.peliculas.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import practica1.com.peliculas.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movie} table.
 */
public class MovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MovieContentValues putMovieTitle(@Nullable String value) {
        mContentValues.put(MovieColumns.MOVIE_TITLE, value);
        return this;
    }

    public MovieContentValues putMovieTitleNull() {
        mContentValues.putNull(MovieColumns.MOVIE_TITLE);
        return this;
    }

    public MovieContentValues putMovieReleaseDate(@Nullable String value) {
        mContentValues.put(MovieColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public MovieContentValues putMovieReleaseDateNull() {
        mContentValues.putNull(MovieColumns.MOVIE_RELEASE_DATE);
        return this;
    }

    public MovieContentValues putMoviePopularity(@Nullable String value) {
        mContentValues.put(MovieColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public MovieContentValues putMoviePopularityNull() {
        mContentValues.putNull(MovieColumns.MOVIE_POPULARITY);
        return this;
    }

    public MovieContentValues putMovieDescription(@Nullable String value) {
        mContentValues.put(MovieColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public MovieContentValues putMovieDescriptionNull() {
        mContentValues.putNull(MovieColumns.MOVIE_DESCRIPTION);
        return this;
    }

    public MovieContentValues putMovieImageUrl(@Nullable String value) {
        mContentValues.put(MovieColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public MovieContentValues putMovieImageUrlNull() {
        mContentValues.putNull(MovieColumns.MOVIE_IMAGE_URL);
        return this;
    }
}
