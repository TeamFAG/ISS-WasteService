import {NativeStackScreenProps} from '@react-navigation/native-stack';
import TcpSockets from 'react-native-tcp-socket';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {Pressable, StyleSheet, Text, View} from 'react-native';
import {Dropdown} from 'react-native-element-dropdown';
import {RootStackParams} from '../RootStackParams';
import {Material, OptionsContextType} from '../static/Types';
import useConnection from '../hooks/useConnection';
import {OptionsContext} from '../context/OptionsContext';
import LargeButton from '../components/LargeButton';
import RoundedInput from '../components/RoundedInput';
import {Palette} from '../static/Colors';
import Icons from '../components/icons/Icons';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
	const materialData = [
		{label: 'Glass', value: Material.GLASS},
		{label: 'Plastic', value: Material.PLASTIC},
	];

	const [client, setClient] = useState<TcpSockets.Socket>();
	const {options} = useContext(OptionsContext) as OptionsContextType;
	const [selectedMaterial, setSelectedMaterial] = useState(Material.GLASS);
	const [quantity, setQuantity] = useState('');
	const [isConnected, setIsConnected] = useState(false);
	const [incomingMessages, setIncomingMessages] = useState<string[]>([]);
	const [dropdownIsFocus, setDropdownIsFocus] = useState(false);

	const setTcpClient = (client: any) => {
		setClient(client);
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
		setIsConnected(true);
	};

	const sendTcpMessage = () => {
		if (!client) {
			console.log('error');
			return;
		}

		client.write('ciao');
	};

	const refresh = useConnection(options, onMessageHandler, onConnectedHandler);

	return (
		<View style={styles.container}>
			<Pressable style={styles.statusContainer} onPress={refresh}>
				<Text>Status: {isConnected ? 'connected' : 'not connected'}</Text>
				<View style={styles.icon}>
					{isConnected ? <Icons.Check /> : <Icons.Cross />}
				</View>
			</Pressable>
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
			<RoundedInput
				value={quantity}
				setValue={setQuantity}
				placeholder="Quantity"
			/>
			<LargeButton
				icon="world"
				text="Send msg"
				handleFunction={sendTcpMessage}
			/>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {},
	dropdown: {
		height: 50,
		borderColor: 'gray',
		borderWidth: 0.5,
		borderRadius: 22,
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
	icon: {
		padding: 5,
	},
	statusContainer: {
		flexDirection: 'row',
		justifyContent: 'center',
		alignItems: 'center',
		borderColor: '#464646',
		borderWidth: 1,
		borderRadius: 22,
		width: '50%',
		height: 35,
	},
});

export default TruckView;
