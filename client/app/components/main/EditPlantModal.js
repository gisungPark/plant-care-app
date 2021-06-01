import React, {Component, useEffect, useState} from 'react';
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
import RadioButtonRN from 'radio-buttons-react-native';
import {findRoom} from '../../api/room';
import {findRoomDetail} from '../../api/room';
import {myPlantEdit} from '../../api/plant';
import ImagePicker from 'react-native-image-crop-picker';
import {Littlechip, LittleMenu} from '../../assets/theme/roomstyle';
import {useDispatch} from 'react-redux';
import {changePlant} from '../../reducers/roomReducer';
// import * as ImagePicker from "expo-image-picker";
// 리액트 네이티브의 image picker 필요

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
  flex: 2;
  flex-direction: row;
  margin-top: 10px;
  margin-left: 25px;
  margin-right: 15px;
  height: 50px;
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
const EditPlantModal = props => {
  const [roomId, setRoomId] = useState([]);
  const [plantName, setPlantName] = useState(props.pname);
  const rid = props.rid;
  const startedDate = props.startedDate;
  const pid = props.pid;
  const plantimage = props.image;
  const [isSelected, setIsSelected] = useState(rid);
  const [image, setImage] = useState(plantimage);
  const takePhotoFromCamera = () => {
    ImagePicker.openCamera({
      compressImageMaxWidth: 900,
      compressImageMaxHeight: 900,
      cropping: true,
      // compressImageQuality: 0.7,
    })
      .then(image => {
        setImage(image.path);
        // changeModalVisible(true);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };
  // console.log(props);

  const choosePhotoFromLibrary = () => {
    ImagePicker.openPicker({
      width: 900,
      height: 900,
      cropping: true,
      // compressImageQuality: 0.7,
    })
      .then(image => {
        setImage(image.path);
        // changeModalVisible(true);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };

  //redux
  const dispatch = useDispatch();
  const plantchange = () => dispatch(changePlant('edit'));
  useEffect(async () => {
    await findRoom()
      .then(res => {
        setRoomId(res.data.response);
      })
      .catch(err => err);
  }, []);

  // 식물 수정 API
  const editPlant = async () => {
    const formData = new FormData();
    formData.append('pid', pid);
    formData.append('rid', isSelected);
    formData.append('nickname', plantName);
    formData.append('image', {
      uri: image,
      name: 'plant.jpg',
      type: 'image/jpeg',
    });
    formData.append('startedDate', startedDate);
    await myPlantEdit(pid, formData)
      .then(res => console.log('response', res))
      .catch(err => console.log(err));
    plantchange();
    closeModal(false, 'Cancel');
  };
  const selectRoom = () => {
    return roomId.map((theme, i) => {
      return (
        <TouchableOpacity
          key={i}
          onPress={() => setIsSelected(theme.rid)}
          style={styles.imagewrap}>
          <Image
            source={{uri: theme.theme}}
            style={isSelected === theme.rid ? styles.selected : styles.themeimg}
          />
          <Text style={{fontSize: 12}}>{theme.roomName}</Text>
        </TouchableOpacity>
      );
    });
  };

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
          {/* 방 옮기기 */}
          <View style={styles.changeroom}>
            <Littlechip>
              <Text style={styles.chiptext}>방 옮기기</Text>
            </Littlechip>
            <View
              style={{
                flexDirection: 'row',
                flexWrap: 'wrap',
                justifyContent: 'flex-start',
                paddingHorizontal: 15,
              }}>
              {selectRoom()}
            </View>
          </View>
          {/* 식물이름입력 */}
          <View style={styles.input}>
            <Littlechip>
              <Text style={styles.chiptext}>식물 이름</Text>
            </Littlechip>
            <TextInputBox>
              <TextInput
                placeholder="식물 이름"
                onChangeText={setPlantName}
                value={plantName}
              />
            </TextInputBox>
          </View>
          {/* 사진등록 */}
          <View style={styles.photo}>
            <Littlechip style={{marginTop: 10}}>
              <Text style={styles.chiptext}>사진 등록</Text>
            </Littlechip>
            <ImageArea>
              <LittleMenu
                onPress={choosePhotoFromLibrary}
                style={{marginRight: 10}}>
                <Icon
                  type="MaterialCommunityIcons"
                  name="image-multiple"
                  style={{
                    fontSize: 20,
                    color: 'rgba(0,0,0,0.7)',
                    marginRight: 10,
                  }}
                />
                <Text style={{fontSize: 10, marginTop: 1}}>사진 선택</Text>
              </LittleMenu>
              <LittleMenu onPress={takePhotoFromCamera}>
                <Icon
                  type="MaterialCommunityIcons"
                  name="camera"
                  style={{
                    fontSize: 20,
                    color: 'rgba(0,0,0,0.7)',
                    marginRight: 10,
                  }}
                />
                <Text style={{fontSize: 10, marginTop: 1}}>사진 촬영</Text>
              </LittleMenu>
            </ImageArea>
            {/* preview */}
            <View style={styles.preview}>
              <Image
                source={{
                  uri: image,
                }}
                style={{
                  height: 100,
                  width: 280,
                  margin: 10,
                  borderRadius: 5,
                  backgroundColor: 'white',
                  padding: 20,
                }}
                imageStyle={{borderRadius: 15}}></Image>
            </View>
          </View>
        </View>
        {/* 버튼 */}
        <View style={styles.button}>
          <AddButton onPress={() => editPlant()}>
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
    height: HEIGHT - 120,
    paddingTop: 10,
    width: WIDTH - 60,
    backgroundColor: '#F9F9F9',
    borderRadius: 10,
    // flex: 0.85,
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
    flex: 1,
  },
  input: {
    marginVertical: 50,
    flex: 1,
    // backgroundColor: 'yellow',
    // marginTop: 10,
  },
  photo: {
    flex: 3,
    // backgroundColor: 'green',
  },
  imagewrap: {
    width: 50,
    height: 50,
    margin: 15,
    justifyContent: 'center',
    alignItems: 'center',
  },
  themeimg: {
    width: 50,
    height: 50,
    borderRadius: 10,
    borderColor: 'transparent',
    resizeMode: 'cover',
    borderWidth: 2,
    margin: 5,
  },
  selected: {
    width: 50,
    height: 50,
    borderRadius: 10,
    borderColor: '#8AD169',
    resizeMode: 'cover',
    borderWidth: 2,
    margin: 5,
  },
  preview: {
    flexDirection: 'column',
    alignItems: 'center',
    backgroundColor: 'white',
    borderRadius: 10,
    marginTop: 5,
  },
});
export {EditPlantModal};
