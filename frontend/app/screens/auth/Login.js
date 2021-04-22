import React from "react";
import { View, Text, StyleSheet, SafeAreaView, TextInput } from "react-native";
import {
  Container,
  Header,
  Content,
  Input,
  Item,
  Button,
  StyleProvider,
} from "native-base";
import "react-native-gesture-handler";
import { useState } from "react";
import {
  AuthButton,
  AuthButtonText,
  SocialButton,
  SocialButtonText,
} from "../../assets/theme/authstyles";

//google login
// import {
//   GoogleSignin,
//   GoogleSigninButton,
//   statusCodes,
// } from "react-native-google-signin";
// import * as Google from "expo-google-app-auth";
import {
  GoogleClientId,
  apiKey,
  messagingSenderId,
  appId,
  authDomain,
  databaseURL,
  projectId,
  storageBucket,
} from "@env";
import * as firebase from "firebase";

const firebaseConfig = {
  apiKey: apiKey,
  authDomain: authDomain,
  databaseURL: databaseURL,
  projectId: projectId,
  storageBucket: storageBucket,
  messagingSenderId: messagingSenderId,
  appId: appId,
  // measurementId: 'G-2NQFW6NDY4',
};

export function LoginScreen({ navigation }) {
  const [isIDFocused, setIsIDFocused] = useState(false);
  const [isPWFocused, setIsPWFocused] = useState(false);

  // google login
  const [loggedIn, setloggedIn] = useState(false);
  const [userInfo, setuserInfo] = useState([]);

  // _signIn = async () => {
  //   try {
  //     await GoogleSignin.hasPlayServices();
  //     const { accessToken, idToken } = await GoogleSignin.signIn();
  //     setloggedIn(true);
  //     console.log("hi");
  //   } catch (error) {
  //     if (error.code === statusCodes.SIGN_IN_CANCELLED) {
  //       // user cancelled the login flow
  //       alert("Cancel");
  //     } else if (error.code === statusCodes.IN_PROGRESS) {
  //       alert("Signin in progress");
  //       // operation (f.e. sign in) is in progress already
  //     } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
  //       alert("PLAY_SERVICES_NOT_AVAILABLE");
  //       // play services not available or outdated
  //     } else {
  //       // some other error happened
  //     }
  //   }
  // };
  // useEffect(() => {
  //   GoogleSignin.configure({
  //     scopes: ["email"], // what API you want to access on behalf of the user, default is email and profile
  //     webClientId: GoogleClientId, // client ID of type WEB for your server (needed to verify user ID and offline access)
  //     offlineAccess: true, // if you want to access Google API on behalf of the user FROM YOUR SERVER
  //   });
  // }, []);

  // async function signInWithGoogleAsync() {
  //   try {
  //     const result = await Google.logInAsync({
  //       behavior: "web",
  //       iosClientId: IOS_CLIENT_ID,
  //       //androidClientId: AND_CLIENT_ID,
  //       scopes: ["profile", "email"],
  //     });

  //     if (result.type === "success") {
  //       return result.accessToken;
  //     } else {
  //       return { cancelled: true };
  //     }
  //   } catch (e) {
  //     return { error: true };
  //   }
  // }
  return (
    <Container style={styles.container}>
      <View style={styles.logo}>
        <View style={styles.halftop}>
          <Text style={styles.logotext}>Green</Text>
        </View>
        <View style={styles.halfbottom}>
          <Text style={styles.logotext}>Fingers</Text>
        </View>
      </View>
      <View style={styles.form}>
        <View style={styles.pairitem}>
          <Item
            style={[
              styles.singleitem,
              isIDFocused ? styles.focused : styles.blurred,
            ]}
            regular
          >
            <Input
              placeholder="ID"
              onBlur={() => setIsIDFocused(false)}
              onFocus={() => setIsIDFocused(true)}
            />
          </Item>
          <Item
            style={[
              styles.singleitem,
              isPWFocused ? styles.focused : styles.blurred,
            ]}
            regular
          >
            <Input
              placeholder="PASSWORD"
              onBlur={() => setIsPWFocused(false)}
              onFocus={() => setIsPWFocused(true)}
            />
          </Item>
        </View>
        <View style={styles.pairitem}>
          <AuthButton full>
            <AuthButtonText>로그인</AuthButtonText>
          </AuthButton>
          <SocialButton full>
            <SocialButtonText>Sign in with Google</SocialButtonText>
          </SocialButton>
        </View>
        <View style={styles.textlinkwrap}>
          <Text
            style={styles.textleft}
            title="Signup"
            onPress={() => navigation.navigate("Signup")}
          >
            회원가입
          </Text>
          <Text style={styles.textmiddle}>|</Text>
          <Text style={styles.textright}>비회원 입장</Text>
        </View>
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
    justifyContent: "center",
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
  form: {
    flex: 2,
    paddingHorizontal: 60,
  },
  pairitem: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    alignSelf: "stretch",
    marginBottom: 0,
  },
  textlinkwrap: {
    flex: 1,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "flex-start",
  },
  textleft: {
    flex: 5,
    textAlign: "right",
  },
  textmiddle: {
    flex: 1,
    textAlign: "center",
  },
  textright: {
    flex: 5,
    textAlign: "left",
  },
  singleitem: {
    marginVertical: 4,
    borderRadius: 12,
    backgroundColor: "white",
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

//https://medium.com/@asumansenol/firebase-authentication-using-google-facebook-and-email-password-log-in-in-expo-react-native-3bb5ac8461c7
//https://www.youtube.com/watch?v=ZcaQJoXY-3Q&list=PLy9JCsy2u97nVN5GxrjC6rv9XfyxoDtB_

//https://dev.to/harleypadua/google-authentication-with-expo-and-react-native-2l24

//클래스형
//https://www.section.io/engineering-education/react-native-firebase-google-authentication/
