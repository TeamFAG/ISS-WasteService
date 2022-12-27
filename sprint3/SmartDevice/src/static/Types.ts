export interface Options {
	tcpOptions: TcpOptions;
	destinationActor: string;
	requestName: string;
}

export interface TcpOptions {
	port: number;
	host: string;
	localAddress: string;
	reuseAddress: boolean;
	interface: string;
}

export type OptionsContextType = {
	options: Options;
	updateOptions: (options: Options) => void;
};

export enum Material {
	GLASS,
	PLASTIC,
}
