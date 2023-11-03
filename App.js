import * as React from "react";
import * as WebBrowser from "expo-web-browser";
import { makeRedirectUri, useAuthRequest } from "expo-auth-session";
import { Button, View, StyleSheet } from "react-native";

WebBrowser.maybeCompleteAuthSession();

const discovery = {
  authorizationEndpoint: "https://www.strava.com/oauth/mobile/authorize",
  tokenEndpoint: "https://www.strava.com/oauth/token",
  revocationEndpoint: "https://www.strava.com/oauth/deauthorize",
};

export default function App() {
  const [request, response, promptAsync] = useAuthRequest(
    {
      clientId: process.env.EXPO_PUBLIC_STRAVA_CLIENT_ID,
      scopes: ["activity:read_all"],
      redirectUri: makeRedirectUri({
        native: process.env.EXPO_PUBLIC_STRAVA_REDIRECT_URL,
      }),
    },
    discovery
  );

  React.useEffect(() => {
    if (response?.type === "success") {
      const { code } = response.params;

      console.log(code);
    }
  }, [response]);

  return (
    <View style={styles.container}>
      <Button
        disabled={!request}
        title="Login"
        onPress={() => {
          promptAsync();
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    flex: 1,
    justifyContent: "center",
  },
});
