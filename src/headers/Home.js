import { Image, Text, View, StyleSheet, TouchableOpacity } from "react-native";
import { useAuth } from "../store/AuthContext";

export default function HomeHeader() {
  const { authState, onLogout } = useAuth();

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Memories</Text>
      <TouchableOpacity style={styles.avatar} onPress={onLogout}>
        <Image
          style={styles.image}
          source={{ uri: authState.session.athlete.picture_url }}
          resizeMode="contain"
        />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    flex: 1,
    justifyContent: "space-between",
    flexDirection: "row",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
  },
  avatar: { marginRight: 32 },
  image: {
    borderRadius: 30,
    width: 32,
    height: 32,
  },
});
