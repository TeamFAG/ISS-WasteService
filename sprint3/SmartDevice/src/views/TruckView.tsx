import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React from 'react';
import {Text, View} from 'react-native';
import {RootStackParams} from '../RootStackParams';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
  return (
    <View>
      <Text>Truck</Text>
    </View>
  );
};

export default TruckView;
