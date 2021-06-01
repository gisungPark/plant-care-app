import React, {useState} from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
} from 'react-native';
import {Button, Icon} from 'native-base';
import styled from 'styled-components';
import {
  ModalButtonBox,
  ModalButton1,
  ModalButton,
} from '../../assets/theme/roomstyle';
import ImagePicker from 'react-native-image-crop-picker';

// 글 작성 textInput 박스
const TextInputBox = styled.View`
  background-color: white;
  height: 50px;
  margin: 20px 20px 10px 20px;
  padding: 10px;
  border: 0.4px solid rgba(0, 0, 0, 0.3);
  border-radius: 10px;
`;
const AddButton = styled.TouchableOpacity`
  height: 40px;
  margin-top: 4px;
  margin-bottom: 10px;
  padding: 2px;
  justify-content: center;
  align-items: center;
  flex-direction: row;
`;
const ButtonText = styled.Text`
  font-weight: bold;
  font-size: 16px;
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
const PhotoChoice = props => {
  // take photo, choose photo
  const [image, setImage] = useState(
    'http://www.pngall.com/wp-content/uploads/5/Profile-PNG-Clipart.png',
  );
  const takePhotoFromCamera = () => {
    ImagePicker.openCamera({
      compressImageMaxWidth: 300,
      compressImageMaxHeight: 300,
      cropping: true,
      compressImageQuality: 0.7,
    })
      .then(image => {
        // console.log(image);
        setImage(image.path);
        this.bs.current.snapTo(1);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };

  const choosePhotoFromLibrary = () => {
    ImagePicker.openPicker({
      width: 300,
      height: 300,
      cropping: true,
      compressImageQuality: 0.7,
    })
      .then(image => {
        // console.log(image);
        setImage(image.path);
        this.bs.current.snapTo(1);
      })
      .catch(err => {
        console.log('openCamera catch' + err.toString());
      });
  };
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };
  bs = React.createRef();
  return (
    <TouchableOpacity disabled={true} style={styles.container}>
      <View style={styles.modal}>
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
        <ImageArea>
          <ImageBox onPress={choosePhotoFromLibrary}>
            <Icon
              type="MaterialCommunityIcons"
              name="image-multiple"
              style={{fontSize: 30, color: 'rgba(0,0,0,0.7)'}}
            />
            <Text style={{fontSize: 13, marginTop: 8}}>사진 선택</Text>
          </ImageBox>
          <ImageBox onPress={takePhotoFromCamera}>
            <Icon
              type="MaterialCommunityIcons"
              name="camera"
              style={{fontSize: 30, color: 'rgba(0,0,0,0.7)'}}
            />
            <Text style={{fontSize: 13, marginTop: 8}}>사진 촬영</Text>
          </ImageBox>
        </ImageArea>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.2)',
  },
  modal: {
    // height: HEIGHT - 100,
    paddingTop: 10,
    width: WIDTH - 120,
    flex: 0.3,
    backgroundColor: 'white',
    borderRadius: 10,
    backgroundColor: '#F9F9F9',
  },
  modaltop: {
    flex: 0.8,
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    paddingVertical: 6,
    paddingHorizontal: 14,
    // backgroundColor: "rgba(52,176,80,0.1)",
    width: WIDTH - 120,
  },
  question: {
    flex: 1,
    marginHorizontal: 20,
  },
  buttons: {
    flex: 2,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
export {PhotoChoice};
