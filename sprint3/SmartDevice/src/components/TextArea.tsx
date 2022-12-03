import {StyleSheet, TextInput, View} from 'react-native';
import {Text} from 'react-native-svg';
import React, {FC, useState} from 'react';
import {Palette} from '../static/Colors';
import {warn} from 'console';

type Props = {
	value: string;
	setValue: (value: string) => void;
};

const TextArea: React.FC<Props> = (props: Props) => {
	return (
		<View style={styles.container}>
			<TextInput
				style={styles.text_input}
				multiline
				numberOfLines={5}
				value={props.value}
				onChangeText={text => props.setValue(text)}
				editable={false}
			/>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		alignSelf: 'center',
		width: '80%',
		height: '50%',
		borderWidth: 0.5,
		borderColor: 'gray',
		borderRadius: 12,
		backgroundColor: Palette.White,
	},
	text_input: {
		padding: 10,
		fontSize: 14,
	},
});

export default TextArea;
