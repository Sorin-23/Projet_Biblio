import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { AuteurDto } from '../../../dto/auteur-dto';
import { AuteurService } from '../../../service/auteur-service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-auteur-page',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
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

  constructor(private auteurService: AuteurService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.auteurs$ = this.auteurService.findAll();

    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.prenomCtrl = this.formBuilder.control('', Validators.required);
    this.nationaliteCtrl = this.formBuilder.control('', Validators.required);

    this.auteurForm = this.formBuilder.group({
      nom: this.nomCtrl,
      prenom: this.prenomCtrl,
      nationalite : this.nationaliteCtrl
    });
  }

  public trackAuteur(index: number, value: AuteurDto) {
    return value.id;
  }

  public creerOuModifier() {
    if (this.editingAuteur) {
      this.auteurService.save(new AuteurDto(this.editingAuteur.id, this.nomCtrl.value,  this.prenomCtrl.value,  this.nationaliteCtrl.value));
    }

    else {
      this.auteurService.save(new AuteurDto(0, this.nomCtrl.value,   this.prenomCtrl.value,  this.nationaliteCtrl.value));
      console.log("ajouté ligne bdd");
    }

    this.editingAuteur = null;
    this.nomCtrl.setValue("");
    this.prenomCtrl.setValue("");
    this.nationaliteCtrl.setValue("");
  }

  public editer(auteur: AuteurDto) {
    this.editingAuteur = auteur;
    this.nomCtrl.setValue(auteur.nom);
    this.prenomCtrl.setValue(auteur.prenom);
    this.nationaliteCtrl.setValue(auteur.nationalite);
  }

  public supprimer(auteur: AuteurDto) {
    this.auteurService.deleteById(auteur.id);
  }
}
