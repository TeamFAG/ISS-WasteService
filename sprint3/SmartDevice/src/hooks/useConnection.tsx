import {useEffect, useRef} from 'react';
import TcpSockets from 'react-native-tcp-socket';
import {Options, TcpOptions} from '../static/Types';

const useConnection = (
	tcpOptions: TcpOptions,
	onMessage: (data: string | Buffer) => void,
	onError: (error: Error) => void,
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
			console.log('useConnection | Error: ', error);
		}

		const client: TcpSockets.Socket = clientRef.current!;

		client.on('data', data => {
			console.log('useConnection | arrived msg ' + data);
			onMessage(data);
		});

		client.on('close', data => {
			console.log(
				`useConnection | closed connection to ${tcpOptions.host}:${tcpOptions.port}`,
			);
		});

		client.on('error', error => {
			console.log('useConnection | error: ' + error);
			onError(error);
		});

		return () => {
			if (client) client.destroy();
		};
	}, []);

	const refreshConnection = () => {
		try {
			clientRef.current = TcpSockets.createConnection(tcpOptions, () => {
				onConnectedHandler(clientRef.current!);
			});
		} catch (error) {
			console.log('useConnection | Error: ', error);
		}
	};

	return refreshConnection;
};

export default useConnection;
