import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";

@Injectable()
export class AllegatoUtilsSharedService implements OnDestroy {

    constructor(
        private logger: LoggerService) {
        this.logger.createService(AllegatoUtilsSharedService.name);
    }

    b64toBlob(b64Data: string, contentType: string) {
        let sliceSize = 512;

        var byteCharacters = atob(b64Data);
        var byteArrays = [];

        for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            var slice = byteCharacters.slice(offset, offset + sliceSize);

            var byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }

            var byteArray = new Uint8Array(byteNumbers);

            byteArrays.push(byteArray);
        }

        var blob = new Blob(byteArrays, { type: contentType });
        return blob;
    }

    ngOnDestroy(): void {
        this.logger.destroyService(AllegatoUtilsSharedService.name);
    }
}