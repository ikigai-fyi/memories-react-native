import * as React from "react";

import Router from "./src/Router";

import { AuthProvider } from "./src/store/AuthContext";

export default function App() {
  return (
    <AuthProvider>
      <Router />
    </AuthProvider>
  );
}
