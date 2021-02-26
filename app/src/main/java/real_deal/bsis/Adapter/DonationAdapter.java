package real_deal.bsis.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.model.Donation;

public class DonationAdapter extends RecyclerView.Adapter < DonationAdapter.ViewHolder > {


    Context           context;
    DateformatControl dateformatControl;
    private List < Donation > donations;

    // RecyclerView recyclerView;
    public DonationAdapter (List < Donation > listdata , Context context) {
        this.donations = listdata;
        this.context   = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View listItem = layoutInflater.inflate (R.layout.list_item_donation , parent , false);
        ViewHolder viewHolder = new ViewHolder (listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder , final int position) {
        dateformatControl = new DateformatControl ();
        final Donation donation = donations.get (position);
        if (donation.getVenue () != null)
            holder.venue.setText ("Venue:  " + donation.getVenue ().getName ());
        if (donation.getDonationDate () != null)
            holder.date.setText ("Date:  " + dateformatControl.convertToShortDate (donation.getDonationDate ()));
        if (donation.getBloodPressureDiastolic () != null && donation.getBloodPressureSystolic () != null)
            holder.bp.setText ("Bp:  " + donation.getBloodPressureSystolic () + " / " + donation.getBloodPressureDiastolic ());
        if (donation.getHaemoglobinCount () != null)
            holder.hb.setText ("Hb:  " + donation.getHaemoglobinCount ());
        if (donation.getDonorPulse () != null)
            holder.pulse.setText ("Pulse:  " + donation.getDonorPulse ());
        if (donation.getPackType () != null)
            holder.packType.setText ("Pack Type:  " + donation.getPackType ().getPackType ());
        if (donation.getDonorWeight () != null)
            holder.weight.setText ("Weight:  " + donation.getDonorWeight ());

        Log.d ("onBindViewHolder" , "onBindViewHolder+++++++++" + position);
        Log.d ("batch" , "onBindViewHolder+++++++++" + donation.getDonationBatch ());
        holder.linearLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                new real_deal.bsis.donor_detail.DonationTab ().onItemSelected (donation , context);
                Toast.makeText (view.getContext () , "click on item: " + donation.getId () , Toast.LENGTH_LONG).show ();
            }
        });
    }


    @Override
    public int getItemCount () {
        return donations.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView     venue;
        public TextView     date;
        public TextView     bp;
        public TextView     hb;
        public TextView     pulse;
        public TextView     packType;
        public TextView     weight;
        public LinearLayout linearLayout;

        public ViewHolder (View itemView) {
            super (itemView);
            this.venue    = itemView.findViewById (R.id.text_venue);
            this.date     = itemView.findViewById (R.id.text_date);
            this.bp       = itemView.findViewById (R.id.text_bp);
            this.hb       = itemView.findViewById (R.id.text_hb);
            this.pulse    = itemView.findViewById (R.id.text_pulse);
            this.packType = itemView.findViewById (R.id.text_pack_type);
            this.weight   = itemView.findViewById (R.id.text_weight);
            linearLayout  = itemView.findViewById (R.id.list_item_linear_layout);
        }
    }

}
