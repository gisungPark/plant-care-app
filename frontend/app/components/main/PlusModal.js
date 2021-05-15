import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
  Image,
} from "react-native";
import { Button, Icon } from "native-base";
import styled from "styled-components";
import { Littlechip } from "../../assets/theme/roomstyle";
import RadioButtonRN from "radio-buttons-react-native";
import ImagePickerExample from "./PhotoUpload";
import * as ImagePicker from "expo-image-picker";

const data = [{ label: "거실" }, { label: "욕실" }];
const img_data = [
  { uri: "../../assets/images/mainroom.jpg" },
  { uri: "../../assets/images/yellowplant.jpg" },
];

const TextInputBox = styled.View`
  background-color: white;
  height: 50px;
  margin: 20px 25px 20px 25px;
  padding: 10px;
  border: 0.4px solid rgba(0, 0, 0, 0.3);
  border-radius: 10px;
`;
const AddButton = styled.TouchableOpacity`
  height: 40px;
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

// 사진 선택 영역 컨테이너
const ImageArea = styled.TouchableOpacity`
  flex: 1;
  flex-direction: row;
  margin-top: 10px;
  margin-left: 15px;
  margin-right: 15px;
`;

// 사진 선택 영역 박스
const ImageBox = styled.TouchableOpacity`
  flex: 1;
  background-color: white;
  margin: 10px;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
`;

const WIDTH = Dimensions.get("window").width;
const HEIGHT = Dimensions.get("window").height;
const HEIGHT_MODAL = 300;
const PlusModal = (props) => {
  let openImagePickerAsync = async () => {
    let permissionResult = await ImagePicker.requestMediaLibraryPermissionsAsync();

    if (permissionResult.granted === false) {
      alert("Permission to access camera roll is required!");
      return;
    }

    let pickerResult = await ImagePicker.launchImageLibraryAsync();
    console.log(pickerResult);
  };
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };
  return (
    <TouchableOpacity disabled={true} style={styles.container}>
      <View style={styles.modal}>
        {/* close 버튼 */}
        <View style={styles.modaltop}>
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
        {/* 내용 */}
        <View style={styles.content}>
          {/* 방 옮기기 */}
          <View style={styles.changeroom}>
            <Littlechip>
              <Text style={styles.chiptext}>방 옮기기</Text>
            </Littlechip>
            <View style={styles.buttons}>
              <Image
                source={{
                  uri:
                    "http://cereshome.co.kr/web/product/small/20200420/659ff6db3048df1a413a053655c22ebb.jpg",
                }}
                style={{ flex: 1 }}
              ></Image>
              <RadioButtonRN
                data={data}
                activeColor={"#B7CDBC"}
                boxDeactiveBgColor={"transparent"}
                boxActiveBgColor={"#EFEFEF"}
                boxStyle={styles.optionlong}
                textStyle={styles.optiontext}
                circleSize={14}
                icon={
                  <Icon
                    type="Ionicons"
                    name="checkmark-circle-outline"
                    style={{
                      fontSize: 23,
                      color: "#B7CDBC",
                    }}
                  ></Icon>
                }
                style={{
                  flex: 1,
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              ></RadioButtonRN>
            </View>
          </View>
          {/* 식물이름입력 */}
          <View style={styles.input}>
            <Littlechip>
              <Text style={styles.chiptext}>식물 이름</Text>
            </Littlechip>
            <TextInputBox style={{ marginBottom: 30 }}>
              <TextInput placeholder="식물 이름" />
            </TextInputBox>
          </View>
          {/* 사진등록 */}
          <View style={styles.photo}>
            <Littlechip>
              <Text style={styles.chiptext}>사진 등록</Text>
            </Littlechip>
            <ImageArea>
              <ImageBox onPress={openImagePickerAsync}>
                <Icon
                  type="MaterialCommunityIcons"
                  name="image-multiple"
                  style={{ fontSize: 30, color: "rgba(0,0,0,0.7)" }}
                />
                <Text style={{ fontSize: 13, marginTop: 8 }}>사진 선택</Text>
              </ImageBox>
              <ImageBox>
                <Icon
                  type="MaterialCommunityIcons"
                  name="camera"
                  style={{ fontSize: 30, color: "rgba(0,0,0,0.7)" }}
                />
                <Text style={{ fontSize: 13, marginTop: 8 }}>사진 촬영</Text>
              </ImageBox>
            </ImageArea>
          </View>
        </View>
        {/* 버튼 */}
        <View style={styles.button}>
          <AddButton onPress={() => closeModal(false, "Cancel")}>
            <ButtonText>저장</ButtonText>
            <Icon
              type="Ionicons"
              name="checkmark-circle-outline"
              style={{ fontSize: 20 }}
            ></Icon>
          </AddButton>
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
    backgroundColor: "rgba(0,0,0,0.4)",
  },
  modal: {
    height: HEIGHT - 200,
    paddingTop: 10,
    width: WIDTH - 60,
    backgroundColor: "#F9F9F9",
    borderRadius: 10,
  },
  modaltop: {
    flexDirection: "row",
    justifyContent: "flex-end",
    alignItems: "center",
    paddingVertical: 6,
    paddingHorizontal: 14,
    // backgroundColor: "rgba(52,176,80,0.1)",
    width: WIDTH - 80,
    marginLeft: 20,
    flex: 1,
  },
  content: {
    flex: 9,
  },
  chiptext: {
    fontWeight: "bold",
    fontSize: 13,
  },
  optionlong: {
    marginHorizontal: 5,
    flex: 0.5,
    borderRadius: 10,
    borderWidth: 2,
    paddingVertical: 20,
  },
  optiontext: {
    marginLeft: 50,
    backgroundColor: "transparent",
    fontWeight: "600",
  },
  buttons: {
    margin: 20,
    justifyContent: "center",
    marginBottom: 80,
  },
  button: {
    marginTop: 15,
    marginBottom: 20,
  },
  changeroom: {
    flex: 2,
  },
  input: {
    flex: 2,
  },
  photo: {
    flex: 3,
  },
});
export { PlusModal };
