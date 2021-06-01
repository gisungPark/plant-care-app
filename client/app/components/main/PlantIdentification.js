import React, {useState, useEffect} from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  TouchableOpacity,
  Dimensions,
  Image,
  ScrollView,
} from 'react-native';
import {Button, Icon} from 'native-base';
import styled from 'styled-components';
import {
  ModalButtonBox,
  ModalButton1,
  ModalButton,
} from '../../assets/theme/roomstyle';
import RadioButtonRN from 'radio-buttons-react-native';
import axios from 'axios';
import {InputAutoSuggest} from 'react-native-autocomplete-search';
import {plantAll, plantInfoDetailByName} from '../../api/plant';
import {useSelector} from 'react-redux';
import {KeyboardAwareScrollView} from 'react-native-keyboard-aware-scroll-view';
import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

import {plantRecognition} from '../../api/plant';

import {RenderLoading} from '../../components/common/renderLoading';

// 글 작성 textInput 박스
const SearchBar = styled.TextInput`
  background-color: white;
  height: 50px;
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
const WIDTH = Dimensions.get('window').width;
const HEIGHT = Dimensions.get('window').height;
const HEIGHT_MODAL = 300;
const PlantIdentification = props => {
  const {uid} = useSelector(state => ({uid: state.authReducer.uid}));
  const [result, setResult] = useState({});
  const [info, setInfo] = useState([]);
  const [loading, setLoading] = useState(false);
  // autocomplete을 위한 식물 정보 불러오기
  const getPlantInfo = () => {
    plantAll()
      .then(res => setInfo(res.data))
      .catch(err => console.log(err));
  };
  useEffect(async () => {
    setLoading(true);
    if (props.image !== undefined) {
      const formData = new FormData();
      formData.append('file', {
        uri: props.image,
        name: 'plant.jpg',
        type: 'image/jpeg',
      });
      const recogResponse = await plantRecognition(formData);

      const detailResponse = await plantInfoDetailByName(
        recogResponse.data.result,
      );
      setResult(detailResponse.data);
    }
    setLoading(false);
    await getPlantInfo();
  }, [props.image]);
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };

  return (
    <ScrollView disabled={true}>
      <KeyboardAwareScrollView contentContainerStyle={styles.container}>
        <View style={styles.modal}>
          <View style={styles.modaltop}>
            <Text style={styles.titletext}>식물 판별</Text>
            <View style={styles.close}>
              <TouchableOpacity onPress={() => closeModal(false, 'Cancel')}>
                <Icon
                  type="Ionicons"
                  name="close-outline"
                  style={{color: '#29582C', fontSize: 30}}></Icon>
              </TouchableOpacity>
            </View>
          </View>
          <View>
            <Text style={styles.intro}>이 식물이 맞나요?</Text>
          </View>
          <View style={styles.modalbody}>
            <View style={styles.listtop}>
              <TouchableOpacity style={styles.plant1}>
                <Image
                  // source={require('../../assets/images/plant.jpg')}
                  source={{uri: result.image}}
                  style={styles.eximage}
                />
                <View style={{alignItems: 'flex-end'}}>
                  <Text style={{fontWeight: 'bold', fontSize: 18}}>
                    {result.name}
                  </Text>
                  <Text
                    style={{
                      fontWeight: 'bold',
                      fontSize: 13,
                      color: 'rgba(0,0,0,0.5)',
                    }}>
                    {result.common}
                  </Text>
                </View>
              </TouchableOpacity>
            </View>
            <View style={styles.plantinput}>
              <Text style={{marginBottom: 20}}>
                {result.name}이(가) 아니라면 직접 입력해주세요
              </Text>

              <View
                style={{
                  height: 120,
                  justifyContent: 'center',
                }}>
                <InputAutoSuggest
                  staticData={info}
                  itemFormat={{
                    id: 'id',
                    name: 'name',
                  }}
                  onDataSelectedChange={data =>
                    data !== null ? setResult(data) : console.log(data)
                  }
                />
              </View>
            </View>
          </View>
          <View style={styles.last}>
            <Button
              onPress={() => closeModal(false, result)}
              style={styles.lastbtn}>
              <Text style={{fontSize: 15, fontWeight: 'bold', color: 'white'}}>
                완료
              </Text>
            </Button>
          </View>
        </View>
      </KeyboardAwareScrollView>
      <RenderLoading isLoading={loading} />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    height: hp('100%'),
    backgroundColor: 'rgba(0,0,0,0.2)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modal: {
    width: WIDTH,
    height: HEIGHT_MODAL,
    flex: 1,
    backgroundColor: 'white',
    borderRadius: 10,
    backgroundColor: '#F9F9F9',
  },
  modaltop: {
    borderBottomColor: 'green',
    borderBottomWidth: 0.3,
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
    marginHorizontal: 30,
    marginTop: 30,
    paddingTop: 10,
    paddingBottom: 10,
  },
  titletext: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#29582C',
    marginLeft: 10,
  },
  intro: {
    fontSize: 15,
    marginLeft: 40,
    marginTop: 40,
    fontWeight: 'bold',
  },
  plant1: {
    backgroundColor: '#F2F5E1',
    borderRadius: 10,
    justifyContent: 'space-around',
    alignItems: 'center',
    marginTop: 35,
    marginHorizontal: 30,
    flexDirection: 'row',
    height: 170,
  },
  eximage: {
    width: 100,
    height: 120,
    borderRadius: 10,
  },
  listbottom: {
    flexDirection: 'row',
  },
  plantinput: {
    margin: 30,
  },
  last: {
    // flexDirection: 'row',
    // alignItems: 'flex-end',
    // justifyContent: 'center',
    // backgroundColor: 'yellow'
    left: WIDTH / 2 - 50,
    marginTop: 20,
  },
  lastbtn: {
    width: 100,
    position: 'absolute',
    backgroundColor: '#29582C',
    justifyContent: 'center',
    borderRadius: 10,
  },
});
export {PlantIdentification};
