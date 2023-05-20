import { ProfiloVO } from "./profilo-vo";

export class UtenteVO extends ProfiloVO{
    public idRuolo : number;
	public descRuolo : string;
	public id : number;
	public isIstruttore : number;
}