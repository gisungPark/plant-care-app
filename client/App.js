import React from 'react';
import {useState, useEffect} from 'react';
// import { StatusBar } from "expo-status-bar";
import {Dimensions, StatusBar, Alert, Modal, LogBox} from 'react-native';

import {StyleSheet, Text, View, Button} from 'react-native';
import 'react-native-gesture-handler';

// style
import theme from './app/assets/theme/index';
import {ThemeProvider} from 'styled-components';
import {Icon} from 'native-base';

//navigation
import Root from './app/navigations/Root';

//redux
import allReducer from './app/reducers/index.js';
import {createStore} from 'redux';
import {Provider as StoreProvider} from 'react-redux';

//firebase
import messaging from '@react-native-firebase/messaging';

//messaging
import MessageModal from './app/components/auth/Messagemodal';

const store = createStore(
  allReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
);

export default function App() {
  const [modalVisible, setModalVisible] = useState(false);
  const [detailMessage, setDetailMessage] = useState('');
  useEffect(() => {
    const unsubscribe = messaging().onMessage(async remoteMessage => {
      const title = JSON.parse(
        JSON.stringify(remoteMessage.notification.title),
      );
      const body = JSON.stringify(remoteMessage.notification.body);
      const messageResponse = JSON.parse(JSON.stringify(remoteMessage));
      setDetailMessage(messageResponse.notification);
      setModalVisible(!modalVisible);
      // console.log('remote message status', remoteMessage);
    });
    return unsubscribe;
  }, []);

  LogBox.ignoreAllLogs();

  return (
    <StoreProvider store={store}>
      <Root></Root>
      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}>
        <MessageModal
          setModalVisible={setModalVisible}
          message={detailMessage}></MessageModal>
      </Modal>
    </StoreProvider>
  );
}

// redux 함수형
//https://lannstark.tistory.com/128
