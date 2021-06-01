import React, {useState, useEffect} from 'react';
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
  Alert,
  SafeAreaView,
  FlatList,
  ActivityIndicator,
} from 'react-native';
import {Container, Icon, Button, Content} from 'native-base';
import {RoomModal} from '../../components/main/RoomModal';
import {HomeEditModal} from '../../components/main/HomeEditModal';

import {
  createDrawerNavigator,
  DrawerContentScrollView,
  DrawerItemList,
  DrawerItem,
} from '@react-navigation/drawer';

import {useDispatch, useSelector} from 'react-redux';
import {findRoom} from '../../api/room';
import {Weather} from '../../components/main/Weather';
import {
  getCurrentPosition,
  Geolocation,
} from 'react-native-geolocation-service';
import {PermissionsAndroid} from 'react-native';
// reducer
import {room, getRoomlist} from '../../reducers/roomReducer';
import {setMain} from '../../reducers/homeReducer';

//modal
import MessageModal from '../../components/auth/Messagemodal';

// api
import {getMessage} from '../../api/auth';
import {main} from '../../api/room';

import {AnimationLoading} from '../../components/common/renderLoading';

// import Modal from "react-native-modal";

// import PropTypes from "prop-types";
const win = Dimensions.get('window');

function CustomDrawerContent(props) {
  const [myMessages, setMyMessages] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [detailMessage, setDetailMessage] = useState('');
  const [msgLength, setMsgLength] = useState(0);

  useEffect(async () => {
    const messageResponse = await getMessage();
    setMyMessages(messageResponse.data.response);
    setMsgLength(myMessages.length);
  }, [msgLength]);

  const messageDetailModal = item => {
    setDetailMessage(item);
    setModalVisible(!modalVisible);
  };

  const allMessages = data => {
    return data.map((item, i) => {
      return (
        <DrawerItem
          key={i}
          label={item.title}
          style={{backgroundColor: '#fafafa'}}
          onPress={() => messageDetailModal(item)}
        />
      );
    });
  };

  return (
    <DrawerContentScrollView {...props}>
      {/* <Text>알람목록</Text> */}
      <DrawerItemList {...props} />
      {allMessages(myMessages)}
      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}>
        <MessageModal
          setModalVisible={setModalVisible}
          message={detailMessage}
          setMsgLength={setMsgLength}
          msgLengtt={msgLength}></MessageModal>
      </Modal>
    </DrawerContentScrollView>
  );
}

function Home({navigation}) {
  // redux에서 state값 불러오기
  const {roomact} = useSelector(state => ({
    roomact: state.roomReducer.roomact,
  }));
  const {plantact} = useSelector(state => ({
    plantact: state.roomReducer.plantact,
  }));
  const [isModalVisible, setisModalVisible] = useState(false);
  const [isModalVisible2, setisModalVisible2] = useState(false);
  const [ChooseData, setChooseData] = useState();
  const [loadingStatus, setLoadingStatus] = useState(false);

  const {homename, theme, userRooms} = useSelector(state => ({
    homename: state.homeReducer.homename,
    theme: state.homeReducer.theme,
    userRooms: state.roomReducer.rooms,
  }));

  // 물주기 상태
  const {registerWaterFlag} = useSelector(state => ({
    registerWaterFlag: state.diaryReducer.registerwater,
  }));
  // 물주기 취소 상태
  const {deleteWaterFlag} = useSelector(state => ({
    deleteWaterFlag: state.diaryReducer.deletewater,
  }));

  const dispatch = useDispatch();
  const setMainInfo = (mainnickname, maintheme) =>
    dispatch(setMain(mainnickname, maintheme));
  const getRooms = rooms => dispatch(getRoomlist(rooms));

  const changeModalVisible = bool => {
    setisModalVisible(bool);
  };
  const changeModalVisible2 = bool => {
    setisModalVisible2(bool);
  };
  const setData = data => {
    setChooseData(data);
  };
  const [loading, setLoading] = useState(false);
  // 방 정보 조회
  const getRoomData = () => {
    findRoom()
      .then(res => {
        // console.log('get room data', res.data.response);
        getRooms(res.data.response);
        setLoadingStatus(false);
      })
      .then(() => {
        setLoading(false);
      })
      .catch(err => {
        // console.log(err);
        setLoading(false);
      });
  };
  // 메인 화면 정보 조회
  const getMainInfo = async () => {
    const mainResponse = await main();
    const mainInfo = mainResponse.data;
    setMainInfo(mainInfo.homeNickname, mainInfo.theme);
  };

  const [info, setInfo] = useState({
    name: 'loading !!',
    temp: 'loading',
    humidity: 'loading',
    desc: 'loading',
    icon: 'loading',
  });

  useEffect(async () => {
    setLoadingStatus(true);
    await getMainInfo();
    getRoomData();
  }, [roomact, plantact]);

  const onEndReached = () => {
    if (loading) {
      return;
    } else {
      getRoomData();
    }
  };

  // 물준날짜 있을 경우: 시간제외 날짜만 표시
  // 물준날짜 없을 경우: '아직 물을 주지 않았어요' 문구 표시
  const showLastDate = plant => {
    if (plant.lastDate != undefined) {
      return plant.lastDate.substring(0, 10);
    } else {
      return '아직 물을 주지 않았어요';
    }
  };

  useEffect(async () => {
    getRoomData();
  }, [registerWaterFlag, deleteWaterFlag]);

  // asking for location permission
  const renderItem = ({item}) => {
    return (
      <View style={styles.rooms}>
        <View style={styles.roominfo}>
          <Text
            style={styles.roomname}
            onPress={() => {
              // console.log('click room name');
              navigation.navigate('Room', {
                rid: item.rid,
                rname: item.roomName,
                plantList: item.plantList,
              });
            }}>
            {item.roomName}
          </Text>
          <Text
            style={styles.gotoroom}
            onPress={() => {
              // console.log('click room name');
              navigation.navigate('Room', {
                rid: item.rid,
                rname: item.roomName,
                plantList: item.plantList,
              });
            }}>
            {item.plantList.length}개의 식물 보러가기
          </Text>
        </View>
        {item.plantList.length > 1 ? (
          <View>
            <View style={styles.abovecard}>
              <TouchableOpacity
                style={styles.plantcard}
                onPress={() => {
                  // console.log('click left');
                  navigation.navigate('Room', {
                    rid: item.rid,
                    rname: item.roomName,
                    plantList: item.plantList,
                  });
                }}>
                <Image
                  source={{uri: item.plantList[0].image}}
                  style={styles.plantimg}
                />
                <View style={styles.plantinfo}>
                  <Text style={styles.plantname}>
                    {item.plantList[0].nickname}
                  </Text>
                  <View style={styles.rightinfo}>
                    <View style={styles.water}>
                      <Text style={styles.watertext}>물 준 날짜</Text>
                      <Text style={styles.waterdate}>
                        {showLastDate(item.plantList[0])}
                      </Text>
                    </View>
                    {/* <Image
                      source={require('../../assets/images/plant1.png')}
                      style={styles.planticon}
                    /> */}
                  </View>
                </View>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.plantcard}
                onPress={() => {
                  // console.log('click right');
                  navigation.navigate('Room', {
                    rid: item.rid,
                    rname: item.roomName,
                    plantList: item.plantList,
                  });
                }}>
                <Image
                  source={{uri: item.plantList[1].image}}
                  style={styles.plantimg}
                />
                <View style={styles.plantinfo}>
                  <Text style={styles.plantname}>
                    {item.plantList[1].nickname}
                  </Text>
                  <View style={styles.rightinfo}>
                    <View style={styles.water}>
                      <Text style={styles.watertext}>물 준 날짜</Text>
                      <Text style={styles.waterdate}>
                        {showLastDate(item.plantList[1])}
                      </Text>
                    </View>
                    {/* <Image
                      source={require('../../assets/images/plant1.png')}
                      style={styles.planticon}
                    /> */}
                  </View>
                </View>
              </TouchableOpacity>
            </View>
          </View>
        ) : (
          <View>
            {item.plantList.length == 1 ? (
              <View style={styles.abovecard}>
                <TouchableOpacity
                  style={styles.plantcard}
                  onPress={() => {
                    // console.log('click left');
                    navigation.navigate('Room', {
                      rid: item.rid,
                      rname: item.roomName,
                      plantList: item.plantList,
                      pid: item.pid,
                    });
                  }}>
                  <Image
                    source={{uri: item.plantList[0].image}}
                    style={styles.plantimg}
                  />
                  <View style={styles.plantinfo}>
                    <Text style={styles.plantname}>
                      {item.plantList[0].nickname}
                    </Text>
                    <View style={styles.rightinfo}>
                      <View style={styles.water}>
                        <Text style={styles.watertext}>물 준 날짜</Text>
                        <Text style={styles.waterdate}>
                          {showLastDate(item.plantList[0])}
                        </Text>
                      </View>
                      {/* <Image
                        source={require('../../assets/images/plant1.png')}
                        style={styles.planticon}
                      /> */}
                    </View>
                  </View>
                </TouchableOpacity>
              </View>
            ) : (
              <View style={styles.abovecard}>
                <TouchableOpacity
                  style={styles.plantcard2}
                  onPress={() => {
                    // console.log('click left');
                    navigation.navigate('Room', {
                      rid: item.rid,
                      rname: item.roomName,
                      plantList: item.plantList,
                      pid: item.pid,
                    });
                  }}>
                  <Text style={styles.gotoaddplant}>please add plant</Text>
                </TouchableOpacity>
              </View>
            )}
          </View>
        )}
      </View>
    );
  };
  return (
    <View style={{flex: 1, backgroundColor: 'transparent'}}>
      {loadingStatus ? (
        <AnimationLoading isLoading={loadingStatus} />
      ) : (
        <View style={{flex: 1, backgroundColor: 'transparent'}}>
          <View style={{flex: 0.1}}>
            <Image
              style={{
                height: win.height,
                position: 'absolute',
                top: 0,
                left: 0,
                resizeMode: 'stretch',
                width: '100%',
              }}
              source={{
                uri:
                  // 'https://ssafybucket.s3.ap-northeast-2.amazonaws.com/DEFAULT_HOME_THEME.jpg',
                  theme,
              }}
            />
          </View>
          {/* 오른쪽 상단 아이콘 */}
          <View style={styles.mainicons}>
            <TouchableOpacity onPress={() => navigation.openDrawer()}>
              <Icon
                type="Ionicons"
                name="notifications-outline"
                style={styles.bell}></Icon>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => changeModalVisible2(true)}>
              <Icon
                type="Ionicons"
                name="options-outline"
                style={styles.option}></Icon>
            </TouchableOpacity>
            <Modal
              transparent={true}
              animationType="fade"
              visible={isModalVisible2}
              nRequestClose={() => changeModalVisible2(false)}
              style={styles.plantmodal}>
              <HomeEditModal
                changeModalVisible={changeModalVisible2}
                setData={setData}
              />
            </Modal>
          </View>
          {/* 홈이름 */}
          <View style={styles.mainname}>
            <Text style={styles.nametext}>{homename}</Text>
          </View>
          {/* 날씨 */}
          <Weather />
          {/* 방추가아이콘 */}
          <View style={styles.add}>
            <TouchableOpacity onPress={() => changeModalVisible(true)}>
              <Icon
                type="Ionicons"
                name="add-circle-outline"
                style={styles.addicon}></Icon>
            </TouchableOpacity>
            <Modal
              transparent={true}
              animationType="fade"
              visible={isModalVisible}
              nRequestClose={() => changeModalVisible(false)}
              style={styles.plantmodal}>
              <RoomModal
                changeModalVisible={changeModalVisible}
                setData={setData}
              />
            </Modal>
          </View>
          {/* 방리스트 */}
          <SafeAreaView style={{flex: 1}}>
            <FlatList
              data={userRooms}
              renderItem={renderItem}
              keyExtractor={(item, index) => index.toString()}
              onEndReachedThreshold={0.9}
              onEndReached={onEndReached}
            />
          </SafeAreaView>
        </View>
      )}
    </View>
  );
}

const Drawer = createDrawerNavigator();
export function HomeScreen() {
  return (
    <Drawer.Navigator
      drawerContent={props => <CustomDrawerContent {...props} />}>
      <Drawer.Screen
        name="Feed"
        component={Home}
        options={{
          // drawerLabel: '알림목록',
          title: '알림목록',
          drawerIcon: () => null,
        }}
      />
    </Drawer.Navigator>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'rgba(0,0,0,0)',
    // ImageBackground:''
    flex: 0,
  },
  image: {
    flex: 1,
    resizeMode: 'cover',
    justifyContent: 'center',
  },
  option: {
    paddingLeft: 10,
    color: 'white',
  },
  bell: {
    color: 'white',
  },
  mainicons: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    paddingHorizontal: 30,
    // backgroundColor: "yellow",
    marginTop: 10,
    marginBottom: 0,
  },
  mainname: {
    // backgroundColor: "yellow",
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    // marginLeft: 20,
    marginTop: 35,
    marginBottom: 30,
    fontFamily: 'Cochin',
  },
  nametext: {
    fontSize: 30,
    fontWeight: 'bold',
    color: 'white',
  },
  pencil: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'rgba(255,255,255,0.8)',
    backgroundColor: 'rgba(0,0,0,0.05)',
    marginLeft: 10,
    padding: 2,
  },
  now: {
    // backgroundColor: "yellow",
    alignItems: 'center',
    marginBottom: 10,
  },
  middlebox: {
    flexDirection: 'row',
    backgroundColor: 'rgba(0,0,0,0.1)',
    paddingHorizontal: 15,
    paddingVertical: 6,
    borderRadius: 30,
    justifyContent: 'center',
    alignItems: 'center',
    paddingLeft: 20,
    height: 40,
  },
  roominfo: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  info: {
    paddingHorizontal: 15,
    color: 'white',
    fontWeight: '300',
  },
  add: {
    alignItems: 'center',
    marginTop: 10,
  },
  addicon: {
    color: 'rgba(255,255,255,0.8)',
    fontWeight: 'bold',
    fontSize: 50,
  },
  roomname: {
    color: 'white',
    fontSize: 18,
    fontWeight: '700',
    paddingLeft: 30,
    paddingTop: 20,
    // paddingBottom: 10,
    flex: 0.4,
    // marginTop: 40,
  },
  gotoroom: {
    color: 'white',
    fontSize: 12,
    fontWeight: '700',
    paddingTop: 25,
    flex: 0.4,
    // backgroundColor: 'yellow',
  },
  abovecard: {
    flexDirection: 'row',
    paddingHorizontal: 20,
    // padding: 20,
  },
  plantcard: {
    flex: 5,
    margin: 3,
    height: win.height * 0.22,
    backgroundColor: 'rgba(255,255,255,0.5)',
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    // alignItems: "center",
  },
  plantcard2: {
    flex: 5,
    margin: 3,
    height: win.height * 0.22,
    backgroundColor: 'rgba(255,255,255,0.5)',
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  plantname: {
    fontWeight: 'bold',
    fontSize: 13,
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
    flexDirection: 'row',
    // backgroundColor: "red",
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 5,
  },
  planticon: {
    height: 30,
    width: 30,
  },
  rightinfo: {
    flexDirection: 'row',
    alignItems: 'center',
    // backgroundColor: "yellow",
  },
  watertext: {
    fontSize: 11,
    fontWeight: 'bold',
  },
  waterdate: {
    fontSize: 10,
  },
  gotoaddplant: {
    fontSize: 18,
    fontWeight: '700',
    color: 'white',
  },
  rooms: {
    marginBottom: 20,
  },
  lottieimage: {
    width: '100%',
    height: '100%',
    paddingVertical: 5,
    zIndex: -1,
    position: 'absolute',
    justifyContent: 'center',
    alignSelf: 'center',
  },
});
