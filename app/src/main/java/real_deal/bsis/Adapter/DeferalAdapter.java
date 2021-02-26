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
import real_deal.bsis.donor_detail.Defferals;
import real_deal.bsis.model.DonorDeferral;

public class DeferalAdapter extends RecyclerView.Adapter < DeferalAdapter.ViewHolder > {


    Context           context;
    DateformatControl dateformatControl;
    private List < DonorDeferral > donorDeferrals;

    // RecyclerView recyclerView;
    public DeferalAdapter (List < DonorDeferral > listdata , Context context) {
        this.donorDeferrals = listdata;
        this.context        = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View listItem = layoutInflater.inflate (R.layout.list_item_deferal , parent , false);
        ViewHolder viewHolder = new ViewHolder (listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder , final int position) {
        dateformatControl = new DateformatControl ();
        final DonorDeferral deferral = donorDeferrals.get (position);
        if (deferral.getVenue () != null)
            holder.venue.setText ("Venue:  " + deferral.getVenue ().getName ());
        holder.created.setText ("Created:  " + dateformatControl.convertToShortDate (deferral.getDeferralDate ()));
        holder.end.setText ("End:  " + dateformatControl.convertToShortDate (deferral.getDeferredUntil ()));
        holder.comment.setText ("Comment:  " + deferral.getDeferralReasonText ());
        if (deferral.getDeferralReason () != null)
            holder.reason.setText ("Reason:  " + deferral.getDeferralReason ().getReason ());
        //     holder.user.setText ( "User:  " + deferral.getVoidedBy () );


        Log.d ("onBindViewHolder" , "onBindViewHolder+++++++++" + position);
        holder.linearLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                new Defferals ().onItemSelected (deferral , context , position);
                Toast.makeText (view.getContext () , "click on item: " + deferral.getId () , Toast.LENGTH_LONG).show ();
            }
        });
    }


    @Override
    public int getItemCount () {
        return donorDeferrals.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView     venue;
        public TextView     created;
        public TextView     end;
        public TextView     reason;
        public TextView     comment;
        //       public TextView     user;
        public LinearLayout linearLayout;

        public ViewHolder (View itemView) {
            super (itemView);
            this.venue   = itemView.findViewById (R.id.text_venue);
            this.created = itemView.findViewById (R.id.text_created);
            this.end     = itemView.findViewById (R.id.text_end);
            this.reason  = itemView.findViewById (R.id.text_reason);
            this.comment = itemView.findViewById (R.id.text_comment);
            //       this.user    = itemView.findViewById ( R.id.text_user );
            linearLayout = itemView.findViewById (R.id.list_item_linear_layout);
        }
    }

}
