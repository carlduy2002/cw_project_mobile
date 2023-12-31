package com.example.cw_project_mobile.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cw_project_mobile.Hike.AddFragment;
import com.example.cw_project_mobile.FragmentTab.HomeFragment;
import com.example.cw_project_mobile.FragmentTab.ListFragment;
import com.example.cw_project_mobile.FragmentTab.ProfileFragment;
import com.example.cw_project_mobile.R;

public class BottomActivity extends AppCompatActivity {

    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        final LinearLayout homeLayout = findViewById(R.id.home_layout);
        final LinearLayout listLayout = findViewById(R.id.list_layout);
        final LinearLayout addLayout = findViewById(R.id.add_layout);
        final LinearLayout profileLayout = findViewById(R.id.profile_layout);

        final ImageView homeImage = findViewById(R.id.home_img);
        final ImageView listImage = findViewById(R.id.list_img);
        final ImageView addImage = findViewById(R.id.add_img);
        final ImageView profileImage = findViewById(R.id.profile_img);

        final TextView homeText = findViewById(R.id.home_txt);
        final TextView listText = findViewById(R.id.list_txt);
        final TextView addText = findViewById(R.id.add_txt);
        final TextView profileText = findViewById(R.id.profile_txt);

        //set default fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();

        //Home tab
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if home tab is already selected or not
                if(selectedTab != 1){
                    //set home fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    //unselect other tabs expect homw tab
                    listText.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    listImage.setImageResource(R.drawable.listhike_icon);
                    addImage.setImageResource(R.drawable.add_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    listLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    addLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //selected home tab
                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.home_selected_icon);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.setAnimation(scaleAnimation);

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();

                    //set selectedTab = 1
                    selectedTab = 1;
                }
            }
        });

        //List tab
        listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if list tab is already selected or not
                if(selectedTab != 2){
                    //set list fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ListFragment.class, null)
                            .commit();

                    //unselect other tabs expect list tab
                    homeText.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    addImage.setImageResource(R.drawable.add_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    addLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //set selected list tab
                    listText.setVisibility(View.VISIBLE);
                    listImage.setImageResource(R.drawable.listhike_selected_icon);
                    listLayout.setBackgroundResource(R.drawable.round_back_list);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    listLayout.setAnimation(scaleAnimation);

                    //set selectTab = 2
                    selectedTab = 2;
                }

            }
        });

        //Add tab
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if add tab is already selected or not
                if(selectedTab != 3){
                    //set add fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, AddFragment.class, null)
                            .commit();

                    //unselect other tabs expect add tab
                    homeText.setVisibility(View.GONE);
                    listText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    listImage.setImageResource(R.drawable.listhike_icon);
                    profileImage.setImageResource(R.drawable.profile_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    listLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //set selected add tab
                    addText.setVisibility(View.VISIBLE);
                    addImage.setImageResource(R.drawable.add_selected_icon);
                    addLayout.setBackgroundResource(R.drawable.round_back_add);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    addLayout.setAnimation(scaleAnimation);

                    //set selectedTab = 3
                    selectedTab = 3;
                }
            }
        });

        //Profile tab
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if profile is already selected or not
                if(selectedTab != 4){
                    //set profile fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
                            .commit();

                    //unselect other tabs expect profile tab
                    homeText.setVisibility(View.GONE);
                    listText.setVisibility(View.GONE);
                    addText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.home_icon);
                    listImage.setImageResource(R.drawable.listhike_icon);
                    addImage.setImageResource(R.drawable.add_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    listLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    addLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //set selected profile tab
                    profileText.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profile_selected_icon);
                    profileLayout.setBackgroundResource(R.drawable.round_back_profile);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.setAnimation(scaleAnimation);

                    //set selectedTab = 4
                    selectedTab = 4;
                }
            }
        });
    }
}