import {
  Button,
  View,
  StyleSheet,
  TextInput,
  NativeModules,
} from "react-native";
import { useState, useEffect } from "react";
import { useAuth } from "../store/AuthContext";

const { RNSharedWidget } = NativeModules;

export default function HomeScreen() {
  const { authState, onLogout } = useAuth();
  const [widgetValue, setWidgetValue] = useState("vide");

  const onSubmit = () => {
    RNSharedWidget.setData(
      "json",
      JSON.stringify({ value: widgetValue }),
      (status) => {
        console.log("START ============");
        console.log(status);
        console.log("END ============");
      }
    );
    console.log("widgetValue = ", widgetValue);
  };

  useEffect(() => {
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
    };
    // fetchActivity();
  }, []);

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.textInput}
        value={widgetValue}
        onChangeText={(value) => setWidgetValue(value)}
      />
      <Button title="Submit" onPress={onSubmit} />

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
