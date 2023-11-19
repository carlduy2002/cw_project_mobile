package com.example.cw_project_mobile.Hike;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.cw_project_mobile.Observation.AddObservationFragment;
import com.example.cw_project_mobile.FragmentTab.HomeFragment;
import com.example.cw_project_mobile.FragmentTab.ListFragment;
import com.example.cw_project_mobile.Object.Hikes;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HikeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HikeDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HikeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HikeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HikeDetailFragment newInstance(String param1, String param2) {
        HikeDetailFragment fragment = new HikeDetailFragment();
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

    private ImageButton btnBack, btnDelete, btnEdit, btnViewObser, btnShare;
    private TextView txtBack, txtName, txtLocation, txtLength, txtDescription;
    private RadioButton parkingYes, parkingNo, levelLow, levelMedium, levelDifficult;
    private ImageView img;
    private String getUriEdit = "";
    String parking = "";
    String level = "";

    private int hike_id = 0;
    private int state = 0;
    private int user_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hike_detail, container, false);

        img = view.findViewById(R.id.hikeImageDetail);
        btnBack = view.findViewById(R.id.btnBack);
        txtBack = view.findViewById(R.id.txtBack);
        txtName = view.findViewById(R.id.txtNameDetail);
        txtLocation = view.findViewById(R.id.txtLocationDetail);
        txtLength = view.findViewById(R.id.txtLengthDetail);
        txtDescription = view.findViewById(R.id.txtDescriptionDetail);

        btnDelete = view.findViewById(R.id.IbtnDelete);
        btnEdit = view.findViewById(R.id.IbtnUpdate);
        btnViewObser = view.findViewById(R.id.IbtnViewObservation);
        btnShare = view.findViewById(R.id.IbtnShare);

        parkingYes = view.findViewById(R.id.parkingYesDetail);
        parkingNo = view.findViewById(R.id.parkingNoDetail);
        levelLow = view.findViewById(R.id.lowDetail);
        levelMedium = view.findViewById(R.id.mediumDetail);
        levelDifficult = view.findViewById(R.id.difficultDetail);

        parkingYes.setEnabled(false);
        parkingNo.setEnabled(false);
        levelLow.setEnabled(false);
        levelMedium.setEnabled(false);
        levelDifficult.setEnabled(false);

        Bundle bundle = getArguments();
        Hikes hike = bundle.getParcelable("ListHikes");
        state = bundle.getInt("state");
        user_id = bundle.getInt("user_id");

        if(state == 0){
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
        }

        if(hike != null){

            txtName.setText(hike.getHike_name());
            txtLocation.setText(hike.getHike_location());
            txtLength.setText(hike.getHike_length());
            txtDescription.setText(hike.getHike_description());

            parking = hike.getParking();
            level = hike.getLevel();

            hike_id = hike.getId();
            getUriEdit = hike.getHike_image();

            if(hike.getHike_image().matches("")){
                img.setImageResource(R.drawable.head);
            }
            else {
                img.setImageURI(Uri.parse(hike.getHike_image()));
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
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();

                //share hike
                sql.shareHike(hike_id);

                //get new share status
                String newShareStatus = sql.selectShareState(hike_id);

                if(!newShareStatus.matches("")){
                    Toast("Share succeed");
                }
                else {
                    Toast("Share failed");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDelete();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupEdit();
            }
        });

        btnViewObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleObservation = new Bundle();
                bundleObservation.putParcelable("ListObservations", hike);
                bundleObservation.putInt("Hike_ID", hike.getId());
                bundleObservation.putInt("state", state);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                AddObservationFragment addObservationFragment = new AddObservationFragment();
                addObservationFragment.setArguments(bundleObservation);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, addObservationFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                if(state == 1){
                    getParentFragmentManager().popBackStack();
                }
                else{
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                if(state == 1){
                    getParentFragmentManager().popBackStack();
                }
                else{
                    getParentFragmentManager().popBackStack();
                }
            }
        });


        return view;
    }

    public void showPopupDelete() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.check_delete_popup);

        Button btnCancel = dialog.findViewById(R.id.btnCancelDeletePopup);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDeletePopup);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();

                //perform delete hike
                sql.deleteObservationByHikeID(hike_id);

                //get current max id of hike
                int currentHikeID = sql.selectCountHikes();
                //perform delete hike
                sql.deleteHike(hike_id, user_id);
                //get new max id of hike
                int newHikeID = sql.selectCountHikes();

                if(newHikeID < currentHikeID) {
                    Toast("Delete Hike Succeed!!");
                    getParentFragmentManager().popBackStack();
                    dialog.dismiss();
                }
                else {
                    Toast("Delete Hike Failed!!");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private ImageView popupImgEdit;

    private void showPopupEdit() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.update_hike_popup);

        popupImgEdit = dialog.findViewById(R.id.imgViewPopupEdit);
        ImageButton popupImgButton = dialog.findViewById(R.id.btnUploadEdit);

        EditText popupNameEdit = dialog.findViewById(R.id.eTxtNameEdit);
        EditText popupLocationEdit = dialog.findViewById(R.id.eTxtLocationEdit);
        EditText popupLengthEdit = dialog.findViewById(R.id.eTxtLengthEdit);
        EditText popupDescriptionEdit = dialog.findViewById(R.id.eTxtDescriptionEdit);

        RadioGroup radioGroupParkingEdit = dialog.findViewById(R.id.parkingPopupGroupEdit);
        RadioGroup radioGroupLevelEdit = dialog.findViewById(R.id.levelPopupGroupEdit);

        RadioButton parkingYesEdit = dialog.findViewById(R.id.parkingYesPopupEdit);
        RadioButton parkingNoEdit = dialog.findViewById(R.id.parkingNoPopupEdit);
        RadioButton levelLowEdit = dialog.findViewById(R.id.lowPopupEdit);
        RadioButton levelMediumEdit = dialog.findViewById(R.id.mediumPopupEdit);
        RadioButton levelDifficultEdit = dialog.findViewById(R.id.difficultPopupEdit);

        Button btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);
        Button btnConfirmEdit = dialog.findViewById(R.id.btnConfirmEdit);

        if(getUriEdit.matches("")){
            popupImgEdit.setImageResource(R.drawable.head);
        }
        else{
            popupImgEdit.setImageURI(Uri.parse(getUriEdit));
        }
        popupNameEdit.setText(txtName.getText());
        popupLocationEdit.setText(txtLocation.getText());
        popupLengthEdit.setText(txtLength.getText());
        popupDescriptionEdit.setText(txtDescription.getText());

        if(parking.toLowerCase().matches("yes")){
            parkingYesEdit.setChecked(true);
        }
        else {
            parkingNoEdit.setChecked(true);
        }

        if(level.toLowerCase().matches("low")){
            levelLowEdit.setChecked(true);
        }
        else if(level.toLowerCase().matches("medium")){
            levelMediumEdit.setChecked(true);
        }
        else{
            levelDifficultEdit.setChecked(true);
        }

        popupImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HikeDetailFragment.this)
                        .crop()                                 //Crop image
                        .compress(1024)			        //Final image size will be less than 1 MB
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080
                        .start();
            }
        });

        btnConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioIdParkingGroup = radioGroupParkingEdit.getCheckedRadioButtonId();
                RadioButton radioButtonParking = dialog.findViewById(radioIdParkingGroup);

                int radioIdLevelGroup = radioGroupLevelEdit.getCheckedRadioButtonId();
                RadioButton radioButtonLevel = dialog.findViewById(radioIdLevelGroup);

                parking = radioButtonParking.getText().toString();
                level = radioButtonLevel.getText().toString();

                SqlQuery sql = new SqlQuery();
                sql.updateHike(popupNameEdit.getText().toString(), popupLocationEdit.getText().toString(), parking,
                        popupLengthEdit.getText().toString(), level, popupDescriptionEdit.getText().toString(),
                        getUriEdit.toString(), hike_id);

                //set value just update to hike detail fragment
                txtName.setText(popupNameEdit.getText());
                txtLocation.setText(popupLocationEdit.getText());
                txtLength.setText(popupLengthEdit.getText());
                txtDescription.setText(popupDescriptionEdit.getText());

                if(getUriEdit.matches("")){
                    img.setImageResource(R.drawable.head);
                }
                else {
                    img.setImageURI(Uri.parse(getUriEdit));
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

                dialog.dismiss();
            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        getUriEdit = uri.toString();
        popupImgEdit.setImageURI(Uri.parse(getUriEdit));
    }

    public void Toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}