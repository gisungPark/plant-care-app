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

import { ThemeProvider } from "styled-components";
import theme from "../../assets/theme/index";
import {
  SurveyButton,
  SurveyButtonText,
  SurveyQText,
} from "../../assets/theme/surveystyles";
import RadioButtonRN from "radio-buttons-react-native";
import ProgressBar from "../../components/progressbar";

export function SurveyquestionScreen({ navigation }) {
  const data1 = [
    {
      label: "있어요",
    },
    {
      label: "없어요",
    },
  ];

  const data2 = [
    {
      label: "일주일에 2-3번 이상",
    },
    {
      label: "일주일에 2-3번 이상",
    },
    {
      label: "일주일에 2-3번 이상",
    },
    {
      label: "일주일에 2-3번 이상",
    },
  ];

  const ProgressData = { completed: 50 };

  return (
    <Container style={styles.container}>
      <View style={styles.titlecontainer}>
        <ProgressBar completed={ProgressData.completed} />
      </View>
      <View style={styles.contentcontainer}>
        <SurveyQText style={styles.contentques} multiline={true}>
          식물을 키워본 경험이 있나요?
        </SurveyQText>
        <View style={styles.contentoptions}>
          {/* <RadioButtonRN
            data={data1}
            selectedBtn={(e) => console.log(e)}
            style={{ flex: 1, flexDirection: "row" }}
            boxStyle={styles.optionshort}
            textStyle={styles.optiontext}
            icon={<Icon></Icon>}
            circleSize={10}
            activeColor={"#8AD169"}
            deactiveColor={"transparent"}
            boxActiveBgColor={"#F9F9F9"}
            boxDeactiveBgColor={"#EFEFEF"}
            textColor={"black"}
          ></RadioButtonRN> */}
          <RadioButtonRN
            data={data2}
            selectedBtn={(e) => console.log(e)}
            style={{
              flex: 1,
              height: 250,
            }}
            boxStyle={styles.optionlong}
            textStyle={styles.optiontext}
            icon={<Icon></Icon>}
            circleSize={10}
            activeColor={"#8AD169"}
            deactiveColor={"transparent"}
            boxActiveBgColor={"#F9F9F9"}
            boxDeactiveBgColor={"#EFEFEF"}
            textColor={"black"}
          ></RadioButtonRN>
        </View>
      </View>
      <View style={styles.buttoncontainer}>
        <ThemeProvider theme={theme}>
          <SurveyButton onPress={() => navigation.navigate("Surveyresult")}>
            <SurveyButtonText>계속</SurveyButtonText>
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
    flexDirection: "row",
    justifyContent: "flex-start",
  },

  optionshort: {
    marginHorizontal: 10,
    flex: 1,
    height: 100,
    borderRadius: 10,
  },
  optionlong: {
    marginHorizontal: 10,
    flex: 1,
    borderRadius: 10,
  },
  optiontext: {
    padding: 15,
    alignSelf: "flex-start",
    textAlign: "center",
  },
});
