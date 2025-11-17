import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../service/auth-service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage implements OnInit {
  protected loginForm!: FormGroup;
  protected usernameCtrl!: FormControl;
  protected passwordCtrl!: FormControl;
  protected errorMessage: string | null = null;
  protected isLoading = false;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usernameCtrl = this.formBuilder.control('', [Validators.required]);
    this.passwordCtrl = this.formBuilder.control('', [Validators.required]);

    this.loginForm = this.formBuilder.group({
      username: this.usernameCtrl,
      password: this.passwordCtrl,
    });
  }

  public login(): void {
    if (!this.loginForm.valid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;

    const username = this.usernameCtrl.value;
    const password = this.passwordCtrl.value;

    this.authService.login(username, password).subscribe({
      next: (response:any) => {
        this.isLoading = false;
        this.router.navigate(['/home']);
      },
      error: (error:any) => {
        this.isLoading = false;
        this.errorMessage = 'Identifiants invalides. Veuillez réessayer.';
        console.error('Login error:', error);
      },
    });
  }

  public getErrorMessage(controlName: string): string | null {
    const control = this.loginForm.get(controlName);
    if (control && control.invalid && control.touched) {
      if (control.hasError('required')) {
        return `${controlName === 'username' ? 'Nom d\'utilisateur' : 'Mot de passe'} requis`;
      }
    }
    return null;
  }
}
