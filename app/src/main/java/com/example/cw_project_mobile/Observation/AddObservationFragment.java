package com.example.cw_project_mobile.Observation;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cw_project_mobile.Adapter.ObservationAdapter;
import com.example.cw_project_mobile.Object.Hikes;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddObservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddObservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddObservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddObservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddObservationFragment newInstance(String param1, String param2) {
        AddObservationFragment fragment = new AddObservationFragment();
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

    private ImageButton btnBack, btnAddNewObser, btnSearchObser, btnCloseSearchObser, btnDeleteSearchObser;
    private EditText eTxtSearchObser;
    private RecyclerView recyclerViewObser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_observation, container, false);

        btnBack = view.findViewById(R.id.btnObserBack);
        btnAddNewObser = view.findViewById(R.id.btnAddObservation);

        btnSearchObser = view.findViewById(R.id.btnSearchObser);
        btnCloseSearchObser = view.findViewById(R.id.btnCloseSearchObser);
        btnDeleteSearchObser = view.findViewById(R.id.btnDeleteSearchObser);

        eTxtSearchObser = view.findViewById(R.id.eTxtSearchObser);

        FrameLayout layoutSearch = (FrameLayout) view.findViewById(R.id.frameSearchObser);
        layoutSearch.setVisibility(View.GONE);

        FrameLayout layoutNavbar = (FrameLayout) view.findViewById(R.id.frameNavbarObser);

        recyclerViewObser = view.findViewById(R.id.recyclerViewObservation);

        Bundle getHikeID = getArguments();
        int hikeID = getHikeID.getInt("Hike_ID");

        SqlQuery sqlQuery = new SqlQuery();
        recyclerViewObser.setLayoutManager(new LinearLayoutManager(getContext()));
        ObservationAdapter observationAdapter = new ObservationAdapter(sqlQuery.selectObservationsOfHike(hikeID),
                getContext(), 0);
        recyclerViewObser.setAdapter(observationAdapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    btnDeleteSearchObser.setVisibility(View.VISIBLE);
                    observationAdapter.getFilter().filter(s);
                }
                else {
                    btnDeleteSearchObser.setVisibility(View.GONE);
                    observationAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        eTxtSearchObser.addTextChangedListener(textWatcher);

        btnSearchObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSearch.setVisibility(View.VISIBLE);
                layoutNavbar.setVisibility(View.GONE);
            }
        });

        btnCloseSearchObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavbar.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.GONE);
            }
        });

        btnDeleteSearchObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtSearchObser.setText("");
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        btnAddNewObser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAddObser();
            }
        });

        return view;
    }

    private String getUriObservation = "";
    private ImageView imgObservation;
    private ImageButton btnImgUpload;
    private EditText eTxtName, eTxtDescription;
    private Button btnCancel, btnConfirm;

    public void Toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showPopupAddObser() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.add_observation_popup);

        imgObservation = dialog.findViewById(R.id.imgObservation);
        btnImgUpload = dialog.findViewById(R.id.btnUploadObservation);

        eTxtName = dialog.findViewById(R.id.eTxtNameObservation);
        eTxtDescription = dialog.findViewById(R.id.eTxtDescriptionObservation);

        btnCancel = dialog.findViewById(R.id.btnCancelObservation);
        btnConfirm = dialog.findViewById(R.id.btnConfirmObservation);


        btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddObservationFragment.this)
                        .crop()                                 //Crop image
                        .compress(1024)                    //Final image size will be less than 1 MB
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080
                        .start();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();
                Hikes hike = bundle.getParcelable("ListObservations");

                if(hike != null) {
                    if(!eTxtName.getText().toString().matches("")){
                        SqlQuery sqlQuery = new SqlQuery();
                        //get current max id of observation
                        int currentID = sqlQuery.selectObservationMaxID();

                        //insert new observation
                        sqlQuery.insertObservation(eTxtName.getText().toString(), eTxtDescription.getText().toString(),
                                getUriObservation, hike.getId());

                        //get new max id of observation
                        int newID = sqlQuery.selectObservationMaxID();

                        if (currentID < newID) {
                            recyclerViewObser.setLayoutManager(new LinearLayoutManager(getContext()));
                            ObservationAdapter observationAdapter = new ObservationAdapter(sqlQuery.selectObservationsOfHike(hike.getId()),
                                    getContext(), 0);
                            recyclerViewObser.setAdapter(observationAdapter);

                            Toast("Add Observation Succeed!!");

                            dialog.dismiss();
                        }
                        else {
                            Toast("Add Observation Failed!!");
                        }
                    }
                    else {
                        Toast("Please, enten observation name!!");
                    }
                }
                else {
                    Toast("Something went wrong!!");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        getUriObservation = uri.toString();
        imgObservation.setImageURI(Uri.parse(getUriObservation));
    }
}