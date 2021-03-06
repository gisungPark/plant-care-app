import {Icon} from 'native-base';
import React from 'react';
import {Pressable, Text} from 'react-native';

import {
  ModalContainer,
  ModalBox,
  ModalHeader,
  ModalButton,
  ModalButtonBox,
  Line,
} from '../../../assets/theme/ModalStyle';

export default function FeedSelectModal(props) {
  const closeModal = visible => {
    props.setModalVisible(visible);
  };
  const openDeleteModal = visible => {
    props.setDeleteModalVisible(visible);
  };
  // console.log('피드셀렉모달에서 받은 프롭스 다이어리', props.diary);
  return (
    <ModalContainer>
      <ModalBox flexHeight="0.22">
        <ModalHeader complete>
          <Pressable onPress={() => closeModal(false)}>
            <Icon type="AntDesign" name="close" style={{fontSize: 20}} />
          </Pressable>
        </ModalHeader>

        <ModalButtonBox borderRadius="10px">
          <ModalButton
            justifyContent="space-between"
            onPress={() => {
              closeModal(false);
              props.navigation.navigate('DiaryUpdate', {
                diary: props.diary,
              });
            }}>
            <Text>다이어리 수정</Text>
            <Icon
              type="FontAwesome"
              name="pencil-square-o"
              style={{fontSize: 20}}
            />
          </ModalButton>
          <Line />
          <ModalButton
            justifyContent="space-between"
            onPress={() => {
              closeModal(false);
              openDeleteModal(true);
            }}>
            <Text style={{color: '#F44336'}}>다이어리 삭제</Text>
            <Icon
              type="FontAwesome"
              name="trash-o"
              style={{fontSize: 20, color: '#F44336'}}
            />
          </ModalButton>
        </ModalButtonBox>
      </ModalBox>
    </ModalContainer>
  );
}
