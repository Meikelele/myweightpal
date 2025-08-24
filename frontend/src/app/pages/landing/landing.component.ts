import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { MwpBrandComponent } from '../../shared/ui/mwp-brand/mwp-brand.component';
@Component({
  standalone: true,
  selector: 'app-landing',
  imports: [ButtonModule, MwpBrandComponent],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {
  private router = inject(Router);

  navigateToLogin(): void {
    this.router.navigateByUrl('/login');
  }
}
