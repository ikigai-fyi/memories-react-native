import { createContext, useContext, useState, useEffect } from "react";
import { AuthRequest, makeRedirectUri } from "expo-auth-session";
import * as SecureStore from "expo-secure-store";
import * as WebBrowser from "expo-web-browser";

const AuthContext = createContext(null);
WebBrowser.maybeCompleteAuthSession();

const discovery = {
  authorizationEndpoint: "https://www.strava.com/oauth/mobile/authorize",
  tokenEndpoint: "https://www.strava.com/oauth/token",
  revocationEndpoint: "https://www.strava.com/oauth/deauthorize",
};

export function AuthProvider({ children }) {
  const [authState, setAuthState] = useState({
    isLoading: false,
    session: null,
  });

  useEffect(() => {
    const restoreSession = async () => {
      setAuthState({ isLoading: true, session: null });

      try {
        const session = await JSON.parse(
          await SecureStore.getItemAsync("session")
        );

        setAuthState({ isLoading: false, session });
      } catch {
        setAuthState({ isLoading: false, session: null });
      }
    };
    restoreSession();
  }, []);

  const login = async () => {
    setAuthState({ isLoading: true, session: null });
    try {
      const stravaRequest = new AuthRequest({
        clientId: process.env.EXPO_PUBLIC_STRAVA_CLIENT_ID,
        scopes: ["activity:read_all,profile:read_all"],
        redirectUri: makeRedirectUri({
          native: process.env.EXPO_PUBLIC_STRAVA_REDIRECT_URL,
          preferLocalhost: true,
        }),
      });

      const stravaResponse = await stravaRequest.promptAsync(discovery);

      if (stravaResponse?.type === "success") {
        const memoriesReponse = await fetch(
          "https://api-dev.ikigai.fyi/rest/auth/login/strava",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              code: stravaResponse.params.code,
              scope: "activity:read_all,profile:read_all",
            }),
          }
        );

        const session = await memoriesReponse.json();

        console.log(session);

        await SecureStore.setItemAsync("session", JSON.stringify(session));

        setAuthState({ isLoading: false, session });
      }
    } catch (e) {
      alert(e);
      setAuthState({ isLoading: false, session: null });
    }
  };

  const logout = async () => {
    setAuthState({ isLoading: true });
    await SecureStore.deleteItemAsync("session");
    setAuthState({ isLoading: false, session: null });
  };

  const value = {
    onLogin: login,
    onLogout: logout,
    authState,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
