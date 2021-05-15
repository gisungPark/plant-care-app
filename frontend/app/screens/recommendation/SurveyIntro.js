import React from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  PickerIOSComponent,
  Button,
} from "react-native";
import {
  Container,
  Header,
  Content,
  Input,
  Item,
  StyleProvider,
} from "native-base";
import { FontAwesome } from "@expo/vector-icons";
import { Ionicons } from "@expo/vector-icons";
import { ThemeProvider } from "styled-components";
import theme from "../../assets/theme/index";
import {
  SurveyButton,
  SurveyButtonText,
} from "../../assets/theme/surveystyles";

export function SurveyintroScreen({ navigation }) {
  return (
    <Container style={styles.container}>
      <View style={styles.titlecontainer}>
        <Text style={styles.titletext}>
          간단한 설문을 통해 맞춤 식물을 추천해 드립니다.
        </Text>
      </View>
      <View style={styles.contentcontainer}>
        <Image
          source={{
            uri:
              "https://www.ikea.com/kr/en/images/products/smycka-artificial-flower-rose-red__0903311_pe596728_s5.jpg",
          }}
          style={styles.contentimg}
        />
        <Text style={styles.contentdesc}>
          본 테스트는 사용자의 설문조사 입력 데이터를 바탕으로 사용자의 성향에
          맞는 식물을 추천하기 위한 설문입니다.본인의 생활패턴과 성향을 있는
          그대로 답변해주세요.
        </Text>
        <View style={styles.contentinfo}>
          <View style={styles.infoleft}>
            <FontAwesome size={25} name="pencil"></FontAwesome>
            <Text> 전체 몇문항</Text>
          </View>
          <View style={styles.inforight}>
            <Ionicons
              size={25}
              // style={styles.textpadding}
              name="alarm-outline"
            ></Ionicons>
            <Text> 약 몇분 소요</Text>
          </View>
        </View>
      </View>
      <View style={styles.buttoncontainer}>
        <ThemeProvider theme={theme}>
          <SurveyButton onPress={() => navigation.navigate("Surveyquestion")}>
            <SurveyButtonText>시작</SurveyButtonText>
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
    flex: 2,
    justifyContent: "flex-end",
    paddingBottom: 25,
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
  titletext: {
    fontWeight: "600",
    fontSize: 17,
    paddingVertical: 10,
  },
  contentimg: {
    flex: 4,
    alignSelf: "stretch",
    paddingVertical: 5,
  },
  contentdesc: {
    flex: 1,
    textAlign: "center",
    alignSelf: "center",
    paddingVertical: 5,
    lineHeight: 16,
  },
  contentinfo: {
    flex: 1,
    paddingVertical: 5,
    flexDirection: "row",
  },
  infoleft: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
    marginRight: 10,
    borderRadius: 10,
  },
  inforight: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
    marginLeft: 10,
    borderRadius: 10,
  },
});
