import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
  protected editingAuteur: AuteurDto | null = null;

  constructor(
    private auteurService: AuteurService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadAuteurs();
    this.buildForm();
  }

  private loadAuteurs(): void {
    this.auteurs$ = this.auteurService.findAll();
  }

  private buildForm(): void {
    this.auteurForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      nationalite: ['']
    });
  }

  saveAuteur(): void {
    if (this.auteurForm.invalid) {
      this.auteurForm.markAllAsTouched();
      return;
    }

    const formValue = this.auteurForm.value as AuteurDto;

    // UPDATE
    if (this.editingAuteur && this.editingAuteur.id != null) {
      this.auteurService.update(this.editingAuteur.id, formValue).subscribe({
        next: () => {
          this.resetForm();
          this.loadAuteurs();
        },
        error: (err:any) => console.error(err)
      });
    }
    // CREATE
    else {
      this.auteurService.create(formValue).subscribe({
        next: () => {
          this.resetForm();
          this.loadAuteurs();
        },
        error: (err:any) => console.error(err)
      });
    }
  }

  editAuteur(auteur: AuteurDto): void {
    this.editingAuteur = auteur;
    this.auteurForm.patchValue({
      nom: auteur.nom,
      prenom: auteur.prenom,
      nationalite: auteur.nationalite ?? ''
    });
  }

  deleteAuteur(auteur: AuteurDto): void {
    if (!auteur.id) return;
    if (!confirm(`Supprimer ${auteur.prenom} ${auteur.nom} ?`)) return;

    this.auteurService.delete(auteur.id).subscribe({
      next: () => this.loadAuteurs(),
      error: (err:any) => console.error(err)
    });
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.editingAuteur = null;
    this.auteurForm.reset({
      nom: '',
      prenom: '',
      nationalite: ''
    });
  }
}
