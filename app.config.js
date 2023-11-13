const widgetConfig = {
  // Paths to all custom fonts used in all widgets
  widgets: [
    {
      name: "Memories", // This name will be the **name** with which we will reference our widget.
      label: "Memories", // Label shown in the widget picker
      minWidth: "320dp",
      minHeight: "120dp",
      description: "Widgets for Strava", // Description shown in the widget picker
      previewImage: "./assets/widget-preview.png", // Path to widget preview image

      // How often, in milliseconds, that this AppWidget wants to be updated.
      // The task handler will be called with widgetAction = 'UPDATE_WIDGET'.
      // Default is 0 (no automatic updates)
      // Minimum is 1800000 (30 minutes == 30 * 60 * 1000).
      updatePeriodMillis: 1800000,
    },
  ],
};

export default ({ config }) => ({
  ...config,
  name: "Memories",
  plugins: [["react-native-android-widget", widgetConfig]],
});
