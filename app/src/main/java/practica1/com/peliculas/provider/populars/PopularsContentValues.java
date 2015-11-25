package practica1.com.peliculas.provider.populars;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import practica1.com.peliculas.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code populars} table.
 */
public class PopularsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PopularsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PopularsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable PopularsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PopularsContentValues putMovieTitle(@Nullable String value) {
        mContentValues.put(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsContentValues putMovieTitleNull() {
        mContentValues.putNull(PopularsColumns.MOVIE_TITLE);
        return this;
    }

    public PopularsContentValues putMovieReleaseDate(@Nullable String value) {
        mContentValues.put(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsContentValues putMovieReleaseDateNull() {
        mContentValues.putNull(PopularsColumns.MOVIE_RELEASE_DATE);
        return this;
    }

    public PopularsContentValues putMoviePopularity(@Nullable String value) {
        mContentValues.put(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsContentValues putMoviePopularityNull() {
        mContentValues.putNull(PopularsColumns.MOVIE_POPULARITY);
        return this;
    }

    public PopularsContentValues putMovieDescription(@Nullable String value) {
        mContentValues.put(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsContentValues putMovieDescriptionNull() {
        mContentValues.putNull(PopularsColumns.MOVIE_DESCRIPTION);
        return this;
    }

    public PopularsContentValues putMovieImageUrl(@Nullable String value) {
        mContentValues.put(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsContentValues putMovieImageUrlNull() {
        mContentValues.putNull(PopularsColumns.MOVIE_IMAGE_URL);
        return this;
    }
}
