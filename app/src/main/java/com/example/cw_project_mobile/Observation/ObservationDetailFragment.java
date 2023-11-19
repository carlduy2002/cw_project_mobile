package com.example.cw_project_mobile.Observation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw_project_mobile.Object.Observations;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObservationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservationDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ObservationDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObservationDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObservationDetailFragment newInstance(String param1, String param2) {
        ObservationDetailFragment fragment = new ObservationDetailFragment();
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

    private String getUriObser = "";
    private TextView txtNameObser, txtDescriptionObser, txtDateObser, txtBack;
    private ImageButton btnDeleteObser, btnUpdateObser;
    private ImageView imgObser, btnBack;

    int observationID = 0;
    private int state = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_observation_detail, container, false);

        Bundle bundle = getArguments();
        Observations observations = bundle.getParcelable("ListObservations");
        state = bundle.getInt("state");

        observationID = observations.getObser_id();
        getUriObser = observations.getObser_img();

        imgObser = view.findViewById(R.id.imgObservation);

        txtBack = view.findViewById(R.id.txtBackObser);
        btnBack = view.findViewById(R.id.btnBackObser);

        txtNameObser = view.findViewById(R.id.txtNameObservationDetail);
        txtDescriptionObser = view.findViewById(R.id.txtDescriptionObservationDetail);
        txtDateObser = view.findViewById(R.id.txtDateObservationDetail);

        if(observations != null){
            txtNameObser.setText(observations.getObser_name());
            txtDescriptionObser.setText(observations.getObser_description());
            txtDateObser.setText(observations.getDate());

            if(observations.getObser_img().matches("")){
                imgObser.setImageResource(R.drawable.fansipan);
            }
            else {
                imgObser.setImageURI(Uri.parse(observations.getObser_img()));
            }
        }

        btnDeleteObser = view.findViewById(R.id.IbtnDeleteObser);
        btnUpdateObser = view.findViewById(R.id.IbtnUpdateObser);

        if(state == 0){
            btnDeleteObser.setVisibility(View.GONE);
            btnUpdateObser.setVisibility(View.GONE);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        btnUpdateObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupEditObservation();
            }
        });

        btnDeleteObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDelete();
            }
        });

        return view;
    }

    private ImageView popupImage;
    private void showPopupEditObservation() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.update_observation_popup);

        popupImage = dialog.findViewById(R.id.imgViewPopupEditObser);
        ImageButton btnUpdateImg = dialog.findViewById(R.id.btnUploadEditObser);

        EditText eTxtName = dialog.findViewById(R.id.eTxtNameEditObser);
        EditText eTxtDescription = dialog.findViewById(R.id.eTxtDescriptionEditObser);

        Button btnCancelObser = dialog.findViewById(R.id.btnCancelEditObser);
        Button btnConfirmObser = dialog.findViewById(R.id.btnConfirmEditObser);

        if(getUriObser.matches("")){
            popupImage.setImageResource(R.drawable.fansipan);
        }
        else {
            popupImage.setImageURI(Uri.parse(getUriObser));
        }

        eTxtName.setText(txtNameObser.getText());
        eTxtDescription.setText(txtDescriptionObser.getText());

        btnUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ObservationDetailFragment.this)
                        .crop()                                 //Crop image
                        .compress(1024)			        //Final image size will be less than 1 MB
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080
                        .start();
            }
        });

        btnConfirmObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eTxtName.getText().toString().matches("")){
                    SqlQuery sql = new SqlQuery();
                    sql.updateObservation(eTxtName.getText().toString(), eTxtDescription.getText().toString(),
                            getUriObser.toString(), observationID);

                    txtNameObser.setText(eTxtName.getText());
                    txtDescriptionObser.setText(eTxtDescription.getText());

                    if(getUriObser.matches("")){
                        imgObser.setImageResource(R.drawable.fansipan);
                    }
                    else {
                        imgObser.setImageURI(Uri.parse(getUriObser));
                    }

                    dialog.dismiss();
                }
                else {
                    Toast("Please enter observation name!!");
                }
            }
        });

        btnCancelObser.setOnClickListener(new View.OnClickListener() {
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

    private void showPopupDelete(){
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.check_delete_popup);

        Button btnCancel = dialog.findViewById(R.id.btnCancelDeletePopup);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDeletePopup);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();

                //get current max id of observation
                int currentID = sql.selectObservationMaxID();
                //perform delete observation
                sql.deleteObservation(observationID);
                //get new max id of observation
                int newID = sql.selectObservationMaxID();

                if(newID < currentID){
                    Toast("Delete Observation Succeed!!");
                    getParentFragmentManager().popBackStack();
                    dialog.dismiss();
                }
                else{
                    Toast("Delete Observation Failed!!");
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

    private void Toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        getUriObser = uri.toString();
        popupImage.setImageURI(Uri.parse(getUriObser));
    }
}