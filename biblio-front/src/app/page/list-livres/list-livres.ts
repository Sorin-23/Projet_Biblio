import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { LivreDto } from '../../dto/livre-dto';
import { LivreService } from '../../service/livre-service';

@Component({
  selector: 'app-list-livres',
  imports: [CommonModule,RouterLink,ReactiveFormsModule],
  templateUrl: './list-livres.html',
  styleUrl: './list-livres.css',
})
export class ListLivres implements OnInit{
  protected livres$!: Observable<LivreDto[]>;
  protected livreForm!: FormGroup;
  protected titreCtrl!:FormControl;
  protected anneeCtrl!:FormControl;
  protected resumeCtrl!:FormControl;

  protected editingLivre!:LivreDto |null;
  constructor(private livreService:LivreService){}

  ngOnInit(): void {
      this.livres$ = this.livreService.findAll();
  }
  public trackLivre(index: number, value: LivreDto) {
    return value.id;
  }

}
