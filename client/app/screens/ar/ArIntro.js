'use strict';
import React, {Component, useState} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Container, Header, Content} from 'native-base';
import {
  ViroARScene,
  ViroText,
  ViroAmbientLight,
  ViroSpotLight,
  ViroARSceneNavigator,
  Viro3DObject,
  ViroMaterials,
} from '@viro-community/react-viro';
// import pizzaObj from '../../13917_Pepperoni_v2_l2.obj';
// import pizzaMtl from '../../13917_Pepperoni_v2_l2.mtl';
// import pizzaDiffuse from '../../13917_Pepperoni_diffuse.jpg';
// import pizzaPlate from '../../plate_diffuse.jpg';

const physicsBody = {
  type: 'Dynamic',
  mass: 25,
  enabled: true,
  useGravity: true,
  restitution: 0.35,
  friction: 0.75,
};

export function ArIntroScreen({route, navigation}) {
  const {arname} = route.params;
  // console.log(arname);
  return (
    <ViroARSceneNavigator
      initialScene={{
        scene: ArIntro,
        passProps: {
          arname: arname,
        },
      }}
    />
  );
}
const ArIntro = props => {
  const ar = props.arname;
  // console.log(ar);
  return (
    // <View>
    //   <Text>AR</Text>
    // </View>
    <ViroARScene>
      <ViroAmbientLight color={'#aaaaaa'} />
      <ViroSpotLight
        innerAngle={5}
        outerAngle={90}
        direction={[0, -1, -0.2]}
        position={[0, 3, 1]}
        castsShadow={true}
        intensity={2500}
      />
      {/* <ViroText
        text="Go Back"
        scale={[0.5, 0.5, 0.5]}
        position={[0, 0, -1]}
        style={styles.helloWorldTextStyle}
        onClick={() => navigation.navigate('ArChoice')}
      /> */}
      {ar === 'arplant1' ? (
        <Viro3DObject
          position={[0, -1, -1]}
          scale={[0.5, 0.5, 0.5]}
          rotation={[180, 90, 180]}
          source={require('../../objects/indoorplant/Indoorplant3.obj')}
          resources={[
            require('../../objects/indoorplant/Indoor3.mtl'),
            require('../../objects/indoorplant/bpng.jpg'),
            require('../../objects/indoorplant/bpng_NRM_Kopie.jpg'),
            require('../../objects/indoorplant/bpng_Schwaz_weistensel.jpg'),
            require('../../objects/indoorplant/textures_col.jpg'),
            require('../../objects/indoorplant/textures_nor.png'),
          ]}
          type="OBJ"
          dragType="FixedDistance"
          onDrag={() => {}}
        />
      ) : (
        <Viro3DObject
          position={[0, -1, -1]}
          scale={[0.08, 0.08, 0.08]}
          rotation={[180, 90, 180]}
          source={require('../../objects/indoor/plant_02.obj')}
          resources={[
            require('../../objects/indoor/plant02.mtl'),
            require('../../objects/indoor/plant_2_COL.jpg'),
            require('../../objects/indoor/plant_2_NOR.jpg'),
            require('../../objects/indoor/plant_2_vl.jpg'),
          ]}
          type="OBJ"
          dragType="FixedDistance"
          onDrag={() => {}}
        />
      )}

      {/* <ViroBox
        position={[0, -0.5, -1]}
        scale={[0.3, 0.3, 0.1]}
        materials={['grid']}
      /> */}
    </ViroARScene>
  );
};

var styles = StyleSheet.create({
  helloWorldTextStyle: {
    fontFamily: 'Arial',
    fontSize: 30,
    color: 'red',
    textAlignVertical: 'center',
    textAlign: 'center',
  },
});
