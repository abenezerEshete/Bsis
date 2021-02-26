package real_deal.bsis.config;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;

import real_deal.bsis.add_donor_fragments.Find_donor;
import real_deal.bsis.donor_detail.Defferals;
import real_deal.bsis.donor_detail.Demographics;
import real_deal.bsis.donor_detail.DonationTab;
import real_deal.bsis.donor_detail.Overview;
import real_deal.bsis.main_fragment.Donate_blood;
import real_deal.bsis.main_fragment.DonationButch;
import real_deal.bsis.main_fragment.Donation_list;
import real_deal.bsis.main_fragment.Home;
import real_deal.bsis.main_fragment.Offline;
import real_deal.bsis.main_fragment.Setting;

public abstract class commonImplimentationClass extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener, Offline.OnFragmentInteractionListener,
        Setting.OnFragmentInteractionListener, Home.OnFragmentInteractionListener,
        Donate_blood.OnFragmentInteractionListener, Donation_list.OnFragmentInteractionListener,
        DonationButch.OnFragmentInteractionListener,
        Overview.OnFragmentInteractionListener, Demographics.OnFragmentInteractionListener,
        DonationTab.OnFragmentInteractionListener, Defferals.OnFragmentInteractionListener,
        Find_donor.OnFragmentInteractionListener {
}
