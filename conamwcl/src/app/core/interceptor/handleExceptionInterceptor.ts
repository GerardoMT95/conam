import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ConfigService } from '../services/config.service';
import { LoggerService } from '../services/logger/logger.service';
import { Router } from '@angular/router';
import { ExceptionVO } from '../../commons/vo/exceptionVO';


export enum MessageRestError {
    GENERIC = "Attenzione! La sessione è scaduta oppure si è verificato un errore di comunicazione con il server! Contattare l'assistenza o riprovare tra qualche minuto",//500
    TIMEOUT = "Attenzione! la rete da cui si è collegati risulta essere troppo lenta",  //599
    UNAUTHORIZED = "Attenzione! Si è tentato di accedere ad una risorsa a cui non si è abilitati.",//403
    NOTAUTHENTICATED = "Attenzione! Si è verificato un problema di autenticazione", //401
    NOTFOUND = "Attenzione! Si è cercato di accendere ad una risorsa inesistente", //404
    INPUTERROR = "Attenzione Errore durante il salvataggio, verificare i dati inseriti",
    BADREQUEST = "Attenzione ! parametri in input errati",
    FILE_SIZE_ERR = "Attenzione! L'allegato supera le dimensioni massime consentite"
}


export enum Esito {
    SUCCESS = "S",
    ERROR = "E",
}


export class ErrorType {
    public static PROCEDURE_WARNING: string = "W";
    public static PROCEDURE_ERROR: string = "E";
}

@Injectable()
export class HandleExceptionInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).timeout(this.config.getTimeout() * 1000)
            .map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    let log: string = 'RESP FOR '.concat(req.url).concat(':');
                    this.loggerService.info(log, event);
                    if (event != null && event.body != null && event.body.hasOwnProperty('codice') && event.body.hasOwnProperty('message')) {
                        let ex: ExceptionVO = this.parseObjectReturn(event.body);//JSON ONJECT
                        throw ex;
                    }
                }
                return event;

            })
            .catch((err: any, caught) => {
                //CASISTICA ERRORI DI RISPOSTA
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 413) {
                        this.loggerService.error(err.status + ": " + MessageRestError.FILE_SIZE_ERR);
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.FILE_SIZE_ERR }, skipLocationChange: true });
                    }
                    else if (err.status === 400) {
                        this.loggerService.error("400: Errore di sintassi errata");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.BADREQUEST }, skipLocationChange: true });
                    }
                    else if (err.status === 401) {
                        this.loggerService.error("401: La richiesta richiede autenticazione ");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.NOTAUTHENTICATED }, skipLocationChange: true });
                    }
                    else if (err.status === 403) {
                        this.loggerService.error("403: Non si autorizzati ad accedere alla servizio rest chiamato");
                        if (err.error != null && err.error.codice != null && err.error.codice == "SESSIONINVALID")
                            this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: err.error.message }, skipLocationChange: true });
                        else
                            this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.UNAUTHORIZED }, skipLocationChange: true });
                    }
                    else if (err.status === 404) {
                        this.loggerService.error("404: Il server non ha trovato nulla che corrisponda all'URI di richiesta.");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.NOTFOUND }, skipLocationChange: true });
                    }
                    else if (err.status >= 500) {
                        this.loggerService.info(err);
                        this.loggerService.error("500+: Il server ha riscontrato una condizione imprevista che gli ha impedito di soddisfare la richiesta.");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.GENERIC }, skipLocationChange: true });
                    }
                    else {
                        this.loggerService.error("Il codice di errore ritornato non è conosciuto dal FE: " + err);
                       // this.router.navigate(['/errorrest'], { queryParams: { err: "undefined", message: MessageRestError.GENERIC }, skipLocationChange: true });
                        this.router.navigate(['/errorrest'], { queryParams: { err: "500", message: MessageRestError.GENERIC }, skipLocationChange: true });
                    }

                    return Observable.throw(err);
                }
                //CASISTICA 200 CON ERROR
                else if (err instanceof ExceptionVO) {
                    return Observable.throw(err);
                }
                else if (err.name == "TimeoutError") {
                    this.loggerService.error("Si è verificato un timeout");
                    return Observable.throw(new ExceptionVO("TMO", "Si è verificato un timeout", "danger"));
                }
                else {
                    this.loggerService.error("L'errore ritornato non è conosciuto dal FE:" + err);
                    this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.GENERIC }, skipLocationChange: true });
                }
            });

    }

    private parseObjectReturn(object: any): ExceptionVO {
        if (typeof (object) === 'string' || object instanceof String) {
            object = JSON.parse(object.toString());
            if (object.hasOwnProperty('esito') && object.esito == Esito.ERROR) {
                object = object.object;
            }

        }
        return new ExceptionVO(object.codice, object.message, object.type);
    }

    constructor(private config: ConfigService, private loggerService: LoggerService, private router: Router) { }


}