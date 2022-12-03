import {NativeStackScreenProps} from '@react-navigation/native-stack';
import TcpSockets from 'react-native-tcp-socket';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {
	Alert,
	Pressable,
	SafeAreaView,
	StyleSheet,
	Text,
	View,
} from 'react-native';
import {Dropdown} from 'react-native-element-dropdown';
import {RootStackParams} from '../RootStackParams';
import {Material, OptionsContextType} from '../static/Types';
import useConnection from '../hooks/useConnection';
import {OptionsContext} from '../context/OptionsContext';
import LargeButton from '../components/LargeButton';
import RoundedInput from '../components/RoundedInput';
import {Palette} from '../static/Colors';
import Icons from '../components/icons/Icons';
import TextArea from '../components/TextArea';
import Header from '../components/Header';

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
	const [log, setLog] = useState('');

	useEffect(() => {
		setTextAreaValue(incomingMessages.toString());
	}, [incomingMessages]);

	const handleBackPressed = () => {
		if (isConnected) {
			client?.destroy();
			setIsConnected(false);
		}
		props.navigation.navigate('Home');
	};

	const showAlert = (text: string) => {
		Alert.alert(text);
	};

	const filterQuantityInput = (text: string) => {
		!isNaN(+text.slice(-1)) && setQuantity(text);
	};

	const setTextAreaValue = (value: string) => {
		setLog(value);
	};

	const setTcpClient = (client: any) => {
		setClient(client);
	};

	const clearMessages = () => {
		setIncomingMessages(() => []);
		setTextAreaValue('');
	};

	const addMessage = (message: string) => {
		console.log('Arrived: ' + message);
		setIncomingMessages(incomingMessages => [...incomingMessages, message]);
	};

	const onMessageHandler = (data: string | Buffer) => {
		addMessage(data.toString());
	};

	const onConnectedHandler = (client: TcpSockets.Socket) => {
		setTcpClient(client);
		setIsConnected(true);
	};

	const sendTcpMessage = (message: string) => {
		if (!client) {
			console.log('Error sending message');
			return;
		}

		client.write(message);
	};

	const chekIfRequestPossible = () => {
		if (!isConnected) showAlert('You are not connected to the WasteService');
		else if (quantity === '') showAlert('Quantity field is empty');
		else {
			const requestMessage = buildRequestMessage();

			sendTcpMessage(requestMessage);
		}
	};

	const buildRequestMessage = (): string => {
		return `msg(${options.requestName}, request, smartdevice, ${
			options.destinationActor
		}, ${options.requestName}(${
			Material[selectedMaterial.valueOf()]
		}, ${quantity}), 1)\n`;
	};

	const refresh = useConnection(
		options.tcpOptions,
		onMessageHandler,
		onConnectedHandler,
	);

	return (
		<SafeAreaView style={styles.container}>
			<View style={styles.header}>
				<Header handleButtonPressed={handleBackPressed}></Header>
			</View>
			<Pressable style={styles.statusContainer} onPress={refresh}>
				<Text>Status: {isConnected ? 'connected' : 'not connected'}</Text>
				<View style={styles.icon}>
					{isConnected ? <Icons.Check /> : <Icons.Cross />}
				</View>
			</Pressable>
			<Text style={styles.text}>Insert material type:</Text>
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
			<Text style={styles.text}>Insert quantity:</Text>
			<RoundedInput
				value={quantity}
				setValue={filterQuantityInput}
				placeholder="Quantity"
			/>
			<Text style={styles.text}>Log:</Text>
			<TextArea value={log} setValue={setTextAreaValue} />
			<LargeButton
				icon="play"
				text="Send Request"
				handleFunction={chekIfRequestPossible}
			/>
		</SafeAreaView>
	);
};

const styles = StyleSheet.create({
	container: {
		backgroundColor: 'white',
		flex: 1,
		justifyContent: 'space-between',
		alignItems: 'center',
	},
	dropdown: {
		height: 40,
		width: '60%',
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
		borderColor: 'gray',
		borderWidth: 0.5,
		borderRadius: 22,
		width: '50%',
		height: 35,
		top: -30,
	},
	header: {
		width: '100%',
		height: '6%',
		paddingTop: 10,
		paddingLeft: 10,
	},
	text: {
		fontSize: 16,
		bottom: -10,
	},
});

export default TruckView;
