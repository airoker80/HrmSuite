package com.harati.hrmsuite.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.hrmsuite.R;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;

/**
 * Created by Sameer on 6/27/2017.
 */

public class UserProfileFragment extends Fragment {
    ImageView logOut;
    UserSessionManager userSessionManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profie, container, false);
        userSessionManager= new UserSessionManager(getContext());
        logOut= (ImageView)view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSessionManager.logoutUser();
            }
        });
        return  view;
    }
}
