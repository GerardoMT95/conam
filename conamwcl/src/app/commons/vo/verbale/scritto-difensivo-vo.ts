import { AmbitoVO, EnteVO, modalitaCaricamentoVO } from "../../../commons/vo/select-vo";
import { RiferimentiNormativiVO } from "./riferimenti-normativi-vo";
import { AllegatoVO } from "./allegato-vo";

export class ScrittoDifensivoVO {
    constructor() {
        if (!this.riferimentoNormativo) this.riferimentoNormativo = new RiferimentiNormativiVO();
    }
    public id: number;
    public nomeFile: string;
    public numVerbaleAccertamento: string;
    public nome: string;
    public cognome: string;
    public ragioneSociale: string;
    public numeroProtocollo: string;
    public dataProtocollo: string;
    public allegato: AllegatoVO;
    public ambito: AmbitoVO;
    public riferimentoNormativo: RiferimentiNormativiVO;
    public enteRiferimentiNormativi: EnteVO;
    public modalitaCaricamento: modalitaCaricamentoVO;
}