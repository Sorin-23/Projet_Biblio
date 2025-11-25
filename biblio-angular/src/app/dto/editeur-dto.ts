export class EditeurDto {
    constructor(private _id: number, private _nom: string, private _pays:string){}

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get nom():string{
        return this._nom;
    }

    public set nom(value:string){
        this._nom=value;
    }

    public get pays():string{
        return this._pays;
    }

    public set pays(value:string){
        this._pays = value;
    }

    public toJson():any{
        return {nom:this.nom,
            pays:this.pays
        };
    }
}
