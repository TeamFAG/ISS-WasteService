import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React, {useContext, useState} from 'react';
import {Text, View} from 'react-native';
import LargeButton from '../components/LargeButton';
import RoundedInput from '../components/RoundedInput';
import {OptionsContext} from '../context/OptionsContext';
import {RootStackParams} from '../RootStackParams';
import {Options, OptionsContextType} from '../static/Types';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
  const {options, updateOptions} = useContext(
    OptionsContext,
  ) as OptionsContextType;

  const [ipText, setIpText] = useState('');
  const [portText, setPortText] = useState('');
  const [localAddressText, setLocalAddressText] = useState('');

  const saveSettings = () => {
    const newOptions: Options = {
      port: portText != '' ? Number(portText) : options.port,
      host: ipText != '' ? ipText : options.host,
      localAddress:
        localAddressText != '' ? localAddressText : options.localAddress,
      reuseAddress: options.reuseAddress,
    };

    updateOptions(newOptions);
  };

  return (
    <View>
      <Text>Truck</Text>
      <Text>{options.host}</Text>
      <Text>{options.port}</Text>
      <Text>{options.localAddress}</Text>
      <Text>{options.reuseAddress}</Text>
      <RoundedInput
        placeholder="IP Address"
        value={ipText}
        setValue={setIpText}
      />
      <RoundedInput
        placeholder="Port"
        value={portText}
        setValue={setPortText}
      />
      <RoundedInput
        placeholder="Local Address"
        value={localAddressText}
        setValue={setLocalAddressText}
      />
      <LargeButton
        text="Save settings"
        icon="settings"
        handleFunction={saveSettings}></LargeButton>
    </View>
  );
};

export default TruckView;
