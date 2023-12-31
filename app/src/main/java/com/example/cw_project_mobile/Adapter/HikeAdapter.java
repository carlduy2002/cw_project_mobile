package com.example.cw_project_mobile.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cw_project_mobile.FragmentTab.HomeFragment;
import com.example.cw_project_mobile.Hike.HikeDetailFragment;
import com.example.cw_project_mobile.Object.Hikes;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> implements Filterable {

    Context context;
    ArrayList<Hikes> lstHikes;
    ArrayList<Hikes> lstFullHikes;
    int state = 0;
    private boolean isDeleteButtonEnabled = false;
    private int hike_id = 0;

    public HikeAdapter(ArrayList<Hikes> _lstHikes, Context _context, int _state){
        super();
        this.lstHikes = _lstHikes;
        this.context  = _context;
        this.state = _state;
        lstFullHikes = _lstHikes;
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view_hike,parent,false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        holder.txtName.setText(lstHikes.get(position).getHike_name());
        holder.txtLocation.setText(lstHikes.get(position).getHike_location());
        holder.txtDate.setText(lstHikes.get(position).getHike_date());
        if(lstHikes.get(position).getHike_image().matches("")){
            holder.img.setImageResource(R.drawable.head);
        }
        else{
            holder.img.setImageURI(Uri.parse(lstHikes.get(position).getHike_image()));
        }

        Hikes hike = lstHikes.get(position);
        hike_id = hike.getId();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeleteButtonEnabled == true){
                    isDeleteButtonEnabled = false;
                    holder.btnDeleteHike.setVisibility(View.GONE);
                    holder.backgroundDelete.setVisibility(View.GONE);
                }
                else {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    HikeDetailFragment hikeDetailFragment = new HikeDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ListHikes", hike);
                    bundle.putInt("state", state);

                    hikeDetailFragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, hikeDetailFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isDeleteButtonEnabled) {
                    isDeleteButtonEnabled = true;
                    holder.btnDeleteHike.setVisibility(View.VISIBLE);
                    holder.backgroundDelete.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        holder.btnDeleteHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup(hike_id);
            }
        });

    }

    private void showDeletePopup(int id) {
        Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.check_delete_popup);

        Button btnCancel = dialog.findViewById(R.id.btnCancelDeletePopup);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDeletePopup);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery sql = new SqlQuery();

                //perform delete hike
                sql.deleteObservationByHikeID(id);

                //get current max id of hike
                int currentHikeID = sql.selectHikeMaxID();
                //perform delete hike
                sql.deleteHike(id);
                //get new max id of hike
                int newHikeID = sql.selectHikeMaxID();

                if(newHikeID < currentHikeID) {
                    Toast("Delete Hike Succeed!!");

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();

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

    public void Toast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return lstHikes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter = constraint.toString().toLowerCase().trim();

                if(filter.isEmpty()){
                    lstHikes = lstFullHikes;
                }
                else {
                    ArrayList<Hikes> hikes = new ArrayList<>();
                    for(Hikes i : lstFullHikes){
                        if(i.getHike_name().toLowerCase().contains(filter)){
                            hikes.add(i);
                        }

//                        if(hikes.isEmpty()){
//                            HomeFragment homeFragment = new HomeFragment();
//                            homeFragment.showWrongIcon = true;
//                        }
//                        else {
//                            HomeFragment homeFragment = new HomeFragment();
//                            homeFragment.showWrongIcon = false;
//                        }
                    }

                    lstHikes = hikes;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lstHikes;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lstHikes = (ArrayList<Hikes>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDate, txtLocation;
        ImageView img;
        ImageButton btnDeleteHike;
        CardView cardView;
        LinearLayout backgroundDelete;
        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtHikeName);
            txtLocation = itemView.findViewById(R.id.txtHikeLocation);
            txtDate = itemView.findViewById(R.id.txtHikeDate);
            img = itemView.findViewById(R.id.imgHike);
            btnDeleteHike = itemView.findViewById(R.id.btnDeleteHike);
            cardView = itemView.findViewById(R.id.card_hike);
            backgroundDelete = itemView.findViewById(R.id.backgroundDelete);

            animation(itemView);
        }
    }

    public void animation(View view){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.setAnimation(animation);
    }
}
