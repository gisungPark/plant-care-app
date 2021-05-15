import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
} from "react-native";
import { Button, Icon } from "native-base";
import styled from "styled-components";

// 글 작성 textInput 박스
const TextInputBox = styled.View`
  background-color: white;
  height: 50px;
  margin: 20px 20px 10px 20px;
  padding: 10px;
  border: 0.4px solid rgba(0, 0, 0, 0.3);
  border-radius: 10px;
`;
const AddButton = styled.TouchableOpacity`
  height: 40px;
  margin-top: 4px;
  margin-bottom: 10px;
  padding: 2px;
  justify-content: center;
  align-items: center;
  flex-direction: row;
`;
const ButtonText = styled.Text`
  font-weight: bold;
  font-size: 16px;
  margin-right: 10px;
`;

const WIDTH = Dimensions.get("window").width;
const HEIGHT = Dimensions.get("window").height;
const HEIGHT_MODAL = 300;
const RoomModal = (props) => {
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
            방 만들기
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
        <TextInputBox>
          <TextInput placeholder="방 이름" />
        </TextInputBox>
        <AddButton onPress={() => closeModal(false, "Cancel")}>
          <ButtonText>저장</ButtonText>
          <Icon
            type="Ionicons"
            name="checkmark-circle-outline"
            style={{ fontSize: 27 }}
          ></Icon>
        </AddButton>
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
});
export { RoomModal };
