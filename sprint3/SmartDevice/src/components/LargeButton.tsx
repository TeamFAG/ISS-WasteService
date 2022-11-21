import {Pressable, StyleSheet, View, Text} from 'react-native';
import {Palette} from '../static/Colors';
import Icons from './icons';

type Props = {
  text: string;
  icon: 'world' | 'search' | 'settings' | 'play';
  handleFunction: () => void;
};

const LargeButton: React.FC<Props> = (props: Props) => {
  const getIconFromString = (icon: string) => {
    switch (icon) {
      case 'world':
        return <Icons.World />;
      case 'search':
        return <Icons.Search />;
      case 'settings':
        return <Icons.Settings />;
      case 'play':
        return <Icons.Play />;
    }
  };

  return (
    <View style={styles.container}>
      <Pressable style={styles.pressable} onPress={props.handleFunction}>
        <View>{getIconFromString(props.icon)}</View>
        <Text style={styles.text}>{props.text}</Text>
      </Pressable>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 300,
    height: 50,
    backgroundColor: Palette.DarkBlue,
    padding: 10,
    borderRadius: 22,
  },
  pressable: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    alignContent: 'center',
  },
  icon: {
    width: '100%',
    height: '100%',
  },
  text: {
    paddingLeft: 60,
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 22,
    color: Palette.White,
  },
});

export default LargeButton;
