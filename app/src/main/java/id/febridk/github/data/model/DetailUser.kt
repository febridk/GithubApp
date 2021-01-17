package id.febridk.github.data.model

data class DetailUser(
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val following: Int = 0,
    val followers: Int = 0
)