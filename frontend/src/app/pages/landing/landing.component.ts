import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { MwpBrandComponent } from '../../shared/ui/mwp-brand/mwp-brand.component';

@Component({
  standalone: true,
  selector: 'app-landing',
  imports: [ButtonModule, MwpBrandComponent],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {}
