import React from "react";
import { TouchableOpacity, Text, Image, View, StyleSheet } from "react-native";
import { useAuth } from "../store/AuthContext";

export default function LoginScreen() {
  const { onLogin } = useAuth();

  return (
    <View style={styles.container}>
      <View style={styles.logoContainer}>
        <Image
          source={require("../../assets/ikigai.png")}
          style={styles.logo}
        />
        <View style={styles.textContainer}>
          <Text style={styles.titleText}>Memories</Text>
          <Text style={[styles.titleText, styles.mirroredText]}>Strava</Text>
        </View>
      </View>
      <TouchableOpacity onPress={onLogin}>
        <Image
          source={require("../../assets/strava_button.png")}
          style={styles.buttonImage}
        />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    justifyContent: "space-between",
    flex: 1,
    marginBottom: 100,
  },
  logoContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  logo: {
    maxHeight: 130,
    maxWidth: 130,
    borderRadius: 25,
  },
  textContainer: {
    marginTop: 6,
    marginLeft: 12,
  },
  titleText: {
    fontSize: 32,
    fontWeight: "900",
  },
  mirroredText: {
    marginTop: -14,
    fontSize: 30,
    transform: [{ rotate: "180deg" }, { scaleX: -1 }],
  },
  buttonImage: {
    maxHeight: 64,
    resizeMode: "contain",
  },
});
