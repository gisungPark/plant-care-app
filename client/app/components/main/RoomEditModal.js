import React, {useState, useEffect} from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
  Image,
} from 'react-native';
import {Button, Icon} from 'native-base';
import styled from 'styled-components';
import {Littlechip} from '../../assets/theme/roomstyle';
import RadioButtonRN from 'radio-buttons-react-native';
import ImagePicker from 'react-native-image-crop-picker';

import {useDispatch, useSelector} from 'react-redux';
import {roomNameTheme} from '../../reducers/roomReducer';

import {themes} from '../../assets/theme/roomTheme';
// import * as ImagePicker from "expo-image-picker";
// 리액트 네이티브의 image picker 필요
import {changeRoomNameTheme} from '../../api/room';

const data = [{label: '거실'}, {label: '욕실'}];
const img_data = [
  {uri: '../../assets/images/mainroom.jpg'},
  {uri: '../../assets/images/yellowplant.jpg'},
];

const TextInputBox = styled.View`
  background-color: white;
  height: 50px;
  margin: 20px 25px 20px 25px;
  padding-left: 10px;
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

const WIDTH = Dimensions.get('window').width;
const HEIGHT = Dimensions.get('window').height;
const HEIGHT_MODAL = 300;
const RoomEditModal = props => {
  // take photo, choose photo
  const [image, setImage] = useState(
    'http://www.pngall.com/wp-content/uploads/5/Profile-PNG-Clipart.png',
  );

  const dispatch = useDispatch();
  const updateRoomNameTheme = (roomid, roomname, roomtheme) =>
    dispatch(roomNameTheme(roomid, roomname, roomtheme));

  const {rooms} = useSelector(state => ({
    rooms: state.roomReducer.rooms,
  }));

  const [roomname, setRoomname] = useState();
  const [isSelected, setIsSelected] = useState();

  useEffect(async () => {
    await rooms.forEach(room => {
      if (room.rid === props.roomid) {
        setRoomname(room.roomName);
        setIsSelected(room.theme);
      }
    });
  }, [rooms]);

  const themeImages = () => {
    return themes.map((theme, i) => {
      return (
        <TouchableOpacity
          key={i}
          onPress={() => setIsSelected(theme.address)}
          style={styles.imagewrap}>
          <Image
            source={{
              uri: theme.address,
            }}
            style={
              isSelected === theme.address ? styles.selected : styles.themeimg
            }
          />
        </TouchableOpacity>
      );
    });
  };

  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };

  const updateChange = async () => {
    const params = {
      roomName: roomname,
      theme: isSelected,
    };
    const roomResponse = await changeRoomNameTheme(
      props.roomid,
      roomname,
      isSelected,
    );
    updateRoomNameTheme(props.roomid, roomname, isSelected);
  };

  const setData = data => {
    setChooseData(data);
  };
  bs = React.createRef();
  return (
    <TouchableOpacity disabled={true} style={styles.container}>
      <View style={styles.modal}>
        {/* close 버튼 */}
        <View style={styles.modaltop}>
          <TouchableOpacity
            onPress={() => closeModal(false, 'Cancel')}
            style={styles.closebtn}>
            <Icon
              type="Ionicons"
              name="close-outline"
              style={{color: 'green', fontSize: 40}}></Icon>
          </TouchableOpacity>
        </View>
        {/* 내용 */}
        <View style={styles.content}>
          {/* 식물이름입력 */}
          <View style={styles.input}>
            <Littlechip>
              <Text style={styles.chiptext}>방 이름 변경</Text>
            </Littlechip>
            <TextInputBox style={{marginBottom: 10}}>
              <TextInput
                placeholder={roomname}
                onChangeText={userRn => setRoomname(userRn)}
              />
            </TextInputBox>
          </View>
          {/* 사진등록 */}
          <View style={styles.photo}>
            <Littlechip>
              <Text style={styles.chiptext}>방 테마 선택</Text>
            </Littlechip>
            <View
              style={{
                flexDirection: 'row',
                flexWrap: 'wrap',
                justifyContent: 'flex-start',
                paddingHorizontal: 15,
              }}>
              {themeImages()}
            </View>
          </View>
        </View>
        {/* 버튼 */}
        <View style={styles.button}>
          <AddButton
            onPress={() => (closeModal(false, 'Cancel'), updateChange())}>
            <ButtonText>저장</ButtonText>
            <Icon
              type="Ionicons"
              name="checkmark-circle-outline"
              style={{fontSize: 20}}></Icon>
          </AddButton>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.4)',
  },
  modal: {
    height: HEIGHT - 200,
    paddingTop: 10,
    width: WIDTH - 60,
    backgroundColor: '#F9F9F9',
    borderRadius: 10,
  },
  modaltop: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
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
    fontWeight: 'bold',
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
    backgroundColor: 'transparent',
    fontWeight: '600',
  },
  buttons: {
    margin: 20,
    justifyContent: 'center',
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
  imagewrap: {
    width: 50,
    height: 50,
    margin: 5,
  },
  themeimg: {
    width: '100%',
    height: '100%',
    borderRadius: 10,
    borderColor: 'transparent',
    resizeMode: 'cover',
    borderWidth: 2,
  },
  selected: {
    width: '100%',
    height: '100%',
    borderRadius: 10,
    resizeMode: 'cover',
    borderColor: '#8AD169',
    borderWidth: 2,
  },
});
export {RoomEditModal};
