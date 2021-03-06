package real_deal.bsis.config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.squareup.okhttp.internal.Internal.logger;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept (Chain chain) throws IOException {

        Request request = chain.request ();
        Long t1 = System.nanoTime ();


        logger.info (String.format ("Sending request %s on %s%n%s" , request.url () , chain.connection () , request.headers ()));


        Response response = chain.proceed (request);
        Long t2 = System.nanoTime ();
        logger.info (String.format ("Received response for %s in %.1fms%n%s" , response.request ().url () ,
                (t2 - t1) / 1e6d , response.headers () , "content length==" + response.body ().contentLength ()));


        return response;
    }
}
