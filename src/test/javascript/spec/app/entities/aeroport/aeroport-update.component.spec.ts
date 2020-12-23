import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AeroportTestModule } from '../../../test.module';
import { AeroportUpdateComponent } from 'app/entities/aeroport/aeroport-update.component';
import { AeroportService } from 'app/entities/aeroport/aeroport.service';
import { Aeroport } from 'app/shared/model/aeroport.model';

describe('Component Tests', () => {
  describe('Aeroport Management Update Component', () => {
    let comp: AeroportUpdateComponent;
    let fixture: ComponentFixture<AeroportUpdateComponent>;
    let service: AeroportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AeroportTestModule],
        declarations: [AeroportUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AeroportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AeroportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AeroportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aeroport(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aeroport();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
