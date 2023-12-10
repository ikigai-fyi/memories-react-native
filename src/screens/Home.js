import {
  Button,
  Text,
  View,
  StyleSheet,
  NativeModules,
  ImageBackground,
} from "react-native";
import { useState, useEffect } from "react";
import { useAuth } from "../store/AuthContext";
import GridSkeleton from "../ui/GridSkeleton";
import { format } from "../lib/activity";

const { ReactBridge } = NativeModules;

export default function HomeScreen() {
  const { authState } = useAuth();
  const [widgetValue, setWidgetValue] = useState({});

  const fetchActivity = async (refresh = false) => {
    const response = await fetch(
      `https://api-dev.ikigai.fyi/rest/memories/current?refresh=${refresh}`,
      {
        headers: {
          Authorization: `Bearer ${authState.session.jwt}`,
        },
      }
    );

    const activity = format((await response.json()).activity);

    ReactBridge.setData(JSON.stringify(activity), () => {});

    setWidgetValue(activity);
  };

  useEffect(() => {
    fetchActivity();
  }, []);

  const onRefresh = async () => {
    await fetchActivity(true);
  };

  return (
    <View style={styles.container}>
      <GridSkeleton />
      <View style={styles.bordered}>
        <ImageBackground
          source={{ uri: widgetValue?.picture }}
          resizeMode="cover"
          style={styles.preview}
        >
          <View style={styles.textContainer}>
            <Text style={styles.nameStyle}>{widgetValue.name}</Text>
            <Text style={styles.textStyle}>{widgetValue.time}</Text>
            <Text style={styles.textStyle}>
              {widgetValue.distance} {widgetValue.elevation}
            </Text>
          </View>
        </ImageBackground>
      </View>
      <Button title="Refresh" onPress={onRefresh} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 10,
  },
  bordered: {
    borderRadius: 20,
    overflow: "hidden",
    elevation: 5,
    marginBottom: 40,
  },
  preview: {
    width: 300,
    height: 250,
    justifyContent: "flex-end",
    borderRadius: 20,
    overflow: "hidden",
    backgroundColor: "grey",
  },
  textContainer: {
    padding: 10,
    borderBottomLeftRadius: 20,
    borderBottomRightRadius: 20,
  },
  nameStyle: {
    fontWeight: "bold",
    fontSize: 18,
    fontFamily: "sans-serif",
    color: "white",
  },
  textStyle: {
    fontSize: 16,
    fontFamily: "sans-serif",
    color: "white",
  },
});
