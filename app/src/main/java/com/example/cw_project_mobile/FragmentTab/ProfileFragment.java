package com.example.cw_project_mobile.FragmentTab;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cw_project_mobile.Authenticate.HashPassword;
import com.example.cw_project_mobile.Authenticate.SignIn;
import com.example.cw_project_mobile.Hike.AddFragment;
import com.example.cw_project_mobile.Hike.HikeDetailFragment;
import com.example.cw_project_mobile.Object.Users;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private ShapeableImageView avatar;
    private ImageButton btnEditProfile, btnChangepassword, btnLogout;
    private TextView txtUsername, txtEmail, txtAddress;
    private String getUri = "";
    private int user_id = 0;
    private Users users;
    private String password = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        users = bundle.getParcelable("lstUsers");

        user_id = users.getId();
        password = users.getPassword();
        getUri = users.getAvatar();

        avatar = view.findViewById(R.id.avatar);

        btnEditProfile = view.findViewById(R.id.Ibtn_EditProfile);
        btnChangepassword = view.findViewById(R.id.Ibtn_ChangePassword);
        btnLogout = view.findViewById(R.id.Ibtn_Logout);

        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);

        if(users.getAvatar().matches("")){
            avatar.setImageResource(R.drawable.user);
        }
        else {
            avatar.setImageURI(Uri.parse(users.getAvatar().toString()));
        }

        txtUsername.setText(users.getUsername());
        txtEmail.setText(users.getEmail());
        txtAddress.setText(users.getAddress());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupEdit(txtUsername.getText().toString(), txtEmail.getText().toString(), txtAddress.getText().toString());
            }
        });

        btnChangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordPopup();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignIn.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showChangePasswordPopup() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.change_password_popup);

        EditText editCurrentPwd = dialog.findViewById(R.id.editCurrentPwd);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashPassword hashPassword = new HashPassword();
                SqlQuery sql = new SqlQuery();
                String currentPwd = "";

                if(!editCurrentPwd.getText().toString().matches("")){
                    currentPwd = hashPassword.hashPassword(editCurrentPwd.getText().toString());

                    String oldPwd = sql.selectPasword(user_id);

                    if(currentPwd.matches(oldPwd)){
                        dialog.dismiss();
                        showCreateNewPassowrdPopup(oldPwd);
                    }
                    else {
                        Toast("Current Pasword is incorrectly");
                    }
                }
                else {
                    Toast("Please, enter Current Password");
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

    private void showCreateNewPassowrdPopup(String oldPassword) {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.create_new_password_popup);

        EditText editNewPwd = dialog.findViewById(R.id.editNewPwd);
        EditText editConfirmPwd = dialog.findViewById(R.id.editConfirmPwd);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirmPassword);
        Button btnCancel = dialog.findViewById(R.id.btnCancelPassword);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashPassword hashPassword = new HashPassword();
                SqlQuery sql = new SqlQuery();

                if(!editNewPwd.getText().toString().matches("")){
                    if(!editConfirmPwd.getText().toString().matches("")){
                        if(editNewPwd.getText().toString().matches(editConfirmPwd.getText().toString())){
                            String newPwd = hashPassword.hashPassword(editNewPwd.getText().toString());

                            if(!newPwd.matches(oldPassword)){
                                //get old pass
                                String oldPassword = sql.selectPasword(user_id);
                                //change pass
                                sql.changePassword(newPwd, user_id);
                                //get new pass
                                String newPassword = sql.selectPasword(user_id);

                                if(!newPassword.matches(oldPassword)){
                                    Toast("Change Password succeed");
                                    dialog.dismiss();
                                }
                                else {
                                    Toast("Change Password failed");
                                }
                            }
                            else {
                                Toast("Password already exist before. Please, enter another password");
                            }
                        }
                        else {
                            Toast("New Password and Confirm Password is not match");
                        }
                    }
                    else {
                        Toast("Please, enter Confirm Password");
                    }
                }
                else {
                    Toast("Please, enter New Password");
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

    private ShapeableImageView avatarUpdate;
    private String u_name = "", u_email = "";
    private EditText editUsername, editEmail;

    private void showPopupEdit(String username, String email, String address) {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.update_profile_popup);

        avatarUpdate = dialog.findViewById(R.id.avatarUpdate);

        ImageButton btnUpload = dialog.findViewById(R.id.btnUploadAvatar);

        Button btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);
        Button btnConfirmEdit = dialog.findViewById(R.id.btnConfirmEdit);

        editUsername = dialog.findViewById(R.id.editUsername);
        editEmail = dialog.findViewById(R.id.editEmail);
        EditText editAddess = dialog.findViewById(R.id.editAddress);

        if(getUri.matches("")){
            avatarUpdate.setImageResource(R.drawable.user);
        }
        else {
            avatarUpdate.setImageURI(Uri.parse(getUri));
        }

        editUsername.setText(username);
        editEmail.setText(email);
        editAddess.setText(address);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .crop()                                 //Crop image
                        .compress(1024)			        //Final image size will be less than 1 MB
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080
                        .start();
            }
        });

        btnConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();
                String regexEmail = "^([a-zA-z0-9]+@gmail+\\.[a-zA-Z]{2,})$";

                if(editEmail.getText().toString().matches(regexEmail)){
                    if(validateUpdatePrifile() == true){
                        sql.updateUsers(u_name, u_email, editAddess.getText().toString(), getUri,user_id);

                        if(!getUri.matches("")){
                            avatar.setImageURI(Uri.parse(getUri.toString()));
                        }
                        else {
                            avatar.setImageResource(R.drawable.user);
                        }
                        txtUsername.setText(editUsername.getText());
                        txtEmail.setText(editEmail.getText());
                        txtAddress.setText(editAddess.getText());

                        dialog.dismiss();
                    }
                }
                else {
                    Toast("Email is invalid");
                }
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

    private boolean validateUpdatePrifile(){
        SqlQuery sql = new SqlQuery();
        u_name = editUsername.getText().toString();
        u_email = editEmail.getText().toString();

        ArrayList<Users> userInfor;
        userInfor = sql.selectUserInfor(user_id);

        for(Users i : userInfor){
            if(u_name.matches(i.getUsername())){
                Toast("Username already exist");
                return false;
            }
            if (u_email.matches(i.getEmail())) {
                Toast("Email already exist");
                return false;
            }
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        getUri = uri.toString();
        avatarUpdate.setImageURI(Uri.parse(getUri));
    }

    private void Toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}