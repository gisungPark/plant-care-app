import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  ScrollView,
  Alert,
  ActivityIndicator,
} from 'react-native';
import {Container} from 'native-base';
import 'react-native-gesture-handler';
import {useState, useEffect} from 'react';
import {
  AuthButton,
  AuthButtonText,
  SocialButton,
  SocialButtonText,
} from '../../assets/theme/authstyles';
import {KeyboardAwareScrollView} from 'react-native-keyboard-aware-scroll-view';

//loading
import {RenderLoading} from '../../components/common/renderLoading';

// google login, firebase
import {
  GoogleSignin,
  statusCodes,
} from '@react-native-google-signin/google-signin';
import auth from '@react-native-firebase/auth';
import {WEB_CLIENT_ID} from '@env';

//redux
import {useDispatch} from 'react-redux';
import {setProfile} from '../../reducers/profileReducer';

//api
import {userInfo, registerDevice} from '../../api/auth';

// MESSAGING
import messaging from '@react-native-firebase/messaging';

export function LoginScreen({navigation}) {
  // redux
  const dispatch = useDispatch();
  const saveProfile = (profile, provider, useremail) =>
    dispatch(setProfile(profile, provider, useremail));

  // input focus
  const [isIDFocused, setIsIDFocused] = useState(false);
  const [isPWFocused, setIsPWFocused] = useState(false);

  // input variables
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  //loading
  const [isLoading, setIsLoading] = useState(false);

  const google_signIn = async () => {
    try {
      setIsLoading(true);
      await GoogleSignin.hasPlayServices();
      const googleUserInfo = await GoogleSignin.signIn();
      const credential = await auth.GoogleAuthProvider.credential(
        googleUserInfo.idToken,
        googleUserInfo.accessToken,
      );
      const response = await auth().signInWithCredential(credential);
      const googleMail = googleUserInfo.user.email;
      const profile = await userInfo();
      // console.log('profile1', profile.data.response);
      // console.log('profile2', profile);
      //DEVICE TOKEN
      const deviceToken = await messaging().getToken();
      const device_response = await registerDevice();

      await saveProfile(profile.data.response, 'google.com', googleMail);
      setIsLoading(false);
    } catch (error) {
      if (error.code === statusCodes.SIGN_IN_CANCELLED) {
        alert('Cancel');
      } else if (error.code === statusCodes.IN_PROGRESS) {
        alert('Signin in progress');
      } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
        alert('PLAY_SERVICES_NOT_AVAILABLE');
      } else {
        alert('some other happend');
        console.log(error.code, error.message);
      }
    }
    setIsLoading(false);
  };

  const email_logIn = async () => {
    if (email && password) {
      try {
        setIsLoading(true);
        let response = await auth().signInWithEmailAndPassword(email, password);
        if (response && response.user) {
          const profile = await userInfo();
          const deviceToken = await messaging().getToken();
          const device_response = await registerDevice();
          await saveProfile(profile.data.response, 'password', email);
          setIsLoading(false);
        }
      } catch (error) {
        if (error.code === 'auth/wrong-password') {
          Alert.alert('로그인 오류', '비밀번호가 일치하지 않습니다.');
        } else if (error.code === 'auth/user-not-found') {
          Alert.alert('로그인 오류', '가입되지 않은 이메일입니다.');
        } else if (error.code === 'auth/invalid-email') {
          Alert.alert('로그인 오류', '이메일 형식이 유효하지 않습니다.');
        } else if (error.code === 'auth/too-many-requests') {
          Alert.alert(
            '로그인 오류',
            '여러번 로그인에 실패하여 로그인이 차단 되었습니다. 잠시 후에 다시 시도하거나 관리자에게 문의해주세요.',
          );
        } else {
          Alert.alert('로그인 오류', '로그인에 실패했습니다.');
        }
        setIsLoading(false);
      }
    } else {
      Alert.alert('로그인 오류', '이메일과 비밀번호를 입력해주세요');
      // console.log('email, password no');
      setIsLoading(false);
    }
    setEmail('');
    setPassword('');
  };

  useEffect(() => {
    GoogleSignin.configure({
      scopes: ['email', 'profile'],
      webClientId: WEB_CLIENT_ID,
      offlineAccess: true,
    });
  }, []);

  return (
    <ScrollView>
      <KeyboardAwareScrollView>
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
              <TextInput
                style={[
                  styles.input,
                  isIDFocused ? styles.focused : styles.blurred,
                ]}
                value={email}
                onBlur={() => setIsIDFocused(false)}
                onFocus={() => setIsIDFocused(true)}
                placeholder="Email"
                onChangeText={userEmail => setEmail(userEmail)}
                autoCapitalize="none"
              />
              <TextInput
                style={[
                  styles.input,
                  isPWFocused ? styles.focused : styles.blurred,
                ]}
                value={password}
                placeholder="Password"
                onBlur={() => setIsPWFocused(false)}
                onFocus={() => setIsPWFocused(true)}
                onChangeText={userPW => setPassword(userPW)}
                autoCapitalize="none"
                secureTextEntry={true}
              />
            </View>
            <View style={styles.pairitem}>
              <AuthButton full>
                <AuthButtonText onPress={email_logIn}>로그인</AuthButtonText>
              </AuthButton>
              <SocialButton full>
                <SocialButtonText onPress={google_signIn}>
                  Sign in with Google
                </SocialButtonText>
              </SocialButton>
            </View>

            <View style={styles.textlinkwrap}>
              <Text
                style={styles.textleft}
                title="Signup"
                onPress={() => navigation.navigate('Signup')}>
                회원가입
              </Text>
              <Text style={styles.textmiddle}>|</Text>
              <Text
                style={styles.textright}
                onPress={() => navigation.navigate('ResetPassword')}>
                비밀번호 재설정
              </Text>
            </View>
          </View>
          <RenderLoading isLoading={isLoading}></RenderLoading>
        </Container>
      </KeyboardAwareScrollView>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#F9F9F9',
  },
  logo: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'flex-start',
    paddingHorizontal: 60,
  },
  halftop: {
    flex: 3,
    justifyContent: 'flex-end',
    alignItems: 'flex-start',
  },
  halfbottom: {
    flex: 2,
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
  },
  logotext: {
    fontSize: 35,
    fontWeight: '700',
    color: '#29582C',
  },
  form: {
    flex: 2,
    paddingHorizontal: 60,
  },
  pairitem: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    alignSelf: 'stretch',
    marginBottom: 0,
  },
  textlinkwrap: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  passwordlink: {
    flex: 3,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  textleft: {
    flex: 2,
    textAlign: 'right',
  },
  textmiddle: {
    flex: 1,
    textAlign: 'center',
  },
  textright: {
    flex: 3,
    textAlign: 'left',
  },
  singleitem: {
    marginVertical: 4,
    borderRadius: 12,
    backgroundColor: 'white',
  },
  focused: {
    borderColor: '#8AD169',
    borderTopWidth: 1.1,
    borderBottomWidth: 1.1,
    borderLeftWidth: 1.1,
    borderRightWidth: 1.1,
  },
  blurred: {
    borderColor: '#ECECE2',
  },
  input: {
    justifyContent: 'flex-start',
    alignItems: 'center',
    marginVertical: 5,
    borderRadius: 12,
    borderColor: 'grey',
    borderWidth: 1,
    backgroundColor: 'white',
    width: '100%',
    paddingLeft: 15,
  },
});

//https://medium.com/@asumansenol/firebase-authentication-using-google-facebook-and-email-password-log-in-in-expo-react-native-3bb5ac8461c7
//https://www.youtube.com/watch?v=ZcaQJoXY-3Q&list=PLy9JCsy2u97nVN5GxrjC6rv9XfyxoDtB_

//https://dev.to/harleypadua/google-authentication-with-expo-and-react-native-2l24

//클래스형
//https://www.section.io/engineering-education/react-native-firebase-google-authentication/
