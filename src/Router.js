import { NavigationContainer } from "@react-navigation/native";

import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { useAuth } from "./store/AuthContext";

import HomeScreen from "./screens/Home";
import ProfileScreen from "./screens/Profile";
import LoadingScreen from "./screens/Loading";
import LoginScreen from "./screens/Login";

import HomeHeader from "./headers/Home";

const Stack = createNativeStackNavigator();

export default Router = function () {
  const { authState } = useAuth();

  if (authState.isLoading) {
    return <LoadingScreen />;
  }

  if (!authState.session || !authState.session.jwt) {
    return <LoginScreen />;
  }

  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{
            headerTitle: (props) => <HomeHeader {...props} />,
            headerTitleStyle: { flex: 1, textAlign: "center" },
          }}
        />
        <Stack.Screen name="Profile" component={ProfileScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};
