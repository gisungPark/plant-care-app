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
import { RoomModal } from "../../components/main/RoomModal";
// import Modal from "react-native-modal";

// import PropTypes from "prop-types";
const win = Dimensions.get("window");

export function HomeScreen({ navigation }) {
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

      <View style={styles.mainicons}>
        <Icon
          type="Ionicons"
          name="notifications-outline"
          style={styles.bell}
        ></Icon>
        <Icon
          type="Ionicons"
          name="options-outline"
          style={styles.option}
        ></Icon>
      </View>
      <ScrollView style={styles.scrollview}>
        <View style={styles.mainname}>
          <Text style={styles.nametext}>Dasol House</Text>
          <Icon
            type="Ionicons"
            name="pencil-outline"
            style={styles.pencil}
            onPress={() => {
              console.log("click pencil");
            }}
          ></Icon>
        </View>
        <View style={styles.now}>
          <View style={styles.middlebox}>
            <Icon type="Ionicons" name="sunny-outline"></Icon>
            <Text style={styles.info}>sunny</Text>
            <Icon type="Ionicons" name="water-outline"></Icon>
            <Text style={styles.info}>55%</Text>
            <Icon type="Ionicons" name="thermometer-outline"></Icon>
            <Text style={styles.info}>25°C</Text>
          </View>
        </View>
        <View style={styles.add}>
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
            style={styles.plantmodal}
          >
            <RoomModal
              changeModalVisible={changeModalVisible}
              setData={setData}
            />
          </Modal>
        </View>

        <View style={styles.rooms}>
          <Text
            style={styles.roomname}
            onPress={() => {
              console.log("click room name");
              navigation.navigate("Room");
            }}
          >
            거실
          </Text>
          <View style={styles.abovecard}>
            <TouchableOpacity
              style={styles.plantcard}
              onPress={() => {
                console.log("click left");
                navigation.navigate("Room");
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
                console.log("click right");
                navigation.navigate("Room");
              }}
            >
              <Image
                source={require("../../assets/images/plant.jpg")}
                style={styles.plantimg}
              />
              <View style={styles.plantinfo}>
                <Text style={styles.plantname}>산세베리아가</Text>
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
          </View>
        </View>
        <View style={styles.rooms}>
          <Text
            style={styles.roomname}
            onPress={() => {
              console.log("click room name");
              navigation.navigate("Room");
            }}
          >
            거실
          </Text>
          <View style={styles.abovecard}>
            <TouchableOpacity
              style={styles.plantcard}
              onPress={() => {
                console.log("click left");
                navigation.navigate("Room");
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
                console.log("click right");
                navigation.navigate("Room");
              }}
            >
              <Image
                source={require("../../assets/images/plant.jpg")}
                style={styles.plantimg}
              />
              <View style={styles.plantinfo}>
                <Text style={styles.plantname}>산세베리아가</Text>
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
          </View>
        </View>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "rgba(0,0,0,0)",
    // ImageBackground:''
    flex: 0,
  },
  image: {
    flex: 1,

    // position: "absolute",
    resizeMode: "cover",
    justifyContent: "center",
  },
  option: {
    paddingLeft: 10,
    color: "white",
  },
  bell: {
    color: "white",
  },
  mainicons: {
    flexDirection: "row",
    justifyContent: "flex-end",
    paddingHorizontal: 30,
    // backgroundColor: "yellow",
    marginTop: 70,
    marginBottom: 20,
  },
  mainname: {
    // backgroundColor: "yellow",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    marginLeft: 20,
    marginTop: 35,
    marginBottom: 30,
    fontFamily: "Cochin",
  },
  nametext: {
    fontSize: 30,
    fontWeight: "bold",
    color: "white",
  },
  pencil: {
    fontSize: 20,
    fontWeight: "bold",
    color: "rgba(255,255,255,0.8)",
    backgroundColor: "rgba(0,0,0,0.05)",
    marginLeft: 10,
    padding: 2,
  },
  now: {
    flex: 0.5,
    // backgroundColor: "yellow",
    alignItems: "center",
    marginBottom: 10,
  },
  middlebox: {
    flexDirection: "row",
    backgroundColor: "rgba(0,0,0,0.1)",
    paddingHorizontal: 15,
    paddingVertical: 6,
    borderRadius: 30,
    justifyContent: "center",
    alignItems: "center",
    paddingLeft: 20,
  },
  rooms: {
    flex: 5,
  },
  info: {
    paddingHorizontal: 15,
    color: "white",
    fontWeight: "300",
  },
  add: {
    alignItems: "center",
    marginTop: 10,
  },
  addicon: {
    color: "rgba(255,255,255,0.8)",
    fontWeight: "bold",
    fontSize: 50,
  },
  roomname: {
    color: "white",
    fontSize: 18,
    fontWeight: "700",
    paddingLeft: 30,
    paddingTop: 20,
    paddingBottom: 10,
    flex: 0.4,
  },
  abovecard: {
    flex: 4,
    flexDirection: "row",
    paddingHorizontal: 20,
    // padding: 20,
  },
  plantcard: {
    flex: 5,
    margin: 3,
    height: win.height * 0.2,
    backgroundColor: "rgba(255,255,255,0.5)",
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    // alignItems: "center",
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
});
