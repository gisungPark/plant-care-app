import {Icon} from 'native-base';
import React, {useEffect, useState} from 'react';
import {View, Text, Pressable, Image, StyleSheet} from 'react-native';

import {recomPlantDetail} from '../../api/recommendation';
import {
  ModalContainer,
  ModalBox,
  ModalHeader,
} from '../../assets/theme/ModalStyle';
import plant from '../../reducers/plantReducer';
//loading
import {RenderLoading} from '../../components/common/renderLoading';

export default function plantdetailmodal(props) {
  const [plant, setPlant] = useState('');
  //loading
  const [isLoading, setIsLoading] = useState(false);
  const closeModal = visible => {
    props.setModalVisible(visible);
  };

  const savePlant = async () => {
    const plant = await recomPlantDetail(props.plantId);
    setPlant(plant.data);
    setIsLoading(false);
  };

  useEffect(async () => {
    setIsLoading(true);
    await savePlant();
  }, [props]);

  // console.log('modal props', props);
  return (
    <ModalContainer>
      <ModalBox flexHeight="0.9" modalWidth="90%">
        <ModalHeader complete flexHeight="0.04">
          <Pressable onPress={() => closeModal(false)}>
            <Icon type="AntDesign" name="close" style={{fontSize: 20}} />
          </Pressable>
        </ModalHeader>
        <View
          style={{
            flex: 1,
            // backgroundColor: 'red',
            width: '100%',
            height: '100%',
            alignItems: 'center',
          }}>
          <Image
            style={styles.plantimage}
            source={{
              uri: plant.image,
            }}
          />
          <View style={styles.plantdescription}>
            <Text style={styles.plantname}>{plant.name}</Text>
            <Text style={styles.plantcommon}>{plant.common}</Text>
            <View style={styles.desc}>
              <Text style={styles.plantleft}>난이도</Text>
              <Text style={styles.plantright}>{plant.level}</Text>
            </View>
            <View style={styles.desc}>
              <Text style={styles.plantleft}>습도</Text>
              <Text style={styles.plantright}>{plant.humid}</Text>
            </View>
            <View style={styles.desc}>
              <Text style={styles.plantleft}>기능성 정보</Text>
              <Text style={styles.plantright}>{plant.water}</Text>
            </View>
          </View>
        </View>
      </ModalBox>
      <RenderLoading isLoading={isLoading}></RenderLoading>
    </ModalContainer>
  );
}

const styles = StyleSheet.create({
  plantimage: {
    flex: 1,
    padding: 0,
    resizeMode: 'contain',
    borderRadius: 10,
    width: 250,
    height: 250,
    transform: [{scale: 1.1}],
  },
  plantdescription: {
    flex: 0.8,
    marginTop: 30,
    width: 280,
  },
  plantname: {
    color: '#29582C',
    fontWeight: 'bold',
    fontSize: 25,
  },
  plantcommon: {
    marginBottom: 40,
  },
  desc: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginVertical: 10,
  },
  plantleft: {
    fontSize: 12,
  },
  plantright: {
    fontSize: 15,
    fontWeight: 'bold',
  },
});
