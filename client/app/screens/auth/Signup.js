import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  Alert,
  ActivityIndicator,
} from 'react-native';
import {Container, Label} from 'native-base';
import {AuthButton, AuthButtonText} from '../../assets/theme/authstyles';
import {KeyboardAwareScrollView} from 'react-native-keyboard-aware-scroll-view';
import {useState} from 'react';
import firebase from '../../config/firebase';
import {userInfo, registerDevice} from '../../api/auth';
import {useDispatch} from 'react-redux';
import {setProfile} from '../../reducers/profileReducer';

// MESSAGING
import messaging from '@react-native-firebase/messaging';

export function SignupScreen({navigation}) {
  // input focus variables
  const [isIDFocused, setIsIDFocused] = useState(false);
  const [isPWFocused, setIsPWFocused] = useState(false);
  const [isPWCFocused, setIsPWCFocused] = useState(false);
  // input validation variables
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [userId, setUserId] = useState('');
  // input error variables
  const [emailError, setEmailError] = useState('');
  const [pwError, setPwError] = useState('');
  const [pwcError, setPwcError] = useState('');

  // loading
  const [isLoading, setIsLoading] = useState(false);

  const dispatch = useDispatch();
  const saveProfile = (profile, provider, useremail) =>
    dispatch(setProfile(profile, provider, useremail));

  const renderLoading = () => {
    if (isLoading) {
      return (
        <ActivityIndicator
          size="large"
          color="#8AD169"
          style={{position: 'absolute', left: 0, right: 0, bottom: 0, top: 0}}
        />
      );
    } else {
      return null;
    }
  };

  const email_signIn = async () => {
    if (email && !emailError && password && !pwError) {
      try {
        setIsLoading(true);
        const credential = await firebase
          .auth()
          .createUserWithEmailAndPassword(email, password);
        const profile = await userInfo();
        //DEVICE TOKEN
        const deviceToken = await messaging().getToken();
        const device_response = await registerDevice();
        await saveProfile(profile.data.response, 'password', email);
        setIsLoading(false);
      } catch (error) {
        if (error.code === 'auth/email-already-in-use') {
          alert('이미 가입된 이메일입니다.');
        } else {
          alert('가입에 실패했습니다.');
          console.log(error);
        }
        setIsLoading(false);
      }
    } else {
      alert('가입양식에 맞춰 작성해주세요.');
      setIsLoading(false);
    }
  };

  const validateEmail = userEmail => {
    // console.log(userEmail);
    //e메일 형식
    let regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w\w+)+$/;
    if (regEmail.test(userEmail) === false) {
      // console.log('Email is not Correct');
      setEmail(userEmail);
      setEmailError('이메일 형식이 맞지 않습니다.');
    } else {
      setEmail(userEmail);
      setEmailError('');
      // console.log('Email is Correct');
    }
  };

  const validatePassword = userPW => {
    // console.log(userPW);
    // 대문자 최소 1개, 소문자 1개 이상, 숫자 1개이상, 특수문자 1개 이상, 8자리이상
    let regPW =
      /^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\d]){1,})(?=(.*[\W]){1,})(?!.*\s).{8,}$/;
    if (regPW.test(userPW) === false) {
      // console.log('대/소문자, 숫자, 특수문자 포함 8자 이상 입력해주세요');
      setPassword(userPW);
      setPwError('대/소문자, 숫자, 특수문자 포함 8자 이상 입력해주세요.');
    } else {
      setPassword(userPW);
      setPwError('');
      // console.log('PW is Correct');
    }
  };

  const validatePWC = userPWC => {
    // console.log(userPWC);
    if (userPWC !== password) {
      // console.log('비밀번호가 일치하지 않습니다');
      setPasswordConfirm(userPWC);
      setPwcError('비밀번호가 일치하지 않습니다.');
      return false;
    } else {
      setPasswordConfirm(userPWC);
      setPwcError('');
      // console.log('PWC is Correct');
    }
  };

  const submitAlert = () => {
    Alert.alert(
      '회원가입',
      '실제 사용중인 이메일을 입력하셔야 나중에 비밀번호 재설정이 가능합니다. 실제 이메일이 맞나요?',
      [
        {
          text: 'Cancel',
          onPress: () => console.log('Cancel Pressed'),
          style: 'cancel',
        },
        {text: 'OK', onPress: () => email_signIn()},
      ],
    );
  };

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
              <Text style={styles.signup}>회원가입</Text>
            </View>
          </View>
          <View style={styles.form}>
            <TextInput
              style={[
                styles.input,
                {marginTop: 30},
                emailError
                  ? styles.error
                  : isIDFocused
                  ? styles.focused
                  : styles.blurred,
              ]}
              onBlur={() => setIsIDFocused(false)}
              onFocus={() => setIsIDFocused(true)}
              placeholder="Email"
              onChangeText={userEmail => validateEmail(userEmail)}
              autoCapitalize="none"
            />
            <Label style={styles.errorlabel}>{emailError}</Label>
            <TextInput
              style={[
                styles.input,
                pwError
                  ? styles.error
                  : isPWFocused
                  ? styles.focused
                  : styles.blurred,
              ]}
              onBlur={() => setIsPWFocused(false)}
              onFocus={() => setIsPWFocused(true)}
              placeholder="비밀번호"
              onChangeText={userPW => validatePassword(userPW)}
              secureTextEntry={true}
            />
            <Label style={styles.errorlabel}>{pwError}</Label>
            <TextInput
              style={[
                styles.input,
                pwcError
                  ? styles.error
                  : isPWCFocused
                  ? styles.focused
                  : styles.blurred,
              ]}
              onBlur={() => setIsPWCFocused(false)}
              onFocus={() => setIsPWCFocused(true)}
              placeholder="비밀번호 확인"
              onChangeText={userPWC => validatePWC(userPWC)}
              secureTextEntry={true}
            />
            <Label style={styles.errorlabel}>{pwcError}</Label>

            <AuthButton full style={{marginTop: 20}}>
              <AuthButtonText title="Signup" onPress={submitAlert}>
                회원가입
              </AuthButtonText>
            </AuthButton>
          </View>
          {renderLoading()}
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
  signup: {
    fontSize: 12,
    marginTop: 8,
  },
  form: {
    flex: 2,
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingHorizontal: 60,
  },
  focused: {
    borderColor: '#8AD169',
    borderTopWidth: 1.1,
    borderBottomWidth: 1.1,
    borderLeftWidth: 1.1,
    borderRightWidth: 1.1,
  },
  error: {
    borderColor: 'red',
    borderTopWidth: 1.1,
    borderBottomWidth: 1.1,
    borderLeftWidth: 1.1,
    borderRightWidth: 1.1,
  },
  blurred: {
    borderColor: '#ECECE2',
    borderWidth: 1,
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
    fontSize: 12,
    paddingLeft: 15,
  },
  errorlabel: {
    color: 'red',
    fontSize: 10,
    alignSelf: 'flex-end',
    paddingTop: 0,
    paddingRight: 10,
    marginTop: 0,
    textAlignVertical: 'top',
    lineHeight: 12,
  },
});

// 밸리데이션
//https://stackoverflow.com/questions/54204827/react-native-form-validation
// reg
// https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
// https://stackoverflow.com/questions/62727346/how-to-validate-using-useeffect
