import {NativeStackScreenProps} from '@react-navigation/native-stack';
import React, {useState} from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {Dropdown} from 'react-native-element-dropdown';
import {RootStackParams} from '../RootStackParams';
import {Material} from '../static/Types';

type Props = NativeStackScreenProps<RootStackParams, 'Truck'>;

const TruckView: React.FC<Props> = (props: Props) => {
  const [selectedMaterial, setSelectedMaterial] = useState(Material.GLASS);
  const [dropdownIsFocus, setDropdownIsFocus] = useState(false);

  const materialData = [
    {label: 'Glass', value: Material.GLASS},
    {label: 'Plastic', value: Material.PLASTIC},
  ];

  return (
    <View style={styles.container}>
      <Text>Truck</Text>
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
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    padding: 16,
  },
  dropdown: {
    height: 50,
    borderColor: 'gray',
    borderWidth: 0.5,
    borderRadius: 8,
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
});

export default TruckView;
