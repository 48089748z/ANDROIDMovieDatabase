package practica1.com.peliculas.provider.toprated;

import practica1.com.peliculas.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Table of Top Rated Movies
 */
public interface TopratedModel extends BaseModel {

    /**
     * Get the {@code movie_title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieTitle();

    /**
     * Get the {@code movie_release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieReleaseDate();

    /**
     * Get the {@code movie_popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMoviePopularity();

    /**
     * Get the {@code movie_description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieDescription();

    /**
     * Get the {@code movie_image_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieImageUrl();
}
