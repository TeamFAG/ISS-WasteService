import {useEffect, useRef} from 'react';
import TcpSockets from 'react-native-tcp-socket';
import {Options} from '../static/Types';

const useConnection = (
	tcpOptions: Options,
	onMessage: (data: string | Buffer) => void,
	onConnectedHandler = (client: TcpSockets.Socket) => {},
) => {
	const clientRef = useRef<TcpSockets.Socket>();

	useEffect(() => {
		if (clientRef.current) return;

		try {
			clientRef.current = TcpSockets.createConnection(tcpOptions, () => {
				onConnectedHandler(client);
			});
		} catch (error) {
			console.log('Error: ', error);
		}

		const client: TcpSockets.Socket = clientRef.current!;

		client.on('data', data => {
			onMessage(data);
		});

		return () => {
			if (client) client.destroy();
		};
	}, []);
};

export default useConnection;
