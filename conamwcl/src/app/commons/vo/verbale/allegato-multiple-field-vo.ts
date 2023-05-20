import { DocumentoProtocollatoVO } from "./documento-protocollato-vo";

export class AllegatoMultipleFieldVO {
    public file: File;
    public filename: string;
    public idTipoAllegato: number;
    public master: boolean;
    public documentoProtocollato: DocumentoProtocollatoVO;
}