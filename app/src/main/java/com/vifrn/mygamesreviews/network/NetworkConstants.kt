package com.vifrn.mygamesreviews.network

object NetworkConstants {
    const val API_URL = "https://api.igdb.com/v4/"
    const val AUTH_URL = "https://id.twitch.tv/oauth2/"

    const val TOKEN_ENDPOINT = "token"
    const val GAMES_ENDPOINT = "games"

    const val GRANT_TYPE = "client_credentials"

    const val FIELDS_FILTER = "fields id, name, summary, cover.*;"
    const val LIMIT_10 = "limit 10;"
    const val LIMIT_50 = "limit 50;"
}