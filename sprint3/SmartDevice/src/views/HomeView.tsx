import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React from 'react';
import {SafeAreaView, StyleSheet, Text, View} from 'react-native';
import LargeButton from '../components/LargeButton';
import {RootStackParams} from '../RootStackParams';

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
});

export default HomeView;