import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React, {useContext} from 'react';
import {Text, View} from 'react-native';
import {RootStackParams} from '../RootStackParams';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
  const options = useContext();

  return (
    <View>
      <Text>Truck</Text>
      <Text>Truck</Text>
      <Text>Truck</Text>
      <Text>Truck</Text>
    </View>
  );
};

export default TruckView;
