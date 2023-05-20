import { FieldTypeVO } from "../../../commons/vo/select-vo";

export class ConfigAllegatoVO {
    public idTipo: number;
    public label: string;
    public fieldType: FieldTypeVO;
    public maxLength: number;
    public scale: number;
    public required: boolean;
    public riga: number;
    public idFonteElenco: number;
    public ordine: number;
    public idField: number

    //solo FE
    public idModel: number;
}