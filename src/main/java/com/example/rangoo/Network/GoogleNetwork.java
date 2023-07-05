package com.example.rangoo.Network;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.example.rangoo.Interfaces.AuthCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * Classe para lidar com operações de autenticação usando a conta do Google.
 */
public class GoogleNetwork {

    private AuthCallback callback;
    private GoogleSignInClient googleSignInClient;

    /***
     * Inicializa a instância do GoogleSignInClient com as opções de login do Google especificadas.
     *
     * @param activity A activity atual.
     */
    private void init(Activity activity) {
        if (googleSignInClient == null) {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
        }
    }

    /***
     * Realiza o login usando a conta do Google.
     *
     * @param activity      A activity atual.
     * @param launcher      O ActivityResultLauncher para obter o resultado da atividade de login.
     * @param signInCallback O AuthCallback para retornar o resultado da operação de login.
     */
    public void signIn(Activity activity, ActivityResultLauncher<Intent> launcher, AuthCallback signInCallback) {
        init(activity);
        setSignInCallback(signInCallback);
        Intent intent = googleSignInClient.getSignInIntent();
        launcher.launch(intent);
    }

    /***
     * Realiza o logout da conta do Google.
     *
     * @param activity A activity atual.
     */
    public void signOut(Activity activity) {
        init(activity);
        googleSignInClient.signOut();
    }

    /***
     * Manipula o resultado da atividade de login do Google.
     *
     * @param intent O intent contendo o resultado da atividade de login do Google.
     */
    public void handleResultSignIn(Intent intent) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
        try {
            String UID = task.getResult(ApiException.class).getId();
            if (callback != null) {
                callback.onSuccess(UID);
            }
        } catch (ApiException ex) {
            if (callback != null) {
                callback.onError(ex.getMessage());
            }
        }
    }

    public void setSignInCallback(AuthCallback callback) {
        this.callback = callback;
    }
}
