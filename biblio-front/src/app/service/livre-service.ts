import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { LivreDto } from '../dto/livre-dto';

@Injectable({
  providedIn: 'root',
})
export class LivreService {
  private apiUrl: string = "/livre";
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http:HttpClient){}

  public findAll():Observable<LivreDto[]>{
    return this.refresh$.pipe(
      startWith(null),
      switchMap(()=>{return this.http.get<LivreDto[]>(this.apiUrl);

      })
    );
  }

  public refresh(){
    this.refresh$.next();
  }

  public findById(id:number):Observable<LivreDto>{
    return this.http.get<LivreDto>(`${ this.apiUrl }/${ id }`);
  }

  public save(livreDto: LivreDto): void {
    const payload = {
    titre: livreDto.titre,
    resume: livreDto.resume,
    annee: livreDto.annee,
    auteurId: livreDto.auteur.id,   
    editeurId: livreDto.editeur.id,
    collectionId: livreDto.collection.id
  }
  console.log('payload envoyé:', payload);
  ;

    if (!livreDto.id) {
      this.http.post<LivreDto>(this.apiUrl, payload).subscribe(() => this.refresh());
    }

    else {
      this.http.put<LivreDto>(`${ this.apiUrl }/${ livreDto.id }`, payload).subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${ this.apiUrl }/${ id }`).subscribe(() => this.refresh());
  }
  
}
