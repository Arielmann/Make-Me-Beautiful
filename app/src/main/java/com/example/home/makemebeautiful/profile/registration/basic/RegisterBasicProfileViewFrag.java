package com.example.home.makemebeautiful.profile.registration.basic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.profile.registration.GenericSettingsFrag;
import com.example.home.makemebeautiful.profile.registration.SetViewsAfterSignUpClicked;

import java.util.HashMap;

import static com.example.home.makemebeautiful.profile.registration.basic.RegisterBasicProfileModel.*;

/**
 * Created by home on 8/5/2016.
 */
public class RegisterBasicProfileViewFrag extends GenericSettingsFrag implements View.OnClickListener, SetViewsAfterSignUpClicked {
    private static final String TAG = RegisterBasicProfileViewFrag.class.getName();
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText locationEditText;
    private Button signUpButton;
//    private TextView loginLink;
//    private ProgressDialog progressDialog;

    @Override
    public void tryToUpdateData() {
        HashMap<String, String> userInputs = new HashMap<>();
        userInputs.put(NAME, nameEditText.getText().toString());
        userInputs.put(PASSWORD, passwordEditText.getText().toString());
        userInputs.put(LOCATION, locationEditText.getText().toString());
        getModel().validate(userInputs);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View signUpViewLayout = inflater.inflate(R.layout.frag_register_basic_profile, null);
        TextView headline = (TextView) signUpViewLayout.findViewById(R.id.headline);
        Typeface headlineFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quirlycues.ttf");
        headline.setTypeface(headlineFont);
        nameEditText = (EditText) signUpViewLayout.findViewById(R.id.input_name);
        passwordEditText = (EditText) signUpViewLayout.findViewById(R.id.input_password);
        locationEditText = (EditText) signUpViewLayout.findViewById(R.id.input_location);
        signUpButton = (Button) signUpViewLayout.findViewById(R.id.btn_signup);
        //loginLink = (TextView) signUpViewLayout.findViewById(R.id.link_login);
        setModel(new RegisterBasicProfileModel(this));
        signUpButton.setOnClickListener(this);
        return signUpViewLayout;
    }

    @Override
    public void onClick(View v) {
        signUpButton.setEnabled(false);
        tryToUpdateData();
        signUpButton.setEnabled(true);
    }

    //*************Interface's method is being called from model********//
    @Override
    public void setViewUponFailedDataSetting(HashMap<String, String> errors) {
        //set errors only if needed
        nameEditText.setError(errors.get("nameError"));
        passwordEditText.setError(errors.get("passwordError"));
        locationEditText.setError(errors.get("locationError"));
        signUpButton.setEnabled(true);
    }
}

