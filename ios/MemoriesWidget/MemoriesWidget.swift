//
//  MemoriesWidget.swift
//  MemoriesWidget
//
//  Created by Thomas Mosmant on 21/11/2023.
//

import WidgetKit
import SwiftUI

struct Provider: AppIntentTimelineProvider {
  func placeholder(in context: Context) -> SimpleEntry {
    return SimpleEntry(date: Date(), configuration: ConfigurationAppIntent(), activity: previewActivity)
  }
  
  func snapshot(for configuration: ConfigurationAppIntent, in context: Context) async -> SimpleEntry {
    return SimpleEntry(date: Date(), configuration: configuration, activity: previewActivity)
  }
  
  func timeline(for configuration: ConfigurationAppIntent, in context: Context) async -> Timeline<SimpleEntry> {
    var entries: [SimpleEntry] = []
    let userDefaults = UserDefaults.init(suiteName: "group.ikigai.Memories")
    let jsonText = userDefaults!.value(forKey: "activity") as? String
    let jsonData = Data(jsonText?.utf8 ?? "".utf8)
    let activity = try! JSONDecoder().decode(Activity.self, from: jsonData)
    
    // Generate a timeline consisting of five entries an hour apart, starting from the current date.
    let currentDate = Date()
    for hourOffset in 0 ..< 5 {
      let entryDate = Calendar.current.date(byAdding: .hour, value: hourOffset, to: currentDate)!
      let entry = SimpleEntry(date: entryDate, configuration: configuration, activity: activity)
      entries.append(entry)
    }
    
    return Timeline(entries: entries, policy: .atEnd)
  }
}

struct Activity: Codable {
  let city: String
  let distance_in_meters: Int
  let elapsed_time_in_seconds: Int
  let has_custom_name: Bool
  let name: String
  let picture_url: String
  let polyline: String
  let sport_type: String
  let start_datetime: String
  let strava_id: Int
  let total_elevation_gain_in_meters: Int
}

struct SimpleEntry: TimelineEntry {
  let date: Date
  let configuration: ConfigurationAppIntent
  let activity: Activity
}

struct MemoriesWidgetEntryView : View {
  var entry: Provider.Entry
  
  var body: some View {
    VStack {
      Text("Time:")
      Text(entry.date, style: .time)
      
      Text("Favorite Emoji:")
      Text(entry.configuration.favoriteEmoji)
    }
  }
}

struct MemoriesWidget: Widget {
  let kind: String = "MemoriesWidget"
  
  var body: some WidgetConfiguration {
    AppIntentConfiguration(kind: kind, intent: ConfigurationAppIntent.self, provider: Provider()) { entry in
      MemoriesWidgetEntryView(entry: entry)
        .containerBackground(.fill.tertiary, for: .widget)
    }
  }
}

extension ConfigurationAppIntent {
  fileprivate static var smiley: ConfigurationAppIntent {
    let intent = ConfigurationAppIntent()
    intent.favoriteEmoji = "😀"
    return intent
  }
  
  fileprivate static var starEyes: ConfigurationAppIntent {
    let intent = ConfigurationAppIntent()
    intent.favoriteEmoji = "🤩"
    return intent
  }
}

let previewActivity = Activity(city: "Ville-la-Grand",
                               distance_in_meters: 18202,
                               elapsed_time_in_seconds: 3513,
                               has_custom_name: true,
                               name: "Y a des tonnes de moucherons ",
                               picture_url: "https://image.mux.com/qWlqO02dPrDlT57sEU3f9qFWfzJkclo8QXyHBDHgRFW00/thumbnail.jpg?width=337&height=600&fit_mode=preserve&time=0",
                               polyline: "osef",
                               sport_type: "Ride",
                               start_datetime: "2022-08-20T10:17:57",
                               strava_id: 7668878718,
                               total_elevation_gain_in_meters: 157)

#Preview(as: .systemSmall) {
  MemoriesWidget()
} timeline: {
  SimpleEntry(date: .now, configuration: .smiley, activity: previewActivity)
  SimpleEntry(date: .now, configuration: .starEyes, activity: previewActivity)
}
