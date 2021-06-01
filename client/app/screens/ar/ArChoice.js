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
import {themesAR} from '../../assets/theme/arTheme';
import {changeRoomNameTheme} from '../../api/room';

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

export function ArChoice({navigation}) {
  const [isSelected, setIsSelected] = useState(
    'https://ssafybucket.s3.ap-northeast-2.amazonaws.com/arplant1.jpg',
  );
  const [isSelectedAr, setIsSelectedAr] = useState('arplant1');
  const Choices = () => {
    return themesAR.map((theme, i) => {
      return (
        <TouchableOpacity
          key={i}
          onPress={() => {
            setIsSelected(theme.address);
            setIsSelectedAr(theme.name);
          }}
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
  return (
    <View style={styles.photo}>
      <Text style={styles.chiptext}>보고싶은 식물 AR을 선택하세요</Text>
      <View
        style={{
          flexDirection: 'row',
          flexWrap: 'wrap',
          justifyContent: 'flex-start',
          paddingHorizontal: 15,
          marginVertical: 30,
        }}>
        {Choices()}
      </View>
      <View style={styles.button}>
        <AddButton
          onPress={() => {
            // console.log('click');
            navigation.navigate('ArIntro', {
              arname: isSelectedAr,
            });
          }}>
          <ButtonText>AR 보러가기</ButtonText>
          <Icon
            type="Ionicons"
            name="checkmark-circle-outline"
            style={{fontSize: 20}}></Icon>
        </AddButton>
      </View>
    </View>
  );
}
const styles = StyleSheet.create({
  photo: {
    alignItems: 'center',
  },
  buttons: {
    margin: 20,
    justifyContent: 'center',
    marginBottom: 80,
  },
  chiptext: {
    fontWeight: '200',
    fontSize: 14,
    marginTop: 100,
  },
  imagewrap: {
    width: 150,
    height: 150,
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
