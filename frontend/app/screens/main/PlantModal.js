import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Dimensions,
} from "react-native";
import { Icon } from "native-base";
const WIDTH = Dimensions.get("window").width;
const HEIGHT = Dimensions.get("window").height;
const HEIGHT_MODAL = 300;
const PlantModal = (props) => {
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };
  return (
    <TouchableOpacity disabled={true} style={styles.container}>
      <View style={styles.modal}>
        <View style={styles.modaltop}>
          <Text
            style={{
              color: "rgba(0,0,0,0.5)",
              fontWeight: "bold",
              marginLeft: 10,
            }}
          >
            SETTINGS
          </Text>
          <TouchableOpacity
            onPress={() => closeModal(false, "Cancel")}
            style={styles.closebtn}
          >
            <Icon
              type="Ionicons"
              name="close-outline"
              style={{ color: "green", fontSize: 40 }}
            ></Icon>
          </TouchableOpacity>
        </View>
        <View style={styles.btnlist}>
          <TouchableOpacity style={styles.btn}>
            <Text style={styles.btntext}>식물 정보 수정</Text>
            <Icon
              style={styles.btnicon}
              type="Ionicons"
              name="create-outline"
            ></Icon>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btn}>
            <Text style={styles.btntext}>떠나간 식물</Text>
            <Icon
              style={styles.btnicon}
              type="Ionicons"
              name="heart-dislike-circle-outline"
            ></Icon>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btn3}>
            <Text style={styles.btntext3}>식물 삭제</Text>
            <Icon
              style={styles.btnicon3}
              type="Ionicons"
              name="trash-bin-outline"
            ></Icon>
          </TouchableOpacity>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "rgba(0,0,0,0.2)",
  },
  modal: {
    // height: HEIGHT - 100,
    paddingTop: 10,
    width: WIDTH - 120,
    backgroundColor: "white",
    borderRadius: 10,
    flex: 0.31,
  },
  modaltop: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingVertical: 6,
    paddingHorizontal: 14,
    // backgroundColor: "rgba(52,176,80,0.1)",
    width: WIDTH - 120,
  },
  btnlist: {
    marginTop: 5,
    backgroundColor: "#F9F9F9",
    margin: 15,
    borderRadius: 10,
  },
  btn: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    borderBottomColor: "rgba(0,0,0,0.5)",
    borderBottomWidth: 0.3,
  },
  btn3: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  btntext: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: "bold",
    color: "rgba(0,0,0,0.8)",
    fontSize: 13,
  },
  btnicon: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: "bold",
    color: "rgba(0,0,0,0.8)",
    fontSize: 17,
  },
  btntext3: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: "bold",
    color: "#BA2F2F",
    fontSize: 13,
  },
  btnicon3: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: "bold",
    color: "#BA2F2F",
    fontSize: 17,
  },
});
export { PlantModal };
