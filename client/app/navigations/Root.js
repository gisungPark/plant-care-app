import React, {Component} from 'react';
import {useState, useEffect} from 'react';
// import { StatusBar } from "expo-status-bar";
import {StatusBar} from 'react-native';

import 'react-native-gesture-handler';

// react navigation
import {NavigationContainer} from '@react-navigation/native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';

//stacks
import AuthStack from './AuthStack';
import DiaryStacks from './DiaryStack';
import HomeStacks from './HomeStack';
import RecommendationStacks from './RecommendationStack';
import ProfileStacks from './ProfileStack';
import ArStacks from './ArStack';

// style
import theme from '../assets/theme/index';
import {ThemeProvider} from 'styled-components';
import {Icon} from 'native-base';

// redux
import {useSelector, useDispatch} from 'react-redux';
import firebase from '../config/firebase';
// import firebase from '@react-native-firebase/app';

import auth from '@react-native-firebase/auth';
import {LoadingScreen} from '../screens/auth/Loading';
import {set} from 'react-native-reanimated';
// import {addUid, addUser} from '../reducers/authReducer';
import {setPlants} from '../reducers/plantReducer';
import {setProfile, setUserID} from '../reducers/profileReducer';
import {setStatus} from '../reducers/rootReducer';
import {userInfo} from '../../app/api/auth';

const Tab = createBottomTabNavigator();

function Tabs() {
  return (
    <Tab.Navigator
      screenOptions={({route}) => ({
        tabBarIcon: ({focused}) => {
          if (route.name === '홈') {
            return (
              <Icon
                type="Ionicons"
                name={focused ? 'home-sharp' : 'home-outline'}
                style={{color: focused ? '#8AD169' : '#808285', fontSize: 20}}
              />
            );
          } else if (route.name === '다이어리') {
            return (
              <Icon
                type="Ionicons"
                name="calendar-sharp"
                style={{color: focused ? '#8AD169' : '#808285', fontSize: 20}}
              />
            );
          } else if (route.name === 'AR') {
            return (
              <Icon
                type="MaterialCommunityIcons"
                name="cube-scan"
                style={{color: focused ? '#8AD169' : '#808285', fontSize: 22}}
              />
            );
          } else if (route.name === '프로필') {
            return (
              <Icon
                type="MaterialCommunityIcons"
                name={focused ? 'account' : 'account-outline'}
                style={{
                  color: focused ? '#8AD169' : '#808285',
                  fontSize: 25,
                  marginTop: 3,
                }}
              />
            );
          }
        },
      })}
      tabBarOptions={{
        activeTintColor: '#8AD169', // 탭 클릭했을 경우 색깔
        inactiveTintColor: '#808285', // 탭 클릭하지 않았을 경우 색깔
        style: {height: 55, paddingBottom: 5}, // bottom tab 높이
      }}>
      <Tab.Screen name="홈" component={HomeStacks} />
      <Tab.Screen name="다이어리" component={DiaryStacks} />
      <Tab.Screen name="AR" component={ArStacks} />
      <Tab.Screen name="프로필" component={ProfileStacks} />
    </Tab.Navigator>
  );
}

export default function Root() {
  const [isLoading, setIsLoading] = useState(true);

  const {uid} = useSelector(state => ({
    uid: state.profileReducer.userId,
    // isLoading: state.rootReducer.isLoading,
  }));

  const dispatch = useDispatch();
  const savePlants = plants => dispatch(setPlants(plants));
  // const saveUid = userId => dispatch(setUserID(userId));
  const saveProfile = (profile, provider, useremail) =>
    dispatch(setProfile(profile, provider, useremail));

  // const setIsLoading = status => dispatch(setStatus(status));

  //삭제예정
  // printToken = async () => {
  //   const token = await auth().currentUser.getIdToken(true);
  //   console.log(token);
  // };

  saveUserInfo = async user => {
    if (user) {
      // await printToken();
      const allAboutUser = await userInfo();
      const myPlants = allAboutUser.data.plants;
      const myInfo = allAboutUser.data.response;
      savePlants(myPlants);
      saveProfile(myInfo, user.providerData[0].providerId, user.email);
      setIsLoading(false);

      // console.log('room loading status', isLoading);
    } else {
      setIsLoading(false);
    }
    setIsLoading(false);
  };

  useEffect(() => {
    var unsubscribe = firebase.auth().onAuthStateChanged(saveUserInfo);
    unsubscribe();
  }, [uid]);

  return (
    <ThemeProvider theme={theme}>
      <StatusBar
        barStyle="dark-content"
        hidden={true}
        backgroundColor="transparent"
        translucent={true}
      />
      {isLoading ? (
        <LoadingScreen />
      ) : (
        <NavigationContainer>
          {uid ? <Tabs /> : <AuthStack />}
        </NavigationContainer>
      )}
    </ThemeProvider>
  );
}

//"collapseKey": "com.green", "data": {}, "from": "1052953661921", "messageId": "0:1620355844004654%3b8058563b805856", "notification": {"android": {}, "body": "테스팅중입니다", "title": "아아아"}, "sentTime": 1620355844000, "ttl": 2419200}
