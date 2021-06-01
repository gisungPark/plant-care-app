import React from 'react';
import {Alert, Modal, Text} from 'react-native';

// style
import styled from 'styled-components';
import {
  ImgSelectBox,
  ImgSelectBtn,
  CompleteBtn,
  CompleteBtnText,
} from '../../assets/theme/DiaryStyle';
import {heightPercentageToDP as hp} from 'react-native-responsive-screen';
import {Icon} from 'native-base';

// image-picker
import ImagePicker from 'react-native-image-crop-picker';
import {useState} from 'react';
import CompleteModal from '../../components/diary/modal/CompleteModal';

import {updateImage} from '../../api/auth';
// redux
import {updateProfileImage} from '../../reducers/profileReducer';
import {useDispatch, useSelector} from 'react-redux';

//loading
import {RenderLoading} from '../../components/common/renderLoading';

// 프로필 사진 변경 페이지 전체 컨테이너
const ImgChangeContainer = styled.View`
  height: ${hp('63%')}px;
  padding: 30px;
`;

// 프로필 사진 프리뷰 영역
const ImgPreviewBox = styled.View`
  flex: 5;
  justify-content: center;
  align-items: center;
`;

// 프로필 사진
const ProfileImg = styled.Image`
  width: 140px;
  height: 140px;
  border-radius: 180px;
  /* background-color: black; */
  border-width: 1px;
  border-color: grey;
  margin-bottom: 30px;
`;

export default function ProfileImgChange({navigation}) {
  const dispatch = useDispatch();
  const updateProfileImg = image => dispatch(updateProfileImage(image));

  //loading
  const [isLoading, setIsLoading] = useState(false);

  const {image} = useSelector(state => ({
    image: state.profileReducer.profile,
  }));

  const [img, setImg] = useState(image);
  const [completeModalVisible, setCompleteModalVisible] = useState(false);

  // 완료 버튼을 눌렀을 때 이동할 페이지
  const complete = () => {
    navigation.navigate('Profile');
  };

  // 갤러리에서 사진 선택
  const pickSingle = () => {
    ImagePicker.openPicker({
      width: 500,
      height: 500,
      cropping: true,
      cropperCircleOverlay: true,
    })
      .then(image => {
        // console.log('received image', image);
        setImg(image.path);
      })
      .catch(e => {
        console.log(e);
      });
  };

  // 사진 촬영
  const PickSingleWithCamera = () => {
    ImagePicker.openCamera({
      width: 500,
      height: 500,
      mediaType: 'photo', // 사진만 받기(동영상x)
      cropping: true,
      cropperCircleOverlay: true,
    })
      .then(image => {
        setImg(image.path);
      })
      .catch(e => console.log(e));
  };

  // 사진 수정
  const changeProfileImage = async () => {
    setIsLoading(true);
    // 사진 폼데이터 형식으로 변환
    const formData = new FormData();
    formData.append('nickname', '바꿀닉네임');
    formData.append('profile', {
      uri: img,
      name: 'profile.jpg',
      type: 'image/jpeg',
    });
    // console.log(formData);
    // 백엔드 요청
    const response = await updateImage(formData);
    const newImage = response.data.response.profile;
    //리덕스 디스패치
    await updateProfileImg(newImage);
    setCompleteModalVisible(true);
    setIsLoading(false);
  };

  return (
    <ImgChangeContainer>
      <ImgPreviewBox>
        <ProfileImg source={{uri: img}}></ProfileImg>
      </ImgPreviewBox>
      <ImgSelectBox flexHeight="2" marginBottom="40px">
        <ImgSelectBtn
          style={{marginRight: 10}}
          onPress={() => {
            pickSingle();
          }}>
          <Icon
            type="MaterialCommunityIcons"
            name="image-multiple"
            style={{fontSize: 30, marginBottom: 5}}
          />
          <Text style={{fontSize: 12}}>사진 선택</Text>
        </ImgSelectBtn>
        <ImgSelectBtn
          style={{marginLeft: 10}}
          onPress={() => {
            PickSingleWithCamera();
          }}>
          <Icon
            type="MaterialCommunityIcons"
            name="camera"
            style={{fontSize: 30, marginBottom: 5}}
          />
          <Text style={{fontSize: 12}}>사진 촬영</Text>
        </ImgSelectBtn>
      </ImgSelectBox>
      <CompleteBtn flexHeight="1" onPress={() => changeProfileImage()}>
        {/* <CompleteBtn flexHeight="1" onPress={() => setCompleteModalVisible(true)}> */}
        <CompleteBtnText>완료</CompleteBtnText>
      </CompleteBtn>

      {/* 프로필 사진 변경 완료 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={completeModalVisible}
        onRequestClose={() => {
          setCompleteModalVisible(!completeModalVisible);
        }}>
        <CompleteModal
          complete={complete}
          content="프로필 사진 변경 완료"
          setCompleteModalVisible={setCompleteModalVisible}
        />
      </Modal>
      <RenderLoading isLoading={isLoading}></RenderLoading>
    </ImgChangeContainer>
  );
}
