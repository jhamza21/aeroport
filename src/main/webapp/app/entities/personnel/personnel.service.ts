import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonnel } from 'app/shared/model/personnel.model';

type EntityResponseType = HttpResponse<IPersonnel>;
type EntityArrayResponseType = HttpResponse<IPersonnel[]>;

@Injectable({ providedIn: 'root' })
export class PersonnelService {
  public resourceUrl = SERVER_API_URL + 'api/personnel';

  constructor(protected http: HttpClient) {}

  create(personnel: IPersonnel): Observable<EntityResponseType> {
    return this.http.post<IPersonnel>(this.resourceUrl, personnel, { observe: 'response' });
  }

  update(personnel: IPersonnel): Observable<EntityResponseType> {
    return this.http.put<IPersonnel>(this.resourceUrl, personnel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonnel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonnel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
