import { registerRootComponent } from "expo";
import { registerWidgetTaskHandler } from "react-native-android-widget";

import App from "./App";
import { widgetTaskHandler } from "./widget/WidgetTaskHandler";

// registerRootComponent calls AppRegistry.registerComponent('main', () => App);
// It also ensures that whether you load the app in Expo Go or in a native build,
// the environment is set up appropriately
import registerRootComponent from "expo/build/launch/registerRootComponent";

registerWidgetTaskHandler(widgetTaskHandler);
registerRootComponent(App);
