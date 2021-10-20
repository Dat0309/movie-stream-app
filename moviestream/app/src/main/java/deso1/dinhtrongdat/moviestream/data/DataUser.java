package deso1.dinhtrongdat.moviestream.data;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import deso1.dinhtrongdat.moviestream.LoginScreen;
import deso1.dinhtrongdat.moviestream.MainActivity;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;
import deso1.dinhtrongdat.moviestream.model.User;

public class DataUser implements Serializable {

    static String USER, PASS, IMG, NAME;
    static List<CategoryItem> favorites;
    static List<User> listUser;

    private static DataUser Instance;

    private DataUser() {
    }

    public static DataUser getInstance(){
        if(Instance == null){
            Instance = new DataUser();
        }
        return Instance;
    }

    public List<User> GetDataUser(){
        listUser = new ArrayList<>();
        favorites = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
                for (DataSnapshot data : nodeChild) {
                    Map<String, Object> map = (Map<String, Object>) data.getValue();
                    USER = map.get("username").toString();
                    PASS = map.get("password").toString();
                    IMG = map.get("avatar").toString();
                    NAME = map.get("name").toString();

                    CategoryItem item = data.child("favorite").getValue(CategoryItem.class);
                    favorites.add(item);

                    User user = new User(USER, PASS, NAME, favorites,IMG);
                    listUser.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return listUser;
    }
}
