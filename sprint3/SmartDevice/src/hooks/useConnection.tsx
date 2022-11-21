import {useState} from 'react';

const useConnection = () => {
  const [connection, setConnection] = useState();
  const [reply, setReply] = useState('');
  const [history, setHistory] = useState([] as string[]);

  const onMessage = () => {};

  const onConnect = () => {};

  const onError = () => {};

  const onClose = () => {};
};

export default useConnection;
