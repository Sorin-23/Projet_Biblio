import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { EditeurDto } from '../../dto/editeur-dto';
import { EditeurService } from '../../service/editeur-service';

@Component({
  selector: 'app-editeur-page',
  imports: [CommonModule, RouterLink, ReactiveFormsModule ],
  templateUrl: './editeur-page.html',
  styleUrl: './editeur-page.css',
})
export class EditeurPage implements OnInit {
  protected editeurs$!: Observable<EditeurDto[]>;
  protected editeurForm!: FormGroup;
  protected labelCtrl!: FormControl;
  protected paysCtrl!: FormControl;
  protected editingEditeur!: EditeurDto | null;

  constructor(private editeurService: EditeurService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.editeurs$ = this.editeurService.findAll();

    this.labelCtrl = this.formBuilder.control('', Validators.required);
    this.paysCtrl = this.formBuilder.control('', Validators.required);
    this.editeurForm = this.formBuilder.group({
      label: this.labelCtrl,
      pays : this.paysCtrl
    });
  }

  public trackEditeur(index: number, value: EditeurDto) {
    return value.id;
  }

  public creerOuModifier() {
    if (this.editingEditeur) {
      this.editeurService.save(new EditeurDto(this.editingEditeur.id, this.labelCtrl.value, this.paysCtrl.value));
    }

    else {
      this.editeurService.save(new EditeurDto(0, this.labelCtrl.value , this.paysCtrl.value));
      console.log("ajouté ligne bdd");
    }

    this.editingEditeur = null;
    this.labelCtrl.setValue("");
    this.paysCtrl.setValue("");
  }

  public editer(editeur: EditeurDto) {
    this.editingEditeur = editeur;
    this.labelCtrl.setValue(editeur.nom);
    this.paysCtrl.setValue(editeur.pays);
  }

  public supprimer(editeur: EditeurDto) {
    this.editeurService.deleteById(editeur.id);
  }
}





