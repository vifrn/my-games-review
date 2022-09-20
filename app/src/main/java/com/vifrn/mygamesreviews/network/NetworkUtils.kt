package com.vifrn.mygamesreviews.network

import com.vifrn.mygamesreviews.model.Game
import org.json.JSONArray

fun parseGamesJsonArray(jsonResult: JSONArray): Array<Game> {
    val gameList = ArrayList<Game>()

    for (i in 0 until jsonResult.length()) {
        val gameJson = jsonResult.getJSONObject(i)

        val id = gameJson.getInt("id")
        val name = gameJson.getString("name")
        val summary = gameJson.optString("summary")
        val url = gameJson.optJSONObject("cover")?.getString("url")

        val game = Game(
            id,
            name,
            summary,
            makeImageUrlForSize(url, ImageSizes.THUMB),
            makeImageUrlForSize(url, ImageSizes.COVER_BIG),
            null, /* user rating */
            null /* user review */)

        gameList.add(game)
    }
    return gameList.toTypedArray()
}

fun makeImageUrlForSize (url : String?, size : ImageSizes) : String {
    if(url == null) return ""

    val newUrl = url.replace("t_thumb", "t_${imageSizesToStringMap[size]!!}")

    return "https:$newUrl"
}

enum class ImageSizes {
    COVER_SMALL,
    COVER_BIG,
    LOGO_MED,
    SCREENSHOT_MED,
    SCREENSHOT_BIG,
    SCREENSHOT_HUGE,
    THUMB,
    MICRO,
    p720,
    p1080
}

private val imageSizesToStringMap = mapOf<ImageSizes, String>(
    ImageSizes.COVER_SMALL to "cover_small",
    ImageSizes.COVER_BIG to "cover_big",
    ImageSizes.LOGO_MED to "logo_med",
    ImageSizes.SCREENSHOT_MED to "screenshot_med",
    ImageSizes.SCREENSHOT_BIG to "screenshot_big",
    ImageSizes.SCREENSHOT_HUGE to "screenshot_huge",
    ImageSizes.THUMB to "thumb",
    ImageSizes.MICRO to "micro",
    ImageSizes.p720 to "720p",
    ImageSizes.p1080 to "1080p"
)