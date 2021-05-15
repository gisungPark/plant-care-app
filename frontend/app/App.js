// react native
import React from "react";
import { useState } from "react";
import { StatusBar } from "expo-status-bar";
import { StyleSheet, Text, View, Button } from "react-native";
import "react-native-gesture-handler";

// react navigation
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

// screen
import { HomeScreen } from "./screens/main/Home";
import { LoginScreen } from "./screens/auth/Login";
import { SignupScreen } from "./screens/auth/Signup";
import { DiaryScreen } from "./screens/diary/Diary";
import { DiaryWriteScreen } from "./screens/diary/DiaryWrite";
import { RecommendationScreen } from "./screens/recommendation/Recommendation";
import { SurveyintroScreen } from "./screens/recommendation/SurveyIntro";
import { SurveyquestionScreen } from "./screens/recommendation/SurveyQ";
import { SurveyresultScreen } from "./screens/recommendation/SurveyResult";

import { RoomScreen } from "./screens/main/Room";
import { PlantDetail } from "./screens/main/PlantDetail";

// theme
import theme from "./assets/theme/index";
import { ThemeProvider } from "styled-components";
import { Icon } from "native-base";

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();
const HomeStack = createStackNavigator();
const RecommendationStack = createStackNavigator();

function HomeStacks() {
  return (
    <HomeStack.Navigator>
      <HomeStack.Screen
        name="Home"
        component={HomeScreen}
        options={{ header: () => null }}
      />
      <HomeStack.Screen
        name="Room"
        component={RoomScreen}
        options={{ header: () => null }}
      />
      <HomeStack.Screen
        name="PlantDetail"
        component={PlantDetail}
        options={{ header: () => null }}
      />
    </HomeStack.Navigator>
  );
}

function RecommendationStacks() {
  return (
    <RecommendationStack.Navigator>
      <RecommendationStack.Screen
        name="Surveyintro"
        component={SurveyintroScreen}
        options={{ header: () => null }}
      />
      <RecommendationStack.Screen
        name="Surveyquestion"
        component={SurveyquestionScreen}
        options={{ title: "맞춤 식물 찾기" }}
      />
      <RecommendationStack.Screen
        name="Surveyresult"
        component={SurveyresultScreen}
        options={{ title: "맞춤 식물 찾기" }}
      />
    </RecommendationStack.Navigator>
  );
}
function DiaryStacks() {
  return (
    <Stack.Navigator>
      <Stack.Screen
        name="Diary"
        component={DiaryScreen}
        options={{
          headerStyle: {
            backgroundColor: "#F9F9F9",
          },
          headerTintColor: "#29582C",
          headerTitleStyle: { fontWeight: "bold" },
        }}
      />
      <Stack.Screen
        name="DiaryWrite"
        component={DiaryWriteScreen}
        options={{
          title: "피드 작성",
          headerStyle: {
            backgroundColor: "#F9F9F9",
          },
          headerTintColor: "#29582C",
          headerTitleStyle: { fontWeight: "bold" },
        }}
      />
    </Stack.Navigator>
  );
}

function Tabs() {
  return (
    <Tab.Navigator>
      <Tab.Screen name="Home" component={HomeStacks} />
      <Tab.Screen name="Diary" component={DiaryStacks} />
      <Tab.Screen name="Recommendation" component={RecommendationStacks} />
    </Tab.Navigator>
  );
}

function Mystack() {
  return (
    <Stack.Navigator>
      <Stack.Screen
        name="Login"
        component={LoginScreen}
        options={{ header: () => null }}
      />
      <Stack.Screen
        name="Signup"
        component={SignupScreen}
        options={{ header: () => null }}
      />
    </Stack.Navigator>
  );
}

export default function App() {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <ThemeProvider theme={theme}>
      <NavigationContainer>
        {isLogin !== true ? <Mystack /> : <Tabs />}
      </NavigationContainer>
    </ThemeProvider>
  );
}
