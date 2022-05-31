package com.mcm.ratify.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.mcm.ratify.remote.retrofit.clients.DeezerClient
import com.mcm.ratify.remote.retrofit.services.DeezerService
import com.mcm.ratify.data.repository.RatifyRepositoryImpl
import com.mcm.ratify.domain.repository.RatifyRepository
import com.mcm.ratify.remote.firebase.FirebaseGoogleSignInClient
import com.mcm.ratify.util.DEEZER_BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRatifyRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        deezerService: DeezerService
    ): RatifyRepository =
        RatifyRepositoryImpl(firebaseAuth, firebaseFirestore, deezerService)

    @Singleton
    @Provides
    fun provideDeezerService(): DeezerService =
        DeezerClient(DEEZER_BASE_URL).createService() as DeezerService

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext
        context: Context
    ): GoogleSignInClient = FirebaseGoogleSignInClient(context).createClient()
}