package com.example.bazaruno;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.google.android.gms.maps.model.LatLng;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class SignUp_Seller extends Fragment {

    View view;

    EditText email, name, phone, cnic, shop_name, shop_full_address, pass, again_pass;

    Spinner sp_city, sp_area, sp_bazzar;
    Button signup;

    VolleyService volleyService;
    private RotateLoading rotating;

    public SignUp_Seller() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.signup_seller, container, false);
        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        cnic = view.findViewById(R.id.cnic);
        shop_name = view.findViewById(R.id.shop_name);
        shop_full_address = view.findViewById(R.id.shop_full_address);
        sp_city = view.findViewById(R.id.sp_city);
        sp_area = view.findViewById(R.id.sp_area);
        sp_bazzar = view.findViewById(R.id.sp_bazzar);
        pass = view.findViewById(R.id.pass);
        again_pass = view.findViewById(R.id.again_pass);
        rotating = view.findViewById(R.id.newton_cradle_loading);
        signup = view.findViewById(R.id.signup);
        volleyService = new VolleyService(getActivity());
        Permissions.check(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,
                null, new PermissionHandler() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onGranted() {

                        getLocation();
                    }
                });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1
                if (email.getText().toString().length() < 5) {
                    Toast.makeText(getActivity(), "Enter Correct Email", Toast.LENGTH_SHORT).show();

                }
                //2
                else if (name.getText().toString().length() < 3) {
                    Toast.makeText(getActivity(), "Enter Correct Name", Toast.LENGTH_SHORT).show();
                }

                //3
                else if (phone.getText().toString().length() < 11) {
                    Toast.makeText(getActivity(), "Correct Your Mobile Number", Toast.LENGTH_SHORT).show();
                }

                //4 example 1730130667517
                else if (cnic.getText().toString().length() < 13) {
                    Toast.makeText(getActivity(), "Correct Your Cnic", Toast.LENGTH_SHORT).show();
                }

                //5
                else if (shop_name.getText().toString().length() < 3) {
                    Toast.makeText(getActivity(), "Correct Your Shop Name", Toast.LENGTH_SHORT).show();
                }

                //6
                else if (shop_full_address.getText().toString().length() < 10) {
                    Toast.makeText(getActivity(), "Enter Full Address", Toast.LENGTH_SHORT).show();
                }
                //7
                else if (sp_city.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
                }

                //8
                else if (sp_area.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please Select Area", Toast.LENGTH_SHORT).show();
                }


                //9
                else if (sp_bazzar.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please Select Bazzar", Toast.LENGTH_SHORT).show();
                }

                //10
                else if (pass.getText().toString().length() < 8) {
                    Toast.makeText(getActivity(), "Password Should be greater then 8", Toast.LENGTH_SHORT).show();

                } else if (!pass.getText().toString().matches(again_pass.getText().toString())) {
                    Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
                } else {
                    Users users = new Users();
                    users.setType("seller");
                    users.setEmail(email.getText().toString().trim());
                    users.setUsername(name.getText().toString().trim());
                    users.setMobile_no(phone.getText().toString().trim());
                    users.setCnic(cnic.getText().toString().trim());
                    users.setShop_name(shop_name.getText().toString().trim());
                    users.setShop_address(shop_full_address.getText().toString().trim());
                    LatLng latLng = getLocationFromAddress(getActivity(), shop_full_address.getText().toString());
                    users.setShop_lat_lang(latLng.latitude + "," + latLng.longitude);
                    users.setCity(sp_city.getSelectedItem().toString());
                    users.setCity_area(sp_area.getSelectedItem().toString());
                    users.setBazzar(sp_bazzar.getSelectedItem().toString());
                    users.setPassword(pass.getText().toString().trim());
                    users.setStatus("0");
                    registerData(users);
                }
            }
        });


        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "here" + " " + i + " " + sp_city.getSelectedItem(), Toast.LENGTH_SHORT).show();

                if (i == 1) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_item, AppConstant.peshawarAreas);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(adapter);

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_item, AppConstant.peshawarBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_bazzar.setAdapter(adapter2);
                }
                if (i == 2) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_item, AppConstant.kohatArea);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(adapter);

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_item, AppConstant.kohatBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_bazzar.setAdapter(adapter2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void registerData(Users users) {
        rotating.start();
        volleyService.RegisterUser(AppConstant.DomainName + AppConstant.Dir + AppConstant.registerUser
                , users, new VolleyService.VolleyResponseListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG + " SignUp Success", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if (success) {

                                MySharePreferences mySharePreferences = new MySharePreferences();
                                Users sellerUsers1 = new Users();
                                sellerUsers1.setEmail(jsonObject.getString("email"));
                                sellerUsers1.setUsername(jsonObject.getString("username"));
                                sellerUsers1.setMobile_no(jsonObject.getString("mobile_no"));
                                sellerUsers1.setCnic(jsonObject.getString("cnic"));
                                sellerUsers1.setShop_name(jsonObject.getString("shop_name"));
                                sellerUsers1.setShop_address(jsonObject.getString("shop_address"));
                                sellerUsers1.setShop_lat_lang(jsonObject.getString("shop_lat_lang"));
                                sellerUsers1.setCity(jsonObject.getString("city"));
                                sellerUsers1.setCity_area(jsonObject.getString("city_area"));
                                sellerUsers1.setBazzar(jsonObject.getString("bazzar"));
                                mySharePreferences.SaveUserAds(getActivity(), sellerUsers1);
                                mySharePreferences.setLoginStatus(getActivity(), true);
                                Toast.makeText(getActivity(), "Login Successfully. . ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));


                            } else {
                                Toast.makeText(getActivity(), "User Already Exist", Toast.LENGTH_SHORT).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(AppConstant.TAG + " SignUp onError JSONException", e.getMessage());
                            rotating.stop();
                        }

                        rotating.stop();
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onError(VolleyError error) {
                        Log.d(AppConstant.TAG + " SignUp onError VolleyError", error.getMessage());
                        rotating.stop();
                    }
                });


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null)
        {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
        Log.d(AppConstant.TAG+" :location",myLocation.getLatitude()+","+myLocation.getLongitude());
    }
}
