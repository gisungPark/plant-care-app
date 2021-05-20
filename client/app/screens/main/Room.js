import React, {useEffect, useState} from 'react';
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
  FlatList,
  SafeAreaView,
} from 'react-native';
import {Container, Icon, Button, Content} from 'native-base';
import {PlusModal} from '../../components/main/PlusModal';
import {RoomEditModal} from '../../components/main/RoomEditModal';
import {DeleteRoomModal} from '../../components/main/DeleteRoomModal';
const win = Dimensions.get('window');
import {useDispatch, useSelector} from 'react-redux';
import {findRoomDetail} from '../../api/room';
import {changeRoom} from '../../reducers/roomReducer';
import {RenderLoading} from '../../components/common/renderLoading';

export function RoomScreen({route, navigation}) {
  // const {roomact} = useSelector(state => ({
  // }));
  const {roomact, plantact, rooms} = useSelector(state => ({
    roomact: state.roomReducer.roomact,
    plantact: state.roomReducer.plantact,
    rooms: state.roomReducer.rooms,
  }));
  const [isModalVisible, setisModalVisible] = useState(false);
  const [isModalVisible2, setisModalVisible2] = useState(false);
  const [isModalVisible3, setisModalVisible3] = useState(false);
  const [ChooseData, setChooseData] = useState();
  const [title, setTitle] = useState('');
  const [roomDetail, setRoomDetail] = useState([]);
  const [roomPlantDetail, setRoomPlantDetail] = useState([]);
  const [loading, setLoading] = useState(true);
  const [roomname, setRoomname] = useState();
  const [roomtheme, setRoomtheme] = useState(
    'https://www.sketchappsources.com/resources/source-image/profile-illustration-gunaldi-yunus.png',
  );
  const changeModalVisible = bool => {
    setisModalVisible(bool);
  };
  const changeModalVisible2 = bool => {
    setisModalVisible2(bool);
  };
  const changeModalVisible3 = bool => {
    setisModalVisible3(bool);
  };
  const setData = data => {
    setChooseData(data);
  };
  console.log(ChooseData);
  // plantList 안에든거 image, lastDate, name, nickname, pid
  const {rid} = route.params;
  const {rname} = route.params;

  // 식물 정보 조회
  const getPlantData = async () => {
    if (roomact !== 'trash') {
      await findRoomDetail(rid)
        .then(res => {
          setRoomDetail(res.data.response);
          console.log('pl', res.data.response);
        })
        .then(() => {
          setLoading(false);
        })
        .catch(err => {
          console.log('err', err);
          navigation.navigate('Home');
          setLoading(false);
        });
    }
  };
  // get room name, theme from redux
  const getRoomNameTheme = async () => {
    await rooms.forEach(room => {
      if (room.rid === route.params.rid) {
        setRoomname(room.roomName);
        setRoomtheme(room.theme);
      }
    });
  };
  const dispatch = useDispatch();
  const makeclean = () => dispatch(changeRoom(''));
  useEffect(async () => {
    const check = roomact;
    if (check === 'trash') {
      // makeclean();
      await navigation.navigate('Home');
    } else {
      await getPlantData(rid);
    }
    await getRoomNameTheme();
  }, [plantact, roomact, rooms]);

  useEffect(() => {
    return () => {
      makeclean();
    };
  }, []);
  const renderItem = ({item}) => {
    return (
      <TouchableOpacity>
        {item.dead ? (
          <TouchableOpacity
            style={styles.plantcard}
            onPress={() => {
              navigation.navigate('PlantDetail', {
                pid: item.pid,
                pname: item.nickname,
                rid: rid,
                rname: rname,
              });
            }}>
            <Image source={{uri: item.image}} style={styles.plantimg} />
            <View style={styles.plantinfo}>
              <Text style={styles.plantname}>{item.nickname}</Text>
              <View style={styles.rightinfo}>
                <View style={styles.water}>
                  <Text style={styles.watertext}>물 준 날짜</Text>
                  <Text style={styles.waterdate}>{item.lastDate}</Text>
                </View>
                <Image
                  source={require('../../assets/images/plant1.png')}
                  style={styles.planticon}
                />
              </View>
            </View>
          </TouchableOpacity>
        ) : (
          <TouchableOpacity
            style={styles.plantcard2}
            onPress={() => {
              navigation.navigate('PlantDetail', {
                pid: item.pid,
                pname: item.nickname,
                rid: rid,
                rname: rname,
              });
            }}>
            <Image source={{uri: item.image}} style={styles.plantimg} />
            <View style={styles.plantinfo}>
              <Text style={styles.plantname}>{item.nickname}</Text>
              <View style={styles.rightinfo}>
                <View style={styles.water}>
                  <Text style={styles.watertext}>물 준 날짜</Text>
                  <Text style={styles.waterdate}>{item.lastDate}</Text>
                </View>
                <Image
                  source={require('../../assets/images/plant1.png')}
                  style={styles.planticon}
                />
              </View>
            </View>
          </TouchableOpacity>
        )}
      </TouchableOpacity>
    );
  };
  return (
    <View style={{flex: 1, backgroundColor: 'transparent'}}>
      {loading ? (
        <RenderLoading isLoading={loading} />
      ) : (
        <View style={{flex: 1}}>
          {/* 배경사진 */}
          <View style={{flex: 0.1}}>
            <Image
              style={{
                height: win.height,
                position: 'absolute',
                top: 0,
                left: 0,
                width: '100%',
              }}
              // source={require('../../assets/images/mainroom.jpg')}
              source={{uri: roomtheme}}
            />
          </View>
          <View style={{flexDirection: 'row', marginTop: 10}}>
            {/* 방 이름 */}
            <TouchableOpacity
              style={styles.roomname}
              onPress={() => {
                navigation.navigate('Home');
              }}>
              <Icon
                type="Ionicons"
                name="chevron-back-outline"
                style={{color: 'white', fontSize: 20, paddingRight: 8}}></Icon>
              {/* <Text style={styles.roomtext}>{roomDetail.roomName}</Text> */}
              <Text style={styles.roomtext}>{roomname}</Text>
            </TouchableOpacity>
            <View style={styles.setting}>
              <TouchableOpacity onPress={() => changeModalVisible3(true)}>
                <Icon
                  type="Ionicons"
                  name="trash-outline"
                  style={{
                    color: 'white',
                    fontSize: 25,
                    paddingRight: 15,
                  }}></Icon>
              </TouchableOpacity>
              <Modal
                transparent={true}
                animationType="fade"
                visible={isModalVisible3}
                nRequestClose={() => changeModalVisible3(false)}
                style={styles.plantmodal}>
                <DeleteRoomModal
                  changeModalVisible={changeModalVisible3}
                  setData={setData}
                  rid={rid}
                  rname={rname}
                />
              </Modal>
              <TouchableOpacity onPress={() => changeModalVisible2(true)}>
                <Icon
                  type="Ionicons"
                  name="options-outline"
                  style={{
                    color: 'white',
                    fontSize: 25,
                    paddingRight: 15,
                  }}></Icon>
              </TouchableOpacity>
              <Modal
                transparent={true}
                animationType="fade"
                visible={isModalVisible2}
                nRequestClose={() => changeModalVisible2(false)}
                style={styles.plantmodal}>
                <RoomEditModal
                  changeModalVisible={changeModalVisible2}
                  setData={setData}
                  roomid={rid}
                />
              </Modal>
            </View>
          </View>
          {/* 식물 목록 */}
          <View style={styles.plantlist}>
            <SafeAreaView style={{flex: 1}}>
              <FlatList
                data={roomDetail.plantList}
                renderItem={renderItem}
                keyExtractor={(item, index) => index.toString()}
              />
            </SafeAreaView>
          </View>
          <TouchableOpacity style={styles.pluscard}>
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
              nRequestClose={() => changeModalVisible(false)}>
              <PlusModal
                changeModalVisible={changeModalVisible}
                setData={setData}
                rid={rid}></PlusModal>
            </Modal>
          </TouchableOpacity>
        </View>
      )}
    </View>
  );
}
const styles = StyleSheet.create({
  roomname: {
    flexDirection: 'row',
    paddingHorizontal: 15,
    marginTop: 25,
    marginBottom: 20,
    alignItems: 'center',
    flex: 1,
  },
  setting: {
    flexDirection: 'row',
    marginTop: 25,
    marginBottom: 20,
    alignItems: 'center',
    justifyContent: 'flex-end',
    paddingRight: 20,
    flex: 1,
    // backgroundColor: "yellow",
  },
  plantlist: {
    flex: 6,
    paddingHorizontal: 15,
  },
  roomtext: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 30,
  },
  plantcard: {
    margin: 3,
    height: win.height * 0.3,
    backgroundColor: 'rgba(255,255,255,0.5)',
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    // alignItems: "center",
  },
  plantcard2: {
    margin: 3,
    height: win.height * 0.3,
    backgroundColor: 'rgba(0,0,0,0.3)',
    borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    // alignItems: "center",
  },
  pluscard: {
    height: win.height * 0.09,
    backgroundColor: 'rgba(255,255,255,0.5)',
    // borderRadius: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  plantname: {
    fontWeight: 'bold',
    fontSize: 11,
  },
  plantimg: {
    height: 150,
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
    fontSize: 9,
    fontWeight: 'bold',
  },
  waterdate: {
    fontSize: 9,
  },
  pencil: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'rgba(255,255,255,0.8)',
    backgroundColor: 'rgba(0,0,0,0.05)',
    marginLeft: 10,
    padding: 2,
  },
  addicon: {
    fontSize: 40,
    color: 'rgba(255,255,255,0.7)',
  },
});
