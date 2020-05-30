package com.xcodeassociated.commons.keycloak;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenApi {

    @FormUrlEncoded
    @POST("token")
    Single<TokenData> login(@Field("username") String username,
                            @Field("password") String password,
                            @Field("client_id") String clientId,
                            @Field("grant_type") String grantType);

}
