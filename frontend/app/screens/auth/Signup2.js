import React from "react";
import { View, Text, StyleSheet } from "react-native";
import {
  Container,
  Header,
  Content,
  Input,
  Item,
  Button,
  StyleProvider,
} from "native-base";
import { AntDesign } from "@expo/vector-icons";
import { AuthButton, AuthButtonText } from "../../assets/theme/authstyles";
import { useState } from "react";

export function SignupScreen({ navigation }) {
  const [isIDFocused, setIsIDFocused] = useState(false);
  const [isPWFocused, setIsPWFocused] = useState(false);
  const [isNNFocused, setIsNNFocused] = useState(false);
  const [isPWCFocused, setIsPWCFocused] = useState(false);

  return (
    <Container>
      <View style={styles.logo}>
        <View style={styles.halftop}>
          <Text style={styles.logotext}>Green</Text>
        </View>
        <View style={styles.halfbottom}>
          <Text style={styles.logotext}>Fingers</Text>
          <Text style={styles.signup}>회원가입</Text>
        </View>
      </View>
      <View style={styles.form}>
        <Item style={styles.singleitem} regular>
          <Input style={styles.inputfocus} placeholder="이름" />
          <Button style={styles.idcheckbtn}>
            <Text style={styles.textpadding}>중복확인</Text>
            <AntDesign
              size={10}
              style={styles.textpadding}
              name="checkcircle"
            ></AntDesign>
          </Button>
        </Item>
        <Item style={styles.singleitem} regular>
          <Input placeholder="닉네임" />
          <Button style={styles.idcheckbtn}>
            <Text style={styles.textpadding}>중복확인</Text>
            <AntDesign
              size={10}
              style={styles.textpadding}
              name="checkcircle"
            ></AntDesign>
          </Button>
        </Item>
        <Item style={styles.singleitem} regular>
          <Input placeholder="비밀번호" />
        </Item>
        <Item style={styles.singleitem} regular>
          <Input placeholder="비밀번호 확인" />
        </Item>
        <AuthButton full>
          <AuthButtonText
            title="Home"
            onPress={() => navigation.replace("main")}
          >
            회원가입
          </AuthButtonText>
        </AuthButton>
      </View>
    </Container>
  );
}

const styles = StyleSheet.create({
  logo: {
    flex: 1,
    alignItems: "flex-start",
    paddingHorizontal: 60,
  },
  halftop: {
    flex: 3,
    justifyContent: "flex-end",
    alignItems: "flex-start",
  },
  halfbottom: {
    flex: 2,
    justifyContent: "flex-start",
    alignItems: "flex-start",
  },
  logotext: {
    fontSize: 35,
    fontWeight: "700",
    color: "#29582C",
  },
  signup: {
    fontSize: 12,
    marginTop: 8,
  },
  form: {
    flex: 2,
    justifyContent: "flex-start",
    alignItems: "center",
    paddingHorizontal: 60,
  },
  singleitem: {
    justifyContent: "flex-start",
    alignItems: "center",
    marginVertical: 8,
  },
  idcheckbtn: {
    backgroundColor: "transparent",
    paddingRight: 5,
    justifyContent: "center",
    alignSelf: "center",
  },
  textpadding: {
    paddingHorizontal: 3,
  },
  inputfocus: {
    borderColor: "green",
    color: "green",
  },
});
