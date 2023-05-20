import { FieldTypeVO } from "../../../commons/vo/select-vo";

export class AllegatoFieldVO {

    public booleanValue: any;
    public numberValue: number;
    public stringValue: string;
    public dateValue: Date;
    public dateTimeValue: Date;
    public fieldType: FieldTypeVO;
    public idField: number;
}