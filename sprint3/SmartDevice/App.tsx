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
import SettingsView from './src/views/SettingsView';
import TruckView from './src/views/TruckView';

const OptionsContext = createContext({});

const App = () => {
	return (
		<OptionsProvider>
			<NavigationContainer>
				<RootStack.Navigator initialRouteName="Home">
					<RootStack.Screen
						name="Home"
						component={HomeView}
						options={{headerShown: false}}
					/>
					<RootStack.Screen
						name="Truck"
						component={TruckView}
						options={{headerShown: false}}
					/>
					<RootStack.Screen
						name="Settings"
						component={SettingsView}
						options={{headerShown: false}}
					/>
				</RootStack.Navigator>
			</NavigationContainer>
		</OptionsProvider>
	);
};

const RootStack = createNativeStackNavigator<RootStackParams>();

export default App;
