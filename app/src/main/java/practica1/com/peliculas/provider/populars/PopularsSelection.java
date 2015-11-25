package practica1.com.peliculas.provider.populars;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import practica1.com.peliculas.provider.base.AbstractSelection;

/**
 * Selection for the {@code populars} table.
 */
public class PopularsSelection extends AbstractSelection<PopularsSelection> {
    @Override
    protected Uri baseUri() {
        return PopularsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularsCursor} object, which is positioned before the first entry, or null.
     */
    public PopularsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PopularsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PopularsCursor} object, which is positioned before the first entry, or null.
     */
    public PopularsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PopularsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PopularsCursor query(Context context) {
        return query(context, null);
    }


    public PopularsSelection id(long... value) {
        addEquals("populars." + PopularsColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularsSelection idNot(long... value) {
        addNotEquals("populars." + PopularsColumns._ID, toObjectArray(value));
        return this;
    }

    public PopularsSelection orderById(boolean desc) {
        orderBy("populars." + PopularsColumns._ID, desc);
        return this;
    }

    public PopularsSelection orderById() {
        return orderById(false);
    }

    public PopularsSelection movieTitle(String... value) {
        addEquals(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection movieTitleNot(String... value) {
        addNotEquals(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection movieTitleLike(String... value) {
        addLike(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection movieTitleContains(String... value) {
        addContains(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection movieTitleStartsWith(String... value) {
        addStartsWith(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection movieTitleEndsWith(String... value) {
        addEndsWith(PopularsColumns.MOVIE_TITLE, value);
        return this;
    }

    public PopularsSelection orderByMovieTitle(boolean desc) {
        orderBy(PopularsColumns.MOVIE_TITLE, desc);
        return this;
    }

    public PopularsSelection orderByMovieTitle() {
        orderBy(PopularsColumns.MOVIE_TITLE, false);
        return this;
    }

    public PopularsSelection movieReleaseDate(String... value) {
        addEquals(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection movieReleaseDateNot(String... value) {
        addNotEquals(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection movieReleaseDateLike(String... value) {
        addLike(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection movieReleaseDateContains(String... value) {
        addContains(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection movieReleaseDateStartsWith(String... value) {
        addStartsWith(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection movieReleaseDateEndsWith(String... value) {
        addEndsWith(PopularsColumns.MOVIE_RELEASE_DATE, value);
        return this;
    }

    public PopularsSelection orderByMovieReleaseDate(boolean desc) {
        orderBy(PopularsColumns.MOVIE_RELEASE_DATE, desc);
        return this;
    }

    public PopularsSelection orderByMovieReleaseDate() {
        orderBy(PopularsColumns.MOVIE_RELEASE_DATE, false);
        return this;
    }

    public PopularsSelection moviePopularity(String... value) {
        addEquals(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection moviePopularityNot(String... value) {
        addNotEquals(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection moviePopularityLike(String... value) {
        addLike(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection moviePopularityContains(String... value) {
        addContains(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection moviePopularityStartsWith(String... value) {
        addStartsWith(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection moviePopularityEndsWith(String... value) {
        addEndsWith(PopularsColumns.MOVIE_POPULARITY, value);
        return this;
    }

    public PopularsSelection orderByMoviePopularity(boolean desc) {
        orderBy(PopularsColumns.MOVIE_POPULARITY, desc);
        return this;
    }

    public PopularsSelection orderByMoviePopularity() {
        orderBy(PopularsColumns.MOVIE_POPULARITY, false);
        return this;
    }

    public PopularsSelection movieDescription(String... value) {
        addEquals(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection movieDescriptionNot(String... value) {
        addNotEquals(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection movieDescriptionLike(String... value) {
        addLike(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection movieDescriptionContains(String... value) {
        addContains(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection movieDescriptionStartsWith(String... value) {
        addStartsWith(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection movieDescriptionEndsWith(String... value) {
        addEndsWith(PopularsColumns.MOVIE_DESCRIPTION, value);
        return this;
    }

    public PopularsSelection orderByMovieDescription(boolean desc) {
        orderBy(PopularsColumns.MOVIE_DESCRIPTION, desc);
        return this;
    }

    public PopularsSelection orderByMovieDescription() {
        orderBy(PopularsColumns.MOVIE_DESCRIPTION, false);
        return this;
    }

    public PopularsSelection movieImageUrl(String... value) {
        addEquals(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection movieImageUrlNot(String... value) {
        addNotEquals(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection movieImageUrlLike(String... value) {
        addLike(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection movieImageUrlContains(String... value) {
        addContains(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection movieImageUrlStartsWith(String... value) {
        addStartsWith(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection movieImageUrlEndsWith(String... value) {
        addEndsWith(PopularsColumns.MOVIE_IMAGE_URL, value);
        return this;
    }

    public PopularsSelection orderByMovieImageUrl(boolean desc) {
        orderBy(PopularsColumns.MOVIE_IMAGE_URL, desc);
        return this;
    }

    public PopularsSelection orderByMovieImageUrl() {
        orderBy(PopularsColumns.MOVIE_IMAGE_URL, false);
        return this;
    }
}
