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
import { PlusModal } from "../../components/main/PlusModal";
const win = Dimensions.get("window");

export function RoomScreen({ navigation }) {
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
      <View style={{ flex: 0.1 }}>
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
      <View style={{ flexDirection: "row", marginTop: 40 }}>
        <TouchableOpacity
          style={styles.roomname}
          onPress={() => {
            navigation.navigate("Home");
          }}
        >
          <Icon
            type="Ionicons"
            name="chevron-back-outline"
            style={{ color: "white", fontSize: 20, paddingRight: 8 }}
          ></Icon>
          <Text style={styles.roomtext}>거실</Text>
          <Icon
            type="Ionicons"
            name="pencil-outline"
            style={styles.pencil}
            onPress={() => {
              console.log("click pencil");
            }}
          ></Icon>
        </TouchableOpacity>
        <View style={styles.setting}>
          <Icon
            type="Ionicons"
            name="trash-outline"
            style={{ color: "white", fontSize: 25, paddingRight: 15 }}
          ></Icon>
          <Icon
            type="Ionicons"
            name="options-outline"
            style={{ color: "white", fontSize: 25, paddingRight: 8 }}
          ></Icon>
        </View>
      </View>
      <View style={styles.plantlist}>
        <TouchableOpacity
          style={styles.plantcard}
          onPress={() => {
            navigation.navigate("PlantDetail");
          }}
        >
          <Image
            source={require("../../assets/images/plant.jpg")}
            style={styles.plantimg}
          />
          <View style={styles.plantinfo}>
            <Text style={styles.plantname}>산세베리아</Text>
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
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.plantcard}
          onPress={() => {
            navigation.navigate("PlantDetail");
          }}
        >
          <Image
            source={require("../../assets/images/plant.jpg")}
            style={styles.plantimg}
          />
          <View style={styles.plantinfo}>
            <Text style={styles.plantname}>산세베리아</Text>
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
        </TouchableOpacity>
        <TouchableOpacity style={styles.pluscard}>
          <TouchableOpacity onPress={() => changeModalVisible(true)}>
            <Icon
              type="Ionicons"
              name="add-circle-outline"
              style={styles.addicon}
            ></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible}
            nRequestClose={() => changeModalVisible(false)}
          >
            <PlusModal
              changeModalVisible={changeModalVisible}
              setData={setData}
            ></PlusModal>
          </Modal>
        </TouchableOpacity>
      </View>
    </View>
  );
}
const styles = StyleSheet.create({
  roomname: {
    flexDirection: "row",
    paddingHorizontal: 15,
    marginTop: 25,
    marginBottom: 20,
    alignItems: "center",
    flex: 1,
  },
  setting: {
    flexDirection: "row",
    marginTop: 25,
    marginBottom: 20,
    alignItems: "center",
    justifyContent: "flex-end",
    paddingRight: 20,
    flex: 1,
    // backgroundColor: "yellow",
  },
  plantlist: {
    flex: 6,
    paddingHorizontal: 15,
  },
  roomtext: {
    color: "white",
    fontWeight: "bold",
    fontSize: 30,
  },
  plantcard: {
    margin: 3,
    height: win.height * 0.2,
    backgroundColor: "rgba(255,255,255,0.5)",
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    // alignItems: "center",
  },
  pluscard: {
    margin: 3,
    height: win.height * 0.2,
    backgroundColor: "rgba(255,255,255,0.5)",
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    alignItems: "center",
    justifyContent: "center",
  },
  plantname: {
    fontWeight: "bold",
    fontSize: 11,
  },
  plantimg: {
    height: 100,
    width: null,
    // marginTop: 10,
    borderRadius: 10,
    // marginHorizontal: 10,
    // alignItems: "center",
  },
  plantinfo: {
    flexDirection: "row",
    // backgroundColor: "red",
    justifyContent: "space-between",
    alignItems: "center",
    marginTop: 5,
  },
  planticon: {
    height: 30,
    width: 30,
  },
  rightinfo: {
    flexDirection: "row",
    alignItems: "center",
    // backgroundColor: "yellow",
  },
  watertext: {
    fontSize: 9,
    fontWeight: "bold",
  },
  waterdate: {
    fontSize: 9,
  },
  pencil: {
    fontSize: 20,
    fontWeight: "bold",
    color: "rgba(255,255,255,0.8)",
    backgroundColor: "rgba(0,0,0,0.05)",
    marginLeft: 10,
    padding: 2,
  },
  addicon: {
    fontSize: 50,
    color: "rgba(255,255,255,0.7)",
  },
});
