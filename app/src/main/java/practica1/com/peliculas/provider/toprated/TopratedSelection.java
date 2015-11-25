package practica1.com.peliculas.provider.toprated;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import practica1.com.peliculas.provider.base.AbstractSelection;

/**
 * Selection for the {@code toprated} table.
 */
public class TopratedSelection extends AbstractSelection<TopratedSelection> {
    @Override
    protected Uri baseUri() {
        return TopratedColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TopratedCursor} object, which is positioned before the first entry, or null.
     */
    public TopratedCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TopratedCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TopratedCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TopratedCursor} object, which is positioned before the first entry, or null.
     */
    public TopratedCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TopratedCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TopratedCursor query(Context context) {
        return query(context, null);
    }


    public TopratedSelection id(long... value) {
        addEquals("toprated." + TopratedColumns._ID, toObjectArray(value));
        return this;
    }

    public TopratedSelection idNot(long... value) {
        addNotEquals("toprated." + TopratedColumns._ID, toObjectArray(value));
        return this;
    }

    public TopratedSelection orderById(boolean desc) {
        orderBy("toprated." + TopratedColumns._ID, desc);
        return this;
    }

    public TopratedSelection orderById() {
        return orderById(false);
    }

    public TopratedSelection movieTitle(String... value) {
        addEquals(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection movieTitleNot(String... value) {
        addNotEquals(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection movieTitleLike(String... value) {
        addLike(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection movieTitleContains(String... value) {
        addContains(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection movieTitleStartsWith(String... value) {
        addStartsWith(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection movieTitleEndsWith(String... value) {
        addEndsWith(TopratedColumns.MOVIE_TITLE, value);
        return this;
    }

    public TopratedSelection orderByMovieTitle(boolean desc) {
        orderBy(TopratedColumns.MOVIE_TITLE, desc);
        return this;
    }

    public TopratedSelection orderByMovieTitle() {
        orderBy(TopratedColumns.MOVIE_TITLE, false);
        return this;
    }

    public TopratedSelection movieReleaseDate(String... value) {
        addEquals(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection movieReleaseDateNot(String... value) {
        addNotEquals(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection movieReleaseDateLike(String... value) {
        addLike(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection movieReleaseDateContains(String... value) {
        addContains(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection movieReleaseDateStartsWith(String... value) {
        addStartsWith(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection movieReleaseDateEndsWith(String... value) {
        addEndsWith(TopratedColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public TopratedSelection orderByMovieReleaseDate(boolean desc) {
        orderBy(TopratedColumns.MOVIE_RELEASE_DATE, desc);
        return this;
    }

    public TopratedSelection orderByMovieReleaseDate() {
        orderBy(TopratedColumns.MOVIE_RELEASE_DATE, false);
        return this;
    }

    public TopratedSelection moviePopularity(String... value) {
        addEquals(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection moviePopularityNot(String... value) {
        addNotEquals(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection moviePopularityLike(String... value) {
        addLike(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection moviePopularityContains(String... value) {
        addContains(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection moviePopularityStartsWith(String... value) {
        addStartsWith(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection moviePopularityEndsWith(String... value) {
        addEndsWith(TopratedColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public TopratedSelection orderByMoviePopularity(boolean desc) {
        orderBy(TopratedColumns.MOVIE_POPULARITY, desc);
        return this;
    }

    public TopratedSelection orderByMoviePopularity() {
        orderBy(TopratedColumns.MOVIE_POPULARITY, false);
        return this;
    }

    public TopratedSelection movieDescription(String... value) {
        addEquals(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection movieDescriptionNot(String... value) {
        addNotEquals(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection movieDescriptionLike(String... value) {
        addLike(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection movieDescriptionContains(String... value) {
        addContains(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection movieDescriptionStartsWith(String... value) {
        addStartsWith(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection movieDescriptionEndsWith(String... value) {
        addEndsWith(TopratedColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public TopratedSelection orderByMovieDescription(boolean desc) {
        orderBy(TopratedColumns.MOVIE_DESCRIPTION, desc);
        return this;
    }

    public TopratedSelection orderByMovieDescription() {
        orderBy(TopratedColumns.MOVIE_DESCRIPTION, false);
        return this;
    }

    public TopratedSelection movieImageUrl(String... value) {
        addEquals(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection movieImageUrlNot(String... value) {
        addNotEquals(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection movieImageUrlLike(String... value) {
        addLike(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection movieImageUrlContains(String... value) {
        addContains(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection movieImageUrlStartsWith(String... value) {
        addStartsWith(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection movieImageUrlEndsWith(String... value) {
        addEndsWith(TopratedColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public TopratedSelection orderByMovieImageUrl(boolean desc) {
        orderBy(TopratedColumns.MOVIE_IMAGE_URL, desc);
        return this;
    }

    public TopratedSelection orderByMovieImageUrl() {
        orderBy(TopratedColumns.MOVIE_IMAGE_URL, false);
        return this;
    }
}
