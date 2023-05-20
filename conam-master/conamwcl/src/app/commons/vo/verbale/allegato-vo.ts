import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";

export class AllegatoVO{
    public id: number;
	public nome: string;
	public tipo: TipoAllegatoVO;
	public numProtocollo: string;
	public idActa: string;
	public dataOraCaricamento: string;
	public numeroDeterminazioneOrdinanza: string;
	public idCategoria:number;
	public utente: string;
	public theUrl ?: MyUrl;
	public nomeCognomeRagioneSocialeSoggetti: string;
}