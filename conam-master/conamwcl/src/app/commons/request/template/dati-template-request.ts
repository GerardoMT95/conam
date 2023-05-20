import { DatiTemplateCompilatiVO } from "../../vo/template/dati-template-compilati-vo";

export class DatiTemplateRequest {
    public codiceTemplate: number;
    public idPiano: number;
    public idOrdinanza: number;
    public idSollecito: number;
    public idVerbaleSoggettoList: Array<Number>
    //altri id

    public datiTemplateCompilatiVO: DatiTemplateCompilatiVO;
}