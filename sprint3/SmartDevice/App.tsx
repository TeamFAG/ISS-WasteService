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
import React, {createContext, useState, type PropsWithChildren} from 'react';
import {SafeAreaView, StyleSheet, Text, View} from 'react-native';
import OptionsProvider from './src/context/OptionsContext';
import {RootStackParams} from './src/RootStackParams';
import HomeView from './src/views/HomeView';
import TruckView from './src/views/TruckView';

const OptionsContext = createContext({});

interface Options {
  port: number;
}

const options = {
  port: 8050,
  host: 'localhost',
  localAddress: '127.0.0.1',
  reuseAddress: true,
};

const App = () => {
  const [connectionOptions, setConnectionOptions] = useState(options);

  return (
    <OptionsProvider>
      <NavigationContainer>
        <RootStack.Navigator initialRouteName="Home">
          <RootStack.Screen name="Home" component={HomeView} />
          <RootStack.Screen name="Truck" component={TruckView} />
        </RootStack.Navigator>
      </NavigationContainer>
    </OptionsProvider>
  );
};

const RootStack = createNativeStackNavigator<RootStackParams>();

export default App;
