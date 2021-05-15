import React, { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  ImageBackground,
  Image,
  TouchableOpacity,
  ScrollView,
  Dimensions,
  Modal,
} from "react-native";
import { Container, Icon, Button, Content } from "native-base";
import { Cardback, Plantchip } from "../../assets/theme/roomstyle";
import { LinearGradient } from "expo-linear-gradient";
import { PlantModal } from "./PlantModal";

const win = Dimensions.get("window");

export function PlantDetail({ navigation }) {
  const [isModalVisible, setisModalVisible] = useState(false);
  const [ChooseData, setChooseData] = useState();
  const changeModalVisible = (bool) => {
    setisModalVisible(bool);
  };
  const setData = (data) => {
    setChooseData(data);
  };
  return (
    <View style={{ flex: 1, backgroundColor: "transparent" }}>
      <View style={{ flex: 0.7 }}>
        <Image
          style={{
            height: win.height,
            position: "absolute",
            top: 0,
            left: 0,
          }}
          source={require("../../assets/images/mainroom.jpg")}
        />
      </View>
      <Cardback style={{ flex: 8 }}>
        <View
          style={{
            flexDirection: "row",
            justifyContent: "space-between",
            margin: 10,
            alignItems: "center",
          }}
        >
          {/* <Text>{ChooseData}</Text> */}
          <TouchableOpacity onPress={() => changeModalVisible(true)}>
            <Icon type="Ionicons" name="ellipsis-vertical-outline"></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible}
            nRequestClose={() => changeModalVisible(false)}
            style={styles.plantmodal}
          >
            <PlantModal
              changeModalVisible={changeModalVisible}
              setData={setData}
            />
          </Modal>

          <TouchableOpacity
            onPress={() => {
              navigation.navigate("Room");
            }}
          >
            <Icon type="Ionicons" name="close-circle-outline"></Icon>
          </TouchableOpacity>
        </View>
        <Image
          source={require("../../assets/images/yellowplant.jpg")}
          style={styles.plantimg}
        />
        <View
          style={{
            flexDirection: "row",
            justifyContent: "space-between",
            height: 100,
          }}
        >
          <View style={styles.leftside}>
            <Plantchip style={{ flexDirection: "row", alignItems: "center" }}>
              <Text
                style={{
                  color: "#29582C",
                  fontWeight: "bold",
                  marginRight: 5,
                  fontSize: 20,
                }}
              >
                |
              </Text>
              <Text style={{ color: "#29582C", fontWeight: "bold" }}>
                Lemon Tree
              </Text>
            </Plantchip>
            <View style={styles.leftdown}>
              <Text style={styles.plantname}>레모나</Text>
              <Text style={styles.startdate}>2021/04/20~</Text>
              <Text style={styles.plantdesc}>
                레몬가지를 꽃병에 꽂아놨어요 레몬 먹고 싶다 레몬은 셔요 레몬은
                바보에요
              </Text>
            </View>
          </View>
          <View
            style={{
              alignItems: "flex-end",
              marginRight: 14,
              justifyContent: "space-between",
              flex: 1,
            }}
          >
            <TouchableOpacity style={styles.diarybtn}>
              <Text
                style={{
                  color: "white",
                  fontWeight: "bold",
                  marginTop: 0,
                  fontSize: 10,
                  textAlign: "center",
                  // backgroundColor: "yellow",
                }}
              >
                DIARY
              </Text>
              <Icon
                type="Ionicons"
                name="book-outline"
                style={{
                  color: "white",
                  fontSize: 20,
                  padding: 0,
                }}
              ></Icon>
            </TouchableOpacity>
            <View style={styles.rightinfo}>
              <View style={styles.water}>
                <Text style={styles.watertext}>물 준 날짜</Text>
                <Text style={styles.waterdate}>2021/02/11</Text>
              </View>
              <Image
                source={require("../../assets/images/plant1.png")}
                style={styles.planticon}
              />
            </View>
          </View>
        </View>
      </Cardback>
    </View>
  );
}

const styles = StyleSheet.create({
  plantimg: {
    height: win.height * 0.45,
    width: null,
    borderRadius: 10,
    marginHorizontal: 10,
    marginTop: 10,
  },
  planticon: {
    height: 30,
    width: 30,
  },
  leftside: {
    padding: 5,
    flex: 1,
  },
  leftdown: {
    marginLeft: 10,
  },
  plantname: {
    fontWeight: "bold",
    fontSize: 17,
    marginBottom: 1,
  },
  startdate: {
    color: "rgba(0,0,0,0.5)",
    fontSize: 12,
  },
  plantdesc: {
    marginTop: 10,
    fontSize: 12,
    width: 160,
    fontWeight: "200",
    // backgroundColor: "yellow",
  },
  rightinfo: {
    flexDirection: "row",
    marginTop: 70,
    alignItems: "flex-end",
    justifyContent: "flex-end",
    // backgroundColor: "green",
  },
  watertext: {
    fontSize: 12,
    fontWeight: "bold",
  },
  waterdate: {
    fontSize: 11,
  },
  plantmodal: {
    marginTop: 40,
  },
  diarybtn: {
    flexDirection: "row",
    backgroundColor: "#29582C",
    paddingHorizontal: 10,
    paddingVertical: 3,
    justifyContent: "space-between",
    alignItems: "center",
    width: win.width * 0.25,
    borderRadius: 10,
    marginTop: 16,
  },
});
