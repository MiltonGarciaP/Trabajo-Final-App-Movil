package com.milton.myapplication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends android.app.Application {

@Override
    public void onCreate()
{
    super.onCreate();
    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
}

}
