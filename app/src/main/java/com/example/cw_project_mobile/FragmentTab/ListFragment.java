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

import com.example.cw_project_mobile.Adapter.HikeAdapter;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
    private FrameLayout frameLayoutNavbar, frameLayoutSearch;
    private ImageButton btnCloseSearch, btnDeleteSearch, btnSearch;
    private EditText eTxtSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMyHike);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        frameLayoutSearch = view.findViewById(R.id.FrameSearch);
        frameLayoutNavbar = view.findViewById(R.id.FrameNavbar);

        btnCloseSearch = view.findViewById(R.id.btnCloseSearch);
        btnDeleteSearch = view.findViewById(R.id.btnDeleteSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        eTxtSearch = view.findViewById(R.id.eTxtSearch);

        SqlQuery sqlQuery = new SqlQuery();
        HikeAdapter myHikeAdapter = new HikeAdapter(sqlQuery.selectMyHikes(), getContext(), 1);

        recyclerView.setAdapter(myHikeAdapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    btnDeleteSearch.setVisibility(View.VISIBLE);
                    myHikeAdapter.getFilter().filter(s);
                }
                else {
                    btnDeleteSearch.setVisibility(View.GONE);
                    myHikeAdapter.getFilter().filter(s);
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
                frameLayoutSearch.setVisibility(View.VISIBLE);
                frameLayoutNavbar.setVisibility(View.GONE);
            }
        });

        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayoutNavbar.setVisibility(View.VISIBLE);
                frameLayoutSearch.setVisibility(View.GONE);
            }
        });

        btnDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtSearch.setText("");
            }
        });

        return view;
    }
}