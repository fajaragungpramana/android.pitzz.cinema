package com.implizstudio.android.app.pitzz.di.module

import com.implizstudio.android.app.pitzz.data.remote.ApiService
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepositoryImpl
import com.implizstudio.android.app.pitzz.ui.bnv.movie.MovieViewModel
import com.implizstudio.android.app.pitzz.ui.bnv.television.TelevisionViewModel
import com.implizstudio.android.app.pitzz.ui.detail.movie.DetailMovieViewModel
import com.implizstudio.android.app.pitzz.ui.detail.television.DetailTelevisionViewModel
import com.implizstudio.android.app.pitzz.ui.search.movie.SearchMovieViewModel
import com.implizstudio.android.app.pitzz.ui.search.television.SearchTelevisionViewModel
import com.implizstudio.android.app.pitzz.ui.tab.movie.FavoriteMovieViewModel
import com.implizstudio.android.app.pitzz.ui.tab.television.FavoriteTelevisionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { ApiService.createTMDb() }
    single<TMDbRepository> { TMDbRepositoryImpl(get()) }

    viewModel { TelevisionViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { DetailMovieViewModel(get(), get()) }
    viewModel { DetailTelevisionViewModel(get(), get()) }
    viewModel { SearchMovieViewModel(get()) }
    viewModel { SearchTelevisionViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTelevisionViewModel(get()) }

}