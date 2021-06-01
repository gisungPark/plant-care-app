import React from 'react';
import {Modal} from 'react-native';

// style
import styled from 'styled-components';
import {heightPercentageToDP as hp} from 'react-native-responsive-screen';
import {CompleteBtn, CompleteBtnText} from '../../assets/theme/DiaryStyle';
import CompleteModal from '../../components/diary/modal/CompleteModal';
import {useState} from 'react';

// firebase google redux
import firebase from '../../config/firebase';
import {set} from 'react-native-reanimated';

// 비밀번호 재설정 페이지 전체 컨테이너
const ImgChangeContainer = styled.View`
  height: ${hp('40%')}px;
  padding: 30px;
`;

// (라벨 + 비밀번호 입력창) 영역
const InputBox = styled.View`
  flex: 1;
  flex-direction: row;
  align-items: center;
`;

// 라벨
const Label = styled.Text`
  flex: 1.5;
`;

// 비밀번호 입력창
const PasswordInput = styled.TextInput`
  flex: 3;
  border: 0.3px solid grey;
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
  background-color: white;
`;

export default function PasswordChange({navigation}) {
  const [completeModalVisible, setCompleteModalVisible] = useState(false);
  const [password, setPassword] = useState('');
  const [newPw, setNewPw] = useState('');
  const [newPwConfirm, setNewPwConfirm] = useState('');
  const [message, setMessage] = useState('');
  const complete = () => {
    if (message === '비밀번호가 변경되었습니다.') {
      navigation.navigate('Profile');
    } else {
    }
  };

  validateProvider = async () => {
    const provider = await firebase.auth().currentUser.providerData[0]
      .providerId;
    if (provider === 'google.com') {
      setMessage('구글로그인은 비밀번호를 변경할 수 없습니다.');
      return false;
    } else {
      return true;
    }
  };

  validatePassword = () => {
    // console.log(userPW);
    // 대문자 최소 1개, 소문자 1개 이상, 숫자 1개이상, 특수문자 1개 이상, 8자리이상
    let regPW =
      /^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\d]){1,})(?=(.*[\W]){1,})(?!.*\s).{8,}$/;
    if (regPW.test(newPw) === false) {
      setMessage('대/소문자, 숫자, 특수문자 포함 8자 이상 입력해주세요');
      return false;
    } else {
      return true;
    }
  };

  const validatePWC = () => {
    // console.log(userPWC);
    if (newPw !== newPwConfirm) {
      setMessage('비밀번호가 일치하지 않습니다');
      return false;
    } else {
      return true;
    }
  };

  const onSubmit = async () => {
    const user = await firebase.auth().currentUser;
    const valProvider = await validateProvider();
    if (!valProvider) {
      // console.log(message);
      setCompleteModalVisible(true);
      return;
    }
    const valPw = await validatePassword();
    if (!valPw) {
      // console.log(message);
      setCompleteModalVisible(true);
      return;
    }
    const valPwc = await validatePWC();
    if (!valPwc) {
      // console.log(message);
      setCompleteModalVisible(true);
      return;
    }
    try {
      await user.updatePassword(newPw);
      await setMessage('비밀번호가 변경되었습니다.');
      await setCompleteModalVisible(true);
    } catch (error) {
      setMessage(error);
      return;
    }
  };

  return (
    <ImgChangeContainer>
      {/* <InputBox>
        <Label>현재 비밀번호</Label>
        <PasswordInput
          onChangeText={userPW => setPassword(userPW)}
          secureTextEntry={true}
        />
      </InputBox> */}
      <InputBox>
        <Label>새 비밀번호</Label>
        <PasswordInput
          placeholder="8자 이상 입력해주세요."
          onChangeText={userNPW => setNewPw(userNPW)}
          secureTextEntry={true}
        />
      </InputBox>
      <InputBox>
        <Label>새 비밀번호 확인</Label>
        <PasswordInput
          placeholder="8자 이상 입력해주세요."
          onChangeText={userNPWC => setNewPwConfirm(userNPWC)}
          secureTextEntry={true}
        />
      </InputBox>
      <CompleteBtn
        flexHeight="0.7"
        marginTop="50px;"
        // onPress={() => setCompleteModalVisible(true)}
        onPress={() => onSubmit()}>
        <CompleteBtnText>완료</CompleteBtnText>
      </CompleteBtn>

      {/* 비밀번호 재설정 완료 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={completeModalVisible}
        onRequestClose={() => {
          setCompleteModalVisible(!completeModalVisible);
        }}>
        <CompleteModal
          complete={complete}
          content={message}
          setCompleteModalVisible={setCompleteModalVisible}
        />
      </Modal>
    </ImgChangeContainer>
  );
}
