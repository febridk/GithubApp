package id.febridk.github.api

import id.febridk.github.data.model.DetailUser
import id.febridk.github.data.model.User
import id.febridk.github.data.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token a555ced456898398e98b8091ed3b12c595eb9f04")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserList>

    @GET("users/{username}")
    @Headers("Authorization: token a555ced456898398e98b8091ed3b12c595eb9f04")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token a555ced456898398e98b8091ed3b12c595eb9f04")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token a555ced456898398e98b8091ed3b12c595eb9f04")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}