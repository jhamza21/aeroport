import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'personnel',
        loadChildren: () => import('./personnel/personnel.module').then(m => m.AeroportPersonnelModule),
      },
      {
        path: 'avion',
        loadChildren: () => import('./avion/avion.module').then(m => m.AeroportAvionModule),
      },
      {
        path: 'aeroport',
        loadChildren: () => import('./aeroport/aeroport.module').then(m => m.AeroportAeroportModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AeroportEntityModule {}
