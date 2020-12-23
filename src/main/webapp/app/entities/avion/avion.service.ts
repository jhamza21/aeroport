import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvion } from 'app/shared/model/avion.model';

type EntityResponseType = HttpResponse<IAvion>;
type EntityArrayResponseType = HttpResponse<IAvion[]>;

@Injectable({ providedIn: 'root' })
export class AvionService {
  public resourceUrl = SERVER_API_URL + 'api/avions';

  constructor(protected http: HttpClient) {}

  create(avion: IAvion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avion);
    return this.http
      .post<IAvion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(avion: IAvion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avion);
    return this.http
      .put<IAvion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(avion: IAvion): IAvion {
    const copy: IAvion = Object.assign({}, avion, {
      dateArr: avion.dateArr && avion.dateArr.isValid() ? avion.dateArr.toJSON() : undefined,
      dateDep: avion.dateDep && avion.dateDep.isValid() ? avion.dateDep.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateArr = res.body.dateArr ? moment(res.body.dateArr) : undefined;
      res.body.dateDep = res.body.dateDep ? moment(res.body.dateDep) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((avion: IAvion) => {
        avion.dateArr = avion.dateArr ? moment(avion.dateArr) : undefined;
        avion.dateDep = avion.dateDep ? moment(avion.dateDep) : undefined;
      });
    }
    return res;
  }
}
