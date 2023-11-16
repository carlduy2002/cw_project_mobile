package com.example.cw_project_mobile.Hike;

//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw_project_mobile.Object.Users;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private EditText hikeName, hikeLocation, hikeLength, hikeDescription;
    private Button btnConfirm;
    private ImageButton btnUpload;
    private RadioGroup radioGroupParking, radioGroupLevel;
    private RadioButton radioButtonParking, radioButtonLevel;
    private ImageView hikeImage;
    String name, location, parking, length, level, description;
    private String getUri = "";
    private static final String SDF_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private String user_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        hikeName = view.findViewById(R.id.hike_name);
        hikeLocation = view.findViewById(R.id.hike_location);
        hikeLength = view.findViewById(R.id.hike_length);
        hikeDescription = view.findViewById(R.id.hike_description);
        btnConfirm = view.findViewById(R.id.btn_Confirm);
        btnUpload = view.findViewById(R.id.btnUpload);
        hikeImage = view.findViewById(R.id.hikeImage);
        radioGroupParking = view.findViewById(R.id.hike_parking_available);
        radioGroupLevel = view.findViewById(R.id.hike_level);

        radioButtonParking = view.findViewById(R.id.parkingYes);
        radioButtonParking.setChecked(true);

        radioButtonLevel = view.findViewById(R.id.low);
        radioButtonLevel.setChecked(true);

        Bundle bundle = getArguments();
        if(bundle != null){
            user_id = bundle.getString("user_id");
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddFragment.this)
                        .crop()                                 //Crop image
                        .compress(1024)			        //Final image size will be less than 1 MB
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080
                        .start();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInfor()){
                    showPopupAdd();
                }
            }
        });

        return view;
    }

    public void Toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        getUri = uri.toString();
        hikeImage.setImageURI(Uri.parse(getUri));
    }

    public boolean validateInfor(){

        int radioIdParking = radioGroupParking.getCheckedRadioButtonId();
        radioButtonParking = getView().findViewById(radioIdParking);

        int radioIdLevel = radioGroupLevel.getCheckedRadioButtonId();
        radioButtonLevel = getView().findViewById(radioIdLevel);

        name = hikeName.getText().toString();
        location = hikeLocation.getText().toString();
        parking = radioButtonParking.getText().toString();
        length = hikeLength.getText().toString();
        level = radioButtonLevel.getText().toString();
        description = hikeDescription.getText().toString();

        if(name.matches("")){
            Toast("Please enter hike name!!");
            return false;
        }
        else if(location.matches("")){
            Toast("Please enter hike location!!");
            return false;
        }
        else if(parking.matches("")){
            Toast("Please choose hike parking state!!");
            return false;
        }
        else if(length.matches("")){
            Toast("Please enter hike length!!");
            return false;
        }
        else if(level.matches("")){
            Toast("Please choose hike level state!!");
            return false;
        }
        else {
            return true;
        }
    }



    public void showPopupAdd(){
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.add_hike_popup);

        ImageView popupImg = dialog.findViewById(R.id.imgViewPopup);

        TextView popupName = dialog.findViewById(R.id.txtName);
        TextView popupLocation = dialog.findViewById(R.id.txtLocation);
        TextView popupLength = dialog.findViewById(R.id.txtLength);
        TextView popupDescription = dialog.findViewById(R.id.txtDescription);

        RadioButton parkingYes = dialog.findViewById(R.id.parkingYesPopup);
        RadioButton parkingNo = dialog.findViewById(R.id.parkingNoPopup);
        RadioButton levelLow = dialog.findViewById(R.id.lowPopup);
        RadioButton levelMedium = dialog.findViewById(R.id.mediumPopup);
        RadioButton levelDifficult = dialog.findViewById(R.id.difficultPopup);

        parkingYes.setEnabled(false);
        parkingNo.setEnabled(false);
        levelLow.setEnabled(false);
        levelMedium.setEnabled(false);
        levelDifficult.setEnabled(false);

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);

        popupImg.setImageURI(Uri.parse(getUri));
        popupName.setText(name);
        popupLocation.setText(location);
        popupLength.setText(length);
        popupDescription.setText(description);

        if(getUri.matches("")){
            popupImg.setImageResource(R.drawable.head);
        }
        else{
            popupImg.setImageURI(Uri.parse(getUri));
        }

        if(parking.toLowerCase().matches("yes")){
            parkingYes.setChecked(true);
        }
        else {
            parkingNo.setChecked(true);
        }

        if(level.toLowerCase().matches("low")){
            levelLow.setChecked(true);
        }
        else if(level.toLowerCase().matches("medium")){
            levelMedium.setChecked(true);
        }
        else{
            levelDifficult.setChecked(true);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();

                //get current max id of hike
                int currentMaxID = sql.selectHikeMaxID();

                sql.insertHike(popupName.getText().toString(), popupLocation.getText().toString(), parking,
                        popupLength.getText().toString(), level, popupDescription.getText().toString(), getUri, Integer.parseInt(user_id));

                int newMaxID = sql.selectHikeMaxID();
                //check and notify
                if(currentMaxID < newMaxID){
                    Toast("Congratulation, You added hike succeed!!");

                    dialog.dismiss();
                }
                else{
                    Toast("Add Hike Failed!!");
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}