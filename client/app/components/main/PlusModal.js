import React, {Component} from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
  Image,
  ImageBackground,
  Modal,
} from 'react-native';
import {Button, Icon} from 'native-base';
import styled from 'styled-components';
import {Littlechip, LittleMenu} from '../../assets/theme/roomstyle';
import RadioButtonRN from 'radio-buttons-react-native';
import DatePicker from 'react-native-date-picker';
import {useState} from 'react';
import ImagePicker from 'react-native-image-crop-picker';
import {PlantIdentification} from './PlantIdentification';
import {myPlantRegister} from '../../api/plant';
import {useDispatch} from 'react-redux';
import {changeRoom} from '../../reducers/roomReducer';
import {changePlant} from '../../reducers/roomReducer';

import {plantRecognition} from '../../api/plant';

const data = [{label: '거실'}, {label: '욕실'}];
const img_data = [
  {uri: '../../assets/images/mainroom.jpg'},
  {uri: '../../assets/images/yellowplant.jpg'},
];

const TextInputBox = styled.View`
  background-color: white;
  height: 50px;
  margin: 10px 25px 20px 25px;
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
const PlusModal = props => {
  // roomchange
  const dispatch = useDispatch();
  const roomchange = () => dispatch(changeRoom());
  const plantchange = () => dispatch(changePlant('register'));
  // rid
  const rid = props.rid;
  // take photo, choose photo
  const [image, setImage] = useState(
    // 'http://www.pngall.com/wp-content/uploads/5/Profile-PNG-Clipart.png',
    'https://i.pinimg.com/564x/08/56/13/0856131b7c2615b7c55e63a33ea32444.jpg',
  );
  const takePhotoFromCamera = () => {
    ImagePicker.openCamera({
      compressImageMaxWidth: 900,
      compressImageMaxHeight: 900,
      cropping: true,
      // compressImageQuality: 0.7,
    })
      .then(image => {
        console.log(image);
        setImage(image.path);
        changeModalVisible(true);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };

  const choosePhotoFromLibrary = () => {
    ImagePicker.openPicker({
      width: 900,
      height: 900,
      cropping: true,
      // compressImageQuality: 0.7,
    })
      .then(image => {
        console.log(image);
        setImage(image.path);
        changeModalVisible(true);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };
  const [date, setDate] = useState(new Date());

  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };
  const [isModalVisible, setisModalVisible] = useState(false);
  const [ChooseData, setChooseData] = useState();

  const setData = data => {
    setChooseData(data);
  };
  const changeModalVisible = bool => {
    setisModalVisible(bool);
  };
  const [plantName, setPlantName] = useState('');
  // 식물 조회 후 등록
  const plusplant = async () => {
    console.log('choosedata', ChooseData);
    if (ChooseData !== undefined) {
      var dd = date.getDate();
      var mm = date.getMonth() + 1;
      var yyyy = date.getFullYear();
      if (dd < 10) {
        dd = '0' + dd;
      }
      if (mm < 10) {
        mm = '0' + mm;
      }
      yyyy = yyyy.toString();
      mm = mm.toString();
      dd = dd.toString();
      var ndate = yyyy + '-' + mm + '-' + dd;

      const formData = new FormData();
      // formData.append('pid', ChooseData.id);
      formData.append('pid', ChooseData.id);
      // rid append. 식물을 등록할 방 고유 번호
      formData.append('rid', rid);
      formData.append('nickname', plantName);
      formData.append('image', {
        uri: image,
        name: 'plant.jpg',
        type: 'image/jpeg',
      });
      formData.append('startedDate', ndate);
      console.log(formData);
      await myPlantRegister(formData)
        .then(res => console.log(res.data))
        .catch(err => console.log(err));
      plantchange();
    }
    closeModal(false, 'Cancel');

    await roomchange();
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
              style={{color: 'green', fontSize: 30}}></Icon>
          </TouchableOpacity>
        </View>
        {/* 내용 */}
        <View style={styles.content}>
          {/* 식물이름입력 */}
          <View style={styles.input}>
            <Littlechip>
              <Text style={styles.chiptext}>식물 이름</Text>
            </Littlechip>
            <TextInputBox style={{marginBottom: 10}}>
              <TextInput
                placeholder="식물 이름"
                onChangeText={setPlantName}
                value={plantName}
              />
            </TextInputBox>
          </View>
          {/* 데려온 날 */}
          <View style={{marginTop: 40}}>
            <Littlechip style={{marginBottom: 10}}>
              <Text style={styles.chiptext}>데려온 날</Text>
            </Littlechip>
            <DatePicker
              style={{
                marginBottom: 10,
                marginHorizontal: 10,
              }}
              date={date}
              mode="date"
              onDateChange={setDate}
            />
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
          </View>
          <View style={styles.identification}>
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
              {/* 식물 분류 */}
              <View style={styles.result}>
                {ChooseData !== undefined ? (
                  <Text style={styles.chiptext}>{ChooseData.name}</Text>
                ) : null}
              </View>
            </View>
          </View>
        </View>
        {/* 버튼 */}
        <View style={styles.button}>
          <AddButton onPress={() => plusplant()}>
            <ButtonText>저장</ButtonText>
            <Icon
              type="Ionicons"
              name="checkmark-circle-outline"
              style={{fontSize: 20}}></Icon>
          </AddButton>
        </View>
        {/* 식물 판별 결과 */}
        <Modal
          transparent={true}
          animationType="fade"
          visible={isModalVisible}
          nRequestClose={() => changeModalVisible(false)}
          style={styles.plantmodal}>
          <PlantIdentification
            changeModalVisible={changeModalVisible}
            setData={setData}
            image={image}
          />
        </Modal>
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
    height: HEIGHT - 50,
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
    flex: 0.8,
  },
  content: {
    flex: 11,
  },
  chiptext: {
    fontWeight: 'bold',
    fontSize: 12,
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
    flex: 1,
  },
  photo: {
    flex: 1,
    flexDirection: 'row',
  },
  identification: {
    flex: 3,
    // flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  preview: {
    flexDirection: 'column',
    alignItems: 'center',
    backgroundColor: 'white',
    borderRadius: 10,
    marginTop: 5,
  },
  result: {
    marginBottom: 10,
  },
});
export {PlusModal};
