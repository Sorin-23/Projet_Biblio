export class AuteurDto {
    constructor(private _id:number, private _nom:string, private _prenom: string,
        private _nationalite:string){}

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get nom(): string {
        return this._nom;
    }

    public set nom(value: string) {
        this._nom = value;
    }

    public get prenom(): string {
        return this._prenom;
    }

    public set prenom(value: string) {
        this._prenom = value;
    }

    public get nationalite(): string {
        return this._nationalite;
    }

    public set nationalite(value: string) {
        this._nationalite= value;
    }

    public toJson(): any {
        return {
            nom: this.nom,
            prenom:this.prenom,
            nationalite:this.nationalite
        };
    }  


}
