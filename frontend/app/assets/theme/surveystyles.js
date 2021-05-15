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

// 추천탭에서 사용하는 것들

export const SurveyButton = styled(Button)`
  background-color: ${({ theme }) => theme.colors.darkGreen};
  height: 60%;
  width: 100px;
  justify-content: center;
`;

export const SurveyButtonText = styled(Text)`
  color: ${({ theme }) => theme.colors.roomName};
  font-size: 20px;
`;

export const SurveyQText = styled(Text)`
  font-size: 22px;
  font-weight: 800;
  width: 100%;
  justify-content: center;
  align-self: flex-start;
`;
