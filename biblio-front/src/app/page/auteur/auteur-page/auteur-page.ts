import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { AuteurDto } from '../../../dto/auteur-dto';
import { AuteurService } from '../../../service/auteur.service';

@Component({
  selector: 'app-auteur-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './auteur-page.html',
  styleUrl: './auteur-page.css'
})
export class AuteurPage implements OnInit {

  protected auteurs$!: Observable<AuteurDto[]>;
  protected auteurForm!: FormGroup;

  protected nomCtrl!: FormControl;
  protected prenomCtrl!: FormControl;
  protected nationaliteCtrl!: FormControl;

  protected editingAuteur!: AuteurDto | null;

  constructor(
    private auteurService: AuteurService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    // même pattern que MatierePage
    this.auteurs$ = this.auteurService.findAll();

    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.prenomCtrl = this.formBuilder.control('', Validators.required);
    this.nationaliteCtrl = this.formBuilder.control('', Validators.required);

    this.auteurForm = this.formBuilder.group({
      nom: this.nomCtrl,
      prenom: this.prenomCtrl,
      nationalite: this.nationaliteCtrl
    });

    this.editingAuteur = null;
  }

  public trackAuteur(index: number, value: AuteurDto) {
    return value.id;
  }

  public creerOuModifier(): void {
    if (!this.auteurForm.valid) {
      this.auteurForm.markAllAsTouched();
      return;
    }

    const nom = this.nomCtrl.value;
    const prenom = this.prenomCtrl.value;
    const nationalite = this.nationaliteCtrl.value;

    if (this.editingAuteur) {
      // UPDATE
      this.auteurService.save(
        new AuteurDto(this.editingAuteur.id, nom, prenom, nationalite)
      );
    } else {
      // CREATE (id = 0 -> !id => POST dans le service, comme pour Matiere)
      this.auteurService.save(
        new AuteurDto(0, nom, prenom, nationalite)
      );
    }

    this.editingAuteur = null;
    this.nomCtrl.setValue('');
    this.prenomCtrl.setValue('');
    this.nationaliteCtrl.setValue('');
  }

  public editer(auteur: AuteurDto): void {
    this.editingAuteur = auteur;

    this.nomCtrl.setValue(auteur.nom);
    this.prenomCtrl.setValue(auteur.prenom);
    this.nationaliteCtrl.setValue(auteur.nationalite);
  }

  public supprimer(auteur: AuteurDto): void {
    this.auteurService.deleteById(auteur.id);
  }
}

