import React, {useState, createContext, ReactNode} from 'react';
import {Options, OptionsContextType} from '../static/Types';

export const OptionsContext = createContext<OptionsContextType | null>(null);

const OptionsProvider: React.FC<ReactNode> = ({children}) => {
  const [options, setOptions] = useState<Options>({
    port: 8050,
    host: 'localhost',
    localAddress: '127.0.0.1',
    reuseAddress: true,
  });

  const updateOptions = (options: Options) => {
    setOptions(options);
  };

  return (
    <OptionsContext.Provider value={{options, updateOptions}}>
      {children}
    </OptionsContext.Provider>
  );
};

export default OptionsProvider;
