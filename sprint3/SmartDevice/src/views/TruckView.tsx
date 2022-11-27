import {NativeStackScreenProps} from '@react-navigation/native-stack';
import TcpSockets from 'react-native-tcp-socket';
import React, {useContext, useRef, useState} from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {Dropdown} from 'react-native-element-dropdown';
import {RootStackParams} from '../RootStackParams';
import {Material, OptionsContextType} from '../static/Types';
import useConnection from '../hooks/useConnection';
import {OptionsContext} from '../context/OptionsContext';
import LargeButton from '../components/LargeButton';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
	const materialData = [
		{label: 'Glass', value: Material.GLASS},
		{label: 'Plastic', value: Material.PLASTIC},
	];

	const [selectedMaterial, setSelectedMaterial] = useState(Material.GLASS);
	const [dropdownIsFocus, setDropdownIsFocus] = useState(false);

	const {options} = useContext(OptionsContext) as OptionsContextType;

	const opzioni = {
		port: 8050,
		host: '127.0.0.1',
		localAddress: '127.0.0.1',
		reuseAddress: true,
	};

	const createConnection = () => {};

	const [incomingMessages, setIncomingMessages] = useState<string[]>([]);
	const tcpClientRef = useRef<TcpSockets.Socket>();

	const setTcpClient = (client: any) => {
		tcpClientRef.current = client;
	};

	const clearMessages = () => {
		setIncomingMessages(() => []);
	};

	const addMessage = (message: string) => {
		console.log('Arrived: ' + message);
		setIncomingMessages((incomingMessages: string[]) => [
			...incomingMessages,
			message,
		]);
	};

	const onMessageHandler = (data: string | Buffer) => {
		addMessage(data.toString());
	};

	const onConnectedHandler = (client: TcpSockets.Socket) => {
		setTcpClient(client);
	};

	const sendTcpMessage = (client: TcpSockets.Socket) => {
		if (!client) {
			console.log('Client error');
			return;
		}

		client.write('CIAO');
	};

	//useConnection(options, onMessageHandler, onConnectedHandler);

	return (
		<View style={styles.container}>
			<Text>Truck</Text>
			<Dropdown
				style={[styles.dropdown, dropdownIsFocus && {borderColor: 'blue'}]}
				placeholderStyle={styles.placeholderStyle}
				selectedTextStyle={styles.selectedTextStyle}
				inputSearchStyle={styles.inputSearchStyle}
				data={materialData}
				maxHeight={300}
				labelField="label"
				valueField="value"
				placeholder={!dropdownIsFocus ? 'Select item' : '...'}
				searchPlaceholder="Search..."
				value={selectedMaterial}
				onFocus={() => setDropdownIsFocus(true)}
				onBlur={() => setDropdownIsFocus(false)}
				onChange={item => {
					setSelectedMaterial(item.value);
					setDropdownIsFocus(false);
				}}
			/>
			<LargeButton
				icon="world"
				text="Connect"
				handleFunction={createConnection}
			/>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		backgroundColor: 'white',
		padding: 16,
	},
	dropdown: {
		height: 50,
		borderColor: 'gray',
		borderWidth: 0.5,
		borderRadius: 8,
		paddingHorizontal: 8,
	},
	icon: {
		marginRight: 5,
	},
	label: {
		position: 'absolute',
		backgroundColor: 'white',
		left: 22,
		top: 8,
		zIndex: 999,
		paddingHorizontal: 8,
		fontSize: 14,
	},
	placeholderStyle: {
		fontSize: 16,
	},
	selectedTextStyle: {
		fontSize: 16,
	},
	iconStyle: {
		width: 20,
		height: 20,
	},
	inputSearchStyle: {
		height: 40,
		fontSize: 16,
	},
});

export default TruckView;
