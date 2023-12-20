import React from "react";
import { View, StyleSheet, Dimensions } from "react-native";
import { LinearGradient } from "expo-linear-gradient";

const windowWidth = Dimensions.get("window").width;
const squareSize = windowWidth / 6;

const GridSkeleton = () => {
  const rows = new Array(6).fill(0);
  const cols = new Array(4).fill(0);

  return (
    <LinearGradient
      colors={[
        "rgba(224, 224, 224, 1)",
        "rgba(224, 224, 224, 0.2)",
        "rgba(224, 224, 224, 1)",
      ]}
      style={styles.gridContainer}
      start={{ x: 0, y: 0 }}
      end={{ x: 0, y: 1 }}
    >
      {rows.map((_, rowIndex) => (
        <View key={rowIndex} style={styles.row}>
          {cols.map((_, colIndex) => (
            <View key={colIndex} style={styles.square} />
          ))}
        </View>
      ))}
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  gridContainer: {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    padding: 30,
    justifyContent: "space-between",
    alignContent: "space-between",
  },
  row: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignContent: "space-between",
  },
  square: {
    width: squareSize,
    height: squareSize,
    backgroundColor: "#e0e0e0",
    borderRadius: 20,
  },
});

export default GridSkeleton;
