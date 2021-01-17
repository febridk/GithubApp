package id.febridk.github.api

import id.febridk.github.BuildConfig
import id.febridk.github.data.model.DetailUser
import id.febridk.github.data.model.User
import id.febridk.github.data.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val GITHUB_API_KEY = BuildConfig.GithubAPIKEY

interface Api {

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_API_KEY")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserList>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_API_KEY")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_API_KEY")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_API_KEY")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}