import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { LivreDto } from '../../../dto/livre-dto';
import { AuteurDto } from '../../../dto/auteur-dto';
import { EditeurDto } from '../../../dto/editeur-dto';
import { CollectionDto } from '../../../dto/collection-dto';

import { LivreService } from '../../../service/livre-service';
//import { AuteurService } from '../../../service/auteur-service';
//import { EditeurService } from '../../../service/editeur-service';
//import { CollectionService } from '../../../service/collection-service';

@Component({
  selector: 'app-livre-page',
  standalone: true,
  imports: [ CommonModule, RouterLink, ReactiveFormsModule ],
  templateUrl: './livre-page.html',
  styleUrl: './livre-page.css',
})
export class LivrePage implements OnInit {
  // listes observables (comme matieres$)
  protected livres$!: Observable<LivreDto[]>;
  protected auteurs$!: Observable<AuteurDto[]>;
  protected editeurs$!: Observable<EditeurDto[]>;
  protected collections$!: Observable<CollectionDto[]>;

  // formulaire
  protected livreForm!: FormGroup;
  protected titreCtrl!: FormControl;
  protected resumeCtrl!: FormControl;
  protected anneeCtrl!: FormControl;
  protected auteurCtrl!: FormControl;      // contiendra un AuteurDto
  protected editeurCtrl!: FormControl;     // contiendra un EditeurDto
  protected collectionCtrl!: FormControl;  // contiendra un CollectionDto

  protected editingLivre!: LivreDto | null;

  constructor(
    private livreService: LivreService,
    private auteurService: AuteurService,
    private editeurService: EditeurService,
    private collectionService: CollectionService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    // chargement des listes (pattern refresh$ dans le service)
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
    if (!this.livreForm.valid) {
      this.livreForm.markAllAsTouched();
      return;
    }

    const titre = this.titreCtrl.value;
    const resume = this.resumeCtrl.value;
    const annee = this.anneeCtrl.value;
    const auteur = this.auteurCtrl.value as AuteurDto;
    const editeur = this.editeurCtrl.value as EditeurDto;
    const collection = this.collectionCtrl.value as CollectionDto;

    if (this.editingLivre) {
      // UPDATE
      this.livreService.save(
        new LivreDto(
          this.editingLivre.id,
          titre,
          resume,
          annee,
          auteur,
          editeur,
          collection
        )
      );
    } else {
      // CREATE (id = 0 -> !id => POST dans le service)
      this.livreService.save(
        new LivreDto(
          0,
          titre,
          resume,
          annee,
          auteur,
          editeur,
          collection
        )
      );
    }

    this.editingLivre = null;
    this.titreCtrl.setValue('');
    this.resumeCtrl.setValue('');
    this.anneeCtrl.setValue('');
    this.auteurCtrl.setValue(null);
    this.editeurCtrl.setValue(null);
    this.collectionCtrl.setValue(null);
  }

  public editer(livre: LivreDto): void {
    this.editingLivre = livre;

    this.titreCtrl.setValue(livre.titre);
    this.resumeCtrl.setValue(livre.resume);
    this.anneeCtrl.setValue(livre.annee);
    this.auteurCtrl.setValue(livre.auteur);
    this.editeurCtrl.setValue(livre.editeur);
    this.collectionCtrl.setValue(livre.collection);
  }

  public supprimer(livre: LivreDto): void {
    this.livreService.deleteById(livre.id);
  }
}
