import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React from 'react';
import {Image, SafeAreaView, StyleSheet, Text, View} from 'react-native';
import LargeButton from '../components/LargeButton';
import {RootStackParams} from '../RootStackParams';
import {Palette} from '../static/Colors';

type Props = NativeStackScreenProps<RootStackParams, 'Home'>;

const HomeView: React.FC<Props> = (props: Props) => {
	const handleConnectButton = () => {
		props.navigation.navigate('Truck');
	};

	const handleSettingsButton = () => {
		props.navigation.navigate('Settings');
	};

	return (
		<SafeAreaView style={styles.container}>
			<View style={styles.icon_container}>
				<Text style={styles.title}>SmartDevice</Text>
				<Image
					style={styles.icon}
					source={require('../static/imgs/garbage-truck.png')}
				/>
			</View>
			<LargeButton
				text="Connect"
				icon="world"
				handleFunction={handleConnectButton}
			/>
			<LargeButton
				text="Settings"
				icon="settings"
				handleFunction={handleSettingsButton}
			/>
		</SafeAreaView>
	);
};

const styles = StyleSheet.create({
	container: {
		flex: 1,
		justifyContent: 'space-evenly',
		alignItems: 'center',
		alignContent: 'center',
		backgroundColor: '#FAFAFA',
	},
	icon_container: {
		alignContent: 'center',
		alignItems: 'center',
		padding: 50,
	},
	icon: {
		width: 150,
		height: 150,
	},
	title: {
		fontSize: 46,
		fontWeight: 'bold',
		color: '#262626',
	},
});

export default HomeView;
