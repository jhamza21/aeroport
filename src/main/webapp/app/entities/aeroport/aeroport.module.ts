import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AeroportSharedModule } from 'app/shared/shared.module';
import { AeroportComponent } from './aeroport.component';
import { AeroportDetailComponent } from './aeroport-detail.component';
import { AeroportUpdateComponent } from './aeroport-update.component';
import { AeroportDeleteDialogComponent } from './aeroport-delete-dialog.component';
import { aeroportRoute } from './aeroport.route';

@NgModule({
  imports: [AeroportSharedModule, RouterModule.forChild(aeroportRoute)],
  declarations: [AeroportComponent, AeroportDetailComponent, AeroportUpdateComponent, AeroportDeleteDialogComponent],
  entryComponents: [AeroportDeleteDialogComponent],
})
export class AeroportAeroportModule {}
