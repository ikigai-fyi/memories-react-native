package fyi.ikigai.memories

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class StravaActivity(
    @Serializable val activity: Activity,
    val type: String,
)

@Serializable
data class Activity @OptIn(ExperimentalSerializationApi::class) constructor(
    val city: String,
    @JsonNames("distance_in_meters") val distanceInMeters: Long,
    @JsonNames("elapsed_time_in_seconds") val elapsedTimeInSeconds: Long,
    @JsonNames("has_custom_name") val hasCustomName: Boolean,
    val name: String,
    @JsonNames("picture_url") val pictureUrl: String,
    val polyline: String,
    @JsonNames("sport_type") val sportType: String,
    @JsonNames("start_datetime") val startDatetime: String,
    @JsonNames("strava_id") val stravaId: String,
    @JsonNames("total_elevation_gain_in_meters") val totalElevationGainInMeters: Long,
)
