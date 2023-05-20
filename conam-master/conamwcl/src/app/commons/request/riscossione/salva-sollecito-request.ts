import { NotificaVO } from "../../vo/notifica/notifica-vo";
import {SollecitoVO } from "../../vo/riscossione/sollecito-vo";

export class SalvaSollecitoRequest  {
    public sollecito: SollecitoVO;    
    public notifica: NotificaVO;
}