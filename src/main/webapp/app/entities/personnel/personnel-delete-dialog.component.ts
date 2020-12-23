import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonnel } from 'app/shared/model/personnel.model';
import { PersonnelService } from './personnel.service';

@Component({
  templateUrl: './personnel-delete-dialog.component.html',
})
export class PersonnelDeleteDialogComponent {
  personnel?: IPersonnel;

  constructor(protected personnelService: PersonnelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personnelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personnelListModification');
      this.activeModal.close();
    });
  }
}
