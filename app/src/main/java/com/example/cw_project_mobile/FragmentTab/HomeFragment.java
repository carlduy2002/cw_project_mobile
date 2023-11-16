package com.example.cw_project_mobile.FragmentTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.cw_project_mobile.Adapter.HikeAdapter;
import com.example.cw_project_mobile.Authenticate.SignIn;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private RecyclerView recyclerView;
    private ImageButton btnSearch, btnDeleteTextSearch, btnCloseSearch;
    private EditText eTxtSearch;
    FrameLayout layoutSearch, layoutNavbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutSearch = view.findViewById(R.id.frameSearch);
        layoutSearch.setVisibility(View.GONE);

        layoutNavbar = view.findViewById(R.id.frameNavbar);

        btnCloseSearch = view.findViewById(R.id.btnCloseSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnDeleteTextSearch = view.findViewById(R.id.btnDeleteSearch);

        eTxtSearch = view.findViewById(R.id.eTxtSearch);

        recyclerView = view.findViewById(R.id.recyclerViewAllHike);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SqlQuery sqlQuery = new SqlQuery();

        Bundle bundle = getArguments();
        String user_id = bundle.getString("user_id");
        int getUID = Integer.parseInt(user_id);

        HikeAdapter hikeAdapter = new HikeAdapter(sqlQuery.selectAllHikes(), getContext(), 0, getUID);
        recyclerView.setAdapter(hikeAdapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    btnDeleteTextSearch.setVisibility(View.VISIBLE);
                    hikeAdapter.getFilter().filter(s);
                }
                else {
                    btnDeleteTextSearch.setVisibility(View.GONE);
                    hikeAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        eTxtSearch.addTextChangedListener(textWatcher);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSearch.setVisibility(View.VISIBLE);
                layoutNavbar.setVisibility(View.GONE);
            }
        });

        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavbar.setVisibility(View.VISIBLE);
                layoutSearch.setVisibility(View.GONE);
            }
        });

        btnDeleteTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtSearch.setText("");
            }
        });

        return view;
    }


}