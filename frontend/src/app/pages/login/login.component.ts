import { Component, signal } from '@angular/core';
import { CardModule } from 'primeng/card';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CardModule, ReactiveFormsModule, ButtonModule, InputTextModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  // TODO: spinner
  loading = signal(false);
  form!: FormGroup;

  constructor(private fb: FormBuilder) { 
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.loading.set(true);

    const { email, password } = this.form.value;
    console.log('LOGIN PAYLOAD', { email, password });

    // TODO: przekierowanie na /dashboard
    // this.router.navigateByUrl('/dashboard');
    setTimeout(() => this.loading.set(false), 400);
  }
  
  // submit() {
  //   if (this.form.invalid) {
  //     this.form.markAllAsTouched();
  //     return;
  //   }
  //   this.loading.set(true);

  //   // Na tym etapie tylko demo:
  //   const { email, password } = this.form.value;
  //   console.log('LOGIN PAYLOAD', { email, password });

  //   // symulacja końca akcji
  //   setTimeout(() => this.loading.set(false), 400);
  // }

  get f() {
    return this.form.controls;
  }

}
