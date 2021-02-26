package real_deal.bsis.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orm.query.Select;

import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.main_activity.BatchDetail;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;

public class BatchesAdapter extends RecyclerView.Adapter < BatchesAdapter.ViewHolder > {
    Context           context;
    DateformatControl dateformatControl;
    private List < DonationBatch > donationBatches;

    // RecyclerView recyclerView;
    public BatchesAdapter (List < DonationBatch > donationBatches , Context context) {
        this.donationBatches = donationBatches;
        this.context         = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View listItem = layoutInflater.inflate (R.layout.batch_list , parent , false);
        ViewHolder viewHolder = new ViewHolder (listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder , int position) {

        try {
            dateformatControl = new DateformatControl ();
            final DonationBatch donationBatch = donationBatches.get (position);

            if (donationBatch.getVenue () != null)
                holder.venue.setText ("Venue:  " + donationBatch.getVenue ().getName ());
            holder.created.setText ("Created:  " + dateformatControl.convertToShortDate (donationBatch.getDonationBatchDate ()));
            Long count =
                    Select.from (Donation.class).where ("donation_batch = " + donationBatch.getId ()).count ();
            holder.noOfDonation.setText ("Number Of Donations:  " + count);

            holder.linearLayout.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {

                    Bundle bundle = new Bundle ();
                    bundle.putParcelable ("donation_batch" , donationBatch);
                    Intent intent = new Intent (context , BatchDetail.class);
                    intent.putExtras (bundle);
                    context.startActivity (intent);
                }
            });
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }


    @Override
    public int getItemCount () {
        return donationBatches.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView     venue;
        public TextView     noOfDonation;
        public TextView     created;
        public LinearLayout linearLayout;

        public ViewHolder (View itemView) {
            super (itemView);
            this.venue        = itemView.findViewById (R.id.text_venue_name);
            this.noOfDonation = itemView.findViewById (R.id.text_no_of_donation);
            this.created      = itemView.findViewById (R.id.text_created);
            linearLayout      = itemView.findViewById (R.id.batch_linear_layout);

        }
    }
}