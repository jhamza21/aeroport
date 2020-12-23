import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAvion, Avion } from 'app/shared/model/avion.model';
import { AvionService } from './avion.service';
import { IAeroport } from 'app/shared/model/aeroport.model';
import { AeroportService } from 'app/entities/aeroport/aeroport.service';

@Component({
  selector: 'jhi-avion-update',
  templateUrl: './avion-update.component.html',
})
export class AvionUpdateComponent implements OnInit {
  isSaving = false;
  aeroports: IAeroport[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [null, [Validators.required]],
    company: [null, [Validators.required]],
    dateArr: [null, [Validators.required]],
    dateDep: [null, [Validators.required]],
    aeroport: [],
  });

  constructor(
    protected avionService: AvionService,
    protected aeroportService: AeroportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avion }) => {
      if (!avion.id) {
        const today = moment().startOf('day');
        avion.dateArr = today;
        avion.dateDep = today;
      }

      this.updateForm(avion);

      this.aeroportService.query().subscribe((res: HttpResponse<IAeroport[]>) => (this.aeroports = res.body || []));
    });
  }

  updateForm(avion: IAvion): void {
    this.editForm.patchValue({
      id: avion.id,
      matricule: avion.matricule,
      company: avion.company,
      dateArr: avion.dateArr ? avion.dateArr.format(DATE_TIME_FORMAT) : null,
      dateDep: avion.dateDep ? avion.dateDep.format(DATE_TIME_FORMAT) : null,
      aeroport: avion.aeroport,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avion = this.createFromForm();
    if (avion.id !== undefined) {
      this.subscribeToSaveResponse(this.avionService.update(avion));
    } else {
      this.subscribeToSaveResponse(this.avionService.create(avion));
    }
  }

  private createFromForm(): IAvion {
    return {
      ...new Avion(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      company: this.editForm.get(['company'])!.value,
      dateArr: this.editForm.get(['dateArr'])!.value ? moment(this.editForm.get(['dateArr'])!.value, DATE_TIME_FORMAT) : undefined,
      dateDep: this.editForm.get(['dateDep'])!.value ? moment(this.editForm.get(['dateDep'])!.value, DATE_TIME_FORMAT) : undefined,
      aeroport: this.editForm.get(['aeroport'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvion>>): void {
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

  trackById(index: number, item: IAeroport): any {
    return item.id;
  }
}
