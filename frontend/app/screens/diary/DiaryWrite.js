// react
import React from "react";
import { ScrollView, Text, TextInput, View } from "react-native";
import "react-native-gesture-handler";

// style
import styled from "styled-components";
import { Icon } from "native-base";

// 다이어리 페이지 컨테이너
const DiaryWriteConatiner = styled.View`
  flex: 1;
  background-color: ${({ theme }) => theme.colors.background};
  padding: 20px;
`;

// 날짜 선택 영역
const DateSelect = styled.View`
  flex: 1.5;
  /* background-color: red; */
`;

// 소제목 태그
const SubHeading = styled.View`
  /* background-color: white; */
  width: auto;
  border-radius: 10px;
  align-items: flex-start;
  margin-left: 10px;
`;

// 소제목 글씨
const SubHeadingText = styled.Text`
  /* flex: 1; */
  font-weight: bold;
`;

// 사진 영역
const ImageSelect = styled.View`
  flex: 3;
  /* background-color: green; */
`;

// 사진 선택 영역 컨테이너
const Image = styled.View`
  flex: 1;
  flex-direction: row;
  margin-bottom: 50px;
`;

// 사진 선택 영역 박스
const ImageBox = styled.View`
  flex: 1;
  background-color: white;
  margin: 10px;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
`;

// 글 작성 영역
const TextBox = styled.View`
  flex: 3;
  /* background-color: grey; */
`;

// 글 작성 textInput 박스
const TextInputBox = styled.View`
  background-color: white;
  height: 150px;
  margin: 10px;
  padding-left: 10px;
`;

// 완료 버튼 영역
const CompleteButton = styled.TouchableOpacity`
  flex: 0.7;
  background-color: ${({ theme }) => theme.colors.lightGreenButton};
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin: 10px;
`;

// 완료 버튼 글씨
const CompoleteButtonText = styled.Text`
  font-weight: bold;
  font-size: 16px;
  color: ${({ theme }) => theme.colors.darkGreen};
`;

export function DiaryWriteScreen({ navigation }) {
  return (
    <DiaryWriteConatiner>
      <DateSelect>
        <SubHeading>
          <SubHeadingText>날짜 선택</SubHeadingText>
        </SubHeading>
        <View></View>
      </DateSelect>
      <ImageSelect>
        <SubHeading>
          <SubHeadingText>사진 선택</SubHeadingText>
        </SubHeading>
        <Image>
          <ImageBox>
            <Icon
              type="MaterialCommunityIcons"
              name="image-multiple"
              style={{ fontSize: 50 }}
            />
            <Text>사진 선택</Text>
          </ImageBox>
          <ImageBox>
            <Icon
              type="MaterialCommunityIcons"
              name="camera"
              style={{ fontSize: 50 }}
            />
            <Text>사진 촬영</Text>
          </ImageBox>
        </Image>
      </ImageSelect>
      <TextBox>
        <SubHeading>
          <SubHeadingText>글 작성</SubHeadingText>
        </SubHeading>
        <TextInputBox>
          <TextInput
            multiline
            numberOfLines={5}
            placeholder="식물과 있었던 일을 기록해주세요 :)"
          />
        </TextInputBox>
      </TextBox>
      <CompleteButton>
        <CompoleteButtonText>완료</CompoleteButtonText>
      </CompleteButton>
    </DiaryWriteConatiner>
  );
}
