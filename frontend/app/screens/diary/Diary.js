// react
import React, { useState } from "react";
import "react-native-gesture-handler";
import { Modal, Pressable, ScrollView, Text, View } from "react-native";

// styled-component
import styled from "styled-components";

// native-base
import {
  Container,
  Tab,
  Tabs,
  ScrollableTab,
  Thumbnail,
  TabHeading,
  Icon,
} from "native-base";

// library
import { CalendarView } from "../../components/diary/Calendar";

// components
import Feed from "../../components/diary/Feed";

// 나중에 Tab을 분리해서 컴포넌트화 할 예정
// import PlantTab from "../../components/diary/Tab";

const PlusButton = styled.TouchableOpacity`
  width: 50;
  height: 50;
  border-radius: 30;
  background-color: ${({ theme }) => theme.colors.darkGreen};
  justify-content: center;
  align-items: center;
  position: absolute;
  bottom: 20;
  right: 20;
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
  /* background-color: #ededed; */
  background-color: ${({ theme }) => theme.colors.lightGreenButton};
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin: 2px;
`;
export function DiaryScreen({ navigation }) {
  const [modalVisible, setModalVisible] = useState(false);
  const [DateModalVisible, setDateModalVisible] = useState(false);
  const [CompleteModalVisible, setCompleteModalVisible] = useState(false);

  return (
    <Container>
      <Tabs
        renderTabBar={() => (
          <ScrollableTab
            tabsContainerStyle={{ justifyContent: "flex-start" }}
            style={{
              height: 80,
              backgroundColor: "#F9F9F9",
              borderWidth: 0,
            }}
          />
        )}
        tabBarUnderlineStyle={{ height: 2, backgroundColor: "#8AD169" }}
      >
        <Tab
          heading={
            <TabHeading
              style={{
                backgroundColor: "#F9F9F9",
              }}
            >
              <Thumbnail
                source={{
                  uri:
                    "http://cereshome.co.kr/web/product/small/20200420/659ff6db3048df1a413a053655c22ebb.jpg",
                }}
              />
            </TabHeading>
          }
        >
          <ScrollView style={{ backgroundColor: "#F9F9F9" }}>
            <CalendarView />
            <Feed />
          </ScrollView>
        </Tab>
        <Tab
          heading={
            <TabHeading
              style={{
                backgroundColor: "#F9F9F9",
              }}
            >
              <Thumbnail
                source={{
                  uri:
                    "http://cereshome.co.kr/web/product/small/20200420/659ff6db3048df1a413a053655c22ebb.jpg",
                }}
              />
            </TabHeading>
          }
        >
          <CalendarView />
        </Tab>
        <Tab
          heading={
            <TabHeading
              style={{
                backgroundColor: "#F9F9F9",
              }}
            >
              <Thumbnail
                source={{
                  uri:
                    "http://cereshome.co.kr/web/product/small/20200420/659ff6db3048df1a413a053655c22ebb.jpg",
                }}
              />
            </TabHeading>
          }
        >
          <CalendarView />
        </Tab>
        {/* <PlantTab /> */}
      </Tabs>

      {/* 화면 오른쪽 하단 플러스 버튼 */}
      <PlusButton title="" onPress={() => setModalVisible(!modalVisible)}>
        <Icon name="md-add" style={{ color: "white" }} />
      </PlusButton>

      {/* 물주기/피드작성 선택 모달창 */}
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
            <ModalHeader complete>
              <Text style={{ fontWeight: "bold" }}>선택</Text>
              <Pressable onPress={() => setModalVisible(!modalVisible)}>
                <Icon type="AntDesign" name="close" style={{ fontSize: 20 }} />
              </Pressable>
            </ModalHeader>
            {/* 물주기 버튼 */}
            <ModalButton1
              style={{ marginBottom: 1 }}
              onPress={() => {
                setModalVisible(!modalVisible);
                setDateModalVisible(!DateModalVisible);
              }}
            >
              <Text>물주기</Text>
            </ModalButton1>
            {/* 피드 작성 버튼 */}
            <ModalButton1
              onPress={() => {
                setModalVisible(!modalVisible);
                navigation.navigate("DiaryWrite");
              }}
            >
              <Text>피드 작성</Text>
            </ModalButton1>
          </ModalBox>
        </ModalContainer>
      </Modal>

      {/* 물주기 날짜 선택 창 */}
      <Modal
        animationType="none"
        transparent={true}
        visible={DateModalVisible}
        onRequestClose={() => {
          setModalVisible(!DateModalVisible);
        }}
      >
        <ModalContainer>
          <ModalBox>
            <ModalHeader>
              <Text style={{ fontWeight: "bold" }}>언제 물을 주었나요?</Text>
              <Pressable onPress={() => setDateModalVisible(!DateModalVisible)}>
                <Icon type="AntDesign" name="close" style={{ fontSize: 20 }} />
              </Pressable>
            </ModalHeader>
            {/* 날짜 선택 View(추후 Date Picker 구현할 예정) */}
            <View style={{ flex: 1 }}></View>
            <ModalButton1
              onPress={() => {
                setDateModalVisible(!DateModalVisible);
                setCompleteModalVisible(!CompleteModalVisible);
              }}
            >
              <Text>선택 완료</Text>
            </ModalButton1>
          </ModalBox>
        </ModalContainer>
      </Modal>

      {/* 물주기 완료 모달 */}
      <Modal
        animationType="none"
        transparent={true}
        visible={CompleteModalVisible}
        onRequestClose={() => {
          setCompleteModalVisible(!CompleteModalVisible);
        }}
      >
        <ModalContainer>
          <ModalBox>
            <ModalHeader>
              <Text
                style={{
                  fontWeight: "bold",
                }}
              >
                물주기 완료
              </Text>
            </ModalHeader>
            {/* 날짜 선택 View(추후 Date Picker 구현할 예정) */}
            <View style={{ flex: 1 }}></View>
            <ModalButton1
              onPress={() => {
                setCompleteModalVisible(!CompleteModalVisible);
              }}
            >
              <Text>확인</Text>
            </ModalButton1>
          </ModalBox>
        </ModalContainer>
      </Modal>
    </Container>
  );
}
