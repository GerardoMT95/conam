import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";

export class DocumentoProtocollatoVO {
	public classificazione: string;
	public classificazioneId: string;
	public file: any;
	public filename: string | MyUrl;
	public folderId: string;
	public idActa: string;
	public idActaMaster: string;
	public numProtocollo: string;
	public numProtocolloMaster?: string;
	public dataOraProtocollo: string;
	public objectIdDocumento: string;
	public documentoUUID: string;
	public parolaChiave: string;
	public registrazioneId: string;
	public tipo: string;
	public giaSalvato: boolean;	
	public tipiAllegatoDuplicabili: number[];
	public filenamesDocMultiplo: string;
}
