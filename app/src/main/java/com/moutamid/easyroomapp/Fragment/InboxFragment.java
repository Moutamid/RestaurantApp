package com.moutamid.easyroomapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moutamid.easyroomapp.Activity.ChatActivity;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.landlord.model.LastMessageItem;
import com.squareup.picasso.Picasso;

public class InboxFragment extends Fragment {

    private DatabaseReference usersRef;
    private RecyclerView inboxRecyclerView;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inbox_fragment, container, false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child("Users");
        inboxRecyclerView = view.findViewById(R.id.inbox_recycler_view);
        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Rooms")
                .child("lastMessage")
                .child(currentUserId)
                .orderByChild("timestamp")
                .limitToLast(50);

        FirebaseRecyclerOptions<LastMessageItem> options =
                new FirebaseRecyclerOptions.Builder<LastMessageItem>()
                        .setQuery(query, LastMessageItem.class)
                        .build();

        FirebaseRecyclerAdapter<LastMessageItem, InboxViewHolder> adapter =
                new FirebaseRecyclerAdapter<LastMessageItem, InboxViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull InboxViewHolder holder, int position, @NonNull LastMessageItem model) {
                        final String userId = getRef(position).getKey();

                        usersRef.child(userId).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                if (dataSnapshot.exists()) {
                                    UserModel user = dataSnapshot.getValue(UserModel.class);
                                    if (user != null) {
                                        holder.bind(user, model, getContext());
                                    }
                                }
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false);
                        return new InboxViewHolder(view);
                    }
                };

        inboxRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    // Rest of the code...

    // ViewHolder class
    public static class InboxViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView lastMessageTextView;
        ImageView profile;
        CardView main_lyt;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.inbox_item_name);
            lastMessageTextView = itemView.findViewById(R.id.inbox_item_last_message);
            profile = itemView.findViewById(R.id.profile);
            main_lyt = itemView.findViewById(R.id.main_lyt);
        }

        public void bind(UserModel user, LastMessageItem lastMessageItem, Context context) {
            nameTextView.setText(user.getName());
            if (user.image_url != null) {
                Picasso.get().load(user.image_url).into(profile);
            }
            else
            {
                Picasso.get().load(R.drawable.logo).into(profile);

            }
            lastMessageTextView.setText(lastMessageItem.getLastMessage());
            main_lyt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("selected_user_id", user.id);
                    intent.putExtra("name", user.getName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
