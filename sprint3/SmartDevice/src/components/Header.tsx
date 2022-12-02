import React from 'react';
import {Pressable, StyleSheet, View} from 'react-native';
import {Text} from 'react-native-svg';
import Icons from './icons/Icons';

type Props = {
	handleButtonPressed: () => void;
	top?: number;
};

const Header: React.FC<Props> = (props: Props) => {
	return (
		<View style={styles.container}>
			<Pressable onPress={props.handleButtonPressed}>
				<Icons.ChevronLeft />
			</Pressable>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		width: '100%',
		height: 70,
		flex: 1,
		flexDirection: 'row',
	},
});

export default Header;
