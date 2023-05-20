import { SafeUrl } from "@angular/platform-browser";

export interface IMyUrl {
  nomeFile: string,
  safeUrl: SafeUrl
}
export class MyUrl implements IMyUrl{
    constructor(
      public nomeFile: string,
      public safeUrl: SafeUrl) { }
}