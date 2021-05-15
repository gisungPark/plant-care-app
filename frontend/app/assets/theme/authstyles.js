import styled from "styled-components";
import theme from "./index";
import {
  Container,
  Header,
  Content,
  Input,
  Item,
  StyleProvider,
  Button,
} from "native-base";

import {
  View,
  Text,
  StyleSheet,
  Image,
  PickerIOSComponent,
} from "react-native";

// 로그인/회원가입에서 사용하는 것들

export const AuthButton = styled(Button)`
  background-color: ${({ theme }) => theme.colors.darkGreen};
  height: 48px;
  width: 100%;
  border-radius: 12px;
  margin: 5px 0;
`;

export const AuthButtonText = styled(Text)`
  color: ${({ theme }) => theme.colors.roomName};
  font-size: 18px;
  font-weight: 700;
  text-align: center;
  width: 100%;
`;

export const SocialButton = styled(Button)`
  border-radius: 12px;
  margin: 5px 0;
  background-color: ${({ theme }) => theme.colors.roomName};
  height: 48px;
  width: 100%;
`;

export const SocialButtonText = styled(Text)`
  color: black;
  font-size: 18px;
  font-weight: 200;
  text-align: center;
  width: 100%;
`;
