//
//  MemoriesWidgetBundle.swift
//  MemoriesWidget
//
//  Created by Thomas Mosmant on 21/11/2023.
//

import WidgetKit
import SwiftUI

@main
struct MemoriesWidgetBundle: WidgetBundle {
    var body: some Widget {
        MemoriesWidget()
        MemoriesWidgetLiveActivity()
    }
}
