package com.example.cw_project_mobile.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cw_project_mobile.FragmentTab.HomeFragment;
import com.example.cw_project_mobile.Object.Observations;
import com.example.cw_project_mobile.Observation.AddObservationFragment;
import com.example.cw_project_mobile.Observation.ObservationDetailFragment;
import com.example.cw_project_mobile.Query.SqlQuery;
import com.example.cw_project_mobile.R;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObserViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Observations> lstObservations;
    private ArrayList<Observations> lstFullObservations;
    private boolean isDeleteButtonEnabled = false;
    private int obserState = 0;
    private int observation_id = 0;

    public ObservationAdapter(ArrayList<Observations> _lstObservations, Context _context, int _obserState) {
        this.context = _context;
        this.lstObservations = _lstObservations;
        this.obserState = _obserState;
        lstFullObservations = _lstObservations;
    }

    @NonNull
    @Override
    public ObserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_view_observation,parent,false);
        return new ObserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObserViewHolder holder, int position) {
        holder.obserImage.setImageURI(Uri.parse(lstObservations.get(position).getObser_img()));
        holder.txtName.setText(lstObservations.get(position).getObser_name());
        holder.txtDate.setText(lstObservations.get(position).getDate());

        if(lstObservations.get(position).getObser_img().matches("")){
            holder.obserImage.setImageResource(R.drawable.head);
        }
        else{
            holder.obserImage.setImageURI(Uri.parse(lstObservations.get(position).getObser_img()));
        }

        Observations observations = lstObservations.get(position);
        observation_id = observations.getObser_id();

        holder.cardViewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeleteButtonEnabled == true){
                    isDeleteButtonEnabled = false;
                    holder.btnDeleteObservation.setVisibility(View.GONE);
                    holder.backgroundDelete.setVisibility(View.GONE);
                }
                else {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ObservationDetailFragment observationDetailFragment = new ObservationDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ListObservations", observations);
                    bundle.putInt("state", obserState);

                    observationDetailFragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, observationDetailFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        holder.cardViewObservation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isDeleteButtonEnabled) {
                    isDeleteButtonEnabled = true;
                    holder.btnDeleteObservation.setVisibility(View.VISIBLE);
                    holder.backgroundDelete.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        holder.btnDeleteObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup(observation_id);
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

                //get current max id of observation
                int currentID = sql.selectObservationMaxID();
                //perform delete observation
                sql.deleteObservation(id);
                //get new max id of observation
                int newID = sql.selectObservationMaxID();

                if(newID < currentID){
                    Toast("Delete Observation Succeed!!");

                    AddObservationFragment addObservationFragment = new AddObservationFragment();
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, addObservationFragment).commit();

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

    public void Toast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return lstObservations.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter = constraint.toString().toLowerCase().trim();

                if(filter.isEmpty()){
                    lstObservations = lstFullObservations;
                }
                else {
                    ArrayList<Observations> observation = new ArrayList<>();
                    for(Observations i : lstFullObservations){
                        if(i.getObser_name().toLowerCase().contains(filter)){
                            observation.add(i);
                        }
                    }

                    lstObservations = observation;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lstObservations;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lstObservations = (ArrayList<Observations>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ObserViewHolder extends RecyclerView.ViewHolder {
        private ImageView obserImage;
        private TextView txtName, txtDate;
        private CardView cardViewObservation;
        private ImageButton btnDeleteObservation;
        private LinearLayout backgroundDelete;
        public ObserViewHolder(@NonNull View itemView) {
            super(itemView);

            obserImage = itemView.findViewById(R.id.imgObservation);
            txtName = itemView.findViewById(R.id.txtObservationName);
            txtDate = itemView.findViewById(R.id.txtObservationDate);
            btnDeleteObservation = itemView.findViewById(R.id.btnDeleteObservation);
            cardViewObservation = itemView.findViewById(R.id.card_observation);
            backgroundDelete = itemView.findViewById(R.id.backgroundDelete);

            animation(itemView);
        }

        public void animation(View view){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.setAnimation(animation);
        }
    }
}
