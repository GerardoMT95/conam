import { DocumentoProtocollatoVO } from "../../vo/verbale/documento-protocollato-vo";
import { ScrittoDifensivoVO } from "../../vo/verbale/scritto-difensivo-vo";

export class SalvaScrittoDifensivoRequest{
    public file:File;
    public scrittoDifensivo: ScrittoDifensivoVO;
    public documentoProtocollato: DocumentoProtocollatoVO;

}