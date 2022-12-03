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
	const [destActor, setDestActor] = useState('');
	const [requestName, setRequestName] = useState('');

	const handlebackPressed = () => {
		props.navigation.navigate('Home');
	};

	const saveSettings = () => {
		const newOptions: Options = {
			tcpOptions: {
				port: portText != '' ? Number(portText) : options.tcpOptions.port,
				host: ipText != '' ? ipText : options.tcpOptions.host,
				localAddress:
					localAddressText != ''
						? localAddressText
						: options.tcpOptions.localAddress,
				reuseAddress: options.tcpOptions.reuseAddress,
			},
			destinationActor: destActor != '' ? destActor : options.destinationActor,
			requestName: requestName != '' ? requestName : options.requestName,
		};

		updateOptions(newOptions);
	};

	return (
		<SafeAreaView style={styles.container}>
			<View style={styles.header}>
				<Header handleButtonPressed={handlebackPressed}></Header>
			</View>
			<View style={styles.textContainer}>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>Host: </Text>
					{options.tcpOptions.host}
				</Text>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>Port: </Text>
					{options.tcpOptions.port}
				</Text>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>LocalAddress: </Text>
					{options.tcpOptions.localAddress}
				</Text>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>ReuseAddress: </Text>
					{options.tcpOptions.reuseAddress ? 'True' : 'False'}
				</Text>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>Dest. Actor: </Text>
					{options.destinationActor}
				</Text>
				<Text style={styles.text}>
					<Text style={styles.text_bold}>Request name: </Text>
					{options.requestName}
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
				<RoundedInput
					placeholder="Destination Actor"
					value={destActor}
					setValue={setDestActor}
				/>
				<RoundedInput
					placeholder="Request Name"
					value={requestName}
					setValue={setRequestName}
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
		backgroundColor: 'white',
	},
	textContainer: {
		flex: 0.3,
		justifyContent: 'space-evenly',
		padding: 10,
		borderWidth: 1,
		borderColor: 'gray',
		borderRadius: 22,
		width: '85%',
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
	text_bold: {
		fontSize: 22,
		fontWeight: 'bold',
	},
	header: {
		width: '100%',
		height: '6%',
		paddingTop: 10,
		paddingLeft: 10,
	},
});

export default SettingsView;
