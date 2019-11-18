package com.implizstudio.android.app.favoritemovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.favoritemovies.model.constant.Constant
import com.implizstudio.android.app.favoritemovies.R
import com.implizstudio.android.app.favoritemovies.adapter.MovieAdapter
import com.implizstudio.android.app.favoritemovies.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val listFavoriteMovie = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(t_main)

        GlobalScope.launch {

            val cursor = contentResolver?.query(Constant.CONTENT_URI, null, null, null, null)
            if (cursor != null) {

                listFavoriteMovie.clear()

                while (cursor.moveToNext()) {
                    val poster = cursor.let { it.getString(it.getColumnIndexOrThrow("poster")) }
                    val title = cursor.let { it.getString(it.getColumnIndexOrThrow("title")) }

                    listFavoriteMovie.add(Movie(poster, title))
                }

                runOnUiThread {

                    rv_movie.layoutManager = StaggeredGridLayoutManager(Constant.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                    rv_movie.adapter = MovieAdapter(this@MainActivity, listFavoriteMovie)

                }

                cursor.close()
            }

        }

    }

}
