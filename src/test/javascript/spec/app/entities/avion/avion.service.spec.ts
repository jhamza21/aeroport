import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AvionService } from 'app/entities/avion/avion.service';
import { IAvion, Avion } from 'app/shared/model/avion.model';

describe('Service Tests', () => {
  describe('Avion Service', () => {
    let injector: TestBed;
    let service: AvionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvion;
    let expectedResult: IAvion | IAvion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Avion(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateArr: currentDate.format(DATE_TIME_FORMAT),
            dateDep: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Avion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateArr: currentDate.format(DATE_TIME_FORMAT),
            dateDep: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateArr: currentDate,
            dateDep: currentDate,
          },
          returnedFromService
        );

        service.create(new Avion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Avion', () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            company: 'BBBBBB',
            dateArr: currentDate.format(DATE_TIME_FORMAT),
            dateDep: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateArr: currentDate,
            dateDep: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Avion', () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            company: 'BBBBBB',
            dateArr: currentDate.format(DATE_TIME_FORMAT),
            dateDep: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateArr: currentDate,
            dateDep: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Avion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
