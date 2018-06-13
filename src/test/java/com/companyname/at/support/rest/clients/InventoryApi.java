package com.companyname.at.support.rest.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface InventoryApi {

    @RequestLine("GET /store/inventory")
    @Headers({"Content-Type: application/json"})
    Response getInventory();
}