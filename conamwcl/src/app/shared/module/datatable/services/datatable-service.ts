import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { MyUrl, IMyUrl } from "../classes/url";

/**
 * Servizio contenitore di metodi utili per utilizzo della datatable
 */
@Injectable()
export class DatatableService {

    /**
     * Crea un oggetto di tipo `MyUrl` pronto per poter essere usato come proprietà di un record in tabella 
     * e permettere all'utente di visualizzare in colonna un link per scaricare il file allegato.
     * @param myFile
     */
    public creaUrl(myFile: File): IMyUrl {
      let safeUrl: SafeUrl = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(myFile));
      return new MyUrl(myFile.name, safeUrl);
    }

  constructor(private sanitizer : DomSanitizer){ }
}