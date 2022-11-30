import {StyleSheet, TextInput, View} from 'react-native';
import {Text} from 'react-native-svg';
import React, {FC, useState} from 'react';
import {Palette} from '../static/Colors';
import {warn} from 'console';

type Props = {};

const TextArea: React.FC<Props> = (props: Props) => {
	const [value, setValue] = useState(
		'ciao\nciao\nciao\nciao\nciao\nciao\nciao\nciao\nciao\nciao',
	);

	return (
		<View style={styles.container}>
			<TextInput
				style={styles.text_input}
				multiline
				numberOfLines={5}
				value={value}
				onChangeText={text => setValue(text)}
				editable={false}
			/>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		alignSelf: 'center',
		width: '80%',
		height: '60%',
		borderWidth: 1,
		borderColor: '#464646',
		borderRadius: 12,
		backgroundColor: Palette.White,
	},
	text_input: {
		padding: 10,
		fontSize: 14,
	},
});

export default TextArea;
