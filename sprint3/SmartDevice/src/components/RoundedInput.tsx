import React, {FC} from 'react';
import {StyleSheet, TextInput, View} from 'react-native';
import {Palette} from '../static/Colors';

type Props = {
	placeholder: string;
	value: string;
	setValue: any;
};

const RoundedInput: React.FC<Props> = (props: Props) => {
	return (
		<View style={styles.container}>
			<View style={styles.input_bar}>
				<TextInput
					placeholder={props.placeholder}
					style={styles.input}
					value={props.value}
					onChangeText={props.setValue}></TextInput>
			</View>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		justifyContent: 'flex-start',
		alignItems: 'center',
		width: '75%',
	},
	input_bar: {
		padding: 10,
		flexDirection: 'row',
		width: '100%',
		backgroundColor: Palette.White,
		borderColor: 'gray',
		borderRadius: 22,
		borderWidth: 0.5,
	},
	input: {
		fontSize: 20,
		width: '90%',
		textAlign: 'center',
	},
});

export default RoundedInput;
