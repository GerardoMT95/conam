export class TipologiaAllegabiliRequest {
    public id: number;
    public tipoDocumento: string;   //una stringa presente nel file delle costanti
    public aggiungiCategoriaEmail: boolean;
    public idOrdinanzaAnnullata? : number;
}