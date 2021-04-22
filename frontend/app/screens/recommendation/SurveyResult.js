import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  PickerIOSComponent,
} from "react-native";
import {
  Container,
  Header,
  Content,
  Input,
  Item,
  StyleProvider,
  Button,
  Icon,
} from "native-base";

import styled, { ThemeProvider } from "styled-components";
import theme from "../../assets/theme/index";
import {
  SurveyButton,
  SurveyButtonText,
  SurveyQText,
} from "../../assets/theme/surveystyles";
import RadioButtonRN from "radio-buttons-react-native";
import ProgressBar from "../../components/progressbar";

export function SurveyresultScreen({ navigation }) {
  const recom = {
    plantname: "싱고니움",
    imageURI:
      "https://o.remove.bg/downloads/559acbae-9756-4d71-a518-a46367902275/image-removebg-preview.png",
  };

  const ProgressData = { completed: 100 };

  return (
    <Container style={styles.container}>
      <View style={styles.titlecontainer}>
        <ProgressBar completed={ProgressData.completed} />
      </View>
      <View style={styles.contentcontainer}>
        <SurveyQText style={styles.contentques} multiline={true}>
          Dasol 님에게 {"\n"}딱 맞는 식물을 추천해 드릴게요.
        </SurveyQText>
        <View style={styles.contentoptions}>
          <View style={styles.recomwrap}>
            <View style={styles.recom}>
              <Image
                style={styles.recomimg}
                source={{
                  uri: recom.imageURI,
                }}
              />
              <Text style={styles.recomtext}>{recom.plantname}</Text>
            </View>
            <View style={styles.recom}>
              <Image
                style={styles.recomimg}
                source={{
                  uri: recom.imageURI,
                }}
              />
              <Text style={styles.recomtext}>{recom.plantname}</Text>
            </View>
          </View>
          <View
            style={{
              alignSelf: "flex-end",
              marginTop: 7,
              color: "#808285",
            }}
          >
            <Text>각 식물을 눌러 상세 설명을 확인해 보세요.</Text>
          </View>
        </View>
      </View>
      <View style={styles.buttoncontainer}>
        <ThemeProvider theme={theme}>
          <SurveyButton>
            <SurveyButtonText>완료</SurveyButtonText>
          </SurveyButton>
        </ThemeProvider>
      </View>
    </Container>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#F9F9F9",
  },
  titlecontainer: {
    flex: 1.5,
    justifyContent: "center",
    alignItems: "center",
  },
  contentcontainer: {
    flex: 5,
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 40,
  },
  buttoncontainer: {
    flex: 1,
    justifyContent: "flex-start",
    alignSelf: "flex-end",
    marginTop: 20,
    paddingHorizontal: 40,
  },
  contentques: {
    display: "flex",
    flex: 1,
    textAlignVertical: "center",
    textAlign: "left",
    paddingVertical: 5,
  },
  contentoptions: {
    flex: 5,
    paddingVertical: 5,

    justifyContent: "center",
    alignItems: "center",
  },
  recomwrap: {
    flexDirection: "row",
  },
  recom: {
    flex: 1,
    borderRadius: 10,
    backgroundColor: "#F2F5E1",
    marginHorizontal: 5,
    height: 190,
    justifyContent: "center",
  },
  recomimg: {
    alignSelf: "stretch",
    flex: 8,
    padding: 0,
    justifyContent: "center",
    resizeMode: "contain",
    transform: [{ scale: 0.7 }],
  },
  recomtext: { textAlign: "center", flex: 2, fontWeight: "800" },
});
