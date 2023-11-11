import { View, StyleSheet } from "react-native";
import { useAuth } from "../store/AuthContext";
import { useEffect } from "react";

export default function HomeScreen() {
  const { authState, onLogout } = useAuth();

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
    fetchActivity();
  }, []);

  return <View style={styles.container}></View>;
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    flex: 1,
    justifyContent: "center",
  },
});
