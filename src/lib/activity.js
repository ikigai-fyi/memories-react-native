import { timeElapsed } from "./date";

export function format(activity) {
  return {
    name: activity.name,
    time: timeElapsed(new Date(activity.start_datetime)),
    distance: `${parseFloat(
      (activity.distance_in_meters / 1000).toFixed(2)
    )}km`,
    elevation: `${activity.total_elevation_gain_in_meters}m`,
    type: activity.sport_type,
    picture: activity.picture_url,
    stravaId: activity.strava_id,
  };
}
