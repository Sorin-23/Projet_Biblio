import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { LivreDto } from '../../dto/livre-dto';
import { EditeurDto} from '../../dto/editeur-dto';
import { AuteurDto } from '../../dto/auteur-dto';
import { CollectionDto } from '../../dto/collection-dto';
import { LivreService } from '../../service/livre-service';
import { AuteurService} from '../../service/auteur-service';
import { CollectionService } from '../../service/collection-service';
import { EditeurService } from '../../service/editeur-service';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-list-livres',
  imports: [CommonModule,RouterLink,ReactiveFormsModule],
  templateUrl: './list-livres.html',
  styleUrl: './list-livres.css',
})
export class ListLivres implements OnInit{

  protected livres$!: Observable<LivreDto[]>;
  protected auteurs$!: Observable<AuteurDto[]>;
  protected editeurs$!: Observable<EditeurDto[]>;
  protected collections$!: Observable<CollectionDto[]>;
  
  protected livreForm!: FormGroup;
  protected titreCtrl!: FormControl;
  protected resumeCtrl!: FormControl;
  protected anneeCtrl!: FormControl;
  protected auteurCtrl!: FormControl;      // contiendra un AuteurDto
  protected editeurCtrl!: FormControl;     // contiendra un EditeurDto
  protected collectionCtrl!: FormControl;  // contiendra un CollectionDto

  

  protected editingLivre!:LivreDto |null;
  constructor(private livreService:LivreService,
    private auteurService: AuteurService,
        private editeurService: EditeurService,
        private collectionService: CollectionService,
         private formBuilder: FormBuilder){}

  ngOnInit(): void {
      this.livres$ = this.livreService.findAll();
      this.auteurs$ = this.auteurService.findAll();
    this.editeurs$ = this.editeurService.findAll();
    this.collections$ = this.collectionService.findAll();
      

      // contrôles du formulaire
    this.titreCtrl = this.formBuilder.control('', Validators.required);
    this.resumeCtrl = this.formBuilder.control('', Validators.required);
    this.anneeCtrl = this.formBuilder.control('', Validators.required);
    this.auteurCtrl = this.formBuilder.control(null, Validators.required);
    this.editeurCtrl = this.formBuilder.control(null, Validators.required);
    this.collectionCtrl = this.formBuilder.control(null, Validators.required);

    this.livreForm = this.formBuilder.group({
      titre: this.titreCtrl,
      resume: this.resumeCtrl,
      annee: this.anneeCtrl,
      auteur: this.auteurCtrl,
      editeur: this.editeurCtrl,
      collection: this.collectionCtrl
    });

    this.editingLivre = null;




  }
  public trackLivre(index: number, value: LivreDto) {
    return value.id;
  }

  public creerOuModifier(): void {
    if (this.editingLivre) {
      this.livreService.save(new LivreDto(this.editingLivre.id,this.titreCtrl.value,
        this.resumeCtrl.value, this.anneeCtrl.value, this.auteurCtrl.value, this.editeurCtrl.value, this.collectionCtrl.value
      ));
    }
    else{
      console.log(this.titreCtrl.value);
      console.log(this.anneeCtrl.value);
      console.log(this.auteurCtrl.value);
      this.livreService.save(new LivreDto(0,this.titreCtrl.value,
        this.resumeCtrl.value, this.anneeCtrl.value, this.auteurCtrl.value, this.editeurCtrl.value, this.collectionCtrl.value));
    }
    this.editingLivre = null;
    this.titreCtrl.setValue("");
    this.resumeCtrl.setValue("");
    this.anneeCtrl.setValue("");
    this.auteurCtrl.setValue(null);
    this.editeurCtrl.setValue(null);
    this.collectionCtrl.setValue(null);


  }
  public editer(livre:LivreDto){
    this.editingLivre = livre;
    this.titreCtrl.setValue(livre.titre);
    this.anneeCtrl.setValue(livre.annee);
    this.resumeCtrl.setValue(livre.resume);
    this.auteurCtrl.setValue(livre.auteur?.id || null);
  this.editeurCtrl.setValue(livre.editeur?.id || null);
  this.collectionCtrl.setValue(livre.collection?.id || null);
    console.log("idAuteur", this.auteurCtrl.value);

  }

  public supprimer(livre: LivreDto): void {
    this.livreService.deleteById(livre.id);
  }

}
