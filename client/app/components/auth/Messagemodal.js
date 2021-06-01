import React from 'react';
import {Text} from 'react-native';

import {
  ModalContainer,
  ModalBox,
  ModalHeader,
  ModalButton,
  ModalContent,
} from '../../assets/theme/ModalStyle';

import {readMessage} from '../../api/auth';

export default function MessageModal(props) {
  const closeModal = async visible => {
    // 알림 읽음으로 변경 로직 추가
    // console.log('props', props);
    if (props.message.messageKey) {
      const params = {messageKey: props.message.messageKey};
      await readMessage(params);
      props.setMsgLength(props.msgLength + 1);
    }
    props.setModalVisible(visible);
  };

  // console.log('message modal props', props);

  return (
    <ModalContainer>
      <ModalBox flexHeight="0.25" modalWidth="70%">
        <ModalHeader justifyContent="center" flexHeight="0.4">
          <Text>{props.message.title}</Text>
        </ModalHeader>
        <ModalContent justifyContent="center" flexHeight="0.6">
          <Text>{props.message.content || props.message.body}</Text>
        </ModalContent>
        <ModalButton
          flexHeight="0.4"
          backgroundColor="#EEF9E8"
          onPress={() => {
            closeModal(false);
          }}>
          <Text style={{color: '#29582C'}}>확인</Text>
        </ModalButton>
      </ModalBox>
    </ModalContainer>
  );
}
