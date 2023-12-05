import { Button, Text, View, StyleSheet, NativeModules } from "react-native";
import { useState, useEffect } from "react";
import { useAuth } from "../store/AuthContext";
import { format } from "../objects";

const { ReactBridge } = NativeModules;

export default function HomeScreen() {
  const { authState, onLogout } = useAuth();
  const [widgetValue, setWidgetValue] = useState({});

  const fetchActivity = async () => {
    const response = await fetch(
      "https://api-dev.ikigai.fyi/rest/activities/random",
      {
        headers: {
          Authorization: `Bearer ${authState.session.jwt}`,
        },
      }
    );

    const activity = await response.json();

    ReactBridge.setData(JSON.stringify(format(activity)), () => {});

    setWidgetValue(activity);
  };

  useEffect(() => {
    fetchActivity();
  }, []);

  const onRefresh = async () => {
    await fetchActivity();
  };

  return (
    <View style={styles.container}>
      {/* <TextInput
        style={styles.textInput}
        value="widgetValue"
        onChangeText={(value) => setWidgetValue(value)}
      /> */}
      <Text style={styles.textInput}>{widgetValue.name}</Text>
      <Button title="Refresh" onPress={onRefresh} />

      {/* <WidgetPreviewScreen /> */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    flex: 1,
    justifyContent: "center",
  },
  textInput: {
    backgroundColor: "white",
    padding: 12,
  },
});
