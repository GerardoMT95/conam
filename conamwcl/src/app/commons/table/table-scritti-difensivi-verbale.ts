import { ScrittoDifensivoVO } from "../vo/verbale/scritto-difensivo-vo";

export class TableScrittiDifensiviVerbale extends ScrittoDifensivoVO{
    public soggettoStr: string;
    public ambitoStr: string;
    public normaStr: string;
    
    public static map(value: ScrittoDifensivoVO): TableScrittiDifensiviVerbale {
        let table = new TableScrittiDifensiviVerbale();
        for (let key in value) {
            table[key]=value[key];
        }
        if(value.ragioneSociale){
            table.soggettoStr = value.ragioneSociale;
        }else{
            table.soggettoStr = value.nome +' '+value.cognome; 
        }
        table.ambitoStr = value.ambito.acronimo +' - '+value.ambito.denominazione;
        table.normaStr = '';
        if(value.riferimentoNormativo){
            if(value.riferimentoNormativo.norma && value.riferimentoNormativo.norma.denominazione){
                table.normaStr +=value.riferimentoNormativo.norma.denominazione +' ';
            }
            if(value.riferimentoNormativo.articolo && value.riferimentoNormativo.articolo.denominazione){
                table.normaStr +=value.riferimentoNormativo.articolo.denominazione +' ';
            }
            if(value.riferimentoNormativo.comma && value.riferimentoNormativo.comma.denominazione){
                table.normaStr +=value.riferimentoNormativo.comma.denominazione +' ';
            }
            if(value.riferimentoNormativo.lettera && value.riferimentoNormativo.lettera.denominazione){
                table.normaStr +=value.riferimentoNormativo.lettera.denominazione;
            }
        }
        return table;
    }
}