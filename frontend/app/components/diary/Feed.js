import React, { useState } from "react";
import { View, Text, Image, Modal, Pressable } from "react-native";

import { Icon } from "native-base";
import styled from "styled-components";

const FeedBox = styled.View`
  flex: 1;
  margin-top: 30px;
  height: 400px;
  border-radius: 10px;
  margin: 10px;
  background-color: white;
  /* align-items: center; */
  /* justify-content: center; */
`;

const FeedBoxHeader = styled.TouchableOpacity`
  flex: 0.9;
  justify-content: center;
  align-items: flex-end;
  margin-right: 5px;
`;
const FeedImage = styled.View`
  flex: 5;
  /* background-color: yellow; */
`;
const FeedDate = styled.View`
  flex: 0.8;
  padding-top: 16px;
  padding-left: 16px;
  /* background-color: green; */
`;
const FeedContents = styled.View`
  flex: 1.5;
  padding-left: 16px;
  padding-right: 16px;
  padding-bottom: 16px;
  /* background-color: grey; */
`;

// 피드 수정/삭제 모달 전체 컨테이너
const ModalContainer = styled.View`
  flex: 1;
  background-color: rgba(0, 0, 0, 0.5);
`;

// 피드 수정/삭제 모달 박스
const ModalBox = styled.View`
  flex: 1;
  margin-left: 100px;
  margin-right: 100px;
  margin-top: 300px;
  margin-bottom: 300px;
  background-color: white;
  padding: 16px;
  border-radius: 10px;
`;

// 피드 수정/삭제 모달 헤더
const ModalHeader = styled.View`
  flex: 0.8;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  margin-left: 4px;
`;

// 모달 회색버튼
const ModalButton1 = styled.TouchableOpacity`
  flex: 1;
  background-color: ${({ theme }) => theme.colors.background};
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin: 2px;
`;

// 모달 빨간색 버튼
const ModalButton2 = styled.TouchableOpacity`
  flex: 1;
  background-color: ${({ theme }) => theme.colors.deleteRed};
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin: 2px;
`;

// 피드 삭제 확인 모달 박스
const DeleteCheckModalBox = styled.View`
  flex: 1;
  margin-left: 70px;
  margin-right: 70px;
  margin-top: 300px;
  margin-bottom: 300px;
  background-color: white;
  padding: 16px;
  border-radius: 10px;
  align-items: center;
`;

// 텍스트 넘칠 경우 처리 해줄 예정
const FeedContentsText = styled.Text``;

export default function Feed() {
  const [modalVisible, setModalVisible] = useState(false);
  const [deleteModalVisible, setDeleteModalVisible] = useState(false);

  return (
    <FeedBox>
      {/* 피드 수정,삭제 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}
      >
        <ModalContainer>
          <ModalBox>
            <ModalHeader>
              <Text style={{ fontWeight: "bold" }}>Settings</Text>
              <Pressable onPress={() => setModalVisible(!modalVisible)}>
                <Icon type="AntDesign" name="close" style={{ fontSize: 20 }} />
              </Pressable>
            </ModalHeader>
            <ModalButton1 style={{ marginBottom: 1 }}>
              <Text>피드 수정</Text>
            </ModalButton1>
            <ModalButton2
              onPress={() => {
                setDeleteModalVisible(!deleteModalVisible);
                setModalVisible(!modalVisible);
              }}
            >
              <Text style={{ color: "white" }}>피드 삭제</Text>
            </ModalButton2>
          </ModalBox>
        </ModalContainer>
      </Modal>

      {/* 삭제 확인 모달창 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={deleteModalVisible}
        onRequestClose={() => {
          setModalVisible(!deleteModalVisible);
        }}
      >
        <ModalContainer>
          <DeleteCheckModalBox>
            <ModalHeader>
              <Text style={{ fontWeight: "bold" }}>정말 삭제하시겠어요?</Text>
              {/* <Pressable onPress={() => setModalVisible(!modalVisible)}>
                <Icon type="AntDesign" name="close" style={{ fontSize: 20 }} />
              </Pressable> */}
            </ModalHeader>
            <View style={{ flex: 0.4, flexDirection: "row" }}>
              <ModalButton2>
                <Text style={{ color: "white" }}>삭제</Text>
              </ModalButton2>
              <ModalButton1
                onPress={() => {
                  setDeleteModalVisible(!deleteModalVisible);
                }}
              >
                <Text>취소</Text>
              </ModalButton1>
            </View>
          </DeleteCheckModalBox>
        </ModalContainer>
      </Modal>

      <FeedBoxHeader onPress={() => setModalVisible(!modalVisible)}>
        <Icon type="MaterialCommunityIcons" name="dots-vertical" />
      </FeedBoxHeader>
      <FeedImage>
        <Image
          source={{
            uri:
              "http://cereshome.co.kr/web/product/small/20200420/659ff6db3048df1a413a053655c22ebb.jpg",
          }}
          style={{ flex: 1 }}
        />
      </FeedImage>
      <FeedDate>
        <Text>2021.04.06   16:15</Text>
      </FeedDate>
      <FeedContents>
        <FeedContentsText>
          오늘은 스투키를 데려온지 10일째다. 그냥 귀여워서 찍어봤다! 스투키는 늘
          귀여워 새로워 짜릿해
        </FeedContentsText>
      </FeedContents>
    </FeedBox>
  );
}
