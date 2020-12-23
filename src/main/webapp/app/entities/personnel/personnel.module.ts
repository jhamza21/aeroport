import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AeroportSharedModule } from 'app/shared/shared.module';
import { PersonnelComponent } from './personnel.component';
import { PersonnelDetailComponent } from './personnel-detail.component';
import { PersonnelUpdateComponent } from './personnel-update.component';
import { PersonnelDeleteDialogComponent } from './personnel-delete-dialog.component';
import { personnelRoute } from './personnel.route';

@NgModule({
  imports: [AeroportSharedModule, RouterModule.forChild(personnelRoute)],
  declarations: [PersonnelComponent, PersonnelDetailComponent, PersonnelUpdateComponent, PersonnelDeleteDialogComponent],
  entryComponents: [PersonnelDeleteDialogComponent],
})
export class AeroportPersonnelModule {}
