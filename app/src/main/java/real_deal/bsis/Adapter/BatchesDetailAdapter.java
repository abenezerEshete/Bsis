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
import android.widget.Toast;

import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.main_activity.EditDonation;
import real_deal.bsis.model.Donation;

public class BatchesDetailAdapter extends RecyclerView.Adapter < BatchesDetailAdapter.ViewHolder > {
    Context context;
    private List < Donation > donations;

    // RecyclerView recyclerView;
    public BatchesDetailAdapter (List < Donation > donations , Context context) {
        this.donations = donations;
        this.context   = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View listItem =
                layoutInflater.inflate (R.layout.batch_donation_detail_list_item , parent , false);
        ViewHolder viewHolder = new ViewHolder (listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder (ViewHolder holder , final int position) {
        System.out.println ("--------------------------hereee-" + position);
        final Donation donation = donations.get (position);
        try {
            if (donation.getDonor () != null)
                holder.donorNumber.setText ("Donor #:  " + donation.getDonor ().getDonorNumber ());
            holder.din.setText ("DIN:  " + donation.getDonationIdentificationNumber ());
            if (donation.getDonationType () != null)
                holder.donationType.setText ("Donation Type:  " + donation.getDonationType ().getDonationType ());
            if (donation.getPackType () != null)
                holder.packType.setText ("Pack Type:  " + donation.getPackType ().getPackType ());
            holder.batchDetailLayout.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {

                    Bundle bundle = new Bundle ();
                    bundle.putParcelable ("donation" , donation);
                    Intent intent = new Intent (context , EditDonation.class);
                    intent.putExtras (bundle);
                    context.startActivity (intent);


                    Toast.makeText (view.getContext () , "click on item: " + donation.getDonor () , Toast.LENGTH_LONG).show ();
                }
            });

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }


    @Override
    public int getItemCount () {
        return donations.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView     donorNumber;
        public TextView     din;
        public TextView     packType;
        public TextView     donationType;
        public LinearLayout batchDetailLayout;

        public ViewHolder (View itemView) {
            super (itemView);
            this.donorNumber  = itemView.findViewById (R.id.donor_number);
            this.din          = itemView.findViewById (R.id.din);
            this.packType     = itemView.findViewById (R.id.pack_type);
            this.donationType = itemView.findViewById (R.id.donation_type);
            batchDetailLayout = itemView.findViewById (R.id.batch_detail_linear_Layout);

        }
    }
}