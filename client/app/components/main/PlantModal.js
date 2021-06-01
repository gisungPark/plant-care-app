import React, {Component, useState, useEffect} from 'react';
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Dimensions,
  Modal,
} from 'react-native';
import {Icon} from 'native-base';
import {ByePlant} from './ByePlant';
import {DeletePlant} from './DeletePlant';
import {EditPlantModal} from './EditPlantModal';
import {useDispatch, useSelector} from 'react-redux';
import {changePlant} from '../../reducers/roomReducer';

const WIDTH = Dimensions.get('window').width;
const HEIGHT = Dimensions.get('window').height;
const HEIGHT_MODAL = 300;
const PlantModal = props => {
  // redux
  const {plantact} = useSelector(state => ({
    plantact: state.roomReducer.plantact,
  }));
  const [isModalVisible, setisModalVisible] = useState(false);
  const [isModalVisible2, setisModalVisible2] = useState(false);
  const [isModalVisible3, setisModalVisible3] = useState(false);
  const [ChooseData, setChooseData] = useState();
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
  const closeModal = (bool, data) => {
    props.changeModalVisible(bool);
    props.setData(data);
  };
  // 식물 삭제 시 ChooseData는 Delete
  //삭제 후에는 모달창이 닫혀야되니까 closeModal을 실행하고 props값을 Room.js로 넘겨준다.
  const dispatch = useDispatch();
  const plantback = () => dispatch(changePlant('back'));
  useEffect(async () => {
    if (plantact === 'bye') {
      plantback();
      closeModal(false, 'Bye');
    }
    if (plantact === 'trash') {
      plantback();
      closeModal(false, 'Trash');
    }
    if (plantact === 'edit') {
      plantback();
      closeModal(false, 'edit');
    }
  }, [plantact]);
  const pid = props.pid;
  const pname = props.pname;
  const rid = props.rid;
  const startedDate = props.startedDate;
  const image = props.image;
  return (
    <TouchableOpacity disabled={true} style={styles.container}>
      <View style={styles.modal}>
        <View style={styles.modaltop}>
          <Text
            style={{
              color: 'rgba(0,0,0,0.5)',
              fontWeight: 'bold',
              marginLeft: 10,
            }}>
            SETTINGS
          </Text>
          <TouchableOpacity
            onPress={() => closeModal(false, 'Cancel')}
            style={styles.closebtn}>
            <Icon
              type="Ionicons"
              name="close-outline"
              style={{color: 'green', fontSize: 40}}></Icon>
          </TouchableOpacity>
        </View>
        <View style={styles.btnlist}>
          <TouchableOpacity
            style={styles.btn}
            onPress={() => changeModalVisible3(true)}>
            <Text style={styles.btntext}>식물 정보 수정</Text>
            <Icon
              style={styles.btnicon}
              type="Ionicons"
              name="create-outline"></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible3}
            nRequestClose={() => changeModalVisible3(false)}
            style={styles.plantmodal}>
            <EditPlantModal
              changeModalVisible={changeModalVisible3}
              setData={setData}
              rid={rid}
              startedDate={startedDate}
              pid={pid}
              image={image}
              pname={pname}
            />
          </Modal>
          <TouchableOpacity
            style={styles.btn}
            onPress={() => changeModalVisible(true)}>
            <Text style={styles.btntext}>떠나간 식물</Text>
            <Icon
              style={styles.btnicon}
              type="Ionicons"
              name="heart-dislike-circle-outline"></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible}
            nRequestClose={() => changeModalVisible(false)}
            style={styles.plantmodal}>
            <ByePlant
              changeModalVisible={changeModalVisible}
              setData={setData}
              pid={pid}
              pname={pname}
            />
          </Modal>
          <TouchableOpacity
            style={styles.btn3}
            onPress={() => changeModalVisible2(true)}>
            <Text style={styles.btntext3}>식물 삭제</Text>
            <Icon
              style={styles.btnicon3}
              type="Ionicons"
              name="trash-bin-outline"></Icon>
          </TouchableOpacity>
          <Modal
            transparent={true}
            animationType="fade"
            visible={isModalVisible2}
            nRequestClose={() => changeModalVisible2(false)}
            style={styles.plantmodal}>
            <DeletePlant
              changeModalVisible={changeModalVisible2}
              setData={setData}
              pid={pid}
              pname={pname}
            />
          </Modal>
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
    backgroundColor: 'rgba(0,0,0,0.2)',
  },
  modal: {
    // height: HEIGHT - 100,
    paddingTop: 10,
    width: WIDTH - 120,
    backgroundColor: 'white',
    borderRadius: 10,
    flex: 0.35,
  },
  modaltop: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 6,
    paddingHorizontal: 14,
    // backgroundColor: "rgba(52,176,80,0.1)",
    width: WIDTH - 120,
  },
  btnlist: {
    marginTop: 5,
    backgroundColor: '#F9F9F9',
    margin: 15,
    borderRadius: 10,
  },
  btn: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    borderBottomColor: 'rgba(0,0,0,0.5)',
    borderBottomWidth: 0.3,
  },
  btn3: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  btntext: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: 'bold',
    color: 'rgba(0,0,0,0.8)',
    fontSize: 13,
  },
  btnicon: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: 'bold',
    color: 'rgba(0,0,0,0.8)',
    fontSize: 17,
  },
  btntext3: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: 'bold',
    color: '#BA2F2F',
    fontSize: 13,
  },
  btnicon3: {
    marginHorizontal: 30,
    marginVertical: 15,
    fontWeight: 'bold',
    color: '#BA2F2F',
    fontSize: 17,
  },
});
export {PlantModal};
