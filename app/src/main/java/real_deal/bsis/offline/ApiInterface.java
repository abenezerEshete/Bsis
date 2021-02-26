package real_deal.bsis.offline;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET ("/bsis/users/login-user-details")
    Call < String > login (@Header ("Authorization") String authHeader);


    @GET ("/bsis/donors/search")
    Call < String > donersSearch (@Header ("Authorization") String authHeader ,
                                  @Query ("donationIdentificationNumber") String donationIdentificationNumber ,
                                  @Query ("firstName") String firstName ,
                                  @Query ("middleName") String middleName ,
                                  @Query ("lastName") String lastName ,
                                  @Query ("phoneNumber") String phoneNumber ,
                                  @Query ("donorNumber") String donorNumber ,
                                  @Query ("usePhraseMatch") boolean usePhraseMatch);

    @GET ("/bsis/donors/{id}")
    Call < String > donersById (@Header ("Authorization") String authHeader ,
                                @Path ("id") Long id);

    @PUT ("/bsis/donors/{id}")
    Call < String > donorsUpdate (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                                  @Path ("id") String id , @Body String json);

    @POST ("/bsis/donations")
    Call < String > addDonation (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                                 @Body String json);

    @POST ("/bsis/donors")
    Call < String > addDonor (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                              @Body String json);

    @POST ("/bsis/deferrals")
    Call < String > addDefferal (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                                 @Body String json);

    @PUT ("/bsis/deferrals/{id}")
    Call < String > deferralUpdate (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                                    @Path ("id") String id , @Body String json);


    @PUT ("bsis/donations/{id}")
    Call < String > donationUpdate (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type ,
                                    @Path ("id") String id , @Body String json);


    @GET ("/bsis/donors/form")
    Call < String > getFormChoice (@Header ("Authorization") String authHeader);


    @PUT ("/bsis/users")
    Call < String > setting (@Header ("Authorization") String authHeader , @Header ("Content-Type") String type , @Body String json);

    @GET ("bsis/returnforms/offline")
    Call < String > downloadformchoice (@Header ("Authorization") String authHeader);

    @GET ("bsis/donors/offline")
    Call < String > downloadDonors (@Header ("Authorization") String authHeader , @Query ("startDate") String firstName ,
                                    @Query ("endDate") String endDate);


}
