import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersonnel, Personnel } from 'app/shared/model/personnel.model';
import { PersonnelService } from './personnel.service';
import { IAvion } from 'app/shared/model/avion.model';
import { AvionService } from 'app/entities/avion/avion.service';

@Component({
  selector: 'jhi-personnel-update',
  templateUrl: './personnel-update.component.html',
})
export class PersonnelUpdateComponent implements OnInit {
  isSaving = false;
  avions: IAvion[] = [];

  editForm = this.fb.group({
    id: [],
    lastName: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    nationality: [null, [Validators.required]],
    avion: [],
  });

  constructor(
    protected personnelService: PersonnelService,
    protected avionService: AvionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personnel }) => {
      this.updateForm(personnel);

      this.avionService.query().subscribe((res: HttpResponse<IAvion[]>) => (this.avions = res.body || []));
    });
  }

  updateForm(personnel: IPersonnel): void {
    this.editForm.patchValue({
      id: personnel.id,
      lastName: personnel.lastName,
      firstName: personnel.firstName,
      nationality: personnel.nationality,
      avion: personnel.avion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personnel = this.createFromForm();
    if (personnel.id !== undefined) {
      this.subscribeToSaveResponse(this.personnelService.update(personnel));
    } else {
      this.subscribeToSaveResponse(this.personnelService.create(personnel));
    }
  }

  private createFromForm(): IPersonnel {
    return {
      ...new Personnel(),
      id: this.editForm.get(['id'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      avion: this.editForm.get(['avion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonnel>>): void {
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

  trackById(index: number, item: IAvion): any {
    return item.id;
  }
}
