package real_deal.bsis.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orm.query.Select;

import java.util.List;

import real_deal.bsis.Adapter.BatchesDetailAdapter;
import real_deal.bsis.R;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;

public class BatchDetail extends AppCompatActivity {
    RecyclerView  recyclerView;
    DonationBatch donationBatch;
    TextView      countText;
    TextView textVenueTitle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button
        setContentView (R.layout.activity_batch_detail);

        Button addDonation = findViewById (R.id.add_donation);
        recyclerView = findViewById (R.id.donation_list);
        countText    = findViewById (R.id.text_number_of_result);
        textVenueTitle = findViewById (R.id.text_venue_name);



        Bundle bundle = getIntent ().getExtras ();
        donationBatch = bundle.getParcelable ("donation_batch");


        donations ();
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        addDonation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent (BatchDetail.this , AddDonationFromBatch.class);
                Bundle bundle = new Bundle ();
                intent.putExtra ("batchNumber" , donationBatch.getBatchNumber ());
                startActivity (intent);
            }
        });
    }

    private void donations () {

        // = Donation.find(Donation.class,"donation_batch = ?",donationBatch.getId()+"");
        List < Donation > donations =
                Select.from (Donation.class).where ("donation_batch = " + donationBatch.getId ()).list ();


        countText.setText ("Number of Donations: " + donations.size ());
        if(donationBatch != null && donationBatch.getVenue () != null)
        textVenueTitle.setText ("Venue : "+donationBatch.getVenue ().getName ());

        recyclerView.setAdapter (new BatchesDetailAdapter (donations , BatchDetail.this));

    }


    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

}
