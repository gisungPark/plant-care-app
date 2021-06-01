import axios from 'axios';
import {Alert} from 'react-native';
import {
  ACCESS_TOKEN,
  API_BASE_URL,
  DEVICE_TOKEN,
  RECOGNITION_BASE_URL,
} from '../config/config';

// axios 인스턴스
export const instance = axios.create({
  baseURL: `${API_BASE_URL}`,
  headers: {
    'Content-type': 'application/json',
  },
});

// axios file upload 인스턴스
export const instanceFile = axios.create({
  baseURL: `${API_BASE_URL}`,
  headers: {
    'Content-type': 'multipart/form-data',
  },
});

// 식물 판별 인스턴스
export const instanceRecognition = axios.create({
  baseURL: `${RECOGNITION_BASE_URL}`,
  headers: {
    'Content-type': 'multipart/form-data',
  },
});

// axios file device token
export const instanceDevice = axios.create({
  baseURL: `${API_BASE_URL}`,
  headers: {
    'Content-type': 'application/json',
  },
});

// 백엔드에 요청 보낼 때, 인터셉터로 토큰 담기!
instance.interceptors.request.use(
  async function (config) {
    const token = await ACCESS_TOKEN();
    config.headers.TOKEN = token;
    return config;
  },
  function (error) {
    // Do something with request error
    // console.log(error);
    return Promise.reject(error);
  },
);

// interceptors
instance.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function (error) {
    // if (error.response.status == '401') {
    //   Alert.alert(
    //     '주의⚠',
    //     '로그인이 만료되었습니다. 다시 로그인 해주세요.(Error Code: 401)',
    //   );
    // } else if (error.response.status == '403') {
    //   Alert.alert('주의⚠', '권한이 없습니다.(Error Code: 403)');
    // } else if (error.response.status == '400') {
    //   Alert.alert('주의⚠', '클라이언트 오류(Error Code: 400)');
    // } else if (error.response.status == '404') {
    //   Alert.alert('주의⚠', 'Not Found(Error Code: 404)');
    // } else {
    //   Alert.alert('주의⚠', '오류 발생');
    // }
    return Promise.reject(error);
  },
);

// 백엔드에 요청 보낼 때, 인터셉터로 토큰 담기!
instanceFile.interceptors.request.use(
  async function (config) {
    const token = await ACCESS_TOKEN();
    config.headers.TOKEN = token;
    return config;
  },
  function (error) {
    // Do something with request error
    // console.log(error);
    return Promise.reject(error);
  },
);

// interceptors
instanceFile.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function (error) {
    // if (error.response.status == '401') {
    //   Alert.alert(
    //     '주의⚠',
    //     '로그인이 만료되었습니다. 다시 로그인 해주세요.(Error Code: 401)',
    //   );
    // } else if (error.response.status == '403') {
    //   Alert.alert('주의⚠', '권한이 없습니다.(Error Code: 403)');
    // } else if (error.response.status == '400') {
    //   Alert.alert('주의⚠', '클라이언트 오류(Error Code: 400)');
    // } else if (error.response.status == '404') {
    //   Alert.alert('주의⚠', 'Not Found(Error Code: 404)');
    // } else {
    //   Alert.alert('주의⚠', '오류 발생');
    //   console.log(error);
    // }
    return Promise.reject(error);
  },
);

// 백엔드에 요청 보낼 때, 인터셉터로 토큰 담기!
instanceDevice.interceptors.request.use(
  async function (config) {
    const token = await ACCESS_TOKEN();
    config.headers.TOKEN = token;
    const device_token = await DEVICE_TOKEN();
    config.headers.DEVICE = device_token;
    return config;
  },
  function (error) {
    // Do something with request error
    // console.log('device token error', error);
    return Promise.reject(error);
  },
);

// interceptors
instanceDevice.interceptors.response.use(
  function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    // consol.log('device token response', response);
    return response;
  },
  function (error) {
    // console.log('에러났다', error.response.headers);
    // if (error.response.status == '401') {
    //   Alert.alert(
    //     '주의⚠',
    //     '로그인이 만료되었습니다. 다시 로그인 해주세요.(Error Code: 401)',
    //   );
    // } else if (error.response.status == '403') {
    //   Alert.alert('주의⚠', '권한이 없습니다.(Error Code: 403)');
    // } else if (error.response.status == '400') {
    //   Alert.alert('주의⚠', '클라이언트 오류(Error Code: 400)');
    // } else if (error.response.status == '404') {
    //   Alert.alert('주의⚠', 'Not Found(Error Code: 404)');
    // } else {
    //   Alert.alert('주의⚠', '오류 발생');
    //   console.log(error);
    // }
    return Promise.reject(error);
  },
);

// 식물판별 인스턴스 토큰 config 인터셉터
instanceRecognition.interceptors.request.use(
  async function (config) {
    // const token = await ACCESS_TOKEN();
    // config.headers.TOKEN = token;
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

// 식물판별 인스턴스 토큰 response 인터셉터
instanceRecognition.interceptors.response.use(
  function (response) {
    // console.log(response);
    return response;
  },
  function (error) {
    // console.log('식물판별 에러났다', error.response);
    // if (error.response.status == '401') {
    //   Alert.alert(
    //     '주의⚠',
    //     '로그인이 만료되었습니다. 다시 로그인 해주세요.(Error Code: 401)',
    //   );
    // } else if (error.response.status == '403') {
    //   Alert.alert('주의⚠', '권한이 없습니다.(Error Code: 403)');
    // } else if (error.response.status == '400') {
    //   Alert.alert('주의⚠', '클라이언트 오류(Error Code: 400)');
    // } else if (error.response.status == '404') {
    //   Alert.alert('주의⚠', 'Not Found(Error Code: 404)');
    // } else {
    //   Alert.alert('주의⚠', '오류 발생');
    //   // console.log(error);
    // }
    return Promise.reject(error);
  },
);
