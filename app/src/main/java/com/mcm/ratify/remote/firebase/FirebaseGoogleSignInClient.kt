package com.mcm.ratify.remote.firebase

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mcm.ratify.util.DEFAULT_WEB_CLIENT_ID

class FirebaseGoogleSignInClient(
    private val context: Context
) {
    private val gso = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(DEFAULT_WEB_CLIENT_ID)
        .build()

    fun createClient(): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }
}