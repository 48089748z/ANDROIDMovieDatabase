package practica1.com.peliculas.provider.toprated;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import practica1.com.peliculas.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code toprated} table.
 */
public class TopratedContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TopratedColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TopratedSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TopratedSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TopratedContentValues putMovieTitle(@Nullable String value) {
        mContentValues.put(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedContentValues putMovieTitleNull() {
        mContentValues.putNull(TopratedColumns.MOVIE_TITLE);
        return this;
    }

    public TopratedContentValues putMovieReleaseDate(@Nullable String value) {
        mContentValues.put(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedContentValues putMovieReleaseDateNull() {
        mContentValues.putNull(TopratedColumns.MOVIE_RELEASE_DATE);
        return this;
    }

    public TopratedContentValues putMoviePopularity(@Nullable String value) {
        mContentValues.put(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedContentValues putMoviePopularityNull() {
        mContentValues.putNull(TopratedColumns.MOVIE_POPULARITY);
        return this;
    }

    public TopratedContentValues putMovieDescription(@Nullable String value) {
        mContentValues.put(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedContentValues putMovieDescriptionNull() {
        mContentValues.putNull(TopratedColumns.MOVIE_DESCRIPTION);
        return this;
    }

    public TopratedContentValues putMovieImageUrl(@Nullable String value) {
        mContentValues.put(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedContentValues putMovieImageUrlNull() {
        mContentValues.putNull(TopratedColumns.MOVIE_IMAGE_URL);
        return this;
    }
}
