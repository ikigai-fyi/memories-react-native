import {
  Text,
  View,
  StyleSheet,
  NativeModules,
  ImageBackground,
  Linking,
} from "react-native";
import { format } from "../lib/activity";
import { useAuth } from "../store/AuthContext";
import { useRef, useState, useEffect } from "react";
import AwesomeButton from "react-native-really-awesome-button";
import GridSkeleton from "../ui/GridSkeleton";
import LottieView from "lottie-react-native";

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

  const onRefresh = async (next) => {
    await fetchActivity(true);
    confettiRef.current?.play(0);
    next();
  };

  const onOpenStrava = async (next) => {
    await Linking.openURL(
      `https://strava.com/activities/${widgetValue.stravaId}`
    );
    next();
  };

  const confettiRef = useRef(null);

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

      <View style={styles.buttonsRow}>
        <AwesomeButton
          style={styles.button}
          progress
          onPress={onRefresh}
          activityColor="white"
          backgroundActive="#cc4200"
          backgroundColor="#fc5201"
        >
          Refresh activity
        </AwesomeButton>

        <AwesomeButton
          style={styles.button}
          onPress={onOpenStrava}
          activityColor="white"
          backgroundColor="#FC1A00"
        >
          Open in Strava
        </AwesomeButton>
      </View>

      <View style={styles.lottie} pointerEvents="none">
        <LottieView
          ref={confettiRef}
          source={require("../../assets/confetti.json")}
          autoPlay={false}
          loop={false}
          style={styles.lottie}
          resizeMode="cover"
        />
      </View>
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
  lottie: {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 1000,
  },
  button: {
    margin: 12,
  },
  buttonsRow: {
    flexDirection: "row",
  },
});
