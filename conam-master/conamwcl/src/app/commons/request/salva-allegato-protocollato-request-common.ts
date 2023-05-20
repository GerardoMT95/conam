import { AllegatoFieldVO } from "../vo/verbale/allegato-field-vo";
import { DocumentoProtocollatoVO } from "../vo/verbale/documento-protocollato-vo";

export interface SalvaAllegatoProtocollatoVerbaleRequestAllegato {
    idTipoAllegato: number;
    allegatoField?: Array<AllegatoFieldVO>; //i metedati
}

export class SalvaAllegatoProtocollatoRequestCommon{
    public allegati: SalvaAllegatoProtocollatoVerbaleRequestAllegato[];
	public documentoProtocollato: DocumentoProtocollatoVO;
}
