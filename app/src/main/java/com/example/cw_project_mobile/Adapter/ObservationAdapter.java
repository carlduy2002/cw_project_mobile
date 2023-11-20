package com.example.cw_project_mobile.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cw_project_mobile.Object.Observations;
import com.example.cw_project_mobile.Observation.ObservationDetailFragment;
import com.example.cw_project_mobile.R;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObserViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Observations> lstObservations;
    private ArrayList<Observations> lstFullObservations;
    private boolean isDeleteButtonEnabled = false;
    private int obserState = 0;

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
            holder.obserImage.setImageResource(R.drawable.fansipan);
        }
        else{
            holder.obserImage.setImageURI(Uri.parse(lstObservations.get(position).getObser_img()));
        }

        Observations observations = lstObservations.get(position);

        holder.cardViewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
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
        private LinearLayout backgroundDelete;
        public ObserViewHolder(@NonNull View itemView) {
            super(itemView);

            obserImage = itemView.findViewById(R.id.imgObservation);
            txtName = itemView.findViewById(R.id.txtObservationName);
            txtDate = itemView.findViewById(R.id.txtObservationDate);
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
