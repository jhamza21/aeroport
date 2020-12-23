import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AeroportTestModule } from '../../../test.module';
import { AeroportDetailComponent } from 'app/entities/aeroport/aeroport-detail.component';
import { Aeroport } from 'app/shared/model/aeroport.model';

describe('Component Tests', () => {
  describe('Aeroport Management Detail Component', () => {
    let comp: AeroportDetailComponent;
    let fixture: ComponentFixture<AeroportDetailComponent>;
    const route = ({ data: of({ aeroport: new Aeroport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AeroportTestModule],
        declarations: [AeroportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AeroportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AeroportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aeroport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aeroport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
