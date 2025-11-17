import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../service/collection-service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { CollectionDto } from '../../dto/collection-dto';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-collection-page',
  imports: [CommonModule, RouterLink, ReactiveFormsModule ],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {
   protected collections$!: Observable<CollectionDto[]>;
  protected collectionForm!: FormGroup;
  protected labelCtrl!: FormControl;
  protected editingCollection!: CollectionDto | null;

  constructor(private collectionService: CollectionService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.collections$ = this.collectionService.findAll();

    this.labelCtrl = this.formBuilder.control('', Validators.required);

    this.collectionForm = this.formBuilder.group({
      label: this.labelCtrl
    });
  }

  public trackCollection(index: number, value: CollectionDto) {
    return value.id;
  }

  public creerOuModifier() {
    if (this.editingCollection) {
      this.collectionService.save(new CollectionDto(this.editingCollection.id, this.labelCtrl.value));
    }

    else {
      this.collectionService.save(new CollectionDto(0, this.labelCtrl.value));
      console.log("ajouté ligne bdd");
    }

    this.editingCollection = null;
    this.labelCtrl.setValue("");
  }

  public editer(collection: CollectionDto) {
    this.editingCollection = collection;
    this.labelCtrl.setValue(collection.nom);
  }

  public supprimer(collection: CollectionDto) {
    this.collectionService.deleteById(collection.id);
  }

}
