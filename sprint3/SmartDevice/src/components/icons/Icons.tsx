import * as React from 'react';
import Svg, {SvgProps, Circle, Ellipse, Path} from 'react-native-svg';

function World(props: SvgProps) {
	return (
		<Svg width={32} height={32} fill="none" {...props}>
			<Circle
				cx={16}
				cy={16}
				r={12}
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
			<Ellipse
				cx={16}
				cy={16}
				rx={6}
				ry={12}
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
			<Path
				d="M27 11H5M27 21H5"
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

function Search(props: SvgProps) {
	return (
		<Svg width={32} height={32} fill="none" {...props}>
			<Circle
				cx={14}
				cy={14}
				r={8}
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
			<Path
				d="M20 20l6 6"
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

function Settings(props: SvgProps) {
	return (
		<Svg width={32} height={32} fill="none" {...props}>
			<Path
				d="M12.847 5.834l.997.083-.997-.083zm-.15 1.814l.372.927.572-.23.052-.614-.997-.083zM10.434 8.97l-.427.905.565.265.486-.39-.624-.78zm-1.663-.783l-.426.904.426-.904zm-2.584.809l-.866-.5.866.5zm-1.16 2.008l.866.5-.866-.5zm.592 2.643l.57-.821-.57.821zm1.527 1.06l.99.143.088-.612-.508-.352-.57.821zm0 2.586l.57.821.508-.352-.088-.612-.99.143zm-1.527 1.06l-.57-.822.57.822zm-.592 2.643l.866-.5-.866.5zm1.16 2.008l-.866.5.866-.5zm2.584.81l-.426-.905.426.905zm1.663-.784l.624-.78-.486-.39-.565.265.427.905zm2.261 1.322l.997-.083-.052-.614-.572-.23-.373.927zm6.457 1.814l-.997-.083.997.083zm.147-1.769l-.36-.933-.584.226-.052.624.996.083zm2.334-1.335l.426-.904-.56-.265-.486.385.62.784zm1.594.752l-.427.904.427-.904zm2.584-.81l-.866-.5.866.5zm1.16-2.008l.866.5-.866-.5zm-.591-2.643l.57-.822-.57.822zm-1.429-.992l-.988-.15-.094.617.512.355.57-.822zm0-2.722l-.57-.822-.512.355.094.617.988-.15zm1.428-.992l.57.822-.57-.822zm.592-2.643l.866-.5-.866.5zm-1.16-2.008l-.866.5.866-.5zm-2.584-.81l.426.905-.426-.904zm-1.594.752l-.62.784.486.385.56-.265-.426-.904zM19.3 7.603l-.996.083.052.624.583.226.361-.933zm-.147-1.77l.996-.082-.996.083zm-5.31.084A1 1 0 0114.84 5V3a3 3 0 00-2.99 2.75l1.994.167zm-.15 1.814l.15-1.814-1.992-.166-.152 1.814 1.994.166zM11.06 9.75a7.992 7.992 0 012.01-1.176l-.747-1.855A9.993 9.993 0 009.81 8.19l1.25 1.56zm-2.714-.66l1.662.784.853-1.81-1.662-.783-.853 1.81zm-1.292.405a1 1 0 011.292-.405l.853-1.809a3 3 0 00-3.877 1.214l1.732 1zm-1.16 2.008l1.16-2.008-1.732-1-1.16 2.008 1.732 1zm.296 1.322a1 1 0 01-.296-1.322l-1.732-1a3 3 0 00.887 3.965l1.14-1.643zm1.527 1.06l-1.527-1.06-1.14 1.643 1.527 1.06 1.14-1.643zM8.055 16c0-.391.028-.775.082-1.15l-1.98-.285c-.068.47-.102.948-.102 1.435h2zm.082 1.15A8.076 8.076 0 018.055 16h-2c0 .487.034.966.102 1.435l1.98-.285zM6.19 19.174l1.527-1.06-1.14-1.643-1.528 1.06 1.14 1.643zm-.296 1.322a1 1 0 01.296-1.322l-1.14-1.643a3 3 0 00-.888 3.965l1.732-1zm1.16 2.008l-1.16-2.008-1.732 1 1.16 2.008 1.732-1zm1.292.405a1 1 0 01-1.292-.405l-1.732 1a3 3 0 003.877 1.214l-.853-1.81zm1.662-.784l-1.662.784.853 1.809 1.662-.783-.853-1.81zm3.061 1.3a7.991 7.991 0 01-2.01-1.176l-1.25 1.562a9.995 9.995 0 002.514 1.47l.746-1.856zm.775 2.658l-.151-1.814-1.993.167.15 1.813 1.994-.166zm.996.917a1 1 0 01-.996-.917l-1.993.166A3 3 0 0014.84 29v-2zm2.32 0h-2.32v2h2.32v-2zm.996-.917a1 1 0 01-.996.917v2a3 3 0 002.99-2.75l-1.994-.167zm.148-1.77l-.148 1.77 1.993.166.148-1.769-1.993-.166zm2.71-2.035a7.988 7.988 0 01-2.075 1.186l.722 1.866a9.99 9.99 0 002.593-1.483l-1.24-1.569zm2.64.63l-1.594-.75-.852 1.809 1.593.751.853-1.81zm1.292-.404a1 1 0 01-1.292.405l-.853 1.809a3 3 0 003.878-1.214l-1.733-1zm1.16-2.008l-1.16 2.008 1.732 1 1.16-2.008-1.732-1zm-.296-1.322a1 1 0 01.296 1.322l1.732 1a3 3 0 00-.887-3.965l-1.14 1.643zm-1.428-.991l1.428.991 1.14-1.643-1.427-.991-1.14 1.643zM24.055 16c0 .412-.032.817-.091 1.211l1.977.3c.075-.493.114-.998.114-1.511h-2zm-.091-1.211c.06.394.09.799.09 1.211h2c0-.513-.038-1.018-.113-1.511l-1.977.3zm1.846-1.963l-1.428.991 1.14 1.643 1.429-.991-1.14-1.643zm.296-1.322a1 1 0 01-.296 1.322l1.14 1.643a3 3 0 00.888-3.965l-1.732 1zm-1.16-2.008l1.16 2.008 1.732-1-1.16-2.008-1.732 1zm-1.292-.405a1 1 0 011.292.405l1.732-1a3 3 0 00-3.877-1.214l.853 1.81zm-1.594.751l1.594-.75-.853-1.81-1.593.751.852 1.81zm-3.12-1.306c.752.29 1.45.693 2.074 1.186l1.24-1.569a9.989 9.989 0 00-2.593-1.483l-.722 1.866zm-.784-2.619l.148 1.769 1.993-.166-.148-1.77-1.993.167zM17.16 5a1 1 0 01.996.917l1.993-.166A3 3 0 0017.16 3v2zm-2.32 0h2.32V3h-2.32v2z"
				fill="#FAFAFA"
			/>
			<Circle cx={16.055} cy={16} r={4} stroke="#FAFAFA" strokeWidth={2} />
		</Svg>
	);
}

function Play(props: SvgProps) {
	return (
		<Svg width={32} height={32} fill="none" {...props}>
			<Path
				clipRule="evenodd"
				d="M10 6a1 1 0 011.6-.8l13.333 10a1 1 0 010 1.6L11.6 26.8A1 1 0 0110 26V6z"
				stroke="#FAFAFA"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

function User(props: SvgProps) {
	return (
		<Svg width={250} height={250} fill="none" {...props}>
			<Circle
				cx={125}
				cy={70.313}
				r={39.063}
				stroke="#464646"
				strokeWidth={5}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
			<Ellipse
				cx={125}
				cy={175.781}
				rx={70.313}
				ry={42.969}
				stroke="#464646"
				strokeWidth={5}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

function SmallUser(props: SvgProps) {
	return (
		<Svg width={32} height={32} fill="none" {...props}>
			<Circle
				cx={16}
				cy={9}
				r={5}
				stroke="#464646"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
			<Ellipse
				cx={16}
				cy={22.5}
				rx={9}
				ry={5.5}
				stroke="#464646"
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

function Check(props: SvgProps) {
	return (
		<Svg width={24} height={24} fill="none" {...props}>
			<Path
				d="M18.71 7.21a.999.999 0 00-1.42 0l-7.45 7.46-3.13-3.14A1.02 1.02 0 105.29 13l3.84 3.84a1 1 0 001.42 0l8.16-8.16a.999.999 0 000-1.47z"
				fill="#6DBC51"
			/>
			<Circle cx={12} cy={12} r={11.5} stroke="#6DBC51" />
		</Svg>
	);
}

function Cross(props: SvgProps) {
	return (
		<Svg width={24} height={24} fill="none" {...props}>
			<Path
				d="M13.41 12l4.3-4.29a1.004 1.004 0 10-1.42-1.42L12 10.59l-4.29-4.3a1.004 1.004 0 00-1.42 1.42l4.3 4.29-4.3 4.29a1 1 0 000 1.42.998.998 0 001.42 0l4.29-4.3 4.29 4.3a.998.998 0 001.42 0 .997.997 0 00.219-1.095.998.998 0 00-.22-.325L13.41 12z"
				fill="#DB0000"
			/>
			<Circle cx={12} cy={12} r={11.5} stroke="#DB0000" />
		</Svg>
	);
}

function ChevronLeft(props: SvgProps) {
	return (
		<Svg width={24} height={24} fill="none" {...props}>
			<Path
				d="M15 18l-6-6 6-6"
				stroke="#000"
				strokeOpacity={0.8}
				strokeWidth={2}
				strokeLinecap="round"
				strokeLinejoin="round"
			/>
		</Svg>
	);
}

export default {
	World,
	Search,
	Settings,
	Play,
	User,
	SmallUser,
	Check,
	Cross,
	ChevronLeft,
};
