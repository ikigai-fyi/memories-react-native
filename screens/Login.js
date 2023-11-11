import * as React from "react";

import { TouchableOpacity, Text, Image, View, StyleSheet } from "react-native";
import { useAuth } from "../store/AuthContext";

export default function LoginScreen({ navigation }) {
  const { onLogin } = useAuth();

  return (
    <View style={styles.container}>
      <View style={styles.titleContainer}>
        <Image source={require("../assets/ikigai.png")} style={styles.icon} />
        <Text style={styles.title}>Memories</Text>
        <Text style={styles.subTitle}>Widgets for Strava</Text>
      </View>
      <TouchableOpacity onPress={onLogin}>
        <Image
          source={require("../assets/strava_button.png")}
          style={styles.stravaButton}
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
    marginTop: 150,
    marginBottom: 100,
  },
  titleContainer: {
    alignItems: "center",
    gap: 8,
  },
  icon: {
    maxHeight: 164,
    maxWidth: 164,
    borderRadius: 41,
  },
  title: {
    fontSize: 30,
    fontWeight: "bold",
  },
  subTitle: { fontSize: 15, fontWeight: "bold" },
  stravaButton: { maxHeight: 50, resizeMode: "contain" },
});
