package com.companyname.at.support.rest.swaggercodegen;

import static com.companyname.at.config.Constants.SERVICE_URL;

import io.swagger.client.ApiClient;
import io.swagger.client.api.StoreApi;
import java.io.IOException;
import java.util.Map;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

public class SwaggerCodeGenHttpClient {

  public ApiClient apiClient;


  public SwaggerCodeGenHttpClient() {
    apiClient = new ApiClient();
    apiClient.getAdapterBuilder()
        .baseUrl(SERVICE_URL);

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    apiClient.getOkBuilder()
        .addInterceptor(loggingInterceptor);
  }


  public Response<Map<String, Integer>> getInventory() throws IOException {
    StoreApi storeApi = apiClient.createService(StoreApi.class);
    return storeApi.getInventory().execute();
  }
}
