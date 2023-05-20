import { Directive, Attribute } from "@angular/core";
import { Validator, AbstractControl, NG_VALIDATORS } from "@angular/forms";
import { Constants } from "../../commons/class/constants";

@Directive({
    selector: '[decimalPlaces]',
    providers: [{ provide: NG_VALIDATORS, useExisting: DecimalDirective, multi: true }]
})
export class DecimalDirective implements Validator {

    public constructor(
        @Attribute('decimalPlaces') public typeId: number
    ) { }

    private MAX_NUMBER: number = 99999999;
    private MIN_NUMBER: number = 0;

    public validate(control: AbstractControl): { [key: string]: any } {
        if ((this.typeId && this.typeId != Constants.FT_NUMERIC) || !control.value) return null;

        try {
            let x: number = parseFloat(control.value);
            if (x < this.MIN_NUMBER) return { 'negativeNumber': true }
            if (x > this.MAX_NUMBER) return { 'maxNumber': true }
        } catch (e) {
            return { 'numberFormatInvalid': true }
        }

        let decimalRegEx = /^[0-9]+(\.[0-9]{1,2})?$/i;
        let valid = decimalRegEx.test(control.value);

        return valid ? null : { 'decimalPlaces': true };
    }
}