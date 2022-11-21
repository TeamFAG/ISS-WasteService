/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * Generated with the TypeScript template
 * https://github.com/react-native-community/react-native-template-typescript
 *
 * @format
 */

import {NavigationContainer, NavigationContext} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import React, {type PropsWithChildren} from 'react';
import {SafeAreaView, StyleSheet, Text, View} from 'react-native';
import {RootStackParams} from './src/RootStackParams';
import HomeView from './src/views/HomeView';
import TruckView from './src/views/TruckView';

const App = () => {
  return (
    <NavigationContainer>
      <RootStack.Navigator initialRouteName="Home">
        <RootStack.Screen name="Home" component={HomeView} />
        <RootStack.Screen name="Truck" component={TruckView} />
      </RootStack.Navigator>
    </NavigationContainer>
  );
};

const RootStack = createNativeStackNavigator<RootStackParams>();

export default App;
