import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { RegioneVO, ProvinciaVO, ComuneVO, NazioneVO } from "../../commons/vo/select-vo";


@Injectable()
export class LuoghiService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(LuoghiService.name);
    }

    getRegioni(): Observable<Array<RegioneVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/regioni';
        return this.http.get<Array<RegioneVO>>(url);
    }
    getcomuniEnteValidInDate () {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/comuniEnteValidInDate';
        return this.http.get<Array<ProvinciaVO>>(url);
    }

    getProvinciaByIdRegione(id: number) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/provinceByIdRegione';
        let params = new HttpParams().set('idRegione', id.toString());
        return this.http.get<Array<ProvinciaVO>>(url, { params: params });
    }

    getComuneByIdProvincia(id: number) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/comuniByIdProvincia';
        let params = new HttpParams().set('idProvincia', id.toString());
        return this.http.get<Array<ComuneVO>>(url, { params: params });
    }

    getRegioniNascita(): Observable<Array<RegioneVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/regioniNascita';
        return this.http.get<Array<RegioneVO>>(url);
    }

    getProvinciaByIdRegioneNascita(id: number) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/provinceByIdRegioneNascita';
        let params = new HttpParams().set('idRegione', id.toString());
        return this.http.get<Array<ProvinciaVO>>(url, { params: params });
    }

    getComuneByIdProvinciaNascita(id: number) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/comuniByIdProvinciaNascita';
        let params = new HttpParams().set('idProvincia', id.toString());
        return this.http.get<Array<ComuneVO>>(url, { params: params });
    }

    getNazioni() {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/nazioni';
        return this.http.get<Array<NazioneVO>>(url);
    }

    getProvinciaByIdRegioneDate(id: number, dataOraAccertamento :string) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/provinceByIdRegioneValidInDate';
        let params = new HttpParams().set('idRegione', id.toString()).set('dataOraAccertamento', dataOraAccertamento );
        return this.http.get<Array<ProvinciaVO>>(url, { params: params });
    }
    
    getComuneByIdProvinciaDate(id: number, dataOraAccertamento :string) {
        var url: string = this.config.getBEServer() + '/restfacade/luoghi/comuniByIdProvinciaValidInDate';
        let params = new HttpParams().set('idProvincia', id.toString()).set('dataOraAccertamento', dataOraAccertamento );
        return this.http.get<Array<ComuneVO>>(url, { params: params });
    }
    
    getReminderData(){
        var url: string = this.config.getBEServer() + '/restfacade/calendar/getReminderData';
        return this.http.get<any>(url);
    }
}