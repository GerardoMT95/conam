import { Pipe, PipeTransform } from '@angular/core';
/*
 * Raise the value exponentially
 * Takes an exponent argument that defaults to 1.
 * Usage:
 *   value | exponentialStrength:exponent
 * Example:
 *   {{ 2 | exponentialStrength:10 }}
 *   formats to: 1024
*/
@Pipe({ name: 'hour' })
export class HourPipe implements PipeTransform {
    transform(value: number): string {
        let v: string = value.toString();
        if (v.length < 2) {
            if (value == 0) return "00";
            else {
                return "0" + v;
            }
        }
        return v;
    }
}