import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React, {useContext, useState} from 'react';
import {SafeAreaView, Settings, StyleSheet, Text, View} from 'react-native';
import Header from '../components/Header';
import LargeButton from '../components/LargeButton';
import RoundedInput from '../components/RoundedInput';
import {OptionsContext} from '../context/OptionsContext';
import {RootStackParams} from '../RootStackParams';
import {Options, OptionsContextType} from '../static/Types';
type Props = NativeStackScreenProps<RootStackParams, 'Settings'>;

const SettingsView: React.FC<Props> = (props: Props) => {
	const {options, updateOptions} = useContext(
		OptionsContext,
	) as OptionsContextType;

	const [ipText, setIpText] = useState('');
	const [portText, setPortText] = useState('');
	const [localAddressText, setLocalAddressText] = useState('');

	const handlebackPressed = () => {
		props.navigation.navigate('Home');
	};

	const saveSettings = () => {
		const newOptions: Options = {
			port: portText != '' ? Number(portText) : options.port,
			host: ipText != '' ? ipText : options.host,
			localAddress:
				localAddressText != '' ? localAddressText : options.localAddress,
			reuseAddress: options.reuseAddress,
		};

		updateOptions(newOptions);
	};

	return (
		<SafeAreaView style={styles.container}>
			<View style={styles.header}>
				<Header handleButtonPressed={handlebackPressed}></Header>
			</View>
			<View style={styles.textContainer}>
				<Text style={styles.text}>Host: {options.host}</Text>
				<Text style={styles.text}>Port: {options.port}</Text>
				<Text style={styles.text}>LocalAddress: {options.localAddress}</Text>
				<Text style={styles.text}>
					ReuseAddress: {options.reuseAddress ? 'True' : 'False'}
				</Text>
			</View>
			<View style={styles.buttonContainer}>
				<RoundedInput
					placeholder="IP Address"
					value={ipText}
					setValue={setIpText}
				/>
				<RoundedInput
					placeholder="Port"
					value={portText}
					setValue={setPortText}
				/>
				<RoundedInput
					placeholder="Local Address"
					value={localAddressText}
					setValue={setLocalAddressText}
				/>
				<LargeButton
					text="Save settings"
					icon="settings"
					handleFunction={saveSettings}></LargeButton>
			</View>
		</SafeAreaView>
	);
};

const styles = StyleSheet.create({
	container: {
		flex: 1,
		alignItems: 'center',
		justifyContent: 'center',
	},
	textContainer: {
		flex: 0.3,
		justifyContent: 'space-evenly',
	},
	buttonContainer: {
		flex: 0.7,
		justifyContent: 'space-evenly',
		alignItems: 'center',
	},
	text: {
		fontSize: 22,
		fontWeight: '400',
	},
	header: {
		width: '100%',
		height: '6%',
		paddingTop: 10,
		paddingLeft: 10,
	},
});

export default SettingsView;
