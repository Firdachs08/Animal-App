package com.example.FirdaJetpack.di

import com.example.FirdaJetpack.data.RepositoryAnimals

object Injection {
    fun provideRepository(): RepositoryAnimals {
        return RepositoryAnimals.getInstance()
    }
}