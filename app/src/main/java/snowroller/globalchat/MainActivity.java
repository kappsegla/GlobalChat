package snowroller.globalchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import snowroller.globalchat.adapter.MessageFirestoreRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText text;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        text = findViewById(R.id.edittext_chatbox);
        recyclerView = findViewById(R.id.reyclerview_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getMessages();

    }

    private void getMessages() {

        //Create query to get latest 50 messages
        Query query = db.collection("messages")
                      .orderBy("timestamp", Query.Direction.DESCENDING)
                      .limit(50);

        FirestoreRecyclerOptions<Message> options =
                new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

             adapter = new MessageFirestoreRecyclerAdapter(Glide.with(this),options);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void logoutClicked(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this.getApplicationContext(), "User logged out",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void sendMessage(View v){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            Message message = new Message();
            message.setText(text.getText().toString());
            message.setUid(uid);
            message.setUserName(name);
            message.setPhotoUrl(photoUrl.toString());
            db.collection("messages").add(message);
        }
    }

    public void chatAttachment(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Martin")
                .setPhotoUri(Uri.parse("https://lh3.googleusercontent.com/-PVA-pg8idWE/Tg12W-NJv5I/AAAAAAAABdY/0XP9s4ukkqsSiVkg34llHM4sK-xqS7UrACEwYBhgL/w140-h139-p/cimg0057-medium.jpg"))
                .build();

        user.updateProfile(profileUpdates);
    }
}
