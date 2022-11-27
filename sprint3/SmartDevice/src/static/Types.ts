export interface Options {
	port: number;
	host: string;
	localAddress: string;
	reuseAddress: boolean;
}

export type OptionsContextType = {
	options: Options;
	updateOptions: (options: Options) => void;
};

export enum Material {
	GLASS,
	PLASTIC,
}
