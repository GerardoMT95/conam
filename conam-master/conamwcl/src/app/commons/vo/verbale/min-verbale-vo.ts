import { EnteVO, StatoVerbaleVO } from "../../../commons/vo/select-vo";

export class MinVerbaleVO {
	public id: number;
	public numeroProtocollo: string;
	public numero: string;
	public enteAccertatore: EnteVO;
	public stato: StatoVerbaleVO;
	public user: string;
	public dataCaricamento: string;
	public modificabile: boolean;
	public dataOraAccertamento: string; 
}