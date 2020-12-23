import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAeroport } from 'app/shared/model/aeroport.model';
import { AeroportService } from './aeroport.service';

@Component({
  templateUrl: './aeroport-delete-dialog.component.html',
})
export class AeroportDeleteDialogComponent {
  aeroport?: IAeroport;

  constructor(protected aeroportService: AeroportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aeroportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aeroportListModification');
      this.activeModal.close();
    });
  }
}
