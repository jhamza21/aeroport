import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAeroport, Aeroport } from 'app/shared/model/aeroport.model';
import { AeroportService } from './aeroport.service';

@Component({
  selector: 'jhi-aeroport-update',
  templateUrl: './aeroport-update.component.html',
})
export class AeroportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    ville: [null, [Validators.required]],
    maxAvion: [null, [Validators.required]],
  });

  constructor(protected aeroportService: AeroportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aeroport }) => {
      this.updateForm(aeroport);
    });
  }

  updateForm(aeroport: IAeroport): void {
    this.editForm.patchValue({
      id: aeroport.id,
      name: aeroport.name,
      ville: aeroport.ville,
      maxAvion: aeroport.maxAvion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aeroport = this.createFromForm();
    if (aeroport.id !== undefined) {
      this.subscribeToSaveResponse(this.aeroportService.update(aeroport));
    } else {
      this.subscribeToSaveResponse(this.aeroportService.create(aeroport));
    }
  }

  private createFromForm(): IAeroport {
    return {
      ...new Aeroport(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      maxAvion: this.editForm.get(['maxAvion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAeroport>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
