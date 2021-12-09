package com.daou.timesapp.di

import com.daou.timesapp.mapper.ArticleMapper
import com.daou.timesapp.mapper.ClipArticleMapper
import com.daou.timesapp.ui.clip.ClipViewModel
import com.daou.timesapp.ui.home.HomeViewModel
import com.daou.timesapp.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ClipViewModel(get(), get()) }

    factory { ClipArticleMapper() }
    factory { ArticleMapper() }
}