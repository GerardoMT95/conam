import { Directive, Attribute } from "@angular/core";
import { Validator, AbstractControl, NG_VALIDATORS } from "@angular/forms";

@Directive({
    selector: '[numeroRate]',
    providers: [{ provide: NG_VALIDATORS, useExisting: NumeroRateDirective, multi: true }]
})
export class NumeroRateDirective implements Validator {

    public validate(control: AbstractControl): { [key: string]: any } {
        return !control.value || (control.value >= 3 && control.value <= 30) ? null : { 'wrongAmount': true };
    }
}