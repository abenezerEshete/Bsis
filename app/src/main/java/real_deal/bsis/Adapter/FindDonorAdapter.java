package real_deal.bsis.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.main_activity.FindDonorActivity;
import real_deal.bsis.model.Donor;

public class FindDonorAdapter extends RecyclerView.Adapter < FindDonorAdapter.ViewHolder > {
    Context context;
    private List < Donor > donors;


    // RecyclerView recyclerView;
    public FindDonorAdapter (List < Donor > donors , Context context) {
        this.donors  = donors;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View listItem = layoutInflater.inflate (R.layout.list_item , parent , false);
        ViewHolder viewHolder = new ViewHolder (listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder , final int position) {
        final Donor donor = donors.get (position);

        try {
            holder.fulname.setText ("Full Name:  " + donor.getFirstName () + " " + donor.getMiddleName () + " " + donor.getLastName ());
            if (donor.getVenue () != null)
                holder.venue.setText ("Venue:  " + donor.getVenue ().getName ());
            holder.id.setText ("Donor Number:  " + donor.getDonorNumber ());
            holder.gender.setText ("Gender:  " + donor.getGender ());
            holder.birthday.setText ("Date of Birth:  " + donor.getBirthDate ());
            //    holder.age.setText ( "Age:  " + donor.getAge () );

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        holder.linearLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                new FindDonorActivity ().onItemSelected (donor , context);
            }
        });
    }


    @Override
    public int getItemCount () {
        return donors.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView     fulname;
        public TextView     id;
        public TextView     venue;
        //    public TextView     age;
        public TextView     gender;
        public TextView     birthday;
        public LinearLayout linearLayout;

        public ViewHolder (View itemView) {
            super (itemView);
            this.fulname = itemView.findViewById (R.id.text_fullname);
            this.id      = itemView.findViewById (R.id.text_id);
            this.venue   = itemView.findViewById (R.id.text_venie);
            //   this.age      = itemView.findViewById ( R.id.text_age );
            this.gender   = itemView.findViewById (R.id.text_gender);
            this.birthday = itemView.findViewById (R.id.text_bithday);
            linearLayout  = itemView.findViewById (R.id.list_item_linear_layout);
        }
    }
}