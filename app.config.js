const widgetConfig = {
  // Paths to all custom fonts used in all widgets
  widgets: [
    {
      name: "MemoriesWidget",
      label: "Memorieso",
      minWidth: "320dp",
      minHeight: "120dp",
      description: "Widgets for Stravao",
      previewImage: "./assets/widget-preview.png",
      updatePeriodMillis: 1800000,
    },
  ],
};

export default ({ config }) => ({
  ...config,
  name: "Memories",
  plugins: [["react-native-android-widget", widgetConfig]],
});
