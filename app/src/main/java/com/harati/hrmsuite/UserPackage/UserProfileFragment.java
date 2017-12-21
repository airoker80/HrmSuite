package com.harati.hrmsuite.UserPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.harati.hrmsuite.ForgotPasswordPackage.ChangePasswordActivity;
import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;
import com.harati.hrmsuite.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/27/2017.
 */

public class UserProfileFragment extends Fragment {
    ImageView setting, refresh;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    TextView gender, dob, phone, permanent, temporary, temporaryAddType, citizen, driving, name, companyName;
    CircleImageView circleImageView;
    List<UserModel.UserData> userDataList = new ArrayList<UserModel.UserData>();
    private RadioGroup radioLanguage;
    DateConverter dateConverter;

    public UserProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profie, container, false);
        apiInterface = RetrofitClient.getApiService();
        dateConverter = new DateConverter();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        setting = (ImageView) view.findViewById(R.id.setting);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        gender = (TextView) view.findViewById(R.id.gender);
        dob = (TextView) view.findViewById(R.id.dob);
        phone = (TextView) view.findViewById(R.id.phone);
        permanent = (TextView) view.findViewById(R.id.permanent);
        temporary = (TextView) view.findViewById(R.id.temporary);
        temporaryAddType = (TextView) view.findViewById(R.id.temporaryAddType);
        citizen = (TextView) view.findViewById(R.id.citizen);
        driving = (TextView) view.findViewById(R.id.driving);
        name = (TextView) view.findViewById(R.id.name);
        companyName = (TextView) view.findViewById(R.id.companyName);
        circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        getUserDetails();

        setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setting.setBackgroundColor(Color.GRAY);
                        break;
                    }
                    case MotionEvent.ACTION_UP:

                        // Your action here on button click
                    {
                        setting.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        showPopMenu(setting);
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        setting.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    }
                }
                return true;
            }
        });


        refresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        refresh.setBackgroundColor(Color.GRAY);
                        break;
                    }
                    case MotionEvent.ACTION_UP:

                        // Your action here on button click
                    {
                        refresh.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        getUserDetails();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        refresh.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    }
                }
                return true;
            }
        });
        return view;
    }

    public void getUserDetails() {

        if (NetworkManager.isConnected(getContext())) {
            Call<UserModel> call = apiInterface.getUserDetails(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken());
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteUserDetails();
                            for (UserModel.UserData userData : response.body().getData()) {
                                databaseHandler.addUserDetails(userData);
                            }
                            prepareData();
                        } else {
                            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            prepareData();
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    prepareData();
                }


            });
        } else {
//            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            prepareData();
        }


    }

    public void prepareData() {
        userDataList.clear();
        userDataList = databaseHandler.getUserDetails();
        if (userDataList.size() > 0) for (int i = 0; i < userDataList.size(); i++) {
            UserModel.UserData userData = userDataList.get(i);
            gender.setText(userData.getGender());
            Date birthday = null;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthday = new SimpleDateFormat("yyyy-MM-dd").parse(userData.getBirthday());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String birthdayDetail = dateFormat.format(birthday);
            String[] birthdayNep = birthdayDetail.split("-");

            if (userSessionManager.getKeyLanguage().equals("English")) {
                dob.setText(dateFormat.format(birthday));
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                Model nepDate = dateConverter.getNepaliDate(Integer.parseInt(birthdayNep[0]), Integer.parseInt(birthdayNep[1]), Integer.parseInt(birthdayNep[2]));
                String year = String.valueOf(nepDate.getYear());
                String month;
                if (nepDate.getMonth() < 10)
                    month = "0" + (nepDate.getMonth() + 1);
                else
                    month = String.valueOf(nepDate.getMonth() + 1);
                String day;
                if (nepDate.getDay() < 10)
                    day = "0" + (nepDate.getDay());
                else
                    day = String.valueOf(nepDate.getDay());

                dob.setText(year + "-" + month + "-" + day);
            }
            phone.setText(userData.getPhone());
            permanent.setText(userData.getPermanentAdd());
            temporary.setText(userData.getTemperoryAdd());
            temporaryAddType.setText(userData.getTemporaryAddType());
            citizen.setText(userData.getCitizenNumber());
            driving.setText(userData.getDrivingLicenceNo());

            name.setText(userData.getFirstname() + " " + userData.getLastname());
            companyName.setText(userData.getCompanyName());
            try {
                Picasso.with(getContext())
                        .load(userData.getPhotoUrl())
                        .resize(200, 200)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_default)
                        .error(R.mipmap.ic_default)
                        .into(circleImageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void showPopMenu(ImageView clView) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), clView);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.setting_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.languageSetting:
                        updateLanguage();
                        break;
                    case R.id.changePassword:
                        getActivity().startActivity(new Intent(getContext(), ChangePasswordActivity.class));
                        break;
                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    public void logout() {
        databaseHandler.deleteALL();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        userSessionManager.logoutUser();
    }

    public void updateLanguage() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setPositiveButton("SUBMIT", null)
                .setNegativeButton("CANCEL", null)
                .create();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View dialogView = inflater.inflate(R.layout.setting_language, null);
        RadioButton english = (RadioButton) dialogView.findViewById(R.id.english);
        RadioButton nepali = (RadioButton) dialogView.findViewById(R.id.nepali);
        radioLanguage = (RadioGroup) dialogView.findViewById(R.id.radioLanguage);
        if (!userSessionManager.getKeyLanguage().equals("")) {
            if (userSessionManager.getKeyLanguage().equals("English")) {
                english.setChecked(true);
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                nepali.setChecked(true);
            }

        }
        alertDialog.setView(dialogView);
        alertDialog.setTitle("Change Date");
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioLanguage.getCheckedRadioButtonId();
                        RadioButton selectedRadio = (RadioButton) dialogView.findViewById(selectedId);
                        userSessionManager.setKeyLanguage(selectedRadio.getText().toString());
                        Toast.makeText(getActivity(), "Date Changed!", Toast.LENGTH_SHORT).show();
                        prepareData();
                        alertDialog.dismiss();
                    }
                });

                final Button btnDecline = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#5bb3dd"));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#5bb3dd"));
    }


}

