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
    <Container style={styles.container}>
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
        <Item
          style={[
            styles.singleitem,
            isIDFocused ? styles.focused : styles.blurred,
          ]}
          regular
        >
          <Input
            onBlur={() => setIsIDFocused(false)}
            onFocus={() => setIsIDFocused(true)}
            style={{ paddingLeft: 15 }}
            placeholder="이름"
          />
          <Button style={styles.idcheckbtn}>
            <Text style={styles.textpadding}>중복확인</Text>
            <AntDesign
              size={10}
              style={styles.textpadding}
              name="checkcircle"
            ></AntDesign>
          </Button>
        </Item>
        <Item
          style={[
            styles.singleitem,
            isNNFocused ? styles.focused : styles.blurred,
          ]}
          regular
        >
          <Input
            onBlur={() => setIsNNFocused(false)}
            onFocus={() => setIsNNFocused(true)}
            style={{ paddingLeft: 15 }}
            placeholder="닉네임"
          />
          <Button style={styles.idcheckbtn}>
            <Text style={styles.textpadding}>중복확인</Text>
            <AntDesign
              size={10}
              style={styles.textpadding}
              name="checkcircle"
            ></AntDesign>
          </Button>
        </Item>
        <Item
          style={[
            styles.singleitem,
            isPWFocused ? styles.focused : styles.blurred,
          ]}
          regular
        >
          <Input
            onBlur={() => setIsPWFocused(false)}
            onFocus={() => setIsPWFocused(true)}
            placeholder="비밀번호"
            style={{ paddingLeft: 15 }}
          />
        </Item>
        <Item
          style={[
            styles.singleitem,
            isPWCFocused ? styles.focused : styles.blurred,
          ]}
          regular
        >
          <Input
            onBlur={() => setIsPWCFocused(false)}
            onFocus={() => setIsPWCFocused(true)}
            placeholder="비밀번호 확인"
            style={{ paddingLeft: 15 }}
          />
        </Item>
        <AuthButton full style={{ marginTop: 20 }}>
          <AuthButtonText title="Home">회원가입</AuthButtonText>
        </AuthButton>
      </View>
    </Container>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#F9F9F9",
  },
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
    borderRadius: 12,
    backgroundColor: "white",
  },
  idcheckbtn: {
    backgroundColor: "transparent",
    paddingRight: 5,
    justifyContent: "center",
    alignSelf: "center",
    elevation: 0, //테두리 그림자 없애는거
  },
  textpadding: {
    paddingHorizontal: 3,
  },
  focused: {
    borderColor: "#8AD169",
    borderTopWidth: 1.1,
    borderBottomWidth: 1.1,
    borderLeftWidth: 1.1,
    borderRightWidth: 1.1,
  },
  blurred: {
    borderColor: "#ECECE2",
  },
});
