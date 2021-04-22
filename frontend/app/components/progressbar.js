import React from "react";
import { View } from "react-native";

const ProgressBar = ({ completed }) => {
  const containerStyles = {
    height: 5,
    width: "80%",
    backgroundColor: "#e0e0de",
    borderRadius: 50,
    margin: 50,
  };

  const fillerStyles = {
    height: "100%",
    width: `${completed}%`,
    borderRadius: 50,
    backgroundColor: "#8AD169",
  };

  return (
    <View style={containerStyles}>
      <View style={fillerStyles}></View>
    </View>
  );
};

export default ProgressBar;

//https://dev.to/ramonak/react-how-to-create-a-custom-progress-bar-component-in-5-minutes-2lcl
