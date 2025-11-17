import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRequest } from '../../dto/auth-request';
import { AuthService } from '../../service/auth-service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage implements OnInit {
  protected loginError: boolean = false;
  protected userForm!: FormGroup;
  protected usernameCtrl!: FormControl;
  protected passwordCtrl!: FormControl;

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.usernameCtrl = this.formBuilder.control('', Validators.required);
    this.passwordCtrl = this.formBuilder.control('', [ Validators.required, Validators.minLength(6) ]);

    this.userForm = this.formBuilder.group({
      username: this.usernameCtrl,
      password: this.passwordCtrl
    });
  }

  public async connecter() {
    try {
      // La méthode auth renvoyant une Promise, on peut attendre la résolution avec "await"
      await this.authService.auth(new AuthRequest(this.usernameCtrl.value, this.passwordCtrl.value));

      // Si tout est OK, on va sur la page des matières
      this.router.navigate([ '/livre' ]);
    }

    // Si la connexion n'a pas pu se faire, affichage de l'erreur sur le template
    catch {
      this.loginError = true;
    }
  }
}
