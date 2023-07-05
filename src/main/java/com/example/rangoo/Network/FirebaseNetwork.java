package com.example.rangoo.Network;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rangoo.Interfaces.AuthCallback;
import com.example.rangoo.Interfaces.GetDataCallback;
import com.example.rangoo.Interfaces.GetListCallback;
import com.example.rangoo.Interfaces.SaveDataCallback;
import com.example.rangoo.Interfaces.UriImageCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.Model.LoginData;
import com.example.rangoo.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FirebaseNetwork {

    private static FirebaseAuth auth;
    private static DatabaseReference database;
    private static StorageReference storage;

    /***
     * Inicializa a instância do FirebaseAuth.
     */
    private static void initAuth() {
        auth = FirebaseAuth.getInstance();
    }

    /***
     * Inicializa a instância do DatabaseReference com o caminho especificado.
     *
     * @param data O caminho para o nó no banco de dados do Firebase.
     */
    private static void initDatabase(String data) {
        database = FirebaseDatabase.getInstance().getReference().child(data);
    }

    private static void initStorage(String caminho){
        storage = FirebaseStorage.getInstance().getReference().child(caminho);
    }

    /***
     * Registra um novo usuário no Firebase Authentication e salva os dados do usuário no banco de dados.
     *
     * @param user     O objeto User contendo os dados do usuário.
     * @param callback O AuthCallback para retornar o resultado da operação.
     */
    public static void signUpUser(User user, AuthCallback callback) {
        if (user != null) {
            initAuth();

            auth.createUserWithEmailAndPassword(user.getLoginData().getEmail(), user.getLoginData().getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveDataUser(auth.getCurrentUser().getUid(), user, callback);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(e.getLocalizedMessage());
                        }
                    });
        }
    }

    /***
     * Salva os dados do usuário no banco de dados do Firebase.
     *
     * @param UID      O UID do usuário atualmente autenticado.
     * @param user     O objeto User contendo os dados do usuário.
     * @param callback O AuthCallback para retornar o resultado da operação.
     */
    public static void saveDataUser(String UID, User user, AuthCallback callback) {
        initDatabase("users");
        if (database != null) {
            database.child(UID).setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callback.onSuccess(UID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(e.getLocalizedMessage());
                        }
                    });
        }
    }

    public static void saveListUser(String UID, ArrayList<String> list, SaveDataCallback callback){
        initDatabase("usersList");
        if (database != null) {
            database.child(UID).setValue(list)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callback.onSuccess(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(e.getLocalizedMessage());
                        }
                    });
        }
    }

    /***
     * Realiza o login do usuário usando o Firebase Authentication.
     *
     * @param loginData O objeto LoginData contendo as credenciais de login do usuário.
     * @param callback  O AuthCallback para retornar o resultado da operação.
     */
    public static void signIn(LoginData loginData, AuthCallback callback) {
        initAuth();
        auth.signInWithEmailAndPassword(loginData.getEmail(), loginData.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(auth.getCurrentUser().getUid());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getLocalizedMessage());
                    }
                });
    }

    public static void getDataUser(String UID, GetDataCallback callback) {
        initDatabase("users");
        if(database != null){
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    callback.onSuccess(snapshot.child(UID));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.getMessage());
                }
            });
        }
    }

    public static void getWeekMenu(GetListCallback callback){
        initDatabase("menu");
        if(database != null){
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Food> foodsList = new ArrayList<>();

                    for(DataSnapshot item: snapshot.getChildren()){
                        foodsList.add(new Food(
                                item.getKey(),
                                item.child("name").getValue(String.class),
                                item.child("resumo").getValue(String.class),
                                item.child("description").getValue(String.class),
                                item.child("imagem").getValue(String.class)
                        ));
                    }
                    callback.onSuccess(foodsList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.getMessage());
                }
            });
        }
    }

    public static void getUserList(String UID, GetListCallback callback){
        initDatabase("usersList");
        if(database != null){
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(UID).exists()){
                        ArrayList<Food> list = new ArrayList<>();
                        for (DataSnapshot item : snapshot.child(UID).getChildren()) {
                            getItemId(item.getValue(String.class), new GetItemCallback() {
                                @Override
                                public void onSuccess(Food item) {
                                    list.add(item);
                                    Log.d("FOOD", list.get(list.size() - 1).toString());

                                    if (list.size() == 5) callback.onSuccess(list);
                                }

                                @Override
                                public void onError(String error) {
                                    if (list.size() == 5) callback.onSuccess(list);
                                }
                            });
                        }
                    } else {
                        callback.onError("Usuario não possui lista");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.getMessage());
                }
            });
        }
    }

    public static void getUriImageUser(String UID, UriImageCallback callback){
        initStorage("imageUsers/"+ UID + ".jpg");
        if(storage != null){
            storage.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            callback.onSuccess(uri);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError(e.getLocalizedMessage());
                        }
                    });
        }
    }

    public static void uploadImageUser(Uri image, String UID, SaveDataCallback callback){
        initStorage("imageUsers/"+ UID + ".jpg");
        if (storage != null){
            UploadTask task = storage.putFile(image);
            task.addOnSuccessListener(taskSnapshot -> {
                callback.onSuccess(true);
            }).addOnFailureListener(e -> {
                callback.onError(e.getLocalizedMessage());
            });
        }
    }

    private static void getItemId(String id, GetItemCallback callback){
        initDatabase("menu/" + id);
        if(database != null){
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot item) {
                        callback.onSuccess(new Food(
                                item.getKey(),
                                item.child("name").getValue(String.class),
                                item.child("resumo").getValue(String.class),
                                item.child("description").getValue(String.class),
                                item.child("imagem").getValue(String.class)
                        ));
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.getMessage());
                }
            });
        }
    }

    /***
     * Realiza o logout do usuário.
     */
    public static void signOut() {
        initAuth();
        auth.signOut();
    }

    private interface GetItemCallback{
        public void onSuccess(Food item);
        public void onError(String error);
    }
}
