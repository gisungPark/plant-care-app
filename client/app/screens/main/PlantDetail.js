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
} from 'react-native';
import {Container, Icon, Button, Content} from 'native-base';
import {Cardback, Plantchip} from '../../assets/theme/roomstyle';
import {PlantModal} from '../../components/main/PlantModal';
import {myPlantInfo} from '../../api/plant';
import {useDispatch, useSelector} from 'react-redux';
import {changePlant} from '../../reducers/roomReducer';

const win = Dimensions.get('window');

export function PlantDetail({route, navigation}) {
  const {plantact, rooms} = useSelector(state => ({
    plantact: state.roomReducer.plantact,
    rooms: state.roomReducer.rooms,
  }));

  // 물주기 상태
  const {registerWaterFlag} = useSelector(state => ({
    registerWaterFlag: state.diaryReducer.registerwater,
  }));
  // 물주기 취소 상태
  const {deleteWaterFlag} = useSelector(state => ({
    deleteWaterFlag: state.diaryReducer.deletewater,
  }));

  const [roomtheme, setRoomtheme] = useState(
    'https://www.sketchappsources.com/resources/source-image/profile-illustration-gunaldi-yunus.png',
  );
  const [isModalVisible, setisModalVisible] = useState(false);
  const [ChooseData, setChooseData] = useState();
  const [myInfo, setMyInfo] = useState([]);
  const changeModalVisible = bool => {
    setisModalVisible(bool);
  };
  const setData = data => {
    setChooseData(data);
  };
  const {pid} = route.params;
  const {pname} = route.params;
  const {rid} = route.params;
  const {rname} = route.params;
  const dispatch = useDispatch();
  const makeclean = () => dispatch(changePlant(''));

  useEffect(async () => {
    // console.log('plnatdetail props', route.params.rid);
    if (plantact === 'back') {
      makeclean();
      navigation.navigate('Room', {rid: rid, rname: rname});
    }
    await rooms.forEach(room => {
      // console.log('rrr', room);
      if (room.rid === route.params.rid) {
        // console.log(room.theme);
        setRoomtheme(room.theme);
      }
    });

    const plantDetail = await myPlantInfo(pid);
    setMyInfo(plantDetail.data);
  }, [plantact]);

  useEffect(async () => {
    const plantDetail = await myPlantInfo(pid);
    setMyInfo(plantDetail.data);
  }, [registerWaterFlag, deleteWaterFlag]);

  // 물준날짜 있을 경우: 시간제외 날짜만 표시
  // 물준날짜 없을 경우: '아직 물을 주지 않았어요' 문구 표시
  const showLastDate = () => {
    if (myInfo.lastDate != undefined) {
      return myInfo.lastDate.substring(0, 10);
    } else {
      return '아직 물을 주지 않았어요';
    }
  };

  return (
    <View style={{flex: 1, backgroundColor: 'transparent'}}>
      <View style={{flex: 0.7}}>
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
      <Cardback style={{flex: 10}}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            margin: 10,
            alignItems: 'center',
          }}>
          <TouchableOpacity onPress={() => changeModalVisible(true)}>
            <Icon type="Ionicons" name="ellipsis-vertical-outline"></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible}
            nRequestClose={() => changeModalVisible(false)}
            style={styles.plantmodal}>
            <PlantModal
              changeModalVisible={changeModalVisible}
              setData={setData}
              rid={rid}
              pid={pid}
              pname={pname}
              startedDate={myInfo.startedDate}
              image={myInfo.image}
            />
          </Modal>

          <TouchableOpacity
            onPress={() => {
              navigation.navigate('Room');
            }}>
            <Icon type="Ionicons" name="close-circle-outline"></Icon>
          </TouchableOpacity>
        </View>
        <Image source={{uri: myInfo.image}} style={styles.plantimg} />
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            height: 100,
          }}>
          <View style={styles.leftside}>
            <Plantchip style={{flexDirection: 'row', alignItems: 'center'}}>
              <Text
                style={{
                  color: '#29582C',
                  fontWeight: 'bold',
                  marginRight: 5,
                  fontSize: 15,
                }}>
                |
              </Text>
              <Text style={styles.common}>{myInfo.common}</Text>
            </Plantchip>
            <View style={styles.leftdown}>
              <Text style={styles.plantname}>{myInfo.nickname}</Text>
              <Text style={styles.startdate}>{myInfo.startedDate}</Text>
              <View style={{flexDirection: 'row', marginTop: 10}}>
                <Text style={styles.plantdesc}>적정온도</Text>
                <Text style={styles.plantdesc2}>{myInfo.temp}</Text>
              </View>
              <View style={{flexDirection: 'row'}}>
                <Text style={styles.plantdesc}>적정습도</Text>
                <Text style={styles.plantdesc2}>{myInfo.humid}</Text>
              </View>
              <View style={{flexDirection: 'row'}}>
                <Text style={styles.plantdesc}>급수주기</Text>
                <Text style={styles.plantdesc2}>{myInfo.water}</Text>
              </View>
            </View>
          </View>
          <View
            style={{
              alignItems: 'flex-end',
              marginRight: 14,
              justifyContent: 'space-between',
              flex: 1,
            }}>
            <TouchableOpacity
              style={styles.diarybtn}
              onPress={() => {
                navigation.navigate('Diary');
              }}>
              <Text
                style={{
                  color: 'white',
                  fontWeight: 'bold',
                  marginTop: 0,
                  fontSize: 10,
                  textAlign: 'center',
                  // backgroundColor: "yellow",
                }}>
                DIARY
              </Text>
              <Icon
                type="Ionicons"
                name="book-outline"
                style={{
                  color: 'white',
                  fontSize: 20,
                  padding: 0,
                }}></Icon>
            </TouchableOpacity>
            <View style={styles.rightinfo}>
              <View style={styles.water}>
                <Text style={styles.watertext}>물 준 날짜</Text>
                <Text style={styles.waterdate}>{showLastDate()}</Text>
              </View>
              {/* <Image
                source={require('../../assets/images/plant1.png')}
                style={styles.planticon}
              /> */}
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
    fontWeight: 'bold',
    fontSize: 17,
    marginBottom: 1,
  },
  startdate: {
    color: 'rgba(0,0,0,0.5)',
    fontSize: 12,
  },
  plantdesc: {
    fontSize: 12,
    width: 70,
    fontWeight: '700',
    // backgroundColor: "yellow",
  },
  plantdesc2: {
    // marginTop: 2,
    fontSize: 12,
    width: 70,
    fontWeight: '200',
    // backgroundColor: "yellow",
  },
  rightinfo: {
    flexDirection: 'row',
    marginTop: 70,
    alignItems: 'flex-end',
    justifyContent: 'flex-end',
    // backgroundColor: "green",
  },
  watertext: {
    fontSize: 12,
    fontWeight: 'bold',
  },
  waterdate: {
    fontSize: 11,
  },
  plantmodal: {
    marginTop: 40,
  },
  diarybtn: {
    flexDirection: 'row',
    backgroundColor: '#29582C',
    paddingHorizontal: 10,
    paddingVertical: 3,
    justifyContent: 'space-between',
    alignItems: 'center',
    width: win.width * 0.25,
    borderRadius: 10,
    marginTop: 16,
  },
  common: {
    color: '#29582C',
    width: 170,
    fontSize: 11,
    fontWeight: 'bold',
    // backgroundColor: 'yellow',
  },
});
