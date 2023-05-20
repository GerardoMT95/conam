import { VerbaleVO } from "./verbale-vo";
import { SoggettoVO } from "./soggetto-vo";
import { RiepilogoAllegatoVO } from "./riepilogo-allegato-vo";

export class RiepilogoVerbaleVO{
    public verbale: VerbaleVO;
    public soggetto: Array<SoggettoVO>;
    public allegati: RiepilogoAllegatoVO
}