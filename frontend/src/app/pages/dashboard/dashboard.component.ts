import { Component, inject, signal } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';

import { DatePickerModule } from 'primeng/datepicker';
import { InputNumberModule } from 'primeng/inputnumber';
import { CheckboxModule } from 'primeng/checkbox';
import { TextareaModule } from 'primeng/textarea';

@Component({
  standalone: true,
  selector: 'app-dashboard',
  imports: [
    ReactiveFormsModule,
    DatePickerModule,
    InputNumberModule,
    CheckboxModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  private formBuilder = inject(FormBuilder);

  form = this.formBuilder.group({
    date: [new Date(), Validators.required],
    weight: ['', Validators.required],
    tookCreatine: [false],
    comment: [''],
  });

  protected onSave() {
    console.log('Save!');
    console.log(this.form.value);
  }
}
