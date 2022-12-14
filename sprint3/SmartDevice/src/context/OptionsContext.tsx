import React, {useState, createContext, ReactNode} from 'react';
import {Options, OptionsContextType} from '../static/Types';

export const OptionsContext = createContext<OptionsContextType | null>(null);

type Props = {
	children: ReactNode;
};

const OptionsProvider = (props: Props) => {
	const [options, setOptions] = useState<Options>({
		tcpOptions: {
			port: 8050,
			host: 'localhost',
			localAddress: '127.0.0.1',
			reuseAddress: true,
			interface: 'wifi',
		},
		destinationActor: 'wasteservice',
		requestName: 'storeRequest',
	});

	const updateOptions = (options: Options) => {
		setOptions(options);
	};

	return (
		<OptionsContext.Provider value={{options, updateOptions}}>
			{props.children}
		</OptionsContext.Provider>
	);
};

export default OptionsProvider;
